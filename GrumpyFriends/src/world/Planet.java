package world;

import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import utils.Point;

public class Planet extends AbstractWorld {
	
	public final static Vec2 GRAVITY = new Vec2(0,-10f);
	
	public Planet() {
		super(GRAVITY, true);
	}

	@Override
	public void addRoundGround(Point start, Point end, Point control) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRoundGround(List<Point> points) {
		// TODO Auto-generated method stub
		
	}
}
