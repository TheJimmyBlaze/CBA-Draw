import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

class ColorPanel{
	private Rectangle panelSize;
	private Rectangle paletteSize;
	private Point point;

	private int size;

	private boolean moving = false;
	private Point handle;

	private Color[] palette;
	private Rectangle[] palettePlacement;
	private Point palettePoint;
	
	public ColorPanel(Point pointPass, Draw draw){
		point = pointPass;
		BuildPalette();

		locateComponants();
		draw.setPalette(palette);
	}

	private void BuildPalette(){
		try{
			/*BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/palette.pal")));

			String fileType = reader.readLine();
			version = Integer.parseInt(reader.readLine());
			size = Integer.parseInt(reader.readLine());	
			
			palette = new Color[size];
			palettePlacement = new Rectangle[size];
			point.y -= size*2;

			for(int i = 0; i < size; i++){
				String color = reader.readLine();
				String[] RGB = color.split(" ");

				palette[i] = new Color(Integer.parseInt(RGB[0]), Integer.parseInt(RGB[1]), Integer.parseInt(RGB[2]));
			}
			*/

			BufferedImage paletteImage = ImageIO.read(getClass().getResource("/palette.png"));
			size = paletteImage.getWidth() * paletteImage.getHeight();
			palette = new Color[size];
			palettePlacement = new Rectangle[size];
			point.y -= size*2;

			for(int i = 0; i < size; i++){
				Color color = new Color(paletteImage.getRGB(i%8, i/8), true);

				palette[i] = color;
			}
		}
		catch(Exception ex){
			System.out.println("Pallete Build IO Error: " + ex);
		}
	}

	public boolean mouseAction(Point pointPass, Draw draw, Boolean press){
		if(!press){
			moving = false;
			return true;
		}
	
		if(paletteSize.contains(pointPass)){
			for(int i = 0; i < size; i++){
				if(palettePlacement[i].contains(pointPass)){
					draw.setColor(i);
				}
			}		
			return true;
		}
		if(panelSize.contains(pointPass)){
			moving = true;	
			handle = new Point((int)(pointPass.getX() - point.getX()), (int)(pointPass.getY() - point.getY()));		
			return true;
		}
		return false;
	}

	public void update(Draw draw){
		if(moving){
			point = new Point(draw.getMouseLocation().x - handle.x, draw.getMouseLocation().y - handle.y);
			locateComponants();
		}
	}

	private void locateComponants(){
		panelSize = new Rectangle((int)point.getX(),(int)point.getY(),132,14 + size*2);
		paletteSize = new Rectangle((int)panelSize.getX() + 2, (int)panelSize.getY() + 12, 128, size*2);

		palettePoint = new Point((int)point.getX() + 2, (int)point.getY() + 12);

		for(int i = 0; i < size; i++){
			palettePlacement[i] = new Rectangle((int)palettePoint.getX() + i%8 * 16, (int)palettePoint.getY() + (int)(i/8 * 16), 16, 16);
		}
	}

	public void draw(Graphics g, Draw draw){

		g.setColor(draw.getUI()[1]);
		g.fillRect((int)point.getX(), (int)point.getY(), (int)panelSize.getWidth(), (int)panelSize.getHeight());
		g.setColor(draw.getUI()[0]);
		g.fillRect((int)point.getX(), (int)point.getY() + 1, 1, (int)panelSize.getHeight() - 1);
		g.fillRect((int)point.getX(), (int)point.getY() + (int)panelSize.getHeight() -1, (int)panelSize.getWidth() - 1, 1);
		g.fillRect((int)point.getX() + 2, (int)point.getY() + 11, 128, 1);
		g.fillRect((int)point.getX() + 2 + 128, (int)point.getY() + 12, 1, size/8*16);
		g.setColor(draw.getUI()[2]);
		g.fillRect((int)point.getX() + 1, (int)point.getY(), (int)panelSize.getWidth() - 1, 1);
		g.fillRect((int)point.getX() + (int)panelSize.getWidth() - 1, (int)point.getY(), 1, (int)panelSize.getHeight() - 1);
		
		for(int i = 0; i < size; i++){
			g.setColor(palette[i]);
			g.fillRect(palettePlacement[i].x,palettePlacement[i].y,palettePlacement[i].width,palettePlacement[i].height);
		}

		if(draw.getSelecting()){
			g.setColor(new Color(20,20,20,224));
			g.fillRect(palettePlacement[0].x, palettePlacement[0].y, 128, size/8*16);
		}
	}
}

