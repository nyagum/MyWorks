import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {   
    public static void main(String[] args) throws IOException, InterruptedException{
    	State state=new State();
    	int number=0;
    	
    	DayChanger dayChanger = new DayChanger(state);
    	WeatherChanger weatherChanger = new WeatherChanger(state);
    	
    	Thread daychange=new Thread(dayChanger,"DayChanger");
		Thread weatherchange=new Thread(weatherChanger, "WeatherChanger");
		
    	Dracura dracura=new Dracura(state);
    	Player player=new Player(state);
    	
    	Match match=new Match(player, dracura);
    	
    	daychange.start();
    	weatherchange.start();
    	
    	
    	System.out.println("공방 게임을 시작합니다.");
    	System.out.println("적인 드라큐라는 낮밤상태에 따라 데미지가 다릅니다.");
    	System.out.println("낮밤과 날씨는 2초마다 바뀌고, 날씨는 랜덤으로 바뀝니다.");
    	System.out.println("플레이어 당신의 차례입니다.");
    	do{
    		state.printState();
    		match.printState();
    		System.out.println("1.공격, 2.방어 3.종료");
    		
    		number=getNumber();
    		switch(number){
    			case 1://공격
    				match.PlayerAttack();
    				break;
    			case 2://방
    				match.DracuraAttack();
    				break;
    			case 3://종료
    				number=3;
    				break;
    			default: break;
    			
    		}
    	}while(number!=3 && match.whoisDie()==1);
    	
    	switch(match.whoisDie()){
    	case 2:
    		System.out.println("드라큘라가 이겼습니다!!!");
    		break;
    	case 3:
    		System.out.println("플레이어가 이겼습니다.");
    		break;
    	default:
    		break;
    	}
    	weatherChanger.finish();
    	System.out.println("End of Main");
    }
    public static int getNumber() throws IOException, NumberFormatException{
		int inputNumber=0;
		String inputString = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		inputString = in.readLine();
		inputNumber=Integer.parseInt(inputString);
		return inputNumber;
	}
}

//class Monster extends Unit{
//	
//}
//class Warrior extends Player{
//	
//}
//class Magician extends Player{
//	
//}
//class Snail extends Player{
//	
//}