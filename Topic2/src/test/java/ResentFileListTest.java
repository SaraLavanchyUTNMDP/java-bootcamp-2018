import org.junit.Assert;
import org.junit.Before;
import  org.junit.Test;
import java.util.Date;

public class ResentFileListTest {

    ResentFileList myList;

    @Before
    public void setUp() {
        myList= new ResentFileList();
    }

    @Test
    public void firstRun(){
        Assert.assertNotNull(myList);
        Assert.assertTrue(myList.getMyFilesList().isEmpty());
    }

    @Test
    public void openFile(){
        File myFile= new File(new Date().toInstant(), "myFile");
        myList.newOpenFile(myFile);
        Assert.assertTrue(myList.getMyFilesList().contains(myFile));
    }

    //note to Fino: i had to add some Millis because in some cases the test run so fast
    // that for my computer they run at the same Time.
    @Test
    public void openFileAgain(){
        File myFile= new File(new Date().toInstant(), "myFile");
        File otherFile = new File(new Date().toInstant(), "mySecondFile");
        myList.newOpenFile(otherFile);
        myList.newOpenFile(myFile);
        File myFileAgain = new File(new  Date().toInstant(), "myFile");
        myList.newOpenFile(myFileAgain);
        Assert.assertTrue(myList.getMyFilesList().contains(otherFile));
        Assert.assertEquals(myFileAgain, myList.getMyFilesList().get(myList.getMyFilesList().size()-1));
        Assert.assertEquals( 2, myList.getMyFilesList().size());
    }

    @Test
    public void fullStack(){
        File firstFile= new File(new Date().toInstant(), "myFile0");
        myList.newOpenFile(firstFile);
        for(int i=1; i<15; i++){
            File myFile= new File(new Date().toInstant().plusMillis(i*100), "myFile"+i);
            myList.newOpenFile(myFile);
        }
        Assert.assertEquals( 15, myList.getMyFilesList().size());
        File newFile = new File(new Date().toInstant().plusMillis(2000), "myFile16");
        myList.newOpenFile(newFile);
        Assert.assertTrue(myList.getMyFilesList().contains(newFile)
                && !myList.getMyFilesList().contains(firstFile));

    }

}
