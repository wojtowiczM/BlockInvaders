package game;

/**
 * Created by Michal on 2017-08-08.
 */
public class HealthBar extends Entity {

    HealthBar(int width, int height){
        super(width, height);
    }
    public void setWidth(int width){
        this.width = width;
    }

    @Override
    public void move() {

    }
}
