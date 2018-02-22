package pl.bliw.emulator.io;

import javafx.scene.media.AudioClip;
import pl.bliw.util.Constants;

import java.net.URL;

/**
 * The class represents sound module that can play sounds.
 */
public class Sound {
    /**
     * Plays sound using file from given url.
     *
     * @param url path to sound file.
     */
    public void play(String url) {
        URL resource = getClass().getResource(url);
        AudioClip clip = new AudioClip(resource.toString());
        clip.play();
    }

    /**
     * Plays beep sound.
     */
    public void beep() {
        play(Constants.BEEP_SOUND_PATH);
    }
}
