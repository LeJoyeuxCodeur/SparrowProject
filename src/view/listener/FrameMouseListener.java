package view.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import view.MainFrame;

public class FrameMouseListener implements MouseListener, MouseMotionListener{
	private Thread mouseThread;
	
	public FrameMouseListener(Thread mouseThread) {
		this.mouseThread = mouseThread;
	}
	
	
	@Override
	public void mouseDragged(MouseEvent arg0) {}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		MainFrame.lastXFound = arg0.getX();
		MainFrame.lastYFound = arg0.getY();
		
		mouseThread.resume();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseThread.resume();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseThread.suspend();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
