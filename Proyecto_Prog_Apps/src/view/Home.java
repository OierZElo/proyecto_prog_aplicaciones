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
import view.MainFrame;
import utils.Utils;

public class Home {
	
	
	public static JPanel PanelHome() {
		JPanel result = new JPanel();
		result.setName("home");
		result.setBackground(MainFrame.BackgroundColor);
		result.setLayout(new BorderLayout());
		JPanel norte = new JPanel(); 
		norte.setLayout(new GridLayout(Math.round(Utils.genres.length/2), 0));
		norte.setBackground(MainFrame.BackgroundColor);
	
		result.add(norte, BorderLayout.NORTH);
		
		for(String g : Utils.genres) {
			JButton boton = new JButton(g);
			boton.setBackground(MainFrame.BackgroundColor);
			boton.setOpaque(true);
			norte.add(boton);}
		
		JPanel sur = new JPanel(); 
		sur.setBackground(MainFrame.BackgroundColor);
		


		result.add(sur, BorderLayout.CENTER);

	
	return result; }
	

	

}
