package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import controller.ManageDB;
import model.Playlist;
import model.Song;
import model.User;

public class PlaylistManagerDialog extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel listPanelContainer;
	private User currentUser;
	private JTextField buscador;

	public static JPanel PlaylistManagerDialogPanel() {
		PlaylistManagerDialog instance = new PlaylistManagerDialog();
		// adicion de KeyListener que invoca metodo recursivo
						return instance.createContentPanel();
	}

	private JPanel createContentPanel() {
		MainFrame main = MainFrame.getInstance();
		JPanel mainPanel = new JPanel(new BorderLayout());
		
// no se puede resolver con listeners ya que los JPanels no tienen el focus nunca y aunque lo forcemos no es muy efectivo
//		mainPanel.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				// TODO Auto-generated method stub
//				// si ctrl + r presionados
//				if (e.getKeyCode() == KeyEvent.VK_R && e.isControlDown() ) {
//					// JDialog  pide cuantas playlists crear,  lista de generos, numero de canciones maximas o duracion maxima por playlist
//					//JDialog d = new JDialog(); 
//					System.out.println("teclas presionadas");
//				}
//			}
//		});
		
// creamos short cut para abrir ventana y crear una playlist mediante metodo recursivo
		// creamos un objeto que representa la combinacion de teclas con la uqe queremos activar el metodo recursivo
		KeyStroke ctrlR = KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK);
		
		// obtenemmos el inputMap cuando el foco este en la ventana que contiene el panel
		//e introducimos el comando ctrlR como clave asociado a una accion con nombre lógico "CNTRL_R_ACTION"

		mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ctrlR, "CNTRL_R_ACTION");;
		
		// obtenemos el actionMap que relaciona el nombre logico anterior con la accion deseada
		mainPanel.getActionMap().put("CNTRL_R_ACTION", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("teclas presionadas");
			}
		});

		mainPanel.setBackground(MainFrame.BackgroundColor);
		currentUser = main.getCurrentUser();
		
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(MainFrame.BackgroundColor);
		topPanel.setBorder(new MatteBorder(0, 0, 2, 0, MainFrame.BorderColor));

		buscador = new JTextField("🔍 Buscar playlist");
		buscador.setOpaque(true);
		buscador.setBackground(Color.WHITE);
		buscador.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		buscador.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		buscador.setHorizontalAlignment(JTextField.CENTER);
		
		JButton btnNewPlaylist = new JButton("➕ New");
		btnNewPlaylist.setBackground(MainFrame.BorderColor);
		btnNewPlaylist.setForeground(Color.WHITE);
		btnNewPlaylist.setFocusPainted(false);
		btnNewPlaylist.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 18));
		btnNewPlaylist.addActionListener(e -> {
			String name = JOptionPane.showInputDialog(mainPanel, "Enter name for new playlist:");
			if (name != null && !name.trim().isEmpty()) {
				name = name.trim();
				if (ManageDB.getInstance().isPlaylistNameExists(name, currentUser.getId())) {
					JOptionPane.showMessageDialog(mainPanel, "A playlist with this name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					Playlist newP = new Playlist(name, currentUser.getId());
					ManageDB.getInstance().insertPlaylist(newP);
					reloadPlaylists(getCurrentFilter());
				}
			}
		});

		topPanel.add(buscador, BorderLayout.CENTER);
		topPanel.add(btnNewPlaylist, BorderLayout.EAST);
		mainPanel.add(topPanel, BorderLayout.NORTH);
		 
		
		
		listPanelContainer = new JPanel();
		listPanelContainer.setBackground(MainFrame.BackgroundColor);
		listPanelContainer.setLayout(new BoxLayout(listPanelContainer, BoxLayout.Y_AXIS));
		reloadPlaylists(""); 

		buscador.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (buscador.getText().equals("🔍 Buscar playlist")) {
					buscador.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (buscador.getText().trim().isEmpty()) {
					buscador.setText("🔍 Buscar playlist");
				}
			}
		});

		buscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				reloadPlaylists(getCurrentFilter());
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(listPanelContainer);
		scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = MainFrame.TextColor;
				this.trackColor = MainFrame.BackgroundColor;
			}
		});
		
		scrollPane.setBorder(null);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		return mainPanel;
	}

	private String getCurrentFilter() {
		String text = buscador.getText().trim();
		if (text.isEmpty() || text.equalsIgnoreCase("🔍 Buscar playlist")) {
			return "";
		}
		return text.toLowerCase();
	}

	private void reloadPlaylists(String filtro) {
		if (listPanelContainer == null || currentUser == null) return;
		
		listPanelContainer.removeAll();
		List<Playlist> freshPlaylists = ManageDB.getInstance().getUserPlaylists(currentUser.getId());
		
		String filtroLimpio = filtro == null ? "" : filtro.toLowerCase().trim();

		for (Playlist p : freshPlaylists) {
			if (!filtroLimpio.isEmpty() && !p.getName().toLowerCase().contains(filtroLimpio)) {
				continue;
			}
			addPlaylistRow(listPanelContainer, p, filtroLimpio);
		}
		listPanelContainer.revalidate();
		listPanelContainer.repaint();
	}

	private void addPlaylistRow(JPanel listPanel, Playlist p, String buscado) {
		JPanel rowPanel = new JPanel(new BorderLayout());
		rowPanel.setBackground(MainFrame.BackgroundColor);
		rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

		String nombre = p.getName();
		if (buscado != null && !buscado.isEmpty()) {
			int indice = nombre.toLowerCase().indexOf(buscado.toLowerCase());
			if (indice != -1) {
				String match = nombre.substring(indice, indice + buscado.length());
				nombre = nombre.substring(0, indice) + "<span style='background: yellow; color: black;'>" + match + "</span>" + nombre.substring(indice + buscado.length());
			}
		}

		int totalSeconds = 0;
		if (p.getSongs() != null) {
			for (Song s : p.getSongs()) {
				totalSeconds += s.getDuration();
			}
		}
		String duracionTotal = Playlist.getDurationFormat(totalSeconds);

		String text = "<html><div style='padding: 5px;'><b>" + nombre + "</b><br>" +
					  "<span style='font-size:10px'>" + p.getN_songs() + " songs - " + duracionTotal + "</span></div></html>";

		JButton buttonPlaylist = new JButton(text);
		buttonPlaylist.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 18));
		buttonPlaylist.setBackground(MainFrame.BorderColor);
		buttonPlaylist.setForeground(MainFrame.TextColor);
		buttonPlaylist.setFocusPainted(false);
		buttonPlaylist.setHorizontalAlignment(SwingConstants.LEFT);

		buttonPlaylist.addActionListener(evt -> openPlaylistView(p));
		
		JButton btnDelete = new JButton("🗑️");
		btnDelete.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 18));
		btnDelete.setBackground(new Color(150, 50, 50));
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setFocusPainted(false);
		btnDelete.setPreferredSize(new Dimension(50, 60));
		btnDelete.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(null, 
				"Are you sure you want to delete playlist '" + p.getName() + "'?", 
				"Delete Playlist", JOptionPane.YES_NO_OPTION);
			
			if (confirm == JOptionPane.YES_OPTION) {
				ManageDB.getInstance().deletePlaylist(p.getCod());
				reloadPlaylists(getCurrentFilter());
			}
		});
		rowPanel.add(buttonPlaylist, BorderLayout.CENTER);
		rowPanel.add(btnDelete, BorderLayout.EAST);
		listPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		listPanel.add(rowPanel);
	}

	private void openPlaylistView(Playlist p) {
		MainFrame main = MainFrame.getInstance();
		JPanel container = new JPanel(new BorderLayout());
		container.setBackground(MainFrame.BackgroundColor);
		
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(MainFrame.BackgroundColor);
		header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JLabel title = new JLabel("Playlist: " + p.getName());
		title.setForeground(MainFrame.TextColor);
		title.setFont(new Font("Arial", Font.BOLD, 18));
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		buttonPanel.setBackground(MainFrame.BackgroundColor);
		
		JButton btnEditName = new JButton("✏️ Edit Title");
		btnEditName.setBackground(MainFrame.BorderColor);
		btnEditName.setForeground(Color.WHITE);
		btnEditName.setFocusPainted(false);
		
		btnEditName.addActionListener(e -> {
			String newName = JOptionPane.showInputDialog(container, "Enter new name:", p.getName());
			if (newName != null && !newName.trim().isEmpty() && !newName.equals(p.getName())) {
				newName = newName.trim();
				if (ManageDB.getInstance().isPlaylistNameExists(newName, currentUser.getId())) {
					JOptionPane.showMessageDialog(container, "Name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					ManageDB.getInstance().updatePlaylistName(p.getCod(), newName);
					p.setName(newName);
					title.setText("Playlist: " + newName);
				}
			}
		});
		
		JButton btnAddSong = new JButton("➕ Add Song");
		btnAddSong.setBackground(MainFrame.BorderColor);
		btnAddSong.setForeground(Color.WHITE);
		btnAddSong.setFocusPainted(false);
		
		btnAddSong.addActionListener(e -> {
			List<Song> allSongs = ManageDB.getInstance().getAllSongs();
			if (allSongs == null || allSongs.isEmpty()) {
				JOptionPane.showMessageDialog(container, "No songs available.");
				return;
			}
			Song[] songArray = allSongs.toArray(new Song[0]);
			Song selectedSong = (Song) JOptionPane.showInputDialog(container, "Choose a song to add:", "Add Song", JOptionPane.QUESTION_MESSAGE, null, songArray, songArray[0]);

			if (selectedSong != null) {
				if (ManageDB.getInstance().isSongInPlaylist(p.getCod(), selectedSong.getCod())) {
					JOptionPane.showMessageDialog(container, "This song is already in the playlist!", "Duplicate", JOptionPane.WARNING_MESSAGE);
				} else {
					ManageDB.getInstance().addSongToPlaylist(p.getCod(), selectedSong.getCod());
					updatePlaylistModel(p);
					openPlaylistView(p);
				}
			}
		});
		
		JButton btnRemoveSong = new JButton("🗑️ Delete Song");
		btnRemoveSong.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 18));
		btnRemoveSong.setBackground(new Color(150, 50, 50));
		btnRemoveSong.setForeground(Color.WHITE);
		btnRemoveSong.setFocusPainted(false);
		
		btnRemoveSong.addActionListener(e -> {
			ArrayList<Song> playlistSongs = ManageDB.getInstance().getSongsByPlaylistId(p.getCod());
			if (playlistSongs == null || playlistSongs.isEmpty()) {
				JOptionPane.showMessageDialog(container, "Playlist is empty.");
				return;
			}
			Song[] songArray = playlistSongs.toArray(new Song[0]);
			Song songToDelete = (Song) JOptionPane.showInputDialog(container, "Select song to remove:", "Remove Song", JOptionPane.WARNING_MESSAGE, null, songArray, songArray[0]);
			
			if (songToDelete != null) {
				ManageDB.getInstance().deleteSongFromPlaylist(p.getCod(), songToDelete.getCod());
				updatePlaylistModel(p);
				openPlaylistView(p);
			}
		});
		
		buttonPanel.add(btnEditName);
		buttonPanel.add(btnAddSong);
		buttonPanel.add(btnRemoveSong);
		
		header.add(title, BorderLayout.WEST);
		header.add(buttonPanel, BorderLayout.EAST);
		
		container.add(header, BorderLayout.NORTH);
		
		JPanel playListSongsTable = songTable.createSongTablePlaylist(p);
		container.add(playListSongsTable, BorderLayout.CENTER);
		
		JButton backBtn = new JButton("🔙 Back to Playlists");
		backBtn.setBackground(MainFrame.BorderColor);
		backBtn.setForeground(Color.WHITE);
		backBtn.setFocusPainted(false);
		backBtn.addActionListener(e -> {
			reloadPlaylists(getCurrentFilter()); 
			main.getCardLayout().show(main.getCardPanel(), "PlaylistManagerDialog");
		});
		container.add(backBtn, BorderLayout.SOUTH);
		
		main.getCardPanel().add(container, "PlaylistSongsTable");
		main.getCardLayout().show(main.getCardPanel(), "PlaylistSongsTable");
		main.setCurrenPanel("PlaylistSongsTable");
		
	}
	
	private void updatePlaylistModel(Playlist p) {
		ArrayList<Song> updatedSongs = ManageDB.getInstance().getSongsByPlaylistId(p.getCod());
		try {
			if (p.getSongs() != null) {
				p.getSongs().clear();
				p.getSongs().addAll(updatedSongs);
			} 
		} catch (Exception e) {
			System.out.println("Error syncing playlist model: " + e.getMessage());
		}
	}
}