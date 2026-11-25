// package model;
// public class Song {

//     private int songId;
//     private String title;
//     private String artist;
//     private String album;
//     private String genre;
//     private int duration;

//     public Song(int songId, String title, String artist,
//                 String album, String genre, int duration) {

//         this.songId = songId;
//         this.title = title;
//         this.artist = artist;
//         this.album = album;
//         this.genre = genre;
//         this.duration = duration;
//     }

//     public int getSongId() {
//         return songId;
//     }

//     public String getTitle() {
//         return title;
//     }

//     public String getArtist() {
//         return artist;
//     }

//     public String getAlbum() {
//         return album;
//     }

//     public String getGenre() {
//         return genre;
//     }

//     public int getDuration() {
//         return duration;
//     }

//     public void displaySong() {

//         System.out.println("ID: " + songId);
//         System.out.println("Title: " + title);
//         System.out.println("Artist: " + artist);
//         System.out.println("Album: " + album);
//         System.out.println("Genre: " + genre);
//         System.out.println("Duration: " + duration + " seconds");
//     }
// }

package model;

public class Song {

    private int songId;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private int duration;

    // Constructor for adding a new song
    public Song(String title, String artist,
                String album, String genre, int duration) {

        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.duration = duration;
    }

    // Constructor for songs retrieved from database
    public Song(int songId, String title, String artist,
                String album, String genre, int duration) {

        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.duration = duration;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return songId + " | " + title + " | " + artist
                + " | " + album + " | " + genre
                + " | " + duration + " seconds";
    }
}

