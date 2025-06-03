import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import java.util.ArrayList;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

@SuppressWarnings({ "deprecation" })
public class map {
    ArrayList<Rectangle> barriers = new ArrayList<>(); // Movement restricting barriers
    ArrayList<Rectangle> keyPoints = new ArrayList<>(); // key points for screen transition points
    private TrueTypeFont gridFont = new TrueTypeFont(new java.awt.Font("Arial", 1, 12), true);
    Image mapImage;

    public map() throws SlickException {
        mapImage = new Image("data/assets/images/map.png");
    }

    public void initBarriers() {
        if (mainGame.debug && !(mainGame.debugCode == 1)) {// create barriers unless debug code 1 is active
            barriers.add(new Rectangle(0, 192, 64 * 2, 64));
            barriers.add(new Rectangle(64, 256, 64, 64 * 2));
            barriers.add(new Rectangle(128, 320, 64 * 2, 64));
            barriers.add(new Rectangle(192, 128, 64, 64 * 4));
            barriers.add(new Rectangle(192, 128, 64 * 4, 64));
            barriers.add(new Rectangle(384, 192, 64 * 3, 64));
            barriers.add(new Rectangle(512, 256, 64 * 8, 64));
            barriers.add(new Rectangle(128, 288, 64, 32));
            barriers.add(new Rectangle(531, 30, 64 + 24, 36));
            barriers.add(new Rectangle(905, 25, 110, 110));
        }
    }

    public void draw() throws SlickException {
        mapImage.draw(0, 0);
    }

    public void grid(Graphics g) throws SlickException {
        g.setColor(Color.red);

        for (int i = 0; i < 64 * 16; i += 64) {
            for (int j = 0; j < 64 * 9; j += 64) {
                gridFont.drawString(i + 3, j + 3, "" + i, Color.white);
                gridFont.drawString(i + 3, j + 12, "" + j, Color.white);
                g.drawRect(i, j, 64, 64);
            }
        }
    }

    public void showBarriers(Graphics g) {
        g.setColor(Color.blue);
        for (Rectangle barrier : barriers) {
            g.draw(barrier);
        }
    }

    public ArrayList<Rectangle> getBarriers() {
        return barriers;
    }

}
