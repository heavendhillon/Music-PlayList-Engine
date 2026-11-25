
package playback;

import model.Song;
import java.util.LinkedList;
import java.util.Queue;

public class PlaybackQueue {

    private final Queue<Song> queue = new LinkedList<>();

    private boolean closed = false;

    // PRODUCER: ADD SONG TO QUEUE
    public synchronized void addSong(Song song) {

        if (closed) {
            throw new IllegalStateException(
                    "Playback queue is closed."
            );
        }

        queue.add(song);

        System.out.println(
                "Added to playback queue: " + song.getTitle()
        );

        notifyAll();
    }

    // CONSUMER: GET NEXT SONG
    public synchronized Song getNextSong()
            throws InterruptedException {

        // GUARDED BLOCK
        while (queue.isEmpty() && !closed) {

            System.out.println(
                    "Queue is empty. Waiting for songs..."
            );

            wait();
        }

        return queue.poll();
    }

    // CLOSE QUEUE
    public synchronized void closeQueue() {

        closed = true;

        notifyAll();
    }
}

