package engine.game.components;

import engine.game.GameObject;

public class AnimationGraphComponent extends Component{

    public AnimationGraphComponent() {
        super();
    }

    @Override
    public int getSystemFlags() {
        return 0;
    }

    @Override
    public String getTag() {
        return null;
    }


    public abstract class Node{
        public String name;

        public Node(String name){
            this.name = name;
        }

    }

    public abstract class AnimationNode{
        public String name;

        public AnimationNode(String name){
            this.name = name;
        }

    }
}
