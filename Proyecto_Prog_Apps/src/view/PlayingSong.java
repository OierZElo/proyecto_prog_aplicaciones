package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Song;

public class PlayingSong extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public static JPanel PlayingSongPanel(Song song) {			//se le pasa por parametro la cancion que se está reproduciendo
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setVisible(true);
		panelPrincipal.setBackground(Color.BLACK);
		BorderLayout borderLayout = new BorderLayout();
		panelPrincipal.setLayout(borderLayout);
		
		//now playing
		
		//title and band
		JPanel southPanel = new JPanel();
		southPanel.setVisible(true);
		southPanel.setOpaque(true);
		GridLayout gridSouth = new GridLayout(2,1);
		southPanel.setLayout(gridSouth);
		//title
		JLabel title = new JLabel(song.getTitle());
		title.setVisible(true);
		title.setOpaque(true);
		title.setForeground(MainFrame.TextColor);
		title.setBackground(MainFrame.BackgroundColor);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 26));
		//band
		JLabel band = new JLabel(song.getBand());
		band.setVisible(true);
		band.setOpaque(true);
		band.setForeground(MainFrame.BorderColor);
		band.setBackground(MainFrame.BackgroundColor);
		band.setHorizontalAlignment(JLabel.CENTER);
		band.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 18));
		southPanel.add(title);
		southPanel.add(band);
		panelPrincipal.add(southPanel, BorderLayout.SOUTH);
		
		//SongIcon
		JPanel panelIcon = new JPanel();
		panelIcon.setBackground(Color.black);
		JLabel Icon = new JLabel();
		Icon.setIcon(new ImageIcon("src/resources/icons/SongIcon.png"));
		panelIcon.add(Icon);
		panelPrincipal.add(panelIcon, BorderLayout.CENTER);
		

		return panelPrincipal;
	}

	
}
