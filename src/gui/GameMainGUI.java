package gui;

import actors.Player;
import game.Game;
import game.GameMap;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;


import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


/**
 * ----------------------------------------------------------------------------  <br>
 * gui.gui.java created byTheo Dimopoulos on 21-09-2016.                 <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                    <br>
 * ----------------------------------------------------------------------------  <br>
 *  GameMainGUI.java implements the game application!
 * @author Theo Dimopoulos
 * @version 21-09-2016
 *
 * Images taken from http://www.freeimages.com Copyrights belong to owner.
 */


public class GameMainGUI extends Application {

    private Game game;
    private Player player;
    private GameMap map;
    private int mapSize;
    private BorderPane gameRoot;

    /**
     * Application main method.
     * Impossible to explain...
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Override Javafx application abstract class method.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        buildMainMenu(primaryStage);
    }

    /**
     * Build main menu screen.
     *
     * @param primaryStage Screen primary stage
     */
    public void buildMainMenu(Stage primaryStage) {

        if (gameRoot != null) {
            gameRoot.getScene().getWindow().setHeight(350);
            gameRoot.getScene().getWindow().setWidth(300);
        }

        BorderPane borderPane = new BorderPane();
        borderPane.setId("menuPane");
        Label textNewGame = new Label("New Game");
        textNewGame.setPrefWidth(300);
        textNewGame.setAlignment(Pos.CENTER);
        textNewGame.setId("newGame");
        borderPane.topProperty().set(textNewGame);

        GridPane grid = new GridPane();
        grid.setVgap(10);

        Label playerNameLabel = new Label("Player name:");
        playerNameLabel.setId("menuLabels");
        grid.add(playerNameLabel, 0, 1);

        TextField playerNameField = new TextField();
        playerNameField.setPrefWidth(200);
        grid.add(playerNameField, 1, 1);

        Label mapSizeLabel = new Label("Map size:");
        mapSizeLabel.setId("menuLabels");
        grid.add(mapSizeLabel, 0, 2);

        ComboBox<String> mapCombo = new ComboBox<>();
        mapCombo.setPrefWidth(200);
        mapCombo.setItems(FXCollections.observableArrayList("3 x 3", "4 x 4", "5 x 5"));
        mapCombo.setValue("Choose map size");
        grid.add(mapCombo, 1, 2);


        Button buttonNewGame = new Button();
        buttonNewGame.setText("START");
        buttonNewGame.setOnAction(event -> {
            String playerName = playerNameField.getText();
            int mapSizeChoice = mapCombo.getSelectionModel().getSelectedIndex();

            if (checkFields(playerName, mapSizeChoice)) {
                GameGUIController gameGUIController = new GameGUIController(primaryStage, playerName, mapSizeChoice);
                gameGUIController.startGame();
                //  initGameScreen(primaryStage, playerName, mapSizeChoice);
            } else {
                showFieldsAreEmptyAlert();
            }
        });

        Button buttonQuit = new Button();
        buttonQuit.setOnAction(event -> System.exit(0));
        buttonQuit.setText("QUIT");

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(buttonNewGame, buttonQuit);


        grid.add(hBox, 1, 5);
        borderPane.setCenter(grid);

        Scene sceneGameMainMenu = new Scene(borderPane, 300, 350);
        sceneGameMainMenu.getStylesheets().addAll(getClass().getResource("/gui/css/game.css").toExternalForm());
        primaryStage.setScene(sceneGameMainMenu);
        primaryStage.setTitle("TD41 RPG");
        primaryStage.show();
    }

    /**
     * Checks if user filled the required fields.
     *
     * @param playerName    Players name field text.
     * @param selectedIndex Map size choice.
     * @return Returns false if fields are not fulfilled.
     */
    private boolean checkFields(String playerName, int selectedIndex) {
        return playerName.length() > 0 && selectedIndex > -1;
    }

    /**
     * Display an alert to inform user.
     */
    private void showFieldsAreEmptyAlert() {
        Alert emptyFieldsAlert = new Alert(Alert.AlertType.WARNING);
        emptyFieldsAlert.setTitle("Empty Fields");
        emptyFieldsAlert.setHeaderText("There are empty fields");
        emptyFieldsAlert.setContentText("Type a player name \nSelect map size");
        emptyFieldsAlert.showAndWait();
    }

}



