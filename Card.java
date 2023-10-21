
public class Card {
	private int value;
	private String name;
	private boolean isUsed;
	
	public Card(int val) {
		value = val;
		isUsed = false;
		
		if (val == 11) {
			name = "Jack";
			value=10;
		}
		else if (val == 12) {
			name = "Queen";
			value=10;
		}
		else if (val == 13) {
			name = "King";
			value=10;
		}
		else if (val == 1) {
			name = "Ace";
		}
		else {
			name = val + "";
		}
	}
	public int getValue() {
		return value;
	}
	
	public void setvalue(int val) {
		value  = val;
	}
	
	public String getName() {
		return name;
	}
	
	public void Used() {
		isUsed = true;
	}
	
	public boolean hasBeenUsed() {
		return isUsed;
	}
	
	public String toString() {
		return name;
	}

}
