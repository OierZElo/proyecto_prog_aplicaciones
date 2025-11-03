package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Song;

public class PlayingSong extends JFrame {
	
	private static final long serialVersionUID = 1L;

	static JPanel southPanel = new JPanel(new GridLayout(2, 1));
	static JPanel panelCentral = new JPanel(new BorderLayout());

	public static JPanel PlayingSongPanel(Song song) {
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		// se le pasa por parametro la cancion que se está reproduciendo
		panelPrincipal.setVisible(true);
		panelPrincipal.setBackground(MainFrame.BackgroundColor);

		// title and band
		southPanel.setVisible(true);
		southPanel.setOpaque(true);

		if (song != null) {
			setUpPanel(song);
		}
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);

		return panelPrincipal;

	}

	static void setUpPanel(Song song) {
		JLabel title = new JLabel();
		JLabel band = new JLabel();
		JPanel panelIcon = new JPanel();
		JLabel icon = new JLabel();
		
		title.setText(song.getTitle());
		title.setVisible(true);
		title.setOpaque(true);
		title.setForeground(MainFrame.TextColor);
		title.setBackground(MainFrame.BackgroundColor);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 26));
		// band
		band.setText(song.getBand());
		band.setVisible(true);
		band.setOpaque(true);
		band.setForeground(MainFrame.BorderColor);
		band.setBackground(MainFrame.BackgroundColor);
		band.setHorizontalAlignment(JLabel.CENTER);
		band.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 18));
		southPanel.add(title);
		southPanel.add(band);
		panelCentral.add(southPanel, BorderLayout.SOUTH);

		// SongIcon
		panelIcon.setBackground(MainFrame.BackgroundColor);

		String path = "src/resources/icons/" + song.getTitle() + ".png";
	    File file = new File(path);
	    if (!file.exists()) {
	        path = "src/resources/icons/SongIcon.png";
	    }
		
	    icon = new JLabel(new ImageIcon(path));
	    
		panelIcon.add(icon);
		panelCentral.add(panelIcon, BorderLayout.CENTER);
	}
			
}
