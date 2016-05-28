package model.thread;

import java.util.Observable;

import org.apache.log4j.Logger;

import model.log.ProjectLogger;
import view.MainFrame;

public class MouseRunnable extends Observable implements Runnable{
	private Logger logger = new ProjectLogger(this.getClass()).getLogger();
	
	public void run(){
		logger.info("Mouse thread initialized");
		logger.info("Mouse thread started");
		
		while(true){
			if(MainFrame.tileCliqued){
				setChanged("TILE_CLIQUED");
				setChanged("REPAINT");
				MainFrame.tileCliqued = false;
			}
			Thread.currentThread().suspend();
		}
	}
	
	private void setChanged(String s){
		setChanged();
		notifyObservers(s);
		
		sleep(10);
	}
	
	private void sleep(int n){
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}