package element;

import java.util.List;

import utils.Point;

public interface Ground extends Element{

	public abstract List<Point> getPoint();
	public abstract void setPosition();
	public abstract void setSize();
}
