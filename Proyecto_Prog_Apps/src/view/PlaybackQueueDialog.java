	package view;
	
	import java.awt.BorderLayout;
	import java.awt.Component;
	import java.awt.Dimension;
	import java.awt.FlowLayout;
	import java.awt.Font;
	import java.awt.Graphics;
	import java.awt.GridBagLayout;
	import java.awt.GridLayout;
	import java.awt.Insets;
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
	import javax.swing.border.Border;
	import javax.swing.plaf.basic.BasicButtonListener;
	import javax.swing.plaf.basic.BasicScrollBarUI;
	import javax.swing.table.DefaultTableCellRenderer;
	import javax.swing.table.DefaultTableModel;

import controller.ManageDB;
import model.Genre;
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
			
			MainFrame main = MainFrame.getInstance();
			
			ManageDB managedb = ManageDB.getInstance();
			
			Queue queue = new Queue();
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
						;
						break;
					case 2:
						queue.getQueue().remove(selectedRow);
						tableModel.removeRow(selectedRow);
						break;
					}
				});
	
			}
			
			for (int i = 0; i < 30; i++) {
				int r = ThreadLocalRandom.current().nextInt(1, managedb.getSongCount());
				queue.enqueue(managedb.getSongById(r));
			}
	
			for (Song s : queue.getQueue()) {
				Object[] row = { s.getTitle(), s.getBand(), model.Playlist.getDurationFormat(s.getDuration()) };
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
	
							main.setPlayingSong(new Song(title, duration, artist));
	
							if (main.getPlayerBar() == null) {
								main.setPlayerBar(songBar.createPlayerBar(main.getPlayingSong()));
								MainFrame.getInstance().mainPanel.add(main.getPlayerBar(), BorderLayout.SOUTH);
							}
							songBar.updateSongLabel(main.getPlayingSong());						
							main.updateSongIcon(main.getPlayingSong());
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
			soundButtonPanel.add(volumeLabel);
			buttonEastPanel.add(soundButtonPanel);
			buttonEastPanel.add(volumeSlider);
			mainPanel.add(buttonEastPanel, BorderLayout.EAST);
	
			return mainPanel;
		}
	
	}
