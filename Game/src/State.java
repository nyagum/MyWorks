
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