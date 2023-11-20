package edu.sdccd.cisc191.template.models;

import java.io.*;
import java.net.*;
import java.util.*;

//Sean Standen - Peer Review
//Moved refactored NumberGuessingGame class to models package.
//Made class public to be accessed in other packages.
//Removed the main method to it's own class file.
public class NumberGuessingGame {

    Map<String, User> userMap = new HashMap<>();
    private ArrayList<Integer> highScores = new ArrayList<>();

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public NumberGuessingGame() {
        try {
            socket = new Socket("localhost", 3000);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Generate a linked list for the secret number
        Node secretNumberList = generateSecretNumberList(random.nextInt(100) + 1);
        int attempts = 0;

        // Load high scores from a file
        highScores = loadHighScores();

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("Try to guess the secret number between 1 and 100.");

        while (true) {
            System.out.print("Enter your guess: ");
            int guess = scanner.nextInt();
            attempts++;

            if (guess < secretNumberList.getValue()) {
                System.out.println("Try higher!");
            } else if (guess > secretNumberList.getValue()) {
                System.out.println("Try lower!");
            } else {
                System.out.println("Congratulations! You guessed the number in " + attempts + " attempts.");
                highScores.add(attempts);
                Collections.sort(highScores); // Sort high scores

                // Save high scores to a file
                saveHighScores(highScores);

                System.out.print("Enter your name: ");
                String playerName = scanner.next();
                User user = new User(playerName, attempts);
                userMap.put(user.getName(), user);

                // Simulate sending user data to server (network communication)
                user.sendMessageToServer();

                System.out.print("Play again? (yes/no): ");
                String playAgain = scanner.next();

                if ("no".equalsIgnoreCase(playAgain)) {
                    System.out.println("Thanks for playing! Goodbye.");
                    break;
                } else if ("yes".equalsIgnoreCase(playAgain)) {
                    secretNumberList = generateSecretNumberList(random.nextInt(100) + 1);
                    attempts = 0;
                }
            }
        }

        scanner.close();
    }

    /**
     * Generates a linked list with a random secret number.
     *
     * @param secretNumber The secret number.
     * @return The head of the linked list.
     */
    public static Node generateSecretNumberList(int secretNumber) {
        Node head = new Node(secretNumber);
        Node current = head;

        for (int i = 0; i < secretNumber; i++) {
            int randomValue = new Random().nextInt(100) + 1;
            Node newNode = new Node(randomValue);
            current.setNext(newNode); //Sean Standen Peer Review - Updated this to setter method instead
            //of directly accessing the instance variable.
            current = newNode;
        }

        head.printNodesRecursively(); // Print the list recursively
        return head;
    }

    /**
     * Loads the high scores from a file.
     *
     * @return An ArrayList containing the high scores.
     */

    //Sean Standen - Peer Review
    //Made method public as it wasn't accessible outside the class.
    public static ArrayList<Integer> loadHighScores() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("highscores.ser"))) {
            return (ArrayList<Integer>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // If there's an error, return an empty list
            return new ArrayList<>();
        }
    }

    /**
     * Saves the high scores to a file.
     *
     * @param highScores An ArrayList containing the high scores.
     */
    public static void saveHighScores(ArrayList<Integer> highScores) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("highscores.ser"))) {
            outputStream.writeObject(highScores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void bubbleSortHighScores(ArrayList<Integer> scores) {
        int n = scores.size();
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (scores.get(j) > scores.get(j+1)) {
                    Integer temp = scores.get(j);
                    scores.set(j, scores.get(j+1));
                    scores.set(j+1, temp);
                }
            }
        }
    }

    // Close the socket and resources when the game ends
    public void closeSocket() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}