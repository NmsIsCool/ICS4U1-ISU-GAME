import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;

@SuppressWarnings("unused")
public class Bobber {
    int x = 0;
    int y = 0;
    int dir = 0; // 0=U, 1=L, 2=D, 3=R;
    float vel = 0;
    Image bobberImage;
    int playerX, playerY;
    int toAddY = 0, toAddX = 0;

    // bobber constructor, just makes the image
    public Bobber() throws SlickException {
        bobberImage = new Image("data/assets/images/bobber.png");
    }

    // draw bobber and fishing line - line position is determined by player
    // direction
    public void draw(Graphics g) throws SlickException {
        playerX = mainGame.player.getX();
        playerY = mainGame.player.getY();
        //output bobber position
        //mainGame.debugOutput("Bobber Position: " + x + ", " + y);
        g.drawImage(bobberImage, x, y);
        
        //make fishing line color black during gameplay, red whilst debugging
        if(!mainGame.debug)
            g.setColor(Color.black); 
        else
            g.setColor(Color.red);
        // adjust fishing line position to align with sprite in each direction
        if (mainGame.player.idlebobber && mainGame.player.dir == 3) { // RIGHT
            Line fishingLine = new Line(playerX + 82, playerY + 28, x + 4, y + 8);
            g.draw(fishingLine);
        } else if (mainGame.player.idlebobber && mainGame.player.dir == 1) { // LEFT
            Line fishingLine = new Line(playerX - 41, playerY + 26, x + 8, y + 4);
            g.draw(fishingLine);
        } else if (mainGame.player.idlebobber && mainGame.player.dir == 2) { // DOWN
            Line fishingLine = new Line(playerX + 45, playerY + 25, x + 4, y + 8);
            g.draw(fishingLine);
        } else if (mainGame.player.idlebobber && mainGame.player.dir == 0) { // UP
            Line fishingLine = new Line(playerX + 60, playerY, x + 4, y + 8);
            g.draw(fishingLine);
        }

    }

    // based on velocity from casting minigame, change position based on velocity
    public void calculateCast() {
        x = mainGame.player.getX();
        y = mainGame.player.getY() + 24;
        if(mainGame.castGame.tickerpercent<=20)
            vel=21;
        else
            vel= (mainGame.castGame.tickerpercent)/1.1f; // get velocity from cast game ticker
        dir = mainGame.player.getDir();

        toAddY = 0;
        toAddX = 0;
        for (vel++; vel >= 0; vel -= 5) {
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
        if(y>992)
            y=992;
        vel = 0;
    }

    //set bobber velocity
    public void setVel(int newVel){
        vel=newVel;
    }

    // get distance between bobber and player, used to calculate quality score
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

    // return quality score based on distance casted and distance from edge of map.
    // If score is greater than 1, return 1
    public float getQualityScore() {
        // quality score is calculated as a percentage of the distance covered between
        // the edge of the dock and the edge of the map left as a decimal
        if (calculateDistance()/ getDistanceFromPlayerToLowerEdge() > 1) {
            return 1;
        } else {
            return (float) (calculateDistance() / getDistanceFromPlayerToLowerEdge());
        }

    }

    //method to get distance from player to lower edge of the map (1920x1088)
    public static int getDistanceFromPlayerToLowerEdge() {
        return GameInit.SCREEN_HEIGHT - (mainGame.player.getY() + 64);
    }
    

}
