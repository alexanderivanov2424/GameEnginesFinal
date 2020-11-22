package engine.game.components.Animation.AnimationSystem;

import engine.game.components.Animation.AnimationComponent;
import engine.game.components.Component;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashMap;

public class AnimationGraphComponent extends Component {

    private AGNode[] nodes;

    private HashMap<String, Integer> nodesLookup;
    private int currentNode = 0;

    private String nextAnimation = null;
    private boolean transitionWithInterupt = false;


    //TODO Animations should have flag for if they are interuptable
    //TODO there should be a proper queue for animations and a "fade out" time
    //Should function like key input system in smash.

    public AnimationGraphComponent(AGNode[] nodes) {
        this.nodes = nodes;
        assert(nodes.length > 0);

        nodesLookup = new HashMap<>();
        for(int i = 0; i < nodes.length; i++){
            assert(!nodesLookup.containsKey(nodes[i].name));
            nodesLookup.put(nodes[i].name,i);
        }
    }

    public void updateState(Vec2d state){
        if(this.nodes[this.currentNode] instanceof AGAnimationGroup){
            AGAnimationGroup g = (AGAnimationGroup)(this.nodes[this.currentNode]);
            g.updateState(state);
        }
    }

    public void queueAnimation(String name){
        this.nextAnimation = name;
        this.transitionWithInterupt = false;
    }

    public void queueAnimation(String name, boolean transitionWithInterupt){
        this.nextAnimation = name;
        this.transitionWithInterupt = transitionWithInterupt;
    }

    private void transitionToQueuedAnimation(){
        currentNode = this.nodesLookup.get(this.nextAnimation);
        this.nextAnimation = null;
        this.transitionWithInterupt = false;
    }

    public void onTick(long nanosSincePreviousTick) {
        AGNode current = this.nodes[this.currentNode];
        if(transitionWithInterupt && this.nextAnimation != null){
            current.restart();
            transitionToQueuedAnimation();
        }

        current.onTick(nanosSincePreviousTick);
        if(current.justFinished()){
            if(this.nextAnimation != null){
                current.restart();
                transitionToQueuedAnimation();
            } else if(current.onFinishTransitionTo != null) {
                current.restart();
                currentNode = this.nodesLookup.get(current.onFinishTransitionTo);
            }
        }
    }

    public void onLateTick() {
        this.nodes[this.currentNode].onLateTick();
    }

    public void onDraw(GraphicsContext g) {
        this.nodes[this.currentNode].onDraw(g);
    }

    @Override
    public int getSystemFlags() {
        return 0;
    }

    @Override
    public String getTag() {
        return null;
    }

}
