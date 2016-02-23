package gui.hud;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import game.AbstractMatchManager;
import game.MatchManager;
import game.MatchTimer;
import gui.MatchPane;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class IndicatorOfTime extends AbstractHudElement{

//	private MatchTimer timer;
	
	private final static String FONT_PATH = "font/clockFont.ttf";
	private Font fontTurnTimer;
	private Font fontMatchTimer;
	
	private final static double DISTANCE_SCREEN_LEFT=30;
	private final static double DISTANCE_SCREEN_TOP=5;
	
	private final static Color BACKGROUND_COLOR = new Color(30d / 255d, 127d / 255d, 169d / 255d, 0.9d);

	
	private Text turnTimer;
	private Text matchTimer;
	
	private Circle clock;
	
	public IndicatorOfTime(MatchPane matchPane,MatchManager matchManager) {
		super(matchPane,matchManager);
		
//		timer = matchManager.getMatchTimer();
		
		clock = new Circle(68, BACKGROUND_COLOR);
		clock.setStroke(Color.BLACK);
		clock.setStrokeWidth(3);
		turnTimer = new Text();
		matchTimer = new Text();
		
		fontTurnTimer= Font.font("Comic Sans MS", FontWeight.BOLD, 120);
		fontMatchTimer= Font.font("Comic Sans MS", FontWeight.BOLD, 30);
		
		turnTimer.setFill(Color.RED);
		turnTimer.setStroke(Color.BLACK);
		turnTimer.setFont(fontTurnTimer);

		matchTimer.setFill(Color.BLACK);
		matchTimer.setFont(fontMatchTimer);
		
		root.getChildren().add(clock);
		root.getChildren().add(turnTimer);
		root.getChildren().add(matchTimer);
		
		root.relocate(DISTANCE_SCREEN_LEFT, DISTANCE_SCREEN_TOP);
	}
	
	
	@Override
	public void draw() {
//		if(!timer.isTurnTimerStopped()){
//			turnTimer.setText(timer.turnTimerStringFormat());
//			
//		}
//		else turnTimer.setText(timer.attackTimerStringFormat());
	    turnTimer.setText(matchManager.turnTimerLeft());	
		turnTimer.relocate(clock.getCenterX()-clock.getRadius()*0.9,clock.getCenterY()-turnTimer.getFont().getSize());
		matchTimer.setText(matchManager.matchTimerLeft());
		matchTimer.relocate(clock.getCenterX()-clock.getRadius()/2,clock.getCenterY()+matchTimer.getFont().getSize()/2);
		
	}


}
