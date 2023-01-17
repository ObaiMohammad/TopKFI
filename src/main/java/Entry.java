
import java.util.ArrayList;

public class Entry implements Comparable <Entry> {
    private String itemSet;
    private int frequency;

    private ArrayList<Integer> occurrence = new ArrayList<>();

    public Entry (String itemSet, int frequency){
        this.frequency = frequency;
        this.itemSet = itemSet;
    }
    public Entry (String itemSet, int frequency,ArrayList<Integer> occurrence){
        this.frequency = frequency;
        this.itemSet = itemSet;
        this.occurrence = occurrence;
    }

    public String getItemSet() {
        return itemSet;
    }

    public int getFrequency() {
        return frequency;
    }
    public ArrayList<Integer> getOccurrence() {
        return occurrence;
    }


    @Override
    public int compareTo( Entry e) {
        if( this.frequency > e.getFrequency() ) return -1;
        else if (this.frequency < e.getFrequency()) return 1;
        return 0;
    }
    public String toSting(){
        return  itemSet + " ("+frequency+")";

    }

}
