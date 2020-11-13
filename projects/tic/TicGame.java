package projects.tic;

import engine.Application;
import engine.Screen;
import engine.UIToolKit.UIButton;
import engine.UIToolKit.UIElement;
import engine.UIToolKit.UIRect;
import engine.UIToolKit.UIText;
import engine.support.Vec2d;
import engine.support.Vec2i;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Tic Tac Toe Main Class
 *
 * Handles Game Logic and basic GUI
 *
 * GUI for the game board (ghost placement and placement) is given to the GameBoard class
 */

public class TicGame{
    public static Screen menuScreen;
    public static Screen board;
    public static Screen victory;

    public static boolean turn = false; // X is false O is true
    public static int[][] grid = new int[3][3]; //X:-1   empty: 0   O:1
    public static boolean gameOver = false;
    public static double timeSinceLastPlay = 0;
    public static final double maxTurnTime = 10; //seconds

    public static Color background = Color.color(.09, .6, .75);
    public static Color backgroundLighter = Color.color(.12, .8, .95);

    public static Color darkText = Color.color(.55, .85, 1);

    public static Color buttonColor = Color.DARKGREY;
    public static Color buttonBorder = Color.BLACK;
    public static Color buttonTextColor = Color.LIGHTGRAY;

    public static Color ghostCross = Color.LIGHTGRAY;
    public static Color playedCross = Color.GRAY;

    public static Color ghostRing = Color.LIGHTGRAY;
    public static Color playedRing = Color.GRAY;


    public static void addGameToApplication(Application app){
        TicGame.createMenuScreen(app);
        TicGame.createGameScreen(app);
    }

    private static void createMenuScreen(Application app){
        menuScreen = new Screen();
        //background
        menuScreen.addUIElement(new UIRect(new Vec2d(0,0), new Vec2d(960,540), TicGame.background));

        //title
        menuScreen.addUIElement(
                new UIText(new Vec2d(100,100), new Vec2d(100,100),
                        "TIC - TAC - TOE", TicGame.darkText, Font.font("Ariel", FontWeight.BOLD, 50))
        );
        //start button
        UIElement startGame = new UIButton(new Vec2d(100,150), new Vec2d(200,50), TicGame.buttonColor, TicGame.buttonBorder) {
            @Override
            public void onMouseClicked(MouseEvent e, Vec2d shift) {
                if(this.mouseInBounds(e,shift)){
                    app.setCurrentScreen("gameScreen");
                }
            }
        };
        startGame.addChild(new UIText(new Vec2d(100,32), new Vec2d(0,0),
                true,"START", TicGame.buttonTextColor, Font.font(30)));
        menuScreen.addUIElement(startGame);

        app.addScreen(menuScreen, "menuScreen");
        return;
    }

    private static void createGameScreen(Application app){
        board = new Screen();
        //background
        board.addUIElement(new UIRect(new Vec2d(0,0), new Vec2d(960,540), TicGame.backgroundLighter));
        //top text
        //handles turn and end game display
        board.addUIElement(new UIText(new Vec2d(480,30), new Vec2d(0,0), true,
                "X's Turn", Color.BLACK, Font.font("Ariel", FontWeight.BOLD, 30)){
            @Override
            public void onTick(long nanosSincePreviousTick) {
                if(TicGame.gameOver){
                    return;
                }
                int gameEnd = TicGame.checkForWin();
                if(gameEnd != 0){
                    if(gameEnd == -2){
                        this.text = "DRAW!";
                    }
                    if(gameEnd == -1){
                        this.text = "X WINS!";
                    }
                    if(gameEnd == 1){
                        this.text = "O WINS!";
                    }
                    TicGame.gameOver = true;
                } else {
                    if(TicGame.turn){
                        this.text = "O's Turn";
                    } else {
                        this.text = "X's Turn";
                    }
                }
            }
        });

        //timer bar and scaling logic
        UIElement timer = new UIRect(new Vec2d(695,45), new Vec2d(60,410), TicGame.buttonBorder);
        board.addUIElement(timer);
        board.addUIElement(new UIRect(new Vec2d(700,50), new Vec2d(50,400), TicGame.buttonColor){
            @Override
            public void onTick(long nanosSincePreviousTick) {
                if(TicGame.gameOver){
                    return;
                }
                if(TicGame.timeSinceLastPlay < TicGame.maxTurnTime) {
                    TicGame.timeSinceLastPlay += (double)nanosSincePreviousTick / 1000000000;
                    this.size = new Vec2d(this.size.x, (timer.size.y - 10) * (1-TicGame.timeSinceLastPlay / TicGame.maxTurnTime));
                }
            }
        });

        //table to support the game board
        UIElement table = new UIRect(new Vec2d(310,80), new Vec2d(340,340),
                TicGame.background);
        //game board (and some logic) taken from GameBoard UIElement
        GameBoard gameBoard = new GameBoard(new Vec2d(330,100), new Vec2d(300,300),
                TicGame.backgroundLighter);

        board.addUIElement(table);
        board.addUIElement(gameBoard);

        //Game restart button
        UIElement restartGame = new UIButton(new Vec2d(380,450), new Vec2d(200,50),
                TicGame.buttonColor, TicGame.buttonBorder) {
            @Override
            public void onMouseClicked(MouseEvent e, Vec2d shift) {
                if(this.mouseInBounds(e,shift)){
                    TicGame.grid = new int[3][3]; //reset logical board
                    gameBoard.clearBoard(); //reset graphical board
                    TicGame.timeSinceLastPlay = 0;
                    TicGame.gameOver = false;
                }
            }
        };
        restartGame.addChild(new UIText(new Vec2d(100,32), new Vec2d(0,0),
                true,"RESTART", TicGame.buttonTextColor, Font.font(30)));

        board.addUIElement(restartGame);

        app.addScreen(board, "gameScreen");
        return;
    }

    /**
     * Checks for a win or draw state in the grid
     * Returns 0 if game can continue
     *
     * @return  1 for O win
     *          0 for not end of game
     *          -1 for X win
     *          -2 for draw
     */
    public static int checkForWin(){
        //Check for timeout
        if(TicGame.timeSinceLastPlay >= TicGame.maxTurnTime){
            return TicGame.turn ? -1 : 1; //True is 1 / O    False is -1 / X
        }
        //check grid
        for(int i = 0; i < 3; i++){
            //columns
            if(Math.abs(grid[i][0] + grid[i][1] + grid[i][2]) == 3) return grid[i][0];
            //rows
            if(Math.abs(grid[0][i] + grid[1][i] + grid[2][i]) == 3) return grid[0][i];
        }
        //diagonals
        if(Math.abs(grid[0][0] + grid[1][1] + grid[2][2]) == 3) return grid[0][0];
        if(Math.abs(grid[0][2] + grid[1][1] + grid[2][0]) == 3) return grid[0][2];
        //draw check
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(grid[i][j] == 0){
                    return 0; //not draw
                }
            }
        }
        return -2; //draw
    }

    /**
     * Checks if a move can be made
     * Used by GameBoard UIElement
     * @param loc location of the move
     */
    public static boolean canMove(Vec2i loc){
        return TicGame.grid[loc.x][loc.y] == 0;
    }

    /**
     * Makes a move on the actual game grid
     * Used by GameBoard UIElement
     * @param loc location of the move
     */
    public static void makeMove(Vec2i loc){
        if(TicGame.grid[loc.x][loc.y] == 0){
            TicGame.timeSinceLastPlay = 0;
            if(TicGame.turn){
                TicGame.grid[loc.x][loc.y] = 1;
            } else {
                TicGame.grid[loc.x][loc.y] = -1;
            }
            TicGame.turn = !TicGame.turn;
        }
        return;
    }


}