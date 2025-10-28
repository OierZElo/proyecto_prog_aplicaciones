package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.MatteBorder;
import view.MainFrame;

import model.Song;

public class songBar {
    static ArrayList<JButton> buttonList = new ArrayList<JButton>();
    static JLabel songLabel = new JLabel();

	public static JPanel createPlayerBar(Song s) {
		//ProgressBar, buttons
		BorderLayout songBarLayout = new BorderLayout();
		FlowLayout buttonLayout = new FlowLayout();
		
		JPanel playerBar = new JPanel(songBarLayout);
        playerBar.setBackground(MainFrame.BackgroundColor);
        playerBar.setPreferredSize(new Dimension(0, 90));
        playerBar.setBorder(new MatteBorder(1, 0, 0, 0, MainFrame.BorderColor));
        
        songLabel.setForeground(MainFrame.TextColor);
        songLabel.setFont(new Font("Arial", Font.BOLD, 14));
        playerBar.add(songLabel, BorderLayout.NORTH);
        
		JSlider progressBar = new JSlider(0, s.getDuration(), 0);
	    progressBar.setBackground(MainFrame.BackgroundColor);
	    playerBar.add(progressBar, BorderLayout.CENTER);
	    
	    JPanel buttonsPanel = new JPanel(buttonLayout);
	    buttonsPanel.setBackground(MainFrame.BackgroundColor);
	    String[] songButtons = {"🔀", "⏮", "▶", "⏭", "🔁"};
	    
	    for (int i = 0; i < songButtons.length; i++) {
			JButton b = new JButton(songButtons[i]);
			buttonList.add(b);
			b.setPreferredSize(new Dimension(40,35));
        	b.setHorizontalAlignment(JLabel.CENTER);
			b.setFocusPainted(false);
			b.setBackground(MainFrame.BackgroundColor);
			b.setForeground(MainFrame.TextColor);
			b.setBorder(BorderFactory.createEmptyBorder());
			b.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 26));
			buttonsPanel.add(b);
		}
	    
	    progressBar.addMouseListener(new java.awt.event.MouseAdapter() {
	        @Override
	        public void mousePressed(java.awt.event.MouseEvent e) {
	            int newValue = (int) ((double) e.getX() / (double) progressBar.getWidth() * progressBar.getMaximum());
	            progressBar.setValue(newValue);
	        }
	    });
	    
	    
	    buttonList.get(2).addActionListener(e -> changePlayPause());
	    
	    playerBar.add(buttonsPanel, BorderLayout.SOUTH);
	    
		return playerBar;
	}
	
	private static void changePlayPause() {
		if(buttonList.get(2).getText().equals("▶")) {
			buttonList.get(2).setText("⏸");
		}
		else {
			buttonList.get(2).setText("▶");
		}
	}
	
	public static void updateSongLabel(Song s) {
        songLabel.setText(s.getTitle() + " - " + s.getBand());
    }

}
