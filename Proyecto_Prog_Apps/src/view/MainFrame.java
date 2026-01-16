package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import controller.ManageDB;
import model.Song;
import model.User;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static MainFrame instance;
	JPanel mainPanel;
	static JPanel cardPanel;
	private JPanel indexPanel;
	public JPanel playerBar;
	private CardLayout cardLayout = new CardLayout();
	private FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);

	public static Color BackgroundColor = Color.black;
	public static Color BorderColor = Color.GRAY;
	public static Color TextColor = Color.white;
	public static boolean desplegado = true;

	private Song playingSong;
	
	private String currentPanel = "Home";
	public User currentUser;

	char[] bloques = { '▁', '▂', '▃', '▄', '▅', '▆', '▇', '█' };

	String[] buttons = { "☰", "Home", "Playlists", "Queue", "Settings", "Account" };
	String[] nombres = { "🔙", "🏠", "🎵", "📄", "⚙", "👤" };
	ArrayList<JButton> buttonList = new ArrayList<>();

	List<Song> l_songs = new ArrayList<Song>();

	private ImageIcon icon;
	private JLabel iconLabel = new JLabel();

	private ManageDB managedb = ManageDB.getInstance();
	private ArrayList<Song> songs;
	private ArrayList<User> users;
	
	private PlayingSong playingSongPanel;

	private MainFrame() {
		instance = this;
//		Utils.generateUsers();
//		songs = managedb.getSongs();
//		users = managedb.getUsers();
//		System.out.println("songs: " + songs);
//		System.out.println("users: "+ users);
//		Utils.generatePlaylists();
		initialize();

		Timer timer = new Timer(150, e -> actualizarTitulo());
		timer.start();
	}

	public void initialize() {
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);

		cardPanel = new JPanel(cardLayout);
		cardPanel.setVisible(true);
		cardPanel.setOpaque(true);
		cardPanel.setBackground(BackgroundColor);
		mainPanel.add(cardPanel, BorderLayout.CENTER);

		// HOME
		cardPanel.add(Home.HomePanel(), "HomePanel");

		// PLAYING SONG
		playingSongPanel = new PlayingSong();
		cardPanel.add(playingSongPanel, "PlayingSong");

		// PLAYLIST MANAGER DIALOG
		cardPanel.add(PlaylistManagerDialog.PlaylistManagerDialogPanel(), "PlaylistManagerDialog");

		// QUEUE
		cardPanel.add(PlaybackQueueDialog.QueuePanel(), "Queue");
		cardLayout.show(cardPanel, "Queue");

		// SETTINGS
		cardPanel.add(ConfigManager.ColorPanel(), "Config");

		// ACCOUNT
		if (currentUser != null) {
			cardPanel.add(UserPanel.PanelUsuario(), "AccountPanel");
		}

		indexPanel = new JPanel(flowLayout);
		indexPanel.setBackground(BackgroundColor);
		indexPanel.setBorder(new MatteBorder(0, 0, 0, 1, BorderColor));
		indexPanel.setPreferredSize(new Dimension(200, 0));
		mainPanel.add(indexPanel, BorderLayout.WEST);

		for (int i = 0; i < buttons.length; i++) {
			JButton button = new JButton(buttons[i]);
			button.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 26));
			button.setMargin(new Insets(0, 0, 0, 0));
			button.setForeground(TextColor);
			button.setBackground(BackgroundColor);
			button.setPreferredSize(new Dimension(199, 45));
			button.setFocusPainted(false);
			indexPanel.add(button);
			buttonList.add(button);

			final int index = i;
			button.addActionListener(e -> {
				switch (index) {
				case 0: // TOGGLE SIDEBAR
					toggleSidebar();
					break;
					
				case 1: // HOME
					cardLayout.show(cardPanel, "HomePanel");
					currentPanel = "HomePanel";
					if (playingSong != null) {
						updateSongIcon(playingSong);
					}
					break;
					
				case 2: // PLAYLISTS
				    JPanel playlistPanelActualizado = PlaylistManagerDialog.PlaylistManagerDialogPanel();
				    cardPanel.add(playlistPanelActualizado, "PlaylistManagerDialog");
				    cardLayout.show(cardPanel, "PlaylistManagerDialog");
				    currentPanel = "PlaylistManagerDialog"; 
				    
				    if (playingSong != null) {
				        updateSongIcon(playingSong);
				    }
				    break;
				    
				case 3: // QUEUE
			        JPanel queuePanelActualizado = PlaybackQueueDialog.QueuePanel();
			        cardPanel.add(queuePanelActualizado, "Queue");
			        cardLayout.show(cardPanel, "Queue");
			        currentPanel = "Queue";

				    if (playingSong != null) {
				        updateSongIcon(playingSong);
				    }
				    break;
				    
				case 4: // SETTINGS
					cardLayout.show(cardPanel, "Config");
					currentPanel = "SettingsPanel";
					if (playingSong != null) {
				        updateSongIcon(playingSong); 
				    }					
					break;
					
				case 5: // ACCOUNT
					cardLayout.show(cardPanel, "AccountPanel");
					currentPanel = "AccountPanel";
					if (playingSong != null) {
				        updateSongIcon(playingSong);
				    }				
					break;
				}
			});
		}

		iconLabel.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (playingSong != null) {
		            playingSongPanel.updateSong(playingSong);
		        }
		        cardLayout.show(cardPanel, "PlayingSong");
		        currentPanel = "PlayingSong";
		        setSongIcon(null);
		    }
		});

		indexPanel.add(iconLabel);
	}
	private void actualizarTitulo() {
		String titulo = new String();
		for (int i = 0; i < 63; i++) {
			int random = ThreadLocalRandom.current().nextInt(0, bloques.length);
			titulo += (bloques[random]);
		}
		setTitle(titulo.toString());
	}

	private void toggleSidebar() {
		if (desplegado) {
			indexPanel.setPreferredSize(new Dimension(50, 50));
			for (int i = 0; i < buttonList.size(); i++) {
				buttonList.get(i).setText(nombres[i]);
				buttonList.get(i).setPreferredSize(new Dimension(50, 50));
			}
			setSongIcon(null);
		} else {
			indexPanel.setPreferredSize(new Dimension(200, 50));
			for (int i = 0; i < buttonList.size(); i++) {
				buttonList.get(i).setText(buttons[i]);
				buttonList.get(i).setPreferredSize(new Dimension(200, 50));
			}

			if (playingSong != null && !currentPanel.equals("PlayingSong")) {
				javax.swing.SwingUtilities.invokeLater(() -> updateSongIcon(playingSong));
			}
		}

		desplegado = !desplegado;
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public static MainFrame getInstance() {
		if (instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}

	public void updateSongIcon(Song s) {
		if (desplegado) {
			
			try {
			String path = "src/resources/icons/" + s.getCod() + ".png"; 
			File file = new File(path);
			if (!file.exists() || s.getTitle() == null) {
				path = "src/resources/icons/SongIcon.png";
			} 
			ImageIcon imageIcon = new ImageIcon(path);
			Image img = imageIcon.getImage();
			Image newImg = img.getScaledInstance(199, 205, Image.SCALE_SMOOTH);
			setSongIcon(new ImageIcon(newImg));
			} 
			catch(NullPointerException e) {
				System.out.println("song is null");
			}	
		}
	}

	public Song getPlayingSong() {
		return playingSong;
	}

	public void setPlayingSong(Song playingSong) {
		this.playingSong = playingSong;
	}

	public String getCurrentPanel() {
		return currentPanel;
	}

	public void setCurrenPanel(String s) {
		this.currentPanel = s;
	}

	public JPanel getPlayerBar() {
		return playerBar;
	}

	public void setPlayerBar(JPanel p) {
		this.playerBar = p;
	}

	public JPanel getCardPanel() {
		return cardPanel;
	}

	@Override
	public void dispose() {
		super.dispose();
		instance = null;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User user) {
		this.currentUser = user;
		String foto =user.getPhotoString(user);
		user.setPhoto(new ImageIcon(foto));
	}

	public CardLayout getCardLayout() {
		return cardLayout;
	}

	public void setCurrentCardLayout(CardLayout cardlayout) {
		this.cardLayout = cardlayout;
	}

	public void setSongIcon(ImageIcon icon) {
		this.icon = icon;
		iconLabel.setIcon(icon);
	}

	public ImageIcon getSongIcon() {
		return icon;
	}

	public ArrayList<Song> getSongs() {
		return songs;
	}
	
	public PlayingSong getPlayingSongPanel() {
	    return playingSongPanel;
	}
}
