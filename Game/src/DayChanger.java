
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