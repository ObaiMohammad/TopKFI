// This file is a template for the project of Dati e Algoritmi AA 2022-23

// You are free to modify this code, but do not use any library outside of
// java.io and java.util. Check carefully the input and output format,
// use the one described in the project specifications otherwise we may not
// be able to test and evaluate your submission!

// The following code reads the input dataset line by line, parsing the items in each transaction.
// Before the submission, disable any debug output (for example, setting the variable
// DEBUG to false) and only output the results as described in the project specifications.

import java.io.*;
import java.util.*;

public class TopKFI{

    public static boolean DEBUG = false;

    public static void main(String args[]){

//        // parse input arguments
//        if(args.length != 3){
//            System.out.println("The arguments are not correct!");
//            System.out.println("Please use \njava TopKFI datasetpath K M");
//            return;
//        }

//		String db_path = args[0];
//		int K = Integer.parseInt(args[1]);
//		int M = Integer.parseInt(args[2]);
//
        String db_path = "D:\\College\\Italy\\CS\\Data Strucutres\\Homeworks\\project\\datasets\\retail_small.dat";
        int K = 7;
        int M = 5;
        ArrayList < int []> items = new ArrayList<>();


        if(K < 0 || M < 0){
            System.out.println("K and M should be positive!");
            return;
        }

        if(DEBUG){
            System.out.println("Path to dataset: "+db_path);
            System.out.println("K: "+K);
            System.out.println("M: "+M);
        }

        // read the input file
        try {
            File file_db = new File(db_path);
            Scanner db_reader = new Scanner(file_db);
            int transaction_id = 0;
            while (db_reader.hasNextLine()) {
                transaction_id++;
                String transaction = db_reader.nextLine();
                if(DEBUG){
                    System.out.println("transaction "+transaction_id+" is "+transaction);
                }
                String[] items_str = transaction.split("\\s+");
                int [] items_int = new int[items_str.length];

                /* read the transaction "items_str" into the array "items" */
                for (int i = 0; i < items_str.length; i++ ) {
                    try {
                        items_int [i] = Integer.parseInt(items_str[i]);

                        if (DEBUG) {
                            System.out.println("  item " +items_int[i]);
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Input format of transaction is wrong!");
                        System.out.println("transaction " + transaction_id + " is " + transaction);
                        e.printStackTrace();
                        return;
                    }
                }
                items.add(items_int);

                /* do something with the array "items" ... */
//                for (int i = 0; i < items.size();i++){
//                    System.out.println(printArrayList(items.get(i)));
//                }

            }

            db_reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("The file "+db_path+" does not exist!");
            e.printStackTrace();
            return;
        }

        ArrayList<Entry> s = new ArrayList<>();
        Top myTop = new Top(items,K,M);
           s = myTop.TopKFI();
       System.out.println(printArrayList(s));


        /* start mining itemsets... */

    }


    private static String  printArrayList (ArrayList<Entry> myArray){
        String myList ="";
       for (int i = 0; i < myArray.size();i++){
           myList =  myList +myArray.get(i).getId()+" ("+myArray.get(i).getFrequency()+")\n";
       }

        return myList;
    }





}
