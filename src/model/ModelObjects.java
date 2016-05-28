package model;

import java.util.Observable;
import java.util.Observer;

import model.field.FullMap;

public class ModelObjects extends Observable implements Observer {
	private FullMap fullMap;
	private Boat boat;

	public ModelObjects(){
		fullMap = new FullMap();
		boat = new Boat();
	}


	public FullMap getFullMap() {
		return fullMap;
	}


	public void setFullMap(FullMap fullMap) {
		this.fullMap = fullMap;
	}


	public Boat getBoat() {
		return boat;
	}


	public void setBoat(Boat boat) {
		this.boat = boat;
	}


	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers(arg);
	}
}