package game;


public class Bullet extends Entity{

    private Entity shooter;

    private int damage = 5;

    Bullet(int width, int height, int parentXPos, int parentYPos, Entity shooter){
        super(width, height);
        this.setXPos(parentXPos);
        this.setYPos(parentYPos);
        this.shooter = shooter;
        if(shooter == GameBoard.player1){
            this.yVelocity = -5;
        }else{
            this.yVelocity = 5;
        }
    }

    @Override
    public void move() {
        if(shooter == GameBoard.player1){
            this.yPos += this.yVelocity;

        }else{
            this.setYPos(this.getYPos() + 5);
        }
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public int getDamage(){
        return damage;
    }

    public Entity getShooter(){
        return shooter;
    }
}
