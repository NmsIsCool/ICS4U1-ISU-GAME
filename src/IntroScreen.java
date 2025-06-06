import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class IntroScreen extends BasicGameState {

    Image introImage;
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        introImage=new Image("data/assets/images/intro.png");
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException { 
       Input in=gc.getInput();
       if(in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
            mainGame.debug=false;
           sbg.enterState(1);
       }
       if(in.isKeyPressed(Input.KEY_BACKSLASH)){
            mainGame.debug=true;
            sbg.enterState(1);
       }
        
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawImage(introImage,0,0);
    }
    
    public int getID() {
       return 0;  //this id will be different for each screen
    }

    
}