import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Fleet_Management {
    private static final Scanner keyboard = new Scanner(System.in);
    private static final int BOATS_IN_FILE = 4; //constant data value representing number of boat stored in initial file
    private static final double MAX_PPRICE = 1000000.00; //constant data value representing maximum boat purchase price
    private static final int MAX_LENGTH =  100; //constant data value representing maximum boat length
    private static final int MAX_MAKE_LENGTH = 10; //constant data value representing maximum boat make name length
    private static final int MAX_BOAT_NAME_LENGTH = 20; //constant data value representing maximum boat name length
    private static final int POWER_BASE = 2;
    private static final int NUMBER_OF_NODES = 8;

    public static void main(String [] args) throws Exception{
        ArrayList<Boat> boats = new ArrayList<>();
        String fileName = "FleetData.csv";

        if (args.length > 0) { //checks if any arguments are present
            readCSV(args[0], boats); //method reading the initial fleet information from provided csv file
        } else {
            if ((boats = loadBoat(fileName)) == null) {
                return;
            }
        }
        displayWelcome(); //displays welcome message
        menu(boats); //calls menu to display options to users
        saveBoats("FleetData.csv", boats); //saves updates to fleet to file



//--------------------------------------------------------------

        int size = 0;
        int[] data;

        try { //ensures amount to spend entered is a numeric value
            getSize();
        } catch (InputMismatchException e){
            System.out.println("Invalid Input - Please enter a number");
        }

        try { //ensures amount to spend entered is a numeric value
            data = new int[size];
        } catch (NegativeArraySizeException e){
            System.out.println("Invalid Input");
        }
    }

    private static int getSize() {
        return(keyboard.nextInt());
    }

    //method reading in data from indicated csv file with initial boats in fleet
    public static void readCSV (String fileName, ArrayList<Boat> boats) throws IOException {
        BufferedReader fromBufferedReader;
        String data;
        int indexData = 0;
        String[] fleetData = new String[BOATS_IN_FILE];

        fromBufferedReader = new BufferedReader(new FileReader(fileName) ); //gets csv fle data from buffered reader
        data = fromBufferedReader.readLine(); //reads boat data from first line of csv file
        while(data != null) {
            fleetData[indexData] = data; //stores boat data read from csv file in array of boats in fleet
            indexData++;
            data = fromBufferedReader.readLine(); //reads boat data from line at index of csv file
        }
        //splits the lines of data for each boat into individual data pieces
        for(int i = 0; i < indexData; i++) {
            String[] boatData = fleetData[i].split(",");
            Boat newBoat = new Boat(boatData); //transfers the boat data split into a boat object
            boats.add(newBoat); //adds boat made above to the fleet arrayList
        }
        fromBufferedReader.close(); //closes buffered reader
    }


    //method displaying welcome message
    public static void displayWelcome() {
        System.out.println("Welcome to the Fleet Management System");
        System.out.print("--------------------------------------\n");
    }

    //method displaying exit message
    public static void displayExit() {
        System.out.print("\nExiting the Fleet Management System");
    }

    //menu method allowing user to choose which function they would like to perform on fleet collection
    public static void menu(ArrayList<Boat> boats) {
        char choice;

        do { //loop showing the menu to user repeatedly until program is exited
            System.out.println("");
            System.out.print("(P)rint, (A)dd, (R)emove, (E)xpense, e(X)it : ");
            choice = Character.toUpperCase(keyboard.nextLine().charAt(0)); //getting choice of operation from user
            switch (choice) { //calling the function to carry out operation chosen by user
                case 'P':
                    displayBoat(boats); //displays fleet inventory
                    break;
                case 'A':
                    addBoat(boats); //adds a boat to the fleet
                    break;
                case 'R':
                    removeBoat(boats); //removes a boat from the fleet
                    break;
                case 'E':
                    addExpenses(boats); //adds maintenance expenses to a specified boat in the fleet
                    break;
                case 'X':
                    displayExit(); //calls method displaying exit message
                    break;
                default: //default method in case choice is invalid
                    System.out.print("Invalid menu option, try again ");
                    break;
            }
        } while (choice != 'X'); //terminates program if user choice is to Exit
    }

    //method displaying fleet inventory information
    public static void displayBoat(ArrayList<Boat>  boats) {
        int i;
        double totalP = 0, totalM = 0;
        System.out.printf("\nFleet report:\n");
        for(i = 0; i < boats.size(); i++ ){ //loop printing each boats' data
            System.out.print("    " + boats.get(i)); //prints data for boat at index i
            totalP += boats.get(i).getpPrice(); //incrementing the total purchasing price for all boats
            totalM += boats.get(i).getmPrice();//incrementing the total maintenance price for all boats
        }
        System.out.printf("    Total                                             : Paid $%9.2f : Spent : $%9.2f",
                totalP, totalM); //prints fleet's total
        // purchasing and maintenance price
        System.out.println("");
    }

    //method adding a boat to the fleet
    public static void addBoat(ArrayList<Boat> boats) {
        Boat newBoat;
        Boat.Type boatType;
        String data;

        System.out.printf("%-44s: ","Please enter the new boat CSV data ");
        data = keyboard.nextLine(); //getting boat data in csv format
        String[] boatData = data.split(","); //separating the line of boat data entered into separate pieces of data
        try { //exception checking correct data types were entered for boat info
            try { //exception checking all 6 boat data classes were entered
                if( checkBoatData(boatData[1], boatData[3], Integer.parseInt(boatData[4]), Double.parseDouble(boatData[5])) ) {
                    newBoat = new Boat(boatData);
                    boats.add(newBoat); //adds the boat to the fleet (the boat array list) if data entered is valid
                }
            }  catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid Data Entry - try again"); //error message printed if exception failed
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Data Entry - try again"); //error message printed if exception failed
        }
    }

    //method checking that boat data values entered adhere to the Commodore's policy
    public static boolean checkBoatData(String name, String make, int length, double cost ){
        if(name.length() > MAX_BOAT_NAME_LENGTH) { //checks boat name length
            return false;
        } else if(make.length() > MAX_MAKE_LENGTH) { //checks boat's make name length
            return false;
        } else if (length > MAX_LENGTH) { //checks boat length
            return false;
        } else if (cost > MAX_PPRICE) { //checks boats purchase price
            return false;
        }
        return true;
    }

    //method removing a boat from the fleet
    public static void removeBoat(ArrayList<Boat>  boats) {
        int i, found = -1;
        System.out.printf("%-44s: ", "Which boat do you want to remove? ");
        String name = keyboard.nextLine(); // gets the name of the boat to be removed from user
        for(i = 0; i < boats.size(); i++ ){ //searching boat ArrayList for matching name
            //checks if current boat name and name entered are the same
            if(boats.get(i).getName().toUpperCase().equals(name.toUpperCase())){
                found = i; // indicates the boat has been found
                boats.remove(i); //removes boat from arrayList if the boat is found to exist
            }
        }
        if(found == -1){ //if the boat name entered does not exist
            System.out.printf("Cannot find boat %s \n", name); //displays error message for invalid boat
        }
    }

    //method adding maintenance expenses to a specified boat in the fleet
    public static void addExpenses(ArrayList<Boat>  boats) {
        int i, found = -1;
        double spend = -1;
        System.out.printf("%-44s: ", "Which boat do you want to spend on? ");
        String name = keyboard.nextLine(); // gets the name of the boat to be spent on
        for (i = 0; i < boats.size(); i++) { //searching boat ArrayList for matching name
            //checks if current boat name and name entered are the same
            if (boats.get(i).getName().toUpperCase().equals(name.toUpperCase())) {
                found = 0; //indicates boat has been found
                System.out.printf("%-44s: ", "How much do you want to spend? ");
                try { //ensures amount to spend entered is a numeric value
                    spend = keyboard.nextDouble(); //gets valid amount to spend to maintain boat
                } catch (InputMismatchException e){ //prints error message if non-numeric data type entered
                    System.out.println("Invalid Input - Please enter a number");
                }
                keyboard.nextLine();
                //checks that amount to spend is valid for found boat
                if ((boats.get(i).getpPrice() > (boats.get(i).getmPrice() + spend) )&& (spend > 0)) {
                    boats.get(i).addExpense(spend); //adds the maintenance expense to the specified boat
                    System.out.printf("Expense Authorized, $%.2f spent. \n", boats.get(i).getmPrice());
                } else {
                    System.out.printf("Expense not permitted, only $%.2f left to spend. \n",
                            (boats.get(i).getpPrice() - boats.get(i).getmPrice()));
                    //if expense not allowed (amount spent too high), prints how much more money can be spent on boat
                }
            }
        }
        if(found == -1) {
            System.out.printf("Cannot find boat %s \n", name); //error message for invalid boat entry
        }
    }

    //method saving new fleet data entered in program to db file
    public static boolean saveBoats(String filename, ArrayList<Boat> boats) {
        ObjectOutputStream toStream = null;

        try {
            toStream = new ObjectOutputStream(new FileOutputStream(filename)); //creates ObjectOutputStream object
            toStream.writeObject(boats); // writes fleet ArrayList to OutputStream object
            return (true);
        } catch (IOException e) { //checks for errors saving the object
            System.out.println("ERROR saving " + e.getMessage());
            return (false);
        } finally {
            if (toStream != null) { //checks for error closing the file the object was written to
                try {
                    toStream.close();
                } catch (IOException e) {
                    System.out.println("ERROR closing " + e.getMessage());
                    return (false);
                }
            }
        }
    }

    //method reading/loading fleet data (including new data entered by user)
    public static ArrayList<Boat> loadBoat(String fileName) {

        ObjectInputStream fromStream = null;
        ArrayList<Boat> local;

        try {
            fromStream = new ObjectInputStream(new FileInputStream(fileName)); //creates InputStream object
            local = (ArrayList<Boat>)fromStream.readObject(); //gets array list of boats saved from file
        } catch (IOException e) { //checks for input/output errors loading the object
            System.out.println("ERROR loading " + e.getMessage());
            return(null);
        } catch (ClassNotFoundException e) { //return error message if class not found
            System.out.println(e.getMessage());
            return(null);
        } finally {
            if (fromStream != null) { //returns error message if there is an error closing the file
                try {
                    fromStream.close();
                } catch (IOException e) {
                    System.out.println("ERROR closing " + e.getMessage());
                    return(null);
                }
            }
        }
        return(local); //returns ArrayList of fleet boats read from file
    }

}
