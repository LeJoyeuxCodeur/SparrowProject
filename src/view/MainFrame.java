package view;

import static model.log.ProjectLogger.LOGGER;

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

import model.configuration.GameProperties;
import model.field.Area;
import model.field.Cell;
import model.field.FieldType;
import model.field.FullMap;
import model.thread.MouseRunnable;
import view.listener.FrameMouseListener;

public class MainFrame extends JFrame implements Observer{
	private static final long serialVersionUID = 1L;
	private FullMap model;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private Image seaTexture, earthTexture;
	private int posX = 0, posY = 0;
	public static int lastXFound= -1, lastYFound= -1;
	private int mouseDetectionOffset = 120;
	private MouseRunnable mouseRunnable = new MouseRunnable(dim, mouseDetectionOffset);
	private Thread mouseThread = new Thread(mouseRunnable);
	private int BUFFERED_IMAGE_WIDTH;
	private int BUFFERED_IMAGE_HEIGHT;
	private BufferedImage offScreen;
	
	public MainFrame(FullMap model){
		this.model = model;
		this.model.addObserver(this);
		mouseRunnable.addObserver(this);
		
		int widthTexture = Integer.parseInt(GameProperties.PROPERTIES.getProperty("widthTexture"));
		int heightTexture = Integer.parseInt(GameProperties.PROPERTIES.getProperty("heightTexture"));
		
		try{
			earthTexture = ImageIO.read(new File("data/textures/EARTH.png")).getScaledInstance(widthTexture, heightTexture, Image.SCALE_DEFAULT);
			seaTexture = ImageIO.read(new File("data/textures/SEA.png")).getScaledInstance(widthTexture, heightTexture, Image.SCALE_DEFAULT);
		} catch (IOException e) {
			LOGGER.error("Error while loading textures");
		}
		
		
		initFrame();
		initThreads();
		addListeners();

		LOGGER.info("View initialized");
	}

	private void initThreads() {
		mouseThread.start();
	}

	private void addListeners() {
		FrameMouseListener frameMouseListener = new FrameMouseListener(mouseThread);
		
		addMouseMotionListener(frameMouseListener);
		addMouseListener(frameMouseListener);
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
		
		int offset = 50;
		
		BUFFERED_IMAGE_WIDTH = cellNumberOnWidth * fieldWidth * widthTexture + offset;
		BUFFERED_IMAGE_HEIGHT = cellNumberOnHeight * fieldHeight * heightTexture + offset;
		
		offScreen = new BufferedImage(BUFFERED_IMAGE_WIDTH, BUFFERED_IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);		
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof FullMap && arg.equals("MAP_CHARGED")){
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
		
		else if(arg.equals("REPAINT"))
			repaint();
		
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		if(offScreen != null)
			g2.drawImage(offScreen.getSubimage(posX, posY, (int)dim.getWidth(), (int)dim.getHeight()), 0, 0, null);
	}

	
	public void display(){
		Area[][] map = model.getFullMap();
		
		int tile_width = Integer.parseInt(GameProperties.PROPERTIES.getProperty("widthTexture"));
		int tile_height = Integer.parseInt(GameProperties.PROPERTIES.getProperty("heightTexture"));
		
		int offset_X = 0;
		int offset_Y = (map[0].length * map[0][0].getCells()[0].length) * tile_height/2;
		int areaOffset = Integer.parseInt(GameProperties.PROPERTIES.getProperty("cellNumberOnWidth"))/2;
		
		displayMap(map, tile_width, tile_height, areaOffset, offset_X, offset_Y);
	}

	private void displayMap(Area[][] map, int tile_width, int tile_height, int areaOffset, int offset_X, int offset_Y) {
		Graphics offgc = offScreen.getGraphics();

		int mid_tile_witdh = tile_width/2;
		int mid_tile_height = tile_height/2;
		
		int tile_width_mult_area_offset = tile_width * areaOffset;
		int tile_height_mult_area_offset = tile_height * areaOffset;
		
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

						if(area[k][l].getFieldType() == FieldType.EARTH){
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
}