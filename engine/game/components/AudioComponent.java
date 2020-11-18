package engine.game.components;

import engine.game.GameObject;
import engine.game.systems.SystemFlag;
import javax.sound.sampled.*;
import engine.support.Vec2d;

import java.io.*;

public class AudioComponent extends Component {

    protected String filePath;
    protected FloatControl gainControl;
    protected GameObject source;

    public AudioComponent(GameObject gameObject, String filePath, GameObject source, boolean loop) {
        super(gameObject);

        this.filePath = "projects/WizTesting/assets/Sounds/" + filePath;
        this.source = source;

        Clip clip = setup();

        gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        gainControl.setValue(-80.0f);

        if(loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }

        clip.start();
    }

    public AudioComponent(GameObject gameObject, String filePath, GameObject source) {
        super(gameObject);

        this.filePath = "projects/WizTesting/assets/Sounds/" + filePath;
        this.source = source;

        Clip clip = setup();




        gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-80.0f);


        clip.start();
    }

    public AudioComponent(GameObject gameObject, String filePath) {
        super(gameObject);

        this.filePath = "projects/WizTesting/assets/Sounds/" + filePath;
        Clip clip = setup();

        clip.start();
    }

    public AudioComponent(GameObject gameObject, String filePath, boolean loop) {
        super(gameObject);

        this.filePath = "projects/WizTesting/assets/Sounds/" + filePath;

        Clip clip = setup();

        if(loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }

        clip.start();
    }

    @Override
    public void onTick(long nanosSincePreviousTick){
        if(source != null) {
            //Based on the game object's position from the source, reduce volume
            float dist = (float)gameObject.getTransform().position.dist(source.getTransform().position);
            if(-0.8f*dist < -80.0f) {
                gainControl.setValue(-80.0f);
            }
            else {
                gainControl.setValue(-0.8f*dist);
            }
            System.out.println(dist);
        }

    }


    public Clip setup() {
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

        return clip;
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