import sun.awt.geom.AreaOp;

import java.util.*;

public class ResentFileList{
    private List<File> myFilesList;

    public ResentFileList(){
        myFilesList = new ArrayList<>();
        Comparator<File> groupByComparator = Comparator.comparing(File::getDate)
                .thenComparing(File::getFile);
        myFilesList.sort(groupByComparator);
    }

    public List<File> getMyFilesList(){
        return myFilesList;
    }


    
    public void newOpenFile(File myFile) {
        File fileToRemove = null;
        if(myFilesList.size() != 15) {
            for(File aFile : myFilesList){
                if(aFile.getFile() == myFile.getFile())
                    fileToRemove = aFile;
            }
            myFilesList.remove(fileToRemove);
            myFilesList.add(myFile);

        }else{
            myFilesList.remove(myFilesList.get(0));
            myFilesList.add(myFile);
        }
    }
}
