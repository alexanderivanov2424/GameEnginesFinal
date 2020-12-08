package projects.final_project;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.components.AudioComponent;
import engine.game.components.SpriteComponent;

public class BackgroundMusic {

    private static GameObject music;
    private static String name;


    public static void playBGM1(GameWorld gameWorld) {
        music = new GameObject(gameWorld);

        AudioComponent bgm = new AudioComponent("bgm1.wav", true);
        bgm.start();

        music.addComponent(bgm);

        gameWorld.addGameObject(music);
        name = "bgm1";
    }

    public static void playBGM2(GameWorld gameWorld) {
        music = new GameObject(gameWorld);

        AudioComponent bgm = new AudioComponent("bgm2.wav", true);
        bgm.start();

        music.addComponent(bgm);

        gameWorld.addGameObject(music);
        name = "bgm2";
    }

    public static void playBossBGM(GameWorld gameWorld) {
        music = new GameObject(gameWorld);

        AudioComponent bgm = new AudioComponent("boss.wav", true);
        bgm.start();

        music.addComponent(bgm);

        gameWorld.addGameObject(music);
        name = "boss";
    }

    public static void stopBGM(GameWorld gameWorld) {
        if(music == null) return;
        ((AudioComponent)(music.getComponent("AudioComponent"))).stop();
        gameWorld.removeGameObject(music);
        name = "none";
    }

    public static String getName() {
        return name;
    }

}
