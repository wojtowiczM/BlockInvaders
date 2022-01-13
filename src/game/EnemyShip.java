package game;


public class EnemyShip extends Entity {

    public boolean left = false, right = true;

    EnemyShip(int width, int height){
        super(width, height);
        this.width = width;
        this.height = height;
    }


    @Override
    public void move() {

        if(right){
            incrementX();
        }else if(left){
            decrementX();
        }
    }

    public void incrementX(){
        this.setXPos(this.getXPos() + 2);

    }
    private void decrementX(){
        this.setXPos(this.getXPos() - 2);

    }

}
