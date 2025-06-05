import java.awt.Dimension;
import java.awt.Toolkit;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class GameInit extends StateBasedGame {

    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 576;

    public GameInit(String title) {
        super(title);
    }

    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new IntroScreen()); // 0
        this.addState(new mainGame()); // 20
        this.addState(new Instructions_Gameplay()); // 1
        this.addState(new Instructions_Objective()); // 2
        this.addState(new House()); //21
    }

    public static void main(String args[]) throws SlickException {

        GameInit game = new GameInit("Fosh");
        AppGameContainer app = new AppGameContainer(game);
        app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
        app.setShowFPS(true);
        app.setTargetFrameRate(200);
        app.setIcons(new String[] {
                "data/assets/images/icon16.png",
                "data/assets/images/icon.png",
        });
        app.start();

    }

    public static int getDisplayDimensionsX() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize.width;
    }

    public static int getDisplayDimensionsY() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize.height;
    }


}