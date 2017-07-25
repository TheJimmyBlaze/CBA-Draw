import java.awt.*;

class MenuPanel{
	private Rectangle panelSize;
	private Rectangle menuSize;
	private Point point;

	private Draw draw;

	public MenuPanel(Point p, Draw d, int w){
		point = p;
		draw = d;
		panelSize = new Rectangle(p.x, p.y, w, 16);
		menuSize = new Rectangle(p.x + 2, p.y + 2, 128, 12);
	}

	public boolean mouseAction(Point p){
		if(menuSize.contains(p)){
			draw.getFilePanel().toggleVisible();
			return true;
		}
		if(panelSize.contains(p)){
			return true;
		}
		return false;
	}

	public void draw(Graphics g, Draw draw){
		g.setColor(draw.getUI()[1]);
		g.fillRect(point.x, point.y, panelSize.width, panelSize.height);
		g.setColor(draw.getUI()[0]);
		g.fillRect(point.x, point.y + 1, 1, panelSize.height -1);
		g.fillRect(point.x, point.y + panelSize.height -1, panelSize.width -1, 1);
		g.fillRect(point.x + 1, point.y + 1, menuSize.width, 1);
		g.fillRect(point.x + 2 + menuSize.width, point.y + 2, 1, menuSize.height);
		g.setColor(draw.getUI()[2]);
		g.fillRect(point.x +1, point.y, panelSize.width -1, 1);
		g.fillRect(point.x + panelSize.width -1, point.y, 1, panelSize.height -1);
		g.fillRect(point.x +1, point.y + 2, 1, menuSize.height);
		g.fillRect(point.x +2, point.y + 2 +menuSize.height, menuSize.width, 1);

		g.setColor(Color.white);
		g.drawString("FILE", point.x + 4, point.y + 12);
	}
}
