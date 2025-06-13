import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;

public class GameOver extends BasicGameState {
    public static Image gameOverScreen;
    public static int finalScore=mainGame.score;
    public static int scoreHung=mainGame.estimScore;
    public static int totalFish=mainGame.cumulativeFish;

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        gameOverScreen=new Image("data/assets/images/gameOverImage.png");
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException { 

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
       
    }
    
    public int getID() {
       return 22;  //this id will be different for each screen
    }

    
}