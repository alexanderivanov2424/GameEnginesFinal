package projects.tic;

import engine.UIToolKit.UIElement;
import engine.UIToolKit.UIRect;
import engine.support.Vec2d;
import engine.support.Vec2i;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/**
 * UIElement that functions as the game board
 * Handles graphical aspect of the game board
 * Relies on the static TicGame class
 */
public class GameBoard extends UIRect {
    UIElement ghostMove;

    public GameBoard(Vec2d position, Vec2d size, Color color) {
        super(position, size, color);
    }

    private Vec2i getGridPos(MouseEvent e){
        Vec2d pos = this.getOffset();
        int mouseGridX = (int)((e.getX() - pos.x)/(this.size.x/3));
        int mouseGridY = (int)((e.getY() - pos.y)/(this.size.y/3));
        return new Vec2i(mouseGridX,mouseGridY);
    }

    public void clearBoard(){
        this.children.clear();
    }

    @Override
    public void onMouseMoved(MouseEvent e, Vec2d shift) {
        if(TicGame.gameOver){
            return;
        }
        if(!this.mouseInBounds(e,shift)){ return; }
        Vec2i mouseGrid = this.getGridPos(e);
        if(mouseGrid.x > 2 || mouseGrid.x < 0 || mouseGrid.y > 2 || mouseGrid.y < 0) return;
        //if spot available show ghost
        if(TicGame.grid[mouseGrid.x][mouseGrid.y] == 0){
            if(ghostMove != null){
                this.children.remove(ghostMove);
            }
            if(TicGame.turn){
                this.ghostMove = new Ring(new Vec2d(mouseGrid.x*(this.originalSize.x/3),mouseGrid.y*(this.originalSize.y/3)),
                        new Vec2d((this.originalSize.x/3),(this.originalSize.y/3)), TicGame.ghostRing);
            } else{
                this.ghostMove = new Cross(new Vec2d(mouseGrid.x*(this.originalSize.x/3),mouseGrid.y*(this.originalSize.y/3)),
                        new Vec2d((this.originalSize.x/3),(this.originalSize.y/3)), TicGame.ghostCross);
            }
            this.addChild(this.ghostMove);
        } else {
            this.children.remove(ghostMove);
            this.ghostMove = null;
        }
    }
    @Override
    public void onMouseClicked(MouseEvent e, Vec2d shift) {
        if(TicGame.gameOver){
            return;
        }
        if(!this.mouseInBounds(e,shift)){ return; }
        Vec2i mouseGrid = this.getGridPos(e);
        if(mouseGrid.x > 2 || mouseGrid.x < 0 || mouseGrid.y > 2 || mouseGrid.y < 0) return;
        if(TicGame.canMove(mouseGrid)){
            UIElement move;
            if(TicGame.turn){
                move = new Ring(new Vec2d(mouseGrid.x*(this.originalSize.x/3),mouseGrid.y*(this.originalSize.y/3)),
                        new Vec2d((this.originalSize.x/3),(this.originalSize.y/3)), TicGame.playedRing);
            } else{
                move = new Cross(new Vec2d(mouseGrid.x*(this.originalSize.x/3),mouseGrid.y*(this.originalSize.y/3)),
                        new Vec2d((this.originalSize.x/3),(this.originalSize.y/3)), TicGame.playedCross);
            }
            this.addChild(move);
            TicGame.makeMove(mouseGrid);
        }
    }

    /**
     * Called when a key is typed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    public void onKeyTyped(KeyEvent e) {

    }

    /**
     * Cross Shape for player X
     */
    private static class Cross extends UIElement{
        Color color;
        public Cross(Vec2d position, Vec2d size, Color color) {
            super(position, size);
            this.color = color;
        }

        @Override
        public void onDraw(GraphicsContext g) {
            Vec2d pos = this.getOffset();
            g.setLineWidth(5);
            g.setStroke(color);
            g.strokeLine(pos.x,pos.y, pos.x + this.size.x, pos.y + this.size.y);
            g.strokeLine(pos.x,pos.y + this.size.y, pos.x + this.size.x, pos.y);
        }
    }

    /**
     * Ring Shape for player O
     */
    private static class Ring extends UIElement{
        Color color;
        public Ring(Vec2d position, Vec2d size, Color color) {
            super(position, size);
            this.color = color;
        }

        @Override
        public void onDraw(GraphicsContext g) {
            Vec2d pos = this.getOffset();
            g.setLineWidth(5);
            g.setStroke(color);
            g.strokeArc(pos.x,pos.y,this.size.x,this.size.y,0,360, ArcType.OPEN);
        }
    }
}