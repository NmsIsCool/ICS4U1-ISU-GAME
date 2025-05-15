import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Instructions_Objective extends BasicGameState {
    private Image OBJECTIVE_INSTRUCTIONS;

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        OBJECTIVE_INSTRUCTIONS = new Image("data/assets/Instructions_Objective.png");
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input in = gc.getInput();
        if (in.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            sbg.enterState(20); // enter main game
        }
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawImage(OBJECTIVE_INSTRUCTIONS, 0, 0);
    }

    public int getID() {
        return 2; // this id will be different for each screen
    }

}