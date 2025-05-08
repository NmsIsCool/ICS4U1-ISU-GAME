import org.newdawn.slick.geom.*;
import org.newdawn.slick.*;
import java.util.ArrayList;

public class Fisher {
    SpriteSheet walkSheet;
    SpriteSheet castSheet;
    Animation castAnim[]=new Animation [12];
    Animation walkAnim[]=new Animation [8];
    Image walk[][]=new Image[4][8];
    Image idleImage[]=new Image[4];
    int x,y;
    int dir=3; // 0=U, 1=L, 2=D, 3=R;
    boolean stop=false;
    private Rectangle hitbox;

    public Fisher(int x, int y) throws SlickException {
        this.x=x;
        this.y=y;
        walkSheet = new SpriteSheet("data/assets/walk.png", 64,64);
        castSheet = new SpriteSheet("data/assets/tool_rod.png", 192, 192);

        walkSheet.startUse();
        for(int i=0; i<4; i++){
            
            for(int j=0; j<8; j++){
                walk[i][j]=walkSheet.getSubImage(j,i);
                
            }
            walkAnim[i]=new Animation(walk[i], 100);
        }

        for(int i=0; i<12; i++){
            
        }
        idleImage[0]=walkSheet.getSubImage(0,0);
        idleImage[1]=walkSheet.getSubImage(0,1);
        idleImage[2]=walkSheet.getSubImage(0,2);
        idleImage[3]=walkSheet.getSubImage(0,3);
        walkSheet.endUse();
        hitbox=new Rectangle(x,y,32,32);
        stop=true;
        dir=3;
    }

    public void move(Input kb)throws SlickException {
        stop = false;
        int x = (int) hitbox.getX();
        int y = (int) hitbox.getY();
        int origx = x, origy = y;
        if (kb.isKeyDown(Input.KEY_D)) {
            x++;
            dir = 3;
        } else if (kb.isKeyDown(Input.KEY_A)) {
            x--;
            dir = 1;
        } else if (kb.isKeyDown(Input.KEY_W)) {
            y--;
            dir = 0;
        } else if (kb.isKeyDown(Input.KEY_S)) {
            y++;
            dir = 2;
        } else {
            stop = true;
        }
        hitbox.setX(x);
        hitbox.setY(y);
    }

    public void draw() {
        if (stop) {
            walkAnim[dir].stop();
            idleImage[dir].draw(hitbox.getX() - 12, hitbox.getY() - 12);
        } else {
            walkAnim[dir].start();
            walkAnim[dir].draw(hitbox.getX() - 12, hitbox.getY() - 12);
        }
    }
}
