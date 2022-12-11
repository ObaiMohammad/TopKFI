import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Entry implements Comparable <Entry> {
    private String id;
    private int frequency;

    private ArrayList<Integer> occurrence = new ArrayList<>();

    public Entry (String id, int frequency){
        this.frequency = frequency;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getFrequency() {
        return frequency;
    }
    public ArrayList<Integer> getOccurrence() {
        return occurrence;
    }

    public void addOccurrence(Integer index) {
        occurrence.add(index) ;
    }

    public void setOccurrence(ArrayList<Integer> occurrence) {
        this.occurrence = occurrence;
    }

    @Override
    public int compareTo(@NotNull Entry e) {
      if( this.frequency > e.getFrequency() ) return -1;
      else if (this.frequency < e.getFrequency()) return 1;
      return 0;
    }
    public String toSting(){
        return  id + " ("+frequency+")";

    }

}
