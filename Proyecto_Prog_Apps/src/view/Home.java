package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Genre;
import model.Playlist;
import model.Song;
import utils.Utils;

public class Home {

	public static JPanel HomePanel() {
        MainFrame main = MainFrame.getInstance();

		JPanel result = new JPanel(new BorderLayout());
		result.setBackground(MainFrame.BackgroundColor);
		
		JPanel norte = new JPanel(new GridLayout(0, 2, 10, 10));
		norte.setBackground(MainFrame.BackgroundColor);
		result.add(norte, BorderLayout.NORTH);
		
		JPanel quickPicks = new JPanel(new BorderLayout());
		quickPicks.setBackground(MainFrame.BackgroundColor);
		
		LinkedList<Color> colores = new LinkedList<>();
		colores.add(Color.RED);
		colores.add(Color.BLUE);
		colores.add(Color.GREEN);
		colores.add(Color.YELLOW);
		colores.add(Color.ORANGE);
		colores.add(Color.MAGENTA); 
		colores.add(Color.PINK);
		colores.add(new Color(139, 69, 19)); // Marrón
		colores.add(Color.GRAY);
		colores.add(new Color(64, 224, 208)); // Turquesa
		// Canciones por genero: 
		
		// organización del display de generos 
		for (int i = 0; i < Genre.values().length; i++) {
			JButton boton = new JButton(Genre.values()[i].toString().toUpperCase());
			boton.setBackground(MainFrame.BackgroundColor);
			boton.setHorizontalTextPosition(SwingConstants.CENTER);
			boton.setFont(new Font("Arial", Font.BOLD, 20));
			boton.setForeground(Color.WHITE);
			boton.setBackground(colores.pop());
			boton.setOpaque(true);
			norte.add(boton);

			final int index = i;

			boton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JPanel panel = new JPanel();; 
					String name = "";
					switch(index) {
					case 0:
						try {
						 ArrayList<Song> song = ConfigManager.managedb.getSongsPerGenre(Genre.ROCK);
						 panel = songTable.createSongTableArrayList(song); } 
						catch (Exception e2) {
							
						}
						 name = "Rock";
						 break;
					case 1:
						try {
							 ArrayList<Song> song = ConfigManager.managedb.getSongsPerGenre(Genre.POP);
							 panel = songTable.createSongTableArrayList(song); } 
							catch (Exception e2) {
								
							}						 name = "Pop";

						 break;

					case 2:
						try {
							 ArrayList<Song> song = ConfigManager.managedb.getSongsPerGenre(Genre.JAZZ);
							 panel = songTable.createSongTableArrayList(song); } 
							catch (Exception e2) {
								
							}						
						name = "Jazz";

						 break;

					case 3:
						try {
							 ArrayList<Song> song = ConfigManager.managedb.getSongsPerGenre(Genre.CLASICA);
							 panel = songTable.createSongTableArrayList(song); } 
							catch (Exception e2) {
								
							}											 
						name = "Clasica";

						 break;

					case 4: 
						try {
							 ArrayList<Song> song = ConfigManager.managedb.getSongsPerGenre(Genre.HIPHOP);
							 panel = songTable.createSongTableArrayList(song); } 
							catch (Exception e2) {
								
							}											 
						name = "HipHop";

						 break;

					case 5:
						try {
							 ArrayList<Song> song = ConfigManager.managedb.getSongsPerGenre(Genre.REGGAE);
							 panel = songTable.createSongTableArrayList(song); } 
							catch (Exception e2) {
								
							}					
						name = "Reggae";

						 break;

					case 6:
						try {
							 ArrayList<Song> song = ConfigManager.managedb.getSongsPerGenre(Genre.ELECTRONICA);
							 panel = songTable.createSongTableArrayList(song); } 
							catch (Exception e2) {
								
							}					
						name = "Electronica";

						 break;

					case 7:
						try {
							 ArrayList<Song> song = ConfigManager.managedb.getSongsPerGenre(Genre.BLUES);
							 panel = songTable.createSongTableArrayList(song); } 
							catch (Exception e2) {
								
							}					
						name = "Blues";

						 break;

					case 8:

						try {
							 ArrayList<Song> song = ConfigManager.managedb.getSongsPerGenre(Genre.COUNTRY);
							 panel = songTable.createSongTableArrayList(song); } 
							catch (Exception e2) {
								
							}					
						name = "Country";

						 break;

					case 9:
						try {
							 ArrayList<Song> song = ConfigManager.managedb.getSongsPerGenre(Genre.METAL);
							 panel = songTable.createSongTableArrayList(song); } 
							catch (Exception e2) {
								
							}					
						name = "Metal";

						 break;
					}

			        result.add(panel, BorderLayout.CENTER);
		            main.getCardPanel().add(panel, name);
		            main.getCardLayout().show(main.getCardPanel(), name);
				}
			});
		}
		
		// panel para random Picks 
//		Utils.generateRandomPlaylist(Utils.playlist1, main.getSongs());
		
		quickPicks.add(songTable.createSongTablePlaylist(Utils.playlist1), BorderLayout.CENTER);

		result.add(quickPicks, BorderLayout.CENTER);
		JLabel x = new JLabel("QUICK PICKS");
		x.setFont(new Font("Arial", Font.PLAIN, 24));
		x.setHorizontalAlignment(JLabel.CENTER);
		x.setForeground(MainFrame.TextColor);
		quickPicks.add(x, BorderLayout.NORTH);
	
		return result;

	}
}
