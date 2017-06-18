import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

enum Weather{
	Sunny, Clowdy, Rainy
}
enum DayTime{
	Day, Night;
}

class Unit{
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
class Player extends Unit{
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
class Match{
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
class Dracura extends Unit{
	
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

class Constant{
	public static final long changeTime=2000;
	public static final long attackTime1000=1000;
	public static final long attackTime2000=2000;
	public static final long attackTime3000=3000;
}
class State{
	private DayTime day=DayTime.Day;
	private Weather weather=Weather.Sunny;
	
	public synchronized void printState(){
		System.out.print("["+day+"] ");
		System.out.println("[ Weather : "+weather+"]");
	}
	public synchronized void setDay(DayTime Day){
		day=Day;
	}
	public synchronized DayTime getDay(){
		return day;
	}
	public synchronized void setWeather(Weather weather){
		this.weather=weather;
	}
	public synchronized Weather getWeather(){
		return this.weather;
	}
}

class WeatherChanger implements Runnable{
	private State state;
	public WeatherChanger(State state){
		this.state=state;
	}
	public void WeatherChange(){
		Random random=new Random(System.currentTimeMillis());
		int randomNumber=random.nextInt(3);
		
		switch(randomNumber){
		case 0: state.setWeather(Weather.Sunny);break;
		case 1: state.setWeather(Weather.Clowdy);break;
		case 2: state.setWeather(Weather.Rainy);break;
		default : state.setWeather(Weather.Sunny);break;
		}
	}
	@Override
	public void run(){
		while(!Thread.interrupted()){
			//System.out.println("\t\t"+Thread.currentThread().getName()+" before :" +state.getWeather().toString());
    		WeatherChange();
    		try {
    			Thread.sleep(Constant.changeTime);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		//System.out.println("\t\t"+Thread.currentThread().getName()+" after :"+state.getWeather().toString());
		}
	}
}

class DayChanger implements Runnable{
	private State state;
	public DayChanger(State state){
		this.state=state;
	}
	public void dayChange(){
		if(state.getDay()==DayTime.Day){
			state.setDay(DayTime.Night);
		}else{
			state.setDay(DayTime.Day);
		}
	}
    @Override
	public void run() {
    	while(!Thread.interrupted()){
    		//System.out.println("\t\t"+Thread.currentThread().getName()+" before :" +state.getDay().toString());
    		dayChange();
    		try {
    			Thread.sleep(Constant.changeTime);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		//System.out.println("\t\t"+Thread.currentThread().getName()+" after :"+state.getDay().toString());
    	}
    }
}

public class Test {   
    public static void main(String[] args) throws IOException, InterruptedException{
    	State state=new State();
    	int number=0;
    	Thread daychange=new Thread(new DayChanger(state),"DayChanger");
    	Thread weatherchange=new Thread(new WeatherChanger(state), "WeatherChanger");
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
    			default: break;
    			
    		}
    	}while(number!=0 && match.whoisDie()==1);
    	
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