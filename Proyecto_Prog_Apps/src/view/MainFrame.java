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

import javax.swing.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.BorderFactory;
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
	public static JPanel playerBar;
	public static BorderLayout borderLayout = new BorderLayout();
	private CardLayout cardLayout = new CardLayout();
	private FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER,0,0);
	
	public static Color BackgroundColor = Color.black;
	public static Color BorderColor = Color.GRAY;
	public static Color TextColor = Color.white;
	
	private boolean desplegado = true;
	
	public static Song playingSong = new Song("TITLE", 2, "BAND");
	//public static Song playingSong;
	
	char[] bloques = {'▁', '▂', '▃', '▄', '▅', '▆', '▇', '█'};
	
    String[] buttons = {"☰", "Home", "Playlists", "Queue", "Settings", "Account"};  
    String[] nombres = {"🔙", "🏠", "🎵", "📄", "⚙", "👤"}; 
    ArrayList<JButton> buttonList = new ArrayList<>();
    
    private model.Queue queue;

	List<Song> l_songs = new ArrayList<Song>();
	Playlist playlist = new Playlist("Playlist 1", 3, 12345, l_songs);
	public MainFrame() {
		instance = this;
		Utils.generateSongs();
        queue = new model.Queue();
        initialize();
        
        Timer timer = new Timer(150, e -> actualizarTitulo());
        timer.start();
    }
	
	private void initialize() {
		setTitle("EchoBeat");

		setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(borderLayout);
        setContentPane(mainPanel);
        
        cardPanel = new JPanel(cardLayout);
        cardPanel.setVisible(true);
        cardPanel.setOpaque(true);
        cardPanel.setBackground(BackgroundColor);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
     
        //PLAYING SONG
        // cardPanel.add(PlayingSong.PlayingSongPanel(playingSong), "PlayingSong");
        // cardLayout.show(cardPanel, "PlayingSong");
        
        
        //PLAYLIST MANAGER DIALOG
        cardPanel.add(PlaylistManagerDialog.PlaylistManagerDialogPanel(playlist), "PlaylisyManagerDialog");
        cardLayout.show(cardPanel, "PlaylisyManagerDialog");
        
        //QUEUE

        // cardPanel.add(PlaybackQueueDialog.QueuePanel(), "Queue");
        // cardLayout.show(cardPanel, "Queue");

        cardPanel.add(PlaybackQueueDialog.QueuePanel(), "Queue");
        //cardLayout.show(cardPanel, "Queue");

        
        
        
        indexPanel = new JPanel(flowLayout);
        indexPanel.setBackground(BackgroundColor);
        indexPanel.setBorder(new MatteBorder(0,0,0,1, BorderColor));
//        indexPanel.setPreferredSize(new Dimension((int)(this.getWidth()*0.2222), 0));
        indexPanel.setPreferredSize(new Dimension(200, 0));
        mainPanel.add(indexPanel, BorderLayout.WEST);
                        
        for(String s: buttons) {
        	JButton button = new JButton(s);

			button.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 26));
			button.setMargin(new Insets(0, 0, 0, 0));
        	button.setForeground(TextColor);
        	button.setHorizontalAlignment(JLabel.CENTER);
        	button.setBackground(BackgroundColor);
//        	button.setPreferredSize(new Dimension((int)(this.getWidth()*0.2211) , (int)(this.getWidth()*0.0556)));
        	button.setPreferredSize(new Dimension(199, 50));
        	button.setFocusPainted(false);
        	indexPanel.add(button);
        	buttonList.add(button);
        } 
        
        buttonList.get(0).addActionListener(e -> toggleSidebar());
        
        
//        this.addComponentListener(new ComponentAdapter() {
//        	public void componentResized(ComponentEvent e) {
//        		
//        	};
//		});
        
	}
	
	public void showPlayingSong() {
		cardLayout.show(cardPanel, "PlayingSong");
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
	        
	       
	    } else {
	        indexPanel.setPreferredSize(new Dimension(200, 50));
	        for (int i = 0; i < buttonList.size(); i++) {
	        	buttonList.get(i).setText(buttons[i]);
	        	buttonList.get(i).setPreferredSize(new Dimension(200, 50));
				
			} 
	        	
	    }
	    desplegado = !desplegado;
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}
	
	public static MainFrame getInstance() {
		return instance;
	}
	
	

}


