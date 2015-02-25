package world;

import object.Element;
import object.Position;

public class AbstractWorldComponent implements Element
{
	private Position position;
	private int height;
	private int width;
	
	
	public AbstractWorldComponent(int x, int y, int height, int width) {
		
		super();
		position = new Position(x,y);
		this.height = height;
		this.width = width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int getX() {
		return position.getX();
	}

	@Override
	public int getY() {
		return position.getY();
	}

	public void setX(int x) {
		position.setX(x);
	}

	public void setY(int y) {
		position.setY(y);
	}

	@Override
	public Position getPosition() {
		return position;
	}

}
