package model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import view.DataModel;
import view.MainFrame;

public class StyledTable extends JTable {
	static int hoverRow = -1; 
	static int hoverColumn = -1; 
	
	public StyledTable(DataModel model) {
		setModel(model);
		initStyle();
	}
	
	private void initStyle() {
		setTableHeader(null);
		setShowGrid(false);
		setFillsViewportHeight(true);
		setBackground(MainFrame.BackgroundColor);
		setForeground(MainFrame.TextColor);
		setSelectionBackground(MainFrame.BorderColor);
		setSelectionForeground(MainFrame.TextColor);
		setRowHeight(40);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));

		//DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		setDefaultRenderer(Object.class, new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				
		        Color bg = isSelected ? table.getSelectionBackground() : MainFrame.BackgroundColor;
		        JLabel result = new JLabel(value.toString());
				result.setOpaque(true);
				result.setFont(new Font("SansSerif", Font.ITALIC, 14));
	            result.setForeground(MainFrame.TextColor);
	            result.setHorizontalAlignment(JLabel.CENTER);
			  //  result.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			    result.setBackground(MainFrame.BackgroundColor);
			   
				
				if (column == 0 && value instanceof Song ) {
					
					Song song = (Song) value; 
					result.setText(song.getTitle());
		            if (row == hoverRow && column == hoverColumn ) {
		            	String path = "src/resources/icons/" + song.getTitle() + ".png"; 
		        		File file = new File(path);
	        		    ImageIcon icon = new ImageIcon("src/resources/icons/SongIcon.png");
		        		if(file.exists()){
		        		    icon = new ImageIcon(path);
		        		} 
		                int height = table.getRowHeight(row);
		                Image scaled = icon.getImage().getScaledInstance(height - 10, height - 10, Image.SCALE_SMOOTH);
		            	result.setIcon(new ImageIcon(scaled));    // icono de la canción
		                result.setText("");      
		            }
				}
				if (column == 2) {
					result.setText(Playlist.getDurationFormat((int) value));
				}
				
				return result;
			}
		});
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				int row = StyledTable.this.rowAtPoint(e.getPoint()); 
				int col = StyledTable.this.columnAtPoint(e.getPoint()); 
				if (row != hoverRow || col != hoverColumn) {
					hoverRow = row; 
					hoverColumn = col; 
					StyledTable.this.repaint();
							
				}
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		//centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		//setDefaultRenderer(Object.class, centerRenderer);

	}

}
