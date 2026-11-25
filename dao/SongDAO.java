// package dao;
// import config.DBConnection;
// import java.sql.*;
// import java.util.ArrayList;
// import model.Song;

// public class SongDAO {


//     // ADD SONG
//     public void addSong(Song song) {

//         try {
//             Connection con = DBConnection.getConnection();

//             String query = "INSERT INTO songs (title, artist, album, genre, duration) VALUES (?, ?, ?, ?, ?)";

//             PreparedStatement pst = con.prepareStatement(query);

//             pst.setString(1, song.getTitle());
//             pst.setString(2, song.getArtist());
//             pst.setString(3, song.getAlbum());
//             pst.setString(4, song.getGenre());
//             pst.setInt(5, song.getDuration());

//             int rows = pst.executeUpdate();

//             if (rows > 0) {
//                 System.out.println("Song added successfully!");
//             }

//             pst.close();
//             con.close();

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }


//     // VIEW ALL SONGS
//     public ArrayList<Song> getAllSongs() {

//         ArrayList<Song> songs = new ArrayList<>();

//         try {
//             Connection con = DBConnection.getConnection();

//             Statement st = con.createStatement();

//             ResultSet rs = st.executeQuery("SELECT * FROM songs");

//             while (rs.next()) {

//                 Song song = new Song(
//                     rs.getInt("song_id"),
//                     rs.getString("title"),
//                     rs.getString("artist"),
//                     rs.getString("album"),
//                     rs.getString("genre"),
//                     rs.getInt("duration")
//                 );

//                 songs.add(song);
//             }

//             rs.close();
//             st.close();
//             con.close();

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }

//         return songs;
//     }


//     // SEARCH SONG BY ID
//     public Song getSongById(int id) {

//         Song song = null;

//         try {

//            Connection con = DBConnection.getConnection();

//             String query = "SELECT * FROM songs WHERE song_id = ?";

//             PreparedStatement pst = con.prepareStatement(query);

//             pst.setInt(1, id);

//             ResultSet rs = pst.executeQuery();

//             if (rs.next()) {

//                 song = new Song(
//                     rs.getInt("song_id"),
//                     rs.getString("title"),
//                     rs.getString("artist"),
//                     rs.getString("album"),
//                     rs.getString("genre"),
//                     rs.getInt("duration")
//                 );
//             }

//             rs.close();
//             pst.close();
//             con.close();

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }

//         return song;
//     }


//     // UPDATE SONG
//     public void updateSong(Song song) {

//         try {

//           Connection con = DBConnection.getConnection();

//             String query = "UPDATE songs SET title=?, artist=?, album=?, genre=?, duration=? WHERE song_id=?";

//             PreparedStatement pst = con.prepareStatement(query);

//             pst.setString(1, song.getTitle());
//             pst.setString(2, song.getArtist());
//             pst.setString(3, song.getAlbum());
//             pst.setString(4, song.getGenre());
//             pst.setInt(5, song.getDuration());
//             pst.setInt(6, song.getSongId());

//             int rows = pst.executeUpdate();

//             if (rows > 0) {
//                 System.out.println("Song updated successfully!");
//             } else {
//                 System.out.println("Song not found!");
//             }

//             pst.close();
//             con.close();

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }


//     // DELETE SONG
//     public void deleteSong(int id) {

//         try {

//            Connection con = DBConnection.getConnection();

//             String query = "DELETE FROM songs WHERE song_id = ?";

//             PreparedStatement pst = con.prepareStatement(query);

//             pst.setInt(1, id);

//             int rows = pst.executeUpdate();

//             if (rows > 0) {
//                 System.out.println("Song deleted successfully!");
//             } else {
//                 System.out.println("Song not found!");
//             }

//             pst.close();
//             con.close();

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }
// }

package dao;

import config.DBConnection;
import model.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDAO {

    // ADD SONG
    public int addSong(Song song) throws SQLException {

        String query = "INSERT INTO songs " +
                "(title, artist, album, genre, duration) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     query, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, song.getTitle());
            pst.setString(2, song.getArtist());
            pst.setString(3, song.getAlbum());
            pst.setString(4, song.getGenre());
            pst.setInt(5, song.getDuration());

            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return -1;
    }

    // VIEW ALL SONGS
    public List<Song> getAllSongs() throws SQLException {

        List<Song> songs = new ArrayList<>();

        String query = "SELECT * FROM songs ORDER BY song_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Song song = new Song(
                        rs.getInt("song_id"),
                        rs.getString("title"),
                        rs.getString("artist"),
                        rs.getString("album"),
                        rs.getString("genre"),
                        rs.getInt("duration")
                );

                songs.add(song);
            }
        }

        return songs;
    }

    // SEARCH SONG BY ID
    public Song getSongById(int id) throws SQLException {

        String query = "SELECT * FROM songs WHERE song_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {

                if (rs.next()) {
                    return new Song(
                            rs.getInt("song_id"),
                            rs.getString("title"),
                            rs.getString("artist"),
                            rs.getString("album"),
                            rs.getString("genre"),
                            rs.getInt("duration")
                    );
                }
            }
        }

        return null;
    }

    // UPDATE SONG
    public boolean updateSong(Song song) throws SQLException {

        String query = "UPDATE songs SET title=?, artist=?, " +
                "album=?, genre=?, duration=? WHERE song_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, song.getTitle());
            pst.setString(2, song.getArtist());
            pst.setString(3, song.getAlbum());
            pst.setString(4, song.getGenre());
            pst.setInt(5, song.getDuration());
            pst.setInt(6, song.getSongId());

            return pst.executeUpdate() > 0;
        }
    }

    // DELETE SONG
    public boolean deleteSong(int id) throws SQLException {

        String query = "DELETE FROM songs WHERE song_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id);

            return pst.executeUpdate() > 0;
        }
    }

    // SEARCH BY TITLE OR ARTIST
    public List<Song> searchSongs(String keyword) throws SQLException {

        List<Song> songs = new ArrayList<>();

        String query = "SELECT * FROM songs " +
                "WHERE title LIKE ? OR artist LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            String search = "%" + keyword + "%";

            pst.setString(1, search);
            pst.setString(2, search);

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
}


