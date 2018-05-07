import java.util.*;

public class ResentFileList{
    private SortedSet<File> myFilesList;
    private Comparator<File> byDate =
            Comparator.comparing(File::getDate);

    public ResentFileList(){
        myFilesList = new TreeSet<>(byDate);
    }

    public SortedSet<File> getMyFilesList(){
        return myFilesList;
    }


    
    public void newOpenFile(File myFile) {
        if(myFilesList.size() != 15) {
            for(File aFile : myFilesList){
                if(myFile.getFile() == aFile.getFile()){
                    myFilesList.remove(aFile);
                }
            }
            myFilesList.add(myFile);
        }else{
            myFilesList.remove(myFilesList.first());
            myFilesList.add(myFile);
        }
    }
}
