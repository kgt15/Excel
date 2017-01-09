package mec.mec.excel16;

/**
 * Created by hassan on 9/3/16.
 */
public class leaderBoardDetails {

    private String rank;
    private int imageId;
    private String imageUrl;
    private String Username;
    private String score;

    public leaderBoardDetails() {}

    public leaderBoardDetails(String rank, int imageId, String Username, String score) {
        this.rank = rank;
        this.imageId = imageId;
        this.Username = Username;
        this.score = score;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public void setImageUrl(String imageUrl) {  this.imageUrl = imageUrl; }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRank() {
        return rank;
    }

    public int getImageId() {
        return imageId;
    }

    public String getImageUrl() {   return imageUrl; }

    public String getUserName() {
        return Username;
    }

    public String getScore() {
        return score;
    }

    public class MyViewHolder {
    }
}