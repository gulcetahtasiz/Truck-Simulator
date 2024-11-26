import java.io.*;
import java.util.ArrayList;


class ParkingLot {

    public int capacityConstraint;
    public int truckLimit;
    Queue<Truck> waitingTrucks; // Queue that saves the trucks at waiting section.
    Queue<Truck> readyTrucks; // Queue that saves the trucks at ready section.


    public ParkingLot(int capacityConstraint, int truckLimit) {
        this.capacityConstraint = capacityConstraint;
        this.truckLimit = truckLimit;
        this.waitingTrucks = new Queue<>();
        this.readyTrucks = new Queue<>();

    }

    //checks if the truck limit is full or not at given parking lot.
    public boolean isParkingLotFull() {
        return waitingTrucks.size() + readyTrucks.size() >= truckLimit; //toplamında truck limiti geçmemeli.
    }

    //checks if the waiting section queue is full or not at given parking lot.
    public boolean isWaitingSectionEmpty(){
        return waitingTrucks.isEmpty();
    }


}