// package dao;

// import java.sql.*;
// import java.util.ArrayList;

// import config.DBConnection;
// import model.Playlist;
// import model.Song;

// public class PlaylistDAO {

//     public void createPlaylist(String playlistName) {

//         String query = "INSERT INTO playlists (playlist_name) VALUES (?)";

//         try (Connection con = DBConnection.getConnection();
//                 PreparedStatement pst = con.prepareStatement(query)) {

//             pst.setString(1, playlistName);

//             int rows = pst.executeUpdate();

//             if (rows > 0) {
//                 System.out.println("Playlist created successfully!");
//             }

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     public ArrayList<Playlist> getAllPlaylists() {

//         ArrayList<Playlist> playlists = new ArrayList<>();

//         String query = "SELECT * FROM playlists";

//         try (Connection con = DBConnection.getConnection();
//                 PreparedStatement pst = con.prepareStatement(query);
//                 ResultSet rs = pst.executeQuery()) {

//             while (rs.next()) {

//                 int id = rs.getInt("playlist_id");
//                 String name = rs.getString("playlist_name");

//                 Playlist playlist = new Playlist(id, name);

//                 playlists.add(playlist);
//             }

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }

//         return playlists;
//     }

//     public void addSongToPlaylist(int playlistId, int songId, int position) {

//         String query = "INSERT INTO playlist_songs (playlist_id, song_id, position) VALUES (?, ?, ?)";

//         try (Connection con = DBConnection.getConnection();
//                 PreparedStatement pst = con.prepareStatement(query)) {

//             pst.setInt(1, playlistId);
//             pst.setInt(2, songId);
//             pst.setInt(3, position);

//             int rows = pst.executeUpdate();

//             if (rows > 0) {
//                 System.out.println("Song added to playlist successfully!");
//             }

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     public ArrayList<Song> getPlaylistSongs(int playlistId) {

//         ArrayList<Song> songs = new ArrayList<>();

//         String query = "SELECT s.* FROM songs s " +
//                 "JOIN playlist_songs ps ON s.song_id = ps.song_id " +
//                 "WHERE ps.playlist_id = ? " +
//                 "ORDER BY ps.position ASC";

//         try (Connection con = DBConnection.getConnection();
//                 PreparedStatement pst = con.prepareStatement(query)) {

//             pst.setInt(1, playlistId);

//             ResultSet rs = pst.executeQuery();

//             while (rs.next()) {

//                 Song song = new Song(
//                         rs.getInt("song_id"),
//                         rs.getString("title"),
//                         rs.getString("artist"),
//                         rs.getString("album"),
//                         rs.getString("genre"),
//                         rs.getInt("duration"));

//                 songs.add(song);
//             }

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }

//         return songs;
//     }

//     public void removeSongFromPlaylist(int playlistId, int songId) {

//         String query = "DELETE FROM playlist_songs WHERE playlist_id = ? AND song_id = ?";

//         try (Connection con = DBConnection.getConnection();
//                 PreparedStatement pst = con.prepareStatement(query)) {

//             pst.setInt(1, playlistId);
//             pst.setInt(2, songId);

//             int rows = pst.executeUpdate();

//             if (rows > 0) {
//                 System.out.println("Song removed from playlist successfully!");
//             } else {
//                 System.out.println("Song not found in this playlist!");
//             }

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     public void changeSongPosition(int playlistId, int songId, int newPosition) {

//         String findQuery = "SELECT position FROM playlist_songs WHERE playlist_id = ? AND song_id = ?";

//         String updateQuery = "UPDATE playlist_songs SET position = ? WHERE playlist_id = ? AND song_id = ?";

//         try (Connection con = DBConnection.getConnection();) {

//             con.setAutoCommit(false);

//             try (PreparedStatement findPst = con.prepareStatement(findQuery)) {

//                 findPst.setInt(1, playlistId);
//                 findPst.setInt(2, songId);

//                 ResultSet rs = findPst.executeQuery();

//                 if (!rs.next()) {
//                     System.out.println("Song not found in this playlist!");
//                     con.rollback();
//                     return;
//                 }

//                 int oldPosition = rs.getInt("position");

//                 if (oldPosition == newPosition) {
//                     System.out.println("Song is already at this position!");
//                     con.rollback();
//                     return;
//                 }

//                 int tempPosition = -1;

//                 try (PreparedStatement pst = con.prepareStatement(updateQuery)) {

//                     // Temporarily move the selected song
//                     pst.setInt(1, tempPosition);
//                     pst.setInt(2, playlistId);
//                     pst.setInt(3, songId);
//                     pst.executeUpdate();

//                     // Shift other songs
//                     String shiftQuery;

//                     if (newPosition < oldPosition) {
//                         shiftQuery = "UPDATE playlist_songs SET position = position + 1 " +
//                                 "WHERE playlist_id = ? AND position >= ? AND position < ?";
//                     } else {
//                         shiftQuery = "UPDATE playlist_songs SET position = position - 1 " +
//                                 "WHERE playlist_id = ? AND position > ? AND position <= ?";
//                     }

//                     try (PreparedStatement shiftPst = con.prepareStatement(shiftQuery)) {

//                         shiftPst.setInt(1, playlistId);

//                         if (newPosition < oldPosition) {
//                             shiftPst.setInt(2, newPosition);
//                             shiftPst.setInt(3, oldPosition);
//                         } else {
//                             shiftPst.setInt(2, oldPosition);
//                             shiftPst.setInt(3, newPosition);
//                         }

//                         shiftPst.executeUpdate();
//                     }

//                     // Put selected song at its new position
//                     pst.setInt(1, newPosition);
//                     pst.setInt(2, playlistId);
//                     pst.setInt(3, songId);
//                     pst.executeUpdate();
//                 }

//                 con.commit();
//                 System.out.println("Song position changed successfully!");

//             } catch (SQLException e) {
//                 con.rollback();
//                 throw e;
//             }

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }
//     // DELETE PLAYLIST
// public void deletePlaylist(int playlistId) {

//     try {

//         Connection con = DBConnection.getConnection();

//         String query = "DELETE FROM playlists WHERE playlist_id = ?";

//         PreparedStatement pst = con.prepareStatement(query);

//         pst.setInt(1, playlistId);

//         int rows = pst.executeUpdate();

//         if (rows > 0) {
//             System.out.println("Playlist deleted successfully!");
//         } else {
//             System.out.println("Playlist not found!");
//         }

//         pst.close();
//         con.close();

//     } catch (SQLException e) {
//         e.printStackTrace();
//     }
// }

// }

package dao;

import config.DBConnection;
import model.Playlist;
import model.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

    // CREATE PLAYLIST
    public int createPlaylist(String name) throws SQLException {

        String query = "INSERT INTO playlists (playlist_name) VALUES (?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     query, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, name);
            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return -1;
    }

    // VIEW ALL PLAYLISTS
    public List<Playlist> getAllPlaylists() throws SQLException {

        List<Playlist> playlists = new ArrayList<>();

        String query = "SELECT * FROM playlists ORDER BY playlist_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Timestamp timestamp = rs.getTimestamp("created_at");

                playlists.add(new Playlist(
                        rs.getInt("playlist_id"),
                        rs.getString("playlist_name"),
                        timestamp == null ? null : timestamp.toLocalDateTime()
                ));
            }
        }

        return playlists;
    }

    // RENAME PLAYLIST
    public boolean renamePlaylist(int id, String newName)
            throws SQLException {

        String query = "UPDATE playlists SET playlist_name=? " +
                "WHERE playlist_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, newName);
            pst.setInt(2, id);

            return pst.executeUpdate() > 0;
        }
    }

    // DELETE PLAYLIST
    public boolean deletePlaylist(int id) throws SQLException {

        String query = "DELETE FROM playlists WHERE playlist_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id);

            return pst.executeUpdate() > 0;
        }
    }

    // ADD SONG TO PLAYLIST
    public boolean addSongToPlaylist(int playlistId, int songId)
            throws SQLException {

        String query = """
                INSERT INTO playlist_songs
                (playlist_id, song_id, position)
                SELECT ?, ?, COALESCE(MAX(position), 0) + 1
                FROM playlist_songs
                WHERE playlist_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, playlistId);
            pst.setInt(2, songId);
            pst.setInt(3, playlistId);

            return pst.executeUpdate() > 0;
        }
    }

    // REMOVE SONG FROM PLAYLIST
    public boolean removeSongFromPlaylist(int playlistId, int songId)
            throws SQLException {

        String query = "DELETE FROM playlist_songs " +
                "WHERE playlist_id=? AND song_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, playlistId);
            pst.setInt(2, songId);

            return pst.executeUpdate() > 0;
        }
    }

    // VIEW SONGS INSIDE A PLAYLIST
    public List<Song> getPlaylistSongs(int playlistId)
            throws SQLException {

        List<Song> songs = new ArrayList<>();

        String query = """
                SELECT s.*
                FROM songs s
                JOIN playlist_songs ps
                ON s.song_id = ps.song_id
                WHERE ps.playlist_id = ?
                ORDER BY ps.position, s.song_id
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, playlistId);

            try (ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {

                    songs.add(new Song(
                            rs.getInt("song_id"),
                            rs.getString("title"),
                            rs.getString("artist"),
                            rs.getString("album"),
                            rs.getString("genre"),
                            rs.getInt("duration")
                    ));
                }
            }
        }

        return songs;
    }

    // CHANGE SONG POSITION
    public boolean changeSongPosition(
            int playlistId, int songId, int newPosition)
            throws SQLException {

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try {
                int oldPosition;
                int count;

                String countQuery =
                        "SELECT COUNT(*) FROM playlist_songs WHERE playlist_id=?";

                try (PreparedStatement pst = con.prepareStatement(countQuery)) {
                    pst.setInt(1, playlistId);

                    try (ResultSet rs = pst.executeQuery()) {
                        rs.next();
                        count = rs.getInt(1);
                    }
                }

                if (newPosition < 1 || newPosition > count) {
                    con.rollback();
                    return false;
                }

                String positionQuery =
                        "SELECT position FROM playlist_songs " +
                        "WHERE playlist_id=? AND song_id=?";

                try (PreparedStatement pst = con.prepareStatement(positionQuery)) {
                    pst.setInt(1, playlistId);
                    pst.setInt(2, songId);

                    try (ResultSet rs = pst.executeQuery()) {
                        if (!rs.next()) {
                            con.rollback();
                            return false;
                        }

                        oldPosition = rs.getInt("position");
                    }
                }

                if (oldPosition == newPosition) {
                    con.commit();
                    return true;
                }

                // Temporarily move the selected song out of the way
                String tempQuery =
                        "UPDATE playlist_songs SET position=-1 " +
                        "WHERE playlist_id=? AND song_id=?";

                try (PreparedStatement pst = con.prepareStatement(tempQuery)) {
                    pst.setInt(1, playlistId);
                    pst.setInt(2, songId);
                    pst.executeUpdate();
                }

                // Shift the other songs
                String shiftQuery;

                if (oldPosition < newPosition) {
                    shiftQuery = "UPDATE playlist_songs " +
                            "SET position=position-1 " +
                            "WHERE playlist_id=? AND position>? AND position<=?";
                } else {
                    shiftQuery = "UPDATE playlist_songs " +
                            "SET position=position+1 " +
                            "WHERE playlist_id=? AND position>=? AND position<?";
                }

                try (PreparedStatement pst = con.prepareStatement(shiftQuery)) {
                    pst.setInt(1, playlistId);
                    pst.setInt(2, Math.min(oldPosition, newPosition));
                    pst.setInt(3, Math.max(oldPosition, newPosition));
                    pst.executeUpdate();
                }

                // Set the selected song to its new position
                String finalQuery =
                        "UPDATE playlist_songs SET position=? " +
                        "WHERE playlist_id=? AND song_id=?";

                try (PreparedStatement pst = con.prepareStatement(finalQuery)) {
                    pst.setInt(1, newPosition);
                    pst.setInt(2, playlistId);
                    pst.setInt(3, songId);
                    pst.executeUpdate();
                }

                con.commit();
                return true;

            } catch (SQLException e) {
                con.rollback();
                throw e;

            } finally {
                con.setAutoCommit(true);
            }
        }
    }
}
