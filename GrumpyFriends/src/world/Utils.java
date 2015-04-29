package world;

public abstract class Utils {
	
	public static float toPixelPosX(float posX) {
		int WIDTH = ((AbstractWorld) AbstractWorld.getInstance()).getWidth();
	    float x = WIDTH*posX / 100.0f;
	    return x;
	}
	 
	//Convert a JavaFX pixel x coordinate to a JBox2D x coordinate
	public static float toPosX(float posX) {
		int WIDTH = ((AbstractWorld) AbstractWorld.getInstance()).getWidth();
	    float x =   (posX*100.0f*1.0f)/WIDTH;
	    return x;
	}
	
	//Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
	public static float toPixelPosY(float posY) {
		int HEIGHT= ((AbstractWorld) AbstractWorld.getInstance()).getHeight();
	    float y = HEIGHT - (1.0f*HEIGHT) * posY / 100.0f;
	    return y;
	}
	 
	//Convert a JavaFX pixel y coordinate to a JBox2D y coordinate
	public static float toPosY(float posY) {
		int HEIGHT= ((AbstractWorld) AbstractWorld.getInstance()).getHeight();
	    float y = 100.0f - ((posY * 100*1.0f) /HEIGHT) ;
	    return y;
	}
	 
	//Convert a JBox2D width to pixel width
	public static float toPixelWidth(float width) {
		int WIDTH = ((AbstractWorld) AbstractWorld.getInstance()).getWidth();
	    return WIDTH*width / 100.0f;
	}
	 
	//Convert a JBox2D height to pixel height
	public static float toPixelHeight(float height) {
		int HEIGHT= ((AbstractWorld) AbstractWorld.getInstance()).getHeight();
	    return HEIGHT*height/100.0f;
	}
}
