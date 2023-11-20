package edu.sdccd.cisc191.template.models;

import java.io.Serializable;

//Sean Standen - Peer Review
//Refactored out User class to its own file under models package.
//Made class public to be accessed in other packages.

public class User {
    private String name;
    private int score;

    public User(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void sendMessageToServer() {
        // Adjust this method to actually send data to the server
        // For now, it just prints a message
        System.out.println("Sending user data to server: " + this);
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', score=" + score + '}';
    }

    public class Message implements Serializable {
        private String type;
        private String content;

        public Message(String type, String content) {
            this.type = type;
            this.content = content;
        }

        // Standard getters and setters
        public String getType() {
            return type;
        }

        public String getContent() {
            return content;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "Message{" +
                    "type='" + type + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}
