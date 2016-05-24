package model.thread;

import static model.log.ProjectLogger.LOGGER;

import java.awt.Dimension;
import java.util.Observable;

import view.MainFrame;

public class MouseRunnable extends Observable implements Runnable{
	private Dimension dim;
	private int mouseDetectionOffset;
	
	public MouseRunnable(Dimension dim, int mouseDetectionOffset){
		this.dim = dim;
		this.mouseDetectionOffset = mouseDetectionOffset;
		
		LOGGER.info("Mouse thread initialized");
	}
	
	public void run(){
		LOGGER.info("Mouse thread started");
		while(true){
			if(MainFrame.lastXFound != -1 && MainFrame.lastYFound != -1){
				// DOWN
				while(MainFrame.lastYFound < mouseDetectionOffset)
					setChanged("POSY_10");
				// UP
				while(MainFrame.lastYFound > dim.getHeight() - mouseDetectionOffset)
					setChanged("POSY_-10");
				// LEFT
				while(MainFrame.lastXFound < mouseDetectionOffset){
					setChanged("POSX_10");
				}
				// RIGHT
				while(MainFrame.lastXFound > dim.getWidth() - mouseDetectionOffset)
					setChanged("POSX_-10");
			}
			Thread.currentThread().suspend();
		}
	}
	
	private void setChanged(String s){
		setChanged();
		notifyObservers(s);
		
		sleep(25);
	}
	
	private void sleep(int n){
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}