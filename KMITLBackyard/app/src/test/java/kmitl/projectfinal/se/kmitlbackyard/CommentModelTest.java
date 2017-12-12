package kmitl.projectfinal.se.kmitlbackyard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import kmitl.projectfinal.se.kmitlbackyard.model.CommentModel;

import static org.junit.Assert.assertEquals;

public class CommentModelTest {

    private CommentModel commentModel;

    @Before
    public void setUp() throws Exception {
        commentModel = new CommentModel();
    }

    @Test
    public void testGetSetContent() {
        commentModel.setContent("1234567890");
        assertEquals("1234567890", commentModel.getContent());
    }

    @Test
    public void testGetSetTimeStamp() {
        commentModel.setTimeStamp("2017/12/12 14:37:37");
        assertEquals("2017/12/12 14:37:37", commentModel.getTimeStamp());
    }

    @Test
    public void testGetSetUid() {
        commentModel.setUid("กันเอง นักเลงพอ");
        assertEquals("กันเอง นักเลงพอ", commentModel.getUid());
    }

    @Test
    public void testGetSetKey() {
        commentModel.setKey("1234567890");
        assertEquals("1234567890", commentModel.getKey());
    }

    @Test
    public void testGetSetUser_key() {
        commentModel.setUser_key("1234567890");
        assertEquals("1234567890", commentModel.getUser_key());
    }

    @After
    public void tearDown() throws Exception {
        commentModel = null;
    }

}