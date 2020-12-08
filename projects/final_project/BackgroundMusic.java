package projects.final_project;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.components.AudioComponent;
import engine.game.components.SpriteComponent;

public class BackgroundMusic {

    private static GameObject music;

    public static void playBGM1(GameWorld gameWorld) {
        music = new GameObject(gameWorld);

        AudioComponent bgm = new AudioComponent("bgm1.wav", true);
        bgm.start();

        music.addComponent(bgm);

        gameWorld.addGameObject(music);
    }

    public static void playBGM2(GameWorld gameWorld) {
        music = new GameObject(gameWorld);

        AudioComponent bgm = new AudioComponent("bgm2.wav", true);
        bgm.start();

        music.addComponent(bgm);

        gameWorld.addGameObject(music);
    }

    public static void stopBGM(GameWorld gameWorld) {
        ((AudioComponent)(music.getComponent("AudioComponent"))).stop();
        gameWorld.removeGameObject(music);
    }

}
