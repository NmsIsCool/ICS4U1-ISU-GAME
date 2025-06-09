import java.util.Random;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

@SuppressWarnings("unused")
public class fishingMiniGame {
    static float qualScore = 0;
    static Random rand = new Random();

    public fishingMiniGame() {
        qualScore = mainGame.castScore;
    }

    // return meanings table
    /*
     1 - trash
     2 - minor fish
     3 - mediocre fish
     4 - large fish
     5 - mythic fish
     6 - RNGesus fish
     */
    public static int getFishType() {
        qualScore = mainGame.castScore;
        int roller = rand.nextInt(100);

        if (qualScore >= 0 && qualScore < 0.4f) {
            // 0-0.39: trash - 100%
            return 1;
        } else if (qualScore >= 0.4f && qualScore < 0.5f) {
            // 0.4-0.49: minor fish - 75%, trash - 25%
            if (roller < 75) return 2;
            else return 1;
        } else if (qualScore >= 0.5f && qualScore < 0.7f) {
            // 0.5-0.69: mediocre fish - 50%, minor fish - 30%, trash - 20%
            if (roller < 50) return 3;
            else if (roller < 80) return 2;
            else return 1;
        } else if (qualScore >= 0.7f && qualScore < 0.8f) {
            // 0.7-0.79: large fish - 40%, mediocre fish - 25%, minor fish - 20%, trash - 15%
            if (roller < 40) return 4;
            else if (roller < 65) return 3;
            else if (roller < 85) return 2;
            else return 1;
        } else if (qualScore >= 0.8f && qualScore < 0.95f) {
            // 0.8-0.94: large fish - 55%, mediocre fish - 35%, minor fish - 9%, mythic fish - 1%
            if (roller < 55) return 4;
            else if (roller < 90) return 3;
            else if (roller < 99) return 2;
            else return 5;
        } else if (qualScore >= 0.95f && qualScore <= 1.0f) {
            // 0.95-1: Mythic fish - 15%, large fish - 30%, mediocre fish - 45%, minor fish - 9%, RNGesus fish - 1%
            if (roller < 15) return 5;
            else if (roller < 45) return 4;
            else if (roller < 90) return 3;
            else if (roller < 99) return 2;
            else return 6;
        } else {
            // fallback, should not happen
            return 1;
        }
    }

    public static void draw(Graphics g){
        if(mainGame.enterFishMiniGame){
            
        }
    }
}