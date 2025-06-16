import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class IntroScreen extends BasicGameState {
    //declare image
    Image introImage;

    //initialize image
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        introImage = new Image("data/assets/images/intro.png");
    }

    //checl for inputs, set up debugging flags and states
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input in = gc.getInput();
        if (in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) { //default game run
            mainGame.debug = false;
            sbg.enterState(1);
        }
        if (in.isKeyPressed(Input.KEY_BACKSLASH)) { // run game in debug mode
            mainGame.debug = true;
            sbg.enterState(20);
        }

        if (in.isKeyPressed(Input.KEY_PERIOD)) //cut right to game over screen
            sbg.enterState(22);

    }

    //draw intro image
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawImage(introImage, 0, 0);
    }

    //return state ID
    public int getID() {
        return 0;
    }

}