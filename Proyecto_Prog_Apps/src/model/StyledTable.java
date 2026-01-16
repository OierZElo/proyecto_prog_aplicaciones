package model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

import view.DataModel;
import view.MainFrame;

public class StyledTable extends JTable {
	 private static final long serialVersionUID = 1L;
	 int hoverRow = -1; 
	 int hoverColumn = -1; 
	
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
			    result.setBackground(bg);
			   
				
				if (column == 0 && value instanceof Song ) {
					
					Song song = (Song) value; 
					result.setText(song.getTitle());
		            if (row == hoverRow && column == hoverColumn ) {
	        		    ImageIcon icon = new ImageIcon(String.format("src/resources/icons/%d.png", song.getCod()));
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
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				hoverColumn = -1; 
				hoverRow = -1;
				StyledTable.this.repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
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
			}
		});

	}

}
