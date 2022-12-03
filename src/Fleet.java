import java.util.*;
import java.util.Scanner;
public class Fleet {
    private static final Scanner keyboard = new Scanner(System.in);
    //private Boat[] boats;
    ArrayList<Boat> boats = new ArrayList<>();

    public Fleet() {
        ArrayList<Boat> boats = new ArrayList<>();
    }

    public Fleet(Boat temp) {
        ArrayList<Boat> boats = new ArrayList<>();
        boats.add(temp);
    }

    public void display() {
        int i;
        System.out.println("Fleet report:");
        for(i = 0; i < boats.size(); i++ ){
            System.out.println(boats.get(i));
        }
    }

    public void addBoat() {
        Boat temp;

        System.out.println("Please enter the new boat CSV data : ");
    }

    public void removeBoat() {
        int i;
        System.out.println("Which boat do you want to remove?  : ");
        String name = keyboard.next();
        for(i = 0; i < boats.size(); i++ ){
            if(boats.get(i).getName()==(name)){
                boats.remove(i);
            } else {
                System.out.printf("Cannot find boat %s", name);
            }
        }
    }

    public void expenses() {
        int i;
        System.out.println("Which boat do you want to spend on?  : ");
        String name = keyboard.next();
        for(i = 0; i < boats.size(); i++ ) {
            if(boats.get(i).getName()==(name)) {
                System.out.println("How much do you want to spend?  : ");
                double spend = keyboard.nextDouble();
                if(boats.get(i).getpPrice()>spend) {
                    boats.get(i).addExpense(spend);
                    System.out.printf("Expense Authorized, $%.2f spent", boats.get(i).getmPrice());
                } else {
                    System.out.printf("Expense not permitted, only $%.2f left to spend", (boats.get(i).getpPrice() - boats.get(i).getmPrice()) );
                }
            } else {
                System.out.printf("Cannot find boat %s", name);
            }
        }



    }
}