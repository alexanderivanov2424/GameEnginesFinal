package projects.final_project;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.components.AudioComponent;
import engine.game.components.SpriteComponent;

public class BackgroundMusic {

    public static void playBGM1(GameWorld gameWorld) {
        GameObject music = new GameObject(gameWorld);

        AudioComponent bgm1 = new AudioComponent("bgm1.wav", true);

        music.addComponent(bgm1);

        gameWorld.addGameObject(music);
    }
}
