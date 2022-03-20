package Server;
import java.util.ArrayList;

public class Client {
	
	private String id;
	private String password;
	private int balance;
	private ArrayList<Payment> historico;
	
	
	public Client(String id, String password, int balance) {
		this.id = id;
		this.password = password;
		this.balance = balance;
		this.historico = new ArrayList<Payment>();
	}


	public String getId() {
		return id;
	}


	public String getPassword() {
		return password;
	}


	public int getBalance() {
		return balance;
	}


	public ArrayList<Payment> getHistorico() {
		return historico;
	}


	public void setId(String id) {
		this.id = id;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setBalance(int balance) {
		this.balance = balance;
	}


	public void setHistorico(ArrayList<Payment> historico) {
		this.historico = historico;
	}
}
