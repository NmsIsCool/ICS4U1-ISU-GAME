import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Sound;
import org.newdawn.slick.Music;
import org.newdawn.slick.*;


@SuppressWarnings({"unused", "deprecation"})
public class map {
    private TrueTypeFont ttf=new TrueTypeFont(new java.awt.Font("Arial", 1, 12),true);
    Image mapImage;

    public map() throws SlickException {
        mapImage = new Image("data/assets/images/map.png");
    }

    public void draw() throws SlickException {
        mapImage.draw(0,0);
    }

    
    public void grid(Graphics g) throws SlickException {
        g.setColor(Color.red);
        
        for(int i=0; i<64*16; i+=64){
            for(int j=0; j<64*9; j+=64){
                ttf.drawString(i+3, j+3, ""+i, Color.white);
                ttf.drawString(i+3, j+12, ""+j, Color.white);
                g.drawRect(i,j,64,64);
            }
        }
    }

}
    


