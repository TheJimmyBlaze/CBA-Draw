import java.awt.*;
import java.awt.event.*;

class InputHandler implements MouseListener, KeyListener{
	private Draw draw;
	private Boolean drawing = false;
	private Boolean removing = false;
	private Boolean movingCentre = false;
	private Boolean selecting = false;
	private Boolean sampling = false;

	private Boolean drawMode = true;
	private Boolean cntrl = false;

	private Point lastMouseLocation;
	private Point initSelect = new Point(0,0);

	public Boolean getSelecting(){return !drawMode;}

	public InputHandler(Frame frame, Draw drawPass){
		frame.addMouseListener(this);
		frame.addKeyListener(this);
		draw = drawPass;
	}

	public void update(){
		if(drawing){
			ColorByte colorByte = new ColorByte(new Point((draw.getMouseLocation().x - draw.getCentre().x) / draw.getZoom(), (draw.getMouseLocation().y - draw.getCentre().y) / draw.getZoom()), draw.getColor(), draw, true);
			draw.addColorByte(colorByte);
		}
		if(removing){
			int x = draw.getMouseLocation().x - draw.getCentre().x;
			int y = draw.getMouseLocation().y - draw.getCentre().y;

			int xDelta = 4;
			if(x >= 0){
				xDelta = 0;
			}
			int yDelta = 4;
			if(y >= 0){
				yDelta = 0;
			}
			draw.removeColorByte(new Point(x / draw.getZoom() /4*4 - xDelta, y / draw.getZoom() /4*4 - yDelta));
		}
		if(movingCentre){
			Point currentMouseLocation = draw.getMouseLocation();
			int xDif = currentMouseLocation.x - lastMouseLocation.x;
			int yDif = currentMouseLocation.y - lastMouseLocation.y;
		
			draw.setCentre(new Point(draw.getCentre().x + xDif, draw.getCentre().y + yDif));
			lastMouseLocation = currentMouseLocation;
		}
		if(selecting){
			int x = draw.getMouseLocation().x - draw.getCentre().x;
			int y = draw.getMouseLocation().y - draw.getCentre().y;

			int xDelta = 0;
			if(x >= 0){
				xDelta = 4;
			}
			int yDelta = 0;
			if(y >= 0){
				yDelta = 4;
			}
			if(x/draw.getZoom() /4*4 + xDelta> initSelect.x && y/draw.getZoom() /4*4  + yDelta> initSelect.y){
				draw.setSelection(new Rectangle(initSelect.x, initSelect.y, (x/draw.getZoom() /4*4 + xDelta) - initSelect.x, (y/draw.getZoom() /4*4 + yDelta) - initSelect.y));
			}
			else{
				draw.setSelection(null);
			}
		}
	}

	public void mousePressed(MouseEvent e){
		if(e.getButton() == MouseEvent.BUTTON1){
			if(!draw.getColorPanel().mouseAction(draw.getMouseLocation(), draw, true) &&
				!draw.getMenuPanel().mouseAction(draw.getMouseLocation()) &&
				!draw.getFilePanel().mouseAction(draw.getMouseLocation())){
				if(drawMode){
					drawing = true;
				}
				else{
					int x = draw.getMouseLocation().x - draw.getCentre().x;
					int y = draw.getMouseLocation().y - draw.getCentre().y;

					int xDelta = 4;
					if(x >= 0){
						xDelta = 0;
					}
					int yDelta = 4;
					if(y >= 0){
						yDelta = 0;
					}
					initSelect = new Point((x / draw.getZoom()) /4*4 - xDelta, (y / draw.getZoom())/4*4 - yDelta);
					selecting = true;			
				}
			}
		}
		if(e.getButton() == MouseEvent.BUTTON2){
			lastMouseLocation = draw.getMouseLocation();
			movingCentre = true;
		}
		if(e.getButton() == MouseEvent.BUTTON3){
			if(drawMode){			
				removing = true;}
			else{
				draw.setSelection(null);
			}
		}
	}

	public void mouseReleased(MouseEvent e){
		if(e.getButton() == MouseEvent.BUTTON1){
			draw.getColorPanel().mouseAction(draw.getMouseLocation(), draw, false);
			if(drawMode){
				drawing = false;
			}
			else{
				selecting = false;
			}
		}
		if(e.getButton() == MouseEvent.BUTTON2){
			movingCentre = false;
		}
		if(e.getButton() == MouseEvent.BUTTON3){
			removing = false;
		}
	}

	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == 61){
			if(draw.getZoom() < 8){
				draw.setZoom(draw.getZoom()*2);
			}
		}
		if(e.getKeyCode() == 45){
			if(draw.getZoom() > 1){
				draw.setZoom(draw.getZoom()/2);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			draw.resetCentre();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP){
			draw.setCentre(new Point(draw.getCentre().x, draw.getCentre().y + 32));
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			draw.setCentre(new Point(draw.getCentre().x, draw.getCentre().y - 32));
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			draw.setCentre(new Point(draw.getCentre().x + 32, draw.getCentre().y));
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			draw.setCentre(new Point(draw.getCentre().x - 32, draw.getCentre().y));
		}
		if(e.getKeyCode() == KeyEvent.VK_Z){
			drawMode = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_X){
			drawMode = false;
		}
	}
	
	public void mouseEntered(MouseEvent e){}

	public void mouseExited(MouseEvent e){}
	
	public void mouseClicked(MouseEvent e){}

	public void keyTyped(KeyEvent e){}

	public void keyReleased(KeyEvent e){}
}
