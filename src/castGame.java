import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Music;

@SuppressWarnings({"unused"})

public class castGame {
    boolean holdingCast = false;
    boolean castHoldLatch=false;
    int x, y;
    int ticker = 0;

    public castGame() {
        this.x = mainGame.player.getX() - 32;
        this.y = mainGame.player.getY() - 32;
        ticker = 0;
    }

    public void update() {
        holdingCast = mainGame.player.holdingcast;
        x = mainGame.player.getX() - 32;
        y = mainGame.player.getY() - 32;
    }

    public void drawGame(Graphics g) throws SlickException {
        if (holdingCast) {
            castHoldLatch=true;
            g.drawRect(x, y, 96, 24);

        }else{
            castHoldLatch=false;
            ticker=0;
        }

    }

    public float getCastVelocity() {
        //returns velocity as a float based on the percent the ticker has progressed the cast game bar
        /*if (ticker < 100) {
            return ticker / 100f * 50; // Example: max velocity of 50
        } else {
            return 50; // Default max velocity if ticker exceeds 100
        }*/
        return 35+mainGame.player.varyDist; // Placeholder for cast velocity, can be adjusted or calculated based on game logic
    }
}