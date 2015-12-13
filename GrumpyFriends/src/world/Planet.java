package world;

import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import utils.Point;

public class Planet extends AbstractWorld {
	
	public final static Vec2 GRAVITY = new Vec2(0,-20f);
	
	public Planet() {
		super(GRAVITY, true);
	}

	@Override
	public String getType() {
		return "Planet";
	}
}
