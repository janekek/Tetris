package utils.music;

import main.Main;
import utils.FileLoader;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.util.ArrayList;

public class Music {

    public static ArrayList<Clip> clips = new ArrayList<>();

    public static void stopAll () {
        for (Clip c : clips) c.stop();
    }

    public static void startAll () {
        for (Clip c : clips) c.start();
    }

    public static void clearAll () {
        clips.clear();
    }

    public static void restartAll () {
        for (Clip c : clips) {
            c.setMicrosecondPosition(0);
            c.start();
        }
    }

    public static void playBackground () {
        Clip c = playMusic("music/Theme.wav", true);
        clips.add(c);
    }
    public static void playSkipDownSound () {
        playMusic("music/SkipDown.wav", false);
    }
    public static void playMoveSound () {
        playMusic("music/Move.wav", false);
    }
    public static void playRow () {
        playMusic("music/Row.wav", false);
    }
//    public static void playBlockPlace () {
//        playMusic("music/BlockPlace.wav", false);
//    }

    public static void updateMusic () {
        if (Main.music) startAll();
        else stopAll();
        for (Clip c : clips) {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue((float) (Math.log10(Main.masterVolume /100) / Math.log10(10) * 20));
        }
    }

    private static Clip playMusic (String path, boolean repeat) {

        File music = FileLoader.getFile(path);
        try {
            if (music.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(music);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);

                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue((float) (Math.log10(Main.masterVolume /100) / Math.log10(10) * 20));

                if (repeat) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }

                clip.start();
                return clip;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
