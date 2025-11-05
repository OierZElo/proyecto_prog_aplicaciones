package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.MatteBorder;

import view.PlayingSong;
import view.songBar;
import java.awt.event.ComponentEvent;

import model.Playlist;
import model.Song;
import model.User;
import utils.Utils;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static MainFrame instance;
	JPanel mainPanel;
	static JPanel cardPanel;
	private JPanel indexPanel;
	public JPanel playerBar;
	static CardLayout cardLayout = new CardLayout();
	private FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);

	public static Color BackgroundColor = Color.black;
	public static Color BorderColor = Color.GRAY;
	public static Color TextColor = Color.white;

	public static boolean desplegado = true;

	private Song playingSong;
	private boolean songPanelSetUpDone = false;
	
	private String currentPanel = "Home";
	public User currentUser;

	char[] bloques = { '▁', '▂', '▃', '▄', '▅', '▆', '▇', '█' };

	String[] buttons = { "☰", "Home", "Playlists", "Queue", "Settings", "Account" };
	String[] nombres = { "🔙", "🏠", "🎵", "📄", "⚙", "👤" };
	ArrayList<JButton> buttonList = new ArrayList<>();

	List<Song> l_songs = new ArrayList<Song>();
	
	static JLabel icon = new JLabel();
	

	public MainFrame() {
		instance = this;
		Utils.generateUsers();
		Utils.generateSongs();
		Utils.generatePlaylists();
		initialize();

		Timer timer = new Timer(150, e -> actualizarTitulo());
		timer.start();
	}

	public void initialize() {
		setTitle("EchoBeat");
	
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

		//HOME 
		cardPanel.add(Home.HomePanel(), "HomePanel");
		//cardLayout.show(cardPanel, "HomePanel");

		
		//PLAYING SONG
		cardPanel.add(PlayingSong.PlayingSongPanel(playingSong), "PlayingSong");
		
		// PLAYLIST MANAGER DIALOG
		cardPanel.add(PlaylistManagerDialog.PlaylistManagerDialogPanel(), "PlaylistManagerDialog");
		//cardLayout.show(cardPanel, "PlaylistManagerDialog");	

		// QUEUE
		cardPanel.add(PlaybackQueueDialog.QueuePanel(), "Queue");
//      cardLayout.show(cardPanel, "Queue");
		
		//SETTINGS
		cardPanel.add(ConfigManager.ColorPanel(), "Config");
		
	
		

		indexPanel = new JPanel(flowLayout);
		indexPanel.setBackground(BackgroundColor);
		indexPanel.setBorder(new MatteBorder(0, 0, 0, 1, BorderColor));
//        indexPanel.setPreferredSize(new Dimension((int)(this.getWidth()*0.2222), 0));
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
				case 0:
					toggleSidebar();
					break;
				case 1:
					cardLayout.show(cardPanel, "HomePanel");
					if(playingSong != null) {
						updateSongIcon(playingSong);
						currentPanel = "HomePanel";
					}
					break;
				case 2:
					cardLayout.show(cardPanel, "PlaylistManagerDialog");
					if(playingSong != null) {
						updateSongIcon(playingSong);
						currentPanel = "PlaylistManagerDialog";
					}
					
					break;
				case 3:
					cardLayout.show(cardPanel, "Queue");
					if(playingSong != null) {
						updateSongIcon(playingSong);
						currentPanel = "Queue";
						}
					break;
				case 4:
					cardLayout.show(cardPanel, "Config");
					currentPanel = "SettingsPanel";
					break;
				case 5:
					cardLayout.show(cardPanel, "AccountPanel");
					currentPanel = "AccountPanel";
					break;
				}
			});
		}
		
		// PLAYING SONG
				icon.addMouseListener(new MouseAdapter() {
				    @Override
				    public void mouseClicked(MouseEvent e) {
				        if (!songPanelSetUpDone && playingSong != null) {
							PlayingSong.setUpPanel(playingSong);
				            songPanelSetUpDone = true;
						}
				        cardLayout.show(cardPanel, "PlayingSong");
				        currentPanel = "PlayingSong";
				        icon.setIcon(null);
				    }
				});
		indexPanel.add(icon);

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
	        icon.setIcon(null);
	    } else {
	        indexPanel.setPreferredSize(new Dimension(200, 50));
	        for (int i = 0; i < buttonList.size(); i++) {
	            buttonList.get(i).setText(buttons[i]);
	            buttonList.get(i).setPreferredSize(new Dimension(200, 50));
	        }

	        if (playingSong != null && !currentPanel.equals("PlayingSong") ) {
	            javax.swing.SwingUtilities.invokeLater(() -> updateSongIcon(playingSong));
	        }
	    }

	    desplegado = !desplegado;
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}

	public static MainFrame getInstance() {
		return instance;
	}
	
	public static void updateSongIcon(Song s) {
	    if(desplegado) {
	    String path = "src/resources/icons/" + s.getTitle() + ".png";
	    File file = new File(path);
	    if (!file.exists()) {
	        path = "src/resources/icons/SongIcon.png";
	    }

	    ImageIcon imageIcon = new ImageIcon(path);
	    Image img = imageIcon.getImage();


	    Image newImg = img.getScaledInstance(199, 205, Image.SCALE_SMOOTH);
	    icon.setIcon(new ImageIcon(newImg));
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
}
