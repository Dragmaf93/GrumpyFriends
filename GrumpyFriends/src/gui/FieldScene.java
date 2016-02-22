package gui;

import java.util.ArrayList;
import java.util.List;

import game.AbstractMatchManager;
import game.MatchManager;
import javafx.animation.PathTransition;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Camera;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import utils.Point;
import utils.Vector;
import world.World;

public class FieldScene extends SubScene {

	private final static int MAX_ZOOM = -6000;
	private final static int MIN_ZOOM = -2000;
	private final static int INCR_ZOOM = 100;
	private final static double MAX_TIME = 100;

	private final static double VALUE_MOVE_CAMERA = 20.0;

	private final static double DISTANCE_FROM_BORDER = -300;

	private MatchManager matchManager;
	private Camera camera;
	private FieldPane pane;

	private double zoom;

	List<Point> movementOfCamera;

	public boolean focusPoint;
	private double time, vX, vY;
	private double incrTime;

	private Point position0;
	private Point cameraPosition;
	private Point nextPosition;

	private int indexNextPoint;
	private boolean cameraMoving;
	private boolean movementNextPlayer;

	private boolean focusPlayer;

	public FieldScene(Parent parent, final MatchManager matchManager2,
			double width, double height) {
		super(parent, width, height);
		this.pane = (FieldPane) parent;
		this.matchManager = matchManager2;
		this.camera = new PerspectiveCamera(true);

		movementOfCamera = new ArrayList<Point>();
		position0 = new Point();
		cameraPosition = new Point();
		nextPosition = new Point();

		zoom = -3000;
		camera.setTranslateZ(zoom);
		camera.setNearClip(0);
		camera.setFarClip(-10);
		((PerspectiveCamera) camera).setFieldOfView(35);
		setCamera(camera);
		focusPlayer = true;
	}

	public Point getCameraPosition() {
		return cameraPosition;
	}

	public void moveCamera(double x, double y) {

	}

	public void update() {
		pane.update();

		if (cameraMoving) {
			moveCameraToNextPoint();
		} else {
			if (focusPlayer)
				if(matchManager.getCurrentPlayer().getX()>-World.DISTANCE_WORLDS_BORDER && matchManager.getCurrentPlayer().getX()<+World.DISTANCE_WORLDS_BORDER+ getWidth()
						&&matchManager.getCurrentPlayer().getY()+100<getHeight())
				cameraPosition.set(matchManager.getCurrentPlayer().getX(),
						matchManager.getCurrentPlayer().getY()-140);

			camera.setTranslateX(cameraPosition.x);
			camera.setTranslateY(cameraPosition.y);
		}

	}

	public void addMovemetoToCamera(double x, double y) {
		movementOfCamera.add(new Point(x, y));
	}

	public void startCamerasMovements() {
		indexNextPoint = 0;
		time = 1;
		incrTime = 1;

		position0.set(cameraPosition.x, cameraPosition.y);
		nextPosition = movementOfCamera.get(indexNextPoint);

		vX = (nextPosition.x - position0.x) / MAX_TIME;
		vY = (nextPosition.y - position0.y) / MAX_TIME;

		cameraMoving = true;
		focusPoint = false;
	}

	public boolean cameraFocusAddedPoint() {
		return focusPoint;
	}

	public void nextCameraMovement() {
		time = 1;

		if (indexNextPoint + 1 >= movementOfCamera.size()) {
			movementOfCamera.clear();
			return;
		}

		position0 = movementOfCamera.get(indexNextPoint);
		indexNextPoint++;
		nextPosition = movementOfCamera.get(indexNextPoint);

		vX = (nextPosition.x - position0.x) / MAX_TIME;
		vY = (nextPosition.y - position0.y) / MAX_TIME;

		cameraMoving = true;
		focusPoint = false;

	}

	public void focusNextPlayer() {
		if (!movementNextPlayer) {
			time = 1;
			incrTime = 0.5;
			position0.set(cameraPosition.x, cameraPosition.y);
			nextPosition.set(matchManager.nextPlayer().getX(), matchManager
					.nextPlayer().getY());

			vX = (nextPosition.x - position0.x) / MAX_TIME;
			vY = (nextPosition.y - position0.y) / MAX_TIME;

			cameraMoving = true;
			focusPoint = false;
			movementNextPlayer = true;
		}
	}

	private void moveCameraToNextPoint() {
		time += incrTime;
		camera.setTranslateX(position0.x + time * vX);
		camera.setTranslateY((position0.y + time * vY )-140);
		// System.out.println(position0);
		// System.out.println(nextPosition);
		// System.out.println(time + "  "+ (position0.x + time * vX)+
		// "   "+(position0.y + time * vY));
		if (time >= MAX_TIME) {
			focusPoint = true;
			cameraMoving = false;
			if (movementNextPlayer)
				matchManager.setCanStartNextTurn(true);
			movementNextPlayer = false;

		}
	}

	public boolean getMovementNextPlayer() {
		return movementNextPlayer;
	}

	public void setFocusPlayer(boolean focus) {
		focusPlayer = focus;
	}

	public double getZoom() {
		return zoom;
	}

	public void setZoom(boolean zoom) {

		if (zoom && this.zoom + INCR_ZOOM <= MIN_ZOOM)
			this.zoom += INCR_ZOOM;
		else if (!zoom && this.zoom - INCR_ZOOM >= MAX_ZOOM)
			this.zoom -= INCR_ZOOM;
	}

	public void cameraMovesUP() {
		if (cameraPosition.y - VALUE_MOVE_CAMERA >= -DISTANCE_FROM_BORDER)
			cameraPosition.set(cameraPosition.x, cameraPosition.y
					- VALUE_MOVE_CAMERA);
	}

	public void cameraMovesDOWN() {
		if (cameraPosition.y + VALUE_MOVE_CAMERA <= getHeight()+DISTANCE_FROM_BORDER)
			cameraPosition.set(cameraPosition.x, cameraPosition.y
					+ VALUE_MOVE_CAMERA);
	}

	public void cameraMovesLEFT() {
		if (cameraPosition.x - VALUE_MOVE_CAMERA >= -DISTANCE_FROM_BORDER)
				cameraPosition.set(cameraPosition.x - VALUE_MOVE_CAMERA,
						cameraPosition.y);
	}

	public void cameraMovesRIGTH() {
		if (cameraPosition.y - VALUE_MOVE_CAMERA <= getWidth() + DISTANCE_FROM_BORDER)
			cameraPosition.set(cameraPosition.x + VALUE_MOVE_CAMERA,
					cameraPosition.y);
	}

}
