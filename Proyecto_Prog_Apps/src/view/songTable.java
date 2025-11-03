package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;

import model.Playlist;
import model.Song;
import model.StyledTable;
import utils.Utils;

public class songTable {
	
	
	public static JPanel createSongTablePlaylist(Playlist playlist) {
		MainFrame main = MainFrame.getInstance();

		String[] columns = { "Title", "Artist", "Duration" };
		JPanel mainPanel = new JPanel(new BorderLayout());

		DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		for (Song s : playlist.getL_songs()) {
			Object[] row = { s.getTitle(), s.getBand(), model.Playlist.getDurationFormat(s.getDuration()) };
			tableModel.addRow(row);
		}

		JTable songTable = new StyledTable(tableModel);

		songTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = songTable.getSelectedRow();
					if (row >= 0) {
						String title = (String) tableModel.getValueAt(row, 0);
						String artist = (String) tableModel.getValueAt(row, 1);
						int duration = Playlist.parseDuration((String) tableModel.getValueAt(row, 2));

						
						main.setPlayingSong(new Song(title, duration, artist));

						if (main.getPlayerBar() == null) {
							main.setPlayerBar(songBar.createPlayerBar(main.getPlayingSong()));
							main.mainPanel.add(main.getPlayerBar(), BorderLayout.SOUTH);
						}
						songBar.updateSongLabel(main.getPlayingSong());						
						MainFrame.updateSongIcon(main.getPlayingSong());
						mainPanel.revalidate();
						mainPanel.repaint();
					}
				}
			}
		});

		// JScrollPane
		JScrollPane scrollPane = new JScrollPane(songTable);
		scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = MainFrame.TextColor;
				this.trackColor = MainFrame.BackgroundColor;
			}
		});

		mainPanel.add(scrollPane, BorderLayout.CENTER);

		return mainPanel;
	}
}