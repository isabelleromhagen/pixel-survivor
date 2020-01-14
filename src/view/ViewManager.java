package view;

import controller.SpaceInvaderButtonListener;
import controller.SpaceInvaderController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Constants;
import model.InGameModel;

import java.util.Scanner;

/**
 * This class handles everything that is visible in game play.
 * Implements IViewState.
 *
 * @author Isabelle Romhagen, Ludvig Lundin, Mattias Frigren, Jasmine Söderberg, Khazar Mehraban
 * @version 1.2
 */
public class ViewManager {

    private static ViewManager view;

    private Scene mainScene;
    private Stage mainStage;
    private IViewState gameState;
    private SubScene exitScene;
    private boolean changeGameState = true;

    /////////************** Getter and setters ***********************

    public boolean isChangeGameState() {
        return changeGameState;
    }

    public void setChangeGameState(boolean changeGameState) {
        this.changeGameState = changeGameState;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public IViewState getGameState () {
        return gameState;
    }


    public static ViewManager getViewManager(Stage stage) {
        if (view == null) {
            view = new ViewManager(stage);
        }
        return view;
    }

    public static ViewManager getViewManager() {
        return view;
    }

    /////////**************End of Getter and setters ***********************

    /**
     * Constructor initializes current scene depending on game state.
     * Initializes exit sub scene if user tries to close the window.
     *
     * @param stage window
     */
    private ViewManager(Stage stage) {
        gameState = SpaceInvaderMenuView.getSpaceInvaderMenuView();
        mainScene = ((SpaceInvaderMenuView) gameState).getMenuScene();
        mainStage = stage;
        mainStage.setTitle("SpaceInvader");
        mainStage.setScene(mainScene);
        mainStage.setResizable(false);
        mainStage.sizeToScene();
        mainStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e->{
            e.consume();
            initializeExitSubScene();
        });

        stage.show();


    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
        mainStage.setScene(mainScene);
    }

    /**
     * Sets scene depending on if game is running or not.
     *
     * @param isPlaying if game is running or not
     */
    public void changeToInGameScene(boolean isPlaying) {

        if (isPlaying) {
            gameState = SpaceInvaderInGameView.getGameView();
            mainScene = ((SpaceInvaderInGameView) gameState).getGameScene();


        } else {
            gameState = SpaceInvaderMenuView.getSpaceInvaderMenuView();
            mainScene = ((SpaceInvaderMenuView) gameState).getMenuScene();

        }
        mainStage.setScene(mainScene);


    }

    /**
     * Creates a sub scene with options to confirm that they want to quit or go back.
     * Adds attributes to texts and buttons.
     * Closes sub scene on no, closes program on yes.
     */
    public void initializeExitSubScene(){
        double subSceneWidth = Constants.SCREENWIDTH * 0.45;
        double subSceneHeight = Constants.SCREENHEIGHT * 0.45;

        AnchorPane exitAnchor = new AnchorPane();
        exitScene = new SubScene(exitAnchor, subSceneWidth, subSceneHeight);

        BackgroundImage image = new BackgroundImage(new Image(Constants.gameOverSubSceneBackground, Constants.SCREENWIDTH * 0.45, Constants.SCREENHEIGHT * 0.45, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

        exitAnchor.setBackground(new Background(image));

        exitScene.setLayoutX(Constants.SCREENWIDTH / 3);
        exitScene.setLayoutY(Constants.SCREENHEIGHT / 3);

        Text exitText = new Text("Are you sure you want to quit?");
        exitText.setX(subSceneWidth/8);
        exitText.setY(subSceneHeight/3);
        exitText.setFont(Font.font("Verdana", 20));
        exitText.setFill(Color.color(0.75, 0.9, 0.9));
        exitAnchor.getChildren().add(exitText);

        Button yesButton = new Button("yes");
        yesButton.setLayoutX(subSceneWidth/5);
        yesButton.setLayoutY(subSceneHeight/2);
        yesButton.setFont(Font.font("Verdana", 20));
        yesButton.setBackground(SpaceInvaderMenuView.getSpaceInvaderMenuView().buttonOnReleasedBackground);
        yesButton.setTextFill(Color.color(0.75, 0.9, 0.9));
        yesButton.setPrefWidth(Constants.menuButtonWidth*0.7);
        exitAnchor.getChildren().add(yesButton);

        Button noButton = new Button("no");
        noButton.setLayoutX(subSceneWidth/2);
        noButton.setLayoutY(subSceneHeight/2);
        noButton.setFont(Font.font("Verdana", 20));
        noButton.setBackground(SpaceInvaderMenuView.getSpaceInvaderMenuView().buttonOnReleasedBackground);
        noButton.setTextFill(Color.color(0.75, 0.9, 0.9));
        noButton.setPrefWidth(Constants.menuButtonWidth*0.7);
        exitAnchor.getChildren().add(noButton);

        AnchorPane currentPane = (AnchorPane) mainScene.getRoot();
        currentPane.getChildren().add(exitScene);

        yesButton.addEventFilter(MouseEvent.MOUSE_CLICKED, SpaceInvaderButtonListener.getButtonListener().reallyExit);

        noButton.setOnMousePressed(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                currentPane.getChildren().remove(exitScene);

            }
        });


    }

}
