package ie.gmit.rmi;
/**
* @author Shane Gleeson
*/
import java.rmi.Remote;
import java.rmi.RemoteException;


/* This Interface extends remote
 and has the method Resultator compare();
 */

public interface StringService extends Remote{

    public Resultator compare(String s, String t, String algo) throws RemoteException;

}