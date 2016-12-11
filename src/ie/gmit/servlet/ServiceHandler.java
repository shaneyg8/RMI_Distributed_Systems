package ie.gmit.servlet;
/**
* @author Shane Gleeson
*/
import java.io.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.*;
import javax.servlet.http.*;

import ie.gmit.rmi.Job;
import ie.gmit.rmi.Resultator;
import ie.gmit.rmi.ServiceHandlerClient;
import ie.gmit.rmi.StringService;

/*
This class waits for a doPost() request from a Web Application
The doPost() then calls the doGet() method
Jobs are added to an InQueue and deleted from an OutQueue when Tasks are completed,
The Page refreshes every 10Seconds, until it completes.
 */
public class ServiceHandler extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private String remoteHost = null;
	private static long jobNumber = 0;
	private volatile BlockingQueue<Job> inQueue;
	private volatile Map<String, Resultator> outQueue;
	private volatile ExecutorService e;
	private volatile boolean jobComplete = false;
	private volatile String distance = "";
	private final int THREAD_POOL_SIZE = 3;
	
	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
		remoteHost = ctx.getInitParameter("RMI_SERVER"); //Reads the value from the <context-param> in web.xml
		
		inQueue = new LinkedBlockingQueue<Job>();
		outQueue = new HashMap<String,Resultator>();
		e = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		//Initialise some request variables with the submitted form info. These are local to this method and thread safe...
		String algo = req.getParameter("cmbAlgorithm");
		String s = req.getParameter("txtS");
		String t = req.getParameter("txtT");
		String taskNumber = req.getParameter("frmTaskNumber");

		StringService ss = null;
		try {
			//Create the RMI, gets the Remote Message Object
			ss = (StringService) Naming.lookup("rmi://localhost:1099/stringservice");
		} catch (NotBoundException e1) {
			e1.printStackTrace();
		}
		
		out.print("<html><head><title>Distributed Systems Assignment</title>");		
		out.print("</head>");		
		out.print("<body>");
		
		if (taskNumber == null){
			jobNumber++;
			taskNumber = new String("T" + jobNumber);
			jobComplete = false;

			//Create a new Job object (Request)
			Job job = new Job(s, t, algo, taskNumber);

			//Add the job to the queue
			inQueue.add(job);

			//Pass the Job to the ServiceHandlerClient
			Runnable client = new ServiceHandlerClient(inQueue, outQueue, ss);

			//Execute the Client(fixed_pool_size)
			e.execute(client);

			
		}else {
			//Check outQueue is the job has been completed

			// Get the Value associated with the current job number
			if (outQueue.containsKey(taskNumber)) {
				// Get the Resultator item from the map by tasknumber
				Resultator r = outQueue.get(taskNumber);

				jobComplete = r.isProcessed();

				// Check to see if the Resultator item is Processed
				if (jobComplete == true) {
					// Remove the processed item from Map by taskNumber
					outQueue.remove(taskNumber);
					//Get the Distance of the Current Task
					distance = r.getResult();
					out.print("<center>YOUR REQUEST HAS BEEN PROCESSED AND THE RESULT IS: "+distance+"</center>");
				}
				else
				{
					out.print("<font color=\"#993333\"><b>");
					out.print("Please wait while the result is being calculated..");
				}
			}
		}

		//If the task is complete there is no need to send the form again, just output a thank you message
				if(jobComplete){
					out.print("<font color=\"#993333\"><b>");
					out.print("<br><br><center>THANK YOU FOR USING THE SERVICE<center>");
				}
				else//if task is not complete poll again
				{
					out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
					out.print("<div id=\"r\"></div>");
				
					out.print("<font color=\"#993333\"><b>");
					out.print("RMI Server is located at " + remoteHost);
					out.print("<br>Algorithm: " + algo);		
					out.print("<br>String <i>s</i> : " + s);
					out.print("<br>String <i>t</i> : " + t);
				
					//Refreshes every 10 seconds until the task is completed
					out.print("<form name=\"frmRequestDetails\">");
					out.print("<input name=\"cmbAlgorithm\" type=\"hidden\" value=\"" + algo + "\">");
					out.print("<input name=\"txtS\" type=\"hidden\" value=\"" + s + "\">");
					out.print("<input name=\"txtT\" type=\"hidden\" value=\"" + t + "\">");
					out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
					out.print("</form>");								
					out.print("</body>");	
					out.print("</html>");	
				
					out.print("<script>");
					out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 10000);");
					out.print("</script>");
				}
			}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
