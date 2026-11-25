
package service;

import dao.PlaylistDAO;
import model.Playlist;
import model.Song;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaylistService {

    private final PlaylistDAO playlistDAO = new PlaylistDAO();

    // CREATE PLAYLIST
    public int createPlaylist(String name) throws SQLException {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Playlist name cannot be empty."
            );
        }

        return playlistDAO.createPlaylist(name.trim());
    }

    // VIEW ALL PLAYLISTS
    public List<Playlist> getAllPlaylists() throws SQLException {

        return playlistDAO.getAllPlaylists();
    }

    // RENAME PLAYLIST
    public boolean renamePlaylist(int id, String name)
            throws SQLException {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Playlist name cannot be empty."
            );
        }

        return playlistDAO.renamePlaylist(id, name.trim());
    }

    // DELETE PLAYLIST
    public boolean deletePlaylist(int id) throws SQLException {

        return playlistDAO.deletePlaylist(id);
    }

    // ADD SONG TO PLAYLIST
    public boolean addSongToPlaylist(int playlistId, int songId)
            throws SQLException {

        return playlistDAO.addSongToPlaylist(
                playlistId, songId
        );
    }

    // REMOVE SONG FROM PLAYLIST
    public boolean removeSongFromPlaylist(int playlistId, int songId)
            throws SQLException {

        return playlistDAO.removeSongFromPlaylist(
                playlistId, songId
        );
    }

    // VIEW PLAYLIST SONGS
    public List<Song> getPlaylistSongs(int playlistId)
            throws SQLException {

        return playlistDAO.getPlaylistSongs(playlistId);
    }

    // CHANGE SONG POSITION
    public boolean changeSongPosition(
            int playlistId, int songId, int newPosition)
            throws SQLException {

        return playlistDAO.changeSongPosition(
                playlistId, songId, newPosition
        );
    }

    // SHUFFLE PLAYLIST
    public List<Song> shufflePlaylist(int playlistId)
            throws SQLException {

        List<Song> shuffledSongs = new ArrayList<>(
                playlistDAO.getPlaylistSongs(playlistId)
        );

        Collections.shuffle(shuffledSongs);

        return shuffledSongs;
    }
}
