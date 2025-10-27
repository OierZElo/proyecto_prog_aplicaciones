package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import model.Playlist;

public class PlaylistManagerDialog extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public static JPanel PlaylistManagerDialogPanel(Playlist playlist) {
		JPanel mainpanel = new JPanel();
		mainpanel.setBackground(MainFrame.BackgroundColor);
		BorderLayout borderLayout = new BorderLayout();
		mainpanel.setLayout(borderLayout);
		
		JTextField buscador = new JTextField("Escribe el nombre de la playlist");
		buscador.setOpaque(true);
		mainpanel.add(buscador, borderLayout.NORTH);
		buscador.setBackground(MainFrame.BackgroundColor);
        buscador.setBorder(new MatteBorder(0,0,1,0, MainFrame.BorderColor));

		buscador.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 26));
		buscador.setHorizontalAlignment(JLabel.CENTER);
		
		 buscador.addFocusListener(new FocusAdapter() {
	            @Override
	            public void focusGained(FocusEvent e) {
	                if (buscador.getText().equals("Escribe el nombre de la playlist")) {
	                    buscador.setText("");
	                }
	            }
	            @Override
	            public void focusLost(FocusEvent e) {
	                if (buscador.getText().isEmpty()) {
	                    buscador.setText("Escribe el nombre de la playlist");
	                }
	            }
	        });
	
		return mainpanel;
	}
}