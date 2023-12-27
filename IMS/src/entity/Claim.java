package entity;

public class Claim {
	private int claimId;
    private String claimNumber;
    private String dateFiled;
    private double claimAmount;
    private String status;
    private String policy;
    private Client client;
	
    //Parameterized  Constructors
    public Claim(int claimId, String claimNumber, String dateFiled, double claimAmount, String status, String policy,
			Client client) {
		super();
		this.claimId = claimId;
		this.claimNumber = claimNumber;
		this.dateFiled = dateFiled;
		this.claimAmount = claimAmount;
		this.status = status;
		this.policy = policy;
		this.client = client;
	}
    
    //getter and setters
	public int getClaimId() {
		return claimId;
	}

	public void setClaimId(int claimId) {
		this.claimId = claimId;
	}

	public String getClaimNumber() {
		return claimNumber;
	}

	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}

	public String getDateFiled() {
		return dateFiled;
	}

	public void setDateFiled(String dateFiled) {
		this.dateFiled = dateFiled;
	}

	public double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	//toString() method

	@Override
	public String toString() {
		return "Claim [claimId=" + claimId + ", claimNumber=" + claimNumber + ", dateFiled=" + dateFiled
				+ ", claimAmount=" + claimAmount + ", status=" + status + ", policy=" + policy + ", client=" + client
				+ "]";
	}
    
	
	
	
    
}
