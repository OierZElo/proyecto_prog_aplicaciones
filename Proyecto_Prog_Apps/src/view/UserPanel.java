package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.BorderFactory;
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
		
// JPanel container of all the UserPanel's window	
		JPanel result = new JPanel(); 
		result.setBorder(BorderFactory.createLineBorder(MainFrame.BackgroundColor, 10));

		result.setBackground(MainFrame.BackgroundColor);
		result.setLayout(new BorderLayout(20, 10));
		 
		 
		 
		
		 JPanel LastSongs = new JPanel(); 
		 LastSongs.setBackground(MainFrame.BackgroundColor);

// Window's north's settings: 
		 // users data display
		JPanel userdata = new JPanel();; 
		 userdata.setBackground(MainFrame.BackgroundColor);
		 userdata.setLayout(new BorderLayout(10, 10));
		 userdata.add(new JLabel(usuario.getPhoto()), BorderLayout.WEST);
		 userdata.add(generarDatos(usuario), BorderLayout.CENTER);
		 //user's data controlers's display:
		 userdata.add(botonesControl(),BorderLayout.SOUTH);
		 result.add(userdata, BorderLayout.NORTH);

// Windoe's center's display: 
		 JPanel lsl = new JPanel(); 
		 lsl.setLayout(new BorderLayout(10, 10));
		 lsl.setBackground(MainFrame.BackgroundColor);

		 JLabel title = new JLabel("LAST 10 SONGS LISTENED"); 
		 title.setHorizontalAlignment(JLabel.CENTER);
		 title.setBackground(MainFrame.BackgroundColor);
		 title.setForeground(MainFrame.TextColor);
		 Font font = new Font("Arial", Font.BOLD, 24);
		 title.setFont(font);
		 lsl.add(title);
		 result.add(lsl, BorderLayout.CENTER);


		
		return result;
	} 
	
	private static JPanel generarDatos(User usuario) {
		JPanel r = new JPanel(new GridLayout(3, 0, 0, 10)); 
		r.setBackground(MainFrame.BackgroundColor);
		JLabel mail = new JLabel("Mail : " + usuario.getMail()); 
		JLabel name = new  JLabel("Username: " + usuario.getName()); 
		JLabel password = new JLabel("Password: " + usuario.getPassword());
		// text Alignment 
		mail.setHorizontalAlignment(JLabel.CENTER);
		name.setHorizontalAlignment(JLabel.CENTER);
		password.setHorizontalAlignment(JLabel.CENTER);

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
