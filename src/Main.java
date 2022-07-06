import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    private int dimensions_gameInformationWidth;
    private int dimensions_gameInformationHeight;
    private int dimensions_gameInformationMargin;
    private int speed;
    private int level;
    private String levelString;
    private int numberOfRowsCleared;
    private int fallingTetraminoType;
    private int orientation;
    private int startingRow;
    private int startingColumn;
    private int tetraminoRow;
    private int tetraminoColumn;
    private int tetraminoIndexTop;
    private int tetraminoIndexBottom;
    private int tetraminoIndexLeft;
    private int tetraminoIndexRight;
    private int startRowBoard;
    private int startColBoard;
    private int currentRowBoard;
    private int currentColBoard;
    private int currentRowTetramino;
    private int currentColTetramino;
    private int lowestOccupiedRow;
    private int numOfRowsBoard;
    private int numOfColsBoard;
    private int numOfRowsTetramino;
    private int numOfColsTetramino;
    private int simultaneousClearedRows;
    private int score;
    private int holdTetraminoType;
    private int numberOfUniqueTetraminos;
    private final int SCREEN_WIDTH = (int)Screen.getPrimary().getBounds().getWidth();
    private final int SCREEN_HEIGHT = (int)Screen.getPrimary().getBounds().getHeight();
    private final int BLOCK_SIZE = (int)(SCREEN_WIDTH * (15 / 683.0));                  // 30
    private final int START_X_HOLD_TETRAMINO = (int)(BLOCK_SIZE * (2 / 3.0));           // 20
    private final int START_Y_HOLD_TETRAMINO = (int)(BLOCK_SIZE * (2 / 3.0));           // 20
    private final int START_X_BOARD = (int)(BLOCK_SIZE * (25 / 3.0));                   // 250
    private final int START_Y_BOARD = (int)(BLOCK_SIZE * (2 / 3.0));                    // 20
    private final int START_X_GAME_INFORMATION = (int)(BLOCK_SIZE * (86 / 3.0));        // 860
    private final int START_Y_GAME_INFORMATION = (int)(BLOCK_SIZE * (2 / 3.0));         // 20
    private int[][] boardMatrix;
    private int[][] tetraminoMatrix;
    private int[][] trimmedTetraminoMatrix;
    private List<int[]> fallingTetraminoBlocksCoordinates;
    private Rectangle currentBlock;
    // private Rectangle[] blocks;
    private Rectangle[][] blocks;
    private Rectangle[][] holdBlocks;
    private Block tetraminoBlock;
    private Color currentColor;
    private Color colorOBlock, colorIBlock, colorSBlock, colorZBlock, colorLBlock, colorJBlock, colorTBlock,
            colorEmptyBlock;
    private Color colorBorder;
    private Text pauseText;

    private int tetraminoHeight;


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

        /*
        /////////////////////////////////// TEMPORARY - FOR TEST PURPOSES ONLY ////////////////////////////////////
        startingRow = 0;
        startingColumn = 4;
        tetraminoRow = 0;
        tetraminoColumn = 0;
        // generateNewBlock();
         */
     /*
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////// THIS IS FOR TESTING //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||
        ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||
       \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  /
        \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/
     */
        System.out.println("fallingTetraminoBlocksCoordinates: \nSTART");

        for (int i = 0; i < fallingTetraminoBlocksCoordinates.size(); i++) {
            System.out.println("row = " + fallingTetraminoBlocksCoordinates.get(i)[0] +
                    ", column = " + fallingTetraminoBlocksCoordinates.get(i)[1]);
        }

        System.out.println("fallingTetraminoBlocksCoordinates: \nEND\n\n");
        System.out.println(Arrays.deepToString(boardMatrix));


        placeFallingTetraminoIntoBoardMatrix();
        drawFallingTetramino();

     /*/\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\
      /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \
       ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||
       ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////// THIS IS FOR TESTING //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    */


        System.out.println("SCREEN WIDTH = " + Screen.getPrimary().getBounds().getWidth() +
                ", SCREEN HEIGHT = " + Screen.getPrimary().getBounds().getHeight());

        System.out.println("BLOCK_SIZE = " + BLOCK_SIZE);
        System.out.println("dimensions_gameInformationWidth = " + dimensions_gameInformationWidth);
        System.out.println("dimensions_gameInformationHeight = " + dimensions_gameInformationHeight);
        System.out.println("dimensions_gameInformationMargin = " + dimensions_gameInformationMargin);
        System.out.println("START_X_BOARD = " + START_X_BOARD);
        System.out.println("START_Y_BOARD = " + START_Y_BOARD);
        System.out.println("START_X_HOLD_TETRAMINO = " + START_X_HOLD_TETRAMINO);
        System.out.println("START_Y_HOLD_TETRAMINO = " + START_Y_HOLD_TETRAMINO);
        System.out.println("START_X_GAME_INFORMATION = " + START_X_GAME_INFORMATION);
        System.out.println("START_Y_GAME_INFORMATION = " + START_Y_GAME_INFORMATION);




        timer = new Timeline(new KeyFrame(Duration.millis(speed), e-> {
            playGame();
        }));

        // Set the CycleCount of the Animation to INDEFINITE (animation doesn't end) and Play the animation
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        //////////////////////////////////////////// CONTROLS ////////////////////////////////////////////////////
        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.LEFT){
                if (canFallingTetraminoMoveLeftOneColumn()) {
                    moveFallingTetraminoLeftOneColumn();
                }
                placeFallingTetraminoIntoBoardMatrix();
                drawFallingTetramino();
            }
            else if (e.getCode() == KeyCode.RIGHT) {
                if (canFallingTetraminoMoveRightOneColumn()) {
                    moveFallingTetraminoRightOneColumn();
                }
                placeFallingTetraminoIntoBoardMatrix();
                drawFallingTetramino();
            }
            else if (e.getCode() == KeyCode.DOWN) {
                if (canFallingTetraminoMoveDownOneRow()) {
                    moveFallingTetraminoDownOneRow();
                }
                placeFallingTetraminoIntoBoardMatrix();
                drawFallingTetramino();
            } else if (e.getCode() == KeyCode.UP) {
                while (canFallingTetraminoMoveDownOneRow()) {
                    moveFallingTetraminoDownOneRow();
                }
                placeFallingTetraminoIntoBoardMatrix();
                drawFallingTetramino();
            }
            else if (e.getCode() == KeyCode.A) {
                if (canFallingTetraminoRotateCounterClockwise()) {
                    rotateFallingTetraminoCounterClockwise();
                } else {                                            //
                    eraseFallingTetramino();                        // THIS ELSE STATEMENT IS NEW CODE
                }                                                   //
                placeFallingTetraminoIntoBoardMatrix();
                drawFallingTetramino();
            }
            else if (e.getCode() == KeyCode.D) {
                if (canFallingTetraminoRotateClockwise()) {
                    rotateFallingTetraminoClockwise();
                } else {                                            //
                    eraseFallingTetramino();                        // THIS ELSE STATEMENT IS NEW CODE
                }                                                   //
                placeFallingTetraminoIntoBoardMatrix();
                drawFallingTetramino();
            }
            else if (e.getCode() == KeyCode.W) {
                if (isHoldTetraminoBoxEmpty) {
                    reserveFallingTetraminoInHoldBox();
                } else {
                    generateSpecifiedFallingTetramino(holdTetraminoType);
                }
            }
            else if (e.getCode() == KeyCode.SPACE) {
                if (timer.getStatus() == Animation.Status.RUNNING) {
                    pauseText = new Text(75, 320, "P A U S E D");
                    pauseText.setFont(new Font(36));
                    pauseText.setFill(Color.RED);
                    paneGameBoard.getChildren().add(pauseText);
                    timer.pause();
                } else {
                    paneGameBoard.getChildren().remove(pauseText);
                    timer.play();
                }
            }
        });
    }


    @Override
    public void init() throws Exception {
        // Initialize Random variable
        random = new Random();

        numberOfUniqueTetraminos = 7;
        numberOfRowsCleared = 0;
        level = 0;

        // Set the default size of the boardMatrix
        numOfRowsBoard = 20;
        numOfColsBoard = 10;

        isHoldTetraminoBoxEmpty = true;
        holdTetraminoType = 0;

        dimensions_boardWidth = BLOCK_SIZE * numOfColsBoard;
        dimensions_boardHeight = BLOCK_SIZE * numOfRowsBoard;
        dimensions_holdTetraminoWidth = BLOCK_SIZE * 6;
        dimensions_holdTetraminoHeight = BLOCK_SIZE * 7;
        dimensions_gameInformationWidth = BLOCK_SIZE * 10;
        dimensions_gameInformationHeight = (int)(BLOCK_SIZE * (5 / 3.0));      // 50
        dimensions_gameInformationMargin = (int)(BLOCK_SIZE * (5 / 3.0));      // 50

        // Create GridPane to hold all the elements of the UI
        gridPaneMain = new GridPane();
        gridPaneMain.setPrefSize((BLOCK_SIZE * 20), (BLOCK_SIZE * (68 / 3.0))); // 600x680

        // Create hold Tetramino Pane
        paneHoldTetramino = new Pane();

        Line holdTetraminoBorderLeft = new Line(
                START_X_HOLD_TETRAMINO,
                START_Y_HOLD_TETRAMINO,
                START_X_HOLD_TETRAMINO,
                (START_Y_HOLD_TETRAMINO + dimensions_holdTetraminoHeight)
        );
        Line holdTetraminoBorderRight = new Line(
                (START_X_HOLD_TETRAMINO + dimensions_holdTetraminoWidth),
                START_Y_HOLD_TETRAMINO,
                (START_X_HOLD_TETRAMINO + dimensions_holdTetraminoWidth),
                (START_Y_HOLD_TETRAMINO + dimensions_holdTetraminoHeight)
        );
        Line holdTetraminoBorderTop = new Line(
                START_X_HOLD_TETRAMINO,
                START_Y_HOLD_TETRAMINO,
                (START_X_HOLD_TETRAMINO + dimensions_holdTetraminoWidth),
                START_Y_HOLD_TETRAMINO
        );
        Line holdTetraminoBorderBottom = new Line(
                START_X_HOLD_TETRAMINO,
                (START_Y_HOLD_TETRAMINO + dimensions_holdTetraminoHeight),
                (START_X_HOLD_TETRAMINO + dimensions_holdTetraminoWidth),
                (START_Y_HOLD_TETRAMINO + dimensions_holdTetraminoHeight)
        );

        Font fontHold = new Font(36);
        Label lblHoldTetramino = new Label("HOLD");

        lblHoldTetramino.setLabelFor(paneHoldTetramino);
        lblHoldTetramino.setLayoutX(
                (START_X_HOLD_TETRAMINO + ((double)dimensions_holdTetraminoWidth / 4.5))
        );
        lblHoldTetramino.setLayoutY((START_Y_HOLD_TETRAMINO));
        lblHoldTetramino.setTextFill(Color.GREY);
        lblHoldTetramino.setFont(fontHold);

        paneHoldTetramino.getChildren().addAll(holdTetraminoBorderLeft, holdTetraminoBorderRight,
                holdTetraminoBorderTop, holdTetraminoBorderBottom, lblHoldTetramino);

        // Create game board Pane
        paneGameBoard = new Pane();

        // Create and Initialize four Lines to outline the playing field
        Line gameBoardBorderLeft = new Line(
                START_X_BOARD,
                START_Y_BOARD,
                START_X_BOARD,
                (START_Y_BOARD + dimensions_boardHeight)
        );
        Line gameBoardBorderRight = new Line(
                (START_X_BOARD + dimensions_boardWidth),
                START_Y_BOARD,
                (START_X_BOARD + dimensions_boardWidth),
                (START_Y_BOARD + dimensions_boardHeight)
        );
        Line gameBoardBorderTop = new Line(
                START_X_BOARD,
                START_Y_BOARD,
                (START_X_BOARD + dimensions_boardWidth),
                START_Y_BOARD
        );
        Line gameBoardBorderBottom = new Line(
                START_X_BOARD,
                (START_Y_BOARD + dimensions_boardHeight),
                (START_X_BOARD + dimensions_boardWidth),
                (START_Y_BOARD + dimensions_boardHeight)
        );

        // Add the borders to the game board
        paneGameBoard.getChildren().addAll(
                gameBoardBorderLeft, gameBoardBorderRight, gameBoardBorderTop, gameBoardBorderBottom
        );

        paneScoreDisplay = new Pane();

        Line scoreBorderLeft = new Line(
                START_X_GAME_INFORMATION,
                START_Y_GAME_INFORMATION,
                START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight)
        );
        Line scoreBorderRight = new Line(
                (START_X_GAME_INFORMATION + dimensions_gameInformationWidth),
                START_Y_GAME_INFORMATION,
                (START_X_GAME_INFORMATION + dimensions_gameInformationWidth),
                (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight)
        );
        Line scoreBorderTop = new Line(
                START_X_GAME_INFORMATION,
                START_Y_GAME_INFORMATION,
                (START_X_GAME_INFORMATION + dimensions_gameInformationWidth),
                START_Y_GAME_INFORMATION
        );
        Line scoreBorderBottom = new Line(
                START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight),
                (START_X_GAME_INFORMATION + dimensions_gameInformationWidth),
                (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight)
        );
        Line scoreDividingLine = new Line(
                (START_X_GAME_INFORMATION + ((double)dimensions_gameInformationWidth / 2)),
                START_Y_GAME_INFORMATION,
                (START_X_GAME_INFORMATION + ((double)dimensions_gameInformationWidth / 2)),
                (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight)
        );

        Label lblScore = new Label("Score: ");

        lblScoreValue = new Label();

        Font fontDisplay = new Font(48);


        lblScore.setLabelFor(lblScoreValue);
        lblScore.setLayoutX((START_X_GAME_INFORMATION + 10));
        lblScore.setLayoutY((START_Y_GAME_INFORMATION - 10));
        lblScore.setTextFill(Color.RED);
        lblScore.setFont(fontDisplay);

        lblScoreValue.setLayoutX(((START_X_GAME_INFORMATION +
                (double)dimensions_gameInformationWidth / 2) + 10));
        lblScoreValue.setLayoutY((START_Y_GAME_INFORMATION - 10));
        lblScoreValue.setTextFill(Color.RED);
        lblScoreValue.setFont(fontDisplay);
        lblScoreValue.setText(String.valueOf(score));


        paneScoreDisplay.getChildren().addAll(scoreBorderLeft, scoreBorderRight, scoreBorderTop, scoreBorderBottom,
                scoreDividingLine, lblScore, lblScoreValue);

        paneLevelDisplay = new Pane();

        Line levelBorderLeft = new Line(
                START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight +
                        dimensions_gameInformationMargin),
                START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + dimensions_gameInformationMargin +
                        (2 * dimensions_gameInformationHeight))
        );
        Line levelBorderRight = new Line(
                (START_X_GAME_INFORMATION + dimensions_gameInformationWidth),
                (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight +
                        dimensions_gameInformationMargin),
                (START_X_GAME_INFORMATION + dimensions_gameInformationWidth),
                (START_Y_GAME_INFORMATION + dimensions_gameInformationMargin +
                        (2 * dimensions_gameInformationHeight))
        );
        Line levelBorderTop = new Line(
                START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight +
                        dimensions_gameInformationMargin),
                (START_X_GAME_INFORMATION + dimensions_gameInformationWidth),
                (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight +
                        dimensions_gameInformationMargin)
        );
        Line levelBorderBottom = new Line(
                START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + dimensions_gameInformationMargin +
                        (2 * dimensions_gameInformationHeight)),
                (START_X_GAME_INFORMATION + dimensions_gameInformationWidth),
                (START_Y_GAME_INFORMATION + dimensions_gameInformationMargin +
                        (2 * dimensions_gameInformationHeight))
        );
        Line levelDividingLine = new Line(
                (START_X_GAME_INFORMATION + ((double)dimensions_gameInformationWidth / 2)),
                (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight +
                        dimensions_gameInformationMargin),
                (START_X_GAME_INFORMATION + ((double)dimensions_gameInformationWidth / 2)),
                (START_Y_GAME_INFORMATION + dimensions_gameInformationMargin +
                        (2 * dimensions_gameInformationHeight))
        );

        Label lblLevel = new Label("Level: ");
        lblLevelValue = new Label();

        levelString = String.format("%02d", level);

        lblLevel.setLabelFor(lblLevelValue);
        lblLevel.setLayoutX((START_X_GAME_INFORMATION + 10));
        lblLevel.setLayoutY(
                (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight +
                dimensions_gameInformationMargin - 10)
        );
        lblLevel.setTextFill(Color.RED);
        lblLevel.setFont(fontDisplay);

        lblLevelValue.setLayoutX(
                (START_X_GAME_INFORMATION + ((double)dimensions_gameInformationWidth / 2) + 10)
        );
        lblLevelValue.setLayoutY(
                (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight +
                dimensions_gameInformationMargin - 10)
        );
        lblLevelValue.setTextFill(Color.RED);
        lblLevelValue.setFont(fontDisplay);
        lblLevelValue.setText(levelString);

        paneLevelDisplay.getChildren().addAll(levelBorderLeft, levelBorderRight,
                levelBorderTop, levelBorderBottom, levelDividingLine, lblLevel, lblLevelValue);

        paneClearedLinesDisplay = new Pane();


        Line clearedLinesBorderLeft = new Line(
                START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + (2 * dimensions_gameInformationHeight) +
                        (2 * dimensions_gameInformationMargin)),
                START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + (2 * dimensions_gameInformationHeight) +
                        (2 * dimensions_gameInformationMargin) + dimensions_gameInformationHeight)
        );
        Line clearedLinesBorderRight = new Line(
                (START_X_GAME_INFORMATION + dimensions_gameInformationWidth),
                (START_Y_GAME_INFORMATION + (2 * dimensions_gameInformationHeight) +
                        (2 * dimensions_gameInformationMargin)),
                (START_X_GAME_INFORMATION + dimensions_gameInformationWidth),
                (START_Y_GAME_INFORMATION + (2 * dimensions_gameInformationHeight) +
                        (2 * dimensions_gameInformationMargin) + dimensions_gameInformationHeight)
        );
        Line clearedLinesBorderTop = new Line(
                START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + (2 * dimensions_gameInformationHeight) +
                        (2 * dimensions_gameInformationMargin)),
                (START_X_GAME_INFORMATION + dimensions_gameInformationWidth),
                (START_Y_GAME_INFORMATION + (2 * dimensions_gameInformationHeight) +
                        (2 * dimensions_gameInformationMargin))
        );
        Line clearedLinesBorderBottom = new Line(
                START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + (2 * dimensions_gameInformationHeight) +
                        (2 * dimensions_gameInformationMargin) + dimensions_gameInformationHeight),
                (START_X_GAME_INFORMATION + dimensions_gameInformationWidth),
                (START_Y_GAME_INFORMATION + (2 * dimensions_gameInformationHeight) +
                        (2 * dimensions_gameInformationMargin) + dimensions_gameInformationHeight)
        );
        Line clearedLinesDividingLine = new Line(
                (START_X_GAME_INFORMATION + ((double)dimensions_gameInformationWidth / 2)),
                (START_Y_GAME_INFORMATION + (2 * dimensions_gameInformationHeight) +
                        (2 * dimensions_gameInformationMargin)),
                (START_X_GAME_INFORMATION + ((double)dimensions_gameInformationWidth / 2)),
                (START_Y_GAME_INFORMATION + (2 * dimensions_gameInformationHeight) +
                        (2 * dimensions_gameInformationMargin) + dimensions_gameInformationHeight)
        );

        Label lblClearedLines = new Label("Lines: ");
        lblClearedLinesValue = new Label();

        lblClearedLines.setLabelFor(lblClearedLinesValue);
        lblClearedLines.setLayoutX((START_X_GAME_INFORMATION + 10));
        lblClearedLines.setLayoutY(
                (START_Y_GAME_INFORMATION + (2 * dimensions_gameInformationHeight) +
                        (2 * dimensions_gameInformationMargin) - 10)
        );
        lblClearedLines.setTextFill(Color.RED);
        lblClearedLines.setFont(fontDisplay);

        lblClearedLinesValue.setLayoutX(
                (START_X_GAME_INFORMATION + ((double)dimensions_gameInformationWidth / 2) + 10)
        );
        lblClearedLinesValue.setLayoutY(
                (START_Y_GAME_INFORMATION + (2 * dimensions_gameInformationHeight) +
                        (2 * dimensions_gameInformationMargin) - 10)
        );
        lblClearedLinesValue.setTextFill(Color.RED);
        lblClearedLinesValue.setFont(fontDisplay);
        lblClearedLinesValue.setText(String.valueOf(numberOfRowsCleared));

        paneClearedLinesDisplay.getChildren().addAll(
                clearedLinesBorderLeft,
                clearedLinesBorderRight,
                clearedLinesBorderTop, clearedLinesBorderBottom, clearedLinesDividingLine,
                lblClearedLines, lblClearedLinesValue);


        // Add the game board to the main Grid Pane
        gridPaneMain.getChildren().addAll(paneHoldTetramino, paneGameBoard, paneScoreDisplay,
                paneLevelDisplay, paneClearedLinesDisplay);

        // Initialize the Scene
        scene = new Scene(gridPaneMain, (BLOCK_SIZE * (80 / 3.0)), (BLOCK_SIZE * (68 / 3.0)));  // 800X680

        // Initialize the default beginning speed
        // speed = 400;     // ORIGINAL SPEED
        // speed = 1_000;   // SLOW SPEED
        speed = 800;        // Attempting to match NES Tetris speed (800 = speed of level 00, 720 = level 01,
        // 640 = level 02)

        // Initialize the boardMatrix
        boardMatrix = new int[numOfRowsBoard][numOfColsBoard];

        // Set the default size of the tetraminoMatrix
        tetraminoMatrix = new int[numOfRowsTetramino][numOfColsTetramino];

        /*
        // Initialize tetramino matrix
        tetraminoMatrix = new int[][] {
                {0, 2, 0, 0},
                {0, 2, 0, 0},
                {0, 2, 0, 0},
                {0, 2, 0, 0}
        };
         */
        fallingTetraminoType = 1 + random.nextInt(numberOfUniqueTetraminos);

        Color newColor = getBlockColor(fallingTetraminoType);   // ADDED - NEW BLOCKS HAVE NO COLOR
        tetraminoBlock = new Block(newColor);                   // ADDED - NEW BLOCKS HAVE NO COLOR

        tetraminoMatrix = tetraminoBlock.getBlockMatrix(fallingTetraminoType, orientation);

        startRowBoard = 0;
        startColBoard = (numOfColsBoard - 1) / 2;           // ORIGINAL LINE OF CODE
        // startColBoard = 4;                               // NEW LINE OF CODE

        currentRowBoard = startRowBoard;
        currentColBoard = startColBoard;
/*
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////// THIS IS FOR TESTING //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||
        ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||
       \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  /
        \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/
 */
        fallingTetraminoBlocksCoordinates = new ArrayList<>();
        fallingTetraminoBlocksCoordinates = getFallingTetraminoBlocksCoordinatesAsArrayList(tetraminoMatrix);

        // fallingTetraminoType = 0;
        System.out.println(Arrays.deepToString(tetraminoMatrix));

        System.out.println("fallingTetraminoType = " + getFallingTetraminoType(tetraminoMatrix));
        fallingTetraminoType = getFallingTetraminoType(tetraminoMatrix);
        System.out.println("fallingTetraminoType = " + fallingTetraminoType);

        tetraminoHeight = getFallingTetraminoHeight();
        System.out.println("tetraminoHeight = " + tetraminoHeight);

        lowestOccupiedRow = boardMatrix.length - 1;

        /////////////////////// THIS LINE WAS CHANGED BECAUSE blocks WAS CHANGED TO 2D ARRAY /////////////////////////
        blocks = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        ////////////////////// THIS LINE WOULD NEED TO GO IN THE GENERATE NEW TETRAMINO METHOD //////////////////////
        // blocks = new Rectangle[fallingTetraminoBlocksCoordinates.size()]; // ORIGINAL LINE FOR 1D ARRAY

        /*
         *  NEED TO CHANGE BELOW TO ACCOUNT FOR CUSTOM TETRAMINOS
         */
        holdBlocks = new Rectangle[4][4];
        /*
         *  NEED TO CHANGE ABOVE TO ACCOUNT FOR CUSTOM TETRAMINOS
         */
/*     /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\
      /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \
       ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||
       ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////// THIS IS FOR TESTING //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
 */

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

                if (numberOfRowsCleared % 10 == 0) {
                    timer.stop();                                               // NEW CODE
                    incrementAndDisplayLevel();                                 // NEW CODE
                    System.out.println("level = " + level);                     // NEW CODE
                    if (speed - 100 >= 100) {
                        speed -= 100;
                        System.out.println("speed = " + speed);
                    }
                    /*
                     *
                     *  NEW CODE BELOW
                     *
                     */
                    timer.setCycleCount(Timeline.INDEFINITE);                                       // NEW CODE
                    // NEW CODE BELOW
                    timer.rateProperty().bind(new SimpleDoubleProperty(1.0).multiply(1.1));
                    // timer.setRate(speed);                                                        // NEW CODE
                    // timer.play();                                                                   // NEW CODE
                }

                timer.play();                                                                   // NEW CODE

                paneScoreDisplay.getChildren().remove(lblScoreValue);
                score += getValueToAddToScore();
                lblScoreValue.setText(String.valueOf(score));
                paneScoreDisplay.getChildren().add(lblScoreValue);
            }

            generateNewRandomFallingTetramino();

            if (!placeFallingTetraminoIntoBoardMatrix()) {
                lostGame();
                timer.stop();
            }

        }
        drawFallingTetramino();
    }


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

        tetraminoHeight = getFallingTetraminoHeight();   /*   NEW CODE   */
    }


    // THIS METHOD IS NOT WORKING CORRECTLY
    private void eraseHoldTetramino() {
        for (int row = 0; row < holdBlocks.length; row++) {
            for (int col = 0; col < holdBlocks[row].length; col++) {
                paneGameBoard.getChildren().remove(holdBlocks[row][col]);
                holdBlocks[row][col] = null;
            }
        }
    }


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

                        //////////////////////// TRYING TO FIX GETTING A FULL LINE ERROR WITH eraseBoard() //////////////////////
                        // paneGameBoard.getChildren().add(blocks[fallingTetraminoBlocksCoordinates.get(i)[0]][fallingTetraminoBlocksCoordinates.get(i)[1]]);
                        // paneGameBoard.getChildren().add(currentBlock);   // COMMENTED OUT FOR TESTING
                    }
                }
            }

            // startRowBoard = 0;
            // startColBoard = (numOfColsBoard - 1) / 2;
            // currentRowBoard = startRowBoard;
            // currentColBoard = startColBoard;

            // fallingTetraminoBlocksCoordinates = new ArrayList<>();
            // fallingTetraminoBlocksCoordinates = getFallingTetraminoBlocksCoordinatesAsArrayList(tetraminoMatrix);

            // tetraminoHeight = getFallingTetraminoHeight(tetraminoMatrix);   /*   NEW CODE   */

            isHoldTetraminoBoxEmpty = false;

            generateNewRandomFallingTetramino();
        }
    }


    /**
     * Update number of lines completed. Increment by one for each completed row and
     * updating the Label lblClearedLinesValue
     */
    private void incrementAndDisplayClearedLines() {
        paneClearedLinesDisplay.getChildren().remove(lblClearedLinesValue);
        numberOfRowsCleared++;
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


    /*
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////// THIS IS FOR TESTING //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||
        ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||
       \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  / \  /
        \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/   \/
     */

    /********************
     *****
     *  wasARowCompleted HAS SOME KIND OF ERROR WITH IT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *****
     *****/

     /*/\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\   /\
      /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \ /  \
       ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||
       ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////// THIS IS FOR TESTING //////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    */

    /**
     * Loop through the int game board matrix looking for completed rows,
     * calling eraseRow(int completedRowNumber) on completed rows and
     * incrementing simultaneousClearedRows
     * @return boolean value of if any row was completed
     */
    private boolean wasARowCompleted() {
        System.out.println("wasARowCompleted() method entered:");           // TEST
        simultaneousClearedRows = 0;
        int row = numOfRowsBoard - 1;

        System.out.println("lowestOccupiedRow = " + lowestOccupiedRow);

        while (row >= lowestOccupiedRow + simultaneousClearedRows) {
            boolean wasLineCompleted = false;

            for (int col = 0; col < boardMatrix[row].length; col++) {
                System.out.println("\t\t\t\trow = [" + row + "], col = [" + col + "]");
                if (boardMatrix[row][col] == 0) {
                    col += boardMatrix[row].length;
                }

                if ((col == (boardMatrix[row].length - 1)) && boardMatrix[row][col] != 0) {
                    wasLineCompleted = true;
                }
            }
            if (wasLineCompleted) {
                simultaneousClearedRows++;
                System.out.println("\t\trow " + row + "deleted");
                eraseRow(row);
                incrementAndDisplayClearedLines();
                row = numOfRowsBoard - 1;
            } else {
                row--;
            }
        }

        System.out.println("\n\n____________________________________________________________________" +
                "______________________________________________________________________" +
                "\n____________________________________________________________________" +
                "______________________________________________________________________" +
                "\ndidTetrisOccur = " + (simultaneousClearedRows != 0) +
                "\n____________________________________________________________________" +
                "______________________________________________________________________" +
                "\n____________________________________________________________________" +
                "______________________________________________________________________" + "\n\n");
        System.out.println("simultaneousClearedRows = " + simultaneousClearedRows);
        System.out.println("wasARowCompleted() method exited.");            // TEST
        return simultaneousClearedRows != 0;
    }


    /**
     * Removes specified row from int[][] boardMatrix and
     * remove corresponding Rectangles from paneGameBoard.
     * @param completedRowNumber the row index of int[][] boardMatrix that has been completed
     */
    private void eraseRow(int completedRowNumber) {
        /*
        for (int row = 0; row <= completedRowNumber; row++) {
            for (int col = 0; col < blocks[row].length; col++) {
                paneGameBoard.getChildren().remove(blocks[row][col]);
                blocks[row][col] = null;
            }
        }
         */


        // THIS FOR LOOP ADDED TO TRY TO FIX ERASING A ROW WHEN A TETRIS HAPPENS
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


    private void lostGame() {
        System.out.println("GAME OVER!");			// TEMPORARY METHOD BODY
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

        currentColBoard--;  // ADDED TO TRY TO FIX GETTING A FULL LINE ERROR
    }


    /**
     * Determine whether the current falling tetramino can move right one column
     * @return boolean value (true - can move right, false - can not move right)
     */
    private boolean canFallingTetraminoMoveRightOneColumn() {
        eraseFallingTetramino();

        int numOfColsTetramino = getFallingTetraminoWidth();

        System.out.println("\ncurrentColBoard = " + currentColBoard + " + numOfColsTetramino = " +
                numOfColsTetramino + " = " + (currentColBoard + numOfColsTetramino) + "\n");

        if (currentColBoard + numOfColsTetramino >= boardMatrix[currentRowBoard].length) {
            System.out.println("FAILING IN IF STATEMENT OF canFallingTetraminoMoveRightOneColumn(): " +
                    "\nnumOfColsTetramino = " + numOfColsTetramino + "\n");
            return false;
        }

        /*
        if (startColBoard + numOfColsTetramino >= boardMatrix[startRowBoard].length) {
            return false;
        }
         */

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
        // int tetraminoIndexLeft = tetraminoMatrix[0].length - 1;
        // int tetraminoIndexLeft = fallingTetraminoBlocksCoordinates.get(0)[1];
        int tetraminoIndexLeft = boardMatrix[0].length - 1;
        System.out.println("tetraminoIndexLeft = " + tetraminoIndexLeft);
        int tetraminoIndexRight = 0;

        for (int i = 0; i < fallingTetraminoBlocksCoordinates.size(); i++) {
            System.out.println("tetraminoIndexLeft = " + tetraminoIndexLeft +
                    " vs fallingTetraminoBlocksCoordinates.get(i)[1] = " + fallingTetraminoBlocksCoordinates.get(i)[1]);
            tetraminoIndexLeft = Math.min(tetraminoIndexLeft, fallingTetraminoBlocksCoordinates.get(i)[1]);
            tetraminoIndexRight = Math.max(tetraminoIndexRight, fallingTetraminoBlocksCoordinates.get(i)[1]);
        }

        System.out.println("tetraminoIndexRight = " + tetraminoIndexRight);
        System.out.println("tetraminoIndexLeft = " + tetraminoIndexLeft);
        System.out.println("tetramino width = " + ((tetraminoIndexRight - tetraminoIndexLeft) + 1) + "\n");
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
            System.out.println("TEMPORARY OUTPUT TO SHOW IF CODE ENTERED IF STATEMENT OF: " +
                    "canFallingTetraminoRotateClockwise");
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
                // original 2nd check: temporaryFallingTetraminoBlocksCoordinates.get(i)[1] >= boardMatrix[startColBoard].length
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
            // return true; // ORIGINAL CODE
            System.out.println("TEMPORARY OUTPUT TO SHOW IF CODE ENTERED IF STATEMENT OF: " +
                    "canFallingTetraminoRotateCounterClockwise");
            return false;   // NEW CODE
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

        tetraminoHeight = getFallingTetraminoHeight();   /*   NEW CODE   */
    }


    /**
     * Generates a new random tetramino to be the current falling tetramino with orientation = 0
     */
    private void generateNewRandomFallingTetramino() {
        setLowestOccupiedRow();

        fallingTetraminoType = 1 + random.nextInt(numberOfUniqueTetraminos);
        orientation = 0;

        Color newColor = getBlockColor(fallingTetraminoType);   // ADDED - NEW BLOCKS HAVE NO COLOR
        tetraminoBlock = new Block(newColor);                   // ADDED - NEW BLOCKS HAVE NO COLOR

        tetraminoMatrix = tetraminoBlock.getBlockMatrix(fallingTetraminoType, orientation);

        // startRowBoard = 0;
        // startColBoard = (numOfColsBoard - 1) / 2;
        currentRowBoard = startRowBoard;
        currentColBoard = startColBoard;

        fallingTetraminoBlocksCoordinates = new ArrayList<>();
        fallingTetraminoBlocksCoordinates = getFallingTetraminoBlocksCoordinatesAsArrayList(tetraminoMatrix);

        tetraminoHeight = getFallingTetraminoHeight();   /*   NEW CODE   */

        // blocks = new Rectangle[fallingTetraminoBlocksCoordinates.size()]; // FOR TEST PURPOSES THIS IS COMMENTED OUT
    }


    /**
     * Sets int lowestOccupiedRow by comparing the lowest row index of the current falling tetramino
     * to the current value of int lowestOccupiedRow
     */
    private void setLowestOccupiedRow() {
        lowestOccupiedRow = Math.min(fallingTetraminoBlocksCoordinates.get(0)[0], lowestOccupiedRow);

        ///////////////////////////////////////////// TEST PURPOSES /////////////////////////////////////////////////
        System.out.println("lowestOccupiedRow = " + lowestOccupiedRow);
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

        // TRYING TO REPLACE BELOW COMMENTED OUT IF STATEMENT
        if (currentRowBoard + tetraminoHeight >= boardMatrix.length) {
            return false;
        }

        /*
        if (startRowBoard + tetraminoHeight >= boardMatrix.length) {
            return false;
        }
         */

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

        /*
         *
         *  NEW CODE (TEST CODE)
         *
         */
        System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" +
                "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" +
                "\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" +
                "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" +
                "\ntetraminoIndexTop = " + tetraminoIndexTop + "\ntetraminoIndexBottom = " + tetraminoIndexBottom +
                "\ntetraminoHeight = " + ((tetraminoIndexBottom - tetraminoIndexTop) + 1) +
                "\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" +
                "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" +
                "\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" +
                "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n");

        return ((tetraminoIndexBottom - tetraminoIndexTop) + 1);
    }


    /**
     * Remove the current falling tetramino from int[][] boardMatrix and Pane paneGameBoard
     */
    private void eraseFallingTetramino() {
        for (int i = 0; i < fallingTetraminoBlocksCoordinates.size(); i++) {
            boardMatrix[fallingTetraminoBlocksCoordinates.get(i)[0]][fallingTetraminoBlocksCoordinates.get(i)[1]] = 0;
            // paneGameBoard.getChildren().remove(blocks[i]);

            // TESTING BLOCKS AS 2D ARRAY
            paneGameBoard.getChildren().remove(blocks[fallingTetraminoBlocksCoordinates.get(i)[0]][fallingTetraminoBlocksCoordinates.get(i)[1]]);
            blocks[fallingTetraminoBlocksCoordinates.get(i)[0]][fallingTetraminoBlocksCoordinates.get(i)[1]] = null;
        }
    }


    /**
     * Adds Rectangles to Pane paneGameBoard and Rectangle[][] blocks
     * based on ArrayList of int[] fallingTetraminoBlocksCoordinates
     */
    private void drawFallingTetramino() {
        /*
        for (int i = 0; i < fallingTetraminoBlocksCoordinates.size(); i++) {
            Rectangle currentBlock = new Rectangle(START_X + (BLOCK_SIZE * fallingTetraminoBlocksCoordinates.get(i)[1]),
                    START_Y + (BLOCK_SIZE * fallingTetraminoBlocksCoordinates.get(i)[0]), BLOCK_SIZE, BLOCK_SIZE);
            currentBlock.setFill(getBlockColor(boardMatrix[fallingTetraminoBlocksCoordinates.get(i)[0]][fallingTetraminoBlocksCoordinates.get(i)[1]]));
            currentBlock.setStroke(colorBorder);
            blocks[i] = currentBlock;
            paneGameBoard.getChildren().add(currentBlock);
        }
         */

        for (int i = 0; i < fallingTetraminoBlocksCoordinates.size(); i++) {
            Rectangle currentBlock = new Rectangle(START_X_BOARD + (BLOCK_SIZE * fallingTetraminoBlocksCoordinates.get(i)[1]),
                    START_Y_BOARD + (BLOCK_SIZE * fallingTetraminoBlocksCoordinates.get(i)[0]), BLOCK_SIZE, BLOCK_SIZE);

            currentBlock.setFill(getBlockColor(boardMatrix[fallingTetraminoBlocksCoordinates.get(i)[0]][fallingTetraminoBlocksCoordinates.get(i)[1]]));

            currentBlock.setStroke(colorBorder);

            blocks[fallingTetraminoBlocksCoordinates.get(i)[0]][fallingTetraminoBlocksCoordinates.get(i)[1]] = currentBlock;

            paneGameBoard.getChildren().add(currentBlock);

            //////////////////////// TRYING TO FIX GETTING A FULL LINE ERROR WITH eraseBoard() //////////////////////
            // paneGameBoard.getChildren().add(blocks[fallingTetraminoBlocksCoordinates.get(i)[0]][fallingTetraminoBlocksCoordinates.get(i)[1]]);
            // paneGameBoard.getChildren().add(currentBlock);   // COMMENTED OUT FOR TESTING
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

                System.out.println("tetraminoMatrix[" + row + "][" + col + "] = " + tetraminoMatrix[row][col]);
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
                    // ORIGINAL LINE OF CODE
                    // temporaryList.add(new int[]{(startRowBoard + row), (startColBoard + col)});
                    // NEW LINE OF CODE
                    temporaryList.add(new int[]{(currentRowBoard + row), (currentColBoard + col)});
                    largestOccupiedColIndex = Math.max(largestOccupiedColIndex, col);       /* NEW CODE */
                }
            }
        }

        /*
         *      NEW CODE BELOW
         */
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

        // TO SEE IF THIS ADDED CODE GOT RID OF EMPTY ROWS AND COLS
        System.out.println("\n///////////////////////////////////////////////////////////////////////////" +
                "/////////////////////////////////////////////////////////////////////////////////");
        for (int i = 0; i < temporaryList.size(); i++) {
            System.out.println("temporaryList[" + i + "] = [" + temporaryList.get(i)[0] + "], [" +
                    temporaryList.get(i)[1] + "]");
        }
        // System.out.println("///////////////////////////////////////////////////////////////////////////" +
        //         "/////////////////////////////////////////////////////////////////////////////////\n");
        /*
         *      NEW CODE ABOVE
         */

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