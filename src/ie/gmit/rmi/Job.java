package ie.gmit.rmi;
/**
* This class is used to pass it into the queue
* 
* @author Shane Gleeson
*/
public class Job {
	private String taskNumber;
	private String algo;
	private String str1;
	private String str2;
	
	public Job(){
		super();
	}
	
	public Job(String s, String t, String algorithm,String taskNum) {
		this.algo = algorithm;
		this.str1 = s;
		this.str2 = t;
		this.taskNumber = taskNum;
	}

	//Getters and Setters
	public String getTaskNumber() {
		return taskNumber;
	}
	
	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}
	
	public String getStr1() {
		return str1;
	}
	
	public void setStr1(String str1) {
		this.str1 = str1;
	}
	
	public String getStr2() {
		return str2;
	}
	
	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public String getAlgo() {
		return algo;
	}

	public void setAlgo(String algo) {
		this.algo = algo;
	}
}//end
