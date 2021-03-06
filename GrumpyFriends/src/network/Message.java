package network;

public abstract class Message {

	public final static String OP_REQUEST_LIST = "RL";
	public final static String OP_REQUEST_INFO_MATCH = "RM";
	public final static String OP_REQUEST_INFO_TEAM = "RT";
	public final static String OP_REQUEST_INFO_WORLD = "RW";
	public final static String OP_SELECT_MATCH = "SM";
	public final static String OP_SEND_INFO_TEAM_TO_CHOOSER = "STCH";
	public final static String OP_CREATE_MATCH = "CM";
	
	public final static String OP_SEND_PLAYER_FOUND = "PF";
	public final static String OP_SEND_LIST = "SL";
	public final static String OP_SEND_INFO_MATCH = "SIM";
	public final static String OP_SEND_INFO_WORLD = "SW";
	public final static String OP_CONFIRM = "OK";
	public final static String OP_ERROR = "ERR";
	
	public final static String OP_MOVE_LEFT = "L";
	public final static String OP_MOVE_RIGHT = "R";
	public final static String OP_STOP_MOVE = "S";
	public final static String OP_JUMP = "J";

	public final static String OP_EQUIP_WEAPON = "E";
	public final static String OP_INCREASE_AIM = "IA";
	public final static String OP_DECREASE_AIM = "DA";
	public final static String OP_STOP_AIM = "SA";
	public final static String OP_ATTACK = "A";

	public final static String SERVER_READY = "SR";
	public final static String CLOSE ="CL";
	public static final String OP_SEND_INFO_TEAM_TO_CREATOR = "STCR";
	public static final String WORLD_STATUS = "WS";
	
	public final static String OP_SEND_CURRENT_TEAM="CT";
	public static final String SET_STATER_PHASE = "SNT";
	public static final String CHANGE_TURN = "CNT";
	public static final String SET_DAMAGE_PHASE ="DAP";
	public static final String DAMAGE_CHARACTER = "DC";
	public static final String SET_DEATH_PHASE = "DEP";
	
	
	
}
