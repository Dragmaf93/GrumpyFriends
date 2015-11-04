package gui.weapon;

import org.jbox2d.common.Vec2;

import character.Character;
import element.weaponsManager.Weapon;
import element.weaponsManager.weapons.SimpleBomb;
import element.weaponsManager.weapons.SimpleMissile;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class GuiSimpleMissile implements WeaponGui{

	private final static double BAZOOKA_WIDTH=60;
	private final static double BAZOOKA_HEIGHT=20;
	
	private Group bazookaRoot;
	private Group bulletRoot;
	
	private Character character;
	private SimpleMissile bazooka;
	
	private Rotate bazookaRotate;
	private Rotate bulletRotate;
	
	private GuiExplosion explosion;
	
	private Rectangle rectangle;
//	private Polygon poly;
	private Rectangle poly;
	private boolean finishAnimation;
	
	public  GuiSimpleMissile(Weapon bomb, Character character) {
		this.bazooka = (SimpleMissile) bomb;
		this.character = character;
		bulletRoot = new Group();
		bazookaRoot = new Group();
		finishAnimation = false;
	

	}
	
	@Override
	public Node getWeaponLauncher() {
		if(rectangle==null){
			rectangle = new Rectangle(character.getX()+character.getWidth()*0.3, 
					character.getY()+character.getWidth()/2, BAZOOKA_WIDTH, BAZOOKA_HEIGHT);
			rectangle.setFill(Color.WHITE);
			rectangle.setStroke(Color.BROWN);
			bazookaRoot.getChildren().add(rectangle);
			
			bazookaRotate = new Rotate();
			bazookaRotate.setAxis(Rotate.Z_AXIS);
			rectangle.getTransforms().add(bazookaRotate);
		}
		return bazookaRoot;
	}

	@Override
	public Node getWeaponBullet() {
		explosion = new GuiExplosion(this);
		poly = new Rectangle(bazooka.getX(),bazooka.getY(),bazooka.getWidth(),bazooka.getHeight());
//		poly = new Polygon();
//		poly.getPoints().addAll(0d,0d,bazooka.getWidth()*0.7d,-bazooka.getHeight(),bazooka.getWidth(),0d,
//				bazooka.getWidth()*0.7d,bazooka.getHeight());
		poly.relocate(bazooka.getX(),bazooka.getY());
		poly.setFill(Color.GREY);
		
		bulletRotate = new Rotate();
		bulletRotate.setAxis(Rotate.Z_AXIS);
	
		poly.getTransforms().add(bulletRotate);
		bulletRoot.getChildren().add(poly);
		bulletRoot.getChildren().add(explosion.getExplosionNode());
		
		
		return bulletRoot;
	}

	@Override
	public void updateLauncher() {
		bazookaRotate.setPivotX(rectangle.getX()+rectangle.getWidth()*0.1);
		bazookaRotate.setPivotY(rectangle.getY()+rectangle.getHeight()*0.5);
		bazookaRotate.setAngle(Math.toDegrees(-character.getLauncher().getAngle()));
		rectangle.relocate(character.getX()+character.getWidth()*0.3, 
				character.getY()+character.getWidth()/2);		
	}

	@Override
	public void updateBullet() {
		bazooka.update();
		
		if (!bazooka.isExplosed()){
			bulletRotate.setPivotX(poly.getX());
			bulletRotate.setPivotY(poly.getY());
			bulletRotate.setAngle(Math.toDegrees(-bazooka.getAngle()));
			
			poly.relocate(bazooka.getX(),bazooka.getY());
			
			
		
		}else{
			explosion.playExplosionAnimation();
		}
	}


	@Override
	public boolean finishAnimation() {
		return finishAnimation;
	}

	@Override
	public void afterAttack() {
		bazooka.afterAttack();
		finishAnimation=true;
	}

	@Override
	public Weapon getWeapon() {
		return bazooka;
	}

	@Override
	public void removeBullet() {
		bulletRoot.getChildren().remove(poly);
		
	}
	
}
