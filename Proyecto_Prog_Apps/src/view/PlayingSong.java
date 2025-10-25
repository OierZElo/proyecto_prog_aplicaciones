package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
		
		//title and band
		JPanel southPanel = new JPanel();
		southPanel.setVisible(true);
		southPanel.setOpaque(true);
		GridLayout gridSouth = new GridLayout(2,1);
		southPanel.setLayout(gridSouth);
		JLabel title = new JLabel(song.getTitle());
		title.setVisible(true);
		title.setOpaque(true);
		title.setForeground(Color.white);
		title.setBackground(Color.black);
		title.setHorizontalAlignment(JLabel.CENTER);
		JLabel band = new JLabel(song.getBand());
		band.setVisible(true);
		band.setOpaque(true);
		band.setForeground(Color.black);
		band.setBackground(Color.white);
		band.setHorizontalAlignment(JLabel.CENTER);
		
		southPanel.add(title);
		southPanel.add(band);
		
		panelPrincipal.add(southPanel, BorderLayout.SOUTH);
		
		//SongIcon
		JPanel panelIcon = new JPanel();
		JLabel Icon = new JLabel();
		Icon.setIcon(new ImageIcon("C:\\Users\\ekaitz.lazkano\\git\\proyecto_prog_aplicaciones\\Proyecto_Prog_Apps\\src\\resources\\icons\\SongIcon.png"));
		panelIcon.add(Icon);
		panelPrincipal.add(panelIcon, BorderLayout.CENTER);
		
		//fill borderlayout
//		JPanel west = new JPanel();
//		west.setPreferredSize(new Dimension(0,500));
//		panelPrincipal.add(west);
//		panelPrincipal.add(new JPanel(), BorderLayout.EAST);
//		panelPrincipal.add(new JPanel(), BorderLayout.NORTH);
		
		
		return panelPrincipal;
	}

	
}
