package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameBoard extends JPanel implements Runnable{

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static final int borderWidth = 800;
    public static final int borderHeight = 500;


    private Thread thread;
    private boolean running;
    private long targetTime;

    private Graphics2D g2D;
    private BufferedImage img;

    public static Player1 player1;
    public static Player2 player2;

    HealthBar healthBarPlayer1;
    HealthBar healthBarPlayer2;
    HealthBar deathBarPlayer1;
    HealthBar deathBarPlayer2;
    ArrayList<Bullet> bullets;
    ArrayList<EnemyShip> enemyShips;
    Border border;

    private int shipWidth = 50;
    private int shipHeight = 20;
    private int enemyShipWidth = 50;
    private int enemyShipHeight = 20;
    private int bulletWidth = 5;
    private int bulletHeight = 5;
    private int enemyBulletWidth = 5;
    private int enemyBulletHeight = 10;
    private int healthBarWidth = 100;
    private int healthBarHeight = 20;

    private int speed = 2;
    private boolean shootPlayer1 = false,shootPlayer2=false;
    private boolean PVP = true;


    private boolean keyHeldPlayer1=false, keyHeldPlayer2 = false;
    private int keyCodePlayer1, keyCodePlayer2;

    public boolean upPlayer1=false, downPlayer1=false, leftPlayer1=false, rightPlayer1=false,
                     upPlayer2=false, downPlayer2=false, leftPlayer2=false, rightPlayer2=false;

    private boolean shooted = false;


    public GameBoard(){
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        this.requestFocus();

        this.addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {

            }

            public void keyPressed(KeyEvent e) {

                //PLAYER 1 SWITCH
                switch (e.getKeyCode()) {

                    case KeyEvent.VK_UP:
                        keyHeldPlayer1 = true;
                        keyCodePlayer1 = e.getKeyCode();
                        break;

                    case KeyEvent.VK_DOWN:
                        keyHeldPlayer1 = true;
                        keyCodePlayer1 = e.getKeyCode();
                        break;

                    case KeyEvent.VK_RIGHT:
                        keyHeldPlayer1 = true;
                        keyCodePlayer1 = e.getKeyCode();

                        break;

                    case KeyEvent.VK_LEFT:
                        keyHeldPlayer1 = true;
                        keyCodePlayer1 = e.getKeyCode();
                        break;

                }

                //PLAYER 2 SWITCH
                if (PVP) {

                    switch (e.getKeyCode()) {

                        case KeyEvent.VK_W:
                            keyHeldPlayer2 = true;
                            keyCodePlayer2 = e.getKeyCode();
                            break;

                        case KeyEvent.VK_S:
                            keyHeldPlayer2 = true;
                            keyCodePlayer2 = e.getKeyCode();
                            break;

                        case KeyEvent.VK_D:
                            keyHeldPlayer2 = true;
                            keyCodePlayer2 = e.getKeyCode();

                            break;

                        case KeyEvent.VK_A:
                            keyHeldPlayer2 = true;
                            keyCodePlayer2 = e.getKeyCode();
                            break;

                    }
                }
            }
            public void keyReleased(KeyEvent e) {

                switch(e.getKeyCode()){
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_DOWN:
                        keyHeldPlayer1 = false;
                        player1.setYvelocity(0);
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_RIGHT:
                        keyHeldPlayer1 = false;
                        player1.setXvelocity(0);
                        break;
                }
                if(PVP) {

                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_W:
                        case KeyEvent.VK_S:
                            keyHeldPlayer2 = false;
                            player2.setYvelocity(0);
                            break;
                        case KeyEvent.VK_A:
                        case KeyEvent.VK_D:
                            keyHeldPlayer2 = false;
                            player2.setXvelocity(0);
                            break;

                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    shootPlayer1 = true;
                   }

                if(PVP) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        shootPlayer2 = true;
                    }
                }
            }
        });
    }

    public void addNotify(){
        super.addNotify();
        thread = new Thread(this);
        thread.start();
    }

    private void setFPS(int fps){
        targetTime = 1000 / fps;
    }


    private void init(){
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2D = img.createGraphics();
        running = true;
        setUpGame();
        setFPS(100);
    }

    public void run() {
        if(running) return;
        init();
        long startTime;
        long elapsed;
        long wait;
        while(running){
            startTime = System.nanoTime();
            update();
            requestRender();
            elapsed = System.nanoTime() - startTime;
            wait = targetTime - elapsed / 1000000;
            if(wait > 0){
                try{
                    Thread.sleep(wait);
                }catch (InterruptedException ex){System.out.println(ex.getMessage());}
            }
        }

    }

    private void update(){

        if(keyHeldPlayer1){

            switch(keyCodePlayer1){

                case KeyEvent.VK_UP:
                    player1.setYvelocity(-speed);
                    break;

                case KeyEvent.VK_DOWN:
                    player1.setYvelocity(speed);
                    break;

                case KeyEvent.VK_RIGHT:
                    player1.setXvelocity(speed);
                    break;

                case KeyEvent.VK_LEFT:
                    player1.setXvelocity(-speed);
                    break;

            }
       }

       if (shootPlayer1){
            shootBullet(bulletWidth, bulletHeight, player1.getCenterX(), player1.getCenterY(), player1);
            shootPlayer1 = false;
        }

        if(keyHeldPlayer2){

            switch(keyCodePlayer2){

                case KeyEvent.VK_S:
                    player2.setYvelocity(-speed);
                    break;

                case KeyEvent.VK_W:
                    player2.setYvelocity(speed);
                    break;

                case KeyEvent.VK_D:
                    player2.setXvelocity(speed);
                    break;
                case KeyEvent.VK_A:
                    player2.setXvelocity(-speed);
                    break;

            }
        }

        if (shootPlayer2){
            shootBullet(bulletWidth, bulletHeight, player2.getCenterX(), player2.getCenterY(), player2);
            shootPlayer2 = false;
        }

        if(PVP){
            bulletActionsForPVP();

        }else {
            bulletActionsForEnemies();
            enemyActions();
            if (enemyShips.size() == 0 ) init();
        }

        if(player1.isCollision(player2)){
            player1.setHealth(player1.getHealth() - 1);
            player2.setHealth(player2.getHealth() - 1);
            updateHealthBar();
        }

        if(player1.getHealth() == 0)init();
        if(player2.getHealth() == 0)init();

            player1.move();
            player2.move();

    }

    private boolean isInRange(int index){
        if(enemyShips.get(index).getCenterX() < player1.getCenterX() + 2 && enemyShips.get(index).getCenterX() > player1.getCenterX() - 2)return true;
        else return false;

    }

    private void enemyActions(){

        for(int i = 0; i < enemyShips.size(); i++){
            if(i==0){
                if (enemyShips.get(i).getXPos() < 0) {
                    for(EnemyShip e: enemyShips) {
                        e.left = false;
                        e.right = true;
                    }
                }
            }
            if(i == enemyShips.size() - 1){
                if(enemyShips.get(i).getXPos() + enemyShips.get(i).getWidth() > borderWidth){
                    for(EnemyShip e: enemyShips) {
                        e.left = true;
                        e.right = false;
                    }
                }
            }
            if(i != 0 && i != enemyShips.size() -1){
                enemyShips.get(i).setXPos(enemyShips.get(i - 1).getXPos() + enemyShipWidth +70);
            }
            enemyShips.get(i).move();

            if(!isInRange(i)){
                shooted = false;
            }
            if(!shooted) {
                if (isInRange(i)) {
                    shootBullet(enemyBulletWidth, enemyBulletHeight, enemyShips.get(i).getCenterX(), enemyShips.get(i).getCenterY(), enemyShips.get(i));
                    shooted = true;
                }
            }
            if(enemyShips.get(i).isCollision(player1))init();

        }
    }

    private void bulletActionsForPVP(){

        ArrayList<Bullet> toRemove = new ArrayList<>();

        for(Bullet b: bullets) {
            b.move();
            if (b.isCollision(border) ) toRemove.add(b);
            if(b.isCollision(player1) && b.getShooter() != player1){
                player1.setHealth(player1.getHealth() - b.getDamage());
                updateHealthBar();
            }
            if(b.isCollision(player2) && b.getShooter() != player2){
                player2.setHealth(player2.getHealth() - b.getDamage());
                if(player2.getHealth() == 0)init();
                updateHealthBar();
            }
        }
        bullets.removeAll(toRemove);
    }

    private void bulletActionsForEnemies(){

        for(Bullet b: bullets) {
            b.move();
            ArrayList<EnemyShip> toRemove = new ArrayList<>();
            for (EnemyShip e : enemyShips) {
                if (e.isCollision(b) && b.getShooter() == player1)
                    toRemove.add(e);
            }
            enemyShips.removeAll(toRemove);
            if(b.isCollision(player1) && b.getShooter() != player1)init();
        }
    }


    private void shootBullet(int bWidth, int bHeight, int parentXPos, int parentYPos, Entity shooter){

        bullets.add(new Bullet(bWidth, bHeight, parentXPos, parentYPos, shooter));
    }

    private void setUpGame(){

        player1 = createPlayer1();
        if(PVP)player2 = createPlayer2();
        else enemyShips = createEnemies();
        bullets = new ArrayList<Bullet>();

        border = createBorder(borderWidth, 2, 0, 100);

    }

    private Player1 createPlayer1(){

        Player1 player = new Player1(shipWidth, shipHeight);

        player.setXPos(borderWidth / 2);
        player.setYPos(borderHeight - (borderHeight / 20) + 75);// 575
        player.setHealth(100);
        createPlayer1HealthBar();
        return player;
    }

    private Player2 createPlayer2(){

        Player2 player = new Player2(shipWidth, shipHeight);

        player.setXPos(borderWidth / 2);
        player.setYPos(borderHeight / 20 + 100);
        player.setHealth(100);
        createPlayer2HealthBar();
        return player;
    }

    private ArrayList<EnemyShip> createEnemies(){

        ArrayList<EnemyShip> eShips = new ArrayList<EnemyShip>();
        eShips.add(new EnemyShip(enemyShipWidth, enemyShipHeight));
        eShips.add(new EnemyShip(enemyShipWidth, enemyShipHeight));
        eShips.add(new EnemyShip(enemyShipWidth, enemyShipHeight));
        eShips.add(new EnemyShip(enemyShipWidth, enemyShipHeight));

        for(int i = 0; i < eShips.size();i++){

            eShips.get(i).setYPos(borderHeight / 20);
            if(i==0)eShips.get(i).setXPos(borderWidth /10);
            else eShips.get(i).setXPos(eShips.get(i-1).getXPos() + shipWidth+ 70);

        }
        return eShips;
    }

    private void createPlayer1HealthBar(){

        healthBarPlayer1 = new HealthBar(healthBarWidth, healthBarHeight);
        healthBarPlayer1.setXPos(30);
        healthBarPlayer1.setYPos(30);

        deathBarPlayer1 = new HealthBar(0, healthBarPlayer1.getHeight());
        deathBarPlayer1.setXPos(healthBarPlayer1.getXPos()+healthBarPlayer1.getWidth());
        deathBarPlayer1.setYPos(30);

    }

    private void createPlayer2HealthBar(){

        healthBarPlayer2 = new HealthBar(healthBarWidth, healthBarHeight);
        healthBarPlayer2.setXPos(WIDTH - healthBarPlayer2.getWidth() - 30);
        healthBarPlayer2.setYPos(30);

        deathBarPlayer2 = new HealthBar(0, healthBarPlayer2.getHeight());
        deathBarPlayer2.setXPos(healthBarPlayer2.getXPos() + healthBarPlayer2.getWidth());
        deathBarPlayer2.setYPos(healthBarPlayer2.getYPos());
    }

    private Border createBorder(int width, int height, int xPos, int yPos){

        Border border = new Border(width, height);
        border.setXPos(xPos);
        border.setYPos(yPos);

        return border;
    }

    private void updateHealthBar(){

        healthBarPlayer1.setWidth(player1.getHealth());
        deathBarPlayer1.setXPos(healthBarPlayer1.getXPos() + player1.getHealth());
        deathBarPlayer1.setWidth(healthBarWidth - player1.getHealth() );

        healthBarPlayer2.setWidth(player2.getHealth());
        deathBarPlayer2.setXPos(healthBarPlayer2.getXPos() + player2.getHealth());
        deathBarPlayer2.setWidth(healthBarWidth - player2.getHealth() );
    }

    private void nextLevel(){

        player1.setXPos(borderWidth / 2);
        player1.setYPos(borderHeight - (borderHeight / 10));

        enemyShips.add(new EnemyShip(shipWidth, shipHeight));
        for(int i = 0; i < enemyShips.size();i++){
            if(i==0)enemyShips.get(i).setXPos(borderWidth /10);
            else enemyShips.get(i).setXPos(enemyShips.get(i-1).getXPos() + 70);
        }
    }

    private void requestRender(){
        render(g2D);
        Graphics g = getGraphics();
        g.drawImage(img,0,0,null);
        g.dispose();
    }

    private void render(Graphics2D g2D){
        g2D.clearRect(0,0, WIDTH, HEIGHT);
        g2D.setColor(Color.GREEN);
        player1.render(g2D);
        healthBarPlayer1.render(g2D);
        healthBarPlayer2.render(g2D);
        g2D.setColor(Color.RED);
        if(PVP){
            player2.render(g2D);
        }else{
            for(EnemyShip e: enemyShips){
                e.render(g2D);
            }
        }
        deathBarPlayer1.render(g2D);
        deathBarPlayer2.render(g2D);
        g2D.setColor(Color.WHITE);
        for(Bullet b: bullets){
            b.render(g2D);
        }

        border.render(g2D);

        g2D.drawString("Player 1 : " + player1.getHealth(),healthBarPlayer1.getXPos(),healthBarPlayer1.getYPos() - 5);
        g2D.drawString("Player 2 : " +  player2.getHealth(),healthBarPlayer2.getXPos(),healthBarPlayer1.getYPos() - 5);
    }
}
