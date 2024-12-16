package org.example.cincuentazo.controller;

import org.example.cincuentazo.model.Cards;
import org.example.cincuentazo.model.Deck;
import org.example.cincuentazo.model.Player;
import org.example.cincuentazo.model.Alerts.AlertBox;
import org.example.cincuentazo.view.InstructionsView;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * GameController class to manage the interaction between the model and view classes in the card game.
 * It controls the game logic, player turns, and updates the user interface accordingly.
 * @author Olman Alexander Silva 2343025-2724
 * @version 1.0
 */
public class GameController {
    /**
     * GridPane representing the user's game area.
     */
    public GridPane userGame;
    /**
     * GridPane representing the first machine player's game area.
     */
    public GridPane playerMachine3;
    /**
     * GridPane representing the second machine player's game area.
     */
    public GridPane playerMachine2;
    /**
     * GridPane representing the third machine player's game area.
     */
    public GridPane playerMachine1;
    /**
     * GridPane representing the shared table area where cards are placed.
     */
    public GridPane gridPaneTable;

    /**
     * List of played cards
     */
    private final List<Cards> playedCards = new ArrayList<>();
    /**
     * TextField to display the current sum of the cards on the table.
     */
    public TextField textFieldSuma;
    /**
     * TextField to display the current turn status of the game.
     */
    public TextField textFieldTurnStatus;
    /**
     * List containing all the players participating in the game.
     */
    private final List<Player> players = new ArrayList<>();
    /**
     * The player currently taking their turn.
     */
    private Player currentPlayer;
    /**
     * The deck of cards used in the game.
     */
    private Deck deck;
    /**
     * The number of machine players in the game.
     */
    private int numberOfMachinePlayers;
    /**
     * The current sum of the card values on the table.
     */
    private int tableSum = 0;
    /**
     * Indicates whether it is the user's turn.
     */
    private boolean isUserTurn = false;
    /**
     * Lock used to coordinate access to the current turn, ensuring thread safety.
     */
    private final Lock turnLock = new ReentrantLock();
    /**
     * Condition used to suspend and resume machine player threads based on the user's turn.
     */
    private final Condition userTurnEnded = turnLock.newCondition();

    /**
     * Handles the action of starting the game when the "Start Game" button is clicked.
     * This method performs the following actions:
     * <ul>
     *   <li>Displays a face-down card on the table grid pane.</li>
     *   <li>Prompts the user with a dialog box to select the number of machine players (1 to 3).</li>
     *   <li>Validates the input and shows an alert if the input is invalid.</li>
     *   <li>Initializes the game setup, including displaying cards for machine players and starting the game loop.</li>
     * </ul>
     *
     * @param event the event triggered by clicking the "Start Game" button.
     */
    @FXML
    public void onActionStarGameButton(ActionEvent event) {
        System.out.println("ActionEvent");
        // Display a face-down card on the table grid pane
        ImageView imageViewTable = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/org/example/cincuentazo/Images/cardBack.png")).toExternalForm()));
        imageViewTable.setFitHeight(140);
        imageViewTable.setFitWidth(90);
        // Set event handler for drawing a card from the deck
        imageViewTable.setOnMouseClicked(this::drawCardFromDeck);
        gridPaneTable.add(imageViewTable, 1, 0);
        // Prompt dialog to select the number of machine players
        TextInputDialog dialog = new TextInputDialog("1");
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/cincuentazo/Images/icon.png")));
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        dialog.setTitle("Cincuentazo Game");
        dialog.setHeaderText("Number of players");
        dialog.setContentText("How many machine players do you want to play (maximum 3):");

        Optional<String> result = dialog.showAndWait();

        // Process user input
        result.ifPresent(input -> {
            try {
                int players = Integer.parseInt(input);
                if (players >= 1 && players <= 3) {
                    numberOfMachinePlayers = players;
                    System.out.println("Número de jugadores seleccionado: " + numberOfMachinePlayers);
                } else {
                    new AlertBox().showAlert(
                            "Cincuentazo Game",
                            "Alert",
                            "Please enter a valid number between 1 and 3."
                    );
                }
            } catch (NumberFormatException e) {
                new AlertBox().showAlert(
                        "Cincuentazo Game",
                        "Alert",
                        "Invalid input. Please enter a number."
                );
            }
        });
        // Initialize the game
        initializeGame();
        // Display image of face-down cards for each machine player
        displayMachineCards();
    }

    /**
     * Displays the game instructions.
     * This method is triggered when the "Instructions" button is clicked.
     * It opens the instructions view, providing details about how to play the game.
     *
     * @param actionEvent the event triggered by clicking the "Instructions" button.
     * @throws IOException if an error occurs while loading the instructions view.
     */
    @FXML
    public void onActionGameInstructions(ActionEvent actionEvent) throws IOException {
        InstructionsView.getInstance();
    }

    /**
     * Initializes the game by setting up the deck, players, and the initial state of the table.
     * This method prepares the deck, shuffles it, deals cards to players, sets the initial card
     * on the table, and starts the game loop in a separate thread.
     */
    private void initializeGame() {
        deck = new Deck(90, 140);
        deck.suffle();
        Cards initialCard = deck.drawCard(); // Draw a random card
        // Display the initial card on the table
        Platform.runLater(() -> {
            Cards cardNode = new Cards(initialCard.getRank(), initialCard.getSuit(), 90, 140);
            gridPaneTable.add(cardNode, 0, 0);
            playedCards.add(cardNode);//add card to list
        });
        // Update the initial table sum
        tableSum = initialCard.getValue();
        sumStatus(); // Update the sum status

        // Create the user player
        players.add(new Player("User"));
        // Create machine players based on the user's selection
        for (int i = 1; i <= numberOfMachinePlayers; i++) {
            players.add(new Player("Machine " + i));
        }
        // Deal 4 cards to each player
        for (Player player : players) {
            for (int i = 0; i < 4; i++) {
                player.addCard(deck.drawCard());
            }
        }
        currentPlayer = players.get(1); // Machine 1 start first
        displayUserCards();

        // Code to run startGameLoop in a separate thread from the GUI thread
        Thread gameThread = new Thread(this::startGameLoop);
        gameThread.setDaemon(true);
        gameThread.start();
    }

    /**
     * Displays the back image of cards for the machine players.
     * This method adds the back of cards images to the GridPane for each machine player
     * based on the number of machine players selected. It ensures that the appropriate
     * number of card back images are shown for each machine player.
     */
    private void displayMachineCards() {
        ImageView imageView1 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/org/example/cincuentazo/Images/cardsBack.png")).toExternalForm()));
        imageView1.setFitHeight(85);
        imageView1.setFitWidth(140);
        playerMachine1.add(imageView1, 0, 0);
        if (numberOfMachinePlayers > 1) {
            ImageView imageView2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/org/example/cincuentazo/Images/cardsBack.png")).toExternalForm()));
            imageView2.setFitHeight(85);
            imageView2.setFitWidth(153);
            playerMachine2.add(imageView2, 0, 0);
        }
        if (numberOfMachinePlayers > 2) {
            ImageView imageView3 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/org/example/cincuentazo/Images/cardsBack.png")).toExternalForm()));
            imageView3.setFitHeight(97);
            imageView3.setFitWidth(153);
            playerMachine3.add(imageView3, 0, 0);
        }
    }

    /**
     * Displays the user's cards in the game.
     * This method updates the view to show the current user's hand of cards.
     * It clears any previously displayed cards and then iterates over the user's cards
     * to create new visual representations for each card. Each card is displayed
     * in a grid layout, and mouse click events are attached to the cards to allow
     * the user to interact with them and play their cards.
     */
    private void displayUserCards() {
        Platform.runLater(() -> {
            // Clear previous cards from the user's hand
            userGame.getChildren().clear();
            // Get the current user (assumed to be the first player in the list)
            Player user = players.get(0);
            int col = 0;
            // Iterate through the user's cards and display each one
            for (Cards card : user.getHand()) {
                // Create a new card node to represent the card in the UI
                Cards cardNode = new Cards(card.getRank(), card.getSuit(), 90, 140);
                // Clear any previous mouse click events attached to the card
                cardNode.setOnMouseClicked(null);
                // Add a new mouse click event to play the selected card
                cardNode.setOnMouseClicked(event -> playUserCard(card));
                // Add the card to the user’s grid (userGame) at the current column position
                userGame.add(cardNode, col++, 0);
            }
        });
    }

    /**
     * Allows the user to play a selected card from their hand.
     * This method updates the game state when the user plays a card, including
     * removing the card from the user's hand, updating the table sum, and updating
     * the game interface to reflect the new card placed on the table.
     * It also ensures that the user's turn ends and notifies the game to proceed with the next player's turn.
     *
     * @param card The card the user wishes to play.
     */
    private void playUserCard(Cards card) {
        // Get the current user (assumed to be the first player in the list)
        Player user = players.getFirst();
        // Check if the card is part of the user's hand
        if (user.getHand().contains(card)) {
            user.getHand().remove(card); // Remove the card from the user's hand
            // Update the table sum with the value of the card played
            tableSum += card.getValue();
            // Update the game interface
            Platform.runLater(() -> {
                // Create a visual representation of the card and add it to the table (gridPaneTable)
                Cards cardNode = new Cards(card.getRank(), card.getSuit(), 90, 140);
                gridPaneTable.add(cardNode, 0, 0);
                playedCards.add(cardNode);//add card to list
                // Refresh the user's card display
                displayUserCards();
                // Update the table sum status
                sumStatus();
                // Update the turn status for the current player
                turnStatus(currentPlayer);
                // Check and eliminate players if necessary (e.g., if a player goes over 50 points)
                checkAndEliminatePlayers(tableSum);
            });
            // End the user's turn and notify other threads
            turnLock.lock();
            try {
                // Set the user's turn to false, signaling the end of the turn
                isUserTurn = false;
                // Notify the threads that the user's turn has ended
                userTurnEnded.signal();
            } finally {
                // Release the lock to allow the next thread to proceed
                turnLock.unlock();
            }
        }
    }

    /**
     * This method is called when the user clicks to draw a card from the deck.
     * It checks if the deck is not empty and, if so, draws a card and adds it to the user's hand.
     * The method also updates the game interface to reflect the new card in the user's hand
     * and ends the user's turn.
     * If the deck is empty, it shows an alert to inform the user that no cards are left.
     *
     * @param event The mouse event triggered when the user clicks to draw a card.
     */
    private void drawCardFromDeck(MouseEvent event) {
        // Get the current user (assumed to be the first player in the list)
        Player user = players.getFirst();
        // Check if the deck has cards available
        if (!deck.isEmpty()) {
            // Draw a card from the deck
            Cards drawnCard = deck.drawCard();
            // Update the game interface to display the new cards in the user's hand
            displayUserCards();
            // Add the drawn card to the user's hand
            user.addCard(drawnCard);

            // End the user's turn and notify other threads
            turnLock.lock();
            try {
                // Set the user's turn to false, signaling the end of the turn
                isUserTurn = false;
                // Notify the threads that the user's turn has ended
                userTurnEnded.signal();
            } finally {
                // Release the lock to allow the next thread to proceed
                turnLock.unlock();
            }
            // Print the drawn card's rank and suit for debugging purposes
            System.out.println("Carta tomada: " + drawnCard.getRank() + " de " + drawnCard.getSuit());
        } else {
            // If the deck is empty, display an alert
            Platform.runLater(() -> {
                deck.returnCardToDeck(deck.drawCard());
                new AlertBox().showAlert(
                        "Cincuentazo Game",
                        "Deck Empty",
                        "The Deck haven't cards"
                );
                // add cards to deck
                playedCards.forEach(deck::returnCardToDeck);
                // clear the list
                playedCards.clear();
            });
        }
    }

    /**
     * This method handles the game loop, which manages the alternating turns of the players.
     * It continuously runs, updating the turn status and checking which player is currently playing.
     * If the current player is the user, the method waits for the user to finish their turn before proceeding.
     * If the current player is a machine player, it automatically executes the machine's turn.
     * Once the current player completes their turn, the method proceeds to the next player.
     * The game loop runs indefinitely until the game is stopped or finished.
     */
    private void startGameLoop() {
        // Infinite loop to keep the game running
        while (true) {
            // Lock to ensure synchronized turn management
            turnLock.lock();
            try {
                // Update the turn status in the UI
                Platform.runLater(() -> turnStatus(currentPlayer));
                // Check if the current player is the user
                if (currentPlayer.isUser()) {
                    // If it's the user's turn, set the turn flag and wait for the user to finish
                    isUserTurn = true;
                    waitForUserTurn(); // Wait for the user to play their turn
                    // Wait until the user finishes their turn
                    while (isUserTurn) {
                        // Await signal that the user's turn is over
                        userTurnEnded.await();
                    }
                } else {
                    // If it's a machine player's turn, let the machine play automatically
                    playMachineTurn(currentPlayer);
                }
            } catch (InterruptedException e) {
                // Handle interruption of the thread
                Thread.currentThread().interrupt();
            } finally {
                // Always release the lock after finishing the turn
                turnLock.unlock();
            }
            // Move to the next player
            nextPlayer();
        }
    }

    /**
     * This method waits for the user's turn to play in the game.
     * It notifies the user that it's their turn and allows them to select and play a card from their hand.
     * Once the user plays a card, the method automatically draws a new card for them from the deck and ends their turn.
     * After the turn ends, the method signals to other threads that the user's turn is complete.
     * The method ensures that the user can only make a move during their turn and prevents them from playing cards when it's not their turn.
     */
    private void waitForUserTurn() {
        // Run the code on the JavaFX application thread

        Platform.runLater(() -> {
            // Show an alert to notify the user that it's their turn to play
            new AlertBox().showAlert(
                    "Cincuentazo Game",
                    "Alert",
                    "It's your turn! Play a card."
            );
            // Allow the user to select and play a card from their hand
            Player user = players.getFirst(); // Get the first player (user)
            for (Cards card : user.getHand()) { // Iterate through the user's hand
                // Add a mouse click event handler for each card
                card.setOnMouseClicked(event -> {
                    if (isUserTurn) { // Check if it's the user's turn
                        playUserCard(card); // Play the selected card
                        drawCardFromDeck(null); // Draw a new card from the deck
                        displayUserCards();//*****

                        // End the user's turn
                        turnLock.lock(); // Lock to safely change turn status
                        try {
                            isUserTurn = false; // Set user turn to false
                            userTurnEnded.signal(); // Signal that the user's turn is over
                        } finally {
                            turnLock.unlock(); // Always unlock after modifying the state
                        }
                    }
                });
            }
        });
        checkAndEliminatePlayers(tableSum);
    }

    /**
     * This method handles the turn of a machine player. It allows the machine to play a card,
     * draw a new card from the deck, and updates the game state accordingly.
     * <p>
     * The machine will play a valid card if it has one. If not, the turn is skipped.
     * After playing a card, the machine draws a new card from the deck and updates the table's sum.
     * If the deck is empty or the machine has no cards to play, the method handles these cases accordingly.
     * <p>
     * The method simulates the machine player's thinking time by introducing a delay before making a move.
     *
     * @param machine The machine player whose turn is being processed.
     */
    private void playMachineTurn(Player machine) {
        // Update the turn status on the UI
        Platform.runLater(() -> turnStatus(machine));
        // Check if the machine player has no cards left
        if (machine.getHand().isEmpty()) {
            System.out.println(machine.getName() + " no tiene cartas para jugar.");
            //return; // Skip the turn if no cards are available
        }
        // Check if the deck is empty and no more cards can be drawn
        if (deck.isEmpty()) {
            System.out.println("Deck is empty. Can't take a card.");
            // add cards to deck
            playedCards.forEach(deck::returnCardToDeck);
            // clear the list
            playedCards.clear();
        }
        try {
            // Simulate thinking time for the machine player (2-4 seconds delay)
            Thread.sleep((long) (2000 + Math.random() * 2000));
            // Execute the machine's play on the UI thread
            Platform.runLater(() -> {
                // The machine plays a valid card, or returns null if there are no valid cards
                Cards card = machine.playCard(tableSum);
                if (card == null) {
                    System.out.println(machine.getName() + " haven't a valid card to play");
                    checkAndEliminatePlayers(tableSum);
                    //return; // Machine can't play any valid card
                }
                // Log the card played by the machine
                System.out.println(machine.getName() + " played a card: " + card.getRank() + " of " + card.getSuit());
                // Update the table sum after the card is played
                tableSum += card.getValue();

                // Display the played card on the game table (GridPane)
                Cards cardNode = new Cards(card.getRank(), card.getSuit(), 90, 140);
                gridPaneTable.add(cardNode, 0, 0);
                playedCards.add(cardNode);//add card to list

                // Update the sum status on the UI
                sumStatus();
                // The machine draws a new card from the deck
                machine.addCard(deck.drawCard());
                // Check for player eliminations based on the updated sum
                //checkAndEliminatePlayers(tableSum);*******
            });
            // Machine draws another card after playing
            machine.addCard(deck.drawCard());
        } catch (InterruptedException e) {
            // Handle interruption of the thread during sleep
            Thread.currentThread().interrupt();
        }
    }

    /**
     * This method switches the turn to the next player in the game.
     * It updates the `currentPlayer` to the next player in the list,
     * and if the last player is reached, it loops back to the first player.
     * It also prints the name of the new current player to the console and updates
     * the UI to reflect the current player's turn.
     */
    private void nextPlayer() {
        // Get the current player's index in the players list
        int currentIndex = players.indexOf(currentPlayer);
        // Set the next player as the current player (looping back to the start if necessary)
        currentPlayer = players.get((currentIndex + 1) % players.size());
        // Print the name of the player whose turn it is
        System.out.println("Turno de: " + currentPlayer.getName());
        // Update the UI to show the current player's turn
        Platform.runLater(() -> turnStatus(currentPlayer));
    }

    /**
     * This method updates the game's user interface to display the current player's turn.
     * It checks if the current player is the user or another player (e.g., a machine),
     * and updates the status text accordingly.
     *
     * @param currentPlayer the player whose turn it is
     */
    public void turnStatus(Player currentPlayer) {
        if (currentPlayer.isUser()) {
            textFieldTurnStatus.setText("User's Turn");
        } else {
            textFieldTurnStatus.setText(currentPlayer.getName() + "'s Turn");
        }
    }

    /**
     * This method updates the UI to display the current sum of the table.
     * It sets the value of the `textFieldSuma` to show the current `tableSum`.
     * The `textFieldSuma` is set to be non-editable to prevent user modification.
     */
    public void sumStatus() {
        textFieldSuma.setEditable(false);
        textFieldSuma.setText(String.valueOf(tableSum));
    }

    /**
     * This method checks if any player does not have a valid card to play based on the current sum of points on the table.
     * If a player does not have any playable cards, they are eliminated from the game.
     * If only one player remains, that player is declared the winner.
     *
     * @param currentSum the current sum of points on the table used to determine if a player has a valid card to play
     */
    private void checkAndEliminatePlayers(int currentSum) {
        tableSum = currentSum;
        // Ensure the player list is not empty
        if (players.isEmpty()) {
            System.out.println("No players left to eliminate.");
            return;
        }
        // Iterate through the players and check if they have any playable cards
        Iterator<Player> iterator = players.iterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            System.out.println("Checking player: " + player.getName());
            System.out.println("Player hand: " + player.getHand());
            // Eliminate the player
            if (tableSum > player.getMaxSum()) {
                System.out.println(player.getName() + " has been eliminated because the table sum exceeded the maximum allowed.");
                iterator.remove();
                if (players.size() == 1) {
                    declareWinner(players.getFirst());
                }else{
                    continue; // next player
                }
            }

            // Check if the player has any playable cards based on the current sum
            if (!player.hasPlayableCard(currentSum)) {
                System.out.println(player.getName() + " has been eliminated for not having a playable card.");
                iterator.remove(); // Eliminate the player
            }
        }
        // If only one player is left, declare them the winner
        if (players.size() == 1) {
            declareWinner(players.get(0));
        }
    }

    /**
     * Declares the winner of the game by displaying an alert box with the winner's name.
     * After declaring the winner, the game is ended by calling System.exit(0),
     * which terminates the application.
     *
     * @param winner the player who is the last one remaining in the game and is declared the winner
     */
    private void declareWinner(Player winner) {
        Platform.runLater(() -> {
            // Show an alert box with the winner's name
            new AlertBox().showAlert(
                    "Cincuentazo Game",
                    "Winner!",
                    currentPlayer.getName() + " is the last player standing and wins the game!"
            );
            // End the game and reset the parameters
            tableSum = 0;
            players.clear(); // Clear the list
            deck = new Deck(90, 140); // New deck
            deck.suffle();
            gridPaneTable.getChildren().clear(); //clear the gridPaneTable
            currentPlayer = players.getFirst();
        });
    }
}

