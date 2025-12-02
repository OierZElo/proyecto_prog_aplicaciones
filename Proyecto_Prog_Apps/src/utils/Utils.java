package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import controller.ManageDB;
import model.Genre;
import model.Playlist;
import model.Song;
import model.User;

public class Utils {
//    ManageDB managedb = new ManageDB();
	public static ArrayList<User> users = new ArrayList<User>();
//	public ArrayList<Song> songs = managedb.getSongs();
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

	// list of music genres
//	public static String[] genres = { "Rock", "Pop", "Jazz", "Clásica", "Hip-Hop", "Reggae", "Electrónica", "Blues",
//			"Country", "Metal" };

	// list of songs
//	public static void generateSongs() {
//	    songs.add(new Song("Bohemian Rhapsody", 354, "Queen", Genre.ROCK));
//	    songs.add(new Song("Stairway to Heaven", 482, "Led Zeppelin", Genre.ROCK));
//	    songs.add(new Song("Hotel California", 391, "Eagles", Genre.ROCK));
//	    songs.add(new Song("Smells Like Teen Spirit", 301, "Nirvana", Genre.METAL)); // Grunge → Metal
//	    songs.add(new Song("Imagine", 183, "John Lennon", Genre.POP));
//	    songs.add(new Song("Billie Jean", 294, "Michael Jackson", Genre.POP));
//	    songs.add(new Song("Sweet Child O' Mine", 356, "Guns N' Roses", Genre.ROCK)); // Hard Rock → Rock
//	    songs.add(new Song("Wonderwall", 258, "Oasis", Genre.ROCK)); // Britpop → Rock
//	    songs.add(new Song("Hey Jude", 431, "The Beatles", Genre.ROCK));
//	    songs.add(new Song("Lose Yourself", 326, "Eminem", Genre.HIPHOP));
//	    songs.add(new Song("Like a Rolling Stone", 369, "Bob Dylan", Genre.ROCK)); // Folk Rock → Rock
//	    songs.add(new Song("Comfortably Numb", 384, "Pink Floyd", Genre.ROCK)); // Progressive Rock → Rock
//	    songs.add(new Song("Nothing Else Matters", 388, "Metallica", Genre.METAL)); // Heavy Metal → Metal
//	    songs.add(new Song("Under Pressure", 248, "Queen & David Bowie", Genre.ROCK));
//	    songs.add(new Song("One", 444, "U2", Genre.ROCK));
//	    songs.add(new Song("Enter Sandman", 331, "Metallica", Genre.METAL));
//	    songs.add(new Song("Livin' on a Prayer", 249, "Bon Jovi", Genre.ROCK));
//	    songs.add(new Song("Back in Black", 255, "AC/DC", Genre.ROCK)); // Hard Rock → Rock
//	    songs.add(new Song("Thunderstruck", 292, "AC/DC", Genre.ROCK));
//	    songs.add(new Song("Hallelujah", 283, "Leonard Cohen", Genre.ROCK)); // Folk → Rock (no tenemos folk)
//	    songs.add(new Song("Yesterday", 125, "The Beatles", Genre.POP));
//	    songs.add(new Song("Shape of You", 233, "Ed Sheeran", Genre.POP));
//	    songs.add(new Song("Rolling in the Deep", 228, "Adele", Genre.POP)); // Soul → Pop
//	    songs.add(new Song("Blinding Lights", 200, "The Weeknd", Genre.ELECTRONICA)); // Synthpop → Electronica
//	    songs.add(new Song("Take on Me", 225, "a-ha", Genre.POP));
//	    songs.add(new Song("Africa", 295, "Toto", Genre.ROCK));
//	    songs.add(new Song("Don’t Stop Believin’", 251, "Journey", Genre.ROCK));
//	    songs.add(new Song("Highway to Hell", 208, "AC/DC", Genre.ROCK));
//	    songs.add(new Song("Another One Bites the Dust", 217, "Queen", Genre.ROCK));
//	    songs.add(new Song("Beat It", 258, "Michael Jackson", Genre.POP));
//	    songs.add(new Song("Let It Be", 243, "The Beatles", Genre.ROCK));
//	    songs.add(new Song("Come Together", 259, "The Beatles", Genre.ROCK));
//	    songs.add(new Song("Smoke on the Water", 340, "Deep Purple", Genre.ROCK));
//	    songs.add(new Song("Paranoid", 171, "Black Sabbath", Genre.METAL));
//	    songs.add(new Song("Seven Nation Army", 231, "The White Stripes", Genre.ROCK)); // Garage Rock → Rock
//	    songs.add(new Song("Karma Police", 260, "Radiohead", Genre.ROCK)); // Alternative Rock → Rock
//	    songs.add(new Song("Creep", 238, "Radiohead", Genre.ROCK));
//	    songs.add(new Song("Fix You", 294, "Coldplay", Genre.ROCK));
//	    songs.add(new Song("Viva la Vida", 242, "Coldplay", Genre.ROCK));
//	    songs.add(new Song("Mr. Brightside", 222, "The Killers", Genre.ROCK));
//	    songs.add(new Song("Somebody Told Me", 197, "The Killers", Genre.ROCK));
//	    songs.add(new Song("Boulevard of Broken Dreams", 260, "Green Day", Genre.ROCK)); // Punk Rock → Rock
//	    songs.add(new Song("Holiday", 232, "Green Day", Genre.ROCK));
//	    songs.add(new Song("In the End", 216, "Linkin Park", Genre.METAL)); // Nu Metal → Metal
//	    songs.add(new Song("Numb", 185, "Linkin Park", Genre.METAL));
//	    songs.add(new Song("Chop Suey!", 210, "System of a Down", Genre.METAL)); // Alternative Metal → Metal
//	    songs.add(new Song("Toxicity", 217, "System of a Down", Genre.METAL));
//	    songs.add(new Song("Californication", 329, "Red Hot Chili Peppers", Genre.ROCK)); // Funk Rock → Rock
//	    songs.add(new Song("Scar Tissue", 216, "Red Hot Chili Peppers", Genre.ROCK));
//	    songs.add(new Song("Dream On", 269, "Aerosmith", Genre.ROCK));
//	    songs.add(new Song("Sweet Emotion", 243, "Aerosmith", Genre.ROCK));
//	}



	public static void generatePlaylists(ArrayList<Song> songs) {
//		generateRandomPlaylist(playlist1, songs);
//		generateRandomPlaylist(playlist2, songs);
//		generateRandomPlaylist(playlist3, songs);
//		generateRandomPlaylist(playlist4, songs);
//		generateRandomPlaylist(playlist5, songs);
//		generateRandomPlaylist(playlist6, songs);
//		generateRandomPlaylist(playlist7, songs);
//		generateRandomPlaylist(playlist8, songs);
//		generateRandomPlaylist(playlist9, songs);

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

//	public static void generateRandomPlaylist(Playlist playlist, ArrayList<Song> songs) {
//		Random random = new Random();
//		while (playlist.getN_songs() < 30) {
//			Song song = songs.get(ThreadLocalRandom.current().nextInt(0, songs.size()-1));
//			if (!playlist.getL_songs().contains(song)) {
//				playlist.addSong(song);
//			}
//		}
//	}
    
    static {
    	Playlist rock = new Playlist("rock", 1);
        rock.getSongs().add(new Song("bohemian rhapsody", 354, "Queen", Genre.ROCK));
        rock.getSongs().add(new Song("stairway to heaven", 482, "Led Zeppelin", Genre.ROCK));
        rock.getSongs().add(new Song("hotel california", 391, "Eagles", Genre.ROCK));
        rock.getSongs().add(new Song("sweet child o' mine", 356, "Guns N' Roses", Genre.ROCK));
        rock.getSongs().add(new Song("back in black", 255, "AC/DC", Genre.ROCK));

        Playlist pop = new Playlist("pop", 1);
        pop.getSongs().add(new Song("thriller", 358, "Michael Jackson", Genre.POP));
        pop.getSongs().add(new Song("like a prayer", 342, "Madonna", Genre.POP));
        pop.getSongs().add(new Song("shape of you", 233, "Ed Sheeran", Genre.POP));
        pop.getSongs().add(new Song("rolling in the deep", 228, "Adele", Genre.POP));
        pop.getSongs().add(new Song("blinding lights", 200, "The Weeknd", Genre.ELECTRONICA));

        Playlist jazz = new Playlist("jazz", 1);
        jazz.getSongs().add(new Song("so what", 545, "Miles Davis", Genre.JAZZ));
        jazz.getSongs().add(new Song("take five", 324, "Dave Brubeck", Genre.JAZZ));
        jazz.getSongs().add(new Song("my favorite things", 799, "John Coltrane", Genre.JAZZ));
        jazz.getSongs().add(new Song("summertime", 556, "Ella Fitzgerald", Genre.JAZZ));
        jazz.getSongs().add(new Song("all of me", 426, "Louis Armstrong", Genre.JAZZ));

        Playlist clasica = new Playlist("clasica", 1);
        clasica.getSongs().add(new Song("symphony no. 5", 425, "Beethoven", Genre.CLASICA));
        clasica.getSongs().add(new Song("the four seasons", 2520, "Vivaldi", Genre.CLASICA));
        clasica.getSongs().add(new Song("canon in d", 376, "Pachelbel", Genre.CLASICA));
        clasica.getSongs().add(new Song("clair de lune", 300, "Debussy", Genre.CLASICA));
        clasica.getSongs().add(new Song("ride of the valkyries", 330, "Wagner", Genre.CLASICA));

        Playlist hip_hop = new Playlist("hip-hop", 1);
        hip_hop.getSongs().add(new Song("lose yourself", 326, "Eminem", Genre.HIPHOP));
        hip_hop.getSongs().add(new Song("juicy", 300, "The Notorious B.I.G.", Genre.HIPHOP));
        hip_hop.getSongs().add(new Song("c.r.e.a.m.", 249, "Wu-Tang Clan", Genre.HIPHOP));
        hip_hop.getSongs().add(new Song("sicko mode", 312, "Travis Scott", Genre.HIPHOP));
        hip_hop.getSongs().add(new Song("humble", 177, "Kendrick Lamar", Genre.HIPHOP));

        Playlist reggae = new Playlist("reggae", 1);
        reggae.getSongs().add(new Song("no woman no cry", 431, "Bob Marley", Genre.REGGAE));
        reggae.getSongs().add(new Song("one love", 176, "Bob Marley", Genre.REGGAE));
        reggae.getSongs().add(new Song("red red wine", 196, "UB40", Genre.REGGAE));
        reggae.getSongs().add(new Song("is this love", 218, "Bob Marley", Genre.REGGAE));
        reggae.getSongs().add(new Song("get up stand up", 210, "Bob Marley", Genre.REGGAE));

        Playlist electronica = new Playlist("electronica", 1);
        electronica.getSongs().add(new Song("one more time", 320, "Daft Punk", Genre.ELECTRONICA));
        electronica.getSongs().add(new Song("strobe", 630, "deadmau5", Genre.ELECTRONICA));
        electronica.getSongs().add(new Song("animals", 415, "Martin Garrix", Genre.ELECTRONICA));
        electronica.getSongs().add(new Song("scary monsters and nice sprites", 314, "Skrillex", Genre.ELECTRONICA));
        electronica.getSongs().add(new Song("levels", 224, "Avicii", Genre.ELECTRONICA));

        Playlist blues = new Playlist("blues", 1);
        blues.getSongs().add(new Song("the thrill is gone", 335, "B.B. King", Genre.BLUES));
        blues.getSongs().add(new Song("crossroads", 250, "Cream", Genre.BLUES));
        blues.getSongs().add(new Song("hoochie coochie man", 224, "Muddy Waters", Genre.BLUES));
        blues.getSongs().add(new Song("red house", 456, "Jimi Hendrix", Genre.BLUES));
        blues.getSongs().add(new Song("sweet home chicago", 285, "Robert Johnson", Genre.BLUES));

        Playlist country = new Playlist("country", 1);
        country.getSongs().add(new Song("friends in low places", 291, "Garth Brooks", Genre.COUNTRY));
        country.getSongs().add(new Song("jolene", 155, "Dolly Parton", Genre.COUNTRY));
        country.getSongs().add(new Song("take me home country roads", 191, "John Denver", Genre.COUNTRY));
        country.getSongs().add(new Song("the dance", 255, "Garth Brooks", Genre.COUNTRY));
        country.getSongs().add(new Song("ring of fire", 175, "Johnny Cash", Genre.COUNTRY));

        Playlist metal = new Playlist("metal", 1);
        metal.getSongs().add(new Song("master of puppets", 515, "Metallica", Genre.METAL));
        metal.getSongs().add(new Song("painkiller", 323, "Judas Priest", Genre.METAL));
        metal.getSongs().add(new Song("iron man", 356, "Black Sabbath", Genre.METAL));
        metal.getSongs().add(new Song("fear of the dark", 438, "Iron Maiden", Genre.METAL));
        metal.getSongs().add(new Song("chop suey", 210, "System of a Down", Genre.METAL));
    
    }
    
    
    
}
