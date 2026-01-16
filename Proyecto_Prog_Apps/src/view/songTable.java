package view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicScrollBarUI;
import model.Playlist;
import model.Song;
import model.StyledTable;

public class songTable {
	static int hoverRow = -1; 
	static int hoverColumn = -1; 
	
	public static JPanel createSongTablePlaylist(Playlist playlist) {
		return createSongTableArrayList((ArrayList<Song>) playlist.getL_songs());
	}
	
	public static JPanel createSongTableArrayList(ArrayList<Song> songs) {
		MainFrame main = MainFrame.getInstance();
		JPanel mainPanel = new JPanel(new BorderLayout());
		DataModel tableModel = new DataModel(songs);
		JTable songTable = new StyledTable(tableModel);
		
		songTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = songTable.getSelectedRow();
					if (row >= 0) {
						Song currentSong = songs.get(row);
						PlaybackQueueDialog.queue.getQueue().clear();
						PlaybackQueueDialog.queue.getQueue().addAll(songs);
						PlaybackQueueDialog.activeTable = songTable;
						main.setPlayingSong(currentSong);

						if (main.getPlayerBar() == null) {
							main.setPlayerBar(songBar.createPlayerBar(main.getPlayingSong()));
							main.mainPanel.add(main.getPlayerBar(), BorderLayout.SOUTH);
						}
						
						songBar.updateSongLabel(main.getPlayingSong());						
						main.updateSongIcon(main.getPlayingSong());
						songBar.startProgressThread(main.getPlayingSong(), main);
						mainPanel.revalidate();
						mainPanel.repaint();
					}
				}
			}
		});
		
		// JScrollPane
		JScrollPane scrollPane = new JScrollPane(songTable);
		scrollPane.setColumnHeaderView(null);

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