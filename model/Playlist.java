// package model;

// public class Playlist {

//     private int playlistId;
//     private String playlistName;

//     public Playlist(int playlistId, String playlistName) {
//         this.playlistId = playlistId;
//         this.playlistName = playlistName;
//     }

//     public int getPlaylistId() {
//         return playlistId;
//     }

//     public String getPlaylistName() {
//         return playlistName;
//     }

//     public void displayPlaylist() {
//         System.out.println("Playlist ID: " + playlistId);
//         System.out.println("Playlist Name: " + playlistName);
//     }
// }

package model;

import java.time.LocalDateTime;

public class Playlist {

    private int playlistId;
    private String playlistName;
    private LocalDateTime createdAt;

    public Playlist(int playlistId, String playlistName,
                    LocalDateTime createdAt) {

        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.createdAt = createdAt;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return playlistId + " | " + playlistName
                + " | Created: " + createdAt;
    }
}
