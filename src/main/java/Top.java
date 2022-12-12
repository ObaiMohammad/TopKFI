import java.util.*;
import java.util.stream.Collectors;

public class Top {
    private int k;
    private int m;

    private  ArrayList <int []> items = new ArrayList<>();
    private ArrayList<Entry> solution = new ArrayList<>();
    private ArrayList<Integer> avlItems = new ArrayList<>();
    private PriorityQueue<Entry> pQueue = new PriorityQueue<>();
    private HashMap <Integer,Entry> itemsMap = new HashMap<>();



    
    public Top (ArrayList <int []> myItems,int k, int m) {
        boolean done = false;
        while (!done) {

            if (k < 0 && m < 0) {
                throw new IllegalArgumentException("Please inter a positive value for K and M ");
            }
            this.k = k;
            this.m = m;
            this.items = myItems;
            initializeTopKFI(items);
//            System.out.print(printArrayListInt(I));
//            System.out.println(pQueue);
            done = true;
        }
    }


    public void printTopKFI (){
        ArrayList<Entry> s;
        s = TopKFI();
        if ( s.size() <= m){
            System.out.println(s.size());
            for (Entry e: s ){
                System.out.println(e.toSting());

            }
        } else System.out.println(s.size());


    }

    private ArrayList<Entry> TopKFI (){

        if ((pQueue.isEmpty())){
            return solution;
        }
        System.out.println("pQueue size is " + pQueue.size());


        Entry current= pQueue.remove();
//        System.out.println("Curr is: "+current.getId() + " Freq :" + current.getFrequency());

//        ArrayList<Integer> occ =  current.getOccurrence();
//        System.out.println( "occurred at "+ printArrayListInt(occ));


        findSequences ( pQueue, avlItems,current );
        solution.add(current);
        System.out.println("Top item is "+current.toSting());


        Entry nextItem = pQueue.peek();
//        System.out.println("Next is: "+nextItem.getId() + " F :" + nextItem.getFrequency());


        if ((solution.size() <= k-1 || current.getFrequency() == nextItem.getFrequency())){
            return TopKFI();
        }

        return solution;

    }

    private void initializeTopKFI(ArrayList <int []>  items){
        for (int i = 0; i < items.size();i++){
            int [] current = items.get(i);

            for (int j = 0; j < current.length;j++){

                if (itemsMap.containsKey(current[j])){
                    String  id = String.valueOf(current[j]);
                    int freq = itemsMap.get(current[j]).getFrequency()+1;
                    ArrayList<Integer> occ =  itemsMap.get(current[j]).getOccurrence();

                    Entry entry = new Entry(id,freq);
                    
                    entry.setOccurrence(occ);
                    entry.addOccurrence(i);

//                    myMap.put(current[j],myMap.get(current[j])+1);
                    itemsMap.put(current[j],entry);

                } else{
                    String  id = String.valueOf(current[j]);
                    int freq = 1;
                    Entry entry = new Entry(id,freq);
                    entry.addOccurrence(i);

                    itemsMap.put(current[j],entry);
                }
            }
        }

        itemsMap.forEach((k,v)-> { pQueue.add(v);
            avlItems.add(k);});
    }
    private  int frequency (ArrayList<int[]> list,String sequence){
        int occurrences = 0 ;

        for ( int i = 0 ; i < list.size();i++){

            if (isPresent (list.get(i),sequence)){
                occurrences++;
            }
        }

        return occurrences;
    }

    private  boolean isPresent(int[] purchases, String sequence) {

        Scanner in = new Scanner(sequence);
        String item = in.next();
        int itemId = Integer.parseInt(item);
        int itemLength = item.length();

        if (purchases [0] > itemId){
            return false;
        }
        if (Arrays.binarySearch(purchases, itemId) >= 0 ){
            if (!sequence.contains(" ")){
                return true;
            }
            return isPresent(purchases,sequence.substring(sequence.indexOf(' ') + 1));
        }
        return false;
    } 
    private  int frequencyTest(String sequence) {

        String [] seq = sequence.split("\\s");

        List<Integer> list1 = new ArrayList<>();
        list1.addAll(itemsMap.get(Integer.parseInt(seq[0])).getOccurrence());

        for (int i = 1; i < seq.length;i++){
            List<Integer> list2 = new ArrayList<>();
            list2.addAll(itemsMap.get(Integer.parseInt(seq[i])).getOccurrence());

              list1 = list1.parallelStream().filter(list2::contains).collect(Collectors.toList());
//            list3.retainAll(list2);


        }
        return list1.size();
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

//                    int freq = frequency(items, sequence);
                    int freq = frequencyTest(sequence);

//                    System.out.println(sequence + " ( " + frequency+" )");
                    if (solution.size() < k){
                        int i = solution.size();
                        int j = k-i;
                        int trust = pQueue.size() < j ? 1:freq;
                        if (freq >= trust) pQueue.add(new Entry(sequence, freq));
                    }
                }
            }
        }
    }

    private static String  printArrayListEntry (ArrayList<Entry> myArray){
        String myList ="";
        for (int i = 0; i < myArray.size();i++){
            myList =  myList +myArray.get(i).getId()+" ("+myArray.get(i).getFrequency()+")\n";
        }

        return myList;
    }
    private static String printArrayListInt(ArrayList<Integer> myArray){
        String myList="" ;
        for (int i = 0; i < myArray.size();i++){
            myList =  myList +myArray.get(i)+" , ";
        }
        return myList;
    }


}
