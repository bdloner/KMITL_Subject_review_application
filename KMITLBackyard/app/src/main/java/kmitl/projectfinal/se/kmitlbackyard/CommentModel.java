package kmitl.projectfinal.se.kmitlbackyard;

/**
 * Created by CPCust on 7/12/2560.
 */

public class CommentModel {
    private String content;
    private String post_id;
    private String timeStamp;
    private String uid;

    public CommentModel(String content, String post_id, String timeStamp, String uid) {
        this.content = content;
        this.post_id = post_id;
        this.timeStamp = timeStamp;
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
