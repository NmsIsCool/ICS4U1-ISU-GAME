import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;

@SuppressWarnings("unused")
public class Bobber {
    int x = 0;
    int y = 0;
    int dir = 0; // 0=U, 1=L, 2=D, 3=R;
    int vel;
    Image bobberImage;
    int playerX, playerY;

    public Bobber(int origx, int origy, int direction, int velocity) throws SlickException {
        bobberImage = new Image("data/assets/bobber.png");
        x = origx;
        y = origy;
        dir = direction;
        vel = velocity;
    }

    public void move() throws SlickException {
        playerX = mainGame.player.getX();
        playerY = mainGame.player.getY();
        mainGame.debugOutput("Positions:" +playerX +" | " +playerY);
        if (vel > 0) {
            if (dir == 0) {
                y -= vel;
            } else if (dir == 1) {
                x -= vel;
            } else if (dir == 2) {
                y += vel;
            } else if (dir == 3) {
                x += vel;
            }
        }
        vel -= 5;

    }

    public void draw(Graphics g) throws SlickException {
        g.drawImage(bobberImage, x, y);
        g.setColor(Color.red);
        
        if(mainGame.player.idlebobber && mainGame.player.dir==3){
            Line fishingLine = new Line(playerX+82, playerY+28, x+4, y+8);
            g.draw(fishingLine);
        }
    }
}