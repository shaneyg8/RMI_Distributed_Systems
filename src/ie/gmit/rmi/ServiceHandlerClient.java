package ie.gmit.rmi;
/**
* @author Shane Gleeson
*/
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;


/*This class creates new Threads
 Sends calls to StringService.compare() method
 And Adds the job to the queue */


public class ServiceHandlerClient implements Runnable {
	private BlockingQueue<Job> iq;
	private Resultator result;
	private StringService service;
	private Map<String, Resultator> oq;

	public ServiceHandlerClient(BlockingQueue<Job> inQueue, Map<String, Resultator> outQueue, StringService ss) {
		this.iq = inQueue;
		this.service = ss;
		this.oq = outQueue;
	}

	//Create a new thread
	public void run() {
		Job job = iq.poll();

		try {
		//Simulates an asychronous service by slowing the threads down
			Thread.sleep(10000);
	
			//Send the strings and the algorithm to the StringService compare() method
			result = service.compare(job.getStr1(), job.getStr2(), job.getAlgo());
			
			//Adds the taskNumber and Resultator to OutQueue
			oq.put(job.getTaskNumber(), result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
