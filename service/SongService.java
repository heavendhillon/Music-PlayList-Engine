// package service;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.stream.Collectors;

// import model.Song;
// public class SongService {
//     public List<Song> searchByArtist(ArrayList<Song> songs, String artist) {

//     return songs.stream()
//             .filter(s -> s.getArtist().equalsIgnoreCase(artist))
//             .collect(Collectors.toList());
// }
// public List<Song> sortSongsByTitle(ArrayList<Song> songs) {

//     return songs.stream()
//             .sorted((s1, s2) ->
//                 s1.getTitle().compareToIgnoreCase(s2.getTitle()))
//             .collect(Collectors.toList());
// }
// public List<String> getAllSongTitles(ArrayList<Song> songs) {

//     return songs.stream()
//             .map(s -> s.getTitle())
//             .collect(Collectors.toList());
// }
// public List<String> getUniqueArtists(ArrayList<Song> songs) {

//     return songs.stream()
//             .map(s -> s.getArtist())
//             .distinct()
//             .collect(Collectors.toList());
// }
// }

package service;

import dao.SongDAO;
import model.Song;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class SongService {

    private final SongDAO songDAO = new SongDAO();
    

    // ADD SONG
    public int addSong(Song song) throws SQLException {

        validateSong(song);

        return songDAO.addSong(song);
    }

    // GET ALL SONGS
    public List<Song> getAllSongs() throws SQLException {

        return songDAO.getAllSongs();
    }

    // SEARCH SONG BY ID
    public Song getSongById(int id) throws SQLException {

        return songDAO.getSongById(id);
    }

    // UPDATE SONG
    public boolean updateSong(Song song) throws SQLException {

        validateSong(song);

        return songDAO.updateSong(song);
    }

    // DELETE SONG
    public boolean deleteSong(int id) throws SQLException {

        return songDAO.deleteSong(id);
    }

    // SEARCH BY TITLE OR ARTIST
    public List<Song> searchSongs(String keyword)
            throws SQLException {

        return songDAO.searchSongs(keyword);
    }

    // SORT SONGS
    public List<Song> sortSongs(
            List<Song> songs, String criteria) {

        Comparator<Song> comparator;

        switch (criteria.toLowerCase()) {

            case "artist":
                comparator = Comparator.comparing(
                        Song::getArtist,
                        String.CASE_INSENSITIVE_ORDER
                );
                break;

            case "duration":
                comparator = Comparator.comparingInt(
                        Song::getDuration
                );
                break;

            case "genre":
                comparator = Comparator.comparing(
                        Song::getGenre,
                        Comparator.nullsFirst(
                                String.CASE_INSENSITIVE_ORDER
                        )
                );
                break;

            default:
                comparator = Comparator.comparing(
                        Song::getTitle,
                        String.CASE_INSENSITIVE_ORDER
                );
        }

        return songs.stream()
                .sorted(comparator)
                .toList();
    }

    // VALIDATE SONG
    private void validateSong(Song song) {

        if (song == null) {
            throw new IllegalArgumentException(
                    "Song cannot be empty."
            );
        }

        if (song.getTitle() == null ||
                song.getTitle().isBlank()) {

            throw new IllegalArgumentException(
                    "Song title is required."
            );
        }

        if (song.getArtist() == null ||
                song.getArtist().isBlank()) {

            throw new IllegalArgumentException(
                    "Artist name is required."
            );
        }

        if (song.getDuration() <= 0) {

            throw new IllegalArgumentException(
                    "Duration must be greater than zero."
            );
        }
    }
}
