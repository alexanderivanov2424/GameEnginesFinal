package projects.final_project.characters;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.collisionShapes.CircleShape;
import engine.game.components.CollisionComponent;
import projects.final_project.Player;
import projects.final_project.assets.sounds.AnimationGraphComponent;
import engine.game.systems.CollisionSystem;
import engine.support.Vec2d;
import projects.final_project.FinalGame;

public class NhoKlu {

    public static void placeNhoKlu(GameWorld gameWorld, Vec2d position){
        GameObject oldMan = new GameObject(gameWorld, 1);
        AnimationGraphComponent agc = Characters.getCharacterAnimationGraph(new Vec2d(2,2), "Nho-Klu");
        agc.updateState(new Vec2d[]{new Vec2d(0,1)});
        oldMan.addComponent(agc);

        CollisionComponent talk = new CollisionComponent(new CircleShape(new Vec2d(0,0),1),
                false, false, FinalGame.TALK_LAYER, FinalGame.TALK_MASK);
        talk.linkCollisionCallback(NhoKlu::speakToNhoKlu);
        oldMan.addComponent(talk);

        DialogComponent dc = new DialogComponent(getDialog(), Characters.createTextBox(), Characters.createOptionBox());
        oldMan.addComponent(dc);

        oldMan.getTransform().position = position;
        gameWorld.addGameObject(oldMan);
    }

    public static void speakToNhoKlu(CollisionSystem.CollisionInfo collisionInfo){
        Player.PlayerComponent playerComponent = (Player.PlayerComponent)collisionInfo.gameObjectOther.getComponent("PlayerComponent");
        if(playerComponent == null) return;
        playerComponent.hasAxe = true;
        DialogComponent dc = (DialogComponent)collisionInfo.gameObjectSelf.getComponent("DialogComponent");
        if(dc == null) return;
        dc.startDialog();
    }

    private static DialogNode getDialog(){
        DialogNode dialog = new DialogNode("Hey you! You're finally asleep!");
        DialogNode quest = new DialogNode("Quickly now! You must save the town...");
        DialogNode question = new DialogNode("Do you even know what is happening?");

        DialogNode A = new DialogNode("The evil villain, Slippy the Toad, has invaded our lands. \n" +
                "You are the only person in these parts that has the power to take him on in battle.\n" +
                "Be warned! He has many followers that have been corrupted to his evil ways");
        DialogNode B = new DialogNode("Good, good! Jabralter must have already told you everything.");

        DialogNode sendoff = new DialogNode("Now go, defeat Slippy and his henchmen!\n" +
                "For the TOWN!!");

        dialog.setNextNode(quest);
        quest.setNextNode(question);
        question.setOptions(new String[]{"No", "Yes"}, new DialogNode[]{A,B});
        A.setNextNode(sendoff);
        B.setNextNode(sendoff);
        return dialog;
    }

}
