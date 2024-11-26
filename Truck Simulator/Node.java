class Node {
    int capacity;
    ParkingLot parkingLot;
    int height;
    Node left;
    Node right;

    Node parent;

    Node(int capacity, ParkingLot parkingLot, Node parent) {
        this.parkingLot = parkingLot;
        this.height = 1;
        this.capacity=capacity;
        this.parent = parent;
    }
}
