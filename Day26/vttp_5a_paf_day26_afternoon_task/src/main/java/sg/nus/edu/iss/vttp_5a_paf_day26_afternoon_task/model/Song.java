package sg.nus.edu.iss.vttp_5a_paf_day26_afternoon_task.model;

import org.bson.Document;

public class Song {
    private String trackName;
    private String artistName;

    public Song() {
    }

    public String getTrackName() {
        return trackName;
    }
    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }
    public String getArtistName() {
        return artistName;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public static Song createSongFromDocument(Document document){
        Song song = new Song();
        Object trackName = document.get("track_name");
        if(!(trackName instanceof String)){
            song.setTrackName(String.valueOf(trackName));
        } else {
            song.setTrackName((String)trackName);
        }
        song.setArtistName(document.getString("artist(s)_name"));
        return song;
    }

    
}
