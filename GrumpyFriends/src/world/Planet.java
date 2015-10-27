package world;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class Planet extends AbstractWorld {
	
	public final static Vec2 GRAVITY = new Vec2(0,-20f);
	
	public Planet() {
		super(GRAVITY, true);
		
	}
}
