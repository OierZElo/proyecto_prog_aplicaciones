package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.LinkedList;


import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Playlist;
import utils.Utils;

public class Home {

	public static JPanel HomePanel() {
		JPanel result = new JPanel();
		result.setName("home");
		result.setBackground(MainFrame.BackgroundColor);
		result.setLayout(new BorderLayout());
		JPanel norte = new JPanel();
		norte.setLayout(new GridLayout(Math.round(Utils.genres.length / 2), 0, 10, 10));
		norte.setBackground(MainFrame.BackgroundColor);

		result.add(norte, BorderLayout.NORTH);
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
		
		// organización del display de generos 
		for (int i = 0; i < Utils.genres.length; i++) {
			JButton boton = new JButton(Utils.genres[i].toUpperCase());
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
						 panel = songTable.createSongTablePlaylist(Utils.rock);
						 name = "Rock";
						 break;
					case 1:
						 panel = songTable.createSongTablePlaylist(Utils.pop);
						 name = "Pop";

						 break;

					case 2:
						 panel = songTable.createSongTablePlaylist(Utils.jazz);
						 name = "Jazz";

						 break;

					case 3:
						 panel = songTable.createSongTablePlaylist(Utils.clasica);
						 name = "Clasica";

						 break;

					case 4: 
						 panel = songTable.createSongTablePlaylist(Utils.hip_hop);
						 name = "HipHop";

						 break;

					case 5:
						 panel = songTable.createSongTablePlaylist(Utils.reggae);
						 name = "Reggae";

						 break;

					case 6:
						 panel = songTable.createSongTablePlaylist(Utils.electronica);
						 name = "Electronica";

						 break;

					case 7:
						 panel = songTable.createSongTablePlaylist(Utils.blues);
						 name = "Blues";

						 break;

					case 8:
						 panel = songTable.createSongTablePlaylist(Utils.country);
						 name = "Country";

						 break;

					case 9:
						 panel =  songTable.createSongTablePlaylist(Utils.metal);
						 name = "Metal";

						 break;
					}
					try {
						MainFrame.cardPanel.add(panel, name);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					MainFrame.cardLayout.show(MainFrame.cardPanel, name);
				}
			});


		}
		
		// panel para random Picks 
		JPanel quickPicks = new JPanel(); 
		Utils.generateRandomPlaylist(Utils.playlist1);
		quickPicks = songTable.createSongTablePlaylist(Utils.playlist1);
		JPanel sur = new JPanel();
		sur.add(quickPicks);
		sur.setBackground(MainFrame.BackgroundColor);

		result.add(sur, BorderLayout.CENTER);

		return result;

	}
}
