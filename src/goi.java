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
    FontTitle.drawString((1920-FontTitle.getWidth("TIME'S UP - GAME OVER"))/2,200,"TIME'S UP - GAME OVER");
    FontNorm.drawString(100, 400, "You had a score of: "+mainGame.score);
    FontNorm.drawString(100, 500, "You Caught: "+ mainGame.cumulativeFish+" Fish!");

    if(mainGame.fishOnHand>0)
      FontNorm.drawString(100,600, "You Stranded: " + mainGame.fishOnHand+" Fish, Which left: " + mainGame.estimScore+" Points on the table!");
    else
      FontNorm.drawString(100,600, "You Stranded: " + mainGame.fishOnHand+" Fish! Good work!");
    FontNorm.drawString((1920-FontNorm.getWidth("Thanks for playing!"))/2, 750, "Thanks for playing!");

  }



  public int getID() {
    return 22; // this id will be different for each screen
  }

}
