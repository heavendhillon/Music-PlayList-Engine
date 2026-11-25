
package playback;

import model.Song;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaybackManager {

    private List<Song> playlist = new ArrayList<>();
    private int currentIndex = -1;

    // LOAD PLAYLIST
    public void loadPlaylist(List<Song> songs) {
        playlist = new ArrayList<>(songs);
        currentIndex = playlist.isEmpty() ? -1 : 0;
    }

    // CURRENT SONG
    public Song getCurrentSong() {
        if (currentIndex == -1) {
            return null;
        }

        return playlist.get(currentIndex);
    }

    // NEXT SONG
    public Song nextSong() {
        if (playlist.isEmpty()) {
            return null;
        }

        if (currentIndex < playlist.size() - 1) {
            currentIndex++;
            return getCurrentSong();
        }

        System.out.println("You are at the last song.");
        return null;
    }

    // PREVIOUS SONG
    public Song previousSong() {
        if (playlist.isEmpty()) {
            return null;
        }

        if (currentIndex > 0) {
            currentIndex--;
            return getCurrentSong();
        }

        System.out.println("You are at the first song.");
        return null;
    }

    // SHUFFLE PLAYLIST
    public void shuffle() {
        if (playlist.isEmpty()) {
            return;
        }

        Song current = getCurrentSong();

        Collections.shuffle(playlist);

        currentIndex = playlist.indexOf(current);
    }

    public boolean hasSongs() {
        return !playlist.isEmpty();
    }
}

