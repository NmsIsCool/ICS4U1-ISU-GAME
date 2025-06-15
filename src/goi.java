import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;


@SuppressWarnings("deprecation")
public class goi extends BasicGameState {
 
  static TrueTypeFont FontTitle = new TrueTypeFont(new java.awt.Font("Serif", java.awt.Font.BOLD, 48), true);
  static TrueTypeFont FontNorm = new TrueTypeFont(new java.awt.Font("Serif", java.awt.Font.BOLD, 24), true);

  public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    
  }

  public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {

  }

  public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    g.setColor(Color.red);
    FontTitle.drawString((1920-FontTitle.getWidth("TIME'S UP - GAME OVER"))/2,200,"TIME'S UP - GAME OVER", Color.red);
    FontNorm.drawString(100, 400, "You Caught: "+)
  }



  public int getID() {
    return 22; // this id will be different for each screen
  }

}
