package world;

import element.Element;
import element.Position;

public abstract class AbstractWorldComponent implements Element
{
	protected Position position;
	protected int height;
	protected int width;
	
	//
	public AbstractWorldComponent(int x, int y) {
		
		super();
		position = new Position(x,y);
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
