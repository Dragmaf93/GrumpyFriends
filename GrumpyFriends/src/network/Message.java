package network;

public abstract class Message {

	public final static String OP_REQUEST_LIST = "RL";
	public final static String OP_REQUEST_INFO_MATCH = "RM";
	public final static String OP_REQUEST_INFO_TEAM = "RT";
	public final static String OP_REQUEST_INFO_WORLD = "RW";
	public final static String OP_SELECT_MATCH = "SM";
	public final static String OP_SEND_INFO_TEAM = "ST";
	public final static String OP_CREATE_MATCH = "CM";
	
	public final static String OP_SEND_LIST = "SL";
	public final static String OP_SEND_INFO_MATCH = "SIM";
	public final static String OP_SEND_INFO_WORLD = "SW";
	public final static String OP_CONFIRM = "OK";
	public final static String OP_ERROR = "ERR";
	
}
