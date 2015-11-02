package utils;
public class Utils {
	
	private final static float PHYSICS_WIDTH = 1000f;
	private final static float PHYSICS_HEIGHT = 1000f;
	private final static float METER_TO_PIXES = 20f;

	
	public static float xFromJbox2dToJavaFx(float posX) {
	    float x = posX*javaFxWidth()/PHYSICS_WIDTH;
	    return x;
	}
	 
	//Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
	public static float yFromJbox2dToJavaFx(float posY) {
	    float y = javaFxHeight()-(posY*javaFxHeight()/PHYSICS_HEIGHT);
	    return y;
	}
	 
	 
	//Convert a JBox2D width to pixel width
	public static float widthFromJbox2dToJavaFx(float width) {
	    return METER_TO_PIXES*width;
	}
	 
	//Convert a JBox2D height to pixel height
	public static float heightFromJbox2dToJavaFx(float height) {
	    return METER_TO_PIXES*height;
	}
	
	public static float javaFxHeight(){
		return PHYSICS_HEIGHT*METER_TO_PIXES;
	}
	public static float javaFxWidth(){
		return PHYSICS_WIDTH*METER_TO_PIXES;
	}
	
	
	public static Vector vectorToFx(Vector jbox2d){
		Vector fx = new Vector();
		fx.x=jbox2d.x;
		fx.y=yFromJbox2dToJavaFx(jbox2d.y);
		return fx;
	}
}
