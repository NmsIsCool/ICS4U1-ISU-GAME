import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Input;
import org.newdawn.slick.Image;

public class House extends BasicGameState {
   static map map;
   static NonPlayerCharacter shopkeep;
   Image npcSheet;
   static boolean nearShopkeep;

   //initialize needed objects and collections
   public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
      map = new map("data/assets/images/shop.png");
      npcSheet = new Image("data/assets/images/npc_walk.png");
      shopkeep = new NonPlayerCharacter(npcSheet, 824, 512 - 36, 2, nearShopkeep);
      map.initKeyPointsInside();
      map.initBarriersInside();

   }

   //update core values and read inputs
   public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
      Input in = gc.getInput();
      mainGame.player.move(in, map.getBarriers()); // move player based on input and barriers from map
      if (in.isKeyPressed(Input.KEY_E)) {
         if (mainGame.player.isHitting(map.getKeyPoints())) {
            mainGame.player.setPos(64 + 48, 192 + 20);
            sbg.enterState(20);
         }
      }

   }

   //render game elements and prevent deprecation warnings
   @SuppressWarnings("deprecation")
   public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
      if (mainGame.elapsedMillis >= mainGame.TIME_LIMIT_MILLIS) {
         sbg.enterState(22);
      }
      
      Input in = gc.getInput();
      mainGame.trackGlobalTime();
      mainGame.dispScore();
      map.draw(640, 256);
      if (mainGame.debug && !(mainGame.debugCode == 1)) { // draw grid if debug mode is active
         map.grid(g);
      }
      map.showBarriers(g);
      shopkeep.drawNPC(g);
      shopkeep.render(in, g);
      mainGame.player.draw(g);
      map.showKeyPoints(g);
      if (mainGame.player.isHitting(shopkeep.getInteractBox())) {
         shopkeep.nearMe = true;
         mainGame.debugOutput("isTouching? " + (mainGame.player.isHitting(shopkeep.getInteractBox())));
         mainGame.ttf.drawString(mainGame.player.getX() - 32, mainGame.player.getY() - 24, "Press E to Interact",
               Color.black);
      } else
         shopkeep.nearMe = false;

      if (mainGame.player.isHitting(map.getKeyPoints()))
         mainGame.ttf.drawString(mainGame.player.getX() - 32, mainGame.player.getY() - 24, "Press E to Interact",
               Color.black);

   }

   public int getID() {
      return 21; // this id will be different for each screen
   }

}