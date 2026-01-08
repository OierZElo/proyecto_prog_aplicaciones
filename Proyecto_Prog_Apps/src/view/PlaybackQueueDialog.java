package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import controller.ManageDB;
import model.Queue;
import model.Song;
import model.StyledTable;

public class PlaybackQueueDialog extends JFrame {
	static ArrayList<JButton> buttonList = new ArrayList<JButton>();

	private static final long serialVersionUID = 1L;
	static Queue queue = new Queue();

	public static JTable activeTable; 

	public static JPanel QueuePanel() {
		
		MainFrame main = MainFrame.getInstance();
		ManageDB managedb = ManageDB.getInstance();
		
		String[] columns = { "Title", "Artist", "Duration" };
		String[] buttonIcons = { "  ⬆️", "  ⬇️", "🗑️" };
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel buttonEastPanel = new JPanel(new GridLayout(2, 1));
		JPanel soundButtonPanel = new JPanel(new GridLayout(buttonIcons.length + 1, 1));
		soundButtonPanel.setBackground(MainFrame.BackgroundColor);
		buttonEastPanel.setBackground(MainFrame.BackgroundColor);
		JLabel volumeLabel = new JLabel("50%", JLabel.CENTER);
		volumeLabel.setForeground(MainFrame.TextColor);

		JSlider volumeSlider = new JSlider(JSlider.VERTICAL, 0, 100, 50);
		volumeSlider.setBackground(MainFrame.BackgroundColor);
		volumeSlider.setForeground(MainFrame.TextColor);
		volumeSlider.setPaintTrack(true);

		volumeSlider.addChangeListener(e -> {
			int value = volumeSlider.getValue();
			volumeLabel.setText(value + "%");
		});

//		DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
//			@Override
//			public boolean isCellEditable(int row, int column) {
//				return false;
//			}
//		};
		
		DataModel tableModel = new DataModel(queue.getQueue());

		JTable localSongTable = new StyledTable(tableModel);
		
		activeTable = localSongTable;

		for (int i = 0; i < buttonIcons.length; i++) {
			JButton b = new JButton(buttonIcons[i]);
			b.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 26));
			b.setForeground(MainFrame.TextColor);
			b.setBackground(MainFrame.BackgroundColor);
			b.setPreferredSize(new Dimension(50, 50));
			b.setFocusPainted(false);
			b.setBorder(BorderFactory.createEmptyBorder());

			soundButtonPanel.add(b);
			buttonList.add(b);

			final int index = i;
			b.addActionListener(e -> {
				int selectedRow = localSongTable.getSelectedRow();
				switch (index) {
				case 0:
					if (selectedRow < queue.getQueue().size() && selectedRow > 0) {
						//queue.moveUp(selectedRow);
						tableModel.moveRow(selectedRow, selectedRow, selectedRow - 1);

						localSongTable.setRowSelectionInterval(selectedRow - 1, selectedRow - 1);
					}
					break;
				case 1:
					if (selectedRow >= 0 && selectedRow < queue.getQueue().size() - 1) {
						queue.moveDown(selectedRow);
						tableModel.moveRow(selectedRow, selectedRow, selectedRow + 1);

						localSongTable.setRowSelectionInterval(selectedRow + 1, selectedRow + 1);
					}
					;
					break;
				case 2:
					queue.getQueue().remove(selectedRow);
					tableModel.removeRow(selectedRow);
					break;
				}
			});

		}
		queue.getQueue().clear();
		

		while (queue.getQueue().size() < 30) {
		    int r = ThreadLocalRandom.current().nextInt(1, managedb.getSongCount());
		    Song s = managedb.getSongById(r);
		    if (s == null) continue; 
		    if (!queue.contains(s)) queue.enqueue(s);
		}

		for (Song s : new ArrayList<>(queue.getQueue())) {
		    tableModel.addRow(s);
		}


		localSongTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = localSongTable.getSelectedRow();
					if (row >= 0) {
						activeTable = localSongTable;
						
						Song current = queue.getQueue().get(row);
						main.setPlayingSong(current);

						if (main.getPlayerBar() == null) {
							main.setPlayerBar(songBar.createPlayerBar(main.getPlayingSong()));
							MainFrame.getInstance().mainPanel.add(main.getPlayerBar(), BorderLayout.SOUTH);
						}
						songBar.updateSongLabel(main.getPlayingSong());
						songBar.startProgressThread(main.getPlayingSong(), main);
						main.updateSongIcon(main.getPlayingSong());
						mainPanel.revalidate();
						mainPanel.repaint();
						
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(localSongTable);
		scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = MainFrame.TextColor;
				this.trackColor = MainFrame.BackgroundColor;
			}
		});

		mainPanel.add(scrollPane, BorderLayout.CENTER);
		soundButtonPanel.add(volumeLabel);
		buttonEastPanel.add(soundButtonPanel);
		buttonEastPanel.add(volumeSlider);
		mainPanel.add(buttonEastPanel, BorderLayout.EAST);

		return mainPanel;
	}
	
	public static void playNextSong(Song actual, MainFrame main, boolean loop, boolean random, boolean end) {
		ArrayList<Song> listSongs = queue.getQueue();
		Song nextSong = null;
		
		int index = listSongs.indexOf(actual);
		

		if (listSongs.isEmpty()) {
			return;
		}

		if (index == -1 && !random) {
			index = -1; 
		}

		if(loop==false) {
			if(random==false) {
				if (index >= listSongs.size()-1) {
					return;
				}
				
				nextSong = listSongs.get(index +1);
				index = index + 1;
			} else { 
				if (listSongs.size() > 1) {
					int rIndex = index;
					while (rIndex == index) {
						rIndex = (int)(Math.random() * listSongs.size());
					}
					nextSong = listSongs.get(rIndex);
					index = rIndex;
				} else {
					nextSong = listSongs.get(0);
					index = 0;
				}
			}
		} else {
			if (end==true) {
				nextSong = actual;
			} else {
				if(random==false) {
					if (index >= listSongs.size()-1) {
						index = -1; // reincio
					}
					nextSong = listSongs.get(index +1);
					index = index + 1;
				} else { 
					if (listSongs.size() > 1) {
						int rIndex = index;
						while (rIndex == index) {
							rIndex = (int)(Math.random() * listSongs.size());
						}
						nextSong = listSongs.get(rIndex);
						index = rIndex;
					} else {
						nextSong = listSongs.get(0);
						index = 0;
					}
				}
			}
		}
		
		if (activeTable != null && index >= 0 && index < listSongs.size()) {
			try {
				activeTable.setRowSelectionInterval(index, index);
				activeTable.scrollRectToVisible(activeTable.getCellRect(index, 0, true));
			} catch (Exception e) { System.out.println("Error UI tabla"); }
		}
		
		main.setPlayingSong(nextSong);
		songBar.updateSongLabel(main.getPlayingSong());
		songBar.startProgressThread(main.getPlayingSong(), main);
		main.updateSongIcon(main.getPlayingSong());
	}
	
	public static void playPrevSong(Song actual, MainFrame main) {
		ArrayList<Song> listSongs = queue.getQueue();
		
		int index = listSongs.indexOf(actual);
		if (index == -1 || index == 0) return;
		
		Song prevSong = listSongs.get(index -1);
		index = index - 1;
				
		if (activeTable != null && index >= 0) {
			try {
				activeTable.setRowSelectionInterval(index, index);
				activeTable.scrollRectToVisible(activeTable.getCellRect(index, 0, true));
			} catch (Exception e) { System.out.println("Error UI tabla"); }
		}

		main.setPlayingSong(prevSong);
		songBar.updateSongLabel(main.getPlayingSong());
		songBar.startProgressThread(main.getPlayingSong(), main);
		main.updateSongIcon(main.getPlayingSong());
	}
}