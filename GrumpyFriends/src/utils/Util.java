package utils;
public class Util {
	
	private final static float PHYSICS_WIDTH = 100f;
	private final static float PHYSICS_HEIGHT = 100f;
	
	public static float toPixelPosX(float posX) {
	    float x = PHYSICS_WIDTH*posX / 100.0f;
	    return x;
	}
	 
	//Convert a JavaFX pixel x coordinate to a JBox2D x coordinate
	public static float toPosX(float posX) {
	    float x =   (posX*100.0f*1.0f)/PHYSICS_WIDTH;
	    return x;
	}
	
	//Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
	public static float toPixelPosY(float posY) {
	    float y = PHYSICS_HEIGHT - (1.0f*PHYSICS_HEIGHT) * posY / 100.0f;
	    return y;
	}
	 
	//Convert a JavaFX pixel y coordinate to a JBox2D y coordinate
	public static float toPosY(float posY) {
	    float y = 100.0f - ((posY * 100*1.0f) /PHYSICS_HEIGHT) ;
	    return y;
	}
	 
	//Convert a JBox2D width to pixel width
	public static float toPixelWidth(float width) {
	    return PHYSICS_HEIGHT*width / 100.0f;
	}
	 
	//Convert a JBox2D height to pixel height
	public static float toPixelHeight(float height) {
	    return PHYSICS_HEIGHT*height/100.0f;
	}
}
