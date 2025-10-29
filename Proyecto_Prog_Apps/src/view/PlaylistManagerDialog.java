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
import java.awt.*;
import java.awt.event.*;
import model.Playlist;
import utils.Utils;

public class PlaylistManagerDialog extends JFrame {
	private static final long serialVersionUID = 1L;

	public static JPanel PlaylistManagerDialogPanel(Playlist playlist) {
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
		    buttonPlaylist.setPreferredSize(new Dimension(680, 70));
		    buttonPlaylist.setHorizontalAlignment(SwingConstants.LEFT);
		    
		    Dimension fixed = new Dimension(680, 70);
	        buttonPlaylist.setPreferredSize(fixed);
	        buttonPlaylist.setMaximumSize(fixed); // evita que se estire al ancho del contenedor
	        buttonPlaylist.setAlignmentX(Component.CENTER_ALIGNMENT);
	        
	        buttonPlaylist.addActionListener(evt -> {
	            // aquí muestras la vista de la playlist p (ej. cargar panel con canciones)
	            System.out.println("Playlist seleccionada: " + p.getName());
	            // cardLayout.show(...), o abrir panel con las canciones de p
	        });
	        
	        listPanel.add(Box.createRigidArea(new Dimension(0, 8)));
	        listPanel.add(buttonPlaylist);
	    }
		
	    listPanel.add(Box.createVerticalGlue());
		
	    JScrollPane scrollPane = new JScrollPane(listPanel);
	    scrollPane.setBorder(null);
	    scrollPane.getViewport().setBackground(MainFrame.BackgroundColor);
	    scrollPane.setBackground(MainFrame.BackgroundColor);
	    scrollPane.getVerticalScrollBar().setUnitIncrement(16); // suaviza scroll

	    mainPanel.add(scrollPane, BorderLayout.CENTER);

		

		
		return mainPanel;
	}
}