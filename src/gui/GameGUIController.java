package gui;

import actors.Actor;
import actors.Enemy;
import actors.Player;
import game.Game;
import game.GameMap;
import game.Room;
import inventory.Inventory;
import inventory.Item;
import inventory.Weapon;
import inventory.Armor;

import inventory.Poison;
import inventory.HealthPotion;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.text.Text;

import javafx.stage.Stage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * ----------------------------------------------------------------------------  <br>
 * GameGUIController.java created byTheo Dimopoulos on 12-10-2016.                                <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                             <br>
 * ----------------------------------------------------------------------------  <br>
 * GameGUIController controls the Game play of the application.
 *
 * @author Theo Dimopoulos
 * @version 12-10-2016
 */
public class GameGUIController {

    private static final int LIST_WIDTH_IN_PIXELS = 130;
    private static final int LIST_HEIGHT_IN_PIXELS = 180;
    private static final int BUTTON_HEIGHT_IN_PIXELS = 50;
    private static final int BOARD_ROOM_HEIGHT_IN_PIXELS = 100;
    private static final int ACTOR_IMAGE_VIEW_HEIGHT_IN_PIXELS = 50;
    private static final int BUTTON_INVENTORY_HANDLING_HEIGHT_IN_PIXELS = 60;
    private static final int BUTTON_INVENTORY_HANDLING_WIDTH_IN_PIXELS = 80;
    private static final int LABEL_PLAYER_STAT_WIDTH_IN_PIXELS = 200;
    private static final int LABEL_PLAYER_STAT_HEIGHT_IN_PIXELS = 50;
    private static final int LIST_FIXED_CELL_SIZE = 30;

    private Stage primaryStage;
    private String playerName;
    private int mapSize;

    private Game game;
    private Player player;
    private GameMap map;
    private int numberOfEnemies;

    private GridPane mapPane, playerRoomPane;
    private Label gamePlayLabel;
    private int[] playerPosition = new int[2]; //Position in map, array[0] = x, array[1] = y
    private Label enemyStatsLabel;
    private Map enemies;
    private ImageView playerImage;
    private BorderPane gameRoot;


    /**
     * Construct a GameGUIController.
     *
     * @param primaryStage  Scene primary stage
     * @param playerName    Players name
     * @param mapSizeChoice Map size
     */
    public GameGUIController(Stage primaryStage, String playerName, int mapSizeChoice) {
        this.primaryStage = primaryStage;
        this.playerName = playerName;
        this.mapSize = mapSizeChoice + 3;
    }

    /**
     * Start the application.
     */
    public void startGame() {
        initializeGameScreen();
    }

    /**
     * Initialize the application Screen.
     */
    private void initializeGameScreen() {
        NewGame newGame;

        newGame = new NewGame(playerName, mapSize);

        game = newGame.getGame();
        player = newGame.getPlayer();
        map = newGame.getMap();
        numberOfEnemies = newGame.getNumberOfGameEnemies();
        gameRoot = new BorderPane();
        gameRoot.setId("borderPane");


        setPlayerPosition(0, 0);

        buildGameScreenTop();
        buildGameScreenCenter();
        buildGameScreenBottom();
        buildGameScreenLeft();
        buildGameScreenRight();

        updateScreen();

        Scene gameScreenScene = new Scene(gameRoot);
        gameScreenScene.getStylesheets().addAll(getClass().getResource("/gui/css/game.css").toExternalForm());
        primaryStage.setScene(gameScreenScene);
        primaryStage.setTitle("TD41 RPG");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * Build Top of screen.
     */
    private void buildGameScreenTop() {

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefHeight(40);

        gamePlayLabel = new Label("GAME STARTED!");
        gamePlayLabel.setId("gamePlayLabel");

        hBox.getChildren().addAll(gamePlayLabel);

        gameRoot.setTop(hBox);
    }

    /**
     * Build Left side.
     */
    private void buildGameScreenLeft() {
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10, 20, 10, 20));

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);

        Label enemyListViewLabel = new Label("ROOM ENEMIES");
        enemyListViewLabel.setId("gameLabelTitle");
        ListView enemyListView = new ListView<>();
        enemyListView.setPrefSize(LIST_HEIGHT_IN_PIXELS, LIST_WIDTH_IN_PIXELS);

        enemyStatsLabel = new Label();
        enemyStatsLabel.setId("gameLabel");
        enemyStatsLabel.setPrefWidth(LIST_WIDTH_IN_PIXELS);
        hBox.getChildren().addAll(enemyListView, enemyStatsLabel);

        Button buttonAttack = new Button("ATTACK");
        buttonAttack.setPrefSize(LIST_WIDTH_IN_PIXELS, BUTTON_HEIGHT_IN_PIXELS);
        buttonAttack.setOnAction(event -> performAttackToEnemy());

        Room playerRoom = map.getRoomContaining(player);

        Collection<Actor> roomEnemies = playerRoom.getEnemyOccupants();
        roomEnemies.stream().filter(actor -> actor instanceof Enemy).forEach(actor ->
                enemyListView.getItems().add("Enemy (Life : " + actor.getHealth() + ")"));

        if (enemyListView.getSelectionModel().isEmpty()) {
            buttonAttack.setDisable(true);
        }

        GridPane movementButtonsPane = initNavigationButtons();

        vBox.getChildren().addAll(enemyListViewLabel, hBox, buttonAttack, new Text(""), movementButtonsPane);
        gameRoot.setLeft(vBox);
    }

    /**
     * Build Center.
     */
    private void buildGameScreenCenter() {


        GridPane boardGridPane = new GridPane();
        boardGridPane.alignmentProperty().set(Pos.CENTER);
        boardGridPane.setId("boardGridPane");

        RowConstraints rowConstraint = new RowConstraints(BOARD_ROOM_HEIGHT_IN_PIXELS, BOARD_ROOM_HEIGHT_IN_PIXELS, BOARD_ROOM_HEIGHT_IN_PIXELS);
        ColumnConstraints columnConstraint = new ColumnConstraints(BOARD_ROOM_HEIGHT_IN_PIXELS, BOARD_ROOM_HEIGHT_IN_PIXELS, BOARD_ROOM_HEIGHT_IN_PIXELS);

        if (mapSize == 3) {
            boardGridPane.getRowConstraints().addAll(rowConstraint, rowConstraint, rowConstraint);
            boardGridPane.getColumnConstraints().addAll(columnConstraint, columnConstraint, columnConstraint);
        } else if (mapSize == 4) {
            boardGridPane.getRowConstraints().addAll(rowConstraint, rowConstraint, rowConstraint, rowConstraint);
            boardGridPane.getColumnConstraints().addAll(columnConstraint, columnConstraint, columnConstraint, columnConstraint);
        } else {
            boardGridPane.getRowConstraints().addAll(rowConstraint, rowConstraint, rowConstraint, rowConstraint, rowConstraint);
            boardGridPane.getColumnConstraints().addAll(columnConstraint, columnConstraint, columnConstraint, columnConstraint, columnConstraint);
        }

        playerRoomPane = new GridPane();


        playerImage = new ImageView();
        playerImage.setFitHeight(ACTOR_IMAGE_VIEW_HEIGHT_IN_PIXELS);
        playerImage.setFitWidth(ACTOR_IMAGE_VIEW_HEIGHT_IN_PIXELS);
        playerImage.setImage(new Image("/gui/images/player.png"));

        playerRoomPane.add(playerImage, 0, 0);

        boardGridPane.add(playerRoomPane, 0, 0);

        mapPane = boardGridPane;
        gameRoot.setCenter(boardGridPane);
    }

    /**
     * Build Right side of Screen.
     */
    private void buildGameScreenRight() {
        VBox vBox = new VBox(5);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10, 20, 10, 20));
        Label roomListViewLabel = new Label("ROOM ITEMS");
        roomListViewLabel.setId("gameLabelTitle");
        ListView<String> roomItemsListView = new ListView<>();
        roomItemsListView.setFixedCellSize(LIST_FIXED_CELL_SIZE);
        roomItemsListView.setPrefSize(LIST_WIDTH_IN_PIXELS, LIST_HEIGHT_IN_PIXELS);

        GridPane inventoryButtonsGridPane = new GridPane();

        Button buttonPickItem = new Button("Pick Up");
        buttonPickItem.setPrefSize(BUTTON_INVENTORY_HANDLING_WIDTH_IN_PIXELS,
                BUTTON_INVENTORY_HANDLING_HEIGHT_IN_PIXELS);
        buttonPickItem.setOnAction(event -> performPickUpRoomItem(buttonPickItem));

        Button buttonDropItem = new Button("Drop");
        buttonDropItem.setPrefSize(BUTTON_INVENTORY_HANDLING_WIDTH_IN_PIXELS,
                BUTTON_INVENTORY_HANDLING_HEIGHT_IN_PIXELS);
        buttonDropItem.setOnAction(event -> performPlayerDropItem());

        Button buttonUseConsumable = new Button("use");
        buttonUseConsumable.setPrefSize(BUTTON_INVENTORY_HANDLING_WIDTH_IN_PIXELS,
                BUTTON_INVENTORY_HANDLING_HEIGHT_IN_PIXELS);
        buttonUseConsumable.setOnAction(event -> performUseConsumable());

        Button buttonEquip = new Button("Equip");
        buttonEquip.setPrefSize(BUTTON_INVENTORY_HANDLING_WIDTH_IN_PIXELS,
                BUTTON_INVENTORY_HANDLING_HEIGHT_IN_PIXELS);
        Button buttonUnequip = new Button("UnEquip");
        buttonUnequip.setPrefSize(BUTTON_INVENTORY_HANDLING_WIDTH_IN_PIXELS,
                BUTTON_INVENTORY_HANDLING_HEIGHT_IN_PIXELS);

        buttonEquip.setOnAction(event -> performEquipOn());
        buttonUnequip.setOnAction(event -> performUnEquip(buttonUnequip));

        inventoryButtonsGridPane.add(buttonDropItem, 0, 0);
        inventoryButtonsGridPane.add(buttonUseConsumable, 1, 0);
        inventoryButtonsGridPane.add(buttonEquip, 0, 1);
        inventoryButtonsGridPane.add(buttonUnequip, 1, 1);


        Label playerListViewLabel = new Label("INVENTORY");
        playerListViewLabel.setId("gameLabelTitle");
        ListView<String> playerItemsListView = new ListView<>();
        playerItemsListView.setFixedCellSize(LIST_FIXED_CELL_SIZE);
        playerItemsListView.setPrefSize(LIST_HEIGHT_IN_PIXELS, LIST_WIDTH_IN_PIXELS);

        Room playerRoom = map.getRoomContaining(player);

        Inventory roomInventory = playerRoom.getInventory();
        Inventory playerInventory = player.getInventory();

        int roomInventorySize = roomInventory.getSize();
        int playerInventorySize = playerInventory.getSize();

        for (int i = 0; i < roomInventorySize; i++) {
            roomItemsListView.getItems().add(roomInventory.get(i).getName());
        }
        for (int i = 0; i < playerInventorySize; i++) {
            playerItemsListView.getItems().add(playerInventory.get(i).getName());
        }

        vBox.getChildren().addAll(roomListViewLabel, roomItemsListView, buttonPickItem,
                playerListViewLabel, playerItemsListView, inventoryButtonsGridPane);

        gameRoot.setRight(vBox);
    }

    /**
     * Build Bottom of screen.
     */
    private void buildGameScreenBottom() {

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10, 20, 10, 20));
        GridPane playerStatsPane = new GridPane();      //Set gridPane
        playerStatsPane.setAlignment(Pos.TOP_CENTER);   //Set gridPane alignment


        RowConstraints rowConstraint = new RowConstraints(LABEL_PLAYER_STAT_HEIGHT_IN_PIXELS,
                LABEL_PLAYER_STAT_HEIGHT_IN_PIXELS, LABEL_PLAYER_STAT_HEIGHT_IN_PIXELS);        //GridPane Constraints
        ColumnConstraints columnConstraints = new ColumnConstraints(LABEL_PLAYER_STAT_WIDTH_IN_PIXELS,
                LABEL_PLAYER_STAT_WIDTH_IN_PIXELS, LABEL_PLAYER_STAT_WIDTH_IN_PIXELS);

        playerStatsPane.getRowConstraints().addAll(rowConstraint);
        playerStatsPane.getColumnConstraints().addAll(columnConstraints);

        Label playerStats = new Label("PLAYER " + player.getName().toUpperCase());
        playerStats.setPrefWidth(300);
        playerStats.setId("gameLabelTitle");

        Label playerHealthLabel = new Label();
        playerHealthLabel.setId("gameLabel");
        Label playerAttackLabel = new Label();
        playerAttackLabel.setId("gameLabel");
        Label playerDefenseLabel = new Label();
        playerDefenseLabel.setId("gameLabel");
        Label playerLevelLabel = new Label();
        playerLevelLabel.setId("gameLabel");

        playerStatsPane.add(playerHealthLabel, 0, 0);
        playerStatsPane.add(playerLevelLabel, 1, 0);
        playerStatsPane.add(playerAttackLabel, 0, 1);
        playerStatsPane.add(playerDefenseLabel, 1, 1);

        vBox.getChildren().addAll(playerStats, playerStatsPane);
        gameRoot.setBottom(vBox);
    }

    private GridPane initNavigationButtons() {
        GridPane tempGridPane = new GridPane();
        tempGridPane.setAlignment(Pos.CENTER);

        Button buttonMoveUp = new Button("Go Up");
        Button buttonMoveLeft = new Button("Go Left");
        Button buttonMoveRight = new Button("Go Right");
        Button buttonMoveDown = new Button("Go Down");

        buttonMoveUp.setPrefSize(BUTTON_INVENTORY_HANDLING_WIDTH_IN_PIXELS, BUTTON_HEIGHT_IN_PIXELS);
        buttonMoveLeft.setPrefSize(BUTTON_INVENTORY_HANDLING_WIDTH_IN_PIXELS, BUTTON_HEIGHT_IN_PIXELS);
        buttonMoveRight.setPrefSize(BUTTON_INVENTORY_HANDLING_WIDTH_IN_PIXELS, BUTTON_HEIGHT_IN_PIXELS);
        buttonMoveDown.setPrefSize(BUTTON_INVENTORY_HANDLING_WIDTH_IN_PIXELS, BUTTON_HEIGHT_IN_PIXELS);

        buttonMoveUp.setOnAction(event -> handlePlayerMovement("UP"));
        buttonMoveLeft.setOnAction(event -> handlePlayerMovement("LEFT"));
        buttonMoveRight.setOnAction(event -> handlePlayerMovement("RIGHT"));
        buttonMoveDown.setOnAction(event -> handlePlayerMovement("DOWN"));

        tempGridPane.add(buttonMoveUp, 1, 2);
        tempGridPane.add(buttonMoveLeft, 0, 4);
        tempGridPane.add(buttonMoveRight, 2, 4);
        tempGridPane.add(buttonMoveDown, 1, 6);

        return tempGridPane;
    }


    /**
     * Perform an Action.
     */
    private void performAttackToEnemy() {

        VBox vBox = (VBox) gameRoot.getLeft();
        Button buttonAttack = (Button) vBox.getChildren().get(2);
        HBox hBox = (HBox) vBox.getChildren().get(1);
        ListView<String> enemyListView = (ListView) hBox.getChildren().get(0);

        try {
            String enemyName = enemyListView.getSelectionModel().getSelectedItem();
            Enemy enemy = (Enemy) enemies.get(enemyName);
            int enemyLife = enemy.getHealth();

            game.fight(player, enemy);

            if (enemyLife == enemy.getHealth()) {                            //NO DAMAGE TO ENEMY
                gamePlayLabel.setText("YOU ATTACKED " + enemyName + ",ENEMY DEFENDED!");
                performEnemyHitBack(enemy);
            } else {
                if (enemy.isAlive()) {                                      //ENEMY WAS DAMAGED
                    gamePlayLabel.setText("YOU ATTACKED " + enemyName + "! ");
                    performEnemyHitBack(enemy);
                } else {
                    gamePlayLabel.setText("YOU KILLED "
                            + enemyName + ", NOW YOUR LEVEL IS "
                            + player.getLevel() + "!");  //ENEMY WAS KILLED
                }
            }
            updateScreen();
        } catch (NullPointerException e) {
            buttonAttack.setDisable(true);
        }
    }

    /**
     * Perform an Action.
     */
    private void performEnemyHitBack(Enemy enemy) {
        if (enemy != null) {
            game.fight(enemy, player);
            if (player.isAlive()) {
                gamePlayLabel.setText(gamePlayLabel.getText() + ", ENEMY HITS BACK!");
            } else {
                showDefeatedAlert();
            }
        }
    }

    /**
     * Perform an Action.
     */
    private void performPickUpRoomItem(Button buttonPickItem) {
        VBox vBox = (VBox) gameRoot.getRight();
        ListView roomItemsListView = (ListView) vBox.getChildren().get(1);
        ListView playerItemsListView = (ListView) vBox.getChildren().get(4);
        Room room = map.getRoomContaining(player);
        Inventory roomInventory = room.getInventory();

        try {
            int choice = roomItemsListView.getSelectionModel().getSelectedIndex();
            Item itemForPickUp = roomInventory.get(choice);

            if (game.pickup(player, itemForPickUp)) {
                playerItemsListView.getItems().add(itemForPickUp.getName());
                gamePlayLabel.setText("YOU PICKED UP A " + itemForPickUp.getName().toUpperCase());
                updateScreen();
            } else if (choice < 0) {
                gamePlayLabel.setText("SELECT AN ITEM TO PICK UP");
            } else {
                gamePlayLabel.setText("CANNOT PICK UP ITEM, INVENTORY IS FULL");
            }

        } catch (NullPointerException e) {
            buttonPickItem.setDisable(true);
        }
    }

    /**
     * Perform an Action.
     */
    private void performPlayerDropItem() {

        VBox vBox = (VBox) gameRoot.getRight();
        ListView roomItemsListView = (ListView) vBox.getChildren().get(1);
        ListView playerItemsListView = (ListView) vBox.getChildren().get(4);
        Room room = map.getRoomContaining(player);
        Inventory playerInventory = player.getInventory();

        try {
            int choice = playerItemsListView.getSelectionModel().getSelectedIndex();
            Item droppedItem = playerInventory.get(choice);

            if (game.drop(player, droppedItem)) {
                roomItemsListView.getItems().add(droppedItem.getName());
                gamePlayLabel.setText("YOU DROPPED " + droppedItem.getName().toUpperCase());
                updateScreen();
            } else if (choice < 0) {
                gamePlayLabel.setText("SELECT AN ITEM TO DROP");
            } else {
                gamePlayLabel.setText("CANNOT PICK UP ITEM, INVENTORY IS FULL");
            }

        } catch (NullPointerException e) {

        }

    }

    /**
     * Perform an Action.
     */
    private void performUseConsumable() {
        VBox vBox = (VBox) gameRoot.getRight();
        ListView playerItemsListView = (ListView) vBox.getChildren().get(4);

        VBox vBoxLeft = (VBox) gameRoot.getLeft();
        HBox hBox = (HBox) vBoxLeft.getChildren().get(1);
        ListView enemyListView = (ListView) hBox.getChildren().get(0);

        try {
            String selectedItemName = (String) playerItemsListView.getSelectionModel().getSelectedItem();
            int choiceItem = playerItemsListView.getSelectionModel().getSelectedIndex();
            int choiceEnemy = enemyListView.getSelectionModel().getSelectedIndex();

            String enemyName = (String) enemyListView.getSelectionModel().getSelectedItem();
            Enemy enemy = (Enemy) enemies.get(enemyName);

            Inventory playerInventory = player.getInventory();
            Item selectedItem = playerInventory.get(choiceItem);


            if (choiceEnemy != -1 && choiceItem != -1 && selectedItem instanceof Poison) {
                game.useConsumableOn(player, (Poison) selectedItem, enemy);

                if (!enemy.isAlive()) {
                    map.getRoomContaining(player).removeOccupant(enemy);
                    player.levelUp();

                    gamePlayLabel.setText(selectedItemName + " KILLED " + enemyName + "! LEVEL UP!");
                } else {
                    gamePlayLabel.setText(selectedItemName + " USED ON " + enemyName);
                    performEnemyHitBack(enemy);
                }

                updateScreen();

            } else if (choiceItem != -1 && selectedItem instanceof HealthPotion) {
                game.useConsumableOn(player, (HealthPotion) selectedItem, player);
                gamePlayLabel.setText("YOU USED " + selectedItemName);
                updateScreen();

            } else if (choiceItem == -1) {
                gamePlayLabel.setText("NO ITEM SELECTED");
            } else if (choiceEnemy == -1) {

                gamePlayLabel.setText("NO ENEMY SELECTED");
            }
        } catch (NullPointerException e) {
            gamePlayLabel.setText("CANNOT USE CONSUMABLE ");
        }


    }

    /**
     * Perform an Action.
     */
    private void performEquipOn() {
        VBox vBox = (VBox) gameRoot.getRight();
        ListView playerItemsListView = (ListView) vBox.getChildren().get(4);
        Inventory playerInventory = player.getInventory();

        try {
            String selectedEquipmentName = (String) playerItemsListView.getSelectionModel().getSelectedItem();
            int choiceItem = playerItemsListView.getSelectionModel().getSelectedIndex();
            Item selectedEquipmentItem = playerInventory.get(choiceItem);

            if (selectedEquipmentItem instanceof Armor) {
                game.equipOn(player, (Armor) selectedEquipmentItem);
                gamePlayLabel.setText("YOU EQUIPPED WITH " + selectedEquipmentName);
                updateScreen();
            } else if (selectedEquipmentItem instanceof Weapon) {
                game.equipOn(player, (Weapon) selectedEquipmentItem);
                gamePlayLabel.setText("YOU EQUIPPED WITH " + selectedEquipmentName);
                updateScreen();
            } else if (choiceItem < 0) {
                gamePlayLabel.setText("NO EQUIPMENT SELECTED");
            } else {
                gamePlayLabel.setText("CANNOT EQUIP AN :" + selectedEquipmentName);
            }

        } catch (NullPointerException e) {

        }


    }

    /**
     * Perform an Action.
     *
     * @param buttonUnEquip The button that has
     *                      to be activated after
     *                      player is equipeed
     */
    private void performUnEquip(Button buttonUnEquip) {

        VBox vBox = (VBox) gameRoot.getRight();
        ListView playerItemsListView = (ListView) vBox.getChildren().get(4);
        Inventory playerInventory = player.getInventory();

        try {
            String selectedEquipmentName = (String) playerItemsListView.getSelectionModel().getSelectedItem();
            int choiceItem = playerItemsListView.getSelectionModel().getSelectedIndex();
            Item selectedEquipmentItem = playerInventory.get(choiceItem);

            if (selectedEquipmentItem instanceof Weapon && player.getEquippedWeapon().equals((Weapon) selectedEquipmentItem)) {

                if (player.unequipWeapon()) {
                    gamePlayLabel.setText("YOU UNEQUIPPED FROM " + selectedEquipmentName);
                } else {
                    gamePlayLabel.setText("NOTHING TO UNEQUIP!");
                }
                updateScreen();
            } else if (selectedEquipmentItem instanceof Armor && player.getEquippedArmor().equals((Armor) selectedEquipmentItem)) {

                if (player.unequipArmor()) {
                    gamePlayLabel.setText("YOU UNEQUIPPED FROM " + selectedEquipmentName);
                } else {
                    gamePlayLabel.setText("NOTHING TO UNEQUIP!");
                }
                updateScreen();
            } else if (choiceItem < 0) {
                gamePlayLabel.setText("NO EQUIPMENT SELECTED");
            } else {
                gamePlayLabel.setText("CANNOT EQUIP AN :" + selectedEquipmentName);
            }

            if (!player.isEquipped()) {
                buttonUnEquip.setDisable(true);
            }

        } catch (NullPointerException e) {
            buttonUnEquip.setDisable(true);
        }
    }

    /**
     * Handle playey movement on map.
     *
     * @param direction Player's movement director.
     */
    private void handlePlayerMovement(String direction) {
        switch (direction) {
            case "UP":
                if (setPlayerPosition(playerPosition[0] - 1, playerPosition[1])) {
                    gamePlayLabel.setText("YOU MOVED " + direction);
                    updateScreen();
                } else {
                    gamePlayLabel.setText("CAN'T GO UP!");
                }
                break;
            case "LEFT":
                if (setPlayerPosition(playerPosition[0], playerPosition[1] - 1)) {
                    gamePlayLabel.setText("YOU MOVED " + direction);
                    updateScreen();
                } else {
                    gamePlayLabel.setText("CAN'T GO LEFT!");
                }
                break;
            case "RIGHT":
                if (setPlayerPosition(playerPosition[0], playerPosition[1] + 1)) {
                    gamePlayLabel.setText("YOU MOVED " + direction);
                    updateScreen();

                } else {
                    gamePlayLabel.setText(" CAN'T GO RIGHT!");
                }
                break;
            case "DOWN":
                if (setPlayerPosition(playerPosition[0] + 1, playerPosition[1])) {
                    gamePlayLabel.setText("YOU MOVED " + direction);
                    updateScreen();
                } else {
                    gamePlayLabel.setText(" CAN'T GO DOWN!");
                }
                break;
        }
    }

    /**
     * Update game Screen.
     */
    private void updateScreen() {

        drawOccupantsInRoom();
        updateRoomEnemies();
        updateEnemyStats();
        updateRoomInventory();
        updatePlayerStats();
        updatePlayerInventory();

    }

    /**
     * Update game Screen.
     */
    private void updateEnemyStats() {
        VBox vBox = (VBox) gameRoot.getLeft();
        HBox hBox = (HBox) vBox.getChildren().get(1);
        ListView<String> enemyListView = (ListView) hBox.getChildren().get(0);

        try {
            //enemyListView.getSelectionModel().select(0);
            Enemy enemy = (Enemy) enemies.get(enemyListView.getSelectionModel().getSelectedItem());
            enemyStatsLabel.setText("LIFE    : " + enemy.getHealth() + "\n\nATTACK  : "
                    + enemy.getAttackPower() + "\nDEFENSE : " + enemy.getDefensivePower());

        } catch (NullPointerException e) {
            enemyStatsLabel.setText("");
        }


    }

    /**
     * Update game Screen.
     */
    private void updatePlayerStats() {

        if (player.getLevel() <= numberOfEnemies) {
            VBox vBox = (VBox) gameRoot.getBottom();
            GridPane playerStatsPane = (GridPane) vBox.getChildren().get(1);
            ObservableList<Node> nodes = playerStatsPane.getChildren();

            ((Label) nodes.get(0)).setText("LIFE: " + player.getHealth());

            if (player.isEquipped()) {
                Armor armor;
                Weapon weapon;
                if ((armor = player.getEquippedArmor()) != null) {
                    ((Label) nodes.get(2)).setText("DEFENSE: " + (player.getDefensivePower() - armor.getDefensivePower()) + " (+" + player.getEquippedArmor().getDefensivePower() + ")");
                }
                if ((weapon = player.getEquippedWeapon()) != null) {
                    ((Label) nodes.get(1)).setText("ATTACK: " + (player.getAttackPower() - weapon.getAttackPower()) + " (+" + player.getEquippedWeapon().getAttackPower() + ")");
                }
            } else {
                ((Label) nodes.get(1)).setText("ATTACK: " + player.getAttackPower());
                ((Label) nodes.get(2)).setText("DEFENSE: " + player.getDefensivePower());
            }

            ((Label) nodes.get(3)).setText("LEVEL: " + player.getLevel());

        } else {                             //Player won
            showVictoriousAlert();
        }
    }

    /**
     * Update game Screen.
     */
    private void updateRoomEnemies() {

        VBox vBox = (VBox) gameRoot.getLeft();
        Button buttonAttack = (Button) vBox.getChildren().get(2);

        HBox hBox = (HBox) vBox.getChildren().get(1);
        ListView enemyListView = (ListView<String>) hBox.getChildren().get(0);

        Room room = map.getRoomContaining(player);
        Collection<Actor> enemyActors = room.getEnemyOccupants();

        enemyListView.getItems().clear();
        enemies = new HashMap<String, Enemy>();
        int n = 0;
        for (Actor actor : enemyActors) {
            if (actor.isAlive()) {
                enemyListView.getItems().add("Enemy_" + n);
                enemies.put("Enemy_" + n, actor);
                n++;
            }
        }

        if (n == 0) {
            buttonAttack.setDisable(true);
            enemyStatsLabel.setText("");
        } else {
            try {
                enemyListView.getSelectionModel().select(0);
                enemyListView.setOnMouseClicked(event -> {
                    Enemy enemy = (Enemy) enemies.get(enemyListView.getSelectionModel().getSelectedItem());
                    enemyStatsLabel.setText("LIFE    : " + enemy.getHealth() + "\n\nATTACK  : "
                            + enemy.getAttackPower() + "\nDEFENSE : " + enemy.getDefensivePower());
                    buttonAttack.setDisable(false);
                });
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Update game Screen.
     */
    private void updateRoomInventory() {
        Room room = map.getRoomContaining(player);
        Inventory inventory = room.getInventory();
        int itemsInInventory = inventory.getSize();

        VBox vBox = (VBox) gameRoot.getRight();
        Button buttonPickUpItem = (Button) vBox.getChildren().get(2);
        ListView itemsListView = (ListView) vBox.getChildren().get(1);
        itemsListView.getItems().clear();

        for (int i = 0; i < itemsInInventory; i++) {
            itemsListView.getItems().add(inventory.get(i).getName());
        }

        if (inventory.getSize() > 0) {
            buttonPickUpItem.setDisable(false);
        } else {
            buttonPickUpItem.setDisable(true);
        }
    }

    /**
     * Update game Screen.
     */
    private void updatePlayerInventory() {


        VBox vBox = (VBox) gameRoot.getRight();
        GridPane inventoryButtonsGridPane = (GridPane) vBox.getChildren().get(5);

        Button buttonDrop = (Button) inventoryButtonsGridPane.getChildren().get(0);
        Button buttonUse = (Button) inventoryButtonsGridPane.getChildren().get(1);
        Button buttonEquip = (Button) inventoryButtonsGridPane.getChildren().get(2);
        Button buttonUnequip = (Button) inventoryButtonsGridPane.getChildren().get(3);


        ListView itemsListView = (ListView) vBox.getChildren().get(4);
        itemsListView.getItems().clear();

        Inventory inventory = player.getInventory();
        int itemsInInventory = inventory.getSize();

        for (int i = 0; i < itemsInInventory; i++) {                    //Update players inventory List
            itemsListView.getItems().add(inventory.get(i).getName());
        }

        //Update inventory Buttons
        if (inventory.getSize() > 0) {
            buttonDrop.setDisable(false);       //Item in inventory, activate drop button

            if (inventory.containsConsumable()) {   //Consumable in inventory activate use button
                buttonUse.setDisable(false);
            }

            if (inventory.containsEquipment()) {    //Equipment in inventory activate equip button
                buttonEquip.setDisable(false);
            }
            if (player.isEquipped()) {              //Player wears equipment in inventory activate unequip button
                buttonUnequip.setDisable(false);
            }

        } else {                                //Empty inventory, disabled inventory Buttons
            buttonDrop.setDisable(true);
            buttonUse.setDisable(true);
            buttonEquip.setDisable(true);
            buttonUnequip.setDisable(true);
        }
    }

    /**
     * Update game Screen.
     */
    private void drawOccupantsInRoom() {

        Room playerRoom = map.getRoomContaining(player);
        int numberOfRoomEnemies = playerRoom.getEnemyOccupants().size();
        ImageView[] enemyImages = new ImageView[numberOfRoomEnemies];

        GridPane gridPane = new GridPane();

        for (int i = 0; i < numberOfRoomEnemies; i++) {
            enemyImages[i] = new ImageView();
            enemyImages[i].setFitHeight(50);
            enemyImages[i].setFitWidth(50);
            enemyImages[i].setImage(new Image("/gui/images/enemy.png"));
        }

        if (numberOfRoomEnemies == 1) {
            gridPane.add(enemyImages[0], 1, 0);
        } else if (numberOfRoomEnemies == 2) {
            gridPane.add(enemyImages[0], 1, 0);
            gridPane.add(enemyImages[1], 1, 1);
        } else if (numberOfRoomEnemies == 3) {
            gridPane.add(enemyImages[0], 1, 0);
            gridPane.add(enemyImages[1], 0, 1);
            gridPane.add(enemyImages[2], 1, 1);
        }

        gridPane.add(playerImage, 0, 0);

        mapPane.getChildren().remove(playerRoomPane);
        playerRoomPane = gridPane;

        mapPane.add(playerRoomPane, playerPosition[1], playerPosition[0]);
    }

    /**
     * Set player position to map.
     *
     * @param x Row
     * @param y Column
     * @return Return true if position
     * can be set to player, because of
     * map constraints.
     */
    private boolean setPlayerPosition(int x, int y) {
        if (x > -1 && y > -1 && x < mapSize && y < mapSize) {
            playerPosition[0] = x;
            playerPosition[1] = y;
            game.moveTo(player, map.getRoom(x, y)); //Move player!
            return true;
        } else {
            return false;
        }
    }

    /**
     * Display an alert, for losing.
     */
    private void showDefeatedAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "YOU DIED!");
        alert.setTitle("YOU LOST!");
        alert.setHeaderText("You were defeated!");
        alert.setContentText("Wanna try again?");

        alert.getButtonTypes().clear();
        ButtonType buttonTypeYes = new ButtonType("YES");
        ButtonType buttonTypeNo = new ButtonType("NO");

        alert.getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeYes) {
            startGame();
        } else {
            System.exit(0);
        }
    }

    /**
     * Display an alert, for winning.
     */
    private void showVictoriousAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "YOU WON!");
        alert.setTitle("YOU WON!");
        alert.setHeaderText("You are victorious!");
        alert.setContentText("Choose and action");

        ButtonType buttonTypeQuit = new ButtonType("QUIT");
        ButtonType buttonTypeMenu = new ButtonType("MAIN MENU");

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(buttonTypeMenu, buttonTypeQuit);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeMenu) {
            GameMainGUI newGameGUI = new GameMainGUI();
            newGameGUI.buildMainMenu((Stage) gameRoot.getScene().getWindow());
        } else {
            System.exit(0);
        }
    }
}
