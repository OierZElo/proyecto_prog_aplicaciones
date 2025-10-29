package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.Playlist;
import model.Song;
import model.User;

public class Utils {
	public static ArrayList<User> users = new ArrayList<User>();
	public static ArrayList<Song> songs = new ArrayList<>();
	public static ArrayList<Playlist> playlists = new ArrayList<>();

	public static Playlist playlist1 = new Playlist("Playlist 1", 0, new ArrayList<Song>());
	public static Playlist playlist2 = new Playlist("Playlist 2", 1, new ArrayList<Song>());
	public static Playlist playlist3 = new Playlist("Playlist 3", 2, new ArrayList<Song>());
	public static Playlist playlist4 = new Playlist("Playlist 4", 3, new ArrayList<Song>());
	public static Playlist playlist5 = new Playlist("Playlist 5", 4, new ArrayList<Song>());
	public static Playlist playlist6 = new Playlist("Playlist 6", 5, new ArrayList<Song>());
	public static Playlist playlist7 = new Playlist("Playlist 7", 6, new ArrayList<Song>());
	public static Playlist playlist8 = new Playlist("Playlist 8", 7, new ArrayList<Song>());
	public static Playlist playlist9 = new Playlist("Playlist 9", 8, new ArrayList<Song>());


	
	public static void generateUsers() {
		users.add(new User("user1", "password1"));
		users.add(new User("user2", "password2"));
		users.add(new User("user3", "password3"));
		users.add(new User("user4", "password4"));
		users.add(new User("user5", "password5"));
	}

	public User getUserFromUsername(String username) {
		for (User user : users) {
			if (user.getName().equals(username)) {
				return user;
			}
		}
		return null;
	}

	// list of music genres
	public static String[] genres = { "Rock", "Pop", "Jazz", "Clásica", "Hip-Hop", "Reggae", "Electrónica", "Blues",
			"Country", "Metal" };

	// list of songs
	public static void generateSongs() {
		songs.add(new Song("Bohemian Rhapsody", 354, "Queen"));
		songs.add(new Song("Stairway to Heaven", 482, "Led Zeppelin"));
		songs.add(new Song("Hotel California", 391, "Eagles"));
		songs.add(new Song("Smells Like Teen Spirit", 301, "Nirvana"));
		songs.add(new Song("Imagine", 183, "John Lennon"));
		songs.add(new Song("Billie Jean", 294, "Michael Jackson"));
		songs.add(new Song("Sweet Child O' Mine", 356, "Guns N' Roses"));
		songs.add(new Song("Wonderwall", 258, "Oasis"));
		songs.add(new Song("Hey Jude", 431, "The Beatles"));
		songs.add(new Song("Lose Yourself", 326, "Eminem"));
		songs.add(new Song("Like a Rolling Stone", 369, "Bob Dylan"));
		songs.add(new Song("Comfortably Numb", 384, "Pink Floyd"));
		songs.add(new Song("Nothing Else Matters", 388, "Metallica"));
		songs.add(new Song("Under Pressure", 248, "Queen & David Bowie"));
		songs.add(new Song("One", 444, "U2"));
		songs.add(new Song("Enter Sandman", 331, "Metallica"));
		songs.add(new Song("Livin' on a Prayer", 249, "Bon Jovi"));
		songs.add(new Song("Back in Black", 255, "AC/DC"));
		songs.add(new Song("Thunderstruck", 292, "AC/DC"));
		songs.add(new Song("Hallelujah", 283, "Leonard Cohen"));
		songs.add(new Song("Yesterday", 125, "The Beatles"));
		songs.add(new Song("Shape of You", 233, "Ed Sheeran"));
		songs.add(new Song("Rolling in the Deep", 228, "Adele"));
		songs.add(new Song("Blinding Lights", 200, "The Weeknd"));
		songs.add(new Song("Take on Me", 225, "a-ha"));
		songs.add(new Song("Africa", 295, "Toto"));
		songs.add(new Song("Don’t Stop Believin’", 251, "Journey"));
		songs.add(new Song("Highway to Hell", 208, "AC/DC"));
		songs.add(new Song("Another One Bites the Dust", 217, "Queen"));
		songs.add(new Song("Beat It", 258, "Michael Jackson"));
		songs.add(new Song("Let It Be", 243, "The Beatles"));
		songs.add(new Song("Come Together", 259, "The Beatles"));
		songs.add(new Song("Smoke on the Water", 340, "Deep Purple"));
		songs.add(new Song("Paranoid", 171, "Black Sabbath"));
		songs.add(new Song("Seven Nation Army", 231, "The White Stripes"));
		songs.add(new Song("Karma Police", 260, "Radiohead"));
		songs.add(new Song("Creep", 238, "Radiohead"));
		songs.add(new Song("Fix You", 294, "Coldplay"));
		songs.add(new Song("Viva la Vida", 242, "Coldplay"));
		songs.add(new Song("Mr. Brightside", 222, "The Killers"));
		songs.add(new Song("Somebody Told Me", 197, "The Killers"));
		songs.add(new Song("Boulevard of Broken Dreams", 260, "Green Day"));
		songs.add(new Song("Holiday", 232, "Green Day"));
		songs.add(new Song("In the End", 216, "Linkin Park"));
		songs.add(new Song("Numb", 185, "Linkin Park"));
		songs.add(new Song("Chop Suey!", 210, "System of a Down"));
		songs.add(new Song("Toxicity", 217, "System of a Down"));
		songs.add(new Song("Californication", 329, "Red Hot Chili Peppers"));
		songs.add(new Song("Scar Tissue", 216, "Red Hot Chili Peppers"));
		songs.add(new Song("Dream On", 269, "Aerosmith"));
		songs.add(new Song("Sweet Emotion", 243, "Aerosmith"));
	}

	public static void generatePlaylists() {
		generateRandomPlaylist(playlist1);
		generateRandomPlaylist(playlist2);
		generateRandomPlaylist(playlist3);
		generateRandomPlaylist(playlist4);
		generateRandomPlaylist(playlist5);
		generateRandomPlaylist(playlist6);
		generateRandomPlaylist(playlist7);
		generateRandomPlaylist(playlist8);
		generateRandomPlaylist(playlist9);

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
		Random random = new Random();
		while (playlist.getN_songs() < 4) {
			Song song = songs.get(random.nextInt(songs.size()));
			if (!playlist.getL_songs().contains(song)) {
				playlist.addSong(song);
			}
		}
	}
}
