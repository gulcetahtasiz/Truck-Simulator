public class Queue<T>{
    private class Node {
        T data;
        Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front; // Front of the queue
    private Node rear;  // Rear of the queue
    private int size;   // Size of the queue

    public Queue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    // Adds an element to the end of the queue
    public void enqueue(T data) {
        Node newNode = new Node(data);
        if (rear == null) { // If queue is empty
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    // Removes and returns the front element from the queue
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        T data = front.data;
        front = front.next;
        if (front == null) { // If the queue becomes empty
            rear = null;
        }
        size--;
        return data;
    }

    // Returns the front element without removing it
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return front.data;
    }

    // Checks if the queue is empty
    public boolean isEmpty() {
        return front == null;
    }

    // Returns the size of the queue
    public int size() {
        return size;
    }

    // Clears all elements from the queue
    public void clear() {
        front = rear = null;
        size = 0;
    }
}
