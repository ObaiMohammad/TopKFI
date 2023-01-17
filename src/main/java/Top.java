import java.util.*;
import java.util.stream.Collectors;

public class Top {
    private int k;
    private int m;

    private ArrayList<Transaction> purchase ;
    private ArrayList<Entry> solution = new ArrayList<>();
    private ArrayList<String> pastItems = new ArrayList<>();
    private ArrayList<String> pastSequences = new ArrayList<>();
    private PriorityQueue<Entry> pQueue = new PriorityQueue<>();
    private HashMap<Integer, Entry> itemsMap = new HashMap<>();


    public Top(ArrayList<Transaction> myItems, int k, int m) {

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
        Entry current = pQueue.remove();

        findSequences(current);
        solution.add(current);

        if (current.getItemSet().split("\\s").length == 1){
            pastItems.add(current.getItemSet());
        }

        Entry nextItem = pQueue.peek();

        if ((solution.size() <= k - 1 || current.getFrequency() == nextItem.getFrequency())) {
            return TopKFI();
        }
        return solution;
    }

    private void initializeTopKFI(ArrayList<Transaction> items) {

        for (int i = 0; i < items.size(); i++) {
            int[] current = items.get(i).toArray();

            for (int value : current) {

                if (itemsMap.containsKey(value)) {
                    String id = String.valueOf(value);
                    int freq = itemsMap.get(value).getFrequency() + 1;
                    ArrayList<Integer> occ = itemsMap.get(value).getOccurrence();
                    occ.add(i);

                    Entry entry = new Entry(id, freq, occ);

                    itemsMap.put(value, entry);

                } else {
                    String id = String.valueOf(value);
                    int freq = 1;
                    ArrayList<Integer> occ = new ArrayList<>();
                    occ.add(i);
                    Entry entry = new Entry(id, freq, occ);

                    itemsMap.put(value, entry);
                }
            }
        }
        pQueue.addAll(itemsMap.values());
    }

    private int frequency(String sequence) {

        String[] seq = sequence.split("\\s");
        List<Integer> list1 = new ArrayList<>(itemsMap.get(Integer.parseInt(seq[0])).getOccurrence());

        for (int i = 1; i < seq.length; i++) {
            List<Integer> list2 = new ArrayList<>(itemsMap.get(Integer.parseInt(seq[i])).getOccurrence());
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

        ArrayList <String> mySequence = new ArrayList<>(Arrays.asList( entry.getItemSet().split("\\s"))) ;

        for (String pastItem : pastItems) {

            if (mySequence.contains(pastItem)) continue;
            ArrayList<String> curSequence = new ArrayList<>(mySequence);
            curSequence.add(pastItem);
            Collections.sort(curSequence);
            String sequence = String.join(" ", curSequence);

            if (pastSequences.contains(sequence)) continue;

            pastSequences.add(sequence);
            int freq = frequency(sequence);

            if (freq > 0) pQueue.add(new Entry(sequence, freq));
        }
    }
}




