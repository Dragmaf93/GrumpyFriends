package gui.animation;

import java.util.HashMap;

import character.Character;
import element.weaponsManager.Weapon;
import javafx.scene.image.Image;

public class BlackStormtrooperAnimation implements CharacterAnimation {

	private final static String PATH_FRAME = "file:image/Character/BlackStormtrooper";
	
	private final static Image[] MOVE_FRAMES = {
			new Image(PATH_FRAME + "/Movement/frame1.png"),
			new Image(PATH_FRAME + "/Movement/frame2.png"),
			new Image(PATH_FRAME + "/Movement/frame3.png"),
			new Image(PATH_FRAME + "/Movement/frame4.png"),
			new Image(PATH_FRAME + "/Movement/frame5.png"),
			new Image(PATH_FRAME + "/Movement/frame6.png"),
			new Image(PATH_FRAME + "/Movement/frame7.png"),
			new Image(PATH_FRAME + "/Movement/frame8.png")
			};

	private final static Image[] IDLE_FRAMES = {
			new Image(PATH_FRAME + "/Idle/frame1.png"),
			new Image(PATH_FRAME + "/Idle/frame2.png"),
			new Image(PATH_FRAME + "/Idle/frame3.png"),
			};

	private final static Image[] START_JUMP_FRAMES = {
			new Image(PATH_FRAME + "/Jump/frame1.png"),
			new Image(PATH_FRAME + "/Jump/frame2.png"),
			new Image(PATH_FRAME + "/Jump/frame3.png"),
			new Image(PATH_FRAME + "/Jump/frame4.png"),
			new Image(PATH_FRAME + "/Jump/frame5.png")
			};

	private final static Image[] END_JUMP_FRAMES = {
			new Image(PATH_FRAME + "/Jump/frame9.png"),
			new Image(PATH_FRAME + "/Jump/frame10.png"),
			new Image(PATH_FRAME + "/Jump/frame11.png"),
			new Image(PATH_FRAME + "/Jump/frame12.png")
			};

	private final static Image[] JUMP2_FRAMES = {
			new Image(PATH_FRAME + "/Jump/frame6.png"),
			new Image(PATH_FRAME + "/Jump/frame7.png"),
			new Image(PATH_FRAME + "/Jump/frame8.png") 
	};

	
	private final static HashMap<String, Image> WEAPON_LAUNCHER_CONTAINER = new HashMap<String, Image>();
	private final static HashMap<String, Image> CHARACTER_BODY_WITH_WEAPON = new HashMap<String, Image>();
	
	private SpriteAnimation moveAnimation;
	private SpriteAnimation idleAnimation;
	private SpriteAnimation startJumpAnimation;
	private SpriteAnimation endJumpAnimation;
	private Character character;

	private StatusOfAnimation currentStatus;

	public BlackStormtrooperAnimation() {
		moveAnimation = new SpriteAnimation(MOVE_FRAMES, 150);
		idleAnimation = new SpriteAnimation(IDLE_FRAMES, 150);
		startJumpAnimation = new SpriteAnimation(START_JUMP_FRAMES, 100);
		endJumpAnimation = new SpriteAnimation(END_JUMP_FRAMES, 100);
	}

	@Override
	public Image getCharacterMoveAnimation() {

		if (currentStatus != StatusOfAnimation.MOVEMENT) {
			currentStatus = StatusOfAnimation.MOVEMENT;
			moveAnimation.restartAnimation();
		}
		return moveAnimation.nextFrame();
	}

	@Override
	public Image getCharacterIdleAnimation() {
		if (currentStatus != StatusOfAnimation.IDLE) {
			currentStatus = StatusOfAnimation.IDLE;
			idleAnimation.restartAnimation();
		}
		return idleAnimation.nextFrame();
	}

	@Override
	public Image getCharacterJumpAnimation() {
		return JUMP2_FRAMES[0];
		//
		// if(currentStatus!=StatusOfAnimation.JUMP){
		// currentStatus=StatusOfAnimation.JUMP;
		// startJumpAnimation.restartAnimation();
		// return startJumpAnimation.nextFrame();
		// }
		// else {
		// if(startJumpAnimation.isFinished()){
		// }else
		// return startJumpAnimation.netFrame();
		//
		// }
	}

	@Override
	public double getValueX() {
		return -60;
	}

	@Override
	public double getValueY() {
		return -35;
	}

	@Override
	public Image getCharacterFallAnimation() {
		return JUMP2_FRAMES[2];
	}

	@Override
	public Image getCharacterWeaponLauncher(Weapon weapon) {
		
		if(!WEAPON_LAUNCHER_CONTAINER.containsKey(weapon.getName())){
			String path = PATH_FRAME+"/Weapons/Launcher/"+weapon.getName()+".png";
			Image image = new Image(path);
			WEAPON_LAUNCHER_CONTAINER.put(weapon.getName(),image);
		}
		return WEAPON_LAUNCHER_CONTAINER.get(weapon.getName());
	}

	@Override
	public Image getCharacterBodyWithWeapon(Weapon weapon) {

		if(!CHARACTER_BODY_WITH_WEAPON.containsKey(weapon.getName())){
			String path = PATH_FRAME+"/Weapons/Body/"+weapon.getName()+".png";
			Image image = new Image(path);
			CHARACTER_BODY_WITH_WEAPON.put(weapon.getName(),image);
		}
		return CHARACTER_BODY_WITH_WEAPON.get(weapon.getName());
	}
	
	@Override
	public double getHeight() {
		return 170;
	}
	@Override
	public double getWidth() {
		return 170;
	}
}
