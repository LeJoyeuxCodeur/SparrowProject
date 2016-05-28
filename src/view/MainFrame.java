package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.log4j.Logger;

import model.ModelObjects;
import model.configuration.GameProperties;
import model.field.Area;
import model.field.Cell;
import model.field.FieldType;
import model.log.ProjectLogger;
import model.thread.MouseMotionRunnable;
import model.thread.MouseRunnable;
import view.listener.FrameMouseListener;

public class MainFrame extends JFrame implements Observer{
	private static final long serialVersionUID = 1L;
	private ModelObjects modelObjects;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private Image seaTexture, earthTexture, boat;
	private Image sub, image;
	private Graphics imageGraphics;
	private Graphics2D g2;
	private Cell destination;
	
	/**
	 * Pos x in the map (begin point of the screen)
	 */
	private int posX;
			
	/**
	 * Pos y in the map (begin point of the screen)
	 */
	private int posY;
	
	/**
	 * Last x found for the mouse detection
	 */
	public static int lastXFound;
	
	/**
	 * Last x found for the mouse detection
	 */
	public static int lastYFound;
	private int mouseDetectionOffset = 120;
	private MouseRunnable mouseRunnable = new MouseRunnable();
	private MouseMotionRunnable mouseMotionRunnable = new MouseMotionRunnable(dim, mouseDetectionOffset);
	private Thread mouseThread = new Thread(mouseRunnable);
	private Thread mouseMotionThread = new Thread(mouseMotionRunnable);
	private int BUFFERED_IMAGE_WIDTH;
	private int BUFFERED_IMAGE_HEIGHT;
	private BufferedImage offScreen;
	private Logger logger = new ProjectLogger(this.getClass()).getLogger();
	/**
	 * Position in x when we click
	 */
	private int boatXCliqued = 0;
	/**
	 * Position in y when we click
	 */
	private int boatYCliqued = 1470;
	public static boolean tileCliqued = false;
	
	public MainFrame(ModelObjects modelObjects){
		this.modelObjects = modelObjects;
		this.modelObjects.addObserver(this);
		this.modelObjects.getFullMap().addObserver(modelObjects);
		this.modelObjects.getBoat().addObserver(modelObjects);
		
		mouseRunnable.addObserver(this);
		mouseMotionRunnable.addObserver(this);
		
		try{
			earthTexture = ImageIO.read(new File("data/textures/ILE.png"));
			seaTexture = ImageIO.read(new File("data/textures/SEA.png"));
			boat = ImageIO.read(new File("data/textures/boat.png"));
		} catch (IOException e) {
			logger.error("Error while loading textures");
		}
		
		
		initFrame();
		initThreads();
		addListeners();

		logger.info("View initialized");
	}

	private void initThreads() {
		mouseThread.start();
		mouseMotionThread.start();
	}

	private void addListeners() {
		FrameMouseListener frameMouseListener = new FrameMouseListener(this);
		
		addMouseMotionListener(frameMouseListener);
		addMouseListener(frameMouseListener);
		
		logger.info("Frame listeners initialized");
	}

	private void initFrame() {
		Integer cellNumberOnWidth = Integer.parseInt(GameProperties.PROPERTIES.getProperty("cellNumberOnWidth"));
		Integer cellNumberOnHeight = Integer.parseInt(GameProperties.PROPERTIES.getProperty("cellNumberOnHeight"));
		
		setSize(dim.width, dim.height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new GridLayout(cellNumberOnWidth, cellNumberOnHeight));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setVisible(true);
		
		initOffscreen(cellNumberOnWidth, cellNumberOnHeight);
	}

	private void initOffscreen(int cellNumberOnWidth, int cellNumberOnHeight) {
		Integer fieldWidth = Integer.parseInt(GameProperties.PROPERTIES.getProperty("fieldWidth"));
		Integer fieldHeight = Integer.parseInt(GameProperties.PROPERTIES.getProperty("fieldHeight"));
		
		Integer widthTexture = Integer.parseInt(GameProperties.PROPERTIES.getProperty("widthTexture"));
		Integer heightTexture = Integer.parseInt(GameProperties.PROPERTIES.getProperty("heightTexture"));
		
		BUFFERED_IMAGE_WIDTH = cellNumberOnWidth * fieldWidth * widthTexture;
		BUFFERED_IMAGE_HEIGHT = cellNumberOnHeight * fieldHeight * heightTexture;
		
		offScreen = new BufferedImage(BUFFERED_IMAGE_WIDTH, BUFFERED_IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);	
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg.equals("MAP_CHARGED")){
			display();
		}
		
		// DOWN
		else if(arg.equals("POSY_10")){
			if(posY < BUFFERED_IMAGE_HEIGHT - dim.getHeight())
				posY += 10;
			else
				posY = (int)(BUFFERED_IMAGE_HEIGHT - dim.getHeight());
		}
		// UP
		else if(arg.equals("POSY_-10")){
			if(posY >= 10)
				posY -= 10;
			else
				posY = 0;
		}
		// LEFT
		else if(arg.equals("POSX_10")){
			if(posX < BUFFERED_IMAGE_WIDTH - dim.getWidth())
				posX += 10;
			else
				posX = (int)(BUFFERED_IMAGE_WIDTH - dim.getWidth());
		}
		// RIGHT
		else if(arg.equals("POSX_-10")){
			if(posX >= 10)
				posX -= 10;
			else
				posX = 0;
		}
		
		else if(arg.equals("TILE_CLIQUED"))
			destination = getBoatCoordinates();
		
		else if(arg.equals("REPAINT"))
			repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		g2 = (Graphics2D) g;
		
		if(offScreen != null){
			sub = offScreen.getSubimage(posX, posY, (int)dim.getWidth(), (int)dim.getHeight());
			image = new BufferedImage((int)dim.getWidth(),(int)dim.getHeight(), BufferedImage.TYPE_INT_RGB);
			
			imageGraphics = image.getGraphics();
			imageGraphics.drawImage(sub, 0, 0, null);
			
			
			if(tileCliqued){
				if(posX <= boatXCliqued && boatXCliqued <= posX + dim.getWidth() && posY <= boatYCliqued && boatYCliqued <= posY + dim.getHeight()){
					System.out.println("cliqued : " + boatXCliqued + ";" + boatYCliqued);
					System.out.println("destination : " + destination.getPixelX() + ";" + destination.getPixelY());
					
					imageGraphics.drawImage(boat, destination.getPixelX()-posX-boat.getWidth(null)/2, destination.getPixelY()-posY-boat.getHeight(null), null);
				}
			}
			g2.drawImage(image, 0, 0, null);
		}
	}
	
	public Cell getBoatCoordinates(){
		Area area = modelObjects.getFullMap().getAreaMatchingClick(boatXCliqued, boatYCliqued, posX, posY);
		return area.getCellMatchingClick(boatXCliqued, boatYCliqued, posX, posY);
	}
	
	public void display(){
		Area[][] map = modelObjects.getFullMap().getAreas();
		
		int tile_width = Integer.parseInt(GameProperties.PROPERTIES.getProperty("widthTexture"));
		int tile_height = Integer.parseInt(GameProperties.PROPERTIES.getProperty("heightTexture"));
		
		int offset_X = 0;
		int offset_Y = ((map[0].length * map[0][0].getCells()[0].length) * tile_height/2) - tile_height/2;
		int areaOffset = Integer.parseInt(GameProperties.PROPERTIES.getProperty("cellNumberOnWidth"))/2;
		
		displayMap(map, tile_width, tile_height, areaOffset, offset_X, offset_Y);
	}

	private void displayMap(Area[][] map, int tile_width, int tile_height, int areaOffset, int offset_X, int offset_Y) {
		Graphics offgc = offScreen.getGraphics();

		int mid_tile_witdh = tile_width/2;
		int mid_tile_height = tile_height/2;
		
		int tile_width_mult_area_offset = tile_width * areaOffset;
		int tile_height_mult_area_offset = tile_height * areaOffset;
		
		Integer widthTexture = Integer.parseInt(GameProperties.PROPERTIES.getProperty("widthTexture"));
		Integer heightTexture = Integer.parseInt(GameProperties.PROPERTIES.getProperty("heightTexture"));
		
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[0].length; j++){
				Cell[][] area = map[i][j].getCells();
				
				for(int k = 0 ; k < area.length; k++){
					for(int l = area[k].length-1; l >= 0; l--){
						int x = (l * mid_tile_witdh) + (k * mid_tile_witdh) 
								+ (i * tile_width_mult_area_offset) + (j * tile_width_mult_area_offset) 
								+ offset_X;
						
						int y = (k * mid_tile_height) - (l * mid_tile_height) 
								+ (i * tile_height_mult_area_offset) - (j * tile_height_mult_area_offset) 
								+ offset_Y;

						setPixelsPositions(area[k][l], x, y, widthTexture, heightTexture);
						
						if(area[k][l].getFieldType() == FieldType.ILE){
							offgc.drawImage(earthTexture, x, y, null);
						}
						else if(area[k][l].getFieldType() == FieldType.SEA){
							offgc.drawImage(seaTexture, x, y, null);
						}
					}
				}				
			}
		}		
	}

	private void setPixelsPositions(Cell cell, int x, int y, int widthTexture, int heightTexture) {
		cell.setPixelX(x + widthTexture/2);
		cell.setPixelY(y + heightTexture/2);
	}

	public int getMouseDetectionOffset() {
		return mouseDetectionOffset;
	}
	public Dimension getDimension() {
		return dim;
	}

	public MouseRunnable getMouseRunnable() {
		return mouseRunnable;
	}

	public void setMouseRunnable(MouseRunnable mouseRunnable) {
		this.mouseRunnable = mouseRunnable;
	}

	public Thread getMouseThread() {
		return mouseThread;
	}

	public void setMouseThread(Thread mouseThread) {
		this.mouseThread = mouseThread;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getBoatXCliqued() {
		return boatXCliqued;
	}

	public void setBoatXCliqued(int boatXCliqued) {
		this.boatXCliqued = boatXCliqued;
	}

	public int getBoatYCliqued() {
		return boatYCliqued;
	}

	public void setBoatYCliqued(int boatYCliqued) {
		this.boatYCliqued = boatYCliqued;
	}

	public Thread getMouseMotionThread() {
		return mouseMotionThread;
	}
}