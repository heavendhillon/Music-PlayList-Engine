# 🎵 Music Playlist Engine

A console-based music playlist management system built using **Java, JDBC, and MySQL**. This project demonstrates backend development concepts including database connectivity, CRUD operations, DAO and Service architecture, Java Collections, Stream API, and multithreading.

## 🚀 Features

### 🎶 Song Management
- Add new songs to the music library.
- View all available songs.
- Search songs by title or artist.
- Update existing song details.
- Delete songs from the library.

### 📂 Playlist Management
- Create and manage multiple playlists.
- Add songs to playlists.
- Remove songs from playlists.
- View songs within a playlist.
- Rename and delete playlists.
- Change the position of songs within a playlist.

### ▶️ Playback Management
- Load playlists for playback.
- Play the currently selected song.
- Navigate to the next and previous songs.
- Shuffle playlist order.
- View the currently selected song.
- Maintain recently played history.

*Note: Playback is currently simulated through a background consumer thread. Actual audio playback is not implemented.*

### ⚡ Java Concepts Demonstrated
- Object-Oriented Programming (OOP)
- JDBC and PreparedStatement
- Java Collections Framework
- Queue and LinkedList
- Comparator and sorting
- Stream API
- Multithreading
- Producer-Consumer pattern
- `synchronized`, `wait()`, and `notifyAll()`
- Exception handling

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming |
| JDBC | Database connectivity |
| MySQL | Relational database |
| VS Code | Development environment |
| Git & GitHub | Version control |

## 🏗️ Project Architecture

The project follows a layered architecture:

```text
MusicPlaylistEngine/
│
├── src/
│   ├── config/
│   │   └── DBConnection.java
│   │
│   ├── model/
│   │   ├── Song.java
│   │   └── Playlist.java
│   │
│   ├── dao/
│   │   ├── SongDAO.java
│   │   ├── PlaylistDAO.java
│   │   └── HistoryDAO.java
│   │
│   ├── service/
│   │   ├── SongService.java
│   │   ├── PlaylistService.java
│   │   └── PlaybackService.java
│   │
│   ├── playback/
│   │   ├── PlaybackQueue.java
│   │   └── PlaybackManager.java
│   │
│   └── Main.java
│
├── lib/
│   └── mysql-connector-j.jar
│
├── README.md
└── .gitignore
```

## 🗄️ Database Design

Database name: `music_playlist_engine`

The application uses four tables:

| Table | Description |
|---|---|
| `songs` | Stores song information |
| `play
