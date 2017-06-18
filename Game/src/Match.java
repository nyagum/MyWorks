
public class Match{
	private Player player;
	private Dracura dracura;
	public Match(Player p, Dracura d){
		this.player=p;
		this.dracura=d;
	}
	public void DracuraAttack(){
		player.Damege(dracura.Bloodsucking());
	}
	public void PlayerAttack(){
		dracura.Damege(player.Hit());
	}
	public void printState(){
		System.out.println("======================================================");
		System.out.println("[Player HP :"+player.HP+"] [Dracura HP :"+dracura.HP+"]");
		System.out.println("======================================================");
	}
	public int whoisDie(){
		if(player.isAlive() && dracura.isAlive()){
			return 1;
		}else if(dracura.isAlive()){
			return 2;
		}else if(player.isAlive()){
			return 3;
		}else{
			return 4;
		}
	}
}