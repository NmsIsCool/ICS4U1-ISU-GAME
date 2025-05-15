import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

public class Instructions_Gameplay extends BasicGameState {
    private Image GAMEPLAY_INSTRUCTIONS;
    long t = System.nanoTime();
    long dt = 0;

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        GAMEPLAY_INSTRUCTIONS = new Image("data/assets/Instructions_Gameplay.png");

    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input in = gc.getInput();
        long dt = System.nanoTime() - t;
        if (in.isMousePressed(Input.MOUSE_LEFT_BUTTON) && dt > 1000000000L) {
            sbg.enterState(2); // enter next instruction screen
        }
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawImage(GAMEPLAY_INSTRUCTIONS, 0, 0);
        dt = System.nanoTime() - t;
    }

    public int getID() {
        return 1; // this id will be different for each screen
    }

}