import java.util.Random;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;

public class Fish {

    //declare variables and objects
    public static int qualScore;
    public int fishType = 0;

    static Image exclamationReal;
    static Image exclamationFake;

    static Sound biteReal;
    static Sound biteFake;

    public static boolean distractionActive = false;
    private static long distractionEndTime = 0;
    private static long lastDistractionRoll = 0;
    private static final long DISTRACTION_ROLL_COOLDOWN = 1000;
    private static final long DISTRACTION_PAUSE_AFTER_SHOW = 1000;

    public static final long FISH_ON_LINE_TIME = 1500; // 1000ms
    public static long fishLineEndTime;

    public static boolean catchCueAudioLatch = true;

    public Fish() throws SlickException {

        exclamationReal = new Image("data/assets/images/VisualCueReal.png");
        exclamationFake = new Image("data/assets/images/VisualCueFake.png");

        biteFake = new Sound("data/assets/audio/biteEffectFake.wav");
        biteReal = new Sound("data/assets/audio/biteEffectReal.wav");

    }
    //Fish quality/integer tables
    // 0 - trash
    // 1 - minor fish
    // 2 - mediocre fish
    // 3 - large fish
    // 4 - mythic fish
    // 5 - RNGesus fish
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

    // SCORE TABLE
    /*
     * trash - +1
     * minor - +3
     * mediocre - +5
     * large - +10
     * mythic - +15
     * RNGesus - +25
     */

     //method returning fish type based on cast score and random chance
    public static int getFishType(float castScore) {
        Random rand = new Random();
        int roll = rand.nextInt(100); // 0-99

        if (castScore >= 0 && castScore < 0.4f) {
            // 0-0.39: trash - 100%
            return 0;
        } else if (castScore >= 0.4f && castScore < 0.5f) {
            // 0.4-0.49: minor fish - 75%, trash - 25%
            if (roll < 75)
                return 1;
            else
                return 0;
        } else if (castScore >= 0.5f && castScore < 0.7f) {
            // 0.5-0.69: mediocre fish - 50%, minor fish - 30%, trash - 20%
            if (roll < 50)
                return 2;
            else if (roll < 80)
                return 1;
            else
                return 0;
        } else if (castScore >= 0.7f && castScore < 0.8f) {
            // 0.7-0.79: large fish - 40%, mediocre fish - 25%, minor fish - 20%, trash -
            // 15%
            if (roll < 40)
                return 3;
            else if (roll < 65)
                return 2;
            else if (roll < 85)
                return 1;
            else
                return 0;
        } else if (castScore >= 0.8f && castScore < 0.95f) {
            // 0.8-0.94: large fish - 55%, mediocre fish - 35%, minor fish - 9%, mythic fish
            // - 1%
            if (roll < 55)
                return 3;
            else if (roll < 90)
                return 2;
            else if (roll < 99)
                return 1;
            else
                return 4;
        } else if (castScore >= 0.95f && castScore <= 1.0f) {
            // 0.95 - 1: Mythic fish - 15%, large fish - 30%, mediocre fish - 45%, minor
            // fish - 9%, RNGesus fish - 1%
            if (roll < 15)
                return 4;
            else if (roll < 45)
                return 3;
            else if (roll < 90)
                return 2;
            else if (roll < 99)
                return 1;
            else
                return 5;
        } else {
            // fallback
            return 0;
        }
    }

    //distraction method, play distractions on random roll
    public static void playDistraction(Graphics g) {
        long now = System.currentTimeMillis();

        // Draw distraction if active
        if (distractionActive) {
            fakeExclamation(g);
            if (now > distractionEndTime) {
                distractionActive = false;
                lastDistractionRoll = now + DISTRACTION_PAUSE_AFTER_SHOW; // Add pause after showing
            }
            return; // Don't roll while active
        }

        // Only roll for a new distraction if not already active and cooldown passed
        if (now > lastDistractionRoll) {
            Random r = new Random();
            int roll = r.nextInt(6); // 1/6% chance per roll to roll a distraction
            if (roll == 0 && !(mainGame.fishTimer < 2)) {
                distractionActive = true;
                distractionEndTime = now + 1000; // show for 1 second
            } else if (roll == 1 && !(mainGame.fishTimer < 2)) {
                biteFake.play();
            } else if (roll == 2) {
                int secondroll = r.nextInt(3);
                if (secondroll == 2)
                    mainGame.triggerScreenShake();
            } else if (roll == 3) {
                int secondroll = r.nextInt(6);
                if (secondroll == 3) {
                    distractionActive = true;
                    distractionEndTime = now + 1000; // show for 1 second
                    biteFake.play();
                    mainGame.triggerScreenShake();
                }
            }
            lastDistractionRoll = now + DISTRACTION_ROLL_COOLDOWN;
        }
    }

    //catch fish core loop
    public static void catchFish(Graphics g) {
        long now = System.currentTimeMillis();
        if (mainGame.fishOnLine) {
            realCatchCue(g);
            if (now > fishLineEndTime) {
                mainGame.fishOnLine = false;
            }
        }

    }

    //draw red exclamation mark
    public static void fakeExclamation(Graphics g) {
        g.drawImage(exclamationFake, mainGame.player.hitbox.getX() - 32, mainGame.player.hitbox.getY() - 32);
    }

    //display and run sound for the real catch cue
    public static void realCatchCue(Graphics g) {

        if (catchCueAudioLatch) {
            biteReal.play();
            catchCueAudioLatch = false;
        }
        g.drawImage(exclamationReal, mainGame.player.hitbox.getX() - 32, mainGame.player.hitbox.getY() - 32);
    }
}