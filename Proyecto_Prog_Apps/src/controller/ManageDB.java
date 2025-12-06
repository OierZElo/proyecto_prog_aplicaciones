package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Genre;
import model.Playlist;
import model.Song;
import model.User;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;

public class ManageDB {
	protected static final String databaseFile = "src/resources/db/database.db";
	private final String driverName = "org.sqlite.JDBC";
	private final String connectionString = "jdbc:sqlite:" + databaseFile;
	private static ManageDB instance = null;

	private ManageDB() {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			System.out.println("Error loading driver: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void crearBBDD() {
		String dropSong = "DROP TABLE songs;";
		String dropUser = "DROP TABLE user;";
		String dropPlaylist = "DROP TABLE playlist;";
		String dropPlaylistSong = "DROP TABLE playlist_songs;";
		
		String sqlSong = "CREATE TABLE IF NOT EXISTS songs (" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "name TEXT NOT NULL," + "band TEXT NOT NULL," + "duration INTEGER NOT NULL," + "genre TEXT NOT NULL"
				+ ");";

		String sqlUsuario = "CREATE TABLE IF NOT EXISTS user (" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "name TEXT NOT NULL," + "email TEXT NOT NULL UNIQUE," + "password TEXT NOT NULL" + ");";

		String sqlPlaylist = "CREATE TABLE IF NOT EXISTS playlist (" + "cod INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "name TEXT NOT NULL," + "user_id INTEGER NOT NULL,"
				+ "FOREIGN KEY(user_id) REFERENCES user(id) ON DELETE CASCADE" + ");";

		String sqlPlaylistSongs = "CREATE TABLE IF NOT EXISTS playlist_songs ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT," + "playlist_cod INTEGER NOT NULL,"
				+ "song_id INTEGER NOT NULL," + "FOREIGN KEY(playlist_cod) REFERENCES playlist(cod) ON DELETE CASCADE,"
				+ "FOREIGN KEY(song_id) REFERENCES songs(id) ON DELETE CASCADE" + ");";

		try (Connection con = DriverManager.getConnection(connectionString);
				PreparedStatement psDropSong = con.prepareStatement(dropSong);
				PreparedStatement psDropUser = con.prepareStatement(dropUser);
				PreparedStatement psDropPlaylist = con.prepareStatement(dropPlaylist);
				PreparedStatement psDropPlaylistSong = con.prepareStatement(dropPlaylistSong);
				
				PreparedStatement psSong = con.prepareStatement(sqlSong);
				PreparedStatement psUsuario = con.prepareStatement(sqlUsuario);
				PreparedStatement psPlaylist = con.prepareStatement(sqlPlaylist);
				PreparedStatement psPlaylistSongs = con.prepareStatement(sqlPlaylistSongs);) {

			psDropSong.execute();
			psDropUser.execute();
			psDropPlaylist.execute();
			psDropPlaylistSong.execute();
			
			psSong.execute();
			psUsuario.execute();
			psPlaylist.execute();
			psPlaylistSongs.execute();

			System.out.println("Tables created succesfully.");
		} catch (Exception e) {
			System.out.println("Error creating tables: " + e.getMessage());
		}
	}

	public void insertSong(Song... songs) {
		String sql = "INSERT INTO songs (name, band, duration, genre) VALUES (?, ?, ?, ?);";

		try (Connection con = DriverManager.getConnection(connectionString);
				PreparedStatement ps = con.prepareStatement(sql)) {

			for (Song s : songs) {
				ps.setString(1, s.getTitle());
				ps.setString(2, s.getBand());
				ps.setInt(3, s.getDuration());
				ps.setString(4, s.getGenre().toString());

				int affected = ps.executeUpdate();
				if (affected == 1) {
					System.out.println("Song inserted: " + s.getTitle());
				} else {
					System.out.println("Couldn't insert song: " + s.getTitle());
				}
			}

		} catch (Exception e) {
			System.out.println("Error inserting song: " + e.getMessage());
		}
	}

	public void insertUser(User... users) {
		String sql = "INSERT INTO \"user\" (name, email, password) VALUES (?, ?, ?);";

		try (Connection con = DriverManager.getConnection(connectionString);
				PreparedStatement ps = con.prepareStatement(sql)) {

			for (User u : users) {
				ps.setString(1, u.getName());
				ps.setString(2, u.getMail());
				ps.setString(3, u.getPassword());

				int affected = ps.executeUpdate();
				if (affected == 1) {
					System.out.println("User inserted: " + u.getName());
				} else {
					System.out.println("Couldn't insert user: " + u.getName());
				}
			}

		} catch (Exception e) {
			System.out.println("Error inserting user: " + e.getMessage());
		}
	}

	public void insertPlaylist(Playlist... playlists) {
		String sql = "INSERT INTO playlist (name, user_id) VALUES (?, ?);";

		try (Connection con = DriverManager.getConnection(connectionString);
				PreparedStatement ps = con.prepareStatement(sql)) {

			for (Playlist p : playlists) {
				ps.setString(1, p.getName());
				ps.setInt(2, p.getUser_id());

				int affected = ps.executeUpdate();
				if (affected == 1) {
					System.out.println("Playlist inserted: " + p.getName());
				} else {
					System.out.println("Couldn't inser playlist: " + p.getName());
				}
			}

		} catch (Exception e) {
			System.out.println("Error inserting playlist: " + e.getMessage());
		}
	}
	
	public void deletePlaylist(int id) {
		String sql = "DELETE FROM playlist WHERE cod = ?;";

		try (Connection con = DriverManager.getConnection(connectionString);
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);

			int affected = ps.executeUpdate();
			if (affected == 1) {
				System.out.println("Playlist deleted, id=" + id);
			} else {
				System.out.println("Couldn't find playlist with id=" + id);
			}

		} catch (Exception e) {
			System.out.println("Error deleting playlist: " + e.getMessage());
		}
	}
	
	public void deleteUser(int id) {
		String sql = "DELETE FROM user WHERE id = ?;";

		try (Connection con = DriverManager.getConnection(connectionString);
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);

			int affected = ps.executeUpdate();
			if (affected == 1) {
				System.out.println("User deleted, id=" + id);
			} else {
				System.out.println("Couldn't find user with id=" + id);
			}

		} catch (Exception e) {
			System.out.println("Error deleting user: " + e.getMessage());
		}
	}

	public void deleteSong(int id) {
		String sql = "DELETE FROM songs WHERE id = ?;";

		try (Connection con = DriverManager.getConnection(connectionString);
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);

			int affected = ps.executeUpdate();
			if (affected == 1) {
				System.out.println("Song deleted, id=" + id);
			} else {
				System.out.println("Couldn't find song with id=" + id);
			}

		} catch (Exception e) {
			System.out.println("Error deleting song: " + e.getMessage());
		}
	}

//	public ArrayList<Song> getSongs() {
//		ArrayList<Song> list = new ArrayList<>();
//		String sql = "SELECT id, name, band, duration, genre FROM songs;";
//		
//		try (Connection con = DriverManager.getConnection(connectionString);
//				PreparedStatement ps = con.prepareStatement(sql);
//				ResultSet rs = ps.executeQuery()) {
//
//			while (rs.next()) {
//				Song s = new Song(rs.getString("name"), rs.getInt("duration"), rs.getString("band"),
//						Genre.valueOf(rs.getString("genre").toString().toUpperCase()));
//				list.add(s);
//			}
//
//		} catch (Exception e) {
//			System.out.println("Error getting songs: " + e.getMessage());
//		}
//
//		return list;
//	}

	// MAAAL
//	public ArrayList<User> getUsers() {
//		ArrayList<User> list = new ArrayList<>();
//		String sql = "SELECT id, mail, passwd, username FROM user;";
//
//		try (Connection con = DriverManager.getConnection(connectionString);
//				PreparedStatement ps = con.prepareStatement(sql);
//				ResultSet rs = ps.executeQuery()) {
//
//			while (rs.next()) {
//				User u = new User(rs.getString("mail"), rs.getString("passwd"), rs.getString("username"));
//				list.add(u);
//			}
//
//		} catch (Exception e) {
//			System.out.println("Error getting users: " + e.getMessage());
//		}
//		return list;
//	}
	
	public int getSongCount() {
	    String sql = "SELECT COUNT(*) AS total FROM songs;";
	    int total = 0;

	    try (Connection con = DriverManager.getConnection(connectionString);
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            total = rs.getInt("total");
	        }

	    } catch (Exception e) {
	        System.out.println("Error counting songs: " + e.getMessage());
	    }

	    return total;
	}


	public Song getSongById(int id) {
		String sql = "SELECT name, band, duration, genre FROM songs WHERE id = ?;";
		Song song = null;

		try (Connection con = DriverManager.getConnection(connectionString);
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					song = new Song(rs.getString("name"), rs.getInt("duration"), rs.getString("band"),
							Genre.valueOf(rs.getString("genre")));
				}
			}

		} catch (Exception e) {
			System.out.println("Error getting song: " + e.getMessage());
		}

		return song; // Retorna null si no se encontró
	}

	public static ManageDB getInstance() {
		if (instance == null) {
			instance = new ManageDB();
		}
		return instance;
	}
	
	public void loadUsersFromCSV(String csvPath) {
	    String sql = "INSERT INTO user (name, email, password) VALUES (?, ?, ?);";
	    
	    try (Connection con = DriverManager.getConnection(connectionString);
	         PreparedStatement ps = con.prepareStatement(sql);
	         BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
	        
	        String line = br.readLine();
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(",");
	            if (parts.length < 3) continue;
	            
	            ps.setString(1, parts[0].trim());
	            ps.setString(2, parts[1].trim());
	            ps.setString(3, parts[2].trim());
	            ps.executeUpdate();
	        }
	        System.out.println("Users loaded from CSV.");
	    } catch (Exception e) {
	        System.out.println("Error loading users: " + e.getMessage());
	    }
	}
	
	public void loadSongsFromCSV(String csvPath) {
	    String sql = "INSERT INTO songs (name, band, duration, genre) VALUES (?, ?, ?, ?);";

	    try (Connection con = DriverManager.getConnection(connectionString);
	         PreparedStatement ps = con.prepareStatement(sql);
	         BufferedReader br = new BufferedReader(new FileReader(csvPath))) {

	        String line = br.readLine(); // Leer encabezado
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(",");
	            if (parts.length < 4) continue;

	            ps.setString(1, parts[0].trim());
	            ps.setString(2, parts[1].trim());
	            ps.setInt(3, Integer.parseInt(parts[2].trim()));
	            ps.setString(4, parts[3].trim());
	            ps.executeUpdate();
	        }
	        System.out.println("Songs loaded from CSV.");
	    } catch (Exception e) {
	        System.out.println("Error loading songs: " + e.getMessage());
	    }
	}

	public void loadPlaylistsFromCSV(String csvPath) {
	    String sql = "INSERT INTO playlist (name, user_id) VALUES (?, ?);";

	    try (Connection con = DriverManager.getConnection(connectionString);
	         PreparedStatement ps = con.prepareStatement(sql);
	         BufferedReader br = new BufferedReader(new FileReader(csvPath))) {

	        String line = br.readLine(); // Leer encabezado
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(",");
	            if (parts.length < 2) continue;

	            ps.setString(1, parts[0].trim());
	            ps.setInt(2, Integer.parseInt(parts[1].trim()));
	            ps.executeUpdate();
	        }
	        System.out.println("Playlists loaded from CSV.");
	    } catch (Exception e) {
	        System.out.println("Error loading playlists: " + e.getMessage());
	    }
	}
	
	public void loadPlaylistSongsFromCSV(String csvPath) {
	    String sql = "INSERT INTO playlist_songs (playlist_cod, song_id) VALUES (?, ?);";

	    try (Connection con = DriverManager.getConnection(connectionString);
	         PreparedStatement ps = con.prepareStatement(sql);
	         BufferedReader br = new BufferedReader(new FileReader(csvPath))) {

	        String line = br.readLine(); // Leer encabezado
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(",");
	            if (parts.length < 2) continue;

	            ps.setInt(1, Integer.parseInt(parts[0].trim()));
	            ps.setInt(2, Integer.parseInt(parts[1].trim()));
	            ps.executeUpdate();
	        }
	        System.out.println("Playlist songs loaded from CSV.");
	    } catch (Exception e) {
	        System.out.println("Error loading playlist songs: " + e.getMessage());
	    }
	}

	public boolean isEmailInDB(String email) {
		String sql = "SELECT * FROM user WHERE email=?;";
		
		try (Connection con = DriverManager.getConnection(connectionString);
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			} else {
				return false;
			}
			
		} catch (Exception e) {
			System.out.println("Error searching for user: " + e.getMessage());
			return false;
		}
		
	}
	
	public boolean isUsernameInDB(String username) {
		String sql = "SELECT * FROM user WHERE name=?;";
		
		try (Connection con = DriverManager.getConnection(connectionString);
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			} else {
				return false;
			}
			
		} catch (Exception e) {
			System.out.println("Error searching for user: " + e.getMessage());
			return false;
		}
		
	}
	
	public User getUserFromEmail(String email) {
		String sql = "SELECT * FROM user WHERE email = ?;";
		
		try (Connection con = DriverManager.getConnection(connectionString);
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1,email);
			ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                User u = new User(rs.getString("name"), rs.getString("email"), rs.getString("password"));
                u.setId(rs.getInt("id")); 
                return u;
            }
			
		} catch (Exception e) {
			System.out.println("Error getting user: "+e.getMessage());
		}
        return null;	
	}
	
	public String getPasswordFromEmail(String email) {
		String sql = "SELECT password FROM user WHERE email=?;";
		try (Connection con = DriverManager.getConnection(connectionString);
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			return rs.getString("password");
			
		} catch (Exception e) {
			System.out.println("Error getting password: " + e.getMessage());
			return null;
		}
	} 
	
		
	public ArrayList<Song> getSongsPerGenre(Genre genre) {
	    String sql = "SELECT * FROM songs WHERE genre = ? ORDER BY name LIMIT 10;";
		try (Connection con = DriverManager.getConnection(connectionString);
				PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setString(1, genre.name());  // pasar el valor del enum como string

				ArrayList<Song> songs = new ArrayList<Song>();
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Song s = new Song(rs.getString("name"), rs.getInt("duration"), rs.getString("band"), Genre.valueOf(rs.getString("genre")));
					s.setId(rs.getInt("id"));
					songs.add(s);
				}
				return songs;
			
		} catch (Exception e) {
			System.out.println("Error getting songs " + e.getMessage());
			return null;
		}
	
	}
	
	public ArrayList<Song> getRandomSongs() {
		 String sql = "SELECT * FROM songs  ORDER BY random() LIMIT 10;";
			try (Connection con = DriverManager.getConnection(connectionString);
					PreparedStatement ps = con.prepareStatement(sql) ) {
					ArrayList<Song> songs = new ArrayList<Song>();
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						Song s = new Song(rs.getString("name"), rs.getInt("duration"), rs.getString("band"), Genre.valueOf(rs.getString("genre")));
						s.setId(rs.getInt("id"));
						songs.add(s);
					}
					return songs;
				
			} catch (Exception e) {
				System.out.println("Error getting random songs: " + e.getMessage());
				return null;
			}
	}
	
	// luego mirar como hacer para que funcione con el PK id
	public void updatePassword(String email, String password) {
		String sql = "UPDATE user SET password = ? where id = ?";
		try (Connection con = DriverManager.getConnection(connectionString);
				PreparedStatement ps = con.prepareStatement(sql) ) {
				ps.setString(1, password);  // we replace the first ? with the new password of the user
				ps.setString(2, email); // we replace the second ? with the id of the user
				int affectedRows  = ps.executeUpdate(); 
				if (affectedRows > 0) {
		            System.out.println("se ha actualizado correctamente el usuario de email: " + email);

		        } else {
		            System.out.println("No username found with email " + email);
		        }
			
		} catch (Exception e) {
			System.out.println("Error updating password: " + e.getMessage());
		}
		
	}
	
	public ArrayList<Playlist> getUserPlaylists(int userId) {
	    ArrayList<Playlist> playlists = new ArrayList<>();
	    String sql = "SELECT cod, name FROM playlist WHERE user_id = ?;";

	    try (Connection con = DriverManager.getConnection(connectionString);
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Playlist p = new Playlist(rs.getString("name"), userId);
	            p.setCod(rs.getInt("cod")); 
	            ArrayList<Song> songs = getSongsByPlaylistId(rs.getInt("cod"));
	            for(Song s : songs) {
	                p.addSong(s); 
	            }
	            
	            playlists.add(p);
	        }

	    } catch (Exception e) {
	        System.out.println("Error getting user playlists: " + e.getMessage());
	    }
	    return playlists;
	}

    private ArrayList<Song> getSongsByPlaylistId(int playlistCod) {
        ArrayList<Song> songs = new ArrayList<>();
        String sql = "SELECT s.id, s.name, s.band, s.duration, s.genre " +
                     "FROM songs s " +
                     "JOIN playlist_songs ps ON s.id = ps.song_id " +
                     "WHERE ps.playlist_cod = ?;";

        try (Connection con = DriverManager.getConnection(connectionString);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, playlistCod);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Song s = new Song(
                    rs.getString("name"), 
                    rs.getInt("duration"), 
                    rs.getString("band"), 
                    Genre.valueOf(rs.getString("genre"))
                );
                s.setId(rs.getInt("id"));
                songs.add(s);
            }

        } catch (Exception e) {
            System.out.println("Error getting playlist songs: " + e.getMessage());
        }
        return songs;
    }
    
 // Método para inicializar datos de prueba si no existen
 	public void initData() {

 		String testName = "1";
 		String testEmail = "1"; 
 		String testPass = "1";
 		if (!isEmailInDB(testEmail)) {
 			insertUser(new User(testName, testEmail, testPass));
 			System.out.println("INIT: Usuario de prueba creado.");
 		}
 		
 		User u = getUserFromEmail(testEmail);
 		if (u != null) {
 			int userId = u.getId();
 			ArrayList<Playlist> currentPlaylists = getUserPlaylists(userId);
 			
 			if (currentPlaylists.isEmpty()) {
 				System.out.println("INIT: Insertando playlists de prueba...");
 				
 				Playlist p1 = new Playlist("Rock Clásico", userId);
 				Playlist p2 = new Playlist("Gym Motivation", userId);
 				Playlist p3 = new Playlist("Viaje en Coche", userId);
 				Playlist p4 = new Playlist("Estudiar Lo-Fi", userId);
 				Playlist p5 = new Playlist("Fiesta 2024", userId);
 				
 				insertPlaylist(p1, p2, p3, p4, p5);
 				System.out.println("INIT: Playlists insertadas correctamente.");
 			} else {
 				System.out.println("INIT: El usuario ya tiene playlists.");
 			}
 		}
 	}
}
