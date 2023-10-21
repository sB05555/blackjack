
import java.util.*;

public class Player {
	private ArrayList<Card> playersCards = new ArrayList<Card>();
	private int money;
	private int bet;
	private String name;
	private int total;
	private boolean isDealer;
	private boolean status;
	
	public Player(String n) {
		money = 1000;
		name = n;
		total = 0;
		status = true;
		if (name.equals("Dealer"))
			isDealer = true;
	}
	
	public void givePlayerCard(Card c) {
		playersCards.add(c);
	}
	
	public void getCards() {
		total = 0;
		if(!isDealer) {
			System.out.println(name + "'s cards: " + playersCards.toString());
			for (int i = 0; i < playersCards.size(); i++) 
				total+=playersCards.get(i).getValue();
		} else {
			System.out.print(name + "'s cards: [");
			for (int i = 0; i < playersCards.size() - 1; i++) {
				System.out.print (playersCards.get(i).getName() + ", ");
				total += playersCards.get(i).getValue();
			}	
			System.out.println("XX]");
		}
		System.out.println(name + "'s total is " + total);
	}
	
	public void quit() {
		stop();
	}
	
	public void stop() {
		status = false;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public int getBet() {
		return bet;
	}
	
	public void setBet(int n) {
		bet = n;
	}
	
	public void setMoney(boolean won) {
		if (won) {
			money += bet;
		} else {
			money -= bet;
		}
		bet = 0;
	}
	
	public int getTotal(){
		return total;
	}
	
	public String getName() {
		return name;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void resetCards() {
		playersCards.clear();
	}
	
	public void resetStatus() {
		status = true;
	}
	
	public void resetTotal() {
		total=0;
	}
}





