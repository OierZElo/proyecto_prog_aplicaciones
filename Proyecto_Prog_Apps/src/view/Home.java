package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import utils.Utils;
import view.MainFrame;

public class Home {
	
	
	public static JPanel PanelHome() {
		JPanel result = new JPanel();
		result.setName("home");
		result.setBackground(MainFrame.BackgroundColor);
		result.setLayout(new BorderLayout());
		JPanel norte = new JPanel(); 
		norte.setLayout(new GridLayout(Math.round(Utils.generos.size()/2), 0));
		norte.setBackground(MainFrame.BackgroundColor);
	
		result.add(norte, BorderLayout.NORTH);
		
		for(String g : Utils.generos) {
			JButton boton = new JButton(g);
			boton.setBackground(Color.decode("#FF6F61"));
			boton.setOpaque(true);
			norte.add(boton);}
		
		JPanel sur = new JPanel(); 
		sur.setBackground(MainFrame.BackgroundColor);
		Object[][] datos = new Object[Utils.canciones.size()][4];
		for (int i = 0; i< Utils.canciones.size(); i++) { 
		
			String[] cancion= {Utils.canciones.get(i).getTitulo(),
					Utils.canciones.get(i).getArtista(), 
					Utils.canciones.get(i).getDuracion(),
					Utils.canciones.get(i).getAlbum()};
			datos[i] = cancion; 
		}
		Object[] columna = {"Canción", "Cantante", "Duración", "Album"};
		DefaultTableModel modelo = new DefaultTableModel(datos, columna); 
		JTable tablaCanciones = new JTable(modelo);
		JScrollPane scroll = new JScrollPane(tablaCanciones); 
		sur.add(scroll);
		
		
//		sur.setLayout(new GridLayout(Math.round(Utils.canciones.size()/2), 0)); 
//		for(Song g : Utils.canciones) {
//			JButton boton = new JButton(g.getTitulo());
//			boton.setBackground(Color.RED);
//			boton.setOpaque(true);
//			sur.add(boton);}
		
		result.add(sur, BorderLayout.CENTER);

	
	return result; }
	

	

}
