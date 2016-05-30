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
		
		frame.getMouseMotionThread().resume();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		frame.setBoatXCliqued(arg0.getX());
		frame.setBoatYCliqued(arg0.getY());
		MainFrame.tileCliqued = true;
		
		frame.getMouseThread().resume();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		frame.getMouseMotionThread().resume();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		frame.getMouseMotionThread().suspend();
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
