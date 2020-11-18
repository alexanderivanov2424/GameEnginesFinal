package engine.game.components;

import engine.game.GameObject;
import engine.game.systems.SystemFlag;
import javax.sound.sampled.*;
import engine.support.Vec2d;

import java.io.*;

public class AudioComponent extends Component {

    public AudioComponent(GameObject gameObject, String filePath) {
        super(gameObject);

        filePath = "projects/WizTesting/assets/Sounds/" + filePath;

        File file = new File(filePath);
        InputStream in =
                null;
        try {
            in = new BufferedInputStream(
                    new FileInputStream(file)
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        AudioInputStream stream = null;
        try {
            stream = AudioSystem
                    .getAudioInputStream(in);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            clip.open(stream);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clip.start();
    }

    @Override
    public int getSystemFlags() {
        return SystemFlag.TickSystem;
    }

    @Override
    public String getTag() {
        return "AudioComponent";
    }
}
