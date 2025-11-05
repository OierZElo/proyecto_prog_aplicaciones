package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.JButton;
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
		result.setLayout(new BorderLayout(20, 10));
		JPanel userdata = new JPanel();; 
		 
		 
		 
		
		 JPanel LastSongs = new JPanel(); 
		 LastSongs.setBackground(MainFrame.BackgroundColor);

		 result.add(userdata, BorderLayout.NORTH);
// Window's north's settings: 
		 // users data display
		 userdata.setBackground(MainFrame.BackgroundColor);
		 userdata.setLayout(new BorderLayout(10, 10));
		 userdata.add(new JLabel(usuario.getPhoto()), BorderLayout.WEST);
		 userdata.add(generarDatos(usuario), BorderLayout.CENTER);
		 //user's data controlers's display:
		 userdata.add(botonesControl(),BorderLayout.SOUTH);
		 


		
		return result;
	} 
	
	private static JPanel generarDatos(User usuario) {
		JPanel r = new JPanel(new GridLayout(3, 0, 0, 10)); 
		r.setBackground(MainFrame.BackgroundColor);
		JLabel mail = new JLabel("Mail : " + usuario.getMail()); 
		JLabel name = new  JLabel("Username: " + usuario.getName()); 
		JLabel password = new JLabel("Password: " + usuario.getPassword()); 
		
		mail.setOpaque(true);
		name.setOpaque(true);
		password.setOpaque(true);
		
		r.add(mail);
		r.add(name);
		r.add(password);
		
		return r; }
		
	private static JPanel botonesControl() {
		JPanel r = new JPanel(); 
		r.setBackground(MainFrame.BackgroundColor);
		r.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		JButton cp = new JButton("Change profile's picture"); 
		JButton lg = new JButton("Sing out"); 
		JButton chp = new JButton("Change password "); 
		r.add(cp); 
		r.add(lg);
		r.add(chp);
	return r;
	}
	
	
	
	
	

}
