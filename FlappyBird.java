package flappybird;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardwidth = 360;
    int boardlength = 640;
    Image backgroundImg;
    Image birdImg;
    Image toppipeImg;
    Image bottompipeImg;
    boolean gamestarted = false;

    //Bird
    int birdx = boardwidth/8;
    int birdy = boardlength/2;
    int birdwidth = 34;
    int birdheight = 24;

    class Bird{
        int x = birdx;
        int y = birdy;
        int width= birdwidth;
        int height= birdheight;
        Image img;
        Bird(Image img){
            this.img = img;
        }
    }
    //pipe
    int pipex= boardwidth;
    int pipey= 0;
    int pipeWidth=64;
    int pipeHeight=512;

    class Pipe{
        int x = pipex;
        int y = pipey;
        int width = pipeWidth;
        int height = pipeHeight;
        Image Img;
        boolean passed = false;

        Pipe(Image img){
            this.Img= img;
        }
    }

    //game logic
    Bird bird;
    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameloop;
    Timer PlacepipesTimer;
    boolean gameOver = false;
    double score = 0;


    public FlappyBird() {
        setPreferredSize(new Dimension(boardwidth,boardlength));
        setFocusable(true);
        addKeyListener(this);
    
        //load images
        backgroundImg = new ImageIcon(getClass().getResource("/flappybird/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("/flappybird/flappybird.png")).getImage();
        toppipeImg= new ImageIcon(getClass().getResource("/flappybird/toppipe.png")).getImage();
        bottompipeImg= new ImageIcon(getClass().getResource("/flappybird/bottompipe.png")).getImage();

        //bird
        bird = new Bird(birdImg);
        //pipes
        pipes = new ArrayList<Pipe>();

        //place pipes timer
        PlacepipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                placepipe();
            }
        });
        
        //game timer
        gameloop=new Timer(1000/60,this);
    }

    void placepipe(){
        int RandomY=(int)(pipey - pipeHeight/4 - Math.random() * (pipeHeight/2));
        int openingspace = boardlength/4;

        Pipe toppipe = new Pipe(toppipeImg);
        toppipe.y=RandomY;
        pipes.add(toppipe);

        Pipe bottompipe = new Pipe(bottompipeImg);
        bottompipe.y = toppipe.y + pipeHeight + openingspace;
        pipes.add(bottompipe);     
    }






    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
          //background
       g.drawImage(backgroundImg, 0,0, boardwidth,boardlength,null);

       //bird
       g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
       
       //pipe
       for(int i = 0; i < pipes.size(); i++){
        Pipe pipe = pipes.get(i);
        g.drawImage(pipe.Img, pipe.x, pipe.y, pipe.width, pipe.height, null);
       }

       //score
       g.setColor(Color.white);
       g.setFont(new Font("Arial", Font.PLAIN, 32));
       if(gameOver){
        g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
       }
       else{
        g.drawString("Score: " + String.valueOf((int) score), 10, 35);
       }
    }


    public void move(){
        //bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        //pipe
        for(int i = 0 ;i<pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            //score incremented by 1 if passed a set of pipes
            if(!pipe.passed && bird.x > pipe.x + pipe.width){
                pipe.passed = true;
                score += 0.5;
            }

            //game over if hit a pipe
            if(collision(bird , pipe)){
                gameOver = true;
            }
        }
        if(bird.y > boardlength){
            gameOver = true;
        }
    }

    //collision detection
    public boolean collision(Bird a, Pipe b){      
        return a.x < b.x + b.width &&
               a.x + a.width > b.x &&
               a.y < b.y +b.height &&
               a.y + a.height > b.y; 
    }





    @Override
    public void actionPerformed(ActionEvent e) {
        if(gamestarted){
            move();
            repaint();
             if(gameOver){
                PlacepipesTimer.stop();
                gameloop.stop();
            }
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            if(!gamestarted){
                gamestarted = true;
                PlacepipesTimer.start();
                gameloop.start();

            }
            velocityY = -9;
            if(gameOver){
                //restart the game
                bird.y = birdy;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gamestarted = false;
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
            
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    

    

}
