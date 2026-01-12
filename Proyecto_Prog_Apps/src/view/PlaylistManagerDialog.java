package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import controller.ManageDB;
import model.Genre;
import model.Playlist;
import model.Song;
import model.User;

public class PlaylistManagerDialog extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel listPanelContainer;
	private User currentUser;
	private JTextField buscador;
	private int minCanciones = 0; 
	private int MaxCanciones = 10; 
	
	public static JPanel PlaylistManagerDialogPanel() {
		PlaylistManagerDialog instance = new PlaylistManagerDialog();
		return instance.createContentPanel();
	}

	private JPanel createContentPanel() {
		MainFrame main = MainFrame.getInstance();
		JPanel mainPanel = new JPanel(new BorderLayout());

		// --- CONFIGURACIÓN DE ATAJOS (Ctrl+R) ---
		KeyStroke ctrlR = KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK);
		mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ctrlR, "CNTRL_R_ACTION");;
		
		mainPanel.getActionMap().put( "CNTRL_R_ACTION", new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        showRecursivePlaylistDialog(main);
		    }
		});

		mainPanel.setBackground(MainFrame.BackgroundColor);
		currentUser = main.getCurrentUser();
		
		// --- PANEL SUPERIOR ---
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
		 
		// --- LISTA DE PLAYLISTS ---
		listPanelContainer = new JPanel();
		listPanelContainer.setBackground(MainFrame.BackgroundColor);
		listPanelContainer.setLayout(new BoxLayout(listPanelContainer, BoxLayout.Y_AXIS));
		reloadPlaylists(""); 

		// Listeners del buscador principal
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

	// Método auxiliar para el diálogo recursivo (extraído para limpieza)
	private void showRecursivePlaylistDialog(MainFrame main) {
		JDialog datosPlaylist = new JDialog(main, "Required Information for Playlist", true);
        datosPlaylist.setSize(500, 500);
        datosPlaylist.setLocationRelativeTo(main);

        JPanel control = new JPanel(new GridLayout(5, 1, 10, 10));
        control.setBackground(MainFrame.BackgroundColor);

        JList<Genre> selectedGenre = new JList<>(Genre.values());
        selectedGenre.setBorder(new LineBorder(main.getForeground(), 2));
        selectedGenre.setFont(main.getFont());
        selectedGenre.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        selectedGenre.setBackground(MainFrame.BackgroundColor.brighter());
        selectedGenre.setForeground(MainFrame.TextColor);
        selectedGenre.setLayoutOrientation(JList.VERTICAL_WRAP);
        selectedGenre.setFixedCellWidth(100);
        selectedGenre.setVisibleRowCount((Genre.values().length + 1) / 4);
        
        JSpinner maxCanciones = new JSpinner(new SpinnerNumberModel(minCanciones,minCanciones, MaxCanciones, 1));
        maxCanciones.setPreferredSize(new Dimension(50, 20));
        JLabel ncanciones = new JLabel("Nº songs: " + maxCanciones.getValue(), JLabel.CENTER);
        ncanciones.setForeground(MainFrame.TextColor);
        maxCanciones.addChangeListener(evt -> ncanciones.setText("Nº songs: " + maxCanciones.getValue()));

        JSlider maxDuracion = new JSlider(0, 100);
        JLabel nduracion = new JLabel("Playlist Duration: " + maxDuracion.getValue(), JLabel.CENTER);
        nduracion.setForeground(MainFrame.TextColor);
        maxDuracion.setBackground(MainFrame.BackgroundColor);
        maxDuracion.addChangeListener(evt -> nduracion.setText("Playlist Duration: " + maxDuracion.getValue()));

        JSpinner playListn = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));
        playListn.setPreferredSize(new Dimension(50, 20));
        JLabel nplayList = new JLabel("Nº of playlist: " + playListn.getValue(), JLabel.CENTER);
        nplayList.setForeground(MainFrame.TextColor);
        playListn.addChangeListener(evt -> nplayList.setText("Nº of playlist: " + playListn.getValue()));

        JPanel songs = new JPanel(new FlowLayout(FlowLayout.CENTER,10, 10));
        songs.setBackground(MainFrame.BackgroundColor);
        songs.add(maxCanciones);
        songs.add(ncanciones);

        JPanel tiempo = new JPanel(new FlowLayout(FlowLayout.CENTER,10, 10));
        tiempo.setBackground(MainFrame.BackgroundColor);
        tiempo.add(maxDuracion, BorderLayout.CENTER);
        tiempo.add(nduracion, BorderLayout.SOUTH);

        JPanel playlist = new JPanel(new FlowLayout(FlowLayout.CENTER,10, 10));
        playlist.setBackground(MainFrame.BackgroundColor);
        playlist.add(playListn);
        playlist.add(nplayList);

        JButton accept = new JButton("Accept");
        JButton cancel = new JButton("Cancel");
        accept.setBackground(MainFrame.BorderColor);
        accept.setForeground(MainFrame.TextColor);
        cancel.setBackground(MainFrame.BorderColor);
        cancel.setForeground(MainFrame.TextColor);

        JPanel buttonControl = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonControl.setBackground(MainFrame.BackgroundColor);
        buttonControl.add(accept);
        buttonControl.add(cancel);

        control.add(selectedGenre);
        control.add(songs);
        control.add(tiempo);
        control.add(playlist);
        control.add(buttonControl);

        datosPlaylist.add(control);

        accept.addActionListener(evt -> {
            ArrayList<Genre> obtainedGenres = new ArrayList<>(selectedGenre.getSelectedValuesList());
            datosPlaylist.dispose();
            // Lógica recursiva aquí (si existe la clase Recursivity)
             reloadPlaylists(getCurrentFilter());
        });

        cancel.addActionListener(evt -> datosPlaylist.dispose());
        datosPlaylist.setVisible(true);
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
		
		// --- NUEVA LÓGICA DE AÑADIR CANCIÓN CON BUSCADOR ---
		btnAddSong.addActionListener(e -> {
		    JDialog addSongDialog = new JDialog(main, "Add Song to Playlist", true);
		    addSongDialog.setSize(400, 500);
		    addSongDialog.setLocationRelativeTo(main);
		    addSongDialog.setLayout(new BorderLayout());

		    // Panel superior con buscador
		    JTextField songSearch = new JTextField();
		    songSearch.setBorder(BorderFactory.createTitledBorder("Search Song"));
		    addSongDialog.add(songSearch, BorderLayout.NORTH);

		    // Modelo y lista de canciones
		    DefaultListModel<Song> listModel = new DefaultListModel<>();
		    List<Song> allSongs = ManageDB.getInstance().getAllSongs();
		    allSongs.forEach(listModel::addElement);

		    JList<Song> songList = new JList<>(listModel);
		    songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		    addSongDialog.add(new JScrollPane(songList), BorderLayout.CENTER);

		    // Listener para filtrar en tiempo real
		    songSearch.addKeyListener(new KeyAdapter() {
		        @Override
		        public void keyReleased(KeyEvent e) {
		            String filter = songSearch.getText().toLowerCase();
		            listModel.clear();
		            List<Song> filtered = allSongs.stream()
		                .filter(s -> s.getTitle().toLowerCase().contains(filter) || s.getBand().toLowerCase().contains(filter))
		                .collect(Collectors.toList());
		            filtered.forEach(listModel::addElement);
		        }
		    });

		    // Botón de confirmar
		    JButton btnConfirm = new JButton("Add Selected Song");
		    btnConfirm.addActionListener(ev -> {
		        Song selected = songList.getSelectedValue();
		        if (selected != null) {
		            if (ManageDB.getInstance().isSongInPlaylist(p.getCod(), selected.getCod())) {
		                JOptionPane.showMessageDialog(addSongDialog, "Song already in playlist!", "Duplicate", JOptionPane.WARNING_MESSAGE);
		            } else {
		                ManageDB.getInstance().addSongToPlaylist(p.getCod(), selected.getCod());
		                updatePlaylistModel(p);
		                openPlaylistView(p);
		                addSongDialog.dispose();
		            }
		        }
		    });
		    addSongDialog.add(btnConfirm, BorderLayout.SOUTH);

		    addSongDialog.setVisible(true);
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