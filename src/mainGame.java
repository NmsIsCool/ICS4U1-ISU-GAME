import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class mainGame extends BasicGameState {

   public static boolean debug = false;
   static Fisher player;
   Bobber bobber;

   public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
      player = new Fisher(64 * 8, (int) (64 * 4.5));
      bobber = new Bobber(800, 200, 0, 0);

   }

   public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
      Input in = gc.getInput();
      player.move(in);

      //get memory while debug mode active for ensuring memory usage is in check. Should rarely be an issue.
      if(in.isKeyDown(Input.KEY_M) && debug){
         getMem();
      }

   }

   public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
      player.draw();

      if (player.casting) {
         bobber.draw(g);
         bobber.calculateCastDist();
      }else if(player.idlebobber){
         bobber.draw(g);
      }

   
      /*if (bobber.vel != 0)
            bobber.calculateCastDist();*/

      //if debug mode is active, draw a message on screen
      g.setColor(Color.green);
      if (debug) {
         g.drawString("DEBUG MODE", 10, 50);
      }
      g.setColor(Color.white);

   }

   public int getID() {
      return 20; // this id will be different for each screen
   }

   //debug methods
   public static void debugOutput(String output) {
      if (debug) {
         System.out.println(output);
      }
   }

   public static void getMem(){
      Runtime runtime = Runtime.getRuntime();
      long memory = runtime.totalMemory() - runtime.freeMemory();
      System.out.println("Used memory: " + memory / (1024*1024) + " MB");
   }

   

}