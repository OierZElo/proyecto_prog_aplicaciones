package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Genre;
import model.Playlist;
import model.Song;
import model.User;
import view.MainFrame;

import java.sql.Connection;
import java.sql.DriverManager;


public class ManageDB {
	 protected static final String databaseFile = "src/resources/db/database.db";
	 private final String driverName = "org.sqlite.JDBC";
	 private final String connectionString = "jdbc:sqlite:" + databaseFile;
	 private static ManageDB instance = new ManageDB();
	 

	 private void ManageBD() {
	        try {
	            Class.forName(driverName);
	        } catch (ClassNotFoundException e) {
	            System.out.println("Error loading driver: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	 
	 public void crearBBDD() {
	        String sqlSong = "CREATE TABLE IF NOT EXISTS songs ("
	                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
	                + "name TEXT NOT NULL,"
	                + "band TEXT NOT NULL,"
	                + "duration INTEGER NOT NULL,"
	                + "genre TEXT NOT NULL"
	                + ");";

	        String sqlUsuario = "CREATE TABLE IF NOT EXISTS user ("
	                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
	                + "name TEXT NOT NULL,"
	                + "email TEXT NOT NULL UNIQUE,"
	                + "password TEXT NOT NULL"
	                + ");";

	        String sqlPlaylist = "CREATE TABLE IF NOT EXISTS playlist ("
	                + "cod INTEGER PRIMARY KEY AUTOINCREMENT,"
	                + "name TEXT NOT NULL,"
	                + "user_id INTEGER NOT NULL,"
	                + "FOREIGN KEY(user_id) REFERENCES User(id) ON DELETE CASCADE"
	                + ");";
	        
	        try (Connection con = DriverManager.getConnection(connectionString);
	             PreparedStatement psSong = con.prepareStatement(sqlSong);
	             PreparedStatement psUsuario = con.prepareStatement(sqlUsuario);
	             PreparedStatement psPlaylist = con.prepareStatement(sqlPlaylist)) {

	            psSong.execute();
	            psUsuario.execute();
	            psPlaylist.execute();

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
		    String sql = "INSERT INTO user (name, email, password) VALUES (?, ?, ?);";

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

	 public void deleteSong(int id) {
		    String sql = "DELETE FROM songs WHERE id = ?;";

		    try (Connection con = DriverManager.getConnection(connectionString);
		         PreparedStatement ps = con.prepareStatement(sql)) {

		        ps.setInt(1, id);

		        int affected = ps.executeUpdate();
		        if (affected == 1) {
		            System.out.println("Song deleted, id=" + id);
		        } else {
		            System.out.println("Couldn't find son with id=" + id);
		        }

		    } catch (Exception e) {
		        System.out.println("Error deleting song: " + e.getMessage());
		    }
		}

	 public ArrayList<Song> getSongs() {
		    ArrayList<Song> lista = new ArrayList<>();
		    String sql = "SELECT id, title, duration, band, genre FROM songs;";

		    try (Connection con = DriverManager.getConnection(connectionString);
		         PreparedStatement ps = con.prepareStatement(sql);
		         ResultSet rs = ps.executeQuery()) {

		        while (rs.next()) {
		            Song s = new Song(
		                rs.getString("title"),
		                rs.getInt("duration"),
		                rs.getString("band"),
		                Genre.valueOf(rs.getString("genre").toString().toUpperCase())
		            );
		            lista.add(s);
		        }

		    } catch (Exception e) {
		        System.out.println("Error getting songs: " + e.getMessage());
		    }

		    return lista;
		}

	 public static ManageDB getInstance() {
		    if (instance == null) {
		    	instance = new ManageDB();
		    }
		    return instance;
		}
	 	
}
