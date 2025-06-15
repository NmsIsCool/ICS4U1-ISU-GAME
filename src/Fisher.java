import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.*;
import java.util.ArrayList;

public class Fisher {

    // declare variables/objects
    SpriteSheet walkSheet;
    SpriteSheet castSheet;
    SpriteSheet runSheet;
    Animation castAnim[] = new Animation[12];
    Animation walkAnim[] = new Animation[8];
    Animation runAnim[] = new Animation[8];
    Image walk[][] = new Image[4][8];
    Image run[][] = new Image[4][8];
    Image cast[][] = new Image[4][12];
    Image idleImage[] = new Image[4];
    int x, y;
    int dx = 1;
    int dir = 3; // 0=U, 1=L, 2=D, 3=R;
    boolean stop = false, stopcast = true;
    boolean running = false;
    public Rectangle hitbox;
    public boolean casting = false, holdingcast = false, idlebobber = false;
    public float varyDist = 0;

    // Variables to store fish inventory, not yet used but Soon(TM)
    double scoreMult = 1; // score multiplier based on fish in current inventory
    int score = 0; // score based on fish caught, pretty cool ig
    int trash = 0; // score+=1
    int minorFish = 0; // Score+=5
    int mediocreFish = 0; // Score+=10
    int largeFish = 0; // Score+=25
    int mythicFish = 0; // Score+=75, scoreMult+=1
    int RNGesusFish = 0; // Score+=125, scoreMult+=1.5

    // object contructor, create player and animations from sprite sheets
    public Fisher(int x, int y) throws SlickException {
        this.x = x;
        this.y = y;
        walkSheet = new SpriteSheet("data/assets/images/walk.png", 64, 64);
        runSheet = new SpriteSheet("data/assets/images/run.png", 64, 64);
        castSheet = new SpriteSheet("data/assets/images/tool_rod.png", 128, 128);

        walkSheet.startUse();
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 8; j++) {
                walk[i][j] = walkSheet.getSubImage(j, i);

            }
            walkAnim[i] = new Animation(walk[i], 100, true);
        }
        idleImage[0] = walkSheet.getSubImage(0, 0);
        idleImage[1] = walkSheet.getSubImage(0, 1);
        idleImage[2] = walkSheet.getSubImage(0, 2);
        idleImage[3] = walkSheet.getSubImage(0, 3);
        walkSheet.endUse();

        runSheet.startUse();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                run[i][j] = runSheet.getSubImage(j, i);
            }
            runAnim[i] = new Animation(run[i], 100, true);
        }
        runSheet.endUse();

        castSheet.startUse();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                cast[i][j] = castSheet.getSubImage(j, i);
            }
            castAnim[i] = new Animation(cast[i], 150);

        }
        castSheet.endUse();

        hitbox = new Rectangle(x, y, 32, 64);
        stop = true;
        dir = 3;
    }

    // controls player movement and casting
    public void move(Input kb, ArrayList<Rectangle> barriers) throws SlickException {
        stop = false;
        if (idlebobber)
            stop = true;
        int x = (int) hitbox.getX();
        int y = (int) hitbox.getY();
        int origx = x, origy = y;

        if (kb.isKeyDown(Input.KEY_LSHIFT) && mainGame.debug) {
            running = true;
            dx = 2;
        } else {
            dx = 1;
            running = false;
        }
        // control movement while not holding cast
        if (kb.isKeyDown(Input.KEY_D) && !holdingcast && !idlebobber && !casting) {
            x += dx;
            dir = 3;
        } else if (kb.isKeyDown(Input.KEY_A) && !holdingcast && !idlebobber && !casting) {
            x -= dx;
            dir = 1;
        } else if (kb.isKeyDown(Input.KEY_W) && !holdingcast && !idlebobber && !casting) {
            y -= dx;
            dir = 0;
        } else if (kb.isKeyDown(Input.KEY_S) && !holdingcast && !idlebobber && !casting) {
            y += dx;
            dir = 2;
        } else {
            stop = true;
        }

        if ((!kb.isKeyDown(Input.KEY_D) && !kb.isKeyDown(Input.KEY_A) && !kb.isKeyDown(Input.KEY_W)
                && !kb.isKeyDown(Input.KEY_S))
                || holdingcast || idlebobber || casting) {
            stop = true;
        }

        hitbox.setX(x);
        hitbox.setY(y);

        if (isHitting(barriers) || x > 1920 - 32 || x < 0 || y < 0) {
            hitbox.setX(origx);
            hitbox.setY(origy);
        }

        // allow functionality for holding cast
        // if space is held and bobber not idle, start holding cast

        if (kb.isKeyDown(Input.KEY_SPACE) && !idlebobber) {
            holdingcast = true;
            mainGame.fishTimerLatch = false;
        } else if (!kb.isKeyDown(Input.KEY_SPACE) && holdingcast == true) { // when space released, start casting, if
                                                                            // key space is not held and cast is being
                                                                         // held,
            holdingcast = false; // play cast animation at frame 3 and set bobber to idle
            stopcast = false;
            casting = true;
            
            mainGame.fishTimerLatch = false;
            varyDist = (float) Math.random() * 10;
            castAnim[dir].restart();
            castAnim[dir].setCurrentFrame(3);
            holdingcast = false;
            mainGame.bobber.calculateCast();

            // if key space is not held and player is not casting and not holding cast, stop
            // animation and draw fishing idle frame
        } else if (!kb.isKeyDown(Input.KEY_SPACE) && !casting && !holdingcast) {
            stopcast = true;
            casting = false;

            // if key LMB pressed is pressed and bobber is idle, stop bobber and start idle
            // frame
        }
        if (kb.isMousePressed(Input.MOUSE_LEFT_BUTTON) && (idlebobber || casting)) {
            idlebobber = false;
            stop = true;
            casting = false;
            holdingcast = false;
            stopcast = true;
            castAnim[dir].stop();
            idleImage[dir].draw(x, y);
            mainGame.fishTimerLatch = false;
            mainGame.enterFishMiniGame = false;
            mainGame.debugOutput("Bobber Stopped");
            mainGame.stoppedBobber=true;

        }

        if (casting && castAnim[dir].getFrame() == 9) {
            casting = false;
            idlebobber = true;
        }

    }

    // check is player is hitting barriers/key points
    public boolean isHitting(ArrayList<Rectangle> barriers) {
        // Check if the player is hitting any barriers
        for (Rectangle barrier : barriers) {
            if (hitbox.intersects(barrier)) {
                return true; // Player is hitting a barrier
            }
        }
        return false; // Player is not hitting any barriers
    }

    // overload is hitting to check if player is hitting a static rectangle
    public boolean isHitting(Rectangle rect) {
        if (hitbox.intersects(rect))
            return true;
        else
            return false;
    }

    // draw player and animations based on boolean triggers
    public void draw(Graphics g) {
        float drawX = hitbox.getX() - 16;
        float drawY = hitbox.getY() - 10;

        // Draw holding cast frame
        if (holdingcast && stop) {
            cast[dir][3].draw(drawX - 32, drawY - 32);
        }

        // If player is stopped and not casting, draw idle image
        if (stop && stopcast && !holdingcast && !idlebobber) {
            walkAnim[dir].stop();
            idleImage[dir].draw(drawX, drawY);
            // If player is not stopped and not casting, draw walking animation in set
            // direction
        } else if (!stop && stopcast) {
            if (running) {
                if (runAnim[dir].isStopped()) {
                    runAnim[dir].start();
                }
                runAnim[dir].draw(drawX, drawY);
            } else {
                if (walkAnim[dir].isStopped()) {
                    walkAnim[dir].start();
                }
                walkAnim[dir].draw(drawX, drawY);
            }

            // If player is casting, draw casting animation and wait for it to end
        } else if (stop && !stopcast) {
            castAnim[dir].draw(drawX - 32, drawY - 32);
            // If player is idle with the bobber, draw the idle bobber frame
        } else if (idlebobber && stop) {
            if (idlebobber) {
                cast[dir][9].draw(drawX - 32, drawY - 32);
            }
            castAnim[dir].stop();
            walkAnim[dir].stop();
        }

        if (stop && stopcast && !holdingcast && !idlebobber && !casting) {
            walkAnim[dir].stop();
            idleImage[dir].draw(drawX, drawY);
        }

        if (mainGame.debug) {
            g.draw(hitbox);
        }
    }

    public int getDir() {
        return dir;
    }

    public int getX() {
        return (int) hitbox.getX();
    }

    public int getY() {
        return (int) hitbox.getY();
    }

    public void setPos(int newX, int newY) {
        if (newX == -1)
            hitbox.setX(hitbox.getX());
        else
            hitbox.setX(newX);

        if (newY == -1)
            hitbox.setY(hitbox.getY());
        else
            hitbox.setY(newY);
    }
}
