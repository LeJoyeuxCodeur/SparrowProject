package view;

import static model.log.ProjectLogger.LOGGER;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import model.configuration.GameProperties;
import model.field.Area;
import model.field.Cell;
import model.field.FullMap;

public class MainFrame extends JFrame implements Observer{
	private static final long serialVersionUID = 1L;
	private FullMap model;

	public MainFrame(FullMap model){
		this.model = model;
		this.model.addObserver(this);
		
		LOGGER.info("View initialized");
		
		initFrame();
	}
	
	private void initFrame() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		setSize(dim.width, dim.height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		Integer width = Integer.parseInt((String) GameProperties.PROPERTIES.get("cellWidth"));
		Integer height = Integer.parseInt((String) GameProperties.PROPERTIES.get("cellHeight"));
		
		setLayout(new GridLayout(width, height));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof FullMap && arg.equals("MAP_CHARGED")){
			paintComponents(getGraphics());
		}
	}
	
	@Override
	public void paintComponents(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Graphics offgc;
		Image offscreen = null;
		
		offscreen = createImage(this.getWidth(), this.getHeight());
		if(offscreen != null){
			offgc = offscreen.getGraphics();
			
			// reload map
			Cell[][] firstArea = model.getFullMap()[0][0].getCells();
			
			for(int i = 0 ; i < firstArea.length; i++){
				for(int j = 0; j < firstArea[0].length; j++){
					offgc.drawRect(i * 20, j * 20, 20, 20);
				}
			}
			
			
			g2.drawImage(offscreen, 40, 40, this);
		}
	}
}