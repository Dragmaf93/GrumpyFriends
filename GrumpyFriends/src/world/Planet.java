package world;

public class Planet extends AbstractWorld {
	
	private static Vector gravityForce = new Vector(0, 20);
//	TODO impostare la GRAVITAAAA
	
	public static void setGravityForce(Vector gravityForce) {
		Planet.gravityForce = gravityForce;
	}

	private Planet(String path) {
		super(path);
	}

	@Override
	public Vector getGravity() {
		return gravityForce;
	}
}