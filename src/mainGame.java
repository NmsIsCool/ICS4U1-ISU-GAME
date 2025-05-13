import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class mainGame extends BasicGameState {

    public static boolean debug=false;
    Fisher player;
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
       player=new Fisher(960,540);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException { 
        Input in=gc.getInput();
        if(in.isKeyDown(Input.KEY_SPACE)){
            
        }
        player.move(in);
         
        
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
       player.draw();
       g.setColor(Color.green);
       if(debug) g.drawString("DEBUG MODE", 10,50);
    }
    
    public int getID() {
       return 20;  //this id will be different for each screen
    }

    
}