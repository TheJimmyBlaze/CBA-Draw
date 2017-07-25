import java.awt.*;
import java.io.*;
import java.util.ArrayList;

class File{

	private ArrayList<ColorByte> colorByteArray = new ArrayList<ColorByte>();
	private String name = null;
	private Draw draw;
	private Point topLeft = null;
	private Point bottomRight = null;

	private Rectangle selection = null;

	public ArrayList<ColorByte> getColorByteArray(){return colorByteArray;}
	public void setSelection(Rectangle rec){selection = rec;}

	public void addColorByte(ColorByte colorByte){
		removeColorByte(colorByte.getPoint());
		colorByteArray.add(colorByte);
		setCornerPoints();
	}

	public void removeColorByte(Point p){
		for(int i = 0; i < colorByteArray.size(); i++){
			if(colorByteArray.get(i).getPoint().x == p.x &&
				colorByteArray.get(i).getPoint().y == p.y){
				colorByteArray.remove(i);
				if(colorByteArray.size() > 0){
					setCornerPoints();
				}
				else{
					topLeft = null;
					bottomRight = null;
				}
			}
		}
	}

	public boolean isEmpty(){
		if(colorByteArray.size() == 0){
			return true;
		}
		return false;
	}

	public File(Draw d){draw = d;}

	private void setCornerPoints(){
		int[] returnedPoints = findCorners();
		topLeft = new Point(returnedPoints[0], returnedPoints[1]);
		bottomRight = new Point(returnedPoints[2], returnedPoints[3]);
	}

	private int[] findCorners(){
		int top = colorByteArray.get(0).getPoint().y;
		int left = colorByteArray.get(0).getPoint().x;
		int bottom = colorByteArray.get(0).getPoint().y + 4;
		int right = colorByteArray.get(0).getPoint().x + 4;
		for(int i = 0; i < colorByteArray.size(); i++){
			Point currentPoint = colorByteArray.get(i).getPoint();
			if(currentPoint.x < left){
				left = currentPoint.x;
			}
			if(currentPoint.y < top){
				top = currentPoint.y;
			}
			if(currentPoint.x+4 > right){
				right = currentPoint.x + 4;
			}
			if(currentPoint.y+4 > bottom){
				bottom = currentPoint.y + 4;
			}
		}
		return new int[]{left, top, right, bottom};
	}

	public void saveFile(Frame frame){
		FileDialog saveDialog = new FileDialog(frame, "Save ColorByteArrayImage", FileDialog.SAVE);
		saveDialog.setFile("*.cba");
		saveDialog.setVisible(true);
		name = saveDialog.getFile();
		if(name != null){
			try{
				if(!name.contains(".cba")){
					name = name + ".cba";
				}
				PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(saveDialog.getDirectory() + name)));

				if(selection == null){
					writer.println(topLeft.x + "," + topLeft.y + "," +bottomRight.x + "," + bottomRight.y);	
				}
				else{
					writer.println(Integer.toString(0) + "," + 0 + "," + (bottomRight.x - topLeft.x) + "," + (bottomRight.y - topLeft.y));
				}

				for(int i = 0; i < colorByteArray.size(); i++){
					if(selection == null || selection.contains(colorByteArray.get(i).getPoint())){
						writer.println((colorByteArray.get(i).getPoint().x - topLeft.x) + "," + (colorByteArray.get(i).getPoint().y - topLeft.y) + "," + colorByteArray.get(i).getColor());
					}
				}
				writer.close();
			}
			catch(Exception ex){
				System.out.println("Save file error: " + ex);
			}
		}
		selection = null;
	}

	public void loadFile(Frame frame){
		FileDialog loadDialog = new FileDialog(frame, "Load ColorByteArrayImage", FileDialog.LOAD);
		loadDialog.setFile("*.cba");
		loadDialog.setVisible(true);
		name = loadDialog.getFile();
		if(name != null){
			try{
				colorByteArray = new ArrayList<ColorByte>();
				BufferedReader reader = new BufferedReader(new FileReader(loadDialog.getDirectory() + name));
				String line;
				String[] split;

				line = reader.readLine();
				split = line.split(",");
				topLeft = new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
				bottomRight = new Point(Integer.parseInt(split[2]), Integer.parseInt(split[3]));								
				
				while((line = reader.readLine()) != null){
					split = line.split(",");
					colorByteArray.add(new ColorByte(new Point(Integer.parseInt(split[0]) + topLeft.x, Integer.parseInt(split[1]) + topLeft.y), Integer.parseInt(split[2]), draw, false));
				}
				reader.close();
			}
			catch(Exception ex){
				System.out.println("Load file error: " + ex);
			}
		}
		setCornerPoints();
		selection = null;
	}

	public void draw(Graphics g){
		if(topLeft != null && bottomRight != null){
			g.setColor(Color.white);
			g.fillRect(topLeft.x * draw.getZoom() + draw.getCentre().x, topLeft.y * draw.getZoom() + draw.getCentre().y, (bottomRight.x - topLeft.x) * draw.getZoom(), (bottomRight.y - topLeft.y) * draw.getZoom());
			g.setColor(Color.red);
			g.fillRect(topLeft.x * draw.getZoom() + draw.getCentre().x, topLeft.y * draw.getZoom() + draw.getCentre().y, 5 * draw.getZoom(), 1 * draw.getZoom());
			g.fillRect(topLeft.x * draw.getZoom() + draw.getCentre().x, topLeft.y * draw.getZoom() + draw.getCentre().y, 1 * draw.getZoom(), 5 * draw.getZoom());
		}
	
		for(int i = 0; i < colorByteArray.size(); i++){
			colorByteArray.get(i).draw(g, draw);
		}

		if(selection != null){
			g.setColor(draw.getUI()[1]);
			g.drawRect(selection.x * draw.getZoom() + draw.getCentre().x, selection.y * draw.getZoom() + draw.getCentre().y, selection.width * draw.getZoom(), selection.height * draw.getZoom());
		}

		if(name != null){
			g.setColor(draw.getPalette()[0]);
			g.drawString(name, draw.getFrame().getInsets().left + 2, draw.getHeight() - draw.getFrame().getInsets().bottom - 2);
		}
		else{
			g.setColor(draw.getPalette()[0]);
			g.drawString("newFile", draw.getFrame().getInsets().left + 2, draw.getHeight() - draw.getFrame().getInsets().bottom - 2);
		}
	}
}
