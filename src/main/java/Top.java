import java.util.*;

public class Top {
    private int k;
    private int m;

    private  ArrayList <int []> items = new ArrayList<>();
    private ArrayList<Entry> S = new ArrayList<>();
    private ArrayList<Integer> I = new ArrayList<>();
    private PriorityQueue<Entry> pQueue = new PriorityQueue<>();


    public Top (ArrayList <int []> myItems,int k, int m) {
        boolean done = false;
        while (!done) {

            if (k < 0 && m < 0) {
                throw new IllegalArgumentException("Please inter a positive value for K and M ");
            }
            this.k = k-1;
            this.m = m;
            this.items = myItems;
            initializeTopKFI(items);
//            System.out.print(printArrayList(I));
//            System.out.println(pQueue.peek().getId());
            done = true;
        }
    }

    public ArrayList<Entry> TopKFI (){
        Entry current= pQueue.remove();
        System.out.println(current.getId() + " Freq :" + current.getFrequency());
        Entry nextItem = pQueue.peek();
//        System.out.println(nextItem.getId() + " F :" + nextItem.getFrequency());
        if ((pQueue.isEmpty())){
            return S;
        }
        if ((S.size() > k && current.getFrequency() != nextItem.getFrequency())){
            return S;
        } else {
            S.add(current);
//            findSequences ( pQueue, I ,current );
            return TopKFI();
        }

    }

    private void initializeTopKFI(ArrayList <int []>  items){
        HashMap <Integer,Integer> myMap = new HashMap<>();
        for (int i = 0; i < items.size();i++){
            int [] current = items.get(i);
            for (int j = 0; j < current.length;j++){
                if (myMap.containsKey(current[j])){
                    myMap.put(current[j],myMap.get(current[j])+1);
                }
                else myMap.put(current[j],1);
            }
        }

        myMap.forEach((k,v)-> { pQueue.add(new Entry(k.toString(),v));
            I.add(k);});
    }
    private static int frequency (ArrayList<int[]> list,String sequence){
        int occurrences = 0 ;

        for ( int i = 0 ; i < list.size();i++){

            if (isPresent (list.get(i),sequence)){
                occurrences++;
            }
        }

        return occurrences;
    }

    private static boolean isPresent(int[] myArray, String sequence) {

        Scanner in = new Scanner(sequence);
        String item = in.next();
        int itemId = Integer.parseInt(item);
        int itemLength = item.length();

        if (myArray [0] > itemId){
            return false;
        }
        if (Arrays.binarySearch(myArray, itemId) >= 0 ){
            if (!sequence.contains(" ")){
                return true;
            }
            return isPresent(myArray,sequence.substring(sequence.indexOf(' ') + 1));
        }
        return false;
    }

    private static String  printArrayList (ArrayList<Integer> myArray){
        String myList="" ;
        for (int i = 0; i < myArray.size();i++){
            myList =  myList +myArray.get(i)+" , ";
        }
        return myList;
    }

    private void findSequences ( PriorityQueue<Entry> pQueue,List <Integer> I ,Entry current ){

        String [] mySequence = current.getId().split(" ");
        int lastSeqNumber = Integer.parseInt(mySequence[mySequence.length-1]);
        int currIndex = I.indexOf(lastSeqNumber);

        if (currIndex + 1 < I.size()) {

            List<Integer> greaterItems = I.subList(currIndex + 1, I.size());
            for (Integer item : greaterItems) {

                if (item > lastSeqNumber) {

                    String  sequence = current.getId() + " " + item;

                    int frequency = frequency(items, sequence);

//                    System.out.println(sequence + " ( " + frequency+" )");

                    if (frequency > 0) pQueue.add(new Entry(sequence, frequency));
                }
            }
        }
    }



}
