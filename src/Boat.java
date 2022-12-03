import java.io.*;
public class Boat implements Serializable {
    public static enum Type {SAILING, POWER, UNKNOWN};
    private Type boatType;
    private String name, makeModel;
    private int yearMan, length;
    private double pPrice, mPrice;

    //default Boat object constructor
    public Boat() {
        boatType = null;
        name = null;
        makeModel = null;
        yearMan = 0;
        length = 0;
        pPrice = 0.0;
        mPrice = 0.0;
    }


    //Boat object constructor for when line of csv boat data is passed as argument
    public Boat(String [] boatInfo) {
        boatType = parseType(boatInfo[0]);
        name = boatInfo[1];
        makeModel = boatInfo[3];
        yearMan = Integer.parseInt(boatInfo[2]);
        length = Integer.parseInt(boatInfo[4]);
        pPrice = Double.parseDouble(boatInfo[5]);
        mPrice = 0.0;
    }

    //changes the boat type in csv file from string data type to enum Type data type
    public Type parseType(String type){
        switch(type.toUpperCase()) { //checks the string boat type written in csv data
            case "POWER":
                boatType = Boat.Type.POWER; //saves as POWER boat type
                break;
            case "SAILING":
                boatType = Boat.Type.SAILING; //saves as SAILING boat type
                break;
            default:
                boatType = Boat.Type.UNKNOWN; //if invalid type entered, saved as UNKNOWN boat type
                break;
        }
        return boatType;
    }

    //data output formatting method to neatly display boat info
    public String toString() {
        return(String.format("%-7s %-20s %d %-11s %d' : Paid $%9.2f : Spent : $%9.2f \n",
                boatType.toString(), name, yearMan, makeModel, length, pPrice, mPrice));
    }

    //method finding boat purchase price
    public double getpPrice() {
        return pPrice;
    }

    //method finding current boat maintenance price
    public double getmPrice() {
        return mPrice;
    }

    //method finding boat name
    public String getName() {
        return name;
    }

    //method adding maintenance expense to boat's current maintenance expense
    public void addExpense(double amt) {
        mPrice += (amt);
    }
}