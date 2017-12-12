package kmitl.projectfinal.se.kmitlbackyard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import kmitl.projectfinal.se.kmitlbackyard.model.PostModel;

import static org.junit.Assert.assertEquals;

public class PostModelTest {

    private PostModel postModel;

    @Before
    public void setUp() throws Exception {
        postModel = new PostModel();
    }

    @Test
    public void testGetSetDescription() {
        postModel.setDescription("1234567890");
        assertEquals("1234567890", postModel.getDescription());
    }

    @Test
    public void testGetSetScore() {
        postModel.setScore("5");
        assertEquals("5", postModel.getScore());
    }

    @Test
    public void testGetSetScore_num() {
        postModel.setScore_num("3");
        assertEquals("3", postModel.getScore_num());
    }

    @Test
    public void testGetSetSubject_id() {
        postModel.setSubject_id("0601348");
        assertEquals("0601348", postModel.getSubject_id());
    }

    @Test
    public void testGetSetTimeStamp() {
        postModel.setTimeStamp("2017/12/12 14:37:37");
        assertEquals("2017/12/12 14:37:37", postModel.getTimeStamp());
    }

    @Test
    public void testGetSetTitle() {
        postModel.setTitle("Test Review");
        assertEquals("Test Review", postModel.getTitle());
    }

    @Test
    public void testGetSetUid() {
        postModel.setUid("กันเอง นักเลงพอ");
        assertEquals("กันเอง นักเลงพอ", postModel.getUid());
    }

    @Test
    public void testGetSetPost_id() {
        postModel.setPost_id("9876543210");
        assertEquals("9876543210", postModel.getPost_id());
    }

    @Test
    public void testGetSetUser_key() {
        postModel.setUser_key("147258369");
        assertEquals("147258369", postModel.getUser_key());
    }

    @Test
    public void testGetSetSubjectSelect() {
        postModel.setSubjectSelect("1234567890");
        assertEquals("1234567890", postModel.getSubjectSelect());
    }

    @Test
    public void testGetSetType() {
        postModel.setType("1234567890");
        assertEquals("1234567890", postModel.getType());
    }

    @After
    public void tearDown() throws Exception {
        postModel = null;
    }

}