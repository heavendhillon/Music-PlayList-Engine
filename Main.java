
import java.util.List;
import java.util.Scanner;

import dao.HistoryDAO;
import dao.PlaylistDAO;
import dao.SongDAO;
import model.Playlist;
import model.Song;
import playback.PlaybackManager;
import playback.PlaybackQueue;
import service.PlaybackService;
import service.PlaylistService;
import service.SongService;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final SongDAO songDAO = new SongDAO();
    private static final PlaylistDAO playlistDAO = new PlaylistDAO();
    private static final HistoryDAO historyDAO = new HistoryDAO();

    private static final SongService songService = new SongService();

    private static final PlaylistService playlistService = new PlaylistService();

    private static final PlaybackManager playbackManager = new PlaybackManager();

    private static final PlaybackQueue playbackQueue = new PlaybackQueue();

   private static final PlaybackService playbackService =
        new PlaybackService();
    private static int currentPlaylistId = -1;

    public static void main(String[] args) throws Exception {

        System.out.println("==================================");
        System.out.println("     MUSIC PLAYLIST ENGINE");
        System.out.println("==================================");

        boolean running = true;

        while (running) {

            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Manage Songs");
            System.out.println("2. Manage Playlists");
            System.out.println("3. Playback");
            System.out.println("4. Recently Played");
            System.out.println("5. Sort Songs");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");

            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    manageSongs();
                    break;

                case "2":
                    managePlaylists();
                    break;

                case "3":
                   managePlayback();
                    break;

                case "4":
                  viewRecentlyPlayed();
                    break;

                case "5":
                    sortSongs();
                    break;

                case "0":
                    running = false;
                    System.out.println("Thank you for using Music Playlist Engine!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        sc.close();
    }

private static void manageSongs() {

    boolean running = true;

    while (running) {

        System.out.println("\n========== SONG MANAGEMENT ==========");
        System.out.println("1. Add Song");
        System.out.println("2. View All Songs");
        System.out.println("3. Search Song");
        System.out.println("4. Update Song");
        System.out.println("5. Delete Song");
        System.out.println("0. Back to Main Menu");

        System.out.print("Enter your choice: ");
        String choice = sc.nextLine();

        try {

            switch (choice) {

                case "1":
                    addSong();
                    break;

                case "2":
                    viewAllSongs();
                    break;

                case "3":
                    searchSong();
                    break;

                case "4":
                    updateSong();
                    break;

                case "5":
                    deleteSong();
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

private static void addSong() throws Exception {

    System.out.println("\n========== ADD NEW SONG ==========");

    System.out.print("Enter song title: ");
    String title = sc.nextLine();

    System.out.print("Enter artist name: ");
    String artist = sc.nextLine();

    System.out.print("Enter album name: ");
    String album = sc.nextLine();

    System.out.print("Enter genre: ");
    String genre = sc.nextLine();

    System.out.print("Enter duration in seconds: ");
    int duration = Integer.parseInt(sc.nextLine());

    Song song = new Song(
            title,
            artist,
            album,
            genre,
            duration
    );

    int songId = songService.addSong(song);

    System.out.println("\nSong added successfully!");
    System.out.println("Generated Song ID: " + songId);
}

private static void viewAllSongs() throws Exception {

    System.out.println("\n========== ALL SONGS ==========");

    List<Song> songs = songService.getAllSongs();

    if (songs.isEmpty()) {
        System.out.println("No songs found in the database.");
        return;
    }

    for (Song song : songs) {
        System.out.println(song);
    }
}

private static void searchSong() throws Exception {

    System.out.println("\n========== SEARCH SONG ==========");

    System.out.print("Enter song title or artist name: ");
    String keyword = sc.nextLine();

    List<Song> songs = songService.searchSongs(keyword);

    if (songs.isEmpty()) {
        System.out.println("No matching songs found.");
        return;
    }

    System.out.println("\nSearch Results:");

    for (Song song : songs) {
        System.out.println(song);
    }
}

private static void updateSong() throws Exception {

    System.out.println("\n========== UPDATE SONG ==========");

    System.out.print("Enter Song ID: ");
    int id = Integer.parseInt(sc.nextLine());

    Song existingSong = songService.getSongById(id);

    if (existingSong == null) {
        System.out.println("Song not found!");
        return;
    }

    System.out.println("Current details: " + existingSong);

    System.out.print("Enter new title: ");
    String title = sc.nextLine();

    System.out.print("Enter new artist: ");
    String artist = sc.nextLine();

    System.out.print("Enter new album: ");
    String album = sc.nextLine();

    System.out.print("Enter new genre: ");
    String genre = sc.nextLine();

    System.out.print("Enter new duration (seconds): ");
    int duration = Integer.parseInt(sc.nextLine());

    Song updatedSong = new Song(
            id,
            title,
            artist,
            album,
            genre,
            duration
    );

    boolean updated = songService.updateSong(updatedSong);

    if (updated) {
        System.out.println("Song updated successfully!");
    } else {
        System.out.println("Song could not be updated.");
    }
}

private static void deleteSong() throws Exception {

    System.out.println("\n========== DELETE SONG ==========");

    System.out.print("Enter Song ID: ");
    int id = Integer.parseInt(sc.nextLine());

    Song song = songService.getSongById(id);

    if (song == null) {
        System.out.println("Song not found!");
        return;
    }

    System.out.println("Song details: " + song);

    System.out.print("Are you sure you want to delete this song? (yes/no): ");
    String confirmation = sc.nextLine();

    if (confirmation.equalsIgnoreCase("yes")) {

        boolean deleted = songService.deleteSong(id);

        if (deleted) {
            System.out.println("Song deleted successfully!");
        } else {
            System.out.println("Song could not be deleted.");
        }

    } else {
        System.out.println("Deletion cancelled.");
    }
}

private static void managePlaylists() {

    boolean running = true;

    while (running) {

        System.out.println("\n========== PLAYLIST MANAGEMENT ==========");
        System.out.println("1. Create Playlist");
        System.out.println("2. View All Playlists");
        System.out.println("3. Add Song to Playlist");
        System.out.println("4. Remove Song from Playlist");
        System.out.println("5. View Playlist Songs");
        System.out.println("6. Rename Playlist");
        System.out.println("7. Delete Playlist");
        System.out.println("8. Change Song Position");
        System.out.println("0. Back to Main Menu");

        System.out.print("Enter your choice: ");
        String choice = sc.nextLine();

        try {

            switch (choice) {

                case "1":
                    createPlaylist();
                    break;

                case "2":
                    viewAllPlaylists();
                    break;

                case "3":
                    addSongToPlaylist();
                    break;

                case "4":
                    removeSongFromPlaylist();
                    break;

                case "5":
                    viewPlaylistSongs();
                    break;

                case "6":
                    renamePlaylist();
                    break;

                case "7":
                    deletePlaylist();
                    break;

                case "8":
                    changeSongPosition();
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

private static void createPlaylist() throws Exception {

    System.out.println("\n========== CREATE PLAYLIST ==========");

    System.out.print("Enter playlist name: ");
    String name = sc.nextLine();

    int playlistId = playlistService.createPlaylist(name);

    System.out.println("\nPlaylist created successfully!");
    System.out.println("Playlist ID: " + playlistId);

    System.out.print("Do you want to add songs now? (yes/no): ");
    String answer = sc.nextLine();

    if (answer.equalsIgnoreCase("yes")) {

        while (true) {

            System.out.print("Enter Song ID to add (0 to finish): ");
            int songId = Integer.parseInt(sc.nextLine());

            if (songId == 0) {
                break;
            }

            boolean added = playlistService.addSongToPlaylist(
                    playlistId, songId
            );

            if (added) {
                System.out.println("Song added to playlist!");
            } else {
                System.out.println("Could not add song.");
            }
        }
    }
}

private static void viewAllPlaylists() throws Exception {

    System.out.println("\n========== ALL PLAYLISTS ==========");

    List<Playlist> playlists = playlistService.getAllPlaylists();

    if (playlists.isEmpty()) {
        System.out.println("No playlists found.");
        return;
    }

    for (Playlist playlist : playlists) {
        System.out.println(playlist);
    }
}

private static void addSongToPlaylist() throws Exception {

    System.out.println("\n========== ADD SONG TO PLAYLIST ==========");

    System.out.print("Enter Playlist ID: ");
    int playlistId = Integer.parseInt(sc.nextLine());

    System.out.print("Enter Song ID: ");
    int songId = Integer.parseInt(sc.nextLine());

    boolean added = playlistService.addSongToPlaylist(
            playlistId, songId
    );

    if (added) {
        System.out.println("Song added to playlist successfully!");
    } else {
        System.out.println("Song could not be added.");
    }
}

private static void removeSongFromPlaylist() throws Exception {

    System.out.println("\n========== REMOVE SONG FROM PLAYLIST ==========");

    System.out.print("Enter Playlist ID: ");
    int playlistId = Integer.parseInt(sc.nextLine());

    System.out.print("Enter Song ID to remove: ");
    int songId = Integer.parseInt(sc.nextLine());

    boolean removed = playlistService.removeSongFromPlaylist(
            playlistId, songId
    );

    if (removed) {
        System.out.println("Song removed from playlist successfully!");
    } else {
        System.out.println("Song could not be removed. Check the IDs.");
    }
}

private static void viewPlaylistSongs() throws Exception {

    System.out.println("\n========== VIEW PLAYLIST SONGS ==========");

    System.out.print("Enter Playlist ID: ");
    int playlistId = Integer.parseInt(sc.nextLine());

    List<Song> songs = playlistService.getPlaylistSongs(playlistId);

    if (songs.isEmpty()) {
        System.out.println("This playlist is empty.");
        return;
    }

    System.out.println("\nSongs in this playlist:");

    for (Song song : songs) {
        System.out.println(song);
    }
}

private static void renamePlaylist() throws Exception {

    System.out.println("\n========== RENAME PLAYLIST ==========");

    System.out.print("Enter Playlist ID: ");
    int playlistId = Integer.parseInt(sc.nextLine());

    System.out.print("Enter new playlist name: ");
    String newName = sc.nextLine();

    boolean renamed = playlistService.renamePlaylist(
            playlistId, newName
    );

    if (renamed) {
        System.out.println("Playlist renamed successfully!");
    } else {
        System.out.println("Playlist could not be renamed. Check the ID.");
    }
}

private static void deletePlaylist() throws Exception {

    System.out.println("\n========== DELETE PLAYLIST ==========");

    System.out.print("Enter Playlist ID: ");
    int playlistId = Integer.parseInt(sc.nextLine());

    System.out.print("Are you sure you want to delete this playlist? (yes/no): ");
    String confirmation = sc.nextLine();

    if (confirmation.equalsIgnoreCase("yes")) {

        boolean deleted = playlistService.deletePlaylist(playlistId);

        if (deleted) {
            System.out.println("Playlist deleted successfully!");
        } else {
            System.out.println("Playlist not found or could not be deleted.");
        }

    } else {
        System.out.println("Deletion cancelled.");
    }
}

private static void changeSongPosition() throws Exception {

    System.out.println("\n========== CHANGE SONG POSITION ==========");

    System.out.print("Enter Playlist ID: ");
    int playlistId = Integer.parseInt(sc.nextLine());

    System.out.print("Enter Song ID: ");
    int songId = Integer.parseInt(sc.nextLine());

    System.out.print("Enter new position: ");
    int newPosition = Integer.parseInt(sc.nextLine());

    boolean changed = playlistService.changeSongPosition(
            playlistId, songId, newPosition
    );

    if (changed) {
        System.out.println("Song position changed successfully!");
    } else {
        System.out.println("Could not change song position. Check the IDs and position.");
    }
}

private static void managePlayback() {

    boolean running = true;

    while (running) {

        System.out.println("\n========== PLAYBACK ==========");
        System.out.println("1. Load Playlist");
        System.out.println("2. Play Current Song");
        System.out.println("3. Next Song");
        System.out.println("4. Previous Song");
        System.out.println("5. Shuffle Playlist");
        System.out.println("6. View Current Song");
        System.out.println("0. Back to Main Menu");

        System.out.print("Enter your choice: ");
        String choice = sc.nextLine();

        try {

            switch (choice) {

                case "1":
                    loadPlaylistForPlayback();
                    break;

                case "2":
                    playCurrentSong();
                    break;

                case "3":
                    nextSong();
                    break;

                case "4":
                    previousSong();
                    break;

                case "5":
                    shufflePlayback();
                    break;

                case "6":
                    viewCurrentSong();
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

private static void loadPlaylistForPlayback() throws Exception {

    System.out.println("\n========== LOAD PLAYLIST ==========");

    System.out.print("Enter Playlist ID: ");
    int playlistId = Integer.parseInt(sc.nextLine());
    currentPlaylistId = playlistId;

    List<Song> songs = playlistService.getPlaylistSongs(playlistId);

    if (songs.isEmpty()) {
        System.out.println("This playlist is empty or does not exist.");
        return;
    }

    playbackManager.loadPlaylist(songs);

    System.out.println("\nPlaylist loaded successfully!");
    System.out.println("Total songs: " + songs.size());

    System.out.println("\nFirst song: ");
    System.out.println(playbackManager.getCurrentSong());
}

private static void playCurrentSong() {

    if (!playbackManager.hasSongs()) {
        System.out.println("Please load a playlist first.");
        return;
    }

    Song currentSong = playbackManager.getCurrentSong();

    if (currentSong == null) {
        System.out.println("No song selected.");
        return;
    }

    playbackService.playSong(currentSong);

    System.out.println("Song sent for playback!");
}

private static void nextSong() {

    if (!playbackManager.hasSongs()) {
        System.out.println("Please load a playlist first.");
        return;
    }

    Song next = playbackManager.nextSong();

    if (next == null) {
        return;
    }

    System.out.println("\nNext Song:");
    System.out.println(next);

    playbackService.playSong(next);
}

private static void previousSong() {

    if (!playbackManager.hasSongs()) {
        System.out.println("Please load a playlist first.");
        return;
    }

    Song previous = playbackManager.previousSong();

    if (previous == null) {
        return;
    }

    System.out.println("\nPrevious Song:");
    System.out.println(previous);

    playbackService.playSong(previous);
}

private static void shufflePlayback() throws Exception {

    if (!playbackManager.hasSongs()) {
        System.out.println("Please load a playlist first.");
        return;
    }

    List<Song> shuffledSongs =
            playlistService.shufflePlaylist(
                currentPlaylistId
            );

    playbackManager.loadPlaylist(shuffledSongs);

    System.out.println("\nPlaylist shuffled successfully!");
    System.out.println("New current song:");

    System.out.println(playbackManager.getCurrentSong());
}

private static void viewCurrentSong() {

    if (!playbackManager.hasSongs()) {
        System.out.println("Please load a playlist first.");
        return;
    }

    Song currentSong = playbackManager.getCurrentSong();

    if (currentSong == null) {
        System.out.println("No song is currently selected.");
        return;
    }

    System.out.println("\n========== CURRENT SONG ==========");
    System.out.println(currentSong);
}
private static void viewRecentlyPlayed() throws Exception {

    System.out.println("\n========== RECENTLY PLAYED ==========");

    List<Song> songs = historyDAO.getRecentlyPlayed(10);

    if (songs.isEmpty()) {
        System.out.println("No recently played songs.");
        return;
    }

    for (Song song : songs) {
        System.out.println(song);
    }
}

private static void sortSongs() throws Exception {

    System.out.println("\n========== SORT SONGS ==========");
    System.out.println("1. Sort by Title");
    System.out.println("2. Sort by Artist");
    System.out.println("3. Sort by Duration");
    System.out.println("4. Sort by Genre");

    System.out.print("Choose sorting criteria: ");
    String choice = sc.nextLine();

    String criteria;

    switch (choice) {
        case "1":
            criteria = "title";
            break;

        case "2":
            criteria = "artist";
            break;

        case "3":
            criteria = "duration";
            break;

        case "4":
            criteria = "genre";
            break;

        default:
            System.out.println("Invalid choice!");
            return;
    }

    List<Song> songs = songService.getAllSongs();

    if (songs.isEmpty()) {
        System.out.println("No songs available.");
        return;
    }

    List<Song> sortedSongs =
            songService.sortSongs(songs, criteria);

    System.out.println("\nSorted Songs:");

    for (Song song : sortedSongs) {
        System.out.println(song);
    }
}


}
