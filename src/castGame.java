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
import org.newdawn.slick.geom.Rectangle;

@SuppressWarnings({"unused"})

public class castGame {
    boolean holdingCast = false;
    boolean castHoldLatch=false;
    int x, y;
    float ticker = 50;
    Rectangle castBarBg = new Rectangle(0, 0, 96, 24);
    Rectangle castBar=new Rectangle(0,0,ticker,20);
    int tickerDir=1;
    float tickerpercent=0.0f;

    public castGame() {
        this.x = mainGame.player.getX() - 32;
        this.y = mainGame.player.getY() - 32;
        ticker = 0;
    }

    public void update() {
    boolean prevHoldingCast = holdingCast;
    holdingCast = mainGame.player.holdingcast;
    x = mainGame.player.getX() - 32;
    y = mainGame.player.getY() - 32;

    // Reset ticker and direction when cast starts
    if (holdingCast && !prevHoldingCast) {
        ticker = 0;
        tickerDir = 1;
    }
}

    public void drawGame(Graphics g) throws SlickException {
        if (holdingCast) {
            ticker+=tickerDir;
            tickerpercent=(ticker/92.0f)*100f;
            if(ticker>=92 || ticker<=0){
                tickerDir=tickerDir*-1; //reverse direction of ticker when it reaches max or min
            }
            castBar.setWidth(ticker);
            castBarBg.setX(mainGame.player.getX()-32);
            castBarBg.setY(mainGame.player.getY()-32);

            castBar.setX(castBarBg.getX()+2);
            castBar.setY(castBarBg.getY()+2);
            
            g.setColor(new Color(139, 69, 19)); // brown
            g.fill(castBarBg);
            g.draw(castBarBg);

            g.setColor(Color.green);
            g.fill(castBar);
            g.draw(castBar);

        }else{
            ticker=0;
        }

    }

    //public float getCastVelocity() {
        //returns velocity as a float based on the percent the ticker has progressed the cast game bar
        /*if (ticker < 100) {
            return ticker / 100f * 50; // Example: max velocity of 50
        } else {
            return 50; // Default max velocity if ticker exceeds 100
        }*/
       // return 35+mainGame.player.varyDist; // Placeholder for cast velocity, can be adjusted or calculated based on game logic
    //}
}