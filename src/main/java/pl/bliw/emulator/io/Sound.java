package pl.bliw.emulator.io;

import javafx.scene.media.AudioClip;
import pl.bliw.util.Constants;

import java.net.URL;

public class Sound {

    public void play(String url) {
        URL resource = getClass().getResource(url);
        AudioClip clip = new AudioClip(resource.toString());
        clip.play();
    }

    public void beep() {
        play(Constants.BEEP_SOUND_PATH);
    }

}
