import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import javax.swing.*;
import java.util.ArrayList;

class Draw{
	private final int WIDTH = 1912;
	private final int HEIGHT = 1032;
	private Point centre = new Point(WIDTH/2, HEIGHT/2);

	private JFrame frame;
	private Rectangle bounds;

	private ColorPanel colorPanel;
	private MenuPanel menuPanel;
	private FilePanel filePanel;
	private InputHandler inputHandler;

	private Color[] UI = new Color[4];
	private Color[] palette;
	private int selectedColorIndex;

	private File currentFile;
	private int zoomModifier = 1;

	public int getZoom(){return zoomModifier;}
	public void setZoom(int i){zoomModifier = i;}

	public int getWidth(){return WIDTH;}
	public int getHeight(){return HEIGHT;}

	public MenuPanel getMenuPanel(){return menuPanel;}
	public ColorPanel getColorPanel(){return colorPanel;}
	public FilePanel getFilePanel(){return filePanel;}
	public InputHandler getInputHandler(){return inputHandler;}

	public File getFile(){return currentFile;}
	public void newFile(){currentFile = new File(this);}
	public void saveFile(){currentFile.saveFile(frame);}
	public void loadFile(){currentFile.loadFile(frame);}

	public Point getCentre(){return centre;}
	public void setCentre(Point p){centre = p;}

	public Rectangle getBounds(){return bounds;}

	public ArrayList<ColorByte> getColorByteArray(){return currentFile.getColorByteArray();}
	public void addColorByte(ColorByte c){currentFile.addColorByte(c);}
	public void removeColorByte(Point p){currentFile.removeColorByte(p);}
	public void setSelection(Rectangle rec){currentFile.setSelection(rec);}

	public void setColor(int i){selectedColorIndex = i;}
	public int getColor(){return selectedColorIndex;}

	public Color[] getUI(){return UI;}
	public Color[] getPalette(){return palette;}

	public Frame getFrame(){return frame;}
	public void setPalette(Color[] p){palette = p;}
	public void setBounds(Rectangle b){bounds = b;}
	
	public void resetCentre(){centre = new Point(WIDTH/2, HEIGHT/2);}

	public Boolean getSelecting(){return inputHandler.getSelecting();}

	public Draw(GraphicsDevice device){
		try{
			GraphicsConfiguration conf = device.getDefaultConfiguration();
			
			frame = new JFrame("DrawingMK1", conf);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(WIDTH, HEIGHT);
			frame.setIgnoreRepaint(true);
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);
			frame.setVisible(true);

			UI[0] = new Color(184,64,13);
			UI[1] = new Color(224,119,34);
			UI[2] = new Color(255,167,79);

			bounds = frame.getBounds();
			
			frame.createBufferStrategy(2);
			BufferStrategy bufferStrategy = frame.getBufferStrategy();

			inputHandler = new InputHandler(frame, this);
			
			currentFile = new File(this);
			
			colorPanel = new ColorPanel(new Point(WIDTH - 132 - frame.getInsets().right, HEIGHT - frame.getInsets().bottom - 14), this);
			menuPanel = new MenuPanel(new Point(frame.getInsets().left, frame.getInsets().top), this, WIDTH - frame.getInsets().left - frame.getInsets().right);
			filePanel = new FilePanel(new Point(frame.getInsets().left, frame.getInsets().top + 16), this);

			float lastTime = System.currentTimeMillis();
			
			UpdateThread updateThread = new UpdateThread(this);
			updateThread.start();

			while(true){
				try{
				Graphics g = bufferStrategy.getDrawGraphics();
				g.setColor(Color.lightGray);
				g.fillRect(0,0,WIDTH,HEIGHT);

				g.setColor(palette[0]);
				g.fillRect(centre.x - 4 * zoomModifier, centre.y, 9 * zoomModifier, 1 * zoomModifier);
				g.fillRect(centre.x, centre.y-4 * zoomModifier, 1 * zoomModifier, 9 * zoomModifier);

				g.setColor(Color.black);
				currentFile.draw(g);

				colorPanel.draw(g, this);
				menuPanel.draw(g, this);
				filePanel.draw(g, this);

				bufferStrategy.show();
				g.dispose();
				Thread.sleep(1000/32);
				}
				catch(Exception ex){
					System.out.println("Error caught during drawing. Operations have remained!");
				}
			}
		}
		catch(Exception ex){
			System.out.println("Error During Framing: " + ex);
		}
	}

	public static void main(String[] args){
		try{
			GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice device = environment.getDefaultScreenDevice();
		
			Draw display = new Draw(device);
		}
		catch(Exception ex){
			System.out.println("Error During Boot: " + ex);
		}
		System.exit(0);
	}

	public Point getMouseLocation(){
		Point real = MouseInfo.getPointerInfo().getLocation();
		Point windowCorner = new Point((int)bounds.getX(), (int)bounds.getY());

		return new Point((int)(real.getX() - windowCorner.getX()), (int)(real.getY() - windowCorner.getY()));
	}
}

class UpdateThread extends Thread{
	Draw draw;
	public UpdateThread(Draw d){
		draw = d;
	}

	public void run(){
		while(true){
			draw.setBounds(draw.getFrame().getBounds());
			draw.getColorPanel().update(draw);
			draw.getInputHandler().update();
			try{
				Thread.sleep(1);
			}
			catch(Exception ex){
				System.out.println("Error during update thread: " + ex);
			}
		}
	}
}
