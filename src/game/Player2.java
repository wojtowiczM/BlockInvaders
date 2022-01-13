package game;

/**
 * Created by Michal on 2017-08-07.
 */
public class Player2 extends Entity{

    private int ammunition = 1000;
    private int health = 100;

    Player2(int width, int height){
        super(width, height);
    }

    @Override
    public void move() {
        this.xPos += this.xVelocity;
        this.yPos += this.yVelocity;

        if(this.getXPos() < 0)this.setXPos(boardWidth);
        if(this.getXPos() > boardWidth)this.setXPos(-98);
        if(this.getYPos() < boardHeight - GameBoard.borderHeight)this.setYPos(boardHeight - GameBoard.borderHeight);
        if(this.getYPos()+this.height > boardHeight)this.setYPos(boardHeight-this.height);
    }

    public void setHealth(int health){
        this.health = health;
    }

    public void setAmmunition(int Ammunition){
        this.ammunition = ammunition;
    }

    public int getAmmunition(){
        return ammunition;
    }

    public int getHealth(){
        return health;
    }
}
