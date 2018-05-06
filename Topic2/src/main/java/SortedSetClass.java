import java.util.*;

public class SortedSetClass {
    SortedSet<String> myList;

    public SortedSetClass(){
        myList = new TreeSet<String>();
    }

    public SortedSet<String> getMyList(){
        return myList;
    }

    public void newOpenFile(String string) {
        if(myList.size() != 15) {
            if (this.myList.contains(string))
                myList.remove(string);
            else {
                myList.add(string);
            }
        }

    }
}
