package ie.gmit.rmi;
/**
* @author Shane Gleeson
*/
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


/*
This class extends UnicastRemoteObject and implements StringService interface
Sends requests to StringComparator
 */
public class StringServiceImpl extends UnicastRemoteObject implements StringService {
	
	private static final long serialVersionUID = 1L;

	public StringServiceImpl() throws RemoteException {
		super();
	}

	public Resultator compare(String s, String t, String algo) throws RemoteException {
		Resultator result = new ResultatorImpl();
		
		//Compare Strings w/ StringComparator
		Thread thread = new Thread(new StringComparator(s, t, result, algo));
        thread.start();

		return result;
	}
}