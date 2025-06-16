import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;

@SuppressWarnings("deprecation")
public class NonPlayerCharacter {
    SpriteSheet sheet;
    int x, y;
    int dir; // 0=U, 1=L, 2=D, 3=R
    Image npcIdle[] = new Image[4];
    Rectangle interactbox;
    boolean nearMe;
    Rectangle dialogBox;
    boolean dialogActive;
    long dialogActivatedTime = 0;
    static final long DIALOG_MIN_DURATION = 500;
    TrueTypeFont diaFont = new TrueTypeFont(new java.awt.Font("Impact", 1, 20), true);

    // NPC Conctructor, create NPC and idle array from sprite sheets
    public NonPlayerCharacter(Image npcSheet, int x, int y, int dir, boolean nearMe) throws SlickException {
        // Create an NPC with a sprite sheet, position, and direction
        sheet = new SpriteSheet(npcSheet, 64, 64);

        sheet.startUse();
        for (int i = 0; i < 4; i++) {
            npcIdle[i] = sheet.getSubImage(0, i);
        }

        interactbox = new Rectangle(x - 48, y, 96 - 24, 96);
        dialogBox = new Rectangle(704-64, 704, 64 * 9, 64 * 3);
        this.dir = dir;
    }

    // draw the npc with the needed spritesheet frame
    public void drawNPC(Graphics g) throws SlickException {
        // Draw hitbox if debugging is active
        if (mainGame.debug && !(mainGame.debugCode == 1)) {
            g.setColor(Color.green);
            g.draw(interactbox);
        }
        // draw NPC at set position and direction
        npcIdle[dir].draw(interactbox.getX(), interactbox.getY());
    }

    // render additional NPC elements like dialog
    public void render(Input in, Graphics g) {

        if (nearMe && in.isKeyDown(Input.KEY_E)) {
            dialogActive = true;
            dialogActivatedTime = System.currentTimeMillis();
            mainGame.debugOutput("IsDiaActive? "+dialogActive);
        }
        boolean canClose = dialogActive && (System.currentTimeMillis() - dialogActivatedTime >= DIALOG_MIN_DURATION);

        if (dialogActive && canClose
                && (in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) || !nearMe)) {
            dialogActive = false;
        }

        if (dialogActive) {
            g.setColor(new Color(0x7B, 0x3F, 0x00));
            g.fill(dialogBox);
            g.draw(dialogBox);
            g.setColor(Color.black);

            diaFont.drawString(729, 729, "Lets see what you have here...");
            if (mainGame.fishOnHand > 0) {
                diaFont.drawString(665, 729 + 30, "You got " + mainGame.fishOnHand + " fish... not bad...");
                diaFont.drawString(665, 729 + 60, "Well... add that to this... multiply by the multiplier...");
                diaFont.drawString(665, 729 + 90, "Carry the one... aaaaand....");
                diaFont.drawString(655, 729 + 120, "That total to " + mainGame.estimScore + " Score. good work.");
                diaFont.drawString(655, 729 + 150, "Now get back out there. Catch more fish...");

                mainGame.score += mainGame.estimScore;
                mainGame.fishOnHand = 0;
                mainGame.multiplier = 1;
                mainGame.estimScore = 0;

            } else
                diaFont.drawString(729, 729 + 30, "Zero fish eh? get back out there and catch some, will ya?");

        }
    }

    public Rectangle getInteractBox() {
        // Return the interaction box of the NPC
        return interactbox;
    }
}
