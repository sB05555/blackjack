import java.util.*;

public class Game {
	private int numPlayers;
	private ArrayList<Player> players = new ArrayList<Player>();
	private static Card [] deck = new Card[52];
	private Scanner scan = new Scanner(System.in);
	private boolean keepGoing = false;
	private String userDecision = "";
	
	public Game() {
		resetDeck();
		initialize();
	}
	
	public void getCards(int pos) {
		players.get(pos).getCards();
	}
	
	public void getDealerCards() {
		players.get(0).getCards();
	}
	
	public void initialize() {
		System.out.println("How many players will play today?");
		numPlayers = scan.nextInt();
		while(numPlayers < 2 || numPlayers > 7) {
			System.out.println("Please enter enough players (2-7)");
			numPlayers = scan.nextInt();
		}
		for(int i = 1; i <= numPlayers; i++) {
			String name = "";
			System.out.println("Player " + i + " please enter your name.");
			if(scan.hasNext()) {
				name = scan.nextLine();
				while(name.length() < 1){
					name = scan.nextLine();
				}
			}
			name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
			players.add(new Player(name));
			placeBet(i - 1);
			dealOneCardtoPlayer(i - 1);
			dealOneCardtoPlayer(i - 1);
			players.get(i - 1).getCards();
			System.out.println();
		}
		players.add(0, new Player("Dealer"));
		dealOneCardtoPlayer(0);
		dealOneCardtoPlayer(0);
		players.get(0).getCards();
		System.out.println();
	}
	
	public void play() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Do you want to play Blackjack? (Y/N)");
		userDecision = scanner.nextLine().toUpperCase();
		while(!userDecision.equals("Y")&&!userDecision.equals("N")) {
			System.out.println("Please enter either Y or N.");
			userDecision = scanner.nextLine().toUpperCase();
		}
		while(!userDecision.equals("N")) {
		int finished = 0;
		while (finished <= players.size() - 2) {
			for(int i = 1; i < players.size(); i++) {
				if(players.get(i).getStatus()) {
					System.out.println(players.get(i).getName() + " has " + players.get(i).getTotal()); 
					System.out.println(players.get(i).getName() + ", do you want to hit, stand, or surrender?");
					int input = validateInput(scan.nextLine(), players.get(i));
					if (input==1) {
						keepGoing = true;
					}
					while (keepGoing) {
						dealOneCardtoPlayer(i);
						players.get(i).getCards();
						if(players.get(i).getTotal() > 21) {
							System.out.println(players.get(i).getName() + " went over.");
							finished++;
							players.get(i).quit();
							keepGoing = false;
						} else if(players.get(i).getTotal() == 21) {
							finished++;
							players.get(i).stop();
							keepGoing = false;
						}
						if(keepGoing) {
						System.out.println(players.get(i).getName() + ", do you want to hit, stand, or surrender?");
						input = validateInput(scan.nextLine(), players.get(i));
						if(input!=1) {
							keepGoing = false;
						}
						}
					}
					if(input == 0) {
						players.get(i).quit();
						finished++;
					} else {
						players.get(i).stop();
						finished++;
					}
					System.out.println();
				}
			}
			while(players.get(0).getTotal() < 17) {
				dealOneCardtoPlayer(0);
			   players.get(0).getCards();
			   System.out.println();
			}
		}
		distributeMoney();
		tellMoney();
		resetDeck();
		System.out.println("Do you want to play again?");
		userDecision = scanner.nextLine().toUpperCase();
		while(!userDecision.equals("Y")&&!userDecision.equals("N")) {
			System.out.println("Please enter either Y or N.");
			userDecision = scanner.nextLine().toUpperCase();
		}
		if(userDecision.equals("Y")) {
		playAgain();
		}
		}
		System.out.println("Thanks for playing!");
		
	}
	
	public int validateInput(String str, Player player) {
		while (!str.equalsIgnoreCase("hit") && !str.equalsIgnoreCase("stand") 
				&& !str.equalsIgnoreCase("surrender")){
			System.out.println("Please enter \"hit\", \"stand\", or \"surrender\".");
			str = scan.nextLine();
		}
		if (str.equals("hit"))
			return 1;
		else if (str.equals("stand"))
			return 2;
		return 0;
	}
	
	public void placeBet(int i) {
		System.out.println(players.get(i).getName() + ", how much do you want to bet?");
		int input = scan.nextInt();
		while(input < 2 || input > 500 ||  input> players.get(i).getMoney()) {
			System.out.println("Please enter a integer from 2 - 500, or -1 to omit, and make sure that it is less than your amount of money ($"+players.get(i).getMoney()+")");
			input = scan.nextInt();
		if (input == -1)
				players.remove(i);
		} 
		players.get(i).setBet(input);
		System.out.println();
	}
	
	public  void resetDeck() {
		for (int i = 0; i < 13; i++) {
			for (int j =0; j < 4; j++) {
				Card c = new Card(i + 1);
				deck[i*4 + j] = c;
			}
		}
	}
	
	public void dealOneCardtoPlayer(int num) {
		int random = (int) (Math.random() * 51) + 1;
		while((deck[random - 1].hasBeenUsed())) {
			random = (int) (Math.random() * 51) + 1;
		}
		Card c = deck[random];
		c.Used();
		players.get(num).givePlayerCard(c);
	}
	
	public void dealOneCardToEachPlayer() {
		for(int i = 0; i < players.size(); i++)
			dealOneCardtoPlayer(i);
	}
	
	public void tellMoney() {
		for(int i=1; i<players.size(); i++) {
			System.out.println("Player "+i+" has: $"+players.get(i).getMoney());
		}
	}
	
	public void playAgain() {
		players.remove(0);
		for(int i = 1; i <= numPlayers; i++) {
			players.get(i-1).resetCards();
			players.get(i-1).resetStatus();
			players.get(i-1).resetTotal();
			placeBet(i - 1);
			dealOneCardtoPlayer(i - 1);
			dealOneCardtoPlayer(i - 1);
			players.get(i - 1).getCards();
			System.out.println();
			
		}
		players.add(0, new Player("Dealer"));
		dealOneCardtoPlayer(0);
		dealOneCardtoPlayer(0);
		players.get(0).getCards();
		System.out.println();
	}
	
	/* 
	 If the player is dealt an Ace and a ten-value card (called a "blackjack" or "natural"), and the dealer does not, the player wins and usually receives a bonus.
If the player exceeds a sum of 21 ("busts"), the player loses, even if the dealer also exceeds 21.
If the dealer exceeds 21 ("busts") and the player does not, the player wins.
If the player attains a final sum higher than the dealer and does not bust, the player wins.
If both dealer and player receive a blackjack or any other hands with the same sum, this will be called a "push" and no one wins.
	 */
	
	public void distributeMoney() {
		for(int i=1; i<players.size(); i++) {
			if(players.get(i).getTotal()==21) {
				if(players.get(0).getTotal()!=21) {
					players.get(i).setMoney(true);
				}
			}
			else if(players.get(i).getTotal()>21) {
			players.get(i).setMoney(false);
			} 
			else if(players.get(0).getTotal()>21) {
				players.get(i).setMoney(true);
			}
            else if ((21-players.get(i).getTotal())<(21-players.get(0).getTotal())){
			players.get(i).setMoney(true);
			} 
            else {
				players.get(i).setMoney(false);
			}
		}
	}

}









