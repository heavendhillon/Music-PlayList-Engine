
package dao;

import config.DBConnection;
import model.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {

    // SAVE A PLAYED SONG
    public void addToHistory(int songId) throws SQLException {

        String query =
                "INSERT INTO recently_played (song_id) VALUES (?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, songId);
            pst.executeUpdate();
        }
    }

    // GET RECENTLY PLAYED SONGS
    public List<Song> getRecentlyPlayed(int limit)
            throws SQLException {

        List<Song> history = new ArrayList<>();

        String query = """
                SELECT s.*
                FROM recently_played rp
                JOIN songs s ON rp.song_id = s.song_id
                ORDER BY rp.played_at DESC, rp.history_id DESC
                LIMIT ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, Math.max(1, Math.min(limit, 100)));

            try (ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {

                    Song song = new Song(
                            rs.getInt("song_id"),
                            rs.getString("title"),
                            rs.getString("artist"),
                            rs.getString("album"),
                            rs.getString("genre"),
                            rs.getInt("duration")
                    );

                    history.add(song);
                }
            }
        }

        return history;
    }
}

