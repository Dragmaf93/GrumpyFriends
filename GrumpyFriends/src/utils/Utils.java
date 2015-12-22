package utils;
public class Utils {
	
	private static float PHYSICS_WIDTH ;
	private static float PHYSICS_HEIGHT;
	private static float METER_TO_PIXES = 20f;

	public static void setPhysicsSize(double width, double height){
		PHYSICS_WIDTH=(float) (width);
		PHYSICS_HEIGHT=(float) (height);
	}
	
	public static double xFromJbox2dToJavaFx(float posX) {
	    float x = posX*javaFxWidth()/PHYSICS_WIDTH;
	    return x;
	}
	 
	//Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
	public static double yFromJbox2dToJavaFx(float posY) {
//		System.out.println("HEIGHT: "+javaFxHeight());
//		System.out.println("SOTT: "+posY*javaFxHeight()/PHYSICS_HEIGHT+" "+posY);
//		System.out.println("Y BOX TO JAVA "+(javaFxHeight()-(posY*javaFxHeight()/PHYSICS_HEIGHT)));
//	    System.out.println("--------------------");
		float y = javaFxHeight()-(posY*javaFxHeight()/PHYSICS_HEIGHT);
	    return y;
	}
	 
	 public static float xFromJavaFxToJbox2d(double x){
		 return (float) (x*PHYSICS_WIDTH/javaFxWidth());
	 }

	 public static float getJboxHeight(){
		 return PHYSICS_HEIGHT;
	 }

	 public static float getJboxWidth(){
		 return PHYSICS_WIDTH;
	 }
	 public static float yFromJavaFxToJbox2d(double y){
		 return (float) (PHYSICS_HEIGHT*(javaFxHeight()-y)/javaFxHeight());
	 }
	//Convert a JBox2D width to pixel width
	public static double widthFromJbox2dToJavaFx(float width) {
	    return METER_TO_PIXES*width;
	}
	 
	//Convert a JBox2D height to pixel height
	public static double heightFromJbox2dToJavaFx(float height) {
	    return METER_TO_PIXES*height;
	}
	
	public static float sizeToJbox(double s){
		return (float) (s/METER_TO_PIXES);
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
		fx.y=(float) yFromJbox2dToJavaFx(jbox2d.y);
		return fx;
	}
}
