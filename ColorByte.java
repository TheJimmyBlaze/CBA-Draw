import java.awt.*;

class ColorByte{
	private Point point;
	private final int WIDTH = 4;
	private final int HEIGHT = 4;
	private int colorIndex;
	private Color color;

	public Point getPoint(){return point;}
	public void setPoint(Point p){point = p;}
	public int getColor(){return colorIndex;}

	public Boolean isAt(Point p){
		if(p.x*4/4 == point.x && p.y*4/4 == point.y){
			return true;
		}
		return false;
	}

	public ColorByte(Point pointPass, int colorPass, Draw draw, Boolean f){
		int xDelta = 0;
		if(pointPass.x < 0 && f){
			xDelta = 4;
		}
		int yDelta = 0;
		if(pointPass.y < 0 && f){
			yDelta = 4;
		}
		point = new Point(pointPass.x/4*4 - xDelta, pointPass.y/4*4 - yDelta);
		colorIndex = colorPass;
		color = getNoise(draw.getPalette()[colorIndex]);		
	}

	private Color getNoise(Color c){
		int R = c.getRed();
		int G = c.getGreen();
		int B = c.getBlue();

		R += (int)(Math.random()*8-4);
		G += (int)(Math.random()*8-4);
		B += (int)(Math.random()*8-4);

		return verifyColor(R,G,B);
	}

	private Color verifyColor(int R, int G, int B){
		if(R > 255){R=255;}
		if(R < 0){R=0;}
		if(G > 255){G=255;}
		if(G < 0){G=0;}
		if(B > 255){B=255;}
		if(B < 0){B=0;}

		return new Color(R,G,B);
	}

	private Color scanLine(Color c, int v){
		int volume = v;

		int R = c.getRed();
		int G = c.getGreen();
		int B = c.getBlue();

		R -= volume; G -= volume; B -= volume;

		return verifyColor(R,G,B);
	}
	
	public void draw(Graphics g, Draw draw){
		g.setColor(color);
		g.fillRect(point.x * draw.getZoom() + draw.getCentre().x, point.y * draw.getZoom() + draw.getCentre().y, WIDTH * draw.getZoom() , HEIGHT * draw.getZoom() );
		g.setColor(scanLine(color, -4));
		g.fillRect((point.x + WIDTH -1) * draw.getZoom() + draw.getCentre().x, point.y * draw.getZoom()  + draw.getCentre().y, 1 * draw.getZoom() , HEIGHT * draw.getZoom() );
		g.setColor(scanLine(color, 12));
		g.fillRect(point.x * draw.getZoom() + draw.getCentre().x, (point.y + HEIGHT -1) * draw.getZoom()  + draw.getCentre().y, WIDTH * draw.getZoom() , 1 * draw.getZoom() );
	}
}
