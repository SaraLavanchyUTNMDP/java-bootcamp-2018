import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import  org.junit.Test;

import java.util.Stack;

public class ResentFileListTest {

    ResentFileList myList;

    @Before
    public void setUp() {
        myList= new ResentFileList();
    }

    @Test
    public void firstRun(){
        Assert.assertNotNull(myList);
        Assert.assertTrue(myList.getMyList().isEmpty());
    }

    @Test
    public void openFile(){
        String myFile= "myFile";
        myList.newOpenFile(myFile);
        Assert.assertTrue(myList.getMyList().contains(myFile));
    }

    @Test
    public void openFileAgain(){
        String myFile= "myFile";
        myList.newOpenFile(myFile);
        String otherFile = "otherFile";
        myList.newOpenFile(otherFile);
        myList.newOpenFile(myFile);
        myList.newOpenFile(otherFile);
        Assert.assertTrue(myList.getMyList().contains(otherFile));
        Assert.assertEquals(otherFile, myList.getMyList().get(myList.getMyList().size()-1));
        Assert.assertEquals(myList.getMyList().size(), 2);
    }

    @Test
    public void fullStack(){
        for(int i=0; i<15; i++){
            String file = "file"+i;
            myList.newOpenFile(file);
        }
        Assert.assertEquals(myList.getMyList().size(), 15);
        myList.newOpenFile("file 16");
        Assert.assertTrue(myList.getMyList().contains("file 16") && !myList.getMyList().contains("file0"));

    }

}
