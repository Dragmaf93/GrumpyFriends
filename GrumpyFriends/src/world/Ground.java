package world;


public class Ground extends AbstractWorldComponent {
	
	
	public Ground(int x, int y) {
		super(x, y);
		height = 20;
		width = 20;
	//TODO torniamo
	}
	
	@Override
	public String toString() {
		return "GROU";
	}
	

}
