import object.character.Team;
import world.World;


public class MatchManager 
{
	private Team team1;
	private Team team2;
	private World world;

	
	public boolean isADraw(){
		//TODO implementare 
		return false;	
	}

	public boolean isMatchOver(){
		//TODO implementare 
		return false;	
	}

	public void start(){
		//TODO implementare 
	}

	public void update(){
		//TODO implementare 
	}

	public Team whoIsTheWinner(){
		//TODO implementare 
		return null;
	}

	public Team getTeam1() {
		return team1;
	}

	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return team2;
	}

	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
}
