
public class Dracura extends Unit{
	
	public Dracura(State state){
		super.state=state;
		attack=10;
	}
	public int Bloodsucking(){ // 흡혈하다
		if(state.getDay()==DayTime.Night){ // 밤에는 세진다.
			attack=20;
			//System.out.println("밤이 되었다. 드라큐라의 공격력은 "+attack+"으로 변했다.");
		}else{
			attack=10;
		}
		return attack;
	}
}

