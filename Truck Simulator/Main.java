import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static AVLtree parkingLots = new AVLtree(); // AVL tree to store parking lots.


    //this method creates parking lots with given capacity and truck limit parameters
    public static void createParkingLot(int capacity, int truckLimit) {
        /* Check if there is already a parking lot with the same capacity
        by using the 'getNodebyCapacity' method. If it returns null,
        no parking lot with the given capacity exists.*/
        if (parkingLots.getNodebyCapacity(capacity) == null) {
            ParkingLot newLot = new ParkingLot(capacity, truckLimit);
            // Insert the new parking lot into the parking lot structure (presumably an AVL tree)
            // by using the specified capacity as the key.
            parkingLots.insert(capacity, newLot);
        }
    }


    /*This method adds a given truck to an appropriate parking lot based on the truck's capacity constraint.
   There are four cases handled by this method:
   1) If a parking lot with a matching capacity constraint exists and has not exceeded its truck limit.
   2) If a parking lot with a matching capacity constraint exists, but the truck limit is already exceeded.
   3) If no parking lot exists with a matching capacity constraint, the method will look for the nearest
      available lot with a larger capacity and, if needed, check the nearest smaller capacity.
   4) If no suitable lot is found, the method returns -1.*/
    public static int addTruck(Truck truck) {

        // finds a parking lot node with the same capacity constraint as the truck's max capacity.
        // If found, 'node' will reference that lot; if not, 'node' will be null.
        Node node = parkingLots.getNodebyCapacity(truck.maxCapacity);
        ParkingLot targetLot = null;

        // If no exact capacity match is found, look for the next largest capacity node that could accept the truck.
        // Then, check if there's a smaller node  within acceptable capacity.
        if (node == null) {
            node = parkingLots.findGreaterThan(truck.maxCapacity);

            // If a larger node is found, and it exceeds the truck's max capacity, get the next smaller lot.
            if (node != null && node.parkingLot.capacityConstraint > truck.maxCapacity) {
                node = parkingLots.getInOrderPredecessor(node);
            }
        }

        // Traverse through nodes to find the first parking lot that has available space.
        while (node != null) {
            ParkingLot lot = node.parkingLot;

            // If the current lot is not full, set it as the target lot and exit the loop.
            if (lot != null && !lot.isParkingLotFull()) {
                targetLot = lot;
                break;
            }
            // If the current lot is full, continue to the next smaller capacity node.
            node = parkingLots.getInOrderPredecessor(node);
        }

        // If a suitable lot was found, add the truck to its waiting queue and return the lot's capacity constraint.
        if (targetLot != null) {
            targetLot.waitingTrucks.enqueue(truck); // Add truck to the selected lot's waiting trucks.
            return targetLot.capacityConstraint;  // Return the capacity of the lot where the truck was added.
        } else {
            return -1; // If no suitable lot was found, return -1.
        }
    }



    /* This method prepares a truck from the waiting section of a parking lot with the specified capacity.
    It returns the truck's ID and the capacity of the lot where it was found. If no suitable truck is found, it returns "-1".
    There are four main cases handled in this method:
    1) If there is a parking lot with a capacity constraint exactly matching the specified capacity and has waiting trucks.
    2) If there is a parking lot with a smaller capacity constraint, but no matching capacity lot exists.
    3) If the parking lot with matching or nearest capacity has no trucks in its waiting section, we continue searching.
    4) If no parking lot meets any of these criteria, "-1" is returned to indicate no truck is ready. */
    public static String ready(int capacity) {

        // finds a parking lot node with the same capacity constraint as the truck's max capacity.
        // If found, 'node' will reference that lot; if not, 'node' will be null.
        Node node = parkingLots.getNodebyCapacity(capacity);
        Truck targetTruck = null;  // the variable to save the truck that will move to the ready from waiting.


        // If no exact capacity match is found, look for the next smallest capacity node.
        // Then, check if there's a larger node  within acceptable capacity.
        if (node == null) {
            node = parkingLots.findLessThan(capacity);

            if (node != null && node.parkingLot.capacityConstraint < capacity) {
                node = parkingLots.getInOrderSuccessor(node);
            }
        }

        // Traverse through nodes to find the first parking lot that has available space at its waiting list.
        while (node != null) {
            ParkingLot lot = node.parkingLot;
            // if we found the exact parking lot and it has truck in its waiting list,
            if (!lot.isWaitingSectionEmpty()) {
                targetTruck = lot.waitingTrucks.dequeue(); // dequeue the first in truck
                lot.readyTrucks.enqueue(targetTruck); // enqueue it to the ready queue.
                return targetTruck.id + " " + lot.capacityConstraint;
            }
            // If there is no truck at current lots waiting list, continue to the next larger capacity node.
            node = parkingLots.getInOrderSuccessor(node);
        }

        return "-1"; // If no suitable lot was found, return -1.
    }



    public static void main(String[] args) {
        String filename = args[0];
        String outputFilename = args[1];

        try (BufferedReader br = new BufferedReader(new FileReader(filename));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilename))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String command = parts[0];

                if (command.equals("create_parking_lot")) {
                    int capacity = Integer.parseInt(parts[1]);
                    int truckLimit = Integer.parseInt(parts[2]);
                    createParkingLot(capacity, truckLimit);
                } else if (command.equals("add_truck")) {
                    int id = Integer.parseInt(parts[1]);
                    int maxCapacity = Integer.parseInt(parts[2]);
                    Truck truck = new Truck(id, maxCapacity);
                    int result = addTruck(truck);
                    bw.write(String.valueOf(result));
                    bw.newLine();
                } else if (command.equals("ready")) {
                    int capacity = Integer.parseInt(parts[1]);
                    String result = ready(capacity);
                    bw.write(result);
                    bw.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}