package view;

import java.awt.event.FocusEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import java.awt.*;
import java.awt.event.*;
import model.Playlist;
import utils.Utils;

public class PlaylistManagerDialog extends JFrame {
	private static final long serialVersionUID = 1L;

	public static JPanel PlaylistManagerDialogPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());
	    mainPanel.setBackground(MainFrame.BackgroundColor);
		
	    JTextField buscador = new JTextField("🔍 Buscar playlist");
	    buscador.setOpaque(true);
	    buscador.setBackground(Color.WHITE);
	    buscador.setBorder(new MatteBorder(0, 0, 2, 0, MainFrame.BorderColor));
	    buscador.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 26));
	    buscador.setHorizontalAlignment(JTextField.CENTER);

		buscador.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (buscador.getText().equals("🔍 Buscar playlist")) {
					buscador.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (buscador.getText().isEmpty()) {
					buscador.setText("🔍 Buscar playlist");
				}
			}
		});
		
	    mainPanel.add(buscador, BorderLayout.NORTH);
	    
	    JPanel listPanel = new JPanel();
	    listPanel.setBackground(MainFrame.BackgroundColor);
	    listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

		for (Playlist p: Utils.playlists) {
	    String text = "<html><b>" + p.getName() + "</b><br>" +
                   p.getN_songs() + " songs - " + Playlist.getDurationFormat(p.getDuration()) + " minutes</html>";

		    JButton buttonPlaylist = new JButton(text);
		    buttonPlaylist.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 22));
		    buttonPlaylist.setBackground(MainFrame.BorderColor);
		    buttonPlaylist.setForeground(MainFrame.BackgroundColor);
		    buttonPlaylist.setFocusPainted(false);
		    buttonPlaylist.setHorizontalAlignment(SwingConstants.LEFT);
	        
	        buttonPlaylist.addActionListener(evt -> {
	            JPanel playListSongs = songTable.createSongTablePlaylist(p);
	            MainFrame.cardPanel.add(playListSongs, "PlaylistSongsTable");
	            MainFrame.cardLayout.show(MainFrame.cardPanel, "PlaylistSongsTable");
	            MainFrame.currentPanel = "PlaylistSongsTable";
	        });
	        
	        listPanel.add(Box.createRigidArea(new Dimension(0, 5)));
	        listPanel.add(buttonPlaylist);
	    }
		
		
	    JScrollPane scrollPane = new JScrollPane(listPanel);
		scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = MainFrame.TextColor;
				this.trackColor = MainFrame.BackgroundColor;
			}
		});

	    mainPanel.add(scrollPane, BorderLayout.CENTER);
	    
		return mainPanel;
	}
}