package utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import controller.ManageDB;
import model.Playlist;
import model.Song;
import model.User;

public class Utils {

	public static ArrayList<User> users = new ArrayList<User>();

	public static ArrayList<Playlist> playlists = new ArrayList<>();

	public static Playlist playlist1 = new Playlist("Playlist 1", 0);
	public static Playlist playlist2 = new Playlist("Playlist 2", 1);
	public static Playlist playlist3 = new Playlist("Playlist 3", 2);
	public static Playlist playlist4 = new Playlist("Playlist 4", 3);
	public static Playlist playlist5 = new Playlist("Playlist 5", 4);
	public static Playlist playlist6 = new Playlist("Playlist 6", 5);
	public static Playlist playlist7 = new Playlist("Playlist 7", 6);
	public static Playlist playlist8 = new Playlist("Playlist 8", 7);
	public static Playlist playlist9 = new Playlist("Playlist 9", 8);

	public User getUserFromUsername(String username) {
		for (User user : users) {
			if (user.getMail().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public static void generatePlaylists(ArrayList<Song> songs) {
		playlists.add(playlist1);
		playlists.add(playlist2);
		playlists.add(playlist3);
		playlists.add(playlist4);
		playlists.add(playlist5);
		playlists.add(playlist6);
		playlists.add(playlist7);
		playlists.add(playlist8);
		playlists.add(playlist9);
	}

    public static void generateRandomPlaylist(Playlist playlist) {
        // Limpiamos la playlist por si acaso
        playlist.getL_songs().clear();

        ManageDB managedb = ManageDB.getInstance();
        int totalSongs = managedb.getSongCount();

        if (totalSongs == 0) {
            System.out.println("No hay canciones en la base de datos.");
            return;
        }

        // Usamos un set para no repetir canciones
        Set<Integer> usedIds = new HashSet<>();

        int maxSongs = Math.min(10, totalSongs);

        while (playlist.getN_songs() < maxSongs) {
            int randomId = ThreadLocalRandom.current().nextInt(1, totalSongs + 1); // IDs suelen empezar en 1
            if (usedIds.contains(randomId)) continue;

            Song s = managedb.getSongById(randomId);
            if (s == null) continue;

            playlist.addSong(s);
            usedIds.add(randomId);
        }
    }
}
