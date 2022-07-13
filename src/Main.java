import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class Main extends Application {

    // Class variables
    private Timeline timer;
    private Random random;
    private Pane paneGameBoard;
    private Pane paneScoreDisplay;
    private Pane paneLevelDisplay;
    private Pane paneClearedLinesDisplay;
    private Pane paneNextTetramino;
    private Pane paneHoldTetramino;
    private Pane paneUpcomingTetraminos;
    private VBox vBoxNextAndUpcomingTetraminos;
    private GridPane gridPaneMain;
    private Scene scene;
    private Label lblScoreValue;
    private Label lblLevelValue;
    private Label lblClearedLinesValue;
    private boolean isHoldTetraminoBoxEmpty;
    private int dimensions_holdTetraminoWidth;
    private int dimensions_holdTetraminoHeight;
    private int dimensions_boardWidth;
    private int dimensions_boardHeight;
    private int dimensions_nextTetraminoWidth;
    private int dimensions_nextTetraminoHeight;
    private int dimensions_upcomingTetraminosWidth;
    private int dimensions_upcomingTetraminosHeight;
    private int dimensions_upcomingTetraminoDistanceToMiddle;
    private int dimensions_upcomingTetraminoDistanceToBottom;
    private int dimensions_gameInformationWidth;
    private int dimensions_gameInformationHeight;
    private int dimensions_gameInformationMargin;
    private int speed;
    private int level;
    private String levelString;
    private int numberOfRowsCleared;
    private int fallingTetraminoType;
    private int holdTetraminoType;
    private int nextTetraminoType;
    private int upcomingTetraminoTypeTop;
    private int upcomingTetraminoTypeMiddle;
    private int upcomingTetraminoTypeBottom;
    private int orientation;
    private int startRowBoard;
    private int startColBoard;
    private int currentRowBoard;
    private int currentColBoard;
    private int lowestOccupiedRow;
    private int numOfRowsBoard;
    private int numOfColsBoard;
    private int numOfRowsTetramino;
    private int numOfColsTetramino;
    private int simultaneousClearedRows;
    private int score;
    private int numberOfUniqueTetraminos;
    private int tetraminoHeight;
    private final int SCREEN_WIDTH = (int)Screen.getPrimary().getBounds().getWidth();
    private final int SCREEN_HEIGHT = (int)Screen.getPrimary().getBounds().getHeight();
    private final int BLOCK_SIZE = (int)(SCREEN_WIDTH * (15 / 683.0));                  // 30
    private final int START_X_HOLD_TETRAMINO = (int)(BLOCK_SIZE * (2 / 3.0));           // 20
    private final int START_Y_HOLD_TETRAMINO = (int)(BLOCK_SIZE * (2 / 3.0));           // 20
    private final int START_X_BOARD = (int)(BLOCK_SIZE * (25 / 3.0));                   // 250
    private final int START_Y_BOARD = (int)(BLOCK_SIZE * (2 / 3.0));                    // 20
    private final int START_X_NEXT_TETRAMINO = (int)(BLOCK_SIZE * 20);                  // 600
    private final int START_Y_NEXT_TETRAMINO = (int)(BLOCK_SIZE * (2 / 3.0));           // 20
    private final int START_X_UPCOMING_TETRAMINOS = (int)(BLOCK_SIZE * 20);             // 600
    private final int START_Y_UPCOMING_TETRAMINOS = (int)(BLOCK_SIZE * (24 / 3.0));     // 240
    private final int START_X_GAME_INFORMATION = (int)(BLOCK_SIZE * (86 / 3.0));        // 860
    private final int START_Y_GAME_INFORMATION = (int)(BLOCK_SIZE * (2 / 3.0));         // 20
    private int[][] boardMatrix;
    private int[][] tetraminoMatrix;
    private List<int[]> fallingTetraminoBlocksCoordinates;
    private Rectangle[][] blocks;
    private Rectangle[][] holdBlocks;
    private Rectangle[][] nextBlocks;
    private Rectangle[][] upcomingTopBlocks;
    private Rectangle[][] upcomingMiddleBlocks;
    private Rectangle[][] upcomingBottomBlocks;
    private Block tetraminoBlock;
    private Color colorOBlock, colorIBlock, colorSBlock, colorZBlock, colorLBlock, colorJBlock, colorTBlock,
            colorEmptyBlock;
    private Color colorBorder;
    private Stage pauseStage;
    private KeyCode controlButtonMoveLeft;
    private KeyCode controlButtonMoveRight;
    private KeyCode controlButtonSoftDrop;
    private KeyCode controlButtonHardDrop;
    private KeyCode controlButtonRotateClockwise;
    private KeyCode controlButtonRotateCounterclockwise;
    private KeyCode controlButtonHold;
    private KeyCode controlButtonPause;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        // Set the Title of the Stage, Set the Scene to the Stage and Show the Stage
        primaryStage.setTitle("Tetris - Version by Ryan Huffman");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(BLOCK_SIZE * (68 / 3.0));         // 680
        primaryStage.setMaxHeight(BLOCK_SIZE * (68 / 3.0));         // 680
        primaryStage.setMinWidth(BLOCK_SIZE * (130 / 3.0));         // 1300
        primaryStage.setMaxWidth(BLOCK_SIZE * (130 / 3.0));         // 1300
        primaryStage.show();

        drawBoard();

        placeFallingTetraminoIntoBoardMatrix();
        drawFallingTetramino();

        timer = new Timeline(new KeyFrame(Duration.millis(speed), e-> {
            playGame();
        }));

        // Set the CycleCount of the Animation to INDEFINITE (animation doesn't end) and Play the animation
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        //////////////////////////////////////////// CONTROLS ////////////////////////////////////////////////////
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == controlButtonMoveLeft){
                controlMoveLeftButton();
            }
            else if (e.getCode() == controlButtonMoveRight) {
                controlMoveRightButton();
            }
            else if (e.getCode() == controlButtonSoftDrop) {
                controlSoftDropButton();
            }
            else if (e.getCode() == controlButtonHardDrop) {
                controlHardDropButton();
            }
            else if (e.getCode() == controlButtonRotateCounterclockwise) {
                controlRotateCounterclockwiseButton();
            }
            else if (e.getCode() == controlButtonRotateClockwise) {
                controlRotateClockwiseButton();
            }
            else if (e.getCode() == controlButtonHold) {
                controlHoldButton();
            }
            else if (e.getCode() == controlButtonPause) {
                controlPauseButton();
            }
        });

        /////////////////////////////////////// WINDOW CONTROLS //////////////////////////////////////////
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram(primaryStage, false);
        });
    }


    @Override
    public void init() throws Exception {
        // Initialize Random variable
        random = new Random();

        // Set the default controls
        controlButtonMoveLeft = KeyCode.LEFT;
        controlButtonMoveRight = KeyCode.RIGHT;
        controlButtonSoftDrop = KeyCode.DOWN;
        controlButtonHardDrop = KeyCode.UP;
        controlButtonRotateClockwise = KeyCode.D;
        controlButtonRotateCounterclockwise = KeyCode.A;
        controlButtonHold = KeyCode.W;
        controlButtonPause = KeyCode.SPACE;

        numberOfUniqueTetraminos = 7;
        numberOfRowsCleared = 0;
        level = 0;

        // Set the default size of the boardMatrix
        numOfRowsBoard = 20;
        numOfColsBoard = 10;

        isHoldTetraminoBoxEmpty = true;
        holdTetraminoType = 0;
        nextTetraminoType = 0;
        upcomingTetraminoTypeTop = 0;
        upcomingTetraminoTypeMiddle = 0;
        upcomingTetraminoTypeBottom = 0;

        // Initialize UI element sizes
        dimensions_holdTetraminoWidth = BLOCK_SIZE * 6;
        dimensions_holdTetraminoHeight = BLOCK_SIZE * 7;
        dimensions_boardWidth = BLOCK_SIZE * numOfColsBoard;
        dimensions_boardHeight = BLOCK_SIZE * numOfRowsBoard;
        dimensions_nextTetraminoWidth = BLOCK_SIZE * 6;
        dimensions_nextTetraminoHeight = BLOCK_SIZE * 7;
        dimensions_upcomingTetraminosWidth = BLOCK_SIZE * 6;
        dimensions_upcomingTetraminosHeight = (int)(BLOCK_SIZE * (38 / 3.0));
        dimensions_upcomingTetraminoDistanceToMiddle = (int)(BLOCK_SIZE * (13 / 3.0));
        dimensions_upcomingTetraminoDistanceToBottom = (int)(BLOCK_SIZE * (26 / 3.0));
        dimensions_gameInformationWidth = BLOCK_SIZE * 10;
        dimensions_gameInformationHeight = (int)(BLOCK_SIZE * (5 / 3.0));      // 50
        dimensions_gameInformationMargin = (int)(BLOCK_SIZE * (5 / 3.0));      // 50

        // Create GridPane to hold all the elements of the UI
        gridPaneMain = new GridPane();
        gridPaneMain.setPrefSize((BLOCK_SIZE * 20), (BLOCK_SIZE * (68 / 3.0))); // 600x680


        paneHoldTetramino = new Pane();
        drawBorders(paneHoldTetramino, START_X_HOLD_TETRAMINO, START_Y_HOLD_TETRAMINO,
                dimensions_holdTetraminoWidth, dimensions_holdTetraminoHeight);
        drawTitleLabel(paneHoldTetramino, Color.GREY, 36, "HOLD",
                START_X_HOLD_TETRAMINO, START_Y_HOLD_TETRAMINO, dimensions_holdTetraminoWidth);

        paneGameBoard = new Pane();
        drawBorders(paneGameBoard, START_X_BOARD, START_Y_BOARD, dimensions_boardWidth, dimensions_boardHeight);

        paneNextTetramino = new Pane();
        drawBorders(paneNextTetramino, START_X_NEXT_TETRAMINO, START_Y_NEXT_TETRAMINO,
                dimensions_nextTetraminoWidth, dimensions_nextTetraminoHeight);
        drawTitleLabel(paneNextTetramino, Color.GREY, 36, "NEXT",
                START_X_NEXT_TETRAMINO, START_Y_NEXT_TETRAMINO, dimensions_nextTetraminoWidth);

        paneUpcomingTetraminos = new Pane();
        drawBorders(paneUpcomingTetraminos, START_X_UPCOMING_TETRAMINOS, START_Y_UPCOMING_TETRAMINOS,
                dimensions_upcomingTetraminosWidth, dimensions_upcomingTetraminosHeight);

        paneScoreDisplay = new Pane();
        drawGameInformationContainer(paneScoreDisplay, START_X_GAME_INFORMATION, START_Y_GAME_INFORMATION,
                dimensions_gameInformationWidth, dimensions_gameInformationHeight);

        lblScoreValue = new Label();
        drawGameInformationTitleLabel(paneScoreDisplay, Color.RED, lblScoreValue, "Score: ",
                48, START_X_GAME_INFORMATION, START_Y_GAME_INFORMATION);
        drawGameInformationValueLabel(paneScoreDisplay, lblScoreValue, Color.RED, 48, String.valueOf(score),
                START_X_GAME_INFORMATION, START_Y_GAME_INFORMATION, dimensions_gameInformationWidth);

        paneLevelDisplay = new Pane();
        drawGameInformationContainer(paneLevelDisplay, START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight + dimensions_gameInformationMargin),
                dimensions_gameInformationWidth, dimensions_gameInformationHeight);

        lblLevelValue = new Label();
        levelString = String.format("%02d", level);
        drawGameInformationTitleLabel(paneLevelDisplay, Color.RED, lblLevelValue, "Level: ",
                48, START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight +
                        dimensions_gameInformationMargin));
        drawGameInformationValueLabel(paneLevelDisplay, lblLevelValue, Color.RED, 48,
                levelString, START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight +
                        dimensions_gameInformationMargin), dimensions_gameInformationWidth);

        paneClearedLinesDisplay = new Pane();
        drawGameInformationContainer(paneClearedLinesDisplay, START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + (2 * dimensions_gameInformationHeight) +
                        (2 * dimensions_gameInformationMargin)), dimensions_gameInformationWidth,
                dimensions_gameInformationHeight);

        lblClearedLinesValue = new Label();
        drawGameInformationTitleLabel(paneClearedLinesDisplay, Color.RED, lblClearedLinesValue,
                "Lines: ", 48, START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + (2 * dimensions_gameInformationHeight) +
                        (2 * dimensions_gameInformationMargin)));
        drawGameInformationValueLabel(paneClearedLinesDisplay, lblClearedLinesValue, Color.RED,
                48, String.valueOf(numberOfRowsCleared), START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + (2 * dimensions_gameInformationHeight) +
                        (2 * dimensions_gameInformationMargin)), dimensions_gameInformationWidth);

        // Add the game board to the main Grid Pane
        gridPaneMain.getChildren().addAll(paneHoldTetramino, paneGameBoard, paneNextTetramino,
                paneUpcomingTetraminos, paneScoreDisplay, paneLevelDisplay, paneClearedLinesDisplay);

        // Initialize the Scene
        scene = new Scene(gridPaneMain, (BLOCK_SIZE * (80 / 3.0)), (BLOCK_SIZE * (68 / 3.0)));  // 800X680

        // Initialize the default beginning speed
        // Attempting to match NES Tetris speed (800 = speed of level 00, 720 = level 01, 640 = level 02)
        speed = 800;

        // Initialize the boardMatrix
        boardMatrix = new int[numOfRowsBoard][numOfColsBoard];

        // Set the default size of the tetraminoMatrix
        tetraminoMatrix = new int[numOfRowsTetramino][numOfColsTetramino];

        // Initialize first falling Tetramino
        fallingTetraminoType = 1 + random.nextInt(numberOfUniqueTetraminos);
        Color newColor = getBlockColor(fallingTetraminoType);   // ADDED - NEW BLOCKS HAVE NO COLOR
        tetraminoBlock = new Block(newColor);                   // ADDED - NEW BLOCKS HAVE NO COLOR
        tetraminoMatrix = tetraminoBlock.getBlockMatrix(fallingTetraminoType, orientation);
        startRowBoard = 0;
        startColBoard = (numOfColsBoard - 1) / 2;           // ORIGINAL LINE OF CODE
        currentRowBoard = startRowBoard;
        currentColBoard = startColBoard;
        fallingTetraminoBlocksCoordinates = new ArrayList<>();
        fallingTetraminoBlocksCoordinates = getFallingTetraminoBlocksCoordinatesAsArrayList(tetraminoMatrix);
        tetraminoHeight = getFallingTetraminoHeight();
        lowestOccupiedRow = boardMatrix.length - 1;

        blocks = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        /*
         *  NEED TO CHANGE BELOW TO ACCOUNT FOR CUSTOM TETRAMINOS
         */
        holdBlocks = new Rectangle[4][4];
        nextBlocks = new Rectangle[4][4];
        upcomingTopBlocks = new Rectangle[4][4];
        upcomingMiddleBlocks = new Rectangle[4][4];
        upcomingBottomBlocks = new Rectangle[4][4];
        /*
         *  NEED TO CHANGE ABOVE TO ACCOUNT FOR CUSTOM TETRAMINOS
         */

        nextTetraminoType = generateRandomTetraminoType();
        upcomingTetraminoTypeTop = generateRandomTetraminoType();
        upcomingTetraminoTypeMiddle = generateRandomTetraminoType();
        upcomingTetraminoTypeBottom = generateRandomTetraminoType();

        // Initialize default Colors of the different tetramino blocks
        colorOBlock = Color.YELLOW;
        colorIBlock = Color.PURPLE;
        colorSBlock = Color.ORANGE;
        colorZBlock = Color.RED;
        colorLBlock = Color.GREEN;
        colorJBlock = Color.BLUE;
        colorTBlock = Color.PINK;

        // Initialize the Color of the game board
        colorEmptyBlock = Color.WHITE;

        // Initialize the Color of the block borders
        colorBorder = Color.BLACK;
    }


    /**
     * Set which keyboard button moves the Tetramino left one column
     * @param key KeyCode parameter - KeyCode for the keyboard button to assign
     *            moving Tetramino left one column to
     */
    private void setControlButtonMoveLeft(KeyCode key) {
        controlButtonMoveLeft = key;
    }


    /**
     * Set which keyboard button moves the Tetramino right one column
     * @param key KeyCode parameter - KeyCode for the keyboard button to assign
     *            moving Tetramino right one column to
     */
    private void setControlButtonMoveRight(KeyCode key) {
        controlButtonMoveRight = key;
    }


    /**
     * Set which keyboard button moves the Tetramino down one row
     * @param key KeyCode parameter - KeyCode for the keyboard button to assign
     *            moving Tetramino down one row to
     */
    private void setControlButtonSoftDrop(KeyCode key) {
        controlButtonSoftDrop = key;
    }


    /**
     * Set which keyboard button moves the Tetramino down as many rows as possible
     * until the Tetramino is in place
     * @param key KeyCode parameter - KeyCode for the keyboard button to assign
     *            moving Tetramino down as many rows as possible until the Tetramino is in place
     */
    private void setControlButtonHardDrop(KeyCode key) {
        controlButtonHardDrop = key;
    }

    /**
     * Set which keyboard button rotates the Tetramino counterclockwise
     * @param key KeyCode parameter - KeyCode for the keyboard button to assign
     *            rotating Tetramino counterclockwise
     */
    private void setControlButtonRotateCounterclockwise(KeyCode key) {
        controlButtonRotateCounterclockwise = key;
    }


    /**
     * Set which keyboard button rotates the Tetramino clockwise
     * @param key KeyCode parameter - KeyCode for the keyboard button to assign
     *            rotating Tetramino clockwise
     */
    private void setControlButtonRotateClockwise(KeyCode key) {
        controlButtonRotateClockwise = key;
    }


    /**
     * Set which keyboard button moves the current falling Tetramino into the hold Tetramino box or
     * moves the Tetramino in the hold Tetramino box to the current falling Tetramino
     * @param key KeyCode parameter - KeyCode for the keyboard button to assign
     *            moving the current falling Tetramino into the hold Tetramino box or
     *            moves the Tetramino in the hold Tetramino box to the current falling Tetramino
     */
    private void setControlButtonHold(KeyCode key) {
        controlButtonHold = key;
    }


    /**
     * Set which keyboard button pauses/resumes the game
     * @param key KeyCode parameter - KeyCode for the keyboard button to assign
     *            to pausing/resuming the game
     */
    private void setControlButtonPause(KeyCode key) {
        controlButtonPause = key;
    }


    /**
     * Sets the color of O-Blocks
     * @param color Color parameter - set O-Blocks to this Color
     */
    private void setColorOBlock(Color color) {
        colorOBlock = color;
    }


    /**
     * Sets the color of I-Blocks
     * @param color Color parameter - set I-Blocks to this Color
     */
    private void setColorIBlock(Color color) {
        colorIBlock = color;
    }


    /**
     * Sets the color of S-Blocks
     * @param color Color parameter - set S-Blocks to this Color
     */
    private void setColorSBlock(Color color) {
        colorSBlock = color;
    }


    /**
     * Sets the color of Z-Blocks
     * @param color Color parameter - set Z-Blocks to this Color
     */
    private void setColorZBlock(Color color) {
        colorZBlock = color;
    }


    /**
     * Sets the color of J-Blocks
     * @param color Color parameter - set J-Blocks to this Color
     */
    private void setColorJBlock(Color color) {
        colorJBlock = color;
    }


    /**
     * Sets the color of L-Blocks
     * @param color Color parameter - set L-Blocks to this Color
     */
    private void setColorLBlock(Color color) {
        colorLBlock = color;
    }


    /**
     * Sets the color of T-Blocks
     * @param color Color parameter - set T-Blocks to this Color
     */
    private void setColorTBlock(Color color) {
        colorTBlock = color;
    }


    /**
     * Sets the color of empty blocks (game board color)
     * @param color Color parameter - set empty blocks (game board color) to this Color
     */
    private void setColorEmptyBlock(Color color) {
        colorEmptyBlock = color;
    }


    /**
     * Sets the color of Block borders
     * @param color Color parameter - set Block borders to this Color
     */
    private void setColorBorder(Color color) {
        colorBorder = color;
    }


    /**
     * Draws the specified value to the specified Label in the specified Pane
     * at the specified location and size
     * @param pane Pane parameter - Pane to draw value in
     * @param lbl Label paramter - Label to draw value in
     * @param color Color parameter - Color to draw the value in
     * @param fontSize int parameter - font size to draw the value in
     * @param labelValue String parameter - String representation of the value to draw
     * @param startX int parameter - x-coordinate to start drawing in specified Pane
     * @param startY int parameter - y-coordinate to start drawing in specified Pane
     * @param width int parameter - width in pixels of the drawn container
     */
    private void drawGameInformationValueLabel(
            Pane pane, Label lbl, Color color, int fontSize, String labelValue, int startX, int startY, int width
    ) {
        Font font = new Font(fontSize);

        lbl.setLayoutX(((startX + (double)width / 2) + 10));
        lbl.setLayoutY((startY - 10));
        lbl.setTextFill(color);
        lbl.setFont(font);
        lbl.setText(labelValue);

        pane.getChildren().add(lbl);
    }


    /**
     * Draws the Label title and sets the labelFor property of the specified Label in the specified Pane
     * at the specified location and size
     * @param pane Pane parameter - Pane to draw the Label in
     * @param color Color parameter - Color to draw the Label in
     * @param labelFor Label parameter - Label to set the labelFor property to
     * @param labelText String parameter - String that makes up the text of the Label
     * @param fontSize int parameter - font size of the text of the specified Label
     * @param startX int parameter - x-coordinate to start drawing in the specified Pane
     * @param startY int parameter - y-coordinate to start drawing in the specified Pane
     */
    private void drawGameInformationTitleLabel(
            Pane pane, Color color, Label labelFor, String labelText, int fontSize, int startX, int startY
    ) {
        Label lbl = new Label(labelText);
        Font font = new Font(fontSize);

        lbl.setLabelFor(labelFor);
        lbl.setLayoutX((startX + 10));
        lbl.setLayoutY((startY - 10));
        lbl.setTextFill(color);
        lbl.setFont(font);

        pane.getChildren().add(lbl);
    }


    /**
     * Draws an individual container to display game information in
     * @param pane Pane parameter - Pane to draw the container in
     * @param startX int parameter - x-coordinate to start drawing the container in the specified Pane
     * @param startY int parameter - y-coordinate to start drawing the container in the specified Pane
     * @param width int parameter - width in pixels of the drawn container
     * @param height int parameter - height in pixels of the drawn container
     */
    private void drawGameInformationContainer(Pane pane, int startX, int startY, int width, int height) {
        drawBorders(pane, startX, startY, width, height);
        Line dividingLine = new Line(
                (startX + ((double)width / 2)),
                startY,
                (startX + ((double)width / 2)),
                (startY + height)
        );
        pane.getChildren().add(dividingLine);
    }


    /**
     * Draws the title Label for a game information display element
     * @param pane Pane parameter - Pane to draw the Label in
     * @param color Color parameter - Color to draw the Label in
     * @param fontSize int parameter - font size of the text of the specified Label
     * @param labelText String parameter - String text to draw in the specified Label
     * @param startX int parameter - x-coordinate to start drawing in the specified Pane
     * @param startY int parameter - y-coordinate to start drawing in the specified Pane
     * @param width int parameter - width in pixels of container for the Label
     */
    private void drawTitleLabel(
            Pane pane, Color color, int fontSize, String labelText, int startX, int startY, int width
    ) {
        Font font = new Font(fontSize);
        Label lbl = new Label(labelText);

        lbl.setLabelFor(pane);
        lbl.setLayoutX(
                (startX + ((double)width / 4.5))
        );
        lbl.setLayoutY((startY));
        lbl.setTextFill(color);
        lbl.setFont(font);
        pane.getChildren().add(lbl);
    }


    /**
     * Draws borders for UI elements: hold Tetramino box, game board, next Tetramino box, upcoming Tetraminos box
     * @param pane Pane parameter - Pane to draw the specified borders in
     * @param startX int parameter - x-coordinate to start drawing in the specified Pane
     * @param startY int parameter - y-coordinate to start drawing in the specified Pane
     * @param width int parameter - width in pixels of the box the borders form in the specified Pane
     * @param height int parameter - height in pixels of the box the borders form in the specified Pane
     */
    private void drawBorders(Pane pane, int startX, int startY, int width, int height) {
        // Create and Initialize four Lines for borders
        Line borderLeft = new Line(
                startX,
                startY,
                startX,
                (startY + height)
        );
        Line borderRight = new Line(
                (startX + width),
                startY,
                (startX + width),
                (startY + height)
        );
        Line borderTop = new Line(
                startX,
                startY,
                (startX + width),
                startY
        );
        Line borderBottom = new Line(
                startX,
                (startY + height),
                (startX + width),
                (startY + height)
        );

        // Add the borders to the Pane
        pane.getChildren().addAll(
                borderLeft, borderRight, borderTop, borderBottom
        );
    }


    /**
     * Erases all Rectangles from paneNextTetramino
     */
    private void eraseNextTetramino() {
        for (int row = 0; row < nextBlocks.length; row++) {
            for (int col = 0; col < nextBlocks[row].length; col++) {
                paneNextTetramino.getChildren().remove(nextBlocks[row][col]);
            }
        }
    }


    /**
     * Draws the next Tetramino to be generated in paneNextTetramino
     * @param matrix int[][] parameter - 2D int matrix to be looped through to draw the next Tetramino
     */
    private void drawNextTetramino(int[][] matrix) {
        Rectangle currentBlock;

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] != 0) {
                    currentBlock = new Rectangle(30 + START_X_NEXT_TETRAMINO + (BLOCK_SIZE * col),
                            60 + START_Y_NEXT_TETRAMINO + (BLOCK_SIZE * row), BLOCK_SIZE, BLOCK_SIZE);

                    currentBlock.setFill(getBlockColor(nextTetraminoType));

                    currentBlock.setStroke(colorBorder);

                    nextBlocks[row][col] = currentBlock;

                    paneNextTetramino.getChildren().add(currentBlock);
                }
            }
        }
    }


    /**
     * Generates random Tetramino that will be the next Tetramino to fall and draws it in paneNextTetramino
     */
    private void generateNextTetramino() {
        eraseNextTetramino();
        nextTetraminoType = upcomingTetraminoTypeTop;

        Color newColor = getBlockColor(nextTetraminoType);
        Block nextTetraminoBlock = new Block(newColor);

        int[][] nextTetraminoMatrix = nextTetraminoBlock.getBlockMatrix(nextTetraminoType, 0);

        drawNextTetramino(nextTetraminoMatrix);
    }


    private void eraseUpcomingTetraminoTop() {
        for (int row = 0; row < upcomingTopBlocks.length; row++) {
            for (int col = 0; col < upcomingTopBlocks[row].length; col++) {
                paneUpcomingTetraminos.getChildren().remove(upcomingTopBlocks[row][col]);
            }
        }
    }


    private void drawUpcomingTetraminoTop(int[][] matrix) {
        Rectangle currentBlock;

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] != 0) {
                    currentBlock = new Rectangle(30 + START_X_UPCOMING_TETRAMINOS + (BLOCK_SIZE * col),
                            START_Y_UPCOMING_TETRAMINOS + (BLOCK_SIZE * row), BLOCK_SIZE, BLOCK_SIZE);

                    currentBlock.setFill(getBlockColor(upcomingTetraminoTypeTop));

                    currentBlock.setStroke(colorBorder);

                    upcomingTopBlocks[row][col] = currentBlock;

                    paneUpcomingTetraminos.getChildren().add(currentBlock);
                }
            }
        }
    }


    private void generateUpcomingTetraminoTop() {
        eraseUpcomingTetraminoTop();
        upcomingTetraminoTypeTop = upcomingTetraminoTypeMiddle;

        Color color = getBlockColor(upcomingTetraminoTypeTop);
        Block upcomingTetraminoTopBlock = new Block(color);

        int[][] upcomingTetraminTopMatrix = upcomingTetraminoTopBlock.getBlockMatrix(upcomingTetraminoTypeTop, 0);

        drawUpcomingTetraminoTop(upcomingTetraminTopMatrix);
    }


    private void eraseUpcomingTetraminoMiddle() {
        for (int row = 0; row < upcomingMiddleBlocks.length; row++) {
            for (int col = 0; col < upcomingMiddleBlocks[row].length; col++) {
                paneUpcomingTetraminos.getChildren().remove(upcomingMiddleBlocks[row][col]);
            }
        }
    }


    private void drawUpcomingTetraminoMiddle(int[][] matrix) {
        Rectangle currentBlock;

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] != 0) {
                    currentBlock = new Rectangle(30 + START_X_UPCOMING_TETRAMINOS + (BLOCK_SIZE * col),
                            (START_Y_UPCOMING_TETRAMINOS + dimensions_upcomingTetraminoDistanceToMiddle)
                                    + (BLOCK_SIZE * row), BLOCK_SIZE, BLOCK_SIZE);

                    currentBlock.setFill(getBlockColor(upcomingTetraminoTypeMiddle));

                    currentBlock.setStroke(colorBorder);

                    upcomingMiddleBlocks[row][col] = currentBlock;

                    paneUpcomingTetraminos.getChildren().add(currentBlock);
                }
            }
        }
    }


    private void generateUpcomingTetraminoMiddle() {
        eraseUpcomingTetraminoMiddle();
        upcomingTetraminoTypeMiddle = upcomingTetraminoTypeBottom;

        Color color = getBlockColor(upcomingTetraminoTypeMiddle);
        Block upcomingTetraminoMiddleBlock = new Block(color);

        int[][] upcomingTetraminMiddleMatrix = upcomingTetraminoMiddleBlock.getBlockMatrix(upcomingTetraminoTypeMiddle, 0);

        drawUpcomingTetraminoMiddle(upcomingTetraminMiddleMatrix);
    }


    private void eraseUpcomingTetraminoBottom() {
        for (int row = 0; row < upcomingBottomBlocks.length; row++) {
            for (int col = 0; col < upcomingBottomBlocks[row].length; col++) {
                paneUpcomingTetraminos.getChildren().remove(upcomingBottomBlocks[row][col]);
            }
        }
    }


    private void drawUpcomingTetraminoBottom(int[][] matrix) {
        Rectangle currentBlock;

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] != 0) {
                    currentBlock = new Rectangle(30 + START_X_UPCOMING_TETRAMINOS + (BLOCK_SIZE * col),
                            (START_Y_UPCOMING_TETRAMINOS + dimensions_upcomingTetraminoDistanceToBottom)
                                    + (BLOCK_SIZE * row), BLOCK_SIZE, BLOCK_SIZE);

                    currentBlock.setFill(getBlockColor(upcomingTetraminoTypeBottom));

                    currentBlock.setStroke(colorBorder);

                    upcomingBottomBlocks[row][col] = currentBlock;

                    paneUpcomingTetraminos.getChildren().add(currentBlock);
                }
            }
        }
    }


    private void generateUpcomingTetraminoBottom() {
        eraseUpcomingTetraminoBottom();
        upcomingTetraminoTypeBottom = generateRandomTetraminoType();

        Color color = getBlockColor(upcomingTetraminoTypeBottom);
        Block upcomingTetraminoBottomBlock = new Block(color);

        int[][] upcomingTetraminoBottomMatrix = upcomingTetraminoBottomBlock.getBlockMatrix(upcomingTetraminoTypeBottom, 0);

        drawUpcomingTetraminoBottom(upcomingTetraminoBottomMatrix);
    }


    /**
     * Generates random int from 1 to the value corresponding to the number of unique Tetramino types
     * @return int value - (from 1 to the number of unique Tetramino types)
     */
    private int generateRandomTetraminoType() {
        return (1 + random.nextInt(numberOfUniqueTetraminos));
    }


    private void startTimer() {
        // timestamp = System.currentTimeMillis() - timeFraction;
    }


    /**
     * Closes and exits the application
     * @param stage Stage variable of the current active Stage
     * @param isPaused boolean value (true - game is paused, false - game is not paused)
     */
    private void closeProgram(Stage stage, boolean isPaused) {
        if (!isPaused) {
            timer.pause();
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Quit?",
                ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Close Tetris");
        confirmation.setHeaderText("Are you sure?");
        confirmation.setContentText("Do you want to close and exit the application?");
        confirmation.showAndWait();

        if (confirmation.getResult() == ButtonType.YES) {
            Platform.exit();
        } else {
            if (!isPaused) {
                timer.play();
            }
        }
    }


    /**
     * Controls what happens when a user hits the "move falling Tetramino left one column" button
     */
    private void controlMoveLeftButton() {
        if (canFallingTetraminoMoveLeftOneColumn()) {
            moveFallingTetraminoLeftOneColumn();
        }
        placeFallingTetraminoIntoBoardMatrix();
        drawFallingTetramino();
    }


    /**
     * Controls what happens when a user hits the "move falling Tetramino right one column" button
     */
    private void controlMoveRightButton() {
        if (canFallingTetraminoMoveRightOneColumn()) {
            moveFallingTetraminoRightOneColumn();
        }
        placeFallingTetraminoIntoBoardMatrix();
        drawFallingTetramino();
    }


    /**
     * Controls what happens when a user hits the "move falling Tetramino down one row" button
     */
    private void controlSoftDropButton() {
        if (canFallingTetraminoMoveDownOneRow()) {
            moveFallingTetraminoDownOneRow();
        }
        placeFallingTetraminoIntoBoardMatrix();
        drawFallingTetramino();
    }


    /**
     * Controls what happens when a user hits the
     * "move falling Tetramino down as many rows as possible until it is in place" button
     */
    private void controlHardDropButton() {
        while (canFallingTetraminoMoveDownOneRow()) {
            moveFallingTetraminoDownOneRow();
        }
        placeFallingTetraminoIntoBoardMatrix();
        drawFallingTetramino();
    }


    /**
     * Controls what happens when a user hits the "rotate falling Tetramino left counterclockwise" button
     */
    private void controlRotateCounterclockwiseButton() {
        if (canFallingTetraminoRotateCounterClockwise()) {
            rotateFallingTetraminoCounterClockwise();
        } else {
            eraseFallingTetramino();
        }
        placeFallingTetraminoIntoBoardMatrix();
        drawFallingTetramino();
    }


    /**
     * Controls what happens when a user hits the "rotate falling Tetramino clockwise" button
     */
    private void controlRotateClockwiseButton() {
        if (canFallingTetraminoRotateClockwise()) {
            rotateFallingTetraminoClockwise();
        } else {
            eraseFallingTetramino();
        }
        placeFallingTetraminoIntoBoardMatrix();
        drawFallingTetramino();
    }


    /**
     * Controls what happens when a user hits the
     * "move falling Tetramino into hold Tetramino box" /
     * "make the Tetramino in the hold Tetramino box, the current falling Tetramino" button
     */
    private void controlHoldButton() {
        if (isHoldTetraminoBoxEmpty) {
            reserveFallingTetraminoInHoldBox();
        } else {
            generateSpecifiedFallingTetramino(holdTetraminoType);
        }
    }


    /**
     * Controls what happens when a user hits the "pause" button
     */
    private void controlPauseButton() {
        if (timer.getStatus() == Animation.Status.RUNNING) {
            pauseGame();
        } else {
            closePauseMenu();
            // resumeGame();
        }
    }


    /**
     * CURRENTLY EMPTY METHOD - Unpauses game and resumes play
     */
    private void resumeGame() {
        // timer.play();
    }


    /**
     * Pauses the current game and opens the pause menu
     */
    private void pauseGame() {
        timer.pause();
        openPauseMenu();
    }


    /**
     * NEED TO REFACTOR (CREATE PAUSE MENU IN SEPARATE METHOD) - Creates and displays the pause menu
     */
    private void openPauseMenu() {
        GridPane gridPanePauseMenu = new GridPane();
        VBox vBoxPauseMenu = new VBox();

        Button btnResume = new Button("RESUME");
        Button btnReset = new Button("RESET");
        Button btnOptions = new Button("OPTIONS");
        Button btnCustomize = new Button("CUSTOMIZE");
        Button btnExit = new Button("EXIT");

        vBoxPauseMenu.getChildren().addAll(btnResume, btnReset, btnOptions, btnCustomize, btnExit);
        gridPanePauseMenu.getChildren().add(vBoxPauseMenu);

        // ORIGINAL SIZE: (BLOCK_SIZE * (60 / 3.0)), (BLOCK_SIZE * (40 / 3.0))
        Scene scenePauseMenu = new Scene(gridPanePauseMenu, (BLOCK_SIZE * (24 / 3.0)), (BLOCK_SIZE * (27 / 3.0)));
        pauseStage = new Stage();
        pauseStage.setTitle("Pause Menu");
        pauseStage.setScene(scenePauseMenu);
        pauseStage.show();

        scenePauseMenu.getStylesheets().add("styling/pauseMenu.css");

        pauseStage.setOnCloseRequest(e -> {
            e.consume();
            closePauseMenu();
        });

        scenePauseMenu.setOnKeyPressed(e -> {
            if (e.getCode() == controlButtonPause) {
                scenePauseMenu.getWindow().hide();
                resumeGame();
            }
        });

        btnResume.setOnMouseClicked(e -> {
            closePauseMenu();
        });

        btnResume.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                scenePauseMenu.getWindow().hide();
                timer.play();
            }
        });

        btnExit.setOnMouseClicked(e -> {
            closeProgram(pauseStage, true);
        });
    }


    /**
     * Closes the pause menu Stage and restarts the game
     */
    private void closePauseMenu() {
        pauseStage.close();
        timer.play();
    }


    /**
     * Update score and its Label (lblScoreValue)
     */
    private void incrementAndDisplayScore() {
        paneScoreDisplay.getChildren().remove(lblScoreValue);
        score += getValueToAddToScore();
        lblScoreValue.setText(String.valueOf(score));
        paneScoreDisplay.getChildren().add(lblScoreValue);
    }


    /**
     * NEED TO REFACTOR (speed VALUE SHOULD BE DECREMENTED BY 80 UNTIL speed = 80) -
     * Increments level and speeds up game play
     */
    private void incrementLevel() {
        timer.stop();
        incrementAndDisplayLevel();

        // THIS IS NOT CURRENTLY WORKING - FIGURE OUT WHY AND REFACTOR
        if (speed - 80 >= 80) {
            speed -= 80;
        }

        timer.setCycleCount(Timeline.INDEFINITE);
        timer.rateProperty().bind(new SimpleDoubleProperty(1.0).multiply(1.1));
        timer.play();
    }


    /**
     * Returns whether it is time to increment the current user level
     * @return boolean value (true - call incrementLevel(), false - not time to call incrementLevel())
     */
    private boolean shouldLevelBeIncremented() {
        return (numberOfRowsCleared % 10 == 0);
    }


    /**
     * Runs the game
     */
    private void playGame() {
        if (canFallingTetraminoMoveDownOneRow()) {
            moveFallingTetraminoDownOneRow();
            placeFallingTetraminoIntoBoardMatrix();
        } else {
            placeFallingTetraminoIntoBoardMatrix();
            drawFallingTetramino();

            if (wasARowCompleted()) {
                setLowestOccupiedRowAfterClearingARow();
                eraseBoard();
                createBoardBlocks();
                drawBoard();
                incrementAndDisplayScore();
            }

            generateNewFallingTetraminoFromNextTetramino();
            generateNextTetramino();
            generateUpcomingTetraminoTop();
            generateUpcomingTetraminoMiddle();
            generateUpcomingTetraminoBottom();

            if (!placeFallingTetraminoIntoBoardMatrix()) {
                lostGame();
            }
        }
        drawFallingTetramino();
    }


    /**
     * NEED TO REFACTOR (NOT WORKING PROPERLY) -
     * Generates the specified Tetramino to be the falling Tetramino and erases the Tetramino from the
     * hold Tetramino box if the hold Tetramino box is not currently empty
     * @param tetraminoType int parameter - int representation of the type of Tetramino to generate
     */
    private void generateSpecifiedFallingTetramino(int tetraminoType) {
        eraseFallingTetramino();
        eraseHoldTetramino();
        eraseHoldTetramino();
        isHoldTetraminoBoxEmpty = true;

        fallingTetraminoType = tetraminoType;
        orientation = 0;

        Color newColor = getBlockColor(fallingTetraminoType);
        tetraminoBlock = new Block(newColor);

        tetraminoMatrix = tetraminoBlock.getBlockMatrix(fallingTetraminoType, orientation);

        currentRowBoard = startRowBoard;
        currentColBoard = startColBoard;

        fallingTetraminoBlocksCoordinates = new ArrayList<>();
        fallingTetraminoBlocksCoordinates = getFallingTetraminoBlocksCoordinatesAsArrayList(tetraminoMatrix);

        tetraminoHeight = getFallingTetraminoHeight();
    }


    /**
     * NEED TO REFACTOR (NOT WORKING PROPERLY) -
     * Erases the Rectangles that make up the Tetramino in the hold Tetramino box
     */
    private void eraseHoldTetramino() {
        for (int row = 0; row < holdBlocks.length; row++) {
            for (int col = 0; col < holdBlocks[row].length; col++) {
                paneGameBoard.getChildren().remove(holdBlocks[row][col]);
                holdBlocks[row][col] = null;
            }
        }
    }


    /**
     * Takes the current falling Tetramino, draws in the hold Tetramino box so that it can be
     * summoned on command to be the current falling Tetramino
     */
    private void reserveFallingTetraminoInHoldBox() {
        if (isHoldTetraminoBoxEmpty) {
            eraseFallingTetramino();
            holdTetraminoType = fallingTetraminoType;
            orientation = 0;
            Color newColor = getBlockColor(holdTetraminoType);
            tetraminoBlock = new Block(newColor);

            tetraminoMatrix = tetraminoBlock.getBlockMatrix(holdTetraminoType, orientation);

            Rectangle currentBlock;

            for (int row = 0; row < tetraminoMatrix.length; row++) {
                for (int col = 0; col < tetraminoMatrix[row].length; col++) {
                    if (tetraminoMatrix[row][col] != 0) {
                        currentBlock = new Rectangle(30 + START_X_HOLD_TETRAMINO + (BLOCK_SIZE * col),
                                60 + START_Y_HOLD_TETRAMINO + (BLOCK_SIZE * row), BLOCK_SIZE, BLOCK_SIZE);

                        currentBlock.setFill(getBlockColor(holdTetraminoType));

                        currentBlock.setStroke(colorBorder);

                        holdBlocks[row][col] = currentBlock;

                        paneHoldTetramino.getChildren().add(currentBlock);
                    }
                }
            }

            isHoldTetraminoBoxEmpty = false;

            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////// THIS BELOW WILL NEED TO BE CHANGED! //////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////// THIS BELOW WILL NEED TO BE CHANGED! //////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////// THIS BELOW WILL NEED TO BE CHANGED! //////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////// THIS BELOW WILL NEED TO BE CHANGED! //////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            generateNewFallingTetraminoFromNextTetramino();
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////// THIS ABOVE WILL NEED TO BE CHANGED! //////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////// THIS ABOVE WILL NEED TO BE CHANGED! //////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////// THIS ABOVE WILL NEED TO BE CHANGED! //////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////// THIS ABOVE WILL NEED TO BE CHANGED! //////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////
        }
    }


    /**
     * Update number of lines completed. Increment by one for each completed row and
     * updating the Label lblClearedLinesValue
     */
    private void incrementAndDisplayClearedLines() {
        paneClearedLinesDisplay.getChildren().remove(lblClearedLinesValue);
        numberOfRowsCleared++;

        if (shouldLevelBeIncremented()) {
            incrementLevel();
        }
        lblClearedLinesValue.setText(String.valueOf(numberOfRowsCleared));
        paneClearedLinesDisplay.getChildren().add(lblClearedLinesValue);
    }


    /**
     * Update the current user level. Incrementing by one and updating the Label lblLevelValue
     */
    private void incrementAndDisplayLevel() {
        paneLevelDisplay.getChildren().remove(lblLevelValue);
        level++;
        levelString = String.format("%02d", level);
        lblLevelValue.setText(levelString);
        paneLevelDisplay.getChildren().add(lblLevelValue);
    }


    /**
     * Calculates the score to add to the current score.
     * Score to add depends on simultaneousClearedRows and level values.
     * @return current user level * value based on the number of simultaneously cleared lines
     */
    private int getValueToAddToScore() {
        int simultaneousLineClearScore = 0;

        switch (simultaneousClearedRows) {
            case 1:
                simultaneousLineClearScore = 40;
                break;
            case 2:
                simultaneousLineClearScore = 100;
                break;
            case 3:
                simultaneousLineClearScore = 300;
                break;
            case 4:
                simultaneousLineClearScore = 1200;
                break;
        }

        return ((level + 1) * simultaneousLineClearScore);
    }


    /**
     * Loop through the int game board matrix looking for completed rows,
     * calling eraseRow(int completedRowNumber) on completed rows and
     * incrementing simultaneousClearedRows
     * @return boolean value of if any row was completed
     */
    private boolean wasARowCompleted() {
        simultaneousClearedRows = 0;
        int row = numOfRowsBoard - 1;

        while (row >= lowestOccupiedRow + simultaneousClearedRows) {
            boolean wasLineCompleted = false;

            for (int col = 0; col < boardMatrix[row].length; col++) {
                if (boardMatrix[row][col] == 0) {
                    col += boardMatrix[row].length;
                }

                if ((col == (boardMatrix[row].length - 1)) && boardMatrix[row][col] != 0) {
                    wasLineCompleted = true;
                }
            }
            if (wasLineCompleted) {
                simultaneousClearedRows++;
                eraseRow(row);
                incrementAndDisplayClearedLines();
                row = numOfRowsBoard - 1;
            } else {
                row--;
            }
        }

        return simultaneousClearedRows != 0;
    }


    /**
     * Removes specified row from int[][] boardMatrix and
     * remove corresponding Rectangles from paneGameBoard.
     * @param completedRowNumber the row index of int[][] boardMatrix that has been completed
     */
    private void eraseRow(int completedRowNumber) {
        for (int col = 0; col < blocks[completedRowNumber].length; col++) {
            paneGameBoard.getChildren().remove(blocks[completedRowNumber][col]);
            blocks[completedRowNumber][col] = null;
        }

        /*
         *
         *  MIGHT NEED TO CHANGE THE BELOW LINE TO JUST COPY THE BOARD MATRIX
         *  SO THAT YOU COULD HAVE ROWS WITH EXTRA COLUMNS IF YOU WANTED TO
         *
         */
        int[][] tempBoardMatrix = new int[boardMatrix.length][boardMatrix[0].length];
        /*
         *
         *  MIGHT NEED TO CHANGE THE ABOVE LINE TO JUST COPY THE BOARD MATRIX
         *  SO THAT YOU COULD HAVE ROWS WITH EXTRA COLUMNS IF YOU WANTED TO
         *
         */

        for (int row = tempBoardMatrix.length - 1; row > completedRowNumber; row--) {
            for (int col = 0; col < tempBoardMatrix[row].length; col++) {
                tempBoardMatrix[row][col] = boardMatrix[row][col];
            }
        }

        for (int row = 0; row < completedRowNumber; row++) {
            for (int col = 0; col < tempBoardMatrix[row].length; col++) {
                tempBoardMatrix[(row + 1)][col] = boardMatrix[row][col];
            }
        }

        for (int col = 0; col < tempBoardMatrix[0].length; col++) {
            tempBoardMatrix[0][col] = 0;
        }

        boardMatrix = tempBoardMatrix;
    }


    /**
     * Sets lowestOccupiedRow by looping through int[][] boardMatrix from the highest value index row
     * to the lowest value index row, looking for the first encountered empty row and
     * adding one to that index
     */
    private void setLowestOccupiedRowAfterClearingARow() {
        boolean wasRowEmpty;

        for (int row = boardMatrix.length - 1; row >= 0; row--) {
            wasRowEmpty = false;
            for (int col = 0; col < boardMatrix[row].length; col++) {
                if (boardMatrix[row][col] != 0) {
                    col += boardMatrix[row].length;
                }

                if (col == (boardMatrix[row].length - 1) && boardMatrix[row][col] == 0) {
                    wasRowEmpty = true;
                }
            }
            if (wasRowEmpty) {
                lowestOccupiedRow = Math.min(row + 1, boardMatrix.length - 1);
                row = -1;
            }
        }
    }


    /**
     * Controls what happens when a user loses the game (does not get to when level would switch to level 29)
     */
    private void lostGame() {
        timer.stop();

        Alert gameLostAlert = new Alert(Alert.AlertType.INFORMATION, "YOU LOST!", ButtonType.OK);
        gameLostAlert.setTitle("GAME OVER");
        gameLostAlert.setHeaderText("You lost! " +
                "\n\tscore\t     : " + score +
                "\n\tlevel\t\t     : " + levelString +
                "\n\tcleared lines : " + numberOfRowsCleared);
        gameLostAlert.show();

        if (gameLostAlert.getResult() == ButtonType.OK) {
            Platform.exit();
        }
    }


    /**
     * Determine whether the current falling tetramino can move left one column
     * @return boolean value (true - can move left, false - can not move left)
     */
    private boolean canFallingTetraminoMoveLeftOneColumn() {
        eraseFallingTetramino();

        for (int i = 0; i < fallingTetraminoBlocksCoordinates.size(); i++) {
            if (fallingTetraminoBlocksCoordinates.get(i)[1] == 0 ||
                    boardMatrix[fallingTetraminoBlocksCoordinates.get(i)[0]][fallingTetraminoBlocksCoordinates.get(i)[1] - 1] != 0) {
                return false;
            }
        }

        return true;
    }


    /**
     * Move the current falling tetramino left one column in int[][] boardMatrix and Pane paneGameBoard
     */
    private void moveFallingTetraminoLeftOneColumn() {
        for (int i = 0; i < fallingTetraminoBlocksCoordinates.size(); i++) {
            fallingTetraminoBlocksCoordinates.get(i)[1]--;
        }

        currentColBoard--;
    }


    /**
     * Determine whether the current falling tetramino can move right one column
     * @return boolean value (true - can move right, false - can not move right)
     */
    private boolean canFallingTetraminoMoveRightOneColumn() {
        eraseFallingTetramino();

        int numOfColsTetramino = getFallingTetraminoWidth();

        if (currentColBoard + numOfColsTetramino >= boardMatrix[currentRowBoard].length) {
            return false;
        }

        for (int i = 0; i < fallingTetraminoBlocksCoordinates.size(); i++) {
            if (fallingTetraminoBlocksCoordinates.get(i)[1] >= (boardMatrix.length - 1) ||
                    boardMatrix[fallingTetraminoBlocksCoordinates.get(i)[0]][1 + fallingTetraminoBlocksCoordinates.get(i)[1]] != 0) {
                return false;
            }
        }

        return true;
    }


    /**
     * Calculate the width of the current falling tetramino based on int tetraminoType and int orientation
     * @return int value representing the width of the current falling tetramino and its orientation
     */
    private int getFallingTetraminoWidth() {
        int tetraminoIndexLeft = boardMatrix[0].length - 1;
        int tetraminoIndexRight = 0;

        for (int i = 0; i < fallingTetraminoBlocksCoordinates.size(); i++) {
            tetraminoIndexLeft = Math.min(tetraminoIndexLeft, fallingTetraminoBlocksCoordinates.get(i)[1]);
            tetraminoIndexRight = Math.max(tetraminoIndexRight, fallingTetraminoBlocksCoordinates.get(i)[1]);
        }

        return ((tetraminoIndexRight - tetraminoIndexLeft) + 1);
    }


    /**
     * Move the current falling tetramino right one column in int[][] boardMatrix and Pane paneGameBoard
     */
    private void moveFallingTetraminoRightOneColumn() {
        for (int i = 0; i < fallingTetraminoBlocksCoordinates.size(); i++) {
            fallingTetraminoBlocksCoordinates.get(i)[1]++;
        }

        currentColBoard++;
    }


    /**
     * Rotate the current falling tetramino clockwise
     */
    private void rotateFallingTetraminoClockwise() {
        orientation = changeOrientation(true);

        tetraminoMatrix = tetraminoBlock.getBlockMatrix(fallingTetraminoType, orientation);

        fallingTetraminoBlocksCoordinates = new ArrayList<>();
        fallingTetraminoBlocksCoordinates = getFallingTetraminoBlocksCoordinatesAsArrayList(tetraminoMatrix);

        tetraminoHeight = getFallingTetraminoHeight();       /*   NEW CODE   */
    }


    /**
     * Determine whether the current falling tetramino can rotate clockwise
     * (space to rotate into, still be in bounds)
     * @return boolean value (true - can rotate clockwise, false - can not rotate clockwise)
     */
    private boolean canFallingTetraminoRotateClockwise() {
        if (fallingTetraminoType == 1) {
            return false;
        }

        int desiredOrientation = changeOrientation(true);
        int[][] temporaryTetraminoMatrix = tetraminoBlock.getBlockMatrix(fallingTetraminoType, desiredOrientation);
        List<int[]> temporaryFallingTetraminoBlocksCoordinates = new ArrayList<>();

        eraseFallingTetramino();

        temporaryFallingTetraminoBlocksCoordinates = getFallingTetraminoBlocksCoordinatesAsArrayList(temporaryTetraminoMatrix);

        for (int i = 0; i < temporaryFallingTetraminoBlocksCoordinates.size(); i++) {
            if (temporaryFallingTetraminoBlocksCoordinates.get(i)[0] >= boardMatrix.length ||
                    temporaryFallingTetraminoBlocksCoordinates.get(i)[1] >= boardMatrix[currentColBoard].length ||
                    boardMatrix[temporaryFallingTetraminoBlocksCoordinates.get(i)[0]][temporaryFallingTetraminoBlocksCoordinates.get(i)[1]] != 0) {
                return false;
            }
        }

        return true;
    }


    /**
     * Increment or decrement int orientation based on boolean parameter
     * @param shouldIncrement boolean value (true - increment orientation, false - decrement orientation)
     * @return int value representing the orientation of the current falling tetramino
     */
    private int changeOrientation(boolean shouldIncrement) {
        int desiredOrientation = orientation;

        switch (fallingTetraminoType) {
            case 1:
                return 0;
            case 2:
            case 3:
            case 4:
                desiredOrientation = (orientation == 0) ? 1 : 0;
                break;
            case 5:
            case 6:
            case 7:
                if (shouldIncrement) {
                    switch (orientation) {
                        case 0:
                        case 1:
                        case 2:
                            desiredOrientation++;
                            break;
                        case 3:
                            desiredOrientation = 0;
                            break;
                    }
                } else {
                    switch (orientation) {
                        case 0:
                            desiredOrientation = 3;
                            break;
                        case 1:
                        case 2:
                        case 3:
                            desiredOrientation--;
                            break;
                    }
                }
                break;
        }

        return desiredOrientation;
    }


    /**
     * Determine whether the current falling tetramino can rotate counterclockwise
     * (space to rotate into, still be in bounds)
     * @return boolean value (true - can rotate counterclockwise, false - can not rotate counterclockwise)
     */
    private boolean canFallingTetraminoRotateCounterClockwise() {
        if (fallingTetraminoType == 1) {
            return false;
        }

        int desiredOrientation = changeOrientation(false);
        int[][] temporaryTetraminoMatrix = tetraminoBlock.getBlockMatrix(fallingTetraminoType, desiredOrientation);
        List<int[]> temporaryFallingTetraminoBlocksCoordinates = new ArrayList<>();

        eraseFallingTetramino();

        temporaryFallingTetraminoBlocksCoordinates = getFallingTetraminoBlocksCoordinatesAsArrayList(temporaryTetraminoMatrix);

        for (int i = 0; i < temporaryFallingTetraminoBlocksCoordinates.size(); i++) {
            if (temporaryFallingTetraminoBlocksCoordinates.get(i)[0] >= boardMatrix.length ||
                    temporaryFallingTetraminoBlocksCoordinates.get(i)[1] >= boardMatrix[startColBoard].length ||
                    boardMatrix[temporaryFallingTetraminoBlocksCoordinates.get(i)[0]][temporaryFallingTetraminoBlocksCoordinates.get(i)[1]] != 0) {
                return false;
            }
        }

        return true;
    }


    /**
     * Rotate the current falling tetramino counterclockwise
     */
    private void rotateFallingTetraminoCounterClockwise() {
        orientation = changeOrientation(false);

        tetraminoMatrix = tetraminoBlock.getBlockMatrix(fallingTetraminoType, orientation);

        fallingTetraminoBlocksCoordinates = new ArrayList<>();
        fallingTetraminoBlocksCoordinates = getFallingTetraminoBlocksCoordinatesAsArrayList(tetraminoMatrix);

        tetraminoHeight = getFallingTetraminoHeight();
    }


    /**
     * Generates a new random tetramino to be the current falling tetramino with orientation = 0
     */
    private void generateNewFallingTetraminoFromNextTetramino() {
        setLowestOccupiedRow();

        fallingTetraminoType = nextTetraminoType;
        orientation = 0;

        Color newColor = getBlockColor(fallingTetraminoType);
        tetraminoBlock = new Block(newColor);

        tetraminoMatrix = tetraminoBlock.getBlockMatrix(fallingTetraminoType, orientation);

        currentRowBoard = startRowBoard;
        currentColBoard = startColBoard;

        fallingTetraminoBlocksCoordinates = new ArrayList<>();
        fallingTetraminoBlocksCoordinates = getFallingTetraminoBlocksCoordinatesAsArrayList(tetraminoMatrix);

        tetraminoHeight = getFallingTetraminoHeight();
    }


    /**
     * Sets int lowestOccupiedRow by comparing the lowest row index of the current falling tetramino
     * to the current value of int lowestOccupiedRow
     */
    private void setLowestOccupiedRow() {
        lowestOccupiedRow = Math.min(fallingTetraminoBlocksCoordinates.get(0)[0], lowestOccupiedRow);
    }


    /**
     * Move the current falling tetramino down one row in int[][] boardMatrix and Pane paneGameBoard
     */
    private void moveFallingTetraminoDownOneRow() {
        for (int i = 0; i < fallingTetraminoBlocksCoordinates.size(); i++) {
            fallingTetraminoBlocksCoordinates.get(i)[0]++;
        }

        currentRowBoard++;
    }


    /**
     * Determine whether the current falling tetramino can move down one row
     * @return boolean value (true - can move down, false - can not move down)
     */
    private boolean canFallingTetraminoMoveDownOneRow() {
        eraseFallingTetramino();

        if (currentRowBoard + tetraminoHeight >= boardMatrix.length) {
            return false;
        }

        for (int i = 0; i < fallingTetraminoBlocksCoordinates.size(); i++) {
            if (fallingTetraminoBlocksCoordinates.get(i)[0] + 1 < boardMatrix.length) {
                if (boardMatrix[1 + fallingTetraminoBlocksCoordinates.get(i)[0]][fallingTetraminoBlocksCoordinates.get(i)[1]] != 0) {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;
    }


    /**
     * Calculate the height of the current falling tetramino based on int tetraminoType and int orientation
     * @return int value representing the height of the current falling tetramino and its orientation
     */
    private int getFallingTetraminoHeight() {
        int tetraminoIndexTop = boardMatrix.length - 1;
        int tetraminoIndexBottom = 0;

        for (int i = 0; i < fallingTetraminoBlocksCoordinates.size(); i++) {
            tetraminoIndexTop = Math.min(tetraminoIndexTop, fallingTetraminoBlocksCoordinates.get(i)[0]);
            tetraminoIndexBottom = Math.max(tetraminoIndexBottom, fallingTetraminoBlocksCoordinates.get(i)[0]);
        }

        return ((tetraminoIndexBottom - tetraminoIndexTop) + 1);
    }


    /**
     * Remove the current falling tetramino from int[][] boardMatrix and Pane paneGameBoard
     */
    private void eraseFallingTetramino() {
        for (int i = 0; i < fallingTetraminoBlocksCoordinates.size(); i++) {
            boardMatrix[fallingTetraminoBlocksCoordinates.get(i)[0]][fallingTetraminoBlocksCoordinates.get(i)[1]] = 0;
            paneGameBoard.getChildren().remove(blocks[fallingTetraminoBlocksCoordinates.get(i)[0]][fallingTetraminoBlocksCoordinates.get(i)[1]]);
            blocks[fallingTetraminoBlocksCoordinates.get(i)[0]][fallingTetraminoBlocksCoordinates.get(i)[1]] = null;
        }
    }


    /**
     * Adds Rectangles to Pane paneGameBoard and Rectangle[][] blocks
     * based on ArrayList of int[] fallingTetraminoBlocksCoordinates
     */
    private void drawFallingTetramino() {
        for (int i = 0; i < fallingTetraminoBlocksCoordinates.size(); i++) {
            Rectangle currentBlock = new Rectangle(START_X_BOARD + (BLOCK_SIZE * fallingTetraminoBlocksCoordinates.get(i)[1]),
                    START_Y_BOARD + (BLOCK_SIZE * fallingTetraminoBlocksCoordinates.get(i)[0]), BLOCK_SIZE, BLOCK_SIZE);

            currentBlock.setFill(getBlockColor(boardMatrix[fallingTetraminoBlocksCoordinates.get(i)[0]][fallingTetraminoBlocksCoordinates.get(i)[1]]));

            currentBlock.setStroke(colorBorder);

            blocks[fallingTetraminoBlocksCoordinates.get(i)[0]][fallingTetraminoBlocksCoordinates.get(i)[1]] = currentBlock;

            paneGameBoard.getChildren().add(currentBlock);
        }
    }


    /**
     * Place the current falling tetramino into int[][] boardMatrix
     * @return boolean value (true - can be placed, false - was placed, but game was lost)
     */
    private boolean placeFallingTetraminoIntoBoardMatrix() {
        boolean canBePlaced = true;

        for (int i = 0; i < fallingTetraminoBlocksCoordinates.size(); i++) {
            if (boardMatrix[fallingTetraminoBlocksCoordinates.get(i)[0]][fallingTetraminoBlocksCoordinates.get(i)[1]] != 0) {
                canBePlaced = false;
            }

            boardMatrix[fallingTetraminoBlocksCoordinates.get(i)[0]][fallingTetraminoBlocksCoordinates.get(i)[1]] = fallingTetraminoType;
        }

        return canBePlaced;
    }


    /**
     * Get the type of tetramino the current falling tetramino is
     * by finding first non-zero value in matrix
     * @param matrix the matrix to examine (should be tetraminoMatrix)
     * @return int value representing the tetramino type of the current falling tetramino
     */
    private int getFallingTetraminoType(int[][] matrix) {
        int value = 0;

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] != 0) {
                    return matrix[row][col];
                }
            }
        }

        return value;
    }


    /**
     * Returns an ArrayList of int[] of the row and column indexes of
     * the current falling tetramino blocks inside int[][] boardMatrix
     * @param matrix matrix to be examined (should be tetraminoMatrix)
     * @return ArrayList of int[] representing the row and column indexes of the blocks
     * that make up the current falling tetramino
     */
    private ArrayList<int[]> getFallingTetraminoBlocksCoordinatesAsArrayList(int[][] matrix) {
        ArrayList<int[]> temporaryList = new ArrayList<>();

        int largestOccupiedColIndex = 0;                                                    /* NEW CODE */

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] != 0) {
                    temporaryList.add(new int[]{(currentRowBoard + row), (currentColBoard + col)});
                    largestOccupiedColIndex = Math.max(largestOccupiedColIndex, col);       /* NEW CODE */
                }
            }
        }

        int numberOfEmptyBeginningRows = matrix.length - 1;
        int numberOfEmptyBeginningCols = largestOccupiedColIndex;

        for (int i = 0; i < temporaryList.size(); i++) {
            numberOfEmptyBeginningRows = Math.min(numberOfEmptyBeginningRows, temporaryList.get(i)[0] - currentRowBoard);
            numberOfEmptyBeginningCols = Math.min(numberOfEmptyBeginningCols, temporaryList.get(i)[1] - currentColBoard);
        }

        if (numberOfEmptyBeginningRows > 0) {
            while (numberOfEmptyBeginningRows > 0) {
                for (int i = 0; i < temporaryList.size(); i++) {
                    temporaryList.get(i)[0]--;
                }
                numberOfEmptyBeginningRows--;
            }
        }

        if (numberOfEmptyBeginningCols > 0) {
            while (numberOfEmptyBeginningCols > 0) {
                for (int i = 0; i < temporaryList.size(); i++) {
                    temporaryList.get(i)[1]--;
                }
                numberOfEmptyBeginningCols--;
            }
        }

        return temporaryList;
    }


    /**
     * Returns the appropriate Color to fill the Rectangles of tetraminos
     * based on the int value passed to this method
     * @param shape int value representing the tetramino type
     * @return Color value corresponding to the tetramino type passed in
     */
    private Color getBlockColor(int shape) {
        Color currentBlockColor;

        switch (shape) {
            case 1:
                currentBlockColor = colorOBlock;            // O-Block (Square)
                break;
            case 2:
                currentBlockColor = colorIBlock;            // I-Block
                break;
            case 3:
                currentBlockColor = colorSBlock;            // S-Block
                break;
            case 4:
                currentBlockColor = colorZBlock;            // Z-Block
                break;
            case 5:
                currentBlockColor = colorLBlock;            // L-Block
                break;
            case 6:
                currentBlockColor = colorJBlock;            // J-Block
                break;
            case 7:
                currentBlockColor = colorTBlock;            // T-Block
                break;
            default:
                currentBlockColor = colorEmptyBlock;        // No Block
                break;
        }

        return currentBlockColor;
    }


    /**
     * Removes all Rectangles from Pane paneGameBoard and Rectangle[][] blocks
     */
    private void eraseBoard() {
        for (int row = 0; row < boardMatrix.length; row++) {
            for (int col = 0; col < boardMatrix[row].length; col++) {
                paneGameBoard.getChildren().remove(blocks[row][col]);
                blocks[row][col] = null;
            }
        }
    }


    /**
     * Instantiates a new Rectangle[][] blocks and fills blocks with Rectangles
     * corresponding to any row and column index pair in int[][] boardMatrix that != 0
     */
    private void createBoardBlocks() {
        blocks = new Rectangle[boardMatrix.length][boardMatrix[0].length];

        for (int row = 0; row < boardMatrix.length; row++) {
            for (int col = 0; col < boardMatrix[row].length; col++) {
                if (boardMatrix[row][col] != 0) {
                    Rectangle currentBlock = new Rectangle(START_X_BOARD + (BLOCK_SIZE * col),
                            START_Y_BOARD + (BLOCK_SIZE * row), BLOCK_SIZE, BLOCK_SIZE);

                    currentBlock.setFill(getBlockColor(boardMatrix[row][col]));

                    currentBlock.setStroke(colorBorder);

                    blocks[row][col] = currentBlock;
                }
            }
        }
    }


    /**
     * Adds Rectangles to Pane paneGameBoard from int lowestOccupiedRow to the largest row and index pair
     */
    private void drawBoard() {
        for (int row = lowestOccupiedRow; row < blocks.length; row++) {
            for (int col = 0; col < blocks[row].length; col++) {
                if (blocks[row][col] != null) {
                    paneGameBoard.getChildren().add(blocks[row][col]);
                }
            }
        }
    }
}