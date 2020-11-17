package projects.alc;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.SpriteLoader;
import engine.game.collisionShapes.AABShape;
import engine.game.components.*;
import engine.game.systems.CollisionSystem;
import engine.support.Vec2d;

import java.io.File;

public class AlchemyGame {

    private GameWorld gameWorld;
    private SpriteLoader spriteLoader;

    private static final Vec2d ELEMENT_SIZE = new Vec2d(2,2);

    public AlchemyGame(GameWorld gameWorld){
        this.gameWorld = gameWorld;

        spriteLoader = new SpriteLoader();
        gameWorld.setSpriteLoader(spriteLoader);

    }

    public void init(){
        this.gameWorld.addGameObject(getMainPedestal());
        this.gameWorld.addGameObject(getTrash());
        this.gameWorld.addGameObject(this.getElementRoot("water", new Vec2d(1 - 7,.5-3)));
        this.gameWorld.addGameObject(this.getElementRoot("earth", new Vec2d(4 - 7,.5-3)));
        this.gameWorld.addGameObject(this.getElementRoot("air", new Vec2d(7 - 7,.5-3)));
        this.gameWorld.addGameObject(this.getElementRoot("fire", new Vec2d(10 - 7,.5-3)));
    }

    /*
     * converts a string into the path to the corresponding png file.
     */
    private String getSpritePath(String name){
        File folder = new File("file:.\\projects\\alc\\assets\\");
        File sprite = new File(folder, name.concat(".png"));
        return sprite.toString();
    }

    private GameObject getMainPedestal(){
        GameObject pedestal = new GameObject(this.gameWorld);
        pedestal.addComponent(new SpriteComponent(pedestal, getSpritePath("pedestal"),
                new Vec2d(0,0), new Vec2d(13,3)));

        pedestal.getTransform().position = new Vec2d(-7, -3);
        pedestal.getTransform().size = new Vec2d(13, 3);
        return pedestal;
    }

    private GameObject getTrash(){
        GameObject trash = new GameObject(this.gameWorld);
        trash.addComponent(new SpriteComponent(trash, getSpritePath("trash"),
                new Vec2d(0,0), ELEMENT_SIZE));
        CollisionComponent collisionComponent = new CollisionComponent(trash, new AABShape(new Vec2d(0,0), ELEMENT_SIZE),
                true, true, 1 , 1);
        collisionComponent.linkCollisionCallback(AlchemyGame::trashCallback);
        trash.addComponent(collisionComponent);
        trash.getTransform().position = new Vec2d(-9, -2.5);
        trash.getTransform().size = ELEMENT_SIZE;
        return trash;
    }

    private static void trashCallback(CollisionSystem.CollisionInfo collisionInfo){
        collisionInfo.gameObjectSelf.gameWorld.removeGameObject(collisionInfo.gameObjectOther);
    }

    private GameObject getElement(Vec2d pos, Vec2d size, String elementName){
        GameObject element = new GameObject(this.gameWorld);
        element.addComponent(new DraggableComponent(element));
        element.addComponent(new IDComponent(element, elementName));
        element.addComponent(new SpriteComponent(element, this.getSpritePath(elementName),
                new Vec2d(0,0), ELEMENT_SIZE));
        CollisionComponent collisionComponent = new CollisionComponent(element, new AABShape(new Vec2d(0,0), ELEMENT_SIZE),
                true, true, 1 , 1);
        collisionComponent.linkCollisionCallback(this::elementCallback);
        element.addComponent(collisionComponent);
        element.getTransform().position = new Vec2d(pos.x, pos.y);
        element.getTransform().size = new Vec2d(size.x, size.y);
        return element;
    }
    private void elementCallback(CollisionSystem.CollisionInfo collisionInfo){
        if(canCombineElements(collisionInfo.gameObjectSelf,collisionInfo.gameObjectOther)){
            if(collisionInfo.isParent) {
                combineElements(collisionInfo.gameObjectSelf, collisionInfo.gameObjectOther);
            }
            this.gameWorld.removeGameObject(collisionInfo.gameObjectSelf);
        }
    }

    private GameObject getElementRoot(String elementName, Vec2d pos){
        GameObject elementRoot = new GameObject(this.gameWorld);
        elementRoot.addComponent(new SpriteComponent(elementRoot, this.getSpritePath(elementName),
                new Vec2d(0,0), ELEMENT_SIZE));
        ClickableComponent clickableComponent = new ClickableComponent(elementRoot);
        clickableComponent.linkClickCallback(this::clickCallback);
        elementRoot.addComponent(clickableComponent);
        elementRoot.getTransform().position = pos;
        elementRoot.getTransform().size = ELEMENT_SIZE;
        return elementRoot;
    }

    private void clickCallback(GameObject elementRoot){
        String elementName = "TODO";
        this.gameWorld.addGameObject(getElement(
                elementRoot.getTransform().position,elementRoot.getTransform().size,elementName
        ));
    }

    private void combineElements(GameObject first, GameObject second){
        String firstType = ((IDComponent)first.getComponent("IDComponent")).getId();
        String secondType = ((IDComponent)second.getComponent("IDComponent")).getId();

        Vec2d newPositon = new Vec2d((first.getTransform().position.x + second.getTransform().position.x)/2,
                (first.getTransform().position.y + second.getTransform().position.y)/2);
        GameObject newElement = getElement(newPositon, ELEMENT_SIZE, AlchemyOf(firstType,secondType));
        this.gameWorld.addGameObject(newElement);
    }

    private boolean canCombineElements(GameObject first, GameObject second){
        Component c1 = first.getComponent("IDComponent");
        if(c1 == null) return false;
        Component c2 = second.getComponent("IDComponent");
        if(c2 == null) return false;

        String firstType = ((IDComponent)c1).getId();
        String secondType = ((IDComponent)c2).getId();

        return AlchemyOf(firstType,secondType) != null;
    }

    private boolean isPair(String a, String b, String a_, String b_){
        if(a.equals(a_) && b.equals(b_)){
            return true;
        }
        if(a.equals(b_) && b.equals(a_)){
            return true;
        }
        return false;
    }

    private String AlchemyOf(String a, String b){
        if(isPair(a,b,"water","earth")){
            return "mud";
        }
        if(isPair(a,b,"fire","earth")){
            return "brick";
        }
        if(isPair(a,b,"water","air")){
            return "rain";
        }
        if(isPair(a,b,"earth","air")){
            return "dust";
        }
        if(isPair(a,b,"water","fire")){
            return "gas";
        }
        if(isPair(a,b,"air","fire")){
            return "life";
        }
        if(isPair(a,b,"life","water")){
            return "fish";
        }
        if(isPair(a,b,"water","gas")){
            return "acid";
        }
        if(isPair(a,b,"water","water")){
            return "fish";
        }
        if(isPair(a,b,"life","acid")){
            return "death";
        }
        if(isPair(a,b,"fish","acid")){
            return "death";
        }

        return null;
    }
}
