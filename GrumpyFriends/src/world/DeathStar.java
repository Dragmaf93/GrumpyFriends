package world;

import org.jbox2d.common.Vec2;

public class DeathStar  extends AbstractWorld {
	
	public final static Vec2 GRAVITY = new Vec2(0,-30f);
	
	public DeathStar() {
		super(GRAVITY, true);
	}

	@Override
	public String getType() {
		return "DeathStar";
	}
}
