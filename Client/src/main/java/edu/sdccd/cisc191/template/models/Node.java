package edu.sdccd.cisc191.template.models;

//Sean Standen - Peer Review
//Refactored out Node class to its own file under models package.
//Made class public to be accessed in other packages.
public class Node {
    private int value;
    private Node next;

    public Node(int value) {
        this.value = value;
        this.next = null;
    }

    //Sean Standen - Peer Review
    //Made value a private variable and created a getter method for it.
    public int getValue() {
        return this.value;
    }

    //Sean Standen - Peer Review
    //made next private and created a getter method for it.
    public Node getNext() {
        return this.next;
    }

    //Sean Standen - Peer Review
    //made next private and created a setter method for it.
    public void setNext(Node newNode) {
        this.next = newNode;
    }

    public void printNodesRecursively() {
        System.out.print(this.value + " ");
        if (this.next != null) {
            this.next.printNodesRecursively();
        }
    }

    public boolean search(int target) {
        Node current = this;
        while (current != null) {
            if (current.value == target) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}
