package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.Playlist;
import model.Queue;
import model.Song;
import model.StyledTable;
import utils.Utils;
import model.Playlist;
import view.MainFrame;

public class PlaybackQueueDialog extends JFrame {
	static ArrayList<JButton> buttonList = new ArrayList<JButton>();

	private static final long serialVersionUID = 1L;

	public static JPanel QueuePanel() {
		Queue queue = new Queue();
		String[] columns = { "Title", "Artist", "Duration" };
		String[] buttonIcons = { "  ⬆️", "  ⬇️", "🗑️" };
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel buttonEastPanel = new JPanel(new GridLayout(2, 1));
		JPanel soundButtonPanel = new JPanel(new GridLayout(buttonIcons.length, 1));
		soundButtonPanel.setBackground(MainFrame.BackgroundColor);
		buttonEastPanel.setBackground(MainFrame.BackgroundColor);

		JSlider volumeSlider = new JSlider(JSlider.VERTICAL, 0, 100, 50);
		volumeSlider.setBackground(MainFrame.BackgroundColor);
	
		
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		JTable songTable = new StyledTable(tableModel);

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
				int selectedRow = songTable.getSelectedRow();
				switch (index) {
				case 0:
					if (selectedRow < queue.getQueue().size() && selectedRow > 0) {
						queue.moveUp(selectedRow);
						tableModel.moveRow(selectedRow, selectedRow, selectedRow - 1);
				        songTable.setRowSelectionInterval(selectedRow - 1, selectedRow - 1);
					}
					break;
				case 1:
					if (selectedRow >= 0 && selectedRow < queue.getQueue().size() - 1) {
					queue.moveDown(selectedRow);
					tableModel.moveRow(selectedRow, selectedRow, selectedRow + 1);
			        songTable.setRowSelectionInterval(selectedRow + 1, selectedRow + 1);
					}
;					break;
				case 2:
					queue.getQueue().remove(selectedRow);
					tableModel.removeRow(selectedRow);
					break;
				}
			});

		}

		
		for (Song s : Utils.songs) {
			queue.enqueue(s);
		}
		
		for(Song s : queue.getQueue()) {
			Object[] row = {s.getTitle(), s.getBand(), model.Playlist.getDurationFormat(s.getDuration())};
            tableModel.addRow(row);
		}


		songTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = songTable.getSelectedRow();
					if (row >= 0) {
						String title = (String) tableModel.getValueAt(row, 0);
						String artist = (String) tableModel.getValueAt(row, 1);
						int duration = Playlist.parseDuration((String) tableModel.getValueAt(row, 2));

						MainFrame.playingSong = new Song(title, duration, artist);

						// MainFrame.getInstance().showPlayingSong();
//		            songBar.updateSlider(MainFrame.playingSong);

						if (MainFrame.playerBar == null) {
							MainFrame.playerBar = songBar.createPlayerBar(MainFrame.playingSong);
							MainFrame.getInstance().mainPanel.add(MainFrame.playerBar, BorderLayout.SOUTH);
						}
						songBar.updateSongLabel(MainFrame.playingSong);
						PlayingSong.modifyPlayingSong();
						mainPanel.revalidate();
						mainPanel.repaint();
					}
				}
			}
		});
		
		

		// JScrollPane
		JScrollPane scrollPane = new JScrollPane(songTable);
		scrollPane.getViewport().setBackground(MainFrame.BackgroundColor);
		scrollPane.setBorder(null);

		mainPanel.add(scrollPane, BorderLayout.CENTER);
		buttonEastPanel.add(soundButtonPanel);
		buttonEastPanel.add(volumeSlider);
		mainPanel.add(buttonEastPanel, BorderLayout.EAST);

//	    Queue queue = new Queue();

//	    queue.enqueue(Utils.songs.get(3));
//	    queue.enqueue(Utils.songs.get(5));
//	    queue.enqueue(Utils.songs.get(2));
//	    queue.enqueue(Utils.songs.get(15));
//	    queue.enqueue(Utils.songs.get(25));

		return mainPanel;
	}

}
