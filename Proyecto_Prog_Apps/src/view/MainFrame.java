package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.Timer;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

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
	
	private Color BackgroundColor = Color.BLACK;
	private Color BorderColor = Color.GRAY;
	private Color TextColor = Color.white;
	
	char[] bloques = {'▁', '▂', '▃', '▄', '▅', '▆', '▇', '█'};

	
	public MainFrame() {
        initialize();
        
        Timer timer = new Timer(150, e -> actualizarTitulo());
        timer.start();
    }
	
	private void initialize() {
		setTitle("EchoBeat");

		setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(borderLayout);
        setContentPane(mainPanel);
        
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(BackgroundColor);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
//        JPanel dummyPanel = new JPanel();
//        dummyPanel.setBackground(Color.YELLOW);
//        cardPanel.add(dummyPanel, "dummy");
//        cardLayout.show(cardPanel, "dummy");
        
        indexPanel = new JPanel(flowLayout);
        indexPanel.setBackground(BackgroundColor);
        indexPanel.setBorder(new MatteBorder(0,0,0,1, BorderColor));
        indexPanel.setPreferredSize(new Dimension(180, 0));
        mainPanel.add(indexPanel, BorderLayout.WEST);
        
        String[] buttons = {"Home", "Playlists", "Queue", "Settings", "Account"};
        
        for(String s: buttons) {
        	JButton button = new JButton(s);

        	button.setFont(new Font("Arial", Font.BOLD, 16));
        	button.setForeground(TextColor);
        	button.setHorizontalAlignment(JLabel.CENTER);
        	button.setBackground(BackgroundColor);
        	button.setPreferredSize(new Dimension(179, 50));
        	button.setFocusPainted(false);
        	indexPanel.add(button);
        } 
	}
	
	private void actualizarTitulo() {
        String titulo = new String();
        for (int i = 0; i < 63; i++) {
        	int random = ThreadLocalRandom.current().nextInt(0, bloques.length);
            titulo += (bloques[random]);
        }
        setTitle(titulo.toString());
    }

}
