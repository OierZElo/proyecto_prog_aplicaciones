package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.Queue;
import model.Song;
import utils.Utils;

public class PlaybackQueueDialog extends JFrame {
	public static JPanel QueuePanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());
        String[] columns = {"Title", "Artist", "Duration"};
        
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		
		for (Song s : Utils.songs) {
            Object[] row = {s.getTitle(), s.getBand(), model.Playlist.getDurationFormat(s.getDuration())};
            tableModel.addRow(row);
        }
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		
		JTable songTable = new JTable(tableModel);
		songTable.setTableHeader(null);
		songTable.setShowGrid(false);
	    songTable.setFillsViewportHeight(true);
	    songTable.setBackground(MainFrame.BackgroundColor);
	    songTable.setForeground(MainFrame.TextColor);
	    songTable.setSelectionBackground(MainFrame.BorderColor);
	    songTable.setSelectionForeground(MainFrame.TextColor);
	    songTable.setRowHeight(40);
		songTable.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
		songTable.setDefaultRenderer(Object.class, centerRenderer);


	    // JScrollPane
	    JScrollPane scrollPane = new JScrollPane(songTable);
	    scrollPane.getViewport().setBackground(MainFrame.BackgroundColor);
	    scrollPane.setBorder(null);

	    mainPanel.add(scrollPane, BorderLayout.CENTER);


		
//	    Queue queue = new Queue();
	    
//	    queue.enqueue(Utils.songs.get(3));
//	    queue.enqueue(Utils.songs.get(5));
//	    queue.enqueue(Utils.songs.get(2));
//	    queue.enqueue(Utils.songs.get(15));
//	    queue.enqueue(Utils.songs.get(25));
	    
		
		
	    
		
		
		
		
		
		

		
		
		return mainPanel;
	}


}
