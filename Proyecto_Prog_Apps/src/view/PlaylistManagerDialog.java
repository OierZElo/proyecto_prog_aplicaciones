package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		JPanel mainpanel = new JPanel();
		mainpanel.setBackground(MainFrame.BackgroundColor);
		BorderLayout borderLayout = new BorderLayout();
		mainpanel.setLayout(borderLayout);

		JTextField buscador = new JTextField("🔍 Buscar playlist");
		buscador.setOpaque(true);
		mainpanel.add(buscador, BorderLayout.NORTH);
		buscador.setBackground(Color.WHITE);
		buscador.setBorder(new MatteBorder(0, 0, 2, 0, MainFrame.BorderColor));

		buscador.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 26));
		buscador.setHorizontalAlignment(JLabel.CENTER);

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
		
		JPanel gridPanel = new JPanel();
		GridLayout layout = new GridLayout(10, 1, 5, 5);
		gridPanel.setLayout(layout);
		gridPanel.setBackground(MainFrame.BackgroundColor);

		for (Playlist p: Utils.playlists) {
		    String texto = "<html><b>" + p.getName() + "</b><br>" +
	                   p.getN_songs() + " songs - " + Playlist.getDurationFormat(p.getDuration()) + " minutes</html>";

		    JButton buttonPlaylist = new JButton(texto);
		    buttonPlaylist.setHorizontalAlignment(SwingConstants.LEFT);
		    gridPanel.add(buttonPlaylist);
		}
		
		
		

		
		mainpanel.add(gridPanel, BorderLayout.CENTER);		
		return mainpanel;
	}
}