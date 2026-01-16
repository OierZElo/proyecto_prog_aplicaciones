package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import model.Song;

public class songBar {
	static ArrayList<JButton> buttonList = new ArrayList<JButton>();
	
	static JLabel songLabel = new JLabel();
	static JSlider progressBar = new JSlider();
	
	public static Thread cancionProgreso;
	private static double time;
	static volatile boolean playing = true;
	
	static boolean random = false;
	static boolean loop = false;
	
	public static JPanel createPlayerBar(Song s) {
		
		MainFrame main = MainFrame.getInstance();
		
		// ProgressBar, buttons
		BorderLayout songBarLayout = new BorderLayout();
		FlowLayout buttonLayout = new FlowLayout();

		JPanel playerBar = new JPanel(songBarLayout);
		playerBar.setBackground(MainFrame.BackgroundColor);
		playerBar.setPreferredSize(new Dimension(0, 90));
		playerBar.setBorder(new MatteBorder(1, 0, 0, 0, MainFrame.BorderColor));

		songLabel.setForeground(MainFrame.TextColor);
		songLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 22));
		playerBar.add(songLabel, BorderLayout.NORTH);

		progressBar = new JSlider(0, s.getDuration(), 0);
		progressBar.setBackground(MainFrame.BackgroundColor);
		playerBar.add(progressBar, BorderLayout.CENTER);

		JPanel buttonsPanel = new JPanel(buttonLayout);
		buttonsPanel.setBackground(MainFrame.BackgroundColor);
		String[] songButtons = { "🔀", "⏮", "⏸", "⏭", "🔁" };
		for (int i = 0; i < songButtons.length; i++) {
			JButton b = new JButton(songButtons[i]);
			buttonList.add(b);
			b.setPreferredSize(new Dimension(40, 35));
			b.setHorizontalAlignment(JLabel.CENTER);
			b.setFocusPainted(false);
			b.setBackground(MainFrame.BackgroundColor);
			b.setForeground(MainFrame.TextColor);
			b.setBorder(BorderFactory.createEmptyBorder());
			b.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 26));
			buttonsPanel.add(b);
			b.setOpaque(true);
		}
		// activamos el foco para que se puedan implmentar acciones de teclado 
		
		buttonsPanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke("SPACE"), "togglePlayPause");;
		buttonsPanel.getActionMap().put("togglePlayPause", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changePlayPause();
			}});

		progressBar.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				int newValue = (int) ((double) e.getX() / (double) progressBar.getWidth() * progressBar.getMaximum());
				progressBar.setValue(newValue);
				time = progressBar.getValue();
			}
		});

		
		buttonList.get(0).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (random == false) {
					random = true;
					buttonList.get(0).setForeground(MainFrame.BackgroundColor);
					buttonList.get(0).setBackground(MainFrame.TextColor);
				} else {
					random = false;
					buttonList.get(0).setForeground(MainFrame.TextColor);
					buttonList.get(0).setBackground(MainFrame.BackgroundColor);
				}
			}
		});
		
		buttonList.get(1).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (time<20) {
					PlaybackQueueDialog.playPrevSong(main.getPlayingSong(), main);
				}
				time = 0;	
			}
		});
		
		buttonList.get(2).addActionListener(e -> changePlayPause());
		
		buttonList.get(3).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PlaybackQueueDialog.playNextSong(main.getPlayingSong(), main, loop, random, false);
			}
		});
		
		
		buttonList.get(4).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (loop == false) {
					loop = true;
					buttonList.get(4).setForeground(MainFrame.BackgroundColor);
					buttonList.get(4).setBackground(MainFrame.TextColor);
				} else {
					loop = false;
					buttonList.get(4).setForeground(MainFrame.TextColor);
					buttonList.get(4).setBackground(MainFrame.BackgroundColor);
				}
			}
		});

		playerBar.add(buttonsPanel, BorderLayout.SOUTH);

		return playerBar;
	}
	


	private static void changePlayPause() {
		if (buttonList.get(2).getText().equals("▶")) {
			buttonList.get(2).setText("⏸");
			playing = true;
			
		} else {
			buttonList.get(2).setText("▶");
			playing = false;
		}
	}

	public static void updateSongLabel(Song s) {
		songLabel.setText(s.getTitle() + " - " + s.getBand());
		progressBar.setMaximum(s.getDuration());
	}
	
	public static void startProgressThread(Song s, MainFrame main) {
		if (cancionProgreso != null && cancionProgreso.isAlive()) {
			cancionProgreso.interrupt();
		}
		cancionProgreso = new Thread(new Runnable() {
			@Override
			public void run() {
				progressBar.setMaximum(s.getDuration()*10);
				time = 0;
				progressBar.setValue((int)time);
				
				while(time<progressBar.getMaximum()) {
					while(!playing) {
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							return;
						}
					}
					try {
						Thread.sleep(100);
						time++;
						 javax.swing.SwingUtilities.invokeLater(() -> {
				                progressBar.setValue((int)time);
				          });
					} catch (InterruptedException e) {
						return;
					}
				}
				SwingUtilities.invokeLater(() -> {
					PlaybackQueueDialog.playNextSong(main.getPlayingSong(), main, loop, random, true);
				});
				
			}
		});
		cancionProgreso.start();
	}
	
	public static void reset() {
		if (cancionProgreso != null && cancionProgreso.isAlive()) {
			cancionProgreso.interrupt();
			try {
				cancionProgreso.join(200);
			} catch (InterruptedException e) {}
			cancionProgreso = null;
			
		}
		
		buttonList.clear();
		
		songLabel = new JLabel();
		progressBar= new JSlider();
		time = 0;
		playing = true;
		random = false;
		loop = false;
		
	}
}
