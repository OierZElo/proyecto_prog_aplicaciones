package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import model.Song;

public class DataModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Song> songs; 
	private List<String> col = List.of("Song title", "Band","Duration", "Genre"); 
	
	
	public DataModel(List<Song> songs) {
		this.songs = songs;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.songs.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return col.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return songs.get(rowIndex);}
		else if (columnIndex == 1) {return songs.get(rowIndex).getBand();}
		else if (columnIndex == 2) {return songs.get(rowIndex).getDuration();}
		return songs.get(rowIndex).getGenre();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return col.get(column);
	}
	
	
	
	public void addRow(Song rowData) {
	    songs.add(rowData);
	    int row = songs.size() - 1;
	    fireTableRowsInserted(row, row);
	}
	
	public void removeRow(int row) {
	    songs.remove(row);
	    fireTableRowsDeleted(row, row);
	}
	
	public void moveRow(int start, int end, int to) {
	    if (start < 0 || end >= songs.size() || start > end) return;

	    // Si el destino está dentro del rango, no hacemos nada (igual que DefaultTableModel)
	    if (to >= start && to <= end) return;

	    List<Song> rowsToMove = new ArrayList<>();

	    //extraemos filas 
	    for (int i = start; i <= end; i++) {
	        rowsToMove.add(songs.get(i));
	    }

	    //eliminamos filas originales
	    for (int i = end; i >= start; i--) {
	        songs.remove(i);
	    }

	    // ajustamos el índice destino si está después del bloque eliminado
	    if (to > start) {
	        to -= (end - start + 1);
	    }

	    // Insertar en nueva posición
	    songs.addAll(to, rowsToMove);

	    fireTableDataChanged();
	}




}
