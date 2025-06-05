import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Input;
import org.newdawn.slick.Image;

public class House extends BasicGameState {
   map map;
   NonPlayerCharacter shopkeep;
   Image npcSheet;

   public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
      map = new map("data/assets/images/insideMap.png");
      npcSheet = new Image("data/assets/images/npc_walk.png");
      shopkeep = new NonPlayerCharacter(npcSheet, 256, 192, 2);
      map.initKeyPointsInside();
   }

   public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
      Input in = gc.getInput();
      mainGame.player.move(in, map.getBarriers()); // move player based on input and barriers from map

      if(mainGame.player.isHitting(map.getKeyPoints()) && in.isKeyPressed(Input.KEY_E)){
         mainGame.player.setPos(962,136);
         sbg.enterState(20);
      }
   }

   public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
      map.draw();
      if (mainGame.debug && !(mainGame.debugCode == 1)) { // draw grid if debug mode is active
         map.grid(g);
      }

      shopkeep.drawNPC(g);
      mainGame.player.draw(g);
      map.showKeyPoints(g);
   }

   public int getID() {
      return 21; // this id will be different for each screen
   }

}