import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class IntroScreen extends BasicGameState {

    Fisher fisher;
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        fisher=new Fisher(100,100);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException { 
        Input in=gc.getInput();
        fisher.move(in);
        
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
       fisher.draw();
    }
    
    public int getID() {
       return 0;  //this id will be different for each screen
    }

    
}