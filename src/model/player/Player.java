package model.player;

/**
 * This class represents a player, it can be a human player or a non playing player
 */
public class Player {
	/**
	 * Quantity of money than a player carry on him
	 */
	private Integer money;
	
	/**
	 * The list of differents boats of a player
	 */
	private Boat boat;
	
	/**
	 * Build a player
	 * @param money {@link Integer}
	 */
	public Player(Integer money){
		this.money = money;
	}

	/**
	 * Accessor for money
	 * @return {@link Integer}
	 */
	public Integer getMoney() {
		return money;
	}

	/**
	 * Mutator for money 
	 * @param money {@link Integer}
	 */
	public void setMoney(Integer money) {
		this.money = money;
	}
	
	/**
	 * Accessor for boat
	 * @return {@link Boat}
	 */
	public Boat getBoat() {
		return boat;
	}

	/**
	 * Mutator for boat 
	 * @param boat {@link Boat}
	 */
	public void setBoat(Boat boat) {
		this.boat = boat;
	}
}