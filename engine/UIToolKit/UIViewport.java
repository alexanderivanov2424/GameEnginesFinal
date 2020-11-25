package engine.UIToolKit;

import engine.game.GameWorld;
import engine.support.Vec2d;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

import java.util.Set;


public class UIViewport extends UIElement{
    private GameWorld gameWorld;

    private Vec2d gamePosition;

    private double scale;

    private boolean scalable = false;

    private double panRate = .5;

    private boolean interactable =false;

    //TODO have nice way to specify if viewport should support panning

    public UIViewport(Vec2d position, Vec2d size, GameWorld gameWorld, Vec2d gamePosition, boolean interactable) {
        super(position, size);
        this.gameWorld = gameWorld;
        this.gamePosition = gamePosition;
        this.scale = 1;
        this.interactable = interactable;
    }

    public UIViewport(Vec2d position, Vec2d size, GameWorld gameWorld, Vec2d gamePosition, boolean interactable, double scale) {
        super(position, size);
        this.gameWorld = gameWorld;
        this.gamePosition = gamePosition;
        this.scale = scale;
    }

    public void onTick(long nanosSincePreviousTick) {
        super.onTick(nanosSincePreviousTick);
        this.gameWorld.onTick(nanosSincePreviousTick);

        if(interactable) {
            Set<KeyCode> keyState = this.gameWorld.getKeyState();
            if (keyState.contains(KeyCode.W)) {
                this.gamePosition = new Vec2d(this.gamePosition.x, this.gamePosition.y + this.panRate);
            }
            if (keyState.contains(KeyCode.A)) {
                this.gamePosition = new Vec2d(this.gamePosition.x + this.panRate, this.gamePosition.y);
            }
            if (keyState.contains(KeyCode.S)) {
                this.gamePosition = new Vec2d(this.gamePosition.x, this.gamePosition.y - this.panRate);
            }
            if (keyState.contains(KeyCode.D)) {
                this.gamePosition = new Vec2d(this.gamePosition.x - this.panRate, this.gamePosition.y);
            }
        }
    }

    public void onLateTick() {
        this.gameWorld.onLateTick();
    }

    @Override
    public void onDraw(GraphicsContext g) {
        Vec2d pos = this.getOffset();

        g.save();

        Affine affine = g.getTransform();
        affine.appendTranslation(pos.x + this.size.x/2,pos.y + this.size.y/2);
        affine.appendScale(this.scale,this.scale);
        affine.appendTranslation(-this.gamePosition.x,-this.gamePosition.y);
        g.setTransform(affine);
        this.gameWorld.onDraw(g);
        g.restore();
        super.onDraw(g);
    }

    @Override
    public void onLateDraw(GraphicsContext g) {
        Vec2d pos = this.getOffset();

        g.save();

        Affine affine = g.getTransform();
        affine.appendTranslation(pos.x + this.size.x/2,pos.y + this.size.y/2);
        affine.appendScale(this.scale,this.scale);
        affine.appendTranslation(-this.gamePosition.x,-this.gamePosition.y);
        g.setTransform(affine);
        this.gameWorld.onLateDraw(g);
        g.restore();
        super.onDraw(g);
    }

    private Vec2d screenToGame(Vec2d screenPos){
        Vec2d pos = this.getOffset();

        Affine affine = new Affine();
        affine.prependTranslation(-pos.x - this.size.x/2,-pos.y - this.size.y/2);
        affine.prependScale(1/this.scale,1/this.scale);
        affine.prependTranslation(this.gamePosition.x,this.gamePosition.y);

        Point2D point = affine.transform(screenPos.x,screenPos.y);

        return new Vec2d(point.getX(),point.getY());
    }

    private Vec2d gameToScreen(Vec2d gamePos){
        Vec2d pos = this.getOffset();

        Affine affine = new Affine();
        affine.prependTranslation(-this.gamePosition.x,-this.gamePosition.y);
        affine.prependScale(this.scale,this.scale);
        affine.prependTranslation(pos.x + this.size.x/2,pos.y + this.size.y/2);

        Point2D point = affine.transform(gamePos.x, gamePos.y);
        return new Vec2d(point.getX(),point.getY());
    }

    public double getScale(){
        return this.scale;
    }

    public void setGamePosition(Vec2d gamePosition){
        this.gamePosition = gamePosition;
    }

    public Vec2d getGamePosition(){
        return this.gamePosition;
    }

    /**
     * Called when a key is typed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    @Override
    public void onKeyTyped(KeyEvent e) {
        this.gameWorld.onKeyTyped(e);
    }

    /**
     * Called when a key is pressed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    @Override
    public void onKeyPressed(KeyEvent e) {
        this.gameWorld.onKeyPressed(e);
    }

    /**
     * Called when a key is released.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    @Override
    public void onKeyReleased(KeyEvent e) {
        this.gameWorld.onKeyReleased(e);
    }

    /**
     * Called when the mouse is clicked.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    @Override
    public void onMouseClicked(MouseEvent e, Vec2d shift) {
        this.gameWorld.onMouseClicked(this.screenToGame(new Vec2d(e.getX() + shift.x, e.getY() + shift.y)));
    }

    /**
     * Called when the mouse is pressed.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    @Override
    public void onMousePressed(MouseEvent e, Vec2d shift) {
        this.gameWorld.onMousePressed(this.screenToGame(new Vec2d(e.getX() + shift.x, e.getY() + shift.y)));
    }

    /**
     * Called when the mouse is released.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    @Override
    public void onMouseReleased(MouseEvent e, Vec2d shift) {
        this.gameWorld.onMouseReleased(this.screenToGame(new Vec2d(e.getX() + shift.x, e.getY() + shift.y)));
    }

    /**
     * Called when the mouse is dragged.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    @Override
    public void onMouseDragged(MouseEvent e, Vec2d shift) {
        this.gameWorld.onMouseDragged(this.screenToGame(new Vec2d(e.getX() + shift.x, e.getY() + shift.y)));
    }

    /**
     * Called when the mouse is moved.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    @Override
    public void onMouseMoved(MouseEvent e, Vec2d shift) {
        this.gameWorld.onMouseMoved(this.screenToGame(new Vec2d(e.getX() + shift.x, e.getY() + shift.y)));
    }

    /**
     * Called when the mouse wheel is moved.
     * @param e		an FX {@link ScrollEvent} representing the input event.
     */
    @Override
    public void onMouseWheelMoved(ScrollEvent e, Vec2d shift) {
        this.gameWorld.onMouseWheelMoved(e);
        if(this.mouseInBounds(e.getX() + shift.x, e.getY() + shift.y)){
            double scroll = e.getDeltaY();
            this.scale *= 1 + .005 * scroll;
        }
    }

}
