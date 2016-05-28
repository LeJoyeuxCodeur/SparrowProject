package view.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import view.MainFrame;

public class FrameMouseListener implements MouseListener, MouseMotionListener{
	private MainFrame frame;
	
	public FrameMouseListener(MainFrame frame) {
		this.frame = frame;
	}
	
	
	@Override
	public void mouseDragged(MouseEvent arg0) {}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		MainFrame.lastXFound = arg0.getX();
		MainFrame.lastYFound = arg0.getY();
		
		frame.getMouseThread().resume();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		frame.setXCliqued(arg0.getX() + frame.getPosX());
		frame.setYCliqued(arg0.getY() + frame.getPosY());
		MainFrame.tileCliqued = true;
		
		frame.getMouseThread().resume();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		frame.getMouseThread().resume();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		frame.getMouseThread().suspend();
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
