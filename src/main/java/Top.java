import java.util.*;
import java.util.stream.Collectors;

public class Top {
    private int k;
    private int m;

    private ArrayList<int[]> purchase = new ArrayList<>();
    private ArrayList<Entry> solution = new ArrayList<>();
    private ArrayList<Integer> itemsID = new ArrayList<>();

    private ArrayList<String> pastItems = new ArrayList<>();
    private ArrayList<String> pastSequences = new ArrayList<>();
    private PriorityQueue<Entry> pQueue = new PriorityQueue<>();
    private HashMap<Integer, Entry> itemsMap = new HashMap<>();


    public Top(ArrayList<int[]> myItems, int k, int m) {

        this.k = k;
        this.m = m;
        this.purchase = myItems;
        initializeTopKFI(purchase);

    }


    public void printTopKFI() {
        ArrayList<Entry> s;
        s = TopKFI();

        if (s.size() <= m) {
            System.out.println(s.size());
            for (Entry e : s) {
                System.out.println(e.toSting());
            }
        } else System.out.println(s.size());
    }

    private ArrayList<Entry> TopKFI() {

        if ((pQueue.isEmpty())) {
            return solution;
        }
//        System.out.println("pQueue size is " + pQueue.size());
        Entry current = pQueue.remove();
//        System.out.println("Curr is: "+current.getId() + " Freq :" + current.getFrequency());

//        findSequences(current);
        findSequencesTest(current);
        solution.add(current);

        if (current.getItemSet().split(" ").length == 1){
            pastItems.add(current.getItemSet());
        }

        Entry nextItem = pQueue.peek();

        if ((solution.size() <= k - 1 || current.getFrequency() == nextItem.getFrequency())) {
            return TopKFI();
        }
        return solution;
    }

    private ArrayList<Entry> TopKFITest() {

        if ((pQueue.isEmpty())) {
            return solution;
        }
        Entry current = null;
        while (solution.size() < k) {
            current = pQueue.remove();
            findSequencesTest(current);
            solution.add(current);

            if (current.getItemSet().split(" ").length == 1){
                pastItems.add(current.getItemSet());
            }
        }

        Entry nextItem = pQueue.peek();

        while (current.getFrequency() == nextItem.getFrequency()) {
            nextItem = pQueue.peek();
            solution.add(current);

        }
        return solution;
    }

    private void initializeTopKFI(ArrayList<int[]> items) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < items.size(); i++) {
            int[] current = items.get(i);

            for (int j = 0; j < current.length; j++) {

                if (itemsMap.containsKey(current[j])) {
                    String id = String.valueOf(current[j]);
                    int freq = itemsMap.get(current[j]).getFrequency() + 1;
                    ArrayList<Integer> occ = itemsMap.get(current[j]).getOccurrence();

                    Entry entry = new Entry(id, freq);

                    entry.setOccurrence(occ);
                    entry.addOccurrence(i);
                    itemsMap.put(current[j], entry);

                } else {
                    String id = String.valueOf(current[j]);
                    int freq = 1;
                    Entry entry = new Entry(id, freq);
                    entry.addOccurrence(i);

                    itemsMap.put(current[j], entry);
                }
            }
        }
        itemsMap.forEach((key, value) -> {
            pQueue.add(value);
            itemsID.add(key);
        });
        Collections.sort(itemsID);
    }

    private int frequency(String sequence) {

        String[] seq = sequence.split("\\s");
        List<Integer> list1 = new ArrayList<>();
        list1.addAll(itemsMap.get(Integer.parseInt(seq[0])).getOccurrence());

        for (int i = 1; i < seq.length; i++) {
            List<Integer> list2 = new ArrayList<>();
            list2.addAll(itemsMap.get(Integer.parseInt(seq[i])).getOccurrence());
            list1 = listMatch(list1, list2);

        }
        return list1.size();
    }

    private List<Integer> listMatch(List<Integer> list1, List<Integer> list2) {

        int a = 0;
        int b = 0;
        List<Integer> list3 = new ArrayList<>();

        while (a < list1.size() && b < list2.size()) {

            if (list1.get(a) > list2.get(b)) {
                b++;

            } else if (list1.get(a) < list2.get(b)) {
                a++;
            } else {
                list3.add(list1.get(a));
                a++;
                b++;
            }
        }
        return list3;
    }

    private void findSequences(Entry entry) {

        String[] mySequence = entry.getItemSet().split(" ");

        int lastSeqNumber = Integer.parseInt(mySequence[mySequence.length - 1]);
        int currIndex = Collections.binarySearch(itemsID, lastSeqNumber);

        if (currIndex == itemsID.size() - 1) return;

        for (int i = currIndex + 1; i < itemsID.size(); i++) {
            Entry currEntry = itemsMap.get(itemsID.get(i));
            if (solution.size() > k && entry.getFrequency() > currEntry.getFrequency()) continue;

            String sequence = entry.getItemSet() + " " + itemsID.get(i);

            int freq = frequency(sequence);
            if (solution.size() < k) {
                int a = solution.size();
                int b = k - a;
                int trust = pQueue.size() < b ? 1 : freq;
                if (freq >= trust) pQueue.add(new Entry(sequence, freq));

            }
        }
    }

    private void findSequencesTest(Entry entry) {

        ArrayList <String> mySequence = new ArrayList<>(Arrays.asList( entry.getItemSet().split(" "))) ;

        for (int i = 0 ; i <pastItems.size();i++){

            if (mySequence.contains(pastItems.get(i))) continue;
            ArrayList <String> curSequence = new ArrayList<>();
            curSequence.addAll(mySequence);
            curSequence.add(pastItems.get(i));
            Collections.sort(curSequence);
            String sequence = String.join(" ", curSequence);
            if (pastSequences.contains(sequence)) continue;;
            pastSequences.add(sequence);
            int freq = frequency(sequence);
            if (solution.size() < k) {
                int a = solution.size();
                int b = k - a;
                int trust = pQueue.size() < b ? 1 : freq;
                if (freq >= trust) pQueue.add(new Entry(sequence, freq));
            }
        }




        }


    }




