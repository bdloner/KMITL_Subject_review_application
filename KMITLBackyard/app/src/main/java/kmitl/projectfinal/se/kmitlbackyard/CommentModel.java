package kmitl.projectfinal.se.kmitlbackyard;

/**
 * Created by CPCust on 7/12/2560.
 */

public class CommentModel {
    private String content;
    private String timeStamp;
    private String uid;

    public CommentModel(String content,  String timeStamp, String uid) {
        this.content = content;

        this.timeStamp = timeStamp;
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
