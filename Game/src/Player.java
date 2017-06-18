
public class Player extends Unit{
	public Player(State state){
		super.state=state;
		attack=11;
	}
	public int Hit(){
		if(state.getDay()==DayTime.Night){
			attack=8;
		}else{
			attack=11;
		}
		return attack;
	}
	public void prevent(int attack){
		if(attack<11){
			HP-=8;
		}else{
			HP=HP-(attack-8);
		}
	}
}

