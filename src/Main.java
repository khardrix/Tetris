import javafx.geometry.HPos;
import javafx.stage.StageStyle;
import objects.Block;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.NumberFormat;
import java.util.*;

public class Main extends Application {

    // Class variables
    private EventHandler<KeyEvent> keyHandlerBtnMoveLeft;
    private EventHandler<KeyEvent> keyHandlerBtnMoveRight;
    private EventHandler<KeyEvent> keyHandlerBtnSoftDrop;
    private EventHandler<KeyEvent> keyHandlerBtnHardDrop;
    private EventHandler<KeyEvent> keyHandlerBtnRotateCounterclockwise;
    private EventHandler<KeyEvent> keyHandlerBtnRotateClockwise;
    private EventHandler<KeyEvent> keyHandlerBtnHold;
    private EventHandler<KeyEvent> keyHandlerBtnPause;
    private Button btnMoveLeft;
    private Button btnMoveRight;
    private Button btnSoftDrop;
    private Button btnHardDrop;
    private Button btnRotateCounterclockwise;
    private Button btnRotateClockwise;
    private Button btnHold;
    private Button btnPause;
    private Scene sceneControlOptions;
    private NumberFormat formatNumberWithDelimiter;
    private Timeline timer;
    private Random random;
    private Font fontAlumniSansInlineOne;
    private Font fontBlackOpsOne;
    private Font fontBlakaHollow;
    private Font fontBungeeShade;
    private Font fontCabinSketch;
    private Font fontCreepster;
    private Font fontDancingScript;
    private Font fontFasterOne;
    private Font fontFrederickaTheGreat;
    private Font fontGraduate;
    private Font fontGugi;
    private Font fontHennyPenny;
    private Font fontIrishGrover;
    private Font fontMonoton;
    private Font fontPermanentMarker;
    private Font fontPressStart2P;
    private Font fontRubikMoonrocks;
    private Font fontRye;
    private Font fontSquarePeg;
    private Font fontUnifrakturMaguntia;
    private Font fontContainerTitleLabel;
    private Font fontInformationTitleLabel;
    private Font fontInformationValueLabel;
    private Text textHold;
    private Text textNext;
    private Text textScoreTitle;
    private Text textScoreValue;
    private Text textLevelTitle;
    private Text textLevelValue;
    private Text textLinesTitle;
    private Text textLinesValue;
    private Text textTimerTitle;
    private Text textTimerValue;
    private Pane paneGameBoard;
    private Pane paneScoreDisplay;
    private Pane paneLevelDisplay;
    private Pane paneClearedLinesDisplay;
    private Pane paneTimer;
    private Pane paneNextTetramino;
    private Pane paneHoldTetramino;
    private Pane paneUpcomingTetraminos;
    // private GridPane gridPaneMain;
    private Scene scenePrimary;
    private Label lblHoldTitle;
    private Label lblNextTitle;
    private Label lblScoreTitle;
    private Label lblLevelTitle;
    private Label lblClearedLinesTitle;
    private Label lblTimerTitle;
    private Label lblScoreValue;
    private Label lblLevelValue;
    private Label lblClearedLinesValue;
    private Label lblTimerValue;
    private Timer timerObj;
    private boolean isHoldTetraminoBoxEmpty;
    private boolean hasListenerBeenAdded;
    private int hours;
    private int minutes;
    private int seconds;
    private int mSeconds;
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
    private Button[] buttons;
    private Font[] fonts;
    private int[][] boardMatrix;
    private int[][] holdTetraminoMatrix;
    private int[][] tetraminoMatrix;
    private int[][] nextTetraminoMatrix;
    private int[][] upcomingTetraminoTopMatrix;
    private int[][] upcomingTetraminoMiddleMatrix;
    private int[][] upcomingTetraminoBottomMatrix;
    private List<int[]> fallingTetraminoBlocksCoordinates;
    private Rectangle[][] blocks;
    private Rectangle[][] holdBlocks;
    private Rectangle[][] nextBlocks;
    private Rectangle[][] upcomingTopBlocks;
    private Rectangle[][] upcomingMiddleBlocks;
    private Rectangle[][] upcomingBottomBlocks;
    private KeyCode[] controlKeys;
    private String[] fontFileNames;
    private Text[] texts;
    private Block tetraminoBlock;
    private Color colorOBlock, colorIBlock, colorSBlock, colorZBlock, colorLBlock, colorJBlock, colorTBlock,
            colorEmptyBlock;
    private Color colorBorder;
    private Color colorContainerTitleLabel;
    private Color colorInformationTitleLabel;
    private Color colorInformationValueLabel;
    private Stage stagePause;
    private Stage stageOptions;
    private Stage stageControlOptions;
    private KeyCode controlKeyMoveLeft;
    private KeyCode controlKeyMoveRight;
    private KeyCode controlKeySoftDrop;
    private KeyCode controlKeyHardDrop;
    private KeyCode controlKeyRotateClockwise;
    private KeyCode controlKeyRotateCounterclockwise;
    private KeyCode controlKeyHold;
    private KeyCode controlKeyPause;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        /*
        // load the tron font.
        Font.loadFont(
                Main.class.getResource("/resources/Monoton-Regular.ttf").toExternalForm(),
                32
        );
         */


        // Set the Title of the Stage, Set the Scene to the Stage and Show the Stage
        primaryStage.setTitle("Tetris - Version by Ryan Huffman");
        primaryStage.setScene(scenePrimary);
        primaryStage.setMinHeight(BLOCK_SIZE * (68 / 3.0));         // 680
        primaryStage.setMaxHeight(BLOCK_SIZE * (68 / 3.0));         // 680
        primaryStage.setMinWidth(BLOCK_SIZE * (130 / 3.0));         // 1300
        primaryStage.setMaxWidth(BLOCK_SIZE * (130 / 3.0));         // 1300
        primaryStage.show();
        scenePrimary.getStylesheets().add("/resources/styling/primary-stage.css");

        drawBoard();

        placeFallingTetraminoIntoBoardMatrix();
        drawFallingTetramino();

        startTimer();
        runGameAtSpecifiedSpeed(speed);

        //////////////////////////////////////////// CONTROLS ////////////////////////////////////////////////////
        scenePrimary.setOnKeyPressed(e -> {
            if (e.getCode() == controlKeyMoveLeft){
                controlMoveLeftButton();
            }
            else if (e.getCode() == controlKeyMoveRight) {
                controlMoveRightButton();
            }
            else if (e.getCode() == controlKeySoftDrop) {
                controlSoftDropButton();
            }
            else if (e.getCode() == controlKeyHardDrop) {
                controlHardDropButton();
            }
            else if (e.getCode() == controlKeyRotateClockwise) {
                controlRotateClockwiseButton();
            }
            else if (e.getCode() == controlKeyRotateCounterclockwise) {
                controlRotateCounterclockwiseButton();
            }
            else if (e.getCode() == controlKeyHold) {
                controlHoldButton();
            }
            else if (e.getCode() == controlKeyPause) {
                controlPauseButton();
            }
        });

        /*
        /////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////
        ERRORS:
        1. CAN CLICK MULTIPLE BUTTONS AT A TIME ------- SOLVED!!!!!!!
        2. CAN CLICK A BUTTON AND THEN NOT SET THE KEY ------- SOLVED!!!!!!!
        3. CAN CLICK A BUTTON AND THEN BACK OUT OF THE CONTROL OPTIONS MENU ------- SOLVED!!!!!!!
        4. CAN SET MULTIPLE CONTROLS TO THE SAME KEY ------- SOLVED!!!!!!!
        5. SPACE IS NOT WORKING AFTER SETTING PAUSE TO SOMETHING ELSE. CAN NO LONGER SET IT BACK TO SPACE
                AND THEN SPACE STOPS WORKING FOR CLICKING A BUTTON

        NEED TO IMPLEMENT:
        1. IF USER DECIDES THEY DON'T WANT TO CHANGE THE KEY BINDING FOR AN ACTION, ALLOW THEM TO
                CLICK THE BUTTON AGAIN TO CANCEL THE EventListener ------- SOLVED!!!!!!!
                Idea for implementation: Use a boolean value as a flag of whether the user has
                                            clicked a Button yet. When the user clicks the Button:
                                            boolean (hasBeenClicked) value:
                                            true  - Set corresponding Label value to the name of
                                                    the KeyCode key that has been assigned to
                                                    that control and exit handler
                                            false - Add EventListener as currently is
        /////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////
         */
        keyHandlerBtnMoveLeft = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleKeyEventForControlOptionsMenu(
                        keyHandlerBtnMoveLeft, event, 0, btnMoveLeft
                );
            }
        };

        keyHandlerBtnMoveRight = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleKeyEventForControlOptionsMenu(
                        keyHandlerBtnMoveRight, event, 1, btnMoveRight
                );
            }
        };

        keyHandlerBtnSoftDrop = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleKeyEventForControlOptionsMenu(
                        keyHandlerBtnSoftDrop, event, 2, btnSoftDrop
                );
            }
        };

        keyHandlerBtnHardDrop = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleKeyEventForControlOptionsMenu(
                        keyHandlerBtnHardDrop, event, 3, btnHardDrop
                );
            }
        };

        keyHandlerBtnRotateClockwise = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleKeyEventForControlOptionsMenu(
                        keyHandlerBtnRotateClockwise, event, 4, btnRotateClockwise
                );
            }
        };

        keyHandlerBtnRotateCounterclockwise = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleKeyEventForControlOptionsMenu(
                        keyHandlerBtnRotateCounterclockwise, event, 5, btnRotateCounterclockwise
                );
            }
        };

        keyHandlerBtnHold = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleKeyEventForControlOptionsMenu(
                        keyHandlerBtnHold, event, 6, btnHold
                );
            }
        };

        keyHandlerBtnPause = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleKeyEventForControlOptionsMenu(
                        keyHandlerBtnPause, event, 7, btnPause
                );
            }
        };

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

        //////////////////////////////////////// LOAD FONTS //////////////////////////////////////////////
        // Initialize the String[] array to store all the font file names
        fontFileNames = new String[20];
        // Load the Alumni Sans Inline One font
        fontAlumniSansInlineOne = Font.loadFont(
                Main.class.getResource("/resources/fonts/AlumniSansInlineOne-Regular.ttf").toExternalForm(),
                24
        );
        fontFileNames[0] = "/resources/fonts/AlumniSansInlineOne-Regular.ttf";

        // Load the Black Ops One font
        fontBlackOpsOne = Font.loadFont(
                Main.class.getResource("/resources/fonts/BlackOpsOne-Regular.ttf").toExternalForm(),
                24
        );
        fontFileNames[1] = "/resources/fonts/BlackOpsOne-Regular.ttf"
        ;
        // Load the Blaka Hollow font
        fontBlakaHollow = Font.loadFont(
                Main.class.getResource("/resources/fonts/BlakaHollow-Regular.ttf").toExternalForm(),
                24
        );
        fontFileNames[2] = "/resources/fonts/BlakaHollow-Regular.ttf";

        // Load the Bungee Shade font
        fontBungeeShade = Font.loadFont(
                Main.class.getResource("/resources/fonts/BungeeShade-Regular.ttf").toExternalForm(),
                24
        );
        fontFileNames[3] = "/resources/fonts/BungeeShade-Regular.ttf";

        // Load the Cabin Sketch font
        fontCabinSketch = Font.loadFont(
                Main.class.getResource("/resources/fonts/CabinSketch-Regular.ttf").toExternalForm(),
                24
        );
        fontFileNames[4] = "/resources/fonts/CabinSketch-Regular.ttf";

        // Load the Creepster font
        fontCreepster = Font.loadFont(
                Main.class.getResource("/resources/fonts/Creepster-Regular.ttf").toExternalForm(),
                24
        );
        fontFileNames[5] = "/resources/fonts/Creepster-Regular.ttf";

        // Load the Dancing Script font
        fontDancingScript = Font.loadFont(
                Main.class.getResource("/resources/fonts/DancingScript-VariableFont_wght.ttf").toExternalForm(),
                24
        );
        fontFileNames[6] = "/resources/fonts/DancingScript-VariableFont_wght.ttf";

        // Load the Faster One font
        fontFasterOne = Font.loadFont(
                Main.class.getResource("/resources/fonts/FasterOne-Regular.ttf").toExternalForm(),
                24
        );
        fontFileNames[7] = "/resources/fonts/FasterOne-Regular.ttf";

        // Load the Fredericka The Great font
        fontFrederickaTheGreat = Font.loadFont(
                Main.class.getResource("/resources/fonts/FrederickatheGreat-Regular.ttf").toExternalForm(),
                24
        );
        fontFileNames[8] = "/resources/fonts/FrederickatheGreat-Regular.ttf";

        // Load the Graduate font
        fontGraduate = Font.loadFont(
                Main.class.getResource("/resources/fonts/Graduate-Regular.ttf").toExternalForm(),
                24
        );
        fontFileNames[9] = "/resources/fonts/Graduate-Regular.ttf";

        // Load the Gugi font
        fontGugi = Font.loadFont(
                Main.class.getResource("/resources/fonts/Gugi-Regular.ttf").toExternalForm(),
                24
        );
        fontFileNames[10] = "/resources/fonts/Gugi-Regular.ttf";

        // Load the Henny Penny font
        fontHennyPenny = Font.loadFont(
                Main.class.getResource("/resources/fonts/HennyPenny-Regular.ttf").toExternalForm(),
                24
        );
        fontFileNames[11] = "/resources/fonts/HennyPenny-Regular.ttf";

        // Load the Irish Grover font
        fontIrishGrover = Font.loadFont(
                Main.class.getResource("/resources/fonts/IrishGrover-Regular.ttf").toExternalForm(),
                24
        );
        fontFileNames[12] = "/resources/fonts/IrishGrover-Regular.ttf";

        // Load the Monoton font
        fontMonoton = Font.loadFont(
                Main.class.getResource("/resources/fonts/Monoton-Regular.ttf").toExternalForm(),
                32
        );
        fontFileNames[13] = "/resources/fonts/Monoton-Regular.ttf";

        // Load the Permanent Marker font
        fontPermanentMarker = Font.loadFont(
                Main.class.getResource("/resources/fonts/PermanentMarker-Regular.ttf").toExternalForm(),
                24
        );
        fontFileNames[14] = "/resources/fonts/PermanentMarker-Regular.ttf";

        // Load the Press Start 2P font
        fontPressStart2P = Font.loadFont(
                Main.class.getResource("/resources/fonts/PressStart2P-Regular.ttf").toExternalForm(),
                22
        );
        fontFileNames[15] = "/resources/fonts/PressStart2P-Regular.ttf";

        // Load the Rubik Moonrocks font
        fontRubikMoonrocks = Font.loadFont(
                Main.class.getResource("/resources/fonts/RubikMoonrocks-Regular.ttf").toExternalForm(),
                24
        );
        fontFileNames[16] = "/resources/fonts/RubikMoonrocks-Regular.ttf";

        // Load the Rye font
        fontRye = Font.loadFont(
                Main.class.getResource("/resources/fonts/Rye-Regular.ttf").toExternalForm(),
                24
        );
        fontFileNames[17] = "/resources/fonts/Rye-Regular.ttf";

        // Load the Square Peg font
        fontSquarePeg = Font.loadFont(
                Main.class.getResource("/resources/fonts/SquarePeg-Regular.ttf").toExternalForm(),
                24
        );
        fontFileNames[18] = "/resources/fonts/SquarePeg-Regular.ttf";

        // Load the UnifrakturMaguntia font
        fontUnifrakturMaguntia = Font.loadFont(
                Main.class.getResource("/resources/fonts/UnifrakturMaguntia-Regular.ttf").toExternalForm(),
                24
        );
        fontFileNames[19] = "/resources/fonts/UnifrakturMaguntia-Regular.ttf";


        // Add all Fonts to the Font[] array fonts
        fonts = new Font[] {
                fontAlumniSansInlineOne, fontBlackOpsOne, fontBlakaHollow, fontBungeeShade, fontCabinSketch,
                fontCreepster, fontDancingScript, fontFasterOne, fontFrederickaTheGreat, fontGraduate, fontGugi,
                fontHennyPenny, fontIrishGrover, fontMonoton, fontPermanentMarker, fontPressStart2P,
                fontRubikMoonrocks, fontRye, fontSquarePeg, fontUnifrakturMaguntia
        };

        fontContainerTitleLabel = getSelectedFontInSelectedFontSize(0, 54);
        fontInformationTitleLabel = getSelectedFontInSelectedFontSize(0, 48);
        fontInformationValueLabel = getSelectedFontInSelectedFontSize(0, 48);

        // Initialize the Text variables
        textHold = new Text("HOLD");
        textNext = new Text("NEXT");
        textScoreTitle = new Text("Score: ");
        textScoreValue = new Text("0");
        textLevelTitle = new Text("Level: ");
        textLevelValue = new Text(String.format("%02d", 0));
        textLinesTitle = new Text("Lines: ");
        textLinesValue = new Text("0");
        textTimerTitle = new Text("Time: ");
        textTimerValue = new Text("00 : 00 : 00 : 000");


        // Populate the Text[] array texts
        texts = new Text[] {
                textHold, textNext, textScoreTitle, textScoreValue, textLevelTitle,
                textLevelValue, textLinesTitle, textLinesValue, textTimerTitle, textTimerValue
        };

        // Initialize the Color of the Color variables that will determine what Color the corresponding
        // text will be written in.
        colorContainerTitleLabel = Color.GREY;
        colorInformationTitleLabel = Color.RED;
        colorInformationValueLabel = Color.RED;

        // Initialize NumberFormat object
        formatNumberWithDelimiter = NumberFormat.getInstance();
        formatNumberWithDelimiter.setGroupingUsed(true);

        hours = 0;
        minutes = 0;
        seconds = 0;
        mSeconds = 0;

        // Set the default controls
        controlKeyMoveLeft = KeyCode.LEFT;
        controlKeyMoveRight = KeyCode.RIGHT;
        controlKeySoftDrop = KeyCode.DOWN;
        controlKeyHardDrop = KeyCode.UP;
        controlKeyRotateClockwise = KeyCode.D;
        controlKeyRotateCounterclockwise = KeyCode.A;
        controlKeyHold = KeyCode.W;
        controlKeyPause = KeyCode.SPACE;
        controlKeys = new KeyCode[] {
                controlKeyMoveLeft, controlKeyMoveRight, controlKeySoftDrop, controlKeyHardDrop,
                controlKeyRotateClockwise, controlKeyRotateCounterclockwise, controlKeyHold, controlKeyPause
        };

        numberOfUniqueTetraminos = 7;
        numberOfRowsCleared = 0;
        level = 0;
        score = 0;

        // Set the default size of the boardMatrix
        numOfRowsBoard = 20;
        numOfColsBoard = 10;

        // Initialize boolean type class variables
        isHoldTetraminoBoxEmpty = true;
        hasListenerBeenAdded = false;

        // Initialize Tetramino types
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
        dimensions_gameInformationWidth = BLOCK_SIZE * 13;
        dimensions_gameInformationHeight = (int)(BLOCK_SIZE * (5 / 3.0));      // 50
        dimensions_gameInformationMargin = (int)(BLOCK_SIZE * (5 / 3.0));      // 50

        // Create GridPane to hold all the elements of the UI
        GridPane gridPaneMain = new GridPane();
        gridPaneMain.setPrefSize((BLOCK_SIZE * 20), (BLOCK_SIZE * (68 / 3.0))); // 600x680


        paneHoldTetramino = new Pane();
        lblHoldTitle = new Label(textHold.getText());
        drawBorders(paneHoldTetramino, START_X_HOLD_TETRAMINO, START_Y_HOLD_TETRAMINO,
                dimensions_holdTetraminoWidth, dimensions_holdTetraminoHeight);
        drawTitleLabel(
                paneHoldTetramino, colorContainerTitleLabel, fontContainerTitleLabel, lblHoldTitle,
                START_X_HOLD_TETRAMINO, START_Y_HOLD_TETRAMINO, dimensions_holdTetraminoWidth
        );

        paneGameBoard = new Pane();
        drawBorders(paneGameBoard, START_X_BOARD, START_Y_BOARD, dimensions_boardWidth, dimensions_boardHeight);

        paneNextTetramino = new Pane();
        lblNextTitle = new Label(textNext.getText());
        drawBorders(paneNextTetramino, START_X_NEXT_TETRAMINO, START_Y_NEXT_TETRAMINO,
                dimensions_nextTetraminoWidth, dimensions_nextTetraminoHeight);
        drawTitleLabel(
                paneNextTetramino, colorContainerTitleLabel, fontContainerTitleLabel, lblNextTitle,
                START_X_NEXT_TETRAMINO, START_Y_NEXT_TETRAMINO, dimensions_nextTetraminoWidth
        );

        paneUpcomingTetraminos = new Pane();
        drawBorders(paneUpcomingTetraminos, START_X_UPCOMING_TETRAMINOS, START_Y_UPCOMING_TETRAMINOS,
                dimensions_upcomingTetraminosWidth, dimensions_upcomingTetraminosHeight);

        paneScoreDisplay = new Pane();
        drawGameInformationContainer(paneScoreDisplay, START_X_GAME_INFORMATION, START_Y_GAME_INFORMATION,
                dimensions_gameInformationWidth, dimensions_gameInformationHeight);

        lblScoreTitle = new Label(textScoreTitle.getText());
        lblScoreValue = new Label();
        drawGameInformationTitleLabel(
                paneScoreDisplay, colorInformationTitleLabel, lblScoreValue, lblScoreTitle,
                fontInformationTitleLabel, START_X_GAME_INFORMATION, START_Y_GAME_INFORMATION
        );
        drawGameInformationValueLabel(
                paneScoreDisplay, lblScoreValue, colorInformationValueLabel, fontInformationValueLabel,
                textScoreValue.getText(), START_X_GAME_INFORMATION, START_Y_GAME_INFORMATION,
                dimensions_gameInformationWidth
        );

        paneLevelDisplay = new Pane();
        drawGameInformationContainer(paneLevelDisplay, START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight + dimensions_gameInformationMargin),
                dimensions_gameInformationWidth, dimensions_gameInformationHeight);

        lblLevelTitle = new Label(textLevelTitle.getText());
        lblLevelValue = new Label();
        levelString = String.format("%02d", level);
        textLevelValue.setText(levelString);
        drawGameInformationTitleLabel(
                paneLevelDisplay, colorInformationTitleLabel, lblLevelValue,
                lblLevelTitle, fontInformationTitleLabel,
                START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight + dimensions_gameInformationMargin)
        );
        drawGameInformationValueLabel(
                paneLevelDisplay, lblLevelValue, colorInformationValueLabel,
                fontInformationValueLabel, textLevelValue.getText(),
                START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight + dimensions_gameInformationMargin),
                dimensions_gameInformationWidth
        );

        paneClearedLinesDisplay = new Pane();
        drawGameInformationContainer(paneClearedLinesDisplay, START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + (2 * dimensions_gameInformationHeight) +
                        (2 * dimensions_gameInformationMargin)), dimensions_gameInformationWidth,
                dimensions_gameInformationHeight);

        lblClearedLinesTitle = new Label(textLinesTitle.getText());
        lblClearedLinesValue = new Label();
        drawGameInformationTitleLabel(
                paneClearedLinesDisplay, colorInformationTitleLabel, lblClearedLinesValue,
                lblClearedLinesTitle, fontInformationTitleLabel,
                START_X_GAME_INFORMATION,
                (
                        START_Y_GAME_INFORMATION +
                        (2 * dimensions_gameInformationHeight) +
                        (2 * dimensions_gameInformationMargin)
                )
        );
        drawGameInformationValueLabel(
                paneClearedLinesDisplay, lblClearedLinesValue, colorInformationValueLabel,
                fontInformationValueLabel, textLinesValue.getText(),
                START_X_GAME_INFORMATION,
                (
                        START_Y_GAME_INFORMATION +
                        (2 * dimensions_gameInformationHeight) +
                        (2 * dimensions_gameInformationMargin)
                ),
                dimensions_gameInformationWidth
        );

        paneTimer = new Pane();
        drawGameInformationContainer(paneTimer, START_X_GAME_INFORMATION,
                (START_Y_GAME_INFORMATION + (3 * dimensions_gameInformationHeight) +
                        (3 * dimensions_gameInformationMargin)), dimensions_gameInformationWidth,
                dimensions_gameInformationHeight);

        lblTimerTitle = new Label(textTimerTitle.getText());
        lblTimerValue = new Label();
        drawGameInformationTitleLabel(
                paneTimer, colorInformationTitleLabel, lblTimerValue,
                lblTimerTitle, fontInformationTitleLabel,
                START_X_GAME_INFORMATION,
                (
                        START_Y_GAME_INFORMATION +
                                (3 * dimensions_gameInformationHeight) +
                                (3 * dimensions_gameInformationMargin)
                )
        );
        drawGameInformationValueLabel(
                paneTimer, lblTimerValue, colorInformationValueLabel,
                fontInformationValueLabel, textTimerValue.getText(),
                START_X_GAME_INFORMATION,
                (
                        START_Y_GAME_INFORMATION +
                                (3 * dimensions_gameInformationHeight) +
                                (3 * dimensions_gameInformationMargin)
                ),
                dimensions_gameInformationWidth
        );

        // Add the game board to the main Grid Pane
        gridPaneMain.getChildren().addAll(paneHoldTetramino, paneGameBoard, paneNextTetramino,
                paneUpcomingTetraminos, paneScoreDisplay, paneLevelDisplay, paneClearedLinesDisplay, paneTimer
        );

        // Initialize the Scene
        scenePrimary = new Scene(gridPaneMain, (BLOCK_SIZE * (80 / 3.0)), (BLOCK_SIZE * (68 / 3.0)));  // 800X680

        // Initialize the default beginning speed
        // Attempting to match NES Tetris speed (800 = speed of level 00, 720 = level 01, 640 = level 02)
        speed = 799;

        // Initialize the boardMatrix
        boardMatrix = new int[numOfRowsBoard][numOfColsBoard];

        // Set the default size of the tetraminoMatrix
        tetraminoMatrix = new int[4][4];

        // Initialize the rest of the matrixes
        holdTetraminoMatrix = new int[4][4];
        nextTetraminoMatrix = new int[4][4];
        upcomingTetraminoTopMatrix = new int[4][4];
        upcomingTetraminoMiddleMatrix = new int[4][4];
        upcomingTetraminoBottomMatrix = new int[4][4];

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

        generateNewFallingTetraminoFromNextTetramino();
        generateNextTetraminoFromUpcomingTetraminoTop();
        generateUpcomingTetraminoTopFromUpcomingTetraminoMiddle();
        generateUpcomingTetraminoMiddleFromUpcomingTetraminoBottom();
        generateRandomUpcomingTetraminoBottom();

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
     * Removes the title Labels from the Hold Tetramino and Next Tetramino containers
     */
    private void removeContainerTitleLabels() {
        paneHoldTetramino.getChildren().remove(lblHoldTitle);
        paneNextTetramino.getChildren().remove(lblNextTitle);
    }


    /**
     * Removes all the title Labels from the game information containers
     */
    private void removeGameInformationTitleLabels() {
        paneScoreDisplay.getChildren().remove(lblScoreTitle);
        paneLevelDisplay.getChildren().remove(lblLevelTitle);
        paneClearedLinesDisplay.getChildren().remove(lblClearedLinesTitle);
        paneTimer.getChildren().remove(lblTimerTitle);
    }


    /**
     * Removes all the value Labels from the game information containers
     */
    private void removeGameInformationValueLabels() {
        paneScoreDisplay.getChildren().remove(lblScoreValue);
        paneLevelDisplay.getChildren().remove(lblLevelValue);
        paneClearedLinesDisplay.getChildren().remove(lblClearedLinesValue);
        paneTimer.getChildren().remove(lblTimerValue);
    }


    /**
     * Event handler for the EventHandler (KeyEvent)'s that assigns the KeyCode key pressed to the
     * corresponding KeyCode control key if the user didn't try to use a KeyCode that is already
     * assigned to another KeyCode control key. If the user does try to assign a KeyCode key
     * to a KeyCode control key that is already assigned that KeyCode key, the KeyCode control key
     * is assigned its previous KeyCode key.
     * If successful, that KeyCode key is assigned its corresponding index in the KeyCode[] controlKeys and
     * that KeyCode is assigned to the proper KeyCode control key.
     * Whether it is successful or not, the resulting KeyCode key name is assigned as the text in the
     * corresponding Button.
     * @param keyHandler EventHandler (KeyEvent) parameter - The handler this method is executing for
     * @param event KeyEvent parameter - The KeyCode key that was pressed
     * @param btnIndex int parameter - The index of the Button in the Button[] buttons
     * @param btn Button parameter - The Button that was clicked to activate the EventHandler
     */
    private void handleKeyEventForControlOptionsMenu(
            EventHandler<KeyEvent> keyHandler, KeyEvent event, int btnIndex, Button btn)
    {
        KeyCode key = event.getCode();
        int indexOfOccupiedKey = indexOfOccupiedControlKey(key);

        if (indexOfOccupiedKey == -1) {
            switch (btnIndex) {
                case 0:     setControlKeyMoveLeft(key);                     break;
                case 1:     setControlKeyMoveRight(key);                    break;
                case 2:     setControlKeySoftDrop(key);                     break;
                case 3:     setControlKeyHardDrop(key);                     break;
                case 4:     setControlKeyRotateClockwise(key);              break;
                case 5:     setControlKeyRotateCounterclockwise(key);       break;
                case 6:     setControlKeyHold(key);                         break;
                case 7:     setControlKeyPause(key);                        break;
                default:    break;
            }
            controlKeys[btnIndex] = key;
            btn.setText(key.getName());
        } else {
            btn.setText(controlKeys[btnIndex].getName());
        }
        enableAllButtonsAndWindowControlsInControlOptionsMenu(buttons);
        // Without setting hasListenerBeenAdded to false, after setting the KeyCode value
        // the user would have to click on the Button twice to activate the EventListener again
        hasListenerBeenAdded = false;

        sceneControlOptions.removeEventHandler(KeyEvent.KEY_PRESSED, keyHandler);
    }


    /**
     * Runs the game (repeatedly calls the playGame() game at specified millisecond interval)
     * @param milliseconds int parameter - The number of milliseconds between this method repeatedly calling playGame()
     */
    private void runGameAtSpecifiedSpeed(int milliseconds) {
        timer = new Timeline(new KeyFrame(Duration.millis(milliseconds), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                playGame();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }


    /**
     * Set which keyboard button moves the Tetramino left one column
     * @param key KeyCode parameter - KeyCode for the keyboard button to assign
     *            moving Tetramino left one column to
     */
    private void setControlKeyMoveLeft(KeyCode key) {
        controlKeyMoveLeft = key;
    }


    /**
     * Set which keyboard button moves the Tetramino right one column
     * @param key KeyCode parameter - KeyCode for the keyboard button to assign
     *            moving Tetramino right one column to
     */
    private void setControlKeyMoveRight(KeyCode key) {
        controlKeyMoveRight = key;
    }


    /**
     * Set which keyboard button moves the Tetramino down one row
     * @param key KeyCode parameter - KeyCode for the keyboard button to assign
     *            moving Tetramino down one row to
     */
    private void setControlKeySoftDrop(KeyCode key) {
        controlKeySoftDrop = key;
    }


    /**
     * Set which keyboard button moves the Tetramino down as many rows as possible
     * until the Tetramino is in place
     * @param key KeyCode parameter - KeyCode for the keyboard button to assign
     *            moving Tetramino down as many rows as possible until the Tetramino is in place
     */
    private void setControlKeyHardDrop(KeyCode key) {
        controlKeyHardDrop = key;
    }


    /**
     * Set which keyboard button rotates the Tetramino clockwise
     * @param key KeyCode parameter - KeyCode for the keyboard button to assign
     *            rotating Tetramino clockwise
     */
    private void setControlKeyRotateClockwise(KeyCode key) {
        controlKeyRotateClockwise = key;
    }


    /**
     * Set which keyboard button rotates the Tetramino counterclockwise
     * @param key KeyCode parameter - KeyCode for the keyboard button to assign
     *            rotating Tetramino counterclockwise
     */
    private void setControlKeyRotateCounterclockwise(KeyCode key) {
        controlKeyRotateCounterclockwise = key;
    }


    /**
     * Set which keyboard button moves the current falling Tetramino into the hold Tetramino box or
     * moves the Tetramino in the hold Tetramino box to the current falling Tetramino
     * @param key KeyCode parameter - KeyCode for the keyboard button to assign
     *            moving the current falling Tetramino into the hold Tetramino box or
     *            moves the Tetramino in the hold Tetramino box to the current falling Tetramino
     */
    private void setControlKeyHold(KeyCode key) {
        controlKeyHold = key;
    }


    /**
     * Set which keyboard button pauses/resumes the game
     * @param key KeyCode parameter - KeyCode for the keyboard button to assign
     *            to pausing/resuming the game
     */
    private void setControlKeyPause(KeyCode key) {
        controlKeyPause = key;
    }


    /**
     * Sets the String inside the Text object textHold
     * @param text String parameter - The String that will be used to set the text of Text object textHold
     */
    private void setTextHoldString(String text) {
        this.textHold.setText(text);
    }


    /**
     * Sets the String inside the Text object textNext
     * @param text String parameter - The String that will be used to set the text of Text object textNext
     */
    private void setTextNextString(String text) {
        this.textNext.setText(text);
    }


    /**
     * Sets the String inside the Text object textScoreTitle
     * @param text String parameter - The String that will be used to set the text of Text object textScoreTitle
     */
    private void setTextScoreTitleString(String text) {
        this.textScoreTitle.setText(text);
    }


    /**
     * Sets the String inside the Text object textScoreValue
     * @param text String parameter - The String that will be used to set the text of Text object textScoreValue
     */
    private void setTextScoreValueString(String text) {
        this.textScoreValue.setText(text);
    }


    /**
     * Sets the String inside the Text object textLevelTitle
     * @param text String parameter - The String that will be used to set the text of Text object textLevelTitle
     */
    private void setTextLevelTitleString(String text) {
        this.textLevelTitle.setText(text);
    }


    /**
     * Sets the String inside the Text object textLevelValue
     * @param text String parameter - The String that will be used to set the text of Text object textLevelValue
     */
    private void setTextLevelValueString(String text) {
        this.textLevelValue.setText(text);
    }


    /**
     * Sets the String inside the Text object textLinesTitle
     * @param text String parameter - The String that will be used to set the text of Text object textLinesTitle
     */
    private void setTextLinesTitleString(String text) {
        this.textLinesTitle.setText(text);
    }


    /**
     * Sets the String inside the Text object textLinesValue
     * @param text String parameter - The String that will be used to set the text of Text object textLinesValue
     */
    private void setTextLinesValueString(String text) {
        this.textLinesValue.setText(text);
    }


    /**
     * Sets the Color to write the container text in
     * @param color Color parameter - set the text of the title Label inside containers to this Color
     */
    private void setColorContainerTitleLabel(Color color) {
        this.colorContainerTitleLabel = color;
    }


    /**
     * Sets the Color to write the game information title Label's text in
     * @param color Color parameter - set the text of the title Label inside game information containers to this Color
     */
    private void setColorInformationTitleLabel(Color color) {
        this.colorInformationTitleLabel= color;
    }


    /**
     * Sets the Color to write the game information value Label's text in
     * @param color Color parameter - set the text of the value Label inside game information containers to this Color
     */
    private void setColorInformationValueLabel(Color color) {
        this.colorInformationValueLabel= color;
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

        lbl.setLayoutX(((startX + (double)width / 2.7) + 10));
        lbl.setLayoutY((startY - 10));
        lbl.setTextFill(color);
        // lbl.setFont(font);
        lbl.setFont(fontMonoton);
        lbl.setText(labelValue);

        lbl.getStyleClass().add("value-label");

        pane.getChildren().add(lbl);
    }


    /**
     * Overloaded method (traded int fontSize, for Font font).
     * Draws the specified value to the specified Label in the specified Pane at the specified location and size
     * @param pane Pane parameter - Pane to draw value in
     * @param lbl Label paramter - Label to draw value in
     * @param color Color parameter - Color to draw the value in
     * @param font Font parameter - The Font (including font size) to draw the title Label in
     * @param labelValue String parameter - String representation of the value to draw
     * @param startX int parameter - x-coordinate to start drawing in specified Pane
     * @param startY int parameter - y-coordinate to start drawing in specified Pane
     * @param width int parameter - width in pixels of the drawn container
     */
    private void drawGameInformationValueLabel(
            Pane pane, Label lbl, Color color, Font font, String labelValue, int startX, int startY, int width
    ) {
        lbl.setLayoutX(((startX + (double)width / 2.7) + 10));
        lbl.setLayoutY((startY - 10));
        lbl.setTextFill(color);
        lbl.setFont(font);
        lbl.setText(labelValue);

        lbl.getStyleClass().add("value-label");

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
    /*
    private void drawGameInformationTitleLabel(
            Pane pane, Color color, Label labelFor, String labelText, int fontSize, int startX, int startY
    ) {
        Label lbl = new Label(labelText);
        Font font = new Font(fontSize);
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        // lbl.setStyle("-fx-font-family: Press Start 2P;");
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        lbl.setLabelFor(labelFor);
        lbl.setLayoutX((startX + 10));
        lbl.setLayoutY((startY - 10));
        lbl.setTextFill(color);
        // lbl.setFont(font);
        lbl.setFont(fontPressStart2P);

        lbl.getStyleClass().add("title-label");

        pane.getChildren().add(lbl);
    }
     */


    /**
     * Overloaded method that switches String labelText for Label lbl.
     * Draws the Label title and sets the labelFor property of the specified Label in the specified Pane
     * at the specified location and size
     * @param pane Pane parameter - Pane to draw the Label in
     * @param color Color parameter - Color to draw the Label in
     * @param labelFor Label parameter - Label to set the labelFor property to
     * @param lbl Label parameter - The Label to add to the Pane
     * @param font Font parameter - Font to be used for the text of the specified Label
     * @param startX int parameter - x-coordinate to start drawing in the specified Pane
     * @param startY int parameter - y-coordinate to start drawing in the specified Pane
     */
    private void drawGameInformationTitleLabel(
            Pane pane, Color color, Label labelFor, Label lbl, Font font, int startX, int startY
    ) {
        lbl.setLabelFor(labelFor);
        lbl.setLayoutX((startX + 10));
        lbl.setLayoutY((startY - 10));
        lbl.setTextFill(color);
        lbl.setFont(font);

        lbl.getStyleClass().add("title-label");

        pane.getChildren().add(lbl);
    }


    /**
     * Overloaded method (traded int fontSize, for Font font). Draws a title Label and sets the labelFor
     * property of the specified Label in the specified Pane at the specified location and size
     * @param pane Pane parameter - Pane to draw the Label in
     * @param color Color parameter - Color to draw the Label in
     * @param labelFor Label parameter - Label to set the labelFor property to
     * @param labelText String parameter - String that makes up the text of the Label
     * @param font Font parameter - The Font (including font size) to draw the title Label in
     * @param startX int parameter - x-coordinate to start drawing in the specified Pane
     * @param startY int parameter - y-coordinate to start drawing in the specified Pane
     */
    /*
    private void drawGameInformationTitleLabel(
            Pane pane, Color color, Label labelFor, String labelText, Font font, int startX, int startY
    ) {
        Label lbl = new Label(labelText);

        lbl.setLabelFor(labelFor);
        lbl.setLayoutX((startX + 10));
        lbl.setLayoutY((startY - 10));
        lbl.setTextFill(color);
        lbl.setFont(font);

        lbl.getStyleClass().add("title-label");

        pane.getChildren().add(lbl);
    }
     */


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
                (startX + ((double)width / 2.7)),
                startY,
                (startX + ((double)width / 2.7)),
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
    /*
    private void drawTitleLabel(
            Pane pane, Color color, int fontSize, String labelText, int startX, int startY, int width
    ) {
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////// TEST CODE BELOW //////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        Text txtOfLabel = new Text();
        txtOfLabel.setText(labelText);

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////// TEST CODE ABOVE //////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////




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
     */


    /**
     * Overloaded method: replaces String labelText with Label lbl and int fontSize with Font font
     * Draws the title Label for a game information display element
     * @param pane Pane parameter - Pane to draw the Label in
     * @param color Color parameter - Color to draw the Label in
     * @param font Font parameter - Font of the text of the specified Label
     * @param lbl Label parameter - The specified Label
     * @param startX int parameter - x-coordinate to start drawing in the specified Pane
     * @param startY int parameter - y-coordinate to start drawing in the specified Pane
     * @param width int parameter - width in pixels of container for the Label
     */
    private void drawTitleLabel(
            Pane pane, Color color, Font font, Label lbl, int startX, int startY, int width
    ) {
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
     * Overloaded method that draws the title Label for a game information display element and
     * allows the Font to be passed in as a parameter
     * @param pane Pane parameter - Pane to draw the Label in
     * @param color Color parameter - Color to draw the Label in
     * @param font Font parameter - Set the Font (including font size) of the Label
     * @param labelText String parameter - String text to draw in the specified Label
     * @param startX int parameter - x-coordinate to start drawing in the specified Pane
     * @param startY int parameter - y-coordinate to start drawing in the specified Pane
     * @param width int parameter - width in pixels of container for the Label
     */
    /*
    private void drawTitleLabel(
            Pane pane, Color color, Font font, String labelText, int startX, int startY, int width
    ) {
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
     */


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
     */
    private void drawNextTetramino() {
        for (int row = 0; row < nextTetraminoMatrix.length; row++) {
            for (int col = 0; col < nextTetraminoMatrix[row].length; col++) {
                if (nextTetraminoMatrix[row][col] != 0) {
                    drawBlock(
                            paneNextTetramino,
                            nextBlocks,
                            (30 + START_X_NEXT_TETRAMINO), (60 + START_Y_NEXT_TETRAMINO),
                            row, col,
                            nextTetraminoType
                    );
                }
            }
        }
    }


    /**
     * Generates the Tetramino that will be the next Tetramino to fall from the top upcoming Tetramino
     * and draws it in paneNextTetramino
     */
    private void generateNextTetraminoFromUpcomingTetraminoTop() {
        eraseNextTetramino();
        nextTetraminoType = upcomingTetraminoTypeTop;

        Color newColor = getBlockColor(nextTetraminoType);
        Block nextTetraminoBlock = new Block(newColor);

        nextTetraminoMatrix = nextTetraminoBlock.getBlockMatrix(nextTetraminoType, 0);

        drawNextTetramino();
    }


    /**
     * Erases all Rectangles that make up the top upcoming Tetramino
     */
    private void eraseUpcomingTetraminoTop() {
        for (int row = 0; row < upcomingTopBlocks.length; row++) {
            for (int col = 0; col < upcomingTopBlocks[row].length; col++) {
                paneUpcomingTetraminos.getChildren().remove(upcomingTopBlocks[row][col]);
            }
        }
    }


    /**
     * Draws the top upcoming Tetramino at the top of the Pane paneUpcomingTetraminos
     */
    private void drawUpcomingTetraminoTop() {
        for (int row = 0; row < upcomingTetraminoTopMatrix.length; row++) {
            for (int col = 0; col < upcomingTetraminoTopMatrix[row].length; col++) {
                if (upcomingTetraminoTopMatrix[row][col] != 0) {
                    drawBlock(
                            paneUpcomingTetraminos,
                            upcomingTopBlocks,
                            (30 + START_X_UPCOMING_TETRAMINOS), START_Y_UPCOMING_TETRAMINOS,
                            row, col,
                            upcomingTetraminoTypeTop
                    );
                }
            }
        }
    }


    /**
     * Generates the Tetramino that will be the top upcoming Tetramino from the middle upcoming Tetramino
     * and draws it in paneUpcomingTetraminos
     */
    private void generateUpcomingTetraminoTopFromUpcomingTetraminoMiddle() {
        eraseUpcomingTetraminoTop();
        upcomingTetraminoTypeTop = upcomingTetraminoTypeMiddle;

        Color color = getBlockColor(upcomingTetraminoTypeTop);
        Block upcomingTetraminoTopBlock = new Block(color);

        upcomingTetraminoTopMatrix = upcomingTetraminoTopBlock.getBlockMatrix(upcomingTetraminoTypeTop, 0);

        drawUpcomingTetraminoTop();
    }


    /**
     * Erases all Rectangles that make up the middle upcoming Tetramino
     */
    private void eraseUpcomingTetraminoMiddle() {
        for (int row = 0; row < upcomingMiddleBlocks.length; row++) {
            for (int col = 0; col < upcomingMiddleBlocks[row].length; col++) {
                paneUpcomingTetraminos.getChildren().remove(upcomingMiddleBlocks[row][col]);
            }
        }
    }


    /**
     * Draws the middle upcoming Tetramino at the middle of the Pane paneUpcomingTetraminos
     */
    private void drawUpcomingTetraminoMiddle() {
        for (int row = 0; row < upcomingTetraminoMiddleMatrix.length; row++) {
            for (int col = 0; col < upcomingTetraminoMiddleMatrix[row].length; col++) {
                if (upcomingTetraminoMiddleMatrix[row][col] != 0) {
                    drawBlock(
                            paneUpcomingTetraminos,
                            upcomingMiddleBlocks,
                            (30 + START_X_UPCOMING_TETRAMINOS),
                            (START_Y_UPCOMING_TETRAMINOS + dimensions_upcomingTetraminoDistanceToMiddle),
                            row, col,
                            upcomingTetraminoTypeMiddle
                    );
                }
            }
        }
    }


    /**
     * Generates the Tetramino that will be the middle upcoming Tetramino from the bottom upcoming Tetramino
     * and draws it in paneUpcomingTetraminos
     */
    private void generateUpcomingTetraminoMiddleFromUpcomingTetraminoBottom() {
        eraseUpcomingTetraminoMiddle();
        upcomingTetraminoTypeMiddle = upcomingTetraminoTypeBottom;

        Color color = getBlockColor(upcomingTetraminoTypeMiddle);
        Block upcomingTetraminoMiddleBlock = new Block(color);

        upcomingTetraminoMiddleMatrix = upcomingTetraminoMiddleBlock.getBlockMatrix(upcomingTetraminoTypeMiddle, 0);

        drawUpcomingTetraminoMiddle();
    }


    /**
     * Erases all Rectangles that make up the bottom upcoming Tetramino
     */
    private void eraseUpcomingTetraminoBottom() {
        for (int row = 0; row < upcomingBottomBlocks.length; row++) {
            for (int col = 0; col < upcomingBottomBlocks[row].length; col++) {
                paneUpcomingTetraminos.getChildren().remove(upcomingBottomBlocks[row][col]);
            }
        }
    }


    /**
     * Draws the bottom upcoming Tetramino at the bottom of the Pane paneUpcomingTetraminos
     */
    private void drawUpcomingTetraminoBottom() {
        for (int row = 0; row < upcomingTetraminoBottomMatrix.length; row++) {
            for (int col = 0; col < upcomingTetraminoBottomMatrix[row].length; col++) {
                if (upcomingTetraminoBottomMatrix[row][col] != 0) {
                    drawBlock(
                            paneUpcomingTetraminos,
                            upcomingBottomBlocks,
                            (30 + START_X_UPCOMING_TETRAMINOS),
                            (START_Y_UPCOMING_TETRAMINOS + dimensions_upcomingTetraminoDistanceToBottom),
                            row, col,
                            upcomingTetraminoTypeBottom
                    );
                }
            }
        }
    }


    /**
     * Generates a new random Tetramino that will be the bottom upcoming Tetramino
     * and draws it in paneUpcomingTetraminos
     */
    private void generateRandomUpcomingTetraminoBottom() {
        eraseUpcomingTetraminoBottom();
        upcomingTetraminoTypeBottom = generateRandomTetraminoType();

        Color color = getBlockColor(upcomingTetraminoTypeBottom);
        Block upcomingTetraminoBottomBlock = new Block(color);

        upcomingTetraminoBottomMatrix = upcomingTetraminoBottomBlock.getBlockMatrix(upcomingTetraminoTypeBottom, 0);

        drawUpcomingTetraminoBottom();
    }


    /**
     * Generates random int from 1 to the value corresponding to the number of unique Tetramino types
     * @return int value - (from 1 to the number of unique Tetramino types)
     */
    private int generateRandomTetraminoType() {
        return (1 + random.nextInt(numberOfUniqueTetraminos));
    }


    /**
     * Starts a Timer Object to keep track of the time expired during game play
     */
    private void startTimer() {
        timerObj = new Timer();

        timerObj.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run () {
                    Platform.runLater(() -> {
                        if (mSeconds >= 1_000) {
                            seconds++;
                            mSeconds = 0;

                            if (seconds >= 60) {
                                minutes++;
                                seconds = 0;

                                if (minutes >= 60) {
                                    hours++;
                                    minutes = 0;
                                }
                            }
                        }

                        String timerStr = String.format("%02d : %02d : %02d : %03d", hours, minutes, seconds, mSeconds);
                        updateTimerValueAndLabel(timerStr);
                        mSeconds++;
                    });
                }
        }, 0, 1);
    }


    /**
     * Removes the expired Label lblTimerValue, updates the time String, update the Label lblTimerValue to
     * the new time String, then adds Label lblTimerValue to the Pane paneTimer
     * @param strTime String parameter - A String formatted like a stopwatch with
     *                two digits for hours, minutes and seconds and three digits for milliseconds
     */
    private void updateTimerValueAndLabel(String strTime) {
        paneTimer.getChildren().remove(lblTimerValue);
        textTimerValue.setText(strTime);
        lblTimerValue.setText(textTimerValue.getText());
        paneTimer.getChildren().add(lblTimerValue);
    }


    /**
     * Calls .cancel() on the Timer Object (when you create a new Timer obj, using .cancel() functions like pause)
     */
    private void pauseTimerObj() {
        this.timerObj.cancel();
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
            pauseTimerObj();
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
     * Controls what happens when a user hits the "rotate falling Tetramino clockwise" button
     */
    private void controlRotateClockwiseButton() {
        if (canFallingTetraminoRotateClockwise()) {
            rotateFallingTetraminoClockwise();
        } else {
            eraseAndRemoveFallingTetramino();
        }
        placeFallingTetraminoIntoBoardMatrix();
        drawFallingTetramino();
    }


    /**
     * Controls what happens when a user hits the "rotate falling Tetramino left counterclockwise" button
     */
    private void controlRotateCounterclockwiseButton() {
        if (canFallingTetraminoRotateCounterclockwise()) {
            rotateFallingTetraminoCounterclockwise();
        } else {
            eraseAndRemoveFallingTetramino();
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
            generateNewFallingTetraminoFromNextTetramino();
            generateNextTetraminoFromUpcomingTetraminoTop();
            generateUpcomingTetraminoTopFromUpcomingTetraminoMiddle();
            generateUpcomingTetraminoMiddleFromUpcomingTetraminoBottom();
            generateRandomUpcomingTetraminoBottom();
        } else {
            generateBottomUpcomingTetraminoFromMiddleUpcomingTetramino();
            generateMiddleUpcomingTetraminoFromTopUpcomingTetramino();
            generateTopUpcomingTetraminoFromNextTetramino();
            generateNextTetraminoFromFallingTetramino();
            generateFallingTetraminoFromHoldTetramino(holdTetraminoType);
        }
        placeFallingTetraminoIntoBoardMatrix();
        drawFallingTetramino();
    }


    /**
     * Controls what happens when a user hits the "pause" button
     */
    private void controlPauseButton() {
        if (timer.getStatus() == Animation.Status.RUNNING) {
            pauseGame();
        } else {
            closePauseMenu();
        }
    }


    /**
     * Pauses the current game and opens the pause menu
     */
    private void pauseGame() {
        timer.pause();
        openPauseMenu();
    }


    /**
     * NEED TO REFACTOR (CREATE PAUSE MENU IN SEPARATE METHOD, NEED TO DO FOR ALL MENUS) -
     * Creates and displays the pause menu
     */
    private void openPauseMenu() {
        pauseTimerObj();

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
        Scene scenePauseMenu = new Scene(gridPanePauseMenu, (BLOCK_SIZE * 8), (BLOCK_SIZE * 9));
        stagePause = new Stage();
        stagePause.setTitle("Pause Menu");
        stagePause.setScene(scenePauseMenu);
        stagePause.show();

        scenePauseMenu.getStylesheets().add("resources/styling/pause-menu.css");

        stagePause.setOnCloseRequest(e -> {
            e.consume();
            closePauseMenu();
        });

        btnResume.setOnMouseClicked(e -> {
            closePauseMenu();
        });

        btnResume.setOnKeyPressed(e -> {
            if (e.getCode() == controlKeyPause || e.getCode() == KeyCode.ENTER) {
                closePauseMenu();
            }
        });

        btnReset.setOnMouseClicked(e -> {
            resetGame();

            // Close the pause menu and draw the new current falling Tetramino to restart a new game at initial speed
            closePauseMenu();
            drawFallingTetramino();
        });

        btnReset.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                resetGame();

                // Close the pause menu and draw the new current falling Tetramino to restart a new game at initial speed
                closePauseMenu();
                drawFallingTetramino();
            }
        });

        btnOptions.setOnMouseClicked(e -> {
            stagePause.close();
            openOptionsMenu();
        });

        btnOptions.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                closeStage(stagePause);
                openOptionsMenu();
            }
        });

        btnExit.setOnMouseClicked(e -> {
            closeProgram(stagePause, true);
        });

        btnExit.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                closeProgram(stagePause, true);
            }
        });
    }


    /**
     * Closes the pause menu Stage and restarts the game
     */
    private void closePauseMenu() {
        closeStage(stagePause);
        startTimer();
        runGameAtSpecifiedSpeed(speed);
    }


    /**
     * Creates and opens the options menu from the pause menu
     */
    private void openOptionsMenu() {
        VBox vBoxOptions = new VBox();

        Button btnControls = new Button("CONTROLS");
        Button btnColors = new Button("COLORS");
        Button btnFonts = new Button("FONTS");
        Button btnBack = new Button("BACK");
        /* MORE BUTTONS TO BE ADDED LATER WHEN MORE OPTIONS ARE ADDED */

        vBoxOptions.getChildren().addAll(btnControls, btnColors, btnFonts, btnBack);

        Scene sceneOptions = new Scene(vBoxOptions, (BLOCK_SIZE * 8), (BLOCK_SIZE * 9));
        stageOptions = new Stage();
        stageOptions.setTitle("Options Menu");
        stageOptions.setScene(sceneOptions);
        stageOptions.show();
        sceneOptions.getStylesheets().add("/resources/styling/options-menu.css");

        stageOptions.setOnCloseRequest(e -> {
            e.consume();
            closeStage(stageOptions);
            openPauseMenu();
        });

        btnControls.setOnMouseClicked(e -> {
            closeStage(stageOptions);
            openControlOptionsMenu();
        });

        btnControls.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                closeStage(stageOptions);
                openControlOptionsMenu();
            }
        });

        btnColors.setOnMouseClicked(e -> {
            closeStage(stageOptions);
            openColorOptionsMenu();
        });

        btnColors.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                closeStage(stageOptions);
                openColorOptionsMenu();
            }
        });

        btnFonts.setOnMouseClicked(e -> {
            closeStage(stageOptions);
            openFontOptionsMenu();
        });

        btnFonts.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                closeStage(stageOptions);
                openFontOptionsMenu();
            }
        });

        btnBack.setOnMouseClicked(e -> {
            closeStage(stageOptions);
            openPauseMenu();
        });

        btnBack.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                closeStage(stageOptions);
                openPauseMenu();
            }
        });
    }


    /**
     * Opens the controls menu from the options menu
     */
    private void openControlOptionsMenu() {
        stageControlOptions = new Stage();

        GridPane gridPaneControlOptions = new GridPane();

        Label lblMoveLeft = new Label("Move Left");
        gridPaneControlOptions.setConstraints(lblMoveLeft, 0, 0);
        btnMoveLeft = new Button(controlKeyMoveLeft.getName());
        gridPaneControlOptions.setConstraints(btnMoveLeft, 1, 0);
        Label lblMoveRight = new Label("Move Right");
        gridPaneControlOptions.setConstraints(lblMoveRight, 0, 1);
        btnMoveRight = new Button(controlKeyMoveRight.getName());
        gridPaneControlOptions.setConstraints(btnMoveRight, 1, 1);
        Label lblSoftDrop = new Label("Soft Drop");
        gridPaneControlOptions.setConstraints(lblSoftDrop, 0, 2);
        btnSoftDrop = new Button(controlKeySoftDrop.getName());
        gridPaneControlOptions.setConstraints(btnSoftDrop, 1, 2);
        Label lblHardDrop = new Label("Hard Drop");
        gridPaneControlOptions.setConstraints(lblHardDrop, 0, 3);
        btnHardDrop = new Button(controlKeyHardDrop.getName());
        gridPaneControlOptions.setConstraints(btnHardDrop, 1, 3);
        Label lblRotateClockwise = new Label("Rotate Clockwise");
        gridPaneControlOptions.setConstraints(lblRotateClockwise, 0, 4);
        btnRotateClockwise = new Button(controlKeyRotateClockwise.getName());
        gridPaneControlOptions.setConstraints(btnRotateClockwise, 1, 4);
        Label lblRotateCounterclockwise = new Label("Rotate Counterclockwise");
        gridPaneControlOptions.setConstraints(lblRotateCounterclockwise, 0, 5);
        btnRotateCounterclockwise = new Button(controlKeyRotateCounterclockwise.getName());
        gridPaneControlOptions.setConstraints(btnRotateCounterclockwise, 1, 5);
        Label lblHold = new Label("Hold");
        gridPaneControlOptions.setConstraints(lblHold, 0, 6);
        btnHold = new Button(controlKeyHold.getName());
        gridPaneControlOptions.setConstraints(btnHold, 1, 6);
        Label lblPause = new Label("Pause");
        gridPaneControlOptions.setConstraints(lblPause, 0, 7);
        btnPause = new Button(controlKeyPause.getName());
        gridPaneControlOptions.setConstraints(btnPause, 1, 7);
        Button btnBack = new Button("Back");
        gridPaneControlOptions.setConstraints(btnBack, 0, 8, 3, 1);
        btnBack.setId("btnBack");

        gridPaneControlOptions.getChildren().addAll(
                lblMoveLeft, btnMoveLeft,
                lblMoveRight, btnMoveRight,
                lblSoftDrop, btnSoftDrop,
                lblHardDrop, btnHardDrop,
                lblRotateClockwise, btnRotateClockwise,
                lblRotateCounterclockwise, btnRotateCounterclockwise,
                lblHold, btnHold,
                lblPause, btnPause,
                btnBack
        );

        gridPaneControlOptions.setId("grid");

        sceneControlOptions = new Scene(gridPaneControlOptions, (BLOCK_SIZE * 16), (BLOCK_SIZE * 15));
        stageControlOptions.setTitle("Control Options");
        stageControlOptions.setScene(sceneControlOptions);
        stageControlOptions.show();
        sceneControlOptions.getStylesheets().add("/resources/styling/control-options-menu.css");

        Button[] controlOptionsMenuButtons = new Button[] {
                btnMoveLeft, btnMoveRight, btnSoftDrop, btnHardDrop,
                btnRotateClockwise, btnRotateCounterclockwise, btnHold, btnPause, btnBack
        };
        setButtonArrayButtons(controlOptionsMenuButtons);

        stageControlOptions.setOnCloseRequest(e -> {
            e.consume();
            closeStage(stageControlOptions);
            openOptionsMenu();
        });

        btnBack.setOnMouseClicked(e -> {
            closeStage(stageControlOptions);
            openOptionsMenu();
        });

        btnBack.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                closeStage(stageControlOptions);
                openOptionsMenu();
            }
        });

        btnMoveLeft.setOnMouseClicked(e -> {
            handleControlOptionsMenuButtonClick(
                    btnMoveLeft, controlOptionsMenuButtons, 0,
                    keyHandlerBtnMoveLeft, controlKeyMoveLeft
            );
        });

        btnMoveLeft.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                handleControlOptionsMenuButtonClick(
                        btnMoveLeft, controlOptionsMenuButtons, 0,
                        keyHandlerBtnMoveLeft, controlKeyMoveLeft
                );
            }
        });

        btnMoveRight.setOnMouseClicked(e -> {
            handleControlOptionsMenuButtonClick(
                    btnMoveRight, controlOptionsMenuButtons, 1,
                    keyHandlerBtnMoveRight, controlKeyMoveRight
            );
        });

        btnMoveRight.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                handleControlOptionsMenuButtonClick(
                        btnMoveRight, controlOptionsMenuButtons, 1,
                        keyHandlerBtnMoveRight, controlKeyMoveRight
                );
            }
        });

        btnSoftDrop.setOnMouseClicked(e -> {
            handleControlOptionsMenuButtonClick(
                    btnSoftDrop, controlOptionsMenuButtons, 2,
                    keyHandlerBtnSoftDrop, controlKeySoftDrop
            );
        });

        btnSoftDrop.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                handleControlOptionsMenuButtonClick(
                        btnSoftDrop, controlOptionsMenuButtons, 2,
                        keyHandlerBtnSoftDrop, controlKeySoftDrop
                );
            }
        });

        btnHardDrop.setOnMouseClicked(e -> {
            handleControlOptionsMenuButtonClick(
                    btnHardDrop, controlOptionsMenuButtons, 3,
                    keyHandlerBtnHardDrop, controlKeyHardDrop
            );
        });

        btnHardDrop.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                handleControlOptionsMenuButtonClick(
                        btnHardDrop, controlOptionsMenuButtons, 3,
                        keyHandlerBtnHardDrop, controlKeyHardDrop
                );
            }
        });

        btnRotateClockwise.setOnMouseClicked(e -> {
            handleControlOptionsMenuButtonClick(
                    btnRotateClockwise, controlOptionsMenuButtons, 4,
                    keyHandlerBtnRotateClockwise, controlKeyRotateClockwise
            );
        });

        btnRotateClockwise.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                handleControlOptionsMenuButtonClick(
                        btnRotateClockwise, controlOptionsMenuButtons, 4,
                        keyHandlerBtnRotateClockwise, controlKeyRotateClockwise
                );
            }
        });

        btnRotateCounterclockwise.setOnMouseClicked(e -> {
            handleControlOptionsMenuButtonClick(
                    btnRotateCounterclockwise, controlOptionsMenuButtons, 5,
                    keyHandlerBtnRotateCounterclockwise, controlKeyRotateCounterclockwise
            );
        });

        btnRotateCounterclockwise.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                handleControlOptionsMenuButtonClick(
                        btnRotateCounterclockwise, controlOptionsMenuButtons, 5,
                        keyHandlerBtnRotateCounterclockwise, controlKeyRotateCounterclockwise
                );
            }
        });

        btnHold.setOnMouseClicked(e -> {
            handleControlOptionsMenuButtonClick(
                    btnHold, controlOptionsMenuButtons, 6,
                    keyHandlerBtnHold, controlKeyHold
            );
        });

        btnHold.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                handleControlOptionsMenuButtonClick(
                        btnHold, controlOptionsMenuButtons, 6,
                        keyHandlerBtnHold, controlKeyHold
                );
            }
        });

        btnPause.setOnMouseClicked(e -> {
            handleControlOptionsMenuButtonClick(
                    btnPause, controlOptionsMenuButtons, 7,
                    keyHandlerBtnPause, controlKeyPause
            );
        });

        btnPause.setOnKeyPressed(e -> {
            if (e.getCode() == controlKeyPause) {
                handleControlOptionsMenuButtonClick(
                        btnPause, controlOptionsMenuButtons, 7,
                        keyHandlerBtnPause, controlKeyPause
                );
            }
        });
    }


    /**
     * Helps handle Button clicks in the control options menu -
     * disables other Buttons once a Button is clicked, allows a Button to be clicked again to cancel
     * setting a KeyCode key to that control KeyCode, enables Buttons again after a KeyCode is pressed or
     * the user clicks the Button of the control key they were attempting to set, again.
     * @param btn Button parameter - The Button that was clicked
     * @param btns Button[] parameter - The Button[] that contains the Buttons from the control options menu
     * @param btnIndex int index - The index of the Button btn parameter inside the Button[] btns parameter
     * @param eventHandler EventHandler (KeyEvent) parameter - corresponds to the Event Handler for Button btn parameter
     * @param key KeyCode parameter - The KeyCode of the KeyCode that presently corresponds to the KeyCode being changed
     */
    private void handleControlOptionsMenuButtonClick(
            Button btn, Button[] btns, int btnIndex, EventHandler<KeyEvent> eventHandler, KeyCode key
    ) {
        if (!hasListenerBeenAdded) {
            // So the user can click the Button again to cancel the EventListener
            // Without setting hasListenerBeenAdded to true, clicking on the Button, after clicking
            // the Button to add the EventListener, does nothing
            hasListenerBeenAdded = true;
            btn.setText("---");
            disableAllControlOptionsMenuButtonsOtherThanSpecifiedButton(btns, btnIndex);
            sceneControlOptions.addEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
        } else {
            // So the user can click the Button again to cancel the EventListener
            // Without setting hasListenerBeenAdded to false, if the user tries to click on the Button
            // after setting its KeyCode value, the Button will not do anything
            hasListenerBeenAdded = false;

            btn.setText(key.getName());
            enableAllButtonsAndWindowControlsInControlOptionsMenu(btns);
            sceneControlOptions.removeEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
        }
    }


    /**
     * Sets the class variable Button[] buttons to the passed in Button[] buttons
     * @param buttons Button[] parameter - The Button[] array to set the class variable Button[] buttons equal to
     */
    private void setButtonArrayButtons(Button[] buttons) {
        this.buttons = buttons;
    }


    /**
     * Disable all the Buttons in the control options menu other than the Button at the specified index
     * in the passed in Button[] array
     * @param buttons Button[] parameter - Button array that stores all the Buttons in the control options menu
     * @param btnIndex int parameter - The index in the buttons parameter where the specified Button is
     */
    private void disableAllControlOptionsMenuButtonsOtherThanSpecifiedButton(Button[] buttons, int btnIndex) {
        // Disable all Buttons in the Stage other than the one at the specified index in the Button[] array
        for (int i = 0; i < buttons.length; i++) {
            if (i != btnIndex) {
                buttons[i].setDisable(true);
            }
        }

        // Make when a user clicks the "X" window control button (close button) on the Stage do nothing
        stageControlOptions.setOnCloseRequest(e -> {
            e.consume();
        });
    }


    /**
     * Enables all the Buttons and window controls (min, max, close) in the control options menu Stage
     * @param buttons Button[] parameter - The Button[] array that stores all the Buttons in
     *                the control options menu Stage
     */
    private void enableAllButtonsAndWindowControlsInControlOptionsMenu(Button[] buttons) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setDisable(false);
        }

        stageControlOptions.setOnCloseRequest(e -> {
            e.consume();
            closeStage(stageControlOptions);
            openOptionsMenu();
        });
    }


    /**
     * Returns the index in the KeyCode[] controlKeys of the specified KeyCode key
     * @param key KeyCode parameter - The KeyCode you want to check if it is already assigned
     * @return int value
     * (-1 = KeyCode has not been assigned, anything else = index of control Key already assigned that KeyCode)
     */
    private int indexOfOccupiedControlKey(KeyCode key) {
        for (int i = 0; i < controlKeys.length; i++) {
            if (key == controlKeys[i]) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Creates and opens the Color Options Menu from the Options Menu
     */
    private void openColorOptionsMenu() {
        Stage stageColorOptionsMenu = new Stage();
        GridPane gridPaneColorOptionsMenu = new GridPane();

        Label lblTetriminoColors = new Label("Tetrimino Colors");
        lblTetriminoColors.getStyleClass().add("title-label");
        GridPane.setHalignment(lblTetriminoColors, HPos.CENTER);
        gridPaneColorOptionsMenu.setConstraints(lblTetriminoColors, 0, 0, 2, 1);
        Label lblColorOBlock = new Label("O-Block");
        gridPaneColorOptionsMenu.setConstraints(lblColorOBlock, 0, 1);
        ColorPicker colorPickerOBlock = new ColorPicker(colorOBlock);
        colorPickerOBlock.getStyleClass().add("button");
        gridPaneColorOptionsMenu.setConstraints(colorPickerOBlock, 1, 1);
        Label lblColorIBlock = new Label("I-Block");
        gridPaneColorOptionsMenu.setConstraints(lblColorIBlock, 0, 2);
        ColorPicker colorPickerIBlock = new ColorPicker(colorIBlock);
        colorPickerIBlock.getStyleClass().add("button");
        gridPaneColorOptionsMenu.setConstraints(colorPickerIBlock, 1, 2);
        Label lblColorSBlock = new Label("S-Block");
        gridPaneColorOptionsMenu.setConstraints(lblColorSBlock, 0, 3);
        ColorPicker colorPickerSBlock = new ColorPicker(colorSBlock);
        colorPickerSBlock.getStyleClass().add("button");
        gridPaneColorOptionsMenu.setConstraints(colorPickerSBlock, 1, 3);
        Label lblColorZBlock = new Label("Z-Block");
        gridPaneColorOptionsMenu.setConstraints(lblColorZBlock, 0, 4);
        ColorPicker colorPickerZBlock = new ColorPicker(colorZBlock);
        colorPickerZBlock.getStyleClass().add("button");
        gridPaneColorOptionsMenu.setConstraints(colorPickerZBlock, 1, 4);
        Label lblColorLBlock = new Label("L-Block");
        gridPaneColorOptionsMenu.setConstraints(lblColorLBlock, 0, 5);
        ColorPicker colorPickerLBlock = new ColorPicker(colorLBlock);
        colorPickerLBlock.getStyleClass().add("button");
        gridPaneColorOptionsMenu.setConstraints(colorPickerLBlock, 1, 5);
        Label lblColorJBlock = new Label("J-Block");
        gridPaneColorOptionsMenu.setConstraints(lblColorJBlock, 0, 6);
        ColorPicker colorPickerJBlock = new ColorPicker(colorJBlock);
        colorPickerJBlock.getStyleClass().add("button");
        gridPaneColorOptionsMenu.setConstraints(colorPickerJBlock, 1, 6);
        Label lblColorTBlock = new Label("T-Block");
        gridPaneColorOptionsMenu.setConstraints(lblColorTBlock, 0, 7);
        ColorPicker colorPickerTBlock = new ColorPicker(colorTBlock);
        colorPickerTBlock.getStyleClass().add("button");
        gridPaneColorOptionsMenu.setConstraints(colorPickerTBlock, 1, 7);
        Label lblMoreColorOptions = new Label("More Color Options");
        lblMoreColorOptions.getStyleClass().add("title-label");
        GridPane.setHalignment(lblMoreColorOptions, HPos.CENTER);
        gridPaneColorOptionsMenu.setConstraints(lblMoreColorOptions, 0, 8, 2, 1);
        Label lblColorBlockBorderAndGrid = new Label("Block Border / Grid Color");
        gridPaneColorOptionsMenu.setConstraints(lblColorBlockBorderAndGrid, 0, 9);
        ColorPicker colorPickerBlockBorderAndGrid = new ColorPicker(colorBorder);
        colorPickerBlockBorderAndGrid.getStyleClass().add("button");
        gridPaneColorOptionsMenu.setConstraints(colorPickerBlockBorderAndGrid, 1, 9);
        Label lblColorGameBoard = new Label("Game Board");
        gridPaneColorOptionsMenu.setConstraints(lblColorGameBoard, 0, 10);
        ColorPicker colorPickerGameBoard = new ColorPicker(colorEmptyBlock);
        colorPickerGameBoard.getStyleClass().add("button");
        gridPaneColorOptionsMenu.setConstraints(colorPickerGameBoard, 1, 10);
        Label lblGrid = new Label("Grid");
        ToggleGroup toggleGroupGrid = new ToggleGroup();
        RadioButton rdoBtnHideGrid = new RadioButton("Hide Grid");
        rdoBtnHideGrid.setToggleGroup(toggleGroupGrid);
        rdoBtnHideGrid.setSelected(true);
        RadioButton rdoBtnShowGrid = new RadioButton("Show Grid");
        rdoBtnShowGrid.setToggleGroup(toggleGroupGrid);
        // gridPaneColorOptionsMenu.setConstraints(colorPickerGameBoard, 1, 10);
        gridPaneColorOptionsMenu.setConstraints(lblGrid, 0, 11);
        gridPaneColorOptionsMenu.setConstraints(rdoBtnHideGrid, 1, 11);
        gridPaneColorOptionsMenu.setConstraints(rdoBtnShowGrid, 1, 12);
        // ColorPicker colorPickerGameBoardWithBlockBorders = new ColorPicker(colorEmptyBlock);
        // gridPaneColorOptionsMenu.setConstraints(colorPickerGameBoardWithBlockBorders, 1, 11);
        Button btnBack = new Button("Back");
        gridPaneColorOptionsMenu.setConstraints(btnBack, 0, 13);

        gridPaneColorOptionsMenu.getChildren().addAll(
                lblTetriminoColors,
                lblColorOBlock, colorPickerOBlock,
                lblColorIBlock, colorPickerIBlock,
                lblColorSBlock, colorPickerSBlock,
                lblColorZBlock, colorPickerZBlock,
                lblColorLBlock, colorPickerLBlock,
                lblColorJBlock, colorPickerJBlock,
                lblColorTBlock, colorPickerTBlock,
                lblMoreColorOptions,
                lblColorBlockBorderAndGrid, colorPickerBlockBorderAndGrid,
                lblColorGameBoard, colorPickerGameBoard,
                lblGrid,
                rdoBtnHideGrid, rdoBtnShowGrid,
                btnBack
        );

        Scene sceneColorOptionsMenu = new Scene(gridPaneColorOptionsMenu, (BLOCK_SIZE * 16), (BLOCK_SIZE * 16));
        sceneColorOptionsMenu.getStylesheets().add("/resources/styling/color-options-menu.css");
        stageColorOptionsMenu.setScene(sceneColorOptionsMenu);
        stageColorOptionsMenu.setTitle("Color Options");
        stageColorOptionsMenu.show();

        stageColorOptionsMenu.setOnCloseRequest(e -> {
            e.consume();
            closeStage(stageColorOptionsMenu);
            openOptionsMenu();
        });

        colorPickerOBlock.setOnAction(new EventHandler() {
            public void handle(Event t) {
                handleColorPicker(colorPickerOBlock, 1);
            }
        });

        colorPickerIBlock.setOnAction(new EventHandler() {
            public void handle(Event t) {
                handleColorPicker(colorPickerIBlock, 2);
            }
        });

        colorPickerSBlock.setOnAction(new EventHandler() {
            public void handle(Event t) {
                handleColorPicker(colorPickerSBlock, 3);
            }
        });

        colorPickerZBlock.setOnAction(new EventHandler() {
            public void handle(Event t) {
                handleColorPicker(colorPickerZBlock, 4);
            }
        });

        colorPickerLBlock.setOnAction(new EventHandler() {
            public void handle(Event t) {
                handleColorPicker(colorPickerLBlock, 5);
            }
        });

        colorPickerJBlock.setOnAction(new EventHandler() {
            public void handle(Event t) {
                handleColorPicker(colorPickerJBlock, 6);
            }
        });

        colorPickerTBlock.setOnAction(new EventHandler() {
            public void handle(Event t) {
                handleColorPicker(colorPickerTBlock, 7);
            }
        });

        colorPickerBlockBorderAndGrid.setOnAction(new EventHandler() {
            public void handle(Event t) {
                Color c = colorPickerBlockBorderAndGrid.getValue();
                setColorBorder(c);

                eraseAllBlocks(holdTetraminoType);
                drawAllBlocks(holdTetraminoType);
            }
        });

        colorPickerGameBoard.setOnAction(new EventHandler() {
            public void handle(Event t) {
                Color c = colorPickerGameBoard.getValue();
                setColorEmptyBlock(c);
                eraseBoard();
                drawGameBoardBackground();
                drawBoard();
                drawFallingTetramino();
            }
        });

        toggleGroupGrid.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (toggleGroupGrid.getSelectedToggle() != null) {
                    if (toggleGroupGrid.getSelectedToggle() == rdoBtnHideGrid) {
                        Color c = colorPickerGameBoard.getValue();
                        setColorEmptyBlock(c);
                        eraseBoard();
                        drawBorders(
                                paneGameBoard, START_X_BOARD, START_Y_BOARD,
                                dimensions_boardWidth, dimensions_boardHeight
                        );
                        drawGameBoardBackground();
                        drawBoard();
                        drawFallingTetramino();
                    } else {
                        Color c = colorPickerGameBoard.getValue();
                        setColorEmptyBlock(c);
                        eraseBoard();
                        drawGameBoardBackgroundWithGrid();
                        drawBoard();
                        drawFallingTetramino();
                    }
                }
            }
        });

        btnBack.setOnMouseClicked(e -> {
            closeStage(stageColorOptionsMenu);
            openOptionsMenu();
        });

        btnBack.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                closeStage(stageColorOptionsMenu);
                openOptionsMenu();
            }
        });
    }


    /**
     * Event handler for ColorPickers in the color options menu. It gets the Color value from
     * the ColorPicker, sets the Color of the corresponding Tetramino type blocks depending on
     * the int blockType passed in. It then erases all the blocks from all Panes and then redraws them
     * with the new Color
     * @param colorPicker ColorPicker parameter - The ColorPicker this method is handling the event of
     * @param blockType int parameter - Int value that corresponds to the Tetramino block type
     */
    private void handleColorPicker(ColorPicker colorPicker, int blockType) {
        Color c = colorPicker.getValue();

        switch (blockType) {
            case 1:     setColorOBlock(c);      break;
            case 2:     setColorIBlock(c);      break;
            case 3:     setColorSBlock(c);      break;
            case 4:     setColorZBlock(c);      break;
            case 5:     setColorLBlock(c);      break;
            case 6:     setColorJBlock(c);      break;
            case 7:     setColorTBlock(c);      break;
        }

        eraseAllBlocks(blockType);
        drawAllBlocks(blockType);
    }


    /**
     * Opens the Font options menu that allows users to select what Font they want used for different
     * Label types and Button types
     */
    private void openFontOptionsMenu() {
        Stage stageFontOptions = new Stage();
        GridPane gridPaneFontOptions = new GridPane();
        gridPaneFontOptions.setId("grid-pane-font");

        HBox hBoxComboBoxes = new HBox();

        ObservableList<String> fontOptions = FXCollections.observableArrayList(
                "Alumni Sans Inline One", "Black Ops One", "Blaka Hollow", "Bungee Shade",
                "Cabin Sketch", "Creepster", "Dancing Script", "Faster One", "Fredericka The Great",
                "Graduate", "Gugi", "Henny Penny", "Irish Grover", "Monoton", "Permanent Marker",
                "Press Start 2P", "Rubik Moonrocks", "Rye", "Square Peg", "Unifraktur Maguntia"
        );
        final ComboBox cmbxFonts = new ComboBox(fontOptions);
        cmbxFonts.setId("cmbx-fonts");

        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        ObservableList<Integer> fontSizeOptions = FXCollections.observableArrayList(
                8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28,
                30, 32, 34, 36, 38, 40, 42, 44, 46, 48
        );
        final ComboBox cmbxFontSize = new ComboBox(fontSizeOptions);
        cmbxFontSize.setId("cmbx-font-size");

        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        ObservableList<String> targetElements = FXCollections.observableArrayList(
                "Tetramino Container Title", "Game Information Title", "Game Information Value"
        );
        final ComboBox cmbxTargetElement = new ComboBox(targetElements);
        cmbxTargetElement.setId("cmbx-target-elements");

        hBoxComboBoxes.getChildren().addAll(cmbxFonts, region1, cmbxFontSize, region2, cmbxTargetElement);
        gridPaneFontOptions.setConstraints(hBoxComboBoxes, 0, 0);

        // HBox hBoxFontColor = new HBox();

        // Label lblFontColor = new Label("Font Color: ");
        ColorPicker colorPickerTargetElement = new ColorPicker(colorContainerTitleLabel);
        colorPickerTargetElement.getStyleClass().add("button");

        // hBoxFontColor.getChildren().addAll(lblFontColor, colorPickerTargetElement);
        // gridPaneFontOptions.setConstraints(hBoxFontColor, 0, 1);
        gridPaneFontOptions.setConstraints(colorPickerTargetElement, 0, 1);

        final Button btnApply = new Button("Apply");
        gridPaneFontOptions.setConstraints(btnApply, 0, 2);

        final TextArea txtareaDemoDisplayLetters = new TextArea();
        txtareaDemoDisplayLetters.setText("the quick brown fox jumps over the lazy dog " +
                "\n\nTHE QUICK BROWN FOX JUMPS OVER THE LAZY DOG");
        txtareaDemoDisplayLetters.setEditable(false);
        txtareaDemoDisplayLetters.setWrapText(true);
        txtareaDemoDisplayLetters.setId("txtarea-letters");
        gridPaneFontOptions.setConstraints(txtareaDemoDisplayLetters, 0, 3);

        final TextArea txtareaDemoDisplayNumbers = new TextArea();
        txtareaDemoDisplayNumbers.setText("1 2 3 4 5 6 7 8 9 0");
        txtareaDemoDisplayNumbers.setEditable(false);
        txtareaDemoDisplayNumbers.setWrapText(true);
        txtareaDemoDisplayNumbers.setId("txtarea-numbers");
        gridPaneFontOptions.setConstraints(txtareaDemoDisplayNumbers, 0, 4);

        final TextArea txtareaDemoDisplayPunctuationAndGlyphs = new TextArea();
        txtareaDemoDisplayPunctuationAndGlyphs.setText(", . < > ? / \\ | ; : \" ' [ ] { } \n" +
                "+ = _ - ` ~ ! @ # $ % ^ & * ( )");
        txtareaDemoDisplayPunctuationAndGlyphs.setEditable(false);
        txtareaDemoDisplayPunctuationAndGlyphs.setWrapText(true);
        txtareaDemoDisplayPunctuationAndGlyphs.setId("txtarea-glyphs");
        gridPaneFontOptions.setConstraints(txtareaDemoDisplayPunctuationAndGlyphs, 0, 5);

        final Button btnBack = new Button("BACK");
        gridPaneFontOptions.setConstraints(btnBack, 0, 6);

        gridPaneFontOptions.getChildren().addAll(
                hBoxComboBoxes,
                colorPickerTargetElement,    // hBoxFontColor,
                btnApply,
                txtareaDemoDisplayLetters,
                txtareaDemoDisplayNumbers,
                txtareaDemoDisplayPunctuationAndGlyphs,
                btnBack
        );

        Scene sceneFontOptions = new Scene(gridPaneFontOptions, (BLOCK_SIZE * 16), (BLOCK_SIZE * 15));
        stageFontOptions.setTitle("Font Options");
        stageFontOptions.setScene(sceneFontOptions);
        stageFontOptions.show();
        sceneFontOptions.getStylesheets().add("/resources/styling/font-options-menu.css");

        stageFontOptions.setOnCloseRequest(e -> {
            e.consume();
            closeStage(stageFontOptions);
            openOptionsMenu();
        });

        btnApply.setDisable(true);

        final Font[] fontSelected = new Font[1];
        final int[] fontSize = {20};
        final int[] targetElement = {-1};
        final boolean[] hasFontBeenSelected = {false};
        final boolean[] hasFontSizeBeenSelected = {false};
        final boolean[] hasTargetElementBeenSelected = {false};
        final int[] fontSelectionIndex = {-1};

        cmbxFonts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                System.out.println(newValue.toString());
                hasFontBeenSelected[0] = true;

                if (hasFontSizeBeenSelected[0] && hasTargetElementBeenSelected[0]) {
                    btnApply.setDisable(false);
                }

                fontSelectionIndex[0] = cmbxFonts.getSelectionModel().getSelectedIndex();
                System.out.println("fontSelectionIndex = " + fontSelectionIndex[0]);

                fontSelected[0] = getSelectedFontInSelectedFontSize(fontSelectionIndex[0], fontSize[0]);
                txtareaDemoDisplayLetters.setFont(fontSelected[0]);
                txtareaDemoDisplayNumbers.setFont(fontSelected[0]);
                txtareaDemoDisplayPunctuationAndGlyphs.setFont(fontSelected[0]);
            }
        });

        cmbxFontSize.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                fontSize[0] = (int) newValue;
                System.out.println(fontSize[0]);
                hasFontSizeBeenSelected[0] = true;

                if (hasFontBeenSelected[0] && hasTargetElementBeenSelected[0]) {
                    btnApply.setDisable(false);
                }

                if (!hasFontBeenSelected[0]) {
                    fontSelected[0] = getSelectedFontInSelectedFontSize(0, fontSize[0]);
                } else {
                    fontSelected[0] = getSelectedFontInSelectedFontSize(fontSelectionIndex[0], fontSize[0]);
                }

                txtareaDemoDisplayLetters.setFont(fontSelected[0]);
                txtareaDemoDisplayNumbers.setFont(fontSelected[0]);
                txtareaDemoDisplayPunctuationAndGlyphs.setFont(fontSelected[0]);
            }
        });

        cmbxTargetElement.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                targetElement[0] = cmbxTargetElement.getSelectionModel().getSelectedIndex();
                System.out.println(targetElement[0]);
                hasTargetElementBeenSelected[0] = true;

                if (hasFontBeenSelected[0] && hasFontSizeBeenSelected[0]) {
                    btnApply.setDisable(false);
                }
            }
        });

        int fontSizeTest = fontSize[0];
        System.out.println("This is outside the handler, but the font size is = " + fontSizeTest);

        btnApply.setOnMouseClicked(e -> {
            if (hasFontBeenSelected[0] && hasFontSizeBeenSelected[0] && hasTargetElementBeenSelected[0]) {
                Color color = colorPickerTargetElement.getValue();
                Font fontCurrent = Font.loadFont(
                        Main.class.getResource(fontFileNames[fontSelectionIndex[0]]).toExternalForm(), fontSize[0]
                );

                if (targetElement[0] == 0) {
                    colorContainerTitleLabel = color;
                    fontContainerTitleLabel = fontCurrent;

                    removeContainerTitleLabels();

                    // Draw Hold Label
                    drawTitleLabel(
                            paneHoldTetramino, colorContainerTitleLabel, fontContainerTitleLabel, lblHoldTitle,
                            START_X_HOLD_TETRAMINO, START_Y_HOLD_TETRAMINO, dimensions_holdTetraminoWidth
                    );

                    // Draw Next Label
                    drawTitleLabel(
                            paneNextTetramino, colorContainerTitleLabel, fontContainerTitleLabel, lblNextTitle,
                            START_X_NEXT_TETRAMINO, START_Y_NEXT_TETRAMINO, dimensions_nextTetraminoWidth
                    );
                } else if (targetElement[0] == 1) {
                    colorInformationTitleLabel = color;
                    fontInformationTitleLabel = fontCurrent;

                    removeGameInformationTitleLabels();

                    // Draw Score Title Label
                    drawGameInformationTitleLabel(
                            paneScoreDisplay, colorInformationTitleLabel, lblScoreValue, lblScoreTitle,
                            fontInformationTitleLabel, START_X_GAME_INFORMATION, START_Y_GAME_INFORMATION
                    );

                    // Draw Level Title Label
                    drawGameInformationTitleLabel(
                            paneLevelDisplay, colorInformationTitleLabel, lblLevelValue,
                            lblLevelTitle, fontInformationTitleLabel,
                            START_X_GAME_INFORMATION,
                            (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight +
                                    dimensions_gameInformationMargin)
                    );

                    // Draw Cleared Lines Title Label
                    drawGameInformationTitleLabel(
                            paneClearedLinesDisplay, colorInformationTitleLabel, lblClearedLinesValue,
                            lblClearedLinesTitle, fontInformationTitleLabel,
                            START_X_GAME_INFORMATION,
                            (
                                    START_Y_GAME_INFORMATION +
                                    (2 * dimensions_gameInformationHeight) +
                                    (2 * dimensions_gameInformationMargin)
                            )
                    );

                    // Draw Time Title Label
                    drawGameInformationTitleLabel(
                            paneTimer, colorInformationTitleLabel, lblTimerValue,
                            lblTimerTitle, fontInformationTitleLabel,
                            START_X_GAME_INFORMATION,
                            (
                                    START_Y_GAME_INFORMATION +
                                            (3 * dimensions_gameInformationHeight) +
                                            (3 * dimensions_gameInformationMargin)
                            )
                    );
                } else if (targetElement[0] == 2) {
                    colorInformationValueLabel = color;
                    fontInformationValueLabel = fontCurrent;

                    removeGameInformationValueLabels();

                    // Draw Score Value Label
                    drawGameInformationValueLabel(
                            paneScoreDisplay, lblScoreValue, colorInformationValueLabel, fontInformationValueLabel,
                            textScoreValue.getText(), START_X_GAME_INFORMATION, START_Y_GAME_INFORMATION,
                            dimensions_gameInformationWidth
                    );

                    // Draw Level Value Label
                    drawGameInformationValueLabel(
                            paneLevelDisplay, lblLevelValue, colorInformationValueLabel,
                            fontInformationValueLabel, textLevelValue.getText(),
                            START_X_GAME_INFORMATION,
                            (START_Y_GAME_INFORMATION + dimensions_gameInformationHeight +
                                    dimensions_gameInformationMargin),
                            dimensions_gameInformationWidth
                    );

                    // Draw Cleared Lines Value Label
                    drawGameInformationValueLabel(
                            paneClearedLinesDisplay, lblClearedLinesValue, colorInformationValueLabel,
                            fontInformationValueLabel, textLinesValue.getText(),
                            START_X_GAME_INFORMATION,
                            (
                                    START_Y_GAME_INFORMATION +
                                    (2 * dimensions_gameInformationHeight) +
                                    (2 * dimensions_gameInformationMargin)
                            ),
                            dimensions_gameInformationWidth
                    );

                    // Draw Time Value Label
                    drawGameInformationValueLabel(
                            paneTimer, lblTimerValue, colorInformationValueLabel,
                            fontInformationValueLabel, textTimerValue.getText(),
                            START_X_GAME_INFORMATION,
                            (
                                    START_Y_GAME_INFORMATION +
                                            (3 * dimensions_gameInformationHeight) +
                                            (3 * dimensions_gameInformationMargin)
                            ),
                            dimensions_gameInformationWidth
                    );
                }
            }
        });

        btnBack.setOnMouseClicked(e -> {
            closeStage(stageFontOptions);
            openOptionsMenu();
        });

        btnBack.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                closeStage(stageFontOptions);
                openOptionsMenu();
            }
        });
    }


    /**
     * Creates and returns the specified Font in the specified Font size
     * @param selectionIndex int parameter - The index of the user selection from the ComboBox cmbxFonts
     * @param fontSize int parameter - The value of the user selection from the ComboBox cmbxFontSize
     * @return Specified Font in the specified Font size
     */
    private Font getSelectedFontInSelectedFontSize(int selectionIndex, int fontSize) {
        return Font.loadFont(Main.class.getResource(fontFileNames[selectionIndex]).toExternalForm(), fontSize);
    }


    /**
     * NEEDS TO BE IMPLEMENTED - SOME IDEAS FOR WHAT CUSTOMIZATIONS TO ADD ARE IN COMMENTS IN METHOD BODY
     */
    private void openCustomizeMenu() {
        // 1. Add whatever music you want for game music
        // 2. Allow user to make up to two custom Tetraminos
        // 3. Allow user to choose what Font they want for the game information display
        // 4. Change the number of rows and columns in the board matrix -
        //          (game board initially is 20 rows and 10 columns)
        // 5. Allow user to choose custom game modes:
        //      A) Number of Tetraminos that have entered the game determines score
        //      B) Disable soft drop and hard drop and the goal is to last as long as possible (or
        //              have it to where the time it would take for the Tetramino to drop normally
        //              would have to be calculated and added to the timer,
        //              that way soft drop and hard drop wouldn't have to be disabled)
        //      C) Race Mode: Given a time limit, try to use as many blocks as possible or
        //              score as many points as possible or clear as many lines as possible
        //      *D) Could try to create an AI to play against the computer
        //      *: THIS WOULD BE A STRETCH AND WOULD REQUIRE FINDING OUT HOW TO MAKE AN AI
        //          TO PLAY THE GAME AND WOULD HAVE TO REDESIGN THE ENTIRE UI TO FIT TWO GAME BOARDS
    }


    /**
     * Resets the current game to how a new game starts --
     * Resets all game values and their displays to zero, erases all Rectangle blocks from every Pane,
     * reinitialize all 2-dimensional int and Rectangle matrices, close pause menu,
     * create and draw a new random current falling Tetramino, reset game speed to initial value and
     * start the game running again at initial speed of 800ms between each time a Tetramino moves down one row
     */
    private void resetGame() {
        // Reset all game values to zero, update their Label displays to reflect the reset,
        // erase all the Rectangle blocks in every Pane and reinitialize all int matrices and Rectangle matrices
        resetScoreAndScoreLabel();
        resetLevelAndLevelLabel();
        resetClearedLinesAndClearedLinesLabel();
        eraseAllBlocks(holdTetraminoType);
        resetIntBlockMatrixes();
        resetRectangleBlockMatrixes();

        // Reset speed to initial value of 800ms between each time a Tetramino moves down one row
        speed = 799;

        // Reset Timer values
        hours = 0;
        minutes = 0;
        seconds = 0;
        mSeconds = 0;

        // Initialize a new first falling Tetramino
        fallingTetraminoType = generateRandomTetraminoType();
        Color color = getBlockColor(fallingTetraminoType);
        tetraminoBlock = new Block(color);
        tetraminoMatrix = tetraminoBlock.getBlockMatrix(fallingTetraminoType, orientation);
        currentRowBoard = startRowBoard;
        currentColBoard = startColBoard;
        fallingTetraminoBlocksCoordinates = new ArrayList<>();
        fallingTetraminoBlocksCoordinates = getFallingTetraminoBlocksCoordinatesAsArrayList(tetraminoMatrix);
        tetraminoHeight = getFallingTetraminoHeight();
        lowestOccupiedRow = boardMatrix.length - 1;

//        // Close the pause menu and draw the new current falling Tetramino to restart a new game at initial speed
//        closePauseMenu();
//        drawFallingTetramino();
    }


    /**
     * Resets score to zero and resets the score value display Label to display "0"
     */
    private void resetScoreAndScoreLabel() {
        paneScoreDisplay.getChildren().remove(lblScoreValue);
        score = 0;
        lblScoreValue.setText(String.valueOf(score));
        paneScoreDisplay.getChildren().add(lblScoreValue);
    }


    /**
     * Resets level to zero and resets the level value display Label to display "00"
     */
    private void resetLevelAndLevelLabel() {
        paneLevelDisplay.getChildren().remove(lblLevelValue);
        level = 0;
        levelString = String.format("%02d", level);
        lblLevelValue.setText(levelString);
        paneLevelDisplay.getChildren().add(lblLevelValue);
    }


    /**
     * Resets numberOfClearedLines to zero and resets the cleared lines value display Label to display "0"
     */
    private void resetClearedLinesAndClearedLinesLabel() {
        paneClearedLinesDisplay.getChildren().remove(lblClearedLinesValue);
        numberOfRowsCleared = 0;

        lblClearedLinesValue.setText(String.valueOf(numberOfRowsCleared));
        paneClearedLinesDisplay.getChildren().add(lblClearedLinesValue);
    }


    /**
     * Resets all the 2-dimensional Rectangle matrixes to be full of null Rectangles
     */
    private void resetRectangleBlockMatrixes() {
        blocks = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        holdBlocks = new Rectangle[4][4];
        nextBlocks = new Rectangle[4][4];
        upcomingTopBlocks = new Rectangle[4][4];
        upcomingMiddleBlocks = new Rectangle[4][4];
        upcomingBottomBlocks = new Rectangle[4][4];
    }


    /**
     * Resets all the 2-dimensional int matrixes to be full of zeroes
     */
    private void resetIntBlockMatrixes() {
        boardMatrix = new int[numOfRowsBoard][numOfColsBoard];

        tetraminoMatrix = new int[4][4];

        holdTetraminoMatrix = new int[4][4];
        nextTetraminoMatrix = new int[4][4];
        upcomingTetraminoTopMatrix = new int[4][4];
        upcomingTetraminoMiddleMatrix = new int[4][4];
        upcomingTetraminoBottomMatrix = new int[4][4];
    }


    /**
     * Draws the current hold Tetramino Rectangle blocks in the Pane paneHoldTetramino and
     * sets the value of isHoldTetraminoBoxEmpty to false
     */
    private void drawHoldTetramino() {
        for (int row = 0; row < holdTetraminoMatrix.length; row ++) {
            for (int col = 0; col < holdTetraminoMatrix[row].length; col++) {
                if (holdTetraminoMatrix[row][col] != 0) {
                    drawBlock(
                            paneHoldTetramino, holdBlocks,
                            (30 + START_X_HOLD_TETRAMINO), (60 + START_Y_HOLD_TETRAMINO),
                            row, col,
                            holdTetraminoType
                    );
                }
            }
        }

        isHoldTetraminoBoxEmpty = false;
    }


    /**
     * Erases all the Rectangle blocks from every Pane
     * @param tetraminoType int parameter - the Tetramino type for the hold Tetramino
     */
    private void eraseAllBlocks(int tetraminoType) {
        if (!isHoldTetraminoBoxEmpty && holdTetraminoType == tetraminoType) {
            eraseHoldTetramino();
        }

        eraseBoard();
        eraseAndRemoveFallingTetramino();
        eraseNextTetramino();
        eraseUpcomingTetraminoTop();
        eraseUpcomingTetraminoMiddle();
        eraseUpcomingTetraminoBottom();
    }


    /**
     * Draws all the Rectangle blocks from the 2-dimensional Rectangle matrixes in their respective Panes
     * @param tetraminoType int parameter - the Tetramino type of the hold Tetramino
     */
    private void drawAllBlocks(int tetraminoType) {
        if (isHoldTetraminoBoxEmpty && holdTetraminoType == tetraminoType) {
            drawHoldTetramino();
        }
        drawBoard();
        drawFallingTetramino();
        drawNextTetramino();
        drawUpcomingTetraminoTop();
        drawUpcomingTetraminoMiddle();
        drawUpcomingTetraminoBottom();
    }


    /**
     * Returns the Color passed in as a Hexadecimal RGB String
     * @param color Color parameter - the Color to convert to Hexadecimal RGB
     * @return String of the Hexadecimal value of the Color passed in
     */
    public String toRGBCode(Color color) {
        return String.format(
                "#%02X%02X%02X",
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255)
        );
    }


    /**
     * Closes whatever Stage is passed to this method
     * @param stageToClose Stage parameter - the Stage that will be closed
     */
    private void closeStage(Stage stageToClose) {
        stageToClose.close();
    }


    /**
     * Update score and its Label (lblScoreValue)
     */
    private void incrementAndDisplayScore() {
        paneScoreDisplay.getChildren().remove(lblScoreValue);
        score += getValueToAddToScore();
        lblScoreValue.setText(formatNumberWithDelimiter.format(score));
        paneScoreDisplay.getChildren().add(lblScoreValue);
    }


    /**
     * NEED TO REFACTOR -
     * (speed VALUES NEED TO COMPARED AGAINST NES NTSC Tetris AT EACH LEVEL TO GET CORRECT SPEEDS) -
     * Increments level and speeds up game play
     */
    private void incrementLevel() {
        timer.stop();

        incrementAndDisplayLevel();
        setGameSpeed();

        runGameAtSpecifiedSpeed(speed);
    }


    /**
     * Sets the game speed that corresponds to the current level
     */
    private void setGameSpeed() {
        switch (level) {
            case 1:     speed = 716;    break;
            case 2:     speed = 632;    break;
            case 3:     speed = 549;    break;
            case 4:     speed = 466;    break;
            case 5:     speed = 383;    break;
            case 6:     speed = 300;    break;
            case 7:     speed = 216;    break;
            case 8:     speed = 133;    break;
            case 9:     speed = 100;    break;
            case 10:    speed = 83;     break;
            case 13:    speed = 67;     break;
            case 16:    speed = 50;     break;
            case 19:    speed = 33;     break;
            case 29:    speed = 17;     break;
            default:    break;
        }
    }


    /**
     * Returns whether it is time to increment the current user level
     * @return boolean value (true - call incrementLevel(), false - not time to call incrementLevel())
     */
    private boolean shouldLevelBeIncremented() {
        return (numberOfRowsCleared % 10 == 0);
    }


    /**
     * Runs the game for one moving Tetramino down one row or summoning a new current falling Tetramino and
     * calling lostGame() if a new current falling Tetramino cannot be placed onto the game board (boardMatrix)
     * at the starting row and column without being placed on top of an already in place Tetramino
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
                instantiateAndPopulateNew2DRectangleMatrixBlocks();
                drawBoard();
                incrementAndDisplayScore();
            }

            generateNewFallingTetraminoFromNextTetramino();
            generateNextTetraminoFromUpcomingTetraminoTop();
            generateUpcomingTetraminoTopFromUpcomingTetraminoMiddle();
            generateUpcomingTetraminoMiddleFromUpcomingTetraminoBottom();
            generateRandomUpcomingTetraminoBottom();

            if (!placeFallingTetraminoIntoBoardMatrix()) {
                lostGame();
            }
        }
        drawFallingTetramino();
    }


    /**
     * Generates the specified Tetramino to be the falling Tetramino and erases the Tetramino from the
     * hold Tetramino box if the hold Tetramino box is not currently empty
     * @param tetraminoType int parameter - int representation of the type of Tetramino to generate
     */
    private void generateFallingTetraminoFromHoldTetramino(int tetraminoType) {
        eraseAndRemoveFallingTetramino();
        eraseHoldTetramino();

        fallingTetraminoType = holdTetraminoType;
        orientation = 0;

        Color color = getBlockColor(fallingTetraminoType);
        tetraminoBlock = new Block(color);

        tetraminoMatrix = tetraminoBlock.getBlockMatrix(fallingTetraminoType, orientation);

        currentRowBoard = startRowBoard;
        currentColBoard = startColBoard;

        fallingTetraminoBlocksCoordinates = new ArrayList<>();
        fallingTetraminoBlocksCoordinates = getFallingTetraminoBlocksCoordinatesAsArrayList(tetraminoMatrix);

        tetraminoHeight = getFallingTetraminoHeight();

        isHoldTetraminoBoxEmpty = true;
    }


    /**
     * Erases the Rectangles that make up the Tetramino in the hold Tetramino box
     */
    private void eraseHoldTetramino() {
        for (int row = 0; row < holdBlocks.length; row++) {
            for (int col = 0; col < holdBlocks[row].length; col++) {
                paneHoldTetramino.getChildren().remove(holdBlocks[row][col]);
            }
        }

        isHoldTetraminoBoxEmpty = true;
    }


    /**
     * Takes the current falling Tetramino, draws in the hold Tetramino box so that it can be
     * summoned on command to be the current falling Tetramino
     */
    private void reserveFallingTetraminoInHoldBox() {
        eraseAndRemoveFallingTetramino();
        holdTetraminoType = fallingTetraminoType;
        orientation = 0;
        Color color = getBlockColor(holdTetraminoType);
        Block holdTetraminoBlock = new Block(color);

        holdTetraminoMatrix = holdTetraminoBlock.getBlockMatrix(holdTetraminoType, orientation);

        drawHoldTetramino();
    }


    /**
     * Generates the next Tetramino from the current falling Tetramino
     * and draws it in paneNextTetramino
     */
    private void generateNextTetraminoFromFallingTetramino() {
        eraseNextTetramino();
        nextTetraminoType = fallingTetraminoType;

        Color color = getBlockColor(nextTetraminoType);
        Block nextTetraminoBlock = new Block(color);

        nextTetraminoMatrix = nextTetraminoBlock.getBlockMatrix(nextTetraminoType, 0);

        drawNextTetramino();
    }


    /**
     * Generates the Tetramino that will be the top upcoming Tetramino from the next Tetramino
     * and draws it in paneUpcomingTetraminos
     */
    private void generateTopUpcomingTetraminoFromNextTetramino() {
        eraseUpcomingTetraminoTop();
        upcomingTetraminoTypeTop = nextTetraminoType;

        Color color = getBlockColor(upcomingTetraminoTypeTop);
        Block upcomingTetraminoTopBlock = new Block(color);

        upcomingTetraminoTopMatrix = upcomingTetraminoTopBlock.getBlockMatrix(upcomingTetraminoTypeTop, 0);

        drawUpcomingTetraminoTop();
    }


    /**
     * Generates the Tetramino that will be the middle upcoming Tetramino from the top upcoming Tetramino
     * and draws it in paneUpcomingTetraminos
     */
    private void generateMiddleUpcomingTetraminoFromTopUpcomingTetramino() {
        eraseUpcomingTetraminoMiddle();
        upcomingTetraminoTypeMiddle = upcomingTetraminoTypeTop;

        Color color = getBlockColor(upcomingTetraminoTypeMiddle);
        Block upcomingTetraminoMiddleBlock = new Block(color);

        upcomingTetraminoMiddleMatrix = upcomingTetraminoMiddleBlock.getBlockMatrix(upcomingTetraminoTypeMiddle, 0);

        drawUpcomingTetraminoMiddle();
    }


    /**
     * Generates the Tetramino that will be the bottom upcoming Tetramino from the middle upcoming Tetramino
     * and draws it in paneUpcomingTetraminos
     */
    private void generateBottomUpcomingTetraminoFromMiddleUpcomingTetramino() {
        eraseUpcomingTetraminoBottom();
        upcomingTetraminoTypeBottom = upcomingTetraminoTypeMiddle;

        Color color = getBlockColor(upcomingTetraminoTypeBottom);
        Block upcomingTetraminoBottomBlock = new Block(color);

        upcomingTetraminoBottomMatrix = upcomingTetraminoBottomBlock.getBlockMatrix(upcomingTetraminoTypeBottom, 0);

        drawUpcomingTetraminoBottom();
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
     * - TEMPORARY METHOD BODY - NEED TO DECIDE WHAT TO DO WHEN THE USER LOSES THE CURRENT GAME
     */
    private void lostGame() {
        timer.stop();
        pauseTimerObj();

        /*
        Alert gameLostAlert = new Alert(Alert.AlertType.INFORMATION, "YOU LOST!", ButtonType.OK);
        gameLostAlert.setTitle("GAME OVER");
        gameLostAlert.setHeaderText(
                "You lost! " +
                "\n\tscore\t     : " + score +
                "\n\tlevel\t\t     : " + levelString +
                "\n\tcleared lines : " + numberOfRowsCleared +
                "\n\ttime\t\t     : " +
                        String.format("%02d : %02d : %02d : %03d", hours, minutes, seconds, mSeconds)
        );
        gameLostAlert.show();

        if (gameLostAlert.getResult() == ButtonType.OK) {
            System.out.println("lostGame(): ENTERED if (gameLostAlert.getResult() == ButtonType.OK) {...}");
            timerObj.cancel();
            Platform.exit();
        } else {
            System.out.println("lostGame(): ENTERED else OF if (gameLostAlert.getResult() == ButtonType.OK) {...}");
        }
         */

        GridPane gridPaneLostGame = new GridPane();

        Label lblLostGameTitle = new Label("YOU LOST!");
        gridPaneLostGame.setConstraints(lblLostGameTitle, 0, 0);

        HBox hBoxResultInformation = new HBox();

        VBox vBoxCategoryTitles = new VBox();
        Label lblScoreTitle = new Label("Score: ");
        Label lblLevelTitle = new Label("Level: ");
        Label lblLinesTitle = new Label("Cleared Lines: ");
        Label lblTimeTitle = new Label("Time: ");
        vBoxCategoryTitles.getChildren().addAll(lblScoreTitle, lblLevelTitle, lblLinesTitle, lblTimeTitle);

        VBox vBoxCategoryResults = new VBox();
        Label lblScoreResult = new Label(formatNumberWithDelimiter.format(score));
        Label lblLevelResult = new Label(String.format("%02d", level));
        Label lblLinesResult = new Label(String.valueOf(numberOfRowsCleared));
        Label lblTimeResult = new Label(String.format("%02d : %02d : %02d : %03d", hours, minutes, seconds, mSeconds));
        vBoxCategoryResults.getChildren().addAll(lblScoreResult, lblLevelResult, lblLinesResult, lblTimeResult);

        hBoxResultInformation.getChildren().addAll(vBoxCategoryTitles, vBoxCategoryResults);
        gridPaneLostGame.setConstraints(hBoxResultInformation, 0, 1);

        HBox hBoxButtons = new HBox();
        Button btnReset = new Button("RESET");
        Region regionMiddle = new Region();
        HBox.setHgrow(regionMiddle, Priority.ALWAYS);
        Button btnExit = new Button("EXIT");
        hBoxButtons.getChildren().addAll(btnReset, regionMiddle, btnExit);
        gridPaneLostGame.setConstraints(hBoxButtons, 0, 2);

        gridPaneLostGame.getChildren().addAll(lblLostGameTitle, hBoxResultInformation, hBoxButtons);

        Scene sceneLostGame = new Scene(gridPaneLostGame, (BLOCK_SIZE * 15), (BLOCK_SIZE * 8));

        Stage stageLostGame = new Stage();
        stageLostGame.initStyle(StageStyle.UTILITY);
        stageLostGame.setScene(sceneLostGame);
        stageLostGame.setTitle("GAME OVER");
        stageLostGame.show();

        stageLostGame.setOnCloseRequest(e -> {
            e.consume();
            closeStage(stageLostGame);
            Platform.exit();
        });

        btnReset.setOnMouseClicked(e -> {
            closeStage(stageLostGame);
            resetGame();
            drawFallingTetramino();
            startTimer();
            runGameAtSpecifiedSpeed(speed);
        });

        btnReset.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                closeStage(stageLostGame);
                resetGame();
                drawFallingTetramino();
                startTimer();
                runGameAtSpecifiedSpeed(speed);
            }
        });

        btnExit.setOnMouseClicked(e -> {
            Platform.exit();
        });

        btnExit.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER || e.getCode() == controlKeyPause) {
                Platform.exit();
            }
        });
    }


    /**
     * Determine whether the current falling tetramino can move left one column
     * @return boolean value (true - can move left, false - can not move left)
     */
    private boolean canFallingTetraminoMoveLeftOneColumn() {
        eraseAndRemoveFallingTetramino();

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
        eraseAndRemoveFallingTetramino();

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

        eraseAndRemoveFallingTetramino();

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
    private boolean canFallingTetraminoRotateCounterclockwise() {
        if (fallingTetraminoType == 1) {
            return false;
        }

        int desiredOrientation = changeOrientation(false);
        int[][] temporaryTetraminoMatrix = tetraminoBlock.getBlockMatrix(fallingTetraminoType, desiredOrientation);
        List<int[]> temporaryFallingTetraminoBlocksCoordinates = new ArrayList<>();

        eraseAndRemoveFallingTetramino();

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
    private void rotateFallingTetraminoCounterclockwise() {
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

        // fallingTetraminoBlocksCoordinates = new ArrayList<>();
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
        eraseAndRemoveFallingTetramino();

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
    private void eraseAndRemoveFallingTetramino() {
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
            drawBlock(
                    paneGameBoard, blocks,
                    START_X_BOARD, START_Y_BOARD,
                    fallingTetraminoBlocksCoordinates.get(i)[0], fallingTetraminoBlocksCoordinates.get(i)[1],
                    fallingTetraminoType
            );
        }
    }


    /**
     * Draws a single Rectangle block in specified Pane
     * @param pane Pane parameter - the Pane to draw the Rectangle block in
     * @param array Rectangle[][] parameter - 2-dimensional Rectangle array to store the Rectangles in
     * @param startX int parameter - x-coordinate base to be summed with xCoord parameter to start drawing
     * @param startY int parameter - y-coordinate base to be summed with yCoord parameter to start drawing
     * @param xCoord int parameter - value to be summed with startX parameter to start drawing
     * @param yCoord int parameter - value to be summed with startY parameter to start drawing
     * @param blockType int parameter - int value that represents the type of Tetramino being drawn (determines Color)
     */
    private void drawBlock(
            Pane pane, Rectangle[][] array, int startX, int startY, int xCoord, int yCoord, int blockType
    )
    {
        Rectangle currentBlock = new Rectangle(startX + (BLOCK_SIZE * yCoord),
                startY + (BLOCK_SIZE * xCoord), BLOCK_SIZE, BLOCK_SIZE);

        currentBlock.setFill(getBlockColor(blockType));
        currentBlock.setStroke(colorBorder);
        array[xCoord][yCoord] = currentBlock;

        pane.getChildren().add(currentBlock);
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
        }

        // For when the game is lost and user chooses reset, this removes the Tetramino blocks that were already
        // at the coordinates the new Tetramino was trying to go. That way, overlapping Rectangle blocks
        // are not left on the game board when the user chooses to reset the game after a loss.
        if (!canBePlaced) {
            for (int i = 0; i < fallingTetraminoBlocksCoordinates.size(); i++) {
                paneGameBoard.getChildren().remove(blocks[fallingTetraminoBlocksCoordinates.get(i)[0]][fallingTetraminoBlocksCoordinates.get(i)[1]]);
            }
        }

        for (int i = 0; i < fallingTetraminoBlocksCoordinates.size(); i++) {
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
            }
        }
    }


    /**
     * Instantiates a new Rectangle[][] blocks and fills blocks with Rectangles
     * corresponding to any row and column index pair in int[][] boardMatrix that != 0
     */
    private void instantiateAndPopulateNew2DRectangleMatrixBlocks() {
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
        instantiateAndPopulateNew2DRectangleMatrixBlocks();
        for (int row = lowestOccupiedRow; row < blocks.length; row++) {
            for (int col = 0; col < blocks[row].length; col++) {
                if (blocks[row][col] != null) {
                    paneGameBoard.getChildren().add(blocks[row][col]);
                }
            }
        }
    }


    /**
     * Colors the entire game board the Color specified in the class variable colorEmptyBlock
     * *NOTE: Need to call drawBoard() and drawFallingTetramino() after this method
     */
    private void drawGameBoardBackground() {
        for (int row = 0; row < boardMatrix.length; row++) {
            for (int col = 0; col < boardMatrix[row].length; col++) {
                Rectangle currentBlock = new Rectangle(START_X_BOARD + (BLOCK_SIZE * col),
                        START_Y_BOARD + (BLOCK_SIZE * row), BLOCK_SIZE, BLOCK_SIZE);

                currentBlock.setFill(colorEmptyBlock);

                paneGameBoard.getChildren().add(currentBlock);
            }
        }
    }


    /**
     * Colors the entire game board the Color specified in the class variable colorEmptyBlock and
     * draws the borders (.setStroke()) the color specified in the class variable colorBorder
     * *NOTE: Need to call drawBoard() and drawFallingTetramino() after this method
     */
    private void drawGameBoardBackgroundWithGrid() {
        for (int row = 0; row < boardMatrix.length; row++) {
            for (int col = 0; col < boardMatrix[row].length; col++) {
                Rectangle currentBlock = new Rectangle(START_X_BOARD + (BLOCK_SIZE * col),
                        START_Y_BOARD + (BLOCK_SIZE * row), BLOCK_SIZE, BLOCK_SIZE);

                currentBlock.setFill(colorEmptyBlock);
                currentBlock.setStroke(colorBorder);

                paneGameBoard.getChildren().add(currentBlock);
            }
        }
    }
}