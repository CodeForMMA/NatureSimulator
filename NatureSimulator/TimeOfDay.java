public class TimeOfDay{
    private int step;

    public TimeOfDay(){

    }

    public void incrementStep(){
        step++;
    }
    public int getCurrentStep(){
        return step;
    }

    private boolean timeToMove(){
        boolean canMove = false;
        if(step == 12){
            canMove = true;
        }
        return canMove;

    }
    public boolean whatTime() {
        boolean canMove = true;
        if (timeToMove()){
            canMove = false;
        }
        return canMove;
    }
}
