import org.jetbrains.annotations.NotNull;

public class Entry implements Comparable <Entry> {
    private String id;
    private int frequency;

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
