package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

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

//		String[] columns = { "Title", "Artist", "Duration", "Genre" };
		JPanel mainPanel = new JPanel(new BorderLayout());
		DataModel tableModel = new DataModel(songs);
//		DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
//			@Override
//			public boolean isCellEditable(int row, int column) {
//				return false;
//			}
//		};

//		for (Song s : songs) {
//			Object[] row = { s.getTitle(), s.getBand(), Playlist.getDurationFormat(s.getDuration()), s.getGenre() };
//			tableModel.addRow(row);
//		}

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
		
		// Table renderer 
//		songTable.getColumnModel().getColumn(0).setPreferredWidth(100);
//		songTable.setRowHeight(60);
//		songTable.setTableHeader(null);
//		songTable.setShowGrid(false);

//		songTable.setDefaultRenderer(Object.class, new TableCellRenderer() {
//		
//			@Override
//			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
//					int row, int column) {
//				
//		        Color bg = isSelected ? table.getSelectionBackground() : MainFrame.BackgroundColor;
//		        JLabel result = new JLabel(value.toString());
//				result.setOpaque(true);
//				result.setFont(new Font("SansSerif", Font.ITALIC, 14));
//	            result.setForeground(MainFrame.TextColor);
//	            result.setHorizontalAlignment(JLabel.CENTER);
//			  //  result.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			    result.setBackground(MainFrame.BackgroundColor);
//				
//				if (column == 0 && value instanceof Song ) {
//					
//					Song song = (Song) value; 
//					result.setText(song.getTitle());
//		            if (row == hoverRow && column == hoverColumn ) {
//		            	ImageIcon icon = song.getImage();
//		                int height = table.getRowHeight(row);
//		                Image scaled = icon.getImage().getScaledInstance(height - 10, height - 10, Image.SCALE_SMOOTH);
//		            	result.setIcon(new ImageIcon(scaled));    // icono de la canción
//		                result.setText("");      
//		            }
//				}
//				if (column == 2) {
//					
//				}
//				
//				return result;
//			}
//		});

//		songTable.addMouseMotionListener(new MouseMotionListener() {
//			
//			@Override
//			public void mouseMoved(MouseEvent e) {
//				int row = songTable.rowAtPoint(e.getPoint()); 
//				int col = songTable.columnAtPoint(e.getPoint()); 
//				if (row != hoverRow || col != hoverColumn) {
//					hoverRow = row; 
//					hoverColumn = col; 
//					songTable.repaint();
//							
//				}
//				
//			}
//			
//			@Override
//			public void mouseDragged(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
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