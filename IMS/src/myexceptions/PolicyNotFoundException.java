package myexceptions;

public class PolicyNotFoundException extends Exception {
	
	public PolicyNotFoundException(String string) {
		super("Policy not found with ID: "+string);
	}
	

}
