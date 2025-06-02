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

@SuppressWarnings({"unused", "deprecation"})
public class mainGame extends BasicGameState {
   private TrueTypeFont ttf=new TrueTypeFont(new java.awt.Font("Arial", 1, 12),true);
   map map;
   public static boolean debug = true;
   public int debugCode=0; // 0=primary debug, 1=mapOff, noGrid
   static Fisher player;
   Bobber bobber;
   String coords;
   int mouseX, mouseY;
   static castGame castGame;

   // initialize needed objects
   public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
      player = new Fisher(64 * 8, (int) (64 * 4.5));
      bobber = new Bobber(800, 200, 0, 0);
      map = new map();
      Music bgmusic =new Music("data/assets/audio/waves.wav");
      bgmusic.loop(1.0f, 0.5f); //loop background music at 50% volume
      castGame=new castGame();
   }

   // run updates and check inputs every frame
   public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
      Input in = gc.getInput();
      player.move(in);

      // get memory while debug mode active for ensuring memory usage is in check.
      // Should rarely be an issue.
      if (in.isKeyDown(Input.KEY_M)) {
         getMem();
      }
      castGame.update();
   }

   // render needed objects every frame
   public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
      Input input=gc.getInput();
      mouseX = input.getMouseX();
      mouseY = input.getMouseY();
      
      if(!(debugCode==1))
         map.draw();
      if (debug && !(debugCode==1))
         map.grid(g); // draw grid for debugging

      if (player.casting) {
         bobber.draw(g);
         bobber.calculateCastDist();
      } else if (player.idlebobber) {
         bobber.draw(g);
      }

      // if debug mode is active, draw a message on screen
      g.setColor(Color.blue);
      if (debug) {
         g.drawString("DEBUG MODE " + debugCode, 10, 50);
         ttf.drawString(10, 70, getMem() + " MB used",Color.blue);
         coords = "(" + mouseX + ", " + mouseY + ")";
         g.setColor(Color.blue);
         g.drawString(coords, mouseX + 10, mouseY - 10);
      }
      g.setColor(Color.white);
      castGame.drawGame(g);
      player.draw(g);

   }

   // return ID for SBG
   public int getID() {
      return 20; // this id will be different for each screen
   }

   // DEBUG METHODS

   // output debug messages to console if debug mode is active
   public static void debugOutput(String output) {
      if (debug) {
         System.out.println(output);
      }
   }

   // output current memory usage to console - should rarely be a problem but its
   // cool
   public static double getMem() {
      if (debug) {
         Runtime runtime = Runtime.getRuntime();
         long memory = runtime.totalMemory() - runtime.freeMemory();
         return (memory/1024/1024); // return memory in MB
      }
      return 0;
   }

}