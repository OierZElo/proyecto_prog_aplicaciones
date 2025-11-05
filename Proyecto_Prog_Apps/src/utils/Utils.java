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

	public static Playlist playlist1 = new Playlist("Playlist 1", 0);
	public static Playlist playlist2 = new Playlist("Playlist 2", 1);
	public static Playlist playlist3 = new Playlist("Playlist 3", 2);
	public static Playlist playlist4 = new Playlist("Playlist 4", 3);
	public static Playlist playlist5 = new Playlist("Playlist 5", 4);
	public static Playlist playlist6 = new Playlist("Playlist 6", 5);
	public static Playlist playlist7 = new Playlist("Playlist 7", 6);
	public static Playlist playlist8 = new Playlist("Playlist 8", 7);
	public static Playlist playlist9 = new Playlist("Playlist 9", 8);


	
	public static void generateUsers() {
		users.add(new User("user1@gmail.com", "password1"));
		users.add(new User("user2@gmail.com", "password2"));
		users.add(new User("user3@gmail.com", "password3"));
		users.add(new User("user4@gmail.com", "password4"));
		users.add(new User("user5@gmail.com", "password5"));
		users.add(new User("Enter your email...", ""));
	}

	public User getUserFromUsername(String username) {
		for (User user : users) {
			if (user.getMail().equals(username)) {
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
		while (playlist.getN_songs() < 30) {
			Song song = songs.get(random.nextInt(songs.size()));
			if (!playlist.getL_songs().contains(song)) {
				playlist.addSong(song);
			}
		}
	}
	
	// playlists por genero para home: 
	public static Playlist rock;
    public static Playlist pop;
    public static Playlist jazz;
    public static Playlist clasica;
    public static Playlist hip_hop;
    public static Playlist reggae;
    public static Playlist electronica;
    public static Playlist blues;
    public static Playlist country;
    public static Playlist metal;
    
    static {
    rock = new Playlist("rock", 1);
    rock.l_songs.add(new Song("bohemian rhapsody", 354, "Queen"));
    rock.l_songs.add(new Song("stairway to heaven", 482, "Led Zeppelin"));
    rock.l_songs.add(new Song("hotel california", 391, "Eagles"));
    rock.l_songs.add(new Song("sweet child o' mine", 356, "Guns N' Roses"));
    rock.l_songs.add(new Song("back in black", 255, "AC/DC"));

    pop = new Playlist("pop", 1);
    pop.l_songs.add(new Song("thriller", 358, "Michael Jackson"));
    pop.l_songs.add(new Song("like a prayer", 342, "Madonna"));
    pop.l_songs.add(new Song("shape of you", 233, "Ed Sheeran"));
    pop.l_songs.add(new Song("rolling in the deep", 228, "Adele"));
    pop.l_songs.add(new Song("blinding lights", 200, "The Weeknd"));

    jazz = new Playlist("jazz", 1);
    jazz.l_songs.add(new Song("so what", 545, "Miles Davis"));
    jazz.l_songs.add(new Song("take five", 324, "Dave Brubeck"));
    jazz.l_songs.add(new Song("my favorite things", 799, "John Coltrane"));
    jazz.l_songs.add(new Song("summertime", 556, "Ella Fitzgerald"));
    jazz.l_songs.add(new Song("all of me", 426, "Louis Armstrong"));

    clasica = new Playlist("clasica", 1);
    clasica.l_songs.add(new Song("symphony no. 5", 425, "Beethoven"));
    clasica.l_songs.add(new Song("the four seasons", 2520, "Vivaldi"));
    clasica.l_songs.add(new Song("canon in d", 376, "Pachelbel"));
    clasica.l_songs.add(new Song("clair de lune", 300, "Debussy"));
    clasica.l_songs.add(new Song("ride of the valkyries", 330, "Wagner"));

    hip_hop = new Playlist("hip-hop", 1);
    hip_hop.l_songs.add(new Song("lose yourself", 326, "Eminem"));
    hip_hop.l_songs.add(new Song("juicy", 300, "The Notorious B.I.G."));
    hip_hop.l_songs.add(new Song("c.r.e.a.m.", 249, "Wu-Tang Clan"));
    hip_hop.l_songs.add(new Song("sicko mode", 312, "Travis Scott"));
    hip_hop.l_songs.add(new Song("humble", 177, "Kendrick Lamar"));

    reggae = new Playlist("reggae", 1);
    reggae.l_songs.add(new Song("no woman no cry", 431, "Bob Marley"));
    reggae.l_songs.add(new Song("one love", 176, "Bob Marley"));
    reggae.l_songs.add(new Song("red red wine", 196, "UB40"));
    reggae.l_songs.add(new Song("is this love", 218, "Bob Marley"));
    reggae.l_songs.add(new Song("get up stand up", 210, "Bob Marley"));

    electronica = new Playlist("electronica", 1);
    electronica.l_songs.add(new Song("one more time", 320, "Daft Punk"));
    electronica.l_songs.add(new Song("strobe", 630, "deadmau5"));
    electronica.l_songs.add(new Song("animals", 415, "Martin Garrix"));
    electronica.l_songs.add(new Song("scary monsters and nice sprites", 314, "Skrillex"));
    electronica.l_songs.add(new Song("levels", 224, "Avicii"));

    blues = new Playlist("blues", 1);
    blues.l_songs.add(new Song("the thrill is gone", 335, "B.B. King"));
    blues.l_songs.add(new Song("crossroads", 250, "Cream"));
    blues.l_songs.add(new Song("hoochie coochie man", 224, "Muddy Waters"));
    blues.l_songs.add(new Song("red house", 456, "Jimi Hendrix"));
    blues.l_songs.add(new Song("sweet home chicago", 285, "Robert Johnson"));

    country = new Playlist("country", 1);
    country.l_songs.add(new Song("friends in low places", 291, "Garth Brooks"));
    country.l_songs.add(new Song("jolene", 155, "Dolly Parton"));
    country.l_songs.add(new Song("take me home country roads", 191, "John Denver"));
    country.l_songs.add(new Song("the dance", 255, "Garth Brooks"));
    country.l_songs.add(new Song("ring of fire", 175, "Johnny Cash"));

    metal = new Playlist("metal", 1);
    metal.l_songs.add(new Song("master of puppets", 515, "Metallica"));
    metal.l_songs.add(new Song("painkiller", 323, "Judas Priest"));
    metal.l_songs.add(new Song("iron man", 356, "Black Sabbath"));
    metal.l_songs.add(new Song("fear of the dark", 438, "Iron Maiden"));
    metal.l_songs.add(new Song("chop suey", 210, "System of a Down"));
    
    }
    
    
    
}
