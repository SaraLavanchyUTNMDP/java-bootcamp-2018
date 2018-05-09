import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MyBlogTest {
    @Mock private Posts myPost;
    @Mock private List myBlog;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(Posts);
        MockitoAnnotations.initMocks(List);
    }



    @Test
    public void newEntry(){
        when(myPost.newPost("algun texto")).then(myBlog.add(algunTexto));
        Assert.assertEquals(myBlog.get(myBlog.size()-1),"algun texto");
    }
}
}
