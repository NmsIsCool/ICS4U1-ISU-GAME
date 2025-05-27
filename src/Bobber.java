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
    int toAddY = 0, toAddX = 0;

    //bobber constructor
    public Bobber(int origx, int origy, int direction, int velocity) throws SlickException {
        bobberImage = new Image("data/assets/images/bobber.png");
        x = origx;
        y = origy;
        dir = direction;
        vel = velocity;
    }

    //draw bobber and fishing line - line position is determined by player direction
    public void draw(Graphics g) throws SlickException {
        playerX = mainGame.player.getX();
        playerY = mainGame.player.getY();

        g.drawImage(bobberImage, x, y);
        g.setColor(Color.red);

        if (mainGame.player.idlebobber && mainGame.player.dir == 3) { // RIGHT
            Line fishingLine = new Line(playerX + 82, playerY + 28, x + 4, y + 8);
            g.draw(fishingLine);
        } else if (mainGame.player.idlebobber && mainGame.player.dir == 1) { // LEFT
            Line fishingLine = new Line(playerX - 41, playerY + 26, x + 8, y + 4);
            g.draw(fishingLine);
        }

        


    }

    //based on velocity from casting minigame, change position based on velocity
    public void calculateCastDist() {
        x = mainGame.player.getX();
        y = mainGame.player.getY();
        vel = mainGame.player.getCastVelocity();
        dir = mainGame.player.getDir();

        toAddY = 0;
        toAddX = 0;
        for (vel++; vel > 0; vel -= 5) {
            // control the distance of the bobber in the Y axis
            if (dir == 0) {// UP
                toAddY -= vel;
            } else if (dir == 2) {// DOWN
                toAddY += vel;
            }
            // X axis
            else if (dir == 1) { // LEFT
                toAddX -= vel;
            } else if (dir == 3) { // RIGHT
                toAddX += vel;
            }
        }
        x += toAddX;
        y += toAddY;
    }

    //get distance between bobber and player, used to calculate quality score
    public double calculateDistance() {
        // bobber positions
        int x1 = x;
        int y1 = y; 

        // player positions
        int x2 = mainGame.player.getX();
        int y2 = mainGame.player.getY();

        // calculate distance with formula D=sqrt((x2-x1)^2+(y2-y1)^2)
        double distance = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        return distance;
    }

    //return quality score based on distance casted and distance from edge of map. If score is greater than 1, return 1
    public float getQualityScore() {
        // quality score is calculated as a percentage of the distance covered between
        // the edge of the dock and the edge of the map left as a decimal
        return  ((calculateDistance()/384)>1) ? 1 : (float) (calculateDistance() / 384);

    }

}
