import java.util.Random;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;

public class Fish {
    public static int qualScore;
    public int fishType=0;

    Image exclamationReal;
    Image exclamationFake;

    Sound biteReal;
    Sound biteFake;
    //0 - trash
    //1 - minor fish
    //2 - mediocre fish
    //3 - large fish
    //4 - mythic fish
    //5 - RNGesus fish

    public Fish(int score) throws SlickException {
        qualScore = score;
        exclamationReal=new Image("data/assets/images/VisualCueReal");
    }

    /*
     * Table to determine quality based off of distance score.
     * 
     * 0-0.39:
     * trash - 100%
     * 
     * 0.4-0.49:
     * minor fish - 75%
     * trash - 25%
     * 
     * 0.5-0.69:
     * mediocre fish - 50%
     * minor fish - 30%
     * trash - 20%
     * 
     * 0.7-0.79:
     * large fish - 40%
     * mediocre fish - 25%
     * minor fish - 20%
     * trash - 15%
     * 
     * 0.8-0.94:
     * large fish - 55%
     * mediocre fish - 35%
     * minor fish - 9%
     * mythic fish - 1%
     * 
     * 0.95 - 1:
     * Mythic fish - 15%
     * large fish - 30%
     * mediocre fish - 45%
     * minor fish - 9%
     * RNGesus fish - 1%
     */

public static int getFishType(float castScore) {
    Random rand = new Random();
    int roll = rand.nextInt(100); // 0-99

    if (castScore >= 0 && castScore < 0.4f) {
        // 0-0.39: trash - 100%
        return 0;
    } else if (castScore >= 0.4f && castScore < 0.5f) {
        // 0.4-0.49: minor fish - 75%, trash - 25%
        if (roll < 75) return 1;
        else return 0;
    } else if (castScore >= 0.5f && castScore < 0.7f) {
        // 0.5-0.69: mediocre fish - 50%, minor fish - 30%, trash - 20%
        if (roll < 50) return 2;
        else if (roll < 80) return 1;
        else return 0;
    } else if (castScore >= 0.7f && castScore < 0.8f) {
        // 0.7-0.79: large fish - 40%, mediocre fish - 25%, minor fish - 20%, trash - 15%
        if (roll < 40) return 3;
        else if (roll < 65) return 2;
        else if (roll < 85) return 1;
        else return 0;
    } else if (castScore >= 0.8f && castScore < 0.95f) {
        // 0.8-0.94: large fish - 55%, mediocre fish - 35%, minor fish - 9%, mythic fish - 1%
        if (roll < 55) return 3;
        else if (roll < 90) return 2;
        else if (roll < 99) return 1;
        else return 4;
    } else if (castScore >= 0.95f && castScore <= 1.0f) {
        // 0.95 - 1: Mythic fish - 15%, large fish - 30%, mediocre fish - 45%, minor fish - 9%, RNGesus fish - 1%
        if (roll < 15) return 4;
        else if (roll < 45) return 3;
        else if (roll < 90) return 2;
        else if (roll < 99) return 1;
        else return 5;
    } else {
        // fallback
        return 0;
    }
}
    public static void playDistraction(){
        Random r=new Random();
        int roll=r.nextInt(4);
        if(roll==1){

        }
    }

    public static void fakeExclamation(Graphics g){

    }

}
