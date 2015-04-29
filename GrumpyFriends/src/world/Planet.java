package world;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class Planet extends AbstractWorld {
	
	public final static Vec2 GRAVITY = new Vec2(0,-5f);
	
	public Planet() {
		super(GRAVITY, true);
		
	}
	
	public void esempio(){
		int x = 0; 
	
		Ground[] g = new Ground[10];
		for(int i =0;i<10;i++){
			x+=10;
			g[i]=new Ground(x, 100);
		}
		
		
	}
	public static void main(String[] args) {
		AbstractWorld.initializes("world.Planet");
		World world = AbstractWorld.getInstance();
		((Planet) world).esempio();
		
	}
}
