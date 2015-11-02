package gui;

import character.Character;
import game.MatchManager;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;

public class MatchScene extends Scene {
	
	private final static int MAX_ZOOM=10000;
	private final static int MIN_ZOOM=-700;
	private final static int INCR_ZOOM =50;
	
	
	private MatchManager matchManager;
	private PerspectiveCamera camera;
	private MatchPane pane;
	
	private boolean focusPlayer;
	private int zoom;
	public MatchScene(Parent parent, MatchManager matchManager, double width, double height) {
		super(parent, width, height);
		this.pane = (MatchPane) parent;
		this.matchManager = matchManager;
		this.camera = new PerspectiveCamera(true);
		zoom=MIN_ZOOM;
		camera.setTranslateZ(zoom);
		camera.setNearClip(0.1);
		camera.setFarClip(MAX_ZOOM);
		camera.setFieldOfView(35);
		setCamera(camera);
		
		setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.D) {
					matchManager.getCurrentPlayer().move(Character.RIGHT);
				}
				if (event.getCode() == KeyCode.A) {
					matchManager.getCurrentPlayer().move(Character.LEFT);
				}
				if (event.getCode() == KeyCode.SPACE) {
					matchManager.getCurrentPlayer().jump();
				}
				if (event.getCode() == KeyCode.B) {
					matchManager.getCurrentPlayer().equipWeapon("SimpleBomb");
				}
				if (event.getCode() == KeyCode.M) {
					matchManager.getCurrentPlayer().equipWeapon("SimpleMissile");
				}
				if(event.getCode() == KeyCode.L){
					matchManager.getCurrentPlayer().attack(10f);
				}
				if(event.getCode() == KeyCode.W){
					matchManager.getCurrentPlayer().changeAim(Character.INCREASE);
				}
				if(event.getCode() == KeyCode.S){
					matchManager.getCurrentPlayer().changeAim(Character.DECREASE);
				}
			}
		});

		setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.D) {
					matchManager.getCurrentPlayer().stopToMove();
				}
				if (event.getCode() == KeyCode.A) {
					matchManager.getCurrentPlayer().stopToMove();
				}
				if(event.getCode() == KeyCode.W){
					matchManager.getCurrentPlayer().changeAim(Character.STOP);
				}
				if(event.getCode() == KeyCode.S){
					matchManager.getCurrentPlayer().changeAim(Character.STOP);
				}
			}
		});
		
		setOnScroll(new EventHandler<ScrollEvent>() {

			@Override
			public void handle(ScrollEvent event) {
				if(event.getDeltaY()>0 && zoom+INCR_ZOOM<=MIN_ZOOM){
					zoom+=INCR_ZOOM;
					camera.setTranslateZ(zoom);
				}
				if(event.getDeltaY()<0){
					zoom-=INCR_ZOOM;
					camera.setTranslateZ(zoom);
				}
			}
			
		});
		
	
		new AnimationTimer() {

			@Override
			public void handle(long now) {
				pane.update();
				camera.setTranslateX(matchManager.getCurrentPlayer().getX());
				camera.setTranslateY(matchManager.getCurrentPlayer().getY());
				
			}
		}.start();
		
		
	}

}
