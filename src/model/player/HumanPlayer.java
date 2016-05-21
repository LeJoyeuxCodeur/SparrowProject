package model.player;

/**
 * This class represents a human player, it contains all informations from a general {@link Player}
 */
public class HumanPlayer extends Player{
	/**
	 * Build a human player
	 * @param money {@link Integer}
	 */
	public HumanPlayer(Integer money) {
		super(money);
	}
}