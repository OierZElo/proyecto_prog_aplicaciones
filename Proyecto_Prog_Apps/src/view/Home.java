package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Playlist;
import utils.Utils;

public class Home {
		private static ArrayList<JButton> buttonList = new ArrayList<JButton>(); 

	public static JPanel HomePanel() {
		JPanel result = new JPanel(new BorderLayout());
		result.setBackground(MainFrame.BackgroundColor);
		JPanel englobaNorte = new JPanel(new BorderLayout()); 
		englobaNorte.setBackground(MainFrame.BackgroundColor);
		JPanel norte = new JPanel(new GridLayout(Math.round(Utils.genres.length / 2), 0, 10, 10));
		englobaNorte.add(norte, BorderLayout.NORTH);
		norte.setBackground(MainFrame.BackgroundColor);
		
		result.add(englobaNorte, BorderLayout.NORTH);
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

			        result.add(panel, BorderLayout.CENTER);
		            MainFrame main = MainFrame.getInstance();
		            main.getCardPanel().add(panel, name);
		            MainFrame.cardLayout.show(main.getCardPanel(), name);
				}
			});
		}
		
		// panel para random Picks 
		JPanel quickPicks = new JPanel(); 
		Utils.generateRandomPlaylist(Utils.playlist1);
		quickPicks = songTable.createSongTablePlaylist(Utils.playlist1);

		result.add(quickPicks, BorderLayout.CENTER);
		JLabel x = new JLabel("QUICK PICKS");
		x.setFont(new Font("Arial", Font.PLAIN, 24));
		x.setHorizontalAlignment(JLabel.CENTER);
		x.setForeground(MainFrame.TextColor);
		englobaNorte.add(x, BorderLayout.SOUTH);

		return result;

	}
}
