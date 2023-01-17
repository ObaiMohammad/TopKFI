
import java.util.Iterator;

public class Transaction  {
    private int [] trans;

    public Transaction (int [] items){
        trans = items;
    }

    public int [] toArray(){
        return trans;
    }

}
