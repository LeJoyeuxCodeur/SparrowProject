package model.thread;

import java.awt.Dimension;
import java.util.Observable;

import org.apache.log4j.Logger;

import model.log.ProjectLogger;
import view.MainFrame;

public class MouseMotionRunnable extends Observable implements Runnable{
	private Dimension dim;
	private int mouseDetectionOffset;
	private Logger logger = new ProjectLogger(this.getClass()).getLogger();
	
	public MouseMotionRunnable(Dimension dim, int mouseDetectionOffset){		
		this.dim = dim;
		this.mouseDetectionOffset = mouseDetectionOffset;
		logger.info("Mouse motion thread initialized");
	}
	
	public void run(){
		logger.info("Mouse motion thread started");
		
		int width = (int) dim.getWidth();
		int height = (int) dim.getHeight();
				
		while(true){
			// LEFT DOWN
			while(MainFrame.lastYFound  < mouseDetectionOffset && MainFrame.lastXFound  < mouseDetectionOffset){
				setChanged("POSY_-10");
				setChanged("POSX_-10");
				setChanged("REPAINT");
			}
			// LEFT UP
			while(MainFrame.lastXFound  < mouseDetectionOffset && MainFrame.lastYFound  > height - mouseDetectionOffset){
				setChanged("POSX_-10");
				setChanged("POSY_10");
				setChanged("REPAINT");
			}
			// RIGHT DOWN
			while(MainFrame.lastXFound  > width - mouseDetectionOffset && MainFrame.lastYFound  < mouseDetectionOffset){
				setChanged("POSX_10");
				setChanged("POSY_-10");
				setChanged("REPAINT");
			}
			// RIGHT UP
			while(MainFrame.lastXFound  > width - mouseDetectionOffset && MainFrame.lastYFound  > height - mouseDetectionOffset){
				setChanged("POSX_10");
				setChanged("POSY_10");
				setChanged("REPAINT");
			}
			// DOWN
			while(MainFrame.lastYFound  < mouseDetectionOffset && MainFrame.lastXFound  > mouseDetectionOffset && MainFrame.lastXFound  < width - mouseDetectionOffset){
				setChanged("POSY_-10");
				setChanged("REPAINT");
			}
			// UP
			while(MainFrame.lastYFound  > height - mouseDetectionOffset && MainFrame.lastXFound  > mouseDetectionOffset && MainFrame.lastXFound  < width - mouseDetectionOffset){
				setChanged("POSY_10");
				setChanged("REPAINT");
			}
			// LEFT
			while(MainFrame.lastXFound  < mouseDetectionOffset && MainFrame.lastYFound  > mouseDetectionOffset && MainFrame.lastYFound  < height - mouseDetectionOffset){
				setChanged("POSX_-10");
				setChanged("REPAINT");
			}
			// RIGHT
			while(MainFrame.lastXFound  > width - mouseDetectionOffset && MainFrame.lastYFound  > mouseDetectionOffset && MainFrame.lastYFound  < height - mouseDetectionOffset){
				setChanged("POSX_10");
				setChanged("REPAINT");
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