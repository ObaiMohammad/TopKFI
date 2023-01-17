
public class Transaction {
    private Integer [] trans;

    public Transaction (Integer [] items){
        trans = items;
    }

    public int get(int index){
        return trans [index];
    }

}
