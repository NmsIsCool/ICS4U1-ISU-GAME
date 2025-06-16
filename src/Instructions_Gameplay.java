import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

public class Instructions_Gameplay extends BasicGameState {
    //declare variables and Image object
    private Image GAMEPLAY_INSTRUCTIONS;
    private int timeElapsed = 0;
    private static final int TIME_LIMIT = 1000; // 1 second

    //initialize image and set time elapsed counter
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        GAMEPLAY_INSTRUCTIONS = new Image("data/assets/images/Instructions.png");
        timeElapsed = 0; // Reset time elapsed
    }

    //update timing and read input for instructions click through
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        timeElapsed += i;
        Input in = gc.getInput();
        if (in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && timeElapsed > TIME_LIMIT) {
            sbg.enterState(20); // enter game
        }
    }

    //render instructions image
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawImage(GAMEPLAY_INSTRUCTIONS, 0, 0);

    }

    //return state ID
    public int getID() {
        return 1;
    }

}