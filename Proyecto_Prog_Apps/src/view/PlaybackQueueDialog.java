package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import controller.ManageDB;
import model.Queue;
import model.Song;
import model.StyledTable;

public class PlaybackQueueDialog extends JFrame {
	
	private static final long serialVersionUID = 1L;
	static ArrayList<JButton> buttonList = new ArrayList<JButton>();
	static Queue queue = new Queue();
	public static JTable activeTable;

	public static JPanel QueuePanel() {
		MainFrame main = MainFrame.getInstance();
		
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.setBackground(MainFrame.BackgroundColor);
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel headerPanel = createHeaderPanel();
		mainPanel.add(headerPanel, BorderLayout.NORTH);

		DataModel tableModel = new DataModel(queue.getQueue());
		JTable localSongTable = new StyledTable(tableModel);
		activeTable = localSongTable;

		setupTableEvents(localSongTable, main);
		populateInitialQueue(tableModel);

		JScrollPane scrollPane = createStyledScrollPane(localSongTable);
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel sideControlPanel = createSidePanel(localSongTable, tableModel);
		mainPanel.add(sideControlPanel, BorderLayout.EAST);

		return mainPanel;
	}

	private static JPanel createHeaderPanel() {
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(MainFrame.BackgroundColor);
		
		JLabel titleLabel = new JLabel("CURRENT PLAYBACK QUEUE", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
		titleLabel.setForeground(MainFrame.TextColor);
		titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
		
		headerPanel.add(titleLabel, BorderLayout.CENTER);
		return headerPanel;
	}

	private static JPanel createSidePanel(JTable table, DataModel model) {
		JPanel sidePanel = new JPanel(new BorderLayout(5, 5));
		sidePanel.setBackground(MainFrame.BackgroundColor);

		JPanel buttonsPanel = createControlButtons(table, model);
		JPanel volumePanel = createVolumeControl();

		sidePanel.add(buttonsPanel, BorderLayout.NORTH);
		sidePanel.add(volumePanel, BorderLayout.CENTER);
		
		return sidePanel;
	}

	private static JPanel createControlButtons(JTable table, DataModel model) {
		String[] buttonIcons = { " ⬆️", " ⬇️", "🗑️" };
		JPanel buttonPanel = new JPanel(new GridLayout(buttonIcons.length, 1, 5, 5));
		buttonPanel.setBackground(MainFrame.BackgroundColor);

		for (int i = 0; i < buttonIcons.length; i++) {
			JButton btn = new JButton(buttonIcons[i]);
			styleButton(btn);
			buttonList.add(btn);
			
			final int actionIndex = i;
			btn.addActionListener(e -> handleButtonAction(actionIndex, table, model));
			
			buttonPanel.add(btn);
		}
		return buttonPanel;
	}

	private static void styleButton(JButton b) {
		b.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 26));
		b.setForeground(MainFrame.TextColor);
		b.setBackground(MainFrame.BackgroundColor);
		b.setPreferredSize(new Dimension(60, 60));
		b.setFocusPainted(false);
		b.setBorder(BorderFactory.createLineBorder(MainFrame.TextColor, 1));
	}

	private static void handleButtonAction(int action, JTable table, DataModel model) {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) return;

		switch (action) {
		case 0: 
			if (selectedRow > 0) {
				model.moveRow(selectedRow, selectedRow, selectedRow - 1);
				table.setRowSelectionInterval(selectedRow - 1, selectedRow - 1);
			}
			break;
		case 1: 
			if (selectedRow < queue.getQueue().size() - 1) {
				queue.moveDown(selectedRow);
				model.moveRow(selectedRow, selectedRow, selectedRow + 1);
				table.setRowSelectionInterval(selectedRow + 1, selectedRow + 1);
			}
			break;
		case 2: 
			queue.getQueue().remove(selectedRow);
			model.removeRow(selectedRow);
			break;
		}
	}

	private static JPanel createVolumeControl() {
		JPanel volumeWrapper = new JPanel(new BorderLayout());
		volumeWrapper.setBackground(MainFrame.BackgroundColor);
		
		JLabel volumeLabel = new JLabel("50%", JLabel.CENTER);
		volumeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
		volumeLabel.setForeground(MainFrame.TextColor);
		volumeLabel.setBorder(new EmptyBorder(10, 0, 5, 0));

		JSlider volumeSlider = new JSlider(JSlider.VERTICAL, 0, 100, 50);
		volumeSlider.setBackground(MainFrame.BackgroundColor);
		volumeSlider.setForeground(MainFrame.TextColor);
		volumeSlider.setPaintTrack(true);
		
		volumeSlider.addChangeListener(e -> {
			volumeLabel.setText(volumeSlider.getValue() + "%");
		});

		volumeWrapper.add(volumeLabel, BorderLayout.NORTH);
		volumeWrapper.add(volumeSlider, BorderLayout.CENTER);
		
		return volumeWrapper;
	}

	private static JScrollPane createStyledScrollPane(JTable table) {
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createLineBorder(MainFrame.TextColor));
		scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = MainFrame.TextColor;
				this.trackColor = MainFrame.BackgroundColor;
			}
		});
		return scrollPane;
	}

	private static void populateInitialQueue(DataModel model) {
		ManageDB managedb = ManageDB.getInstance();
		queue.getQueue().clear();

		while (queue.getQueue().size() < 30) {
			int r = ThreadLocalRandom.current().nextInt(1, managedb.getSongCount());
			Song s = managedb.getSongById(r);
			
			if (s != null && !queue.contains(s)) {
				queue.enqueue(s);
			}
		}

		for (Song s : new ArrayList<>(queue.getQueue())) {
			model.addRow(s);
		}
	}

	private static void setupTableEvents(JTable table, MainFrame main) {
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = table.getSelectedRow();
					if (row >= 0) {
						activeTable = table;
						Song current = queue.getQueue().get(row);
						playSelectedSong(current, main);
					}
				}
			}
		});
	}

	private static void playSelectedSong(Song song, MainFrame main) {
		main.setPlayingSong(song);

		if (main.getPlayerBar() == null) {
			main.setPlayerBar(songBar.createPlayerBar(main.getPlayingSong()));
			MainFrame.getInstance().mainPanel.add(main.getPlayerBar(), BorderLayout.SOUTH);
		}
		
		songBar.updateSongLabel(main.getPlayingSong());
		songBar.startProgressThread(main.getPlayingSong(), main);
		main.updateSongIcon(main.getPlayingSong());
		
		main.mainPanel.revalidate();
		main.mainPanel.repaint();
	}

	public static void playNextSong(Song actual, MainFrame main, boolean loop, boolean random, boolean end) {
		ArrayList<Song> listSongs = queue.getQueue();
		
		if (listSongs == null || listSongs.isEmpty()) {
			return;
		}

		int currentIndex = listSongs.indexOf(actual);
		int nextIndex = -1;

		if (loop && end) {
			nextIndex = currentIndex;
		} else if (random) {
			nextIndex = getRandomIndex(listSongs.size(), currentIndex);
		} else {
			nextIndex = getSequentialIndex(currentIndex, listSongs.size(), loop);
		}

		if (nextIndex != -1) {
			Song nextSong = listSongs.get(nextIndex);
			updateTableSelection(nextIndex);
			updateMainFrameSong(nextSong, main);
		}
	}

	private static int getRandomIndex(int size, int currentIndex) {
		if (size <= 1) return 0;
		
		int rIndex = currentIndex;
		while (rIndex == currentIndex) {
			rIndex = (int)(Math.random() * size);
		}
		return rIndex;
	}

	private static int getSequentialIndex(int currentIndex, int size, boolean loop) {
		if (currentIndex == -1) {
			return 0;
		}
		
		if (currentIndex >= size - 1) {
			return loop ? 0 : -1;
		}
		
		return currentIndex + 1;
	}

	public static void playPrevSong(Song actual, MainFrame main) {
		ArrayList<Song> listSongs = queue.getQueue();
		
		if (listSongs == null || listSongs.isEmpty()) {
			return;
		}

		int index = listSongs.indexOf(actual);
		
		if (index <= 0) {
			return;
		}
		
		int prevIndex = index - 1;
		Song prevSong = listSongs.get(prevIndex);
		
		updateTableSelection(prevIndex);
		updateMainFrameSong(prevSong, main);
	}

	private static void updateTableSelection(int index) {
		if (activeTable != null && index >= 0) {
			try {
				activeTable.setRowSelectionInterval(index, index);
				activeTable.scrollRectToVisible(activeTable.getCellRect(index, 0, true));
			} catch (Exception e) {
				System.out.println("Error UI tabla");
			}
		}
	}

	private static void updateMainFrameSong(Song song, MainFrame main) {
		main.setPlayingSong(song);
		
		if (main.getPlayingSongPanel() != null) {
			main.getPlayingSongPanel().updateSong(main.getPlayingSong());
		}

		songBar.updateSongLabel(main.getPlayingSong());
		songBar.startProgressThread(main.getPlayingSong(), main);
		main.updateSongIcon(main.getPlayingSong());
	}
}