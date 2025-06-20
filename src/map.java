import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import java.util.ArrayList;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

@SuppressWarnings({ "deprecation" })
public class map {
    //declare and init collections/objects
    ArrayList<Rectangle> barriers = new ArrayList<>(); // Movement restricting barriers
    ArrayList<Rectangle> keyPoints = new ArrayList<>(); // key points for screen transition points
    private TrueTypeFont gridFont = new TrueTypeFont(new java.awt.Font("Arial", 1, 12), true);
    Image mapImage;

    //map constructor, sets map image to supplied path
    public map(String mapPath) throws SlickException {
        mapImage = new Image(mapPath);
    }

    //initialize outside barriers
    public void initBarriersOutside() {
        if (!(mainGame.debugCode == 1)) {// create barriers unless debug code 1 is active

            // terrain barriers
            makeBarrier(0, 448, 64 * 3, 64);
            makeBarrier(128, 448, 64, 64 * 5);
            makeBarrier(128, 704, 64 * 4, 64);
            makeBarrier(320, 512, 64, 64 * 4);
            makeBarrier(256, 512, 64 * 2, 32);
            makeBarrier(256, 448, 64 * 4, 64);
            makeBarrier(448, 384, 64 * 3, 64);
            makeBarrier(576, 320, 64 * 3, 64);
            makeBarrier(704, 384, 64 * 4, 64);
            makeBarrier(896, 448, 64 * 4, 64);
            makeBarrier(1088, 448, 64, 64 * 6);
            makeBarrier(1088, 768, 64 * 3, 64);
            makeBarrier(1152, 736, 64, 32);
            makeBarrier(1216, 512, 64, 64 * 5);
            makeBarrier(1216, 512, 64 * 4, 64);
            makeBarrier(1408, 576, 64 * 6, 64);
            makeBarrier(1728, 512, 64 * 3, 64);
            makeBarrier(1856, 448, 64, 64);

            // world geom barriers

            // fish shop
            makeBarrier(75, 90, 110, 120);
            // scarecrow
            makeBarrier(1016, 175, 84, 75);

            // Misc Houses
            int deltax = 100; //size of each house
            int deltay = 105;
            makeBarrier(1550, 218, deltax, deltay);
            makeBarrier(1550, 25, deltax, deltay);
            makeBarrier(1675, 25, deltax, deltay);
            makeBarrier(1675, 218, deltax, deltay);
        }
    }

    //initialize state transition points for the outside
    public void initKeyPointsOutside() {
        if (!(mainGame.debugCode == 1)) {// create barriers unless debug code 1 is active
            makeKey(75, 210, 110, 20);
        }
    }

    //initialize barriers for inside
    public void initBarriersInside() {
        if (!(mainGame.debugCode == 1)) {// create barriers unless debug code 1 is active
            makeBarrier(576, 192, 64 * 11, 64 * 3);
            makeBarrier(576, 192, 64, 64 * 8);
            makeBarrier(1216, 192, 64, 64 * 8);
            makeBarrier(576, 672, 64 * 7.5f, 32);
            makeBarrier(1024, 672, 64 * 5, 32);

        }
    }

    //initialize state transtition points for the inside
    public void initKeyPointsInside() {
        if (!(mainGame.debugCode == 1)) {// create barriers unless debug code 1 is active
            makeKey(832, 640, 64 * 3, 64);
        }
    }

    //draw map image
    public void draw() throws SlickException {
        mapImage.draw(0, 0);
    }

    //overload draw to allow for specified draw positions, used in House.java
    public void draw(int x, int y) throws SlickException {
        mapImage.draw(x, y);
    }

    //draw position grids while debugging
    public void grid(Graphics g) throws SlickException {
        g.setColor(Color.red);

        for (int i = 0; i < 64 * 30; i += 64) {
            for (int j = 0; j < 64 * 17; j += 64) {
                gridFont.drawString(i + 3, j + 3, "" + i, Color.white);
                gridFont.drawString(i + 3, j + 12, "" + j, Color.white);
                g.drawRect(i, j, 64, 64);
            }
        }
    }

    //draw the barriers while debugging
    public void showBarriers(Graphics g) {
        if (mainGame.debug && !(mainGame.debugCode == 1)) {
            g.setColor(Color.blue);
            for (Rectangle barrier : barriers) {
                g.draw(barrier);
            }
        }
    }

    //render state transition points
    public void showKeyPoints(Graphics g) {
        if (mainGame.debug && !(mainGame.debugCode == 1)) {
            g.setColor(Color.yellow);
            for (Rectangle keyPoint : keyPoints) {
                g.draw(keyPoint);
            }
        }
    }

    // i was so tired of writing the full thing so i just made a smaller method for
    // it
    public void makeBarrier(int x, int y, int width, int height) {

        barriers.add(new Rectangle(x, y, width, height));
    }

    //overload makeBarrier to allow for float input
    public void makeBarrier(float x, float y, float width, float height) {

        barriers.add(new Rectangle(x, y, width, height));
    }

    // i was so tired of writing the full thing so i just made a smaller method for
    // it
    public void makeKey(int x, int y, int width, int height) {

        keyPoints.add(new Rectangle(x, y, width, height));
    }

    //return collision barriers
    public ArrayList<Rectangle> getBarriers() {
        return barriers;
    }

    //return state transition points
    public ArrayList<Rectangle> getKeyPoints() {
        return keyPoints;
    }

}
