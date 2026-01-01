package view;

import java.util.ArrayList;
import java.util.Collections;

import controller.ManageDB;
import model.Genre;
import model.Playlist;
import model.Song;
import model.User;

import java.util.List;

public class Recursivity {
	private static ManageDB managedb = ManageDB.getInstance();
	private static MainFrame main = MainFrame.getInstance();
	static User test = new User("test@gmail.com", "qwerty", "test");


	public static void generatePlayLists(ArrayList<Genre> genres, int duration, int maxSongNumber, int playListNumber) {
		System.out.println("Generando combinaciones...");
		ArrayList<Song> filteredSongs = managedb.getSongsPerGenreList(genres);
		ArrayList<ArrayList<Song>> result = new ArrayList<ArrayList<Song>>();

		generateCombinations(result, filteredSongs, maxSongNumber, duration, playListNumber, new ArrayList<Song>());
		for (ArrayList<Song> p : result) {
			System.out.println(p);
		}
	}

	public static void generateCombinations(ArrayList<ArrayList<Song>> result, ArrayList<Song> songs, int maxSongNumber,
			int duration, int playListNumber, ArrayList<Song> aux) {
		if ((duration == 0 || maxSongNumber == aux.size()) && result.size() < playListNumber) {
			List<Song> orderedPlaylist = new ArrayList<Song>(aux);
			Collections.sort(orderedPlaylist);
			if (!result.contains(orderedPlaylist)) {
				System.out.println("posible playlist añadida");
				result.add((ArrayList<Song>)orderedPlaylist);
			}
			return;
		} else {
			for (Song s : songs) {
				if (!aux.contains(s) && s.getDuration() <= duration && aux.size() < maxSongNumber) {
					aux.add(s);
					generateCombinations(result, songs, maxSongNumber, duration - s.getDuration(), playListNumber, aux);
					aux.remove(aux.size() - 1);
				}
			}
		}
	}

//	public static void main(String[] args) {
//		ArrayList<Genre> genres = new ArrayList<Genre>();
//		genres.add(Genre.METAL);
//		genres.add(Genre.ELECTRONICA);
//		
//
//		generatePlayLists(genres, 2000, 2, 3);
//        ArrayList<Song> testSongs = managedb.getSongsPerGenreList(genres);
//        for(Song s:testSongs) {
//        	System.out.println(s.getTitle());
//        }
//
//	}
}
