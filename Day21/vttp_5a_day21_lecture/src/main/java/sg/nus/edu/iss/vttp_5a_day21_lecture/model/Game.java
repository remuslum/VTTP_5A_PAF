package sg.nus.edu.iss.vttp_5a_day21_lecture.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Game {
    private int gid;
    private String name;
    private int year;
    private int ranking;
    private int usersRated;
    private String url;
    private String image;

    public Game(){

    }
    
    public Game(int gid, String name, int year, int ranking, int usersRated, String url, String image) {
        this.gid = gid;
        this.name = name;
        this.year = year;
        this.ranking = ranking;
        this.usersRated = usersRated;
        this.url = url;
        this.image = image;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getUsersRated() {
        return usersRated;
    }

    public void setUsersRated(int usersRated) {
        this.usersRated = usersRated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static Game toGame(SqlRowSet rowSet){
        Game m = new Game(rowSet.getInt("gid"), rowSet.getString("name"), rowSet.getInt("year"),
        rowSet.getInt("ranking"), rowSet.getInt("users_rated"), rowSet.getString("url"), rowSet.getString("image"));
        return m;
    }
}
