package td;

public class OrderData {


	private int id;
	private String ordrId;
	private double price;
	private double brokerage;


	public OrderData(int id, String ordrId, double price, double brokerage) {
		super();
		this.id = id;
		this.ordrId = ordrId;
		this.price = price;
		this.brokerage = brokerage;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrdrId() {
		return ordrId;
	}
	public void setOrdrId(String ordrId) {
		this.ordrId = ordrId;
	}
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	public double getBrokerage() {
		return brokerage;
	}
	public void setBrokerage(double brokerage) {
		this.brokerage = brokerage;
	}


}
