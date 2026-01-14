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
import model.Genre;
import model.Song;

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

				    JPanel panel = new JPanel();
				    String name = "";

				    Genre genre = Genre.values()[index];

				    try {
				        ArrayList<Song> song = ConfigManager.managedb.getSongsPerGenre(genre);
				        panel = songTable.createSongTableArrayList(song);
				    } catch (Exception e2) {
				       
				    }

				    name = genre.getDisplayName();

				    result.add(panel, BorderLayout.CENTER);
				    main.getCardPanel().add(panel, name);
				    main.getCardLayout().show(main.getCardPanel(), name);
				}

			});
		}
		
			try {
			ArrayList<Song> songs =  ConfigManager.managedb.getRandomSongs();
			
			quickPicks.add(songTable.createSongTableArrayList(songs), BorderLayout.CENTER);
			result.add(quickPicks, BorderLayout.CENTER);
			JLabel x = new JLabel("QUICK PICKS");
			x.setFont(new Font("Arial", Font.PLAIN, 24));
			x.setHorizontalAlignment(JLabel.CENTER);
			x.setForeground(MainFrame.TextColor);
			quickPicks.add(x, BorderLayout.NORTH);

		} catch(Exception e) {
			JLabel x = new JLabel("Imposible to load quickpicks from database, try later");
			quickPicks.add(x, BorderLayout.CENTER);

		}
			
		return result;

	}
}