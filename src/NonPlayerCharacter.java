import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class NonPlayerCharacter {
    SpriteSheet sheet;
    int x, y;
    int dir; // 0=U, 1=L, 2=D, 3=R
    Image npcIdle[]=new Image[4];
    Rectangle interactbox;

    //NPC Conctructor, create NPC and idle array from sprite sheets
    //possible support for walking later
    public NonPlayerCharacter(Image npcSheet, int x, int y, int dir) throws SlickException {
        // Create an NPC with a sprite sheet, position, and direction
        sheet=new SpriteSheet(npcSheet,64,64);

        sheet.startUse();
        for(int i=0; i<4; i++){
            npcIdle[i]=sheet.getSubImage(0,i);
        }

        interactbox=new Rectangle(x-48,y,96-24,96);
        this.dir=dir;
    }

    public void drawNPC(Graphics g) throws SlickException{
        //Draw hitbox if debugging is active
        if(mainGame.debug && !(mainGame.debugCode==1)){
            g.setColor(Color.green);
            g.draw(interactbox);
        }
        //draw NPC at set position and direction
        npcIdle[dir].draw(interactbox.getX(), interactbox.getY());
    }

    public Rectangle getInteractBox() {
        // Return the interaction box of the NPC
        return interactbox;
    }
}
