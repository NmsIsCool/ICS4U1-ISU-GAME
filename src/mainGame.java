import java.util.ArrayList;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Music;
import org.newdawn.slick.Image;
import java.util.Random;

@SuppressWarnings({ "deprecation" }) // stop compiler from throwing warnings for deprecated libraries
public class mainGame extends BasicGameState {
   // Fonts
   private TrueTypeFont debugFont = new TrueTypeFont(new java.awt.Font("Arial", 1, 12), true);
   static TrueTypeFont ttf = new TrueTypeFont(new java.awt.Font("Arial", 3, 16), true);

   // Game objects
   static Fisher player;
   static Bobber bobber;
   static castGame castGame;
   static map map;
   static Image cue;
   static Fish fosh;

   // Collections
   ArrayList<Rectangle> barriers = new ArrayList<>(); // movement restricting barriers

   // Primitive types
   public static boolean debug = true;
   public static int debugCode = 0; // 0=primary debug, 1=mapOff, noGrid
   private String coords;
   public static int mouseX, mouseY;
   public static float castScore = 0;
   static int ticker = 0;
   static int secondsElapsed = 0;
   static int currentFishType;
   static int fishTimer;
   static int globalTimer;
   static boolean waitingFish = false;
   static boolean fishTimerLatch = false;
   static boolean enterFishMiniGame = false;
   private static boolean shakeActive = false;
   private static long shakeEndTime = 0;
   private static final int SHAKE_AMOUNT = 8; // pixels
   private static int shakeOffsetX = 0;
   private static int shakeOffsetY = 0;

   // initialize needed objects
   public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
      player = new Fisher(960, 180);
      bobber = new Bobber();
      map = new map("data/assets/images/map.png");
      map.initBarriersOutside(); // load barriers and key points
      map.initKeyPointsOutside();
      barriers = map.getBarriers(); // get barriers from map
      Music bgmusic = new Music("data/assets/audio/waves.wav");
      if (!debug)
         bgmusic.loop(1.0f, 0.5f); // loop background music at 50% volume if not in debug
      castGame = new castGame();
      cue = new Image("data/assets/images/VisualCueReal.png");
      fosh = new Fish();
   }

   // run updates and check inputs every frame
   public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
      Input in = gc.getInput();
      player.move(in, barriers);

      // get memory while debug mode active for ensuring memory usage is in check.
      // Should rarely be an issue.
      castGame.update();

      if (player.isHitting(map.getKeyPoints()) && in.isKeyPressed(Input.KEY_E)) {
         player.setPos(896 + 32, 640 - 48);
         sbg.enterState(21);
      }
   }

   // render needed objects every frame
   public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
      if (shakeActive) {
         if (System.currentTimeMillis() > shakeEndTime) {
            shakeActive = false;
            shakeOffsetX = 0;
            shakeOffsetY = 0;
         } else {
            shakeOffsetX = (int) (Math.random() * SHAKE_AMOUNT * 2) - SHAKE_AMOUNT;
            shakeOffsetY = (int) (Math.random() * SHAKE_AMOUNT * 2) - SHAKE_AMOUNT;
         }
      } else {
         shakeOffsetX = 0;
         shakeOffsetY = 0;
      }

      g.translate(shakeOffsetX, shakeOffsetY);

      ticker++;

      Input input = gc.getInput();
      mouseX = input.getMouseX();
      mouseY = input.getMouseY();

      if (!(debugCode == 1))
         map.draw();
      if (debug && !(debugCode == 1)) {
         map.grid(g);
         map.showBarriers(g);
         map.showKeyPoints(g);
         // draw grid for debugging when not in code one
      }

      if (player.isHitting(map.getKeyPoints()))
         ttf.drawString(player.getX() - 48, player.getY() - 24, "Press E to Interact", Color.black);

      if (player.casting) {
         bobber.draw(g);
      } else if (player.idlebobber) {
         bobber.draw(g);
         if(fishTimer!=0)
            Fish.playDistraction(g);

         if (!fishTimerLatch) {
            castScore = bobber.getQualityScore();
            Fish.distractionActive = false;
            currentFishType = Fish.getFishType(castScore);
            debugOutput("Current Fish: " + currentFishType);
            fishTimer = currentFishType * 3 + (int) Math.random() * 5;
            fishTimerLatch = true;

            debugOutput("Cast Score: " + castScore);
         }
      }

      if (ticker % 200 == 0) {
         secondsElapsed++;
         if (!(fishTimer == 0) && (player.idlebobber)) {
            fishTimer--;
            debugOutput("Fish Timer: " +fishTimer);
         }
         
      }

      // if debug mode is active, draw a message on screen
      g.setColor(Color.blue);
      if (debug) {
         debugFont.drawString(10, 50, "DEBUG MODE " + debugCode, Color.blue);
         debugFont.drawString(10, 70, getMem() + " MB used", Color.blue);
         coords = "(" + mouseX + ", " + mouseY + ")";
         g.setColor(Color.blue);
         g.drawString(coords, mouseX + 10, mouseY - 10);
      }
      g.setColor(Color.white);
      castGame.drawGame(g);

      player.draw(g);
      g.resetTransform(); // Reset translation after drawing

   }

   public static void triggerScreenShake() {
      Random r=new Random();
      shakeActive = true;
      shakeEndTime = System.currentTimeMillis()+ 500 + r.nextInt(1000);
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
         return (memory / 1024 / 1024); // return memory in MB
      }
      return 0;
   }

}