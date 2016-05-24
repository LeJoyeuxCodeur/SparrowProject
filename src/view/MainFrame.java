package view;

import static model.log.ProjectLogger.LOGGER;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
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
import model.thread.MouseRunnable;

public class MainFrame extends JFrame implements Observer, MouseMotionListener{
	private static final long serialVersionUID = 1L;
	private FullMap model;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private Image seaTexture, earthTexture;
	private int posX, posY;
	public static int lastXFound=-1, lastYFound=-1;
	private int mouseDetectionOffset = 50;
	private MouseRunnable mouseRunnable = new MouseRunnable(dim, mouseDetectionOffset);
	private Thread mouseThread = new Thread(mouseRunnable);
	
	public MainFrame(FullMap model){
		this.model = model;
		this.model.addObserver(this);
		mouseRunnable.addObserver(this);
		
		try{
			earthTexture = ImageIO.read(new File("data/textures/EARTH.png")).getScaledInstance(104, 60, Image.SCALE_DEFAULT);
			seaTexture = ImageIO.read(new File("data/textures/SEA.png")).getScaledInstance(104, 60, Image.SCALE_DEFAULT);
		} catch (IOException e) {
			LOGGER.error("Error while loading textures");
		}
		
		
		initFrame();
		addListeners();
		initThreads();

		LOGGER.info("View initialized");
	}

	private void initThreads() {
		mouseThread.start();
	}

	private void addListeners() {
		addMouseMotionListener(this);
	}

	private void initFrame() {
		setSize(dim.width, dim.height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		Integer cellNumberOnWidth = Integer.parseInt((String) GameProperties.PROPERTIES.get("cellNumberOnWidth"));
		Integer cellNumberOnHeight = Integer.parseInt((String) GameProperties.PROPERTIES.get("cellNumberOnHeight"));
		
		setLayout(new GridLayout(cellNumberOnWidth, cellNumberOnHeight));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof FullMap && arg.equals("MAP_CHARGED")){
			repaint();
		}
		
		// DOWN
		else if(arg.equals("POSY_10")){
			posY += 10;
			repaint();
		}
		// UP
		else if(arg.equals("POSY_-10")){
			posY -= 10;
			repaint();
		}
		// LEFT
		else if(arg.equals("POSX_10")){
			posX += 10;
			repaint();
		}
		// RIGHT
		else if(arg.equals("POSX_-10")){
			posX -= 10;
			repaint();
		}
		
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Graphics offgc;
		Image offscreen = null;
		
		offscreen = createImage(this.getWidth(), this.getHeight());
		
		if(offscreen != null){
			if(model != null && model.getFullMap() != null && model.getFullMap()[0][0] != null && model.getFullMap()[0][0].getCells() != null){
				offgc = offscreen.getGraphics();
				
				// reload map
				Cell[][] firstArea = model.getFullMap()[0][0].getCells();
				
				int tile_width = 104;
				int tile_height = 60;
				
				for(int i = 0 ; i < firstArea.length; i++){
					for(int j = firstArea[i].length-1; j >= 0; j--){
						if(firstArea[i][j] != null) {
							if(firstArea[i][j].getFieldType() == FieldType.EARTH){
								offgc.drawImage(earthTexture, (j * tile_width / 2) + (i * tile_width / 2) + posX, (i * tile_height / 2) - (j * tile_height/ 2) + posY, null);
							}
							else if(firstArea[i][j].getFieldType() == FieldType.SEA){
								offgc.drawImage(seaTexture, (j * tile_width / 2) + (i * tile_width / 2) + posX, (i * tile_height / 2) - (j * tile_height/ 2) + posY, null);
							}
						}
					}
				}
			}
			g2.drawImage(offscreen, 0, 0, this);
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {}

	@SuppressWarnings("deprecation")
	@Override
	public void mouseMoved(MouseEvent arg0) {
		lastXFound = arg0.getX();
		lastYFound = arg0.getY();
		
		mouseThread.resume();
	}
}