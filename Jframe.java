package flappybird;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
public class Jframe {
    public static void main(String[] args) {
        int boardwidth=360;
        int boardlength=640;
        JFrame frame = new JFrame(); 
         frame.setTitle("Flappy Bird");
         frame.setResizable(false);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setSize(boardwidth,boardlength);
         ImageIcon image = new ImageIcon("src\\flappybird\\flappybird.png");
         frame.setIconImage(image.getImage());
         

         FlappyBird flappybird = new FlappyBird();
         frame.add(flappybird);
         frame.pack();
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
         flappybird.requestFocusInWindow();
    }
       

}
