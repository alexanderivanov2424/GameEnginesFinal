package projects.nin;

import engine.game.GameObject;
import engine.game.GameWorld;
import engine.game.collisionShapes.Shape;
import engine.game.components.*;
import engine.game.systems.SystemFlag;
import engine.support.Vec2d;
import javafx.scene.input.KeyCode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.Set;

public class PlayerMovementComponent extends Component {

    PhysicsComponent physicsComponent;
    public boolean onGround = false;

    public boolean beamON = false;

    private String player_direction = "right";

    public PlayerMovementComponent(GameObject gameObject) {
        super(gameObject);
        this.physicsComponent = (PhysicsComponent)gameObject.getComponent("PhysicsComponent");
    }

    @Override
    public void onTick(long nanosSincePreviousTick){
        if(this.physicsComponent == null) return;
        double dt = nanosSincePreviousTick/1000000000.0; //seconds since last tick

        Set<KeyCode> keyState = this.gameObject.gameWorld.getKeyState();
        boolean left = keyState.contains(KeyCode.A);
        boolean right = keyState.contains(KeyCode.D);
        Vec2d v = this.physicsComponent.getVelocity();
        if(left ^ right){
            if(left) {
                this.physicsComponent.setVelocity(new Vec2d(0,v.y));
                this.physicsComponent.applyDeltaV(new Vec2d(-.2, 0));
                setPlayerDirection("left");
            } else {
                this.physicsComponent.setVelocity(new Vec2d(0,v.y));
                this.physicsComponent.applyDeltaV(new Vec2d(.2, 0));
                setPlayerDirection("right");
            }
        } else {
            setPlayerDirection("idle");
        }

        if(keyState.contains(KeyCode.SPACE) && onGround) {
            this.physicsComponent.applyImpulse(new Vec2d(0, -100));
            this.onGround = false;
        }

        RayComponent rayComponent = (RayComponent) this.gameObject.getComponent("RayComponent");
        RaySpriteAnimationComponent animationComponent = (RaySpriteAnimationComponent) this.gameObject.getComponent("RaySpriteAnimationComponent");
        if(rayComponent != null){
            if(this.gameObject.gameWorld.getMouseDown()) {
                animationComponent.enable();
                Vec2d player_size = this.gameObject.getTransform().size;
                Vec2d center = this.gameObject.getTransform().position.plus(player_size.smult(.5));
                rayComponent.getRay().src = center;
                Vec2d mouse = this.gameObject.gameWorld.getMousePosition();
                rayComponent.getRay().dir = mouse.minus(center).normalize();
                if(rayComponent.colliding_with != null){
                    PhysicsComponent physicsComponent = (PhysicsComponent)rayComponent.colliding_with.getComponent("PhysicsComponent");
                    HealthComponent healthComponent = (HealthComponent)rayComponent.colliding_with.getComponent("HealthComponent");
                    if(physicsComponent != null){
                        physicsComponent.applyImpulse(rayComponent.getRay().dir);
                    }
                    if(healthComponent != null){
                        healthComponent.hit(4*dt);
                    }
                }
            } else {
                animationComponent.disable();
            }
        }
    }

    private void setPlayerDirection(String dir){
        SpriteAnimationComponent spriteAnimationComponent =  (SpriteAnimationComponent)this.gameObject.getComponent("SpriteAnimationComponent");
        if(spriteAnimationComponent == null) return;
        if(player_direction.equals(dir)) return;
        if(dir.equals("left")){
            spriteAnimationComponent.resetAnimation(NinGame.getSpritePath("player"),
                    new Vec2d(0,0), new Vec2d(2,2),
                    8, new Vec2d(0,257), new Vec2d(64,64), .1);
            spriteAnimationComponent.setHorizontalFlip(true);
        } else if(dir.equals("right")){
            spriteAnimationComponent.resetAnimation(NinGame.getSpritePath("player"),
                    new Vec2d(0,0), new Vec2d(2,2),
                    8, new Vec2d(0,257), new Vec2d(64,64), .1);
            spriteAnimationComponent.setHorizontalFlip(false);
        } else if(dir.equals("idle")){
            spriteAnimationComponent.resetAnimation(NinGame.getSpritePath("player"),
                    new Vec2d(0,0), new Vec2d(2,2),
                    1, new Vec2d(0,513), new Vec2d(64,64), .3);
        }
        this.player_direction = dir;
    }

    @Override
    public void onLateTick(){};

    @Override
    public int getSystemFlags() {
        return SystemFlag.TickSystem;
    }

    @Override
    public String getTag() {
        return "PlayerMovementComponent";
    }

    public Element getXML(Document doc){
        Element component = doc.createElement(this.getClass().getName());
        component.setAttribute("onGround", Boolean.toString(onGround));
        return component;
    }

    public static Component loadFromXML(Element n, GameObject g) {
        NamedNodeMap attr = n.getAttributes();
        boolean onGround = Boolean.parseBoolean(attr.getNamedItem("onGround").getNodeValue());
        PlayerMovementComponent c = new PlayerMovementComponent(g);
        return c;
    }
}
