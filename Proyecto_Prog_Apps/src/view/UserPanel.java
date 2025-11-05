package view;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.User;
import utils.Utils;

public class UserPanel {

	
	public static JPanel UserPanel() {
	// de momento usaremos este user de prueba para el display 
		User usuario = new User("usuario@mail.com", "randomPWRD");
		JPanel result = new JPanel(); 
		result.setBackground(MainFrame.BackgroundColor);
		result.setLayout(new BorderLayout());
		 JPanel userdata = new JPanel();; 
		 userdata.setLayout(new BorderLayout());
		 
		 JPanel dataControl = new JPanel(); 
		 JPanel LastSongs = new JPanel(); 
		 
		 result.add(userdata, BorderLayout.NORTH);
		 userdata.add(dataControl,BorderLayout.SOUTH);
		 userdata.add(new JLabel(usuario.getPhoto()), BorderLayout.WEST);
		
		return result;
	} 

	
	
	
	
	

}
