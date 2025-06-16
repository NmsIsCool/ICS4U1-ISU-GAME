import java.awt.Dimension;
import java.awt.Toolkit;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class GameInit extends StateBasedGame {

    //declare screen properties
    public static final int SCREEN_WIDTH = 1920;
    public static final int SCREEN_HEIGHT = 1040;
    public static final int FRAME_RATE = 200;

    public GameInit(String title) {
        super(title);
    }

    //initialize game states
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new IntroScreen()); // 0
        this.addState(new mainGame()); // 20
        this.addState(new Instructions_Gameplay()); // 1
        this.addState(new House()); // 21
        this.addState(new goi()); // 22
    }

    //start gameContainer app
    public static void main(String args[]) throws SlickException {

        GameInit game = new GameInit("Fish Frenzy");
        AppGameContainer app = new AppGameContainer(game);
        app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
        app.setShowFPS(true);
        app.setTargetFrameRate(FRAME_RATE);

        app.start();

    }

    @Deprecated //deprecated get screen size methods
    public static int getDisplayDimensionsX() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize.width;
    }

    @Deprecated
    public static int getDisplayDimensionsY() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize.height;
    }

}
//  *******press F5 to play*******