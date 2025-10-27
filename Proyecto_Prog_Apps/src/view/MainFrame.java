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

import javax.swing.Timer;

import java.util.ArrayList;
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

import model.Song;
public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JPanel cardPanel;
	private JPanel indexPanel;
	private BorderLayout borderLayout = new BorderLayout();
	private CardLayout cardLayout = new CardLayout();
	private FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER,0,0);
	
	public static Color BackgroundColor = Color.black;
	public static Color BorderColor = Color.GRAY;
	public static Color TextColor = Color.white;
	
	private boolean desplegado = true;
	
	char[] bloques = {'▁', '▂', '▃', '▄', '▅', '▆', '▇', '█'};
	
    String[] buttons = {"☰", "Home", "Playlists", "Queue", "Settings", "Account"};  
    String[] nombres = {"🔙", "🏠", "🎵", "📄", "⚙", "👤"}; 
    ArrayList<JButton> buttonList = new ArrayList<>();

	Song song = new Song("test", 1200, "testband");

	
	public MainFrame() {
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
//        JPanel dummyPanel = new JPanel();
//        dummyPanel.setBackground(Color.YELLOW);
//        cardPanel.add(dummyPanel, "dummy");
//        cardLayout.show(cardPanel, "dummy");
        
        //PLAYING SONG
        cardPanel.add(PlayingSong.PlayingSongPanel(song));
        cardLayout.show(cardPanel, "PlayingSong");
        
        indexPanel = new JPanel(flowLayout);
        indexPanel.setBackground(BackgroundColor);
        indexPanel.setBorder(new MatteBorder(0,0,0,1, BorderColor));
        indexPanel.setPreferredSize(new Dimension(200, 0));
        mainPanel.add(indexPanel, BorderLayout.WEST);
                        
        for(String s: buttons) {
        	JButton button = new JButton(s);

			button.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 26));
			button.setMargin(new Insets(0, 0, 0, 0));
        	button.setForeground(TextColor);
        	button.setHorizontalAlignment(JLabel.CENTER);
        	button.setBackground(BackgroundColor);
        	button.setPreferredSize(new Dimension(199, 50));
        	button.setFocusPainted(false);
        	indexPanel.add(button);
        	buttonList.add(button);
        } 
        
        buttonList.get(0).addActionListener(e -> toggleSidebar());
        
        JPanel playerBar = songBar.createPlayerBar(song);
        mainPanel.add(playerBar, BorderLayout.SOUTH);
        
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
}


