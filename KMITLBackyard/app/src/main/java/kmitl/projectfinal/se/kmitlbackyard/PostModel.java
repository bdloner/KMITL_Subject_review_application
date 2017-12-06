package kmitl.projectfinal.se.kmitlbackyard;

/**
 * Created by CPCust on 5/12/2560.
 */

public class PostModel {
    private String description;
    private String postImgLink;
    private String score;
    private String score_num;
    private String subject_id;
    private String timeStamp;
    private String title;
    private String uid;

    public PostModel(String description,String postImgLink,String score, String score_num, String subject_id, String timeStamp, String title, String uid) {
        this.description = description;
        this.postImgLink = postImgLink;
        this.score = score;
        this.score_num = score_num;
        this.subject_id = subject_id;
        this.timeStamp = timeStamp;
        this.title = title;
        this.uid = uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostImgLink() {
        return postImgLink;
    }

    public void setPostImgLink(String postImgLink) {
        this.postImgLink = postImgLink;
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
