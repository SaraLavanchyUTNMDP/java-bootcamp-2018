import java.util.*;

public class ResentFileList{
    List<String> myList;

    public ResentFileList(){
        myList = new ArrayList<>();
    }

    public List<String> getMyList(){
        return myList;
    }

    public void newOpenFile(String string) {
        if(myList.size() != 15) {
            if (this.myList.contains(string)) {
                myList.add(string);
                myList.remove(myList.lastIndexOf(string));
            } else {
                myList.add(string);
            }
        }else{
            myList.remove(0);
            myList.add(string);
        }
    }
}
