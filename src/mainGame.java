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
   static Image fishPopUp;

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
   static int fishTimer = -1; // placeholder value to prevent repeated execution of one time operations
   static int globalTimer;
   static boolean waitingFish = false;
   static boolean fishTimerLatch = false;
   static boolean enterFishMiniGame = false;
   private static boolean shakeActive = false;
   private static long shakeEndTime = 0;
   private static final int SHAKE_AMOUNT = 8; // pixel to translate everything by during screen shake
   private static int shakeOffsetX = 0;
   private static int shakeOffsetY = 0;
   public static boolean fishOnLine = false;
   public static long cueStart = 0;
   public static int fishOnHand = 0;
   public static int score = 0;
   public static double multiplier = 1; // multiplier will be 1/10 of current fish on hand0
   public static int estimScore = 0;
   public static float fishAlpha = 1f;
   public static boolean showFishPopUp = false;
   private static long fishPopUpStartTime = 0;
   private static final long FISH_POPUP_DURATION = 1000; // 1 second
   public static boolean stoppedBobber = false;
   public static long elapsedMillis = 0;
   public static final long TIME_LIMIT_MILLIS = 180000;
   public static long timeLeft = 180;
   public static int cumulativeFish = 0;
   public static long gameStartTime = 0;
   public static boolean isFirstEnter = true;

   // store fish types
   public static int trash = 0;
   public static int minor = 0;
   public static int mediocre = 0;
   public static int large = 0;
   public static int mythic = 0;
   public static int rng = 0;

   //method to control timer on game startup, only runs when state is first entered
   @Override
   public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
      if (isFirstEnter)
         gameStartTime = System.currentTimeMillis();
      isFirstEnter = false;
   }

   // initialize needed objects and call core methods
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
      fishPopUp = new Image("data/assets/images/icon.png");
   }

   // run updates and check inputs every frame
   public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
      Input in = gc.getInput();
      player.move(in, barriers);
      castGame.update();

      if (player.isHitting(map.getKeyPoints()) && in.isKeyPressed(Input.KEY_E)) {
         player.setPos(896 + 32, 640 - 48);
         sbg.enterState(21);
      }
   }
   //method to track time globally while in other states
   public static void trackGlobalTime() {
      elapsedMillis = System.currentTimeMillis() - gameStartTime;
      timeLeft = TIME_LIMIT_MILLIS - elapsedMillis;
      ttf.drawString(10, 90, "Time Remaining: " + timeLeft / 1000 + "s");
   }

   //display score in other states
   public static void dispScore() {
      ttf.drawString(10, 110, "Current Score: " + score);
   }

   // render needed objects every frame
   public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

      if (elapsedMillis >= TIME_LIMIT_MILLIS) {
         sbg.enterState(22);
      }
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
         if (fishTimer != 0)
            Fish.playDistraction(g);

         if (!fishTimerLatch) {
            Fish.catchCueAudioLatch = true;
            castScore = bobber.getQualityScore();
            Fish.distractionActive = false;
            currentFishType = Fish.getFishType(castScore);
            debugOutput("Current Fish: " + currentFishType);
            fishTimer = (currentFishType + 1) * 3 + (int) Math.random() * 5;
            fishTimerLatch = true;

            debugOutput("Cast Score: " + castScore);
         }
      }

      if (ticker % 200 == 0) {
         secondsElapsed++;
         if (!(fishTimer == 0) && (player.idlebobber)) {
            fishTimer--;
            debugOutput("Fish Timer: " + fishTimer);
         }

      }
      Fish.catchFish(g);

      if (fishOnLine)
         debugOutput("Fish Window");
      if ((input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && fishOnLine) || (stoppedBobber && fishOnLine)) { // Logic controlling when a
                                                                                            // fish is caught
         debugOutput("Caught A Fish!");
         fishOnLine = false;
         fishOnHand++;
         cumulativeFish++;
         showFishPopUp = true;
         fishPopUpStartTime = System.currentTimeMillis();

         multiplier += fishOnHand / 10;
         switch (currentFishType) { // add numbers of each fish type for score purposes
            case 0:
               trash++;
               estimScore += multiplier * 1;
               break;
            case 1:
               minor++;
               estimScore += multiplier * 3;
               break;
            case 2:
               mediocre++;
               estimScore += multiplier * 5;
               break;
            case 3:
               large++;
               estimScore += multiplier * 10;
               break;
            case 4:
               mythic++;
               estimScore += multiplier * 15;
               break;
            case 5:
               rng++;
               estimScore += multiplier * 25;
               break;
            default:
               trash++;
               estimScore += multiplier * 1;
               break;
         }
         /*
          * SCORE TABLE
          * trash - +1
          * minor - +3
          * mediocre - +5
          * large - +10
          * mythic - +15
          * RNGesus - +25
          */
         stoppedBobber = false;
      }
      //logic controlling fish popup when fish caught
      if (showFishPopUp) {
         long elapsed = System.currentTimeMillis() - fishPopUpStartTime;
         float alpha = 1.0f - (float) elapsed / FISH_POPUP_DURATION;
         if (alpha < 0f)
            alpha = 0f;

         g.setColor(new Color(1f, 1f, 1f, alpha));
         g.drawImage(fishPopUp, player.hitbox.getX() - 32, player.hitbox.getY() - 32);
         g.setColor(Color.white);

         if (elapsed >= FISH_POPUP_DURATION) {
            showFishPopUp = false;
         }
      }

      
      if (fishTimer == 0) {
         cueStart = System.currentTimeMillis();
         Fish.fishLineEndTime = cueStart + Fish.FISH_ON_LINE_TIME;
         fishOnLine = true;
         fishTimer = -1; // placeholder value to prevent constant execution
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
      trackGlobalTime();
      dispScore();

   }

   //trigger screen shake when called
   public static void triggerScreenShake() {
      Random r = new Random();
      shakeActive = true;
      shakeEndTime = System.currentTimeMillis() + 500 + r.nextInt(1000);
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