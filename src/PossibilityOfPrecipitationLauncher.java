

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;


public class PossibilityOfPrecipitationLauncher extends StateBasedGame {

    public PossibilityOfPrecipitationLauncher(String title) {
        super(title);
    }
    
    public void initStatesList(GameContainer gc) throws SlickException {
       this.addState(new IntroScreen());
    }

    public static void main(String args[]) throws SlickException {
        
        PossibilityOfPrecipitationLauncher game = new PossibilityOfPrecipitationLauncher("Testing Game");
        AppGameContainer app = new AppGameContainer(game);
        app.setDisplayMode(800, 600, false);
        app.setShowFPS(false);
        app.setTargetFrameRate(100);
        app.start();
    }

}