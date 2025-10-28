package model;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import view.MainFrame;

public class StyledTable extends JTable {
	public StyledTable(DefaultTableModel model) {
		setModel(model);
		initStyle();}
	
	private void initStyle(){			
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

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		setDefaultRenderer(Object.class, centerRenderer);

	}

	
}
