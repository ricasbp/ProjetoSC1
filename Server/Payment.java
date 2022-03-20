package Server;

public class Payment {
	
	private Client c1;
	private Client c2;
	private int amount;
	
	
	public Client getC1() {
		return c1;
	}
	
	public Client getC2() {
		return c2;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setC1(Client c1) {
		this.c1 = c1;
	}
	
	public void setC2(Client c2) {
		this.c2 = c2;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
