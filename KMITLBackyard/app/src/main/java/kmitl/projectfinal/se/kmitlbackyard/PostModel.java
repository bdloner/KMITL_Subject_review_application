package kmitl.projectfinal.se.kmitlbackyard;

/**
 * Created by CPCust on 5/12/2560.
 */

public class PostModel {
    private String description;
    private String score;
    private String score_num;
    private String subject_id;
    private String timeStamp;
    private String title;
    private String uid;
    private String post_id;
    private String post_liked ;
    private String viewer;
    private String user_key;

    public PostModel(String description,String score, String score_num, String subject_id, String timeStamp, String title, String uid, String post_id, String post_liked, String viewer, String user_key) {
        this.description = description;
        this.post_id = post_id;
        this.score = score;
        this.score_num = score_num;
        this.subject_id = subject_id;
        this.timeStamp = timeStamp;
        this.title = title;
        this.uid = uid;
        this.post_liked = post_liked;
        this.viewer = viewer;
        this.user_key = user_key;
    }

    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public String getViewer() {
        return viewer;
    }

    public void setViewer(String viewer) {
        this.viewer = viewer;
    }

    public String getPost_liked() {
        return post_liked;
    }

    public void setPost_liked(String post_liked) {
        this.post_liked = post_liked;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScore_num() {
        return score_num;
    }

    public void setScore_num(String score_num) {
        this.score_num = score_num;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
