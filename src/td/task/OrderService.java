package td.task;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class OrderService extends Service<Number> {

	public double price;
	public double brokerage;
	public int minreftime;
	public int maxreftime;
	public OrderTask ot =new OrderTask();
	public OrderService(double price, double brokerage, int minreftime,int maxreftime) {
		super();
		this.price = price;
		this.brokerage = brokerage;
		this.minreftime = minreftime;
		this.maxreftime = maxreftime;
	}

	public OrderService() {
		super();

	}


	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
		ot.setPrice(price);
	}

	public double getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(double brokerage) {
		this.brokerage = brokerage;
		ot.setBrokerage(brokerage);
	}



	public int getMinreftime() {
		return minreftime;
	}

	public void setMinreftime(int minreftime) {
		ot.setMinreftime(minreftime);
		this.minreftime = minreftime;
	}

	public int getMaxreftime() {
		return maxreftime;
	}

	public void setMaxreftime(int maxreftime) {
		ot.setMaxreftime(maxreftime);
		this.maxreftime = maxreftime;
	}

	@Override
	protected Task<Number> createTask() {
		// TODO Auto-generated method stub
		ot = new OrderTask(price,brokerage,minreftime,maxreftime);
		return ot;
	}




}
