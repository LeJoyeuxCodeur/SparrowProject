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
				// LEFT DOWN
				while(MainFrame.lastYFound < mouseDetectionOffset && MainFrame.lastXFound < mouseDetectionOffset){
					setChanged("POSY_-10");
					setChanged("POSX_-10");
					setChanged("REPAINT");
				}
				// LEFT UP
				while(MainFrame.lastXFound < mouseDetectionOffset && MainFrame.lastYFound > dim.getHeight() - mouseDetectionOffset){
					setChanged("POSX_-10");
					setChanged("POSY_10");
					setChanged("REPAINT");
				}
				// RIGHT DOWN
				while(MainFrame.lastXFound > dim.getWidth() - mouseDetectionOffset && MainFrame.lastYFound < mouseDetectionOffset){
					setChanged("POSX_10");
					setChanged("POSY_-10");
					setChanged("REPAINT");
				}
				// RIGHT UP
				while(MainFrame.lastXFound > dim.getWidth() - mouseDetectionOffset && MainFrame.lastYFound > dim.getHeight() - mouseDetectionOffset){
					setChanged("POSX_10");
					setChanged("POSY_10");
					setChanged("REPAINT");
				}
				// DOWN
				while(MainFrame.lastYFound < mouseDetectionOffset && MainFrame.lastXFound > mouseDetectionOffset && MainFrame.lastXFound < dim.getWidth() - mouseDetectionOffset){
					setChanged("POSY_-10");
					setChanged("REPAINT");
				}
				// UP
				while(MainFrame.lastYFound > dim.getHeight() - mouseDetectionOffset && MainFrame.lastXFound > mouseDetectionOffset && MainFrame.lastXFound < dim.getWidth() - mouseDetectionOffset){
					setChanged("POSY_10");
					setChanged("REPAINT");
				}
				// LEFT
				while(MainFrame.lastXFound < mouseDetectionOffset && MainFrame.lastYFound > mouseDetectionOffset && MainFrame.lastYFound < dim.getHeight() - mouseDetectionOffset){
					setChanged("POSX_-10");
					setChanged("REPAINT");
				}
				// RIGHT
				while(MainFrame.lastXFound > dim.getWidth() - mouseDetectionOffset && MainFrame.lastYFound > mouseDetectionOffset && MainFrame.lastYFound < dim.getHeight() - mouseDetectionOffset){
					setChanged("POSX_10");
					setChanged("REPAINT");
				}
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