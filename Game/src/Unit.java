public class Unit{
	protected State state;
	protected int HP=50;
	protected final int MAX_HP=50;;
	protected int attack;
	
	public void Attacked(int attackAmount){
		if(isAlive()){
			HP=HP-attackAmount;
		}
	}
	public boolean isAlive(){
		boolean isAlive=false;
		if(HP<0){
			isAlive=false;
		}else{
			isAlive=true;
		}
		return isAlive;
	}	
	public void Damege(int damege){
		if(isAlive()){
			HP-=damege;
		}
	}
	public void prevent(int attack){
		if(attack<11){
			HP-=5;
		}else{
			HP=HP-(attack-5);
		}
	}
}
