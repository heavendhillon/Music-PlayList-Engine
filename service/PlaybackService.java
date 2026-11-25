
package service;

import dao.HistoryDAO;
import model.Song;
import playback.PlaybackQueue;

import java.sql.SQLException;
import java.util.List;

public class PlaybackService {

    private final PlaybackQueue playbackQueue =
            new PlaybackQueue();

    private final HistoryDAO historyDAO =
            new HistoryDAO();

    private Thread consumerThread;

    private volatile boolean started = false;

    // START CONSUMER THREAD
    public void startPlayback() {

        if (started) {
            return;
        }

        started = true;

        consumerThread = new Thread(() -> {

            try {

                Song song;

                while ((song = playbackQueue.getNextSong()) != null) {

                    System.out.println(
                            "\nNow Playing: " + song.getTitle()
                    );

                    System.out.println(
                            "Artist: " + song.getArtist()
                    );

                    // Save song in recently played history
                    historyDAO.addToHistory(song.getSongId());

                }

                System.out.println("Playback queue finished.");

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

            } catch (SQLException e) {

                System.out.println(
                        "Could not save playback history: "
                                + e.getMessage()
                );
            }

        });

        consumerThread.start();
    }

    // ADD ONE SONG TO QUEUE
    public void playSong(Song song) {

        if (!started) {
            startPlayback();
        }

        playbackQueue.addSong(song);
    }

    // ADD ENTIRE PLAYLIST TO QUEUE
    public void playPlaylist(List<Song> songs) {

        if (!started) {
            startPlayback();
        }

        for (Song song : songs) {
            playbackQueue.addSong(song);
        }
    }

    // STOP PLAYBACK
    public void stopPlayback() throws InterruptedException {

        playbackQueue.closeQueue();

        if (consumerThread != null) {
            consumerThread.join();
        }
    }
}

