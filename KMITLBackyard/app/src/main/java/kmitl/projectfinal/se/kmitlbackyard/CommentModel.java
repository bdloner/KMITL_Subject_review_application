package kmitl.projectfinal.se.kmitlbackyard;

public class CommentModel {
    private String content;
    private String timeStamp;
    private String uid;
    private String key;
    private String user_key;

    public CommentModel(String content,  String timeStamp, String uid, String key, String user_key) {
        this.content = content;
        this.key = key;
        this.timeStamp = timeStamp;
        this.uid = uid;
        this.user_key = user_key;
    }

    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
