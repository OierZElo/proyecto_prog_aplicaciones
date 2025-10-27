package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Playlist;

public class PlaylistManagerDialog extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public static JPanel PlaylistManagerDialogPanel(Playlist playlist) {
		JPanel mainpanel = new JPanel();
		mainpanel.setBackground(Color.BLACK);

		return mainpanel;
	}
}