package com.company.OOPs;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

class ParkingDetails{
    String vehicleNumber;
    String parkingTime;
    ParkingDetails(){}
    ParkingDetails(String vn){
        vehicleNumber = vn;
        parkingTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}

class Parking{
    ParkingDetails[][] parking;
    int floors;
    int capacity;
    int[] isFull;

    Parking(){}
    Parking(int floor, int seat){
        parking = new ParkingDetails[floor][seat];
        isFull = new int[floor];
        capacity = seat;
        floors = floor;
    }


    void park(String vehicleNumber){
        int floor = 0;
        for(floor = 0; floor < floors; floor++){
            if(isFull[floor] != capacity){
                break;
            }
        }

        if(floor == floors){
            System.out.println(vehicleNumber + " Vehicle parked in ground");
        }else{
            for(int slot = 0; slot < capacity; slot++){
                if(parking[floor][slot] == null){
                    parking[floor][slot] = new ParkingDetails(vehicleNumber);
                    isFull[floor]++;
                    System.out.println(vehicleNumber + " parked on floor " + (floor+1) + " and slot " + (slot+1) );
                    break;
                }
            }
        }
    }

    void parkUndo(String vehicleNumber){
        int floor = 0;
        int slot = 0;

        for(floor = 0; floor < floors; floor++){
            for(slot = 0; slot < capacity; slot++){
                if(parking[floor][slot] != null){
                    ParkingDetails pd = parking[floor][slot];
                    if(pd.vehicleNumber.equals(vehicleNumber)){
                        int time = timeDifferenceInHours(pd);
                        printBill(pd, time);
                        parking[floor][slot] = null;
                        isFull[floor]--;
                        return;
                    }
                }
            }
        }

        System.out.println(vehicleNumber + " Vehicle not found. Check on the ground it might be there.");
    }

    int timeDifferenceInHours(ParkingDetails pd){
        String currTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            Date d1 = sdf.parse(pd.parkingTime);
            Date d2 = sdf.parse(currTime);

            long diff = d2.getTime() - d1.getTime();
            long difference_In_Hours = (diff / (1000 * 60 * 60))% 24;

            return (int)difference_In_Hours;
        }catch (Exception e){
            e.printStackTrace();
        }

        return -1;
    }

    void printBill(ParkingDetails pd, int hour){
        if(hour < 1){
            System.out.println(pd.vehicleNumber + " charge is 0 rs");
        }else if(hour < 3){
            System.out.println(pd.vehicleNumber + " charge is " + hour*15 + " rs");
        }else{
            System.out.println(pd.vehicleNumber + " charge is " + hour * 30 + " rs");
        }
    }




}
class TwoWheeler extends Parking{

    TwoWheeler(int n, int x){
        super(n,x);
    }

}

class FourWheeler extends Parking{

    FourWheeler(int m, int y){
        super(m, y);
    }

}

class ParkingOperation{
    TwoWheeler twoWheeler;
    FourWheeler fourWheeler;

    ParkingOperation(int n, int x, int m, int y){
        twoWheeler = new TwoWheeler(n, x);
        fourWheeler = new FourWheeler(m, y);
    }

    void park(String vehicleNumber, boolean isTwoWheeler){
        if(isTwoWheeler){
            twoWheeler.park(vehicleNumber);
        }else{
            fourWheeler.park(vehicleNumber);
        }
    }

    void parkUndo(String vehicleNumber, boolean isTwoWheeler){
        if(isTwoWheeler){
            twoWheeler.parkUndo(vehicleNumber);
        }else{
            fourWheeler.parkUndo(vehicleNumber);
        }
    }
}


public class ParkingSystem {

    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {

        ParkingOperation parkingOperation = new ParkingOperation(2,1,2,1);
        boolean flag = true;
        while(flag){
            System.out.println("Options : ");
            System.out.println("1 : park vehicle" );
            System.out.println("2 : Take vehicle");
            System.out.println("3 : Stop the parking system");
            int option = scan.nextInt();

            System.out.println("Press 1 if two wheeler other wise press 2");
            int type = scan.nextInt();
            scan.nextLine();
            System.out.println("Enter vehicle number");
            String vehicleNumber = scan.nextLine();

            switch (option){
                case 1 :
                    if(type == 1){
                        parkingOperation.park(vehicleNumber, true);
                    }else if(type == 2){
                        parkingOperation.park(vehicleNumber, false);
                    }else{
                        System.out.println("Enter a correct vehicle type");
                    }
                    break;

                case 2 :
                    if(type == 1){
                        parkingOperation.parkUndo(vehicleNumber, true);
                    }else if(type == 2){
                        parkingOperation.parkUndo(vehicleNumber, false);
                    }else{
                        System.out.println("Enter a correct vehicle type");
                    }

                    break;
                case 3 : flag = false;
                break;

                default:
                    System.out.println("Enter an valid option");
            }
        }
    }
}
