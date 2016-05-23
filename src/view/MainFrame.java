package view;

import static model.log.ProjectLogger.LOGGER;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import model.configuration.GameProperties;
import model.field.Cell;
import model.field.FieldType;
import model.field.FullMap;

public class MainFrame extends JFrame implements Observer{
	private static final long serialVersionUID = 1L;
	private FullMap model;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private Image seaTexture, earthTexture;

	public MainFrame(FullMap model){
		this.model = model;
		this.model.addObserver(this);
		
		try{
			earthTexture = ImageIO.read(new File("data/textures/EARTH.png")).getScaledInstance(104, 60, Image.SCALE_DEFAULT);
			seaTexture = ImageIO.read(new File("data/textures/SEA.png")).getScaledInstance(104, 60, Image.SCALE_DEFAULT);
		} catch (IOException e) {
			LOGGER.error("Error while loading textures");
		}
		
		
		initFrame();

		LOGGER.info("View initialized");
	}

	private void initFrame() {

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
			repaint();
		}
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
			
		if(model != null && model.getFullMap() != null && model.getFullMap()[0][0] != null && model.getFullMap()[0][0].getCells() != null){
			// reload map
			Cell[][] firstArea = model.getFullMap()[0][0].getCells();

			for(int j = 0 ; j < firstArea.length; j++){
				for(int i = 0; i < firstArea[0].length; i++){
					if(firstArea[i][j] != null) {
						if(firstArea[i][j].getFieldType() == FieldType.EARTH){
							g2.drawImage(earthTexture, i*52, j*30, null);
							//g2.drawRect((j+10)*26, (i+10)*15, 26, 15);
						}
						else if(firstArea[i][j].getFieldType() == FieldType.SEA){
							g2.drawImage(seaTexture, i*52, j*30, null);
							//g2.drawRect((j+10)*26, (i+10)*15, 26, 15);
						}
					}
				}
			}
		}
	}
}