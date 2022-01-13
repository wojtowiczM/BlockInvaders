package game;


import java.awt.*;

public abstract class Entity {

    protected int xVelocity, yVelocity;

    protected int xPos, yPos;
    protected int width, height;

    protected int boardWidth = GameBoard.WIDTH;
    protected int boardHeight = GameBoard.HEIGHT;

    Entity(int width, int height){
        this.width = width;
        this.height = height;
    }
    public void setXPos(int xPos){
        this.xPos = xPos;
    }

    public void setYPos(int yPos){
        this.yPos = yPos;
    }

    public int getXPos(){
        return xPos;
    }

    public int getYPos(){
        return yPos;
    }

    public int getWidth(){ return width; }

    public int getHeight(){ return height; }


    public int getCenterX(){return xPos + (this.width / 2); }

    public int getCenterY(){return yPos + (this.height / 2); }

    public void setXvelocity(int xVelocity){
        this.xVelocity = xVelocity;
    }
    public void setYvelocity(int yVelocity){
        this.yVelocity = yVelocity;
    }


    public Rectangle getBound(){
      return new Rectangle(xPos, yPos, width, height);
    }

    public boolean isCollision(Entity o){
        if(o == this) return false;
        return getBound().intersects(o.getBound());
    }

    public void render(Graphics2D g2D){
        g2D.fillRect(xPos, yPos, width, height);
    }

    public abstract void move();

}
