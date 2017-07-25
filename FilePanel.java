import java.awt.*;

class FilePanel{
	private Rectangle panelSize;
	private Rectangle newSize;
	private Rectangle saveSize;
	private Rectangle loadSize;
	private Rectangle quitSize;

	private Point point;
	private Draw draw;

	private boolean visible = false;
	public void toggleVisible(){visible = !visible;}

	public FilePanel(Point p, Draw d){
		point = p;
		draw = d;

		panelSize = new Rectangle(point.x, point.y, 132, 72);
		newSize = new Rectangle(point.x +2, point.y, 128, 16);
		saveSize = new Rectangle(point.x +2, point.y + 18, 128, 16);
		loadSize = new Rectangle(point.x +2, point.y + 36, 128, 16);
		quitSize = new Rectangle(point.x +2, point.y + 54, 128, 16);
	}

	public boolean mouseAction(Point p){
		if(visible){
			if(newSize.contains(p)){
				draw.newFile();
				return true;
			}
			if(saveSize.contains(p)){
				draw.saveFile();
				return true;
			}
			if(loadSize.contains(p)){
				draw.loadFile();
				return true;
			}
			if(quitSize.contains(p)){
				System.exit(0);
				return true;
			}
			if(panelSize.contains(p)){
				return true;
			}
		}
		return false;
	}

	public void draw(Graphics g, Draw draw){
		if(visible){
			g.setColor(draw.getUI()[1]);
			g.fillRect(point.x, point.y, panelSize.width, panelSize.height);
			g.setColor(draw.getUI()[0]);
			g.fillRect(point.x, point.y, 1, panelSize.height);
			g.fillRect(point.x, point.y + panelSize.height -1, panelSize.width, 1);
			g.setColor(draw.getUI()[2]);
			g.fillRect(point.x + panelSize.width -1, point.y, 1, panelSize.height);
		
			for(int i = 0; i < 4; i++){
				g.setColor(draw.getUI()[0]);
				g.fillRect(point.x +2, point.y + 17 + i*18, 128, 1);
				g.fillRect(point.x +130, point.y +i*18, 1, 16);
				g.setColor(draw.getUI()[2]);
				g.fillRect(point.x +1, point.y + i*18, 1, 16);
			       	g.fillRect(point.x +2, point.y + 16 + i*18, 128, 1);
			}

			g.setColor(Color.white);
			g.drawString("New", point.x + 4, point.y + 12);
			g.drawString("Save", point.x + 4, point.y + 30);
			g.drawString("Load", point.x + 4, point.y + 48);
			g.drawString("Quit", point.x + 4, point.y + 66);	
		}
	}
}
