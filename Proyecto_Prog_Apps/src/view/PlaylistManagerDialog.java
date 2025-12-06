package view;

import java.awt.event.FocusEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import model.Playlist;
import model.User;

public class PlaylistManagerDialog extends JFrame {
	private static final long serialVersionUID = 1L;

	public static JPanel PlaylistManagerDialogPanel() {
		MainFrame main = MainFrame.getInstance();
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(MainFrame.BackgroundColor);
		
		// 1. OBTENER USUARIO
		int currentUserId = -1;
		User user = main.getCurrentUser();
		
		// 2. CARGAR PLAYLISTS DE LA BD
		java.util.List<Playlist> tempPlaylists = new java.util.ArrayList<>();

		if (user != null) {
			currentUserId = user.getId();
			tempPlaylists = controller.ManageDB.getInstance().getUserPlaylists(currentUserId);
			System.out.println("Playlists encontradas: " + tempPlaylists.size()); // DEBUG
		} else {
		    System.out.println("El usuario es NULL en PlaylistManagerDialog"); // DEBUG
		}
		
		final java.util.List<Playlist> dbPlaylists = tempPlaylists;

	    JTextField buscador = new JTextField("🔍 Buscar playlist");
	    buscador.setOpaque(true);
	    buscador.setBackground(Color.WHITE);
	    buscador.setBorder(new MatteBorder(0, 0, 2, 0, MainFrame.BorderColor));
	    buscador.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 26));
	    buscador.setHorizontalAlignment(JTextField.CENTER);
	    
	    JPanel listPanel = new JPanel(); 
		
	    buscador.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (buscador.getText().equals("🔍 Buscar playlist")) {
					buscador.setText("");
				}
			}

			@Override
		    public void focusLost(FocusEvent e) {
		        buscador.setText("🔍 Buscar playlist");
		        listPanel.removeAll();
		        
		        // CAMBIO 1: Usamos dbPlaylists en vez de Utils.playlists
		        for (Playlist p : dbPlaylists) {
		            addPlaylistButton(listPanel, p, "");
		        }
		        listPanel.revalidate();
		        listPanel.repaint();
		    }
		});
		
	    mainPanel.add(buscador, BorderLayout.NORTH);
	    
	    listPanel.setBackground(MainFrame.BackgroundColor);
	    listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

	    for (Playlist p: dbPlaylists) {
	        addPlaylistButton(listPanel, p, "");
	    }

	    buscador.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyReleased(KeyEvent e) {
	            String texto = buscador.getText().toLowerCase();
	            listPanel.removeAll();

	            for (Playlist p : dbPlaylists) {
	                if (p.getName().toLowerCase().contains(texto)) {
	                    addPlaylistButton(listPanel, p, texto);
	                }
	            }
	            listPanel.revalidate();
	            listPanel.repaint();
	        }
	    });
		
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

	private static void addPlaylistButton(JPanel listPanel, Playlist p, String buscado) {
	    String nombre = p.getName();

	    if (buscado != null && !buscado.isEmpty()) {
	    	
	        int indice = nombre.toLowerCase().indexOf(buscado.toLowerCase());
	        if (indice != -1) {
	            String antes = nombre.substring(0, indice);
	            String match = nombre.substring(indice, indice + buscado.length());
	            String despues = nombre.substring(indice + buscado.length());
	            nombre = antes + "<span style='background: yellow;'>" + match + "</span>" + despues;
	        }
	    }

	    String text = "<html><b>" + nombre + "</b><br>" +
	                  p.getN_songs() + " songs - " +
	                  Playlist.getDurationFormat(p.getDuration()) +
	                  "</html>";

	    JButton buttonPlaylist = new JButton(text);
	    buttonPlaylist.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 22));
	    buttonPlaylist.setBackground(MainFrame.BorderColor);
	    buttonPlaylist.setForeground(MainFrame.BackgroundColor);
	    buttonPlaylist.setFocusPainted(false);
	    buttonPlaylist.setHorizontalAlignment(SwingConstants.LEFT);

	    buttonPlaylist.addActionListener(evt -> {

	        JPanel playListSongs = songTable.createSongTablePlaylist(p);
	        MainFrame main = MainFrame.getInstance();
	        main.getCardPanel().add(playListSongs, "PlaylistSongsTable");
	        main.getCardLayout().show(main.getCardPanel(), "PlaylistSongsTable");
	        main.setCurrenPanel("PlaylistSongsTable");
	    });

	    listPanel.add(Box.createRigidArea(new Dimension(0, 5)));
	    listPanel.add(buttonPlaylist);
	}
}