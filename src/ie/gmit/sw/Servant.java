package ie.gmit.sw;

/**
 * Shane Gleeson - G00311793
 */

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import ie.gmit.rmi.StringService;
import ie.gmit.rmi.StringServiceImpl;

public class Servant {

		public static void main(String[] args) throws Exception{
			
		StringService service = new StringServiceImpl();
		
		//Start the RMI on port 1099
		LocateRegistry.createRegistry(1099);
		
		//Bind our remote object with the name "stringservice"
		Naming.rebind("stringservice", service);
		
		System.out.println("Server ready....");
	}
}