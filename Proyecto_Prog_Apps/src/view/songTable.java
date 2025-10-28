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
import javax.swing.table.DefaultTableModel;

import model.Playlist;
import model.Song;
import model.StyledTable;
import utils.Utils;

public class songTable {
	public JPanel createSongTablePlaylist(Playlist playlist) {
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
        final boolean[] isAlreadyOneClick = {false};

		songTable.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
					int row = songTable.rowAtPoint(e.getPoint());
					if (row >= 0) {
						String title = (String) tableModel.getValueAt(row, 0);
						String artist = (String) tableModel.getValueAt(row, 1);
						int duration = Playlist.parseDuration((String) tableModel.getValueAt(row, 2));

						MainFrame.playingSong = new Song(title, duration, artist);

						if (MainFrame.playerBar == null) {
							MainFrame.playerBar = songBar.createPlayerBar(MainFrame.playingSong);
							MainFrame.getInstance().mainPanel.add(MainFrame.playerBar, BorderLayout.SOUTH);
						}	
				}
			}

		});

		// JScrollPane
		JScrollPane scrollPane = new JScrollPane(songTable);
		scrollPane.getViewport().setBackground(MainFrame.BackgroundColor);
		scrollPane.setBorder(null);

		mainPanel.add(scrollPane, BorderLayout.CENTER);

		return mainPanel;
	}
}