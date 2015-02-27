package world;

public class Planet extends AbstractWorld {
	
	private static int gravityForce;
	
	public static int getGravityForce() {
		return gravityForce;
	}

	public static void setGravityForce(int gravityForce) {
		Planet.gravityForce = gravityForce;
	}

	private Planet(String path) {
		super(path);
	}

	@Override
	public int getGravity() {
		return gravityForce;
	}
}
