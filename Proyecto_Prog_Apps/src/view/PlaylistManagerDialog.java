package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Playlist;

public class PlaylistManagerDialog extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public static JPanel PlaylistManagerDialogPanel(Playlist playlist) {
		JPanel mainpanel = new JPanel();
		mainpanel.setBackground(Color.BLACK);
		BorderLayout borderLayout = new BorderLayout();
		mainpanel.setLayout(borderLayout);
		
		JLabel buscador = new JLabel("Buscador");
		buscador.setOpaque(true);
		mainpanel.add(buscador, borderLayout.NORTH);
		buscador.setBackground(new Color(255, 255, 255));
		buscador.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 26));
		buscador.setHorizontalAlignment(JLabel.CENTER);
		
		
		return mainpanel;
	}
}