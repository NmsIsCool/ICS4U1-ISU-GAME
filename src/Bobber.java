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
    int toAddY=0, toAddX=0;

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
        /*if (vel > 0) {
            if (dir == 0) {
                y -= vel;
            } else if (dir == 1) {
                x -= vel;
            } else if (dir == 2) {
                y += vel;
            } else if (dir == 3) {
                x += vel;
            }
        }*/
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

    public void calculateCastDist(){
        toAddY=0;
        toAddX=0;
        for(vel++; vel>0; vel-=5){
            //control the distance of the bobber in the Y axis
            if(dir==0){// UP
                toAddY-=vel;
            }else if(dir==2){// DOWN
                toAddY+=vel;
            }
            //X axis
            else if(dir==1){ //LEFT
                toAddX-=vel;
            }else if(dir==3){ //RIGHT
                toAddX+=vel;
            }
        }
        x+=toAddX;
        y+=toAddY;
    }

    public double calculateDistance(){
        //bobber positions
        int x1=x;
        int y1=y;

        //player positions
        int x2=mainGame.player.getX();
        int y2=mainGame.player.getY();

        //calculate distance with formula D=sqrt((x2-x1)^2+(y2-y1)^2)
        double distance=Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1),2));
        return distance;
    }

    public float getQualityScore(){
        //quality score is calculated as a percentage of the distance covered between the edge of the dock and the edge of the map left as a decimal
        return (float) ((calculateDistance()/384));

    }

    }
