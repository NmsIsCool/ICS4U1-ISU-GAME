import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class mainGame extends BasicGameState {

   public static boolean debug = true;
   static Fisher player;
   Bobber bobber;

   //initialize needed objects
   public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
      player = new Fisher(64 * 8, (int) (64 * 4.5));
      bobber = new Bobber(800, 200, 0, 0);

   }

   //run updates and check inputs every frame
   public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
      Input in = gc.getInput();
      player.move(in);

      //get memory while debug mode active for ensuring memory usage is in check. Should rarely be an issue.
      if(in.isKeyDown(Input.KEY_M)){
         getMem();
      }

   }

   //render needed objects every frame
   public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
      player.draw(g);

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

   //return ID for SBG
   public int getID() {
      return 20; // this id will be different for each screen
   }

   //DEBUG METHODS

   //output debug messages to console if debug mode is active
   public static void debugOutput(String output) {
      if (debug) {
         System.out.println(output);
      }
   }

   //output current memory usage to console - should rarely be a problem but its cool
   public static void getMem(){
      if(debug){
      Runtime runtime = Runtime.getRuntime();
      long memory = runtime.totalMemory() - runtime.freeMemory();
      System.out.println("Used memory: " + memory / (1024*1024) + " MB");
      }
   }

   

}