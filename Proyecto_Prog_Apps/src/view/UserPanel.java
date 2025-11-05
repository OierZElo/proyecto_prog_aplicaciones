package view;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.User;
import utils.Utils;

public class UserPanel extends JFrame{

	private static final long serialVersionUID = 1L;
	private User usuario;
	
	public UserPanel(User usuario) throws HeadlessException {
		super();
		this.usuario = usuario;
		this.setBackground(MainFrame.BackgroundColor);
		this.setLayout(new BorderLayout());
		 JPanel userdata = new JPanel();; 
		 userdata.setLayout(new BorderLayout());
		 
		 JPanel dataControl = new JPanel(); 
		 JPanel LastSongs = new JPanel(); 
		 
		 this.add(userdata, BorderLayout.NORTH);
		 userdata.add(dataControl,BorderLayout.SOUTH);
		 userdata.add(new JLabel(usuario.getPhoto()), BorderLayout.WEST);
		
		
	} 
//	
//	public static void main(String[] args) { 
//		UserPanel p = new UserPanel(Utils.u);
//	}
	
	
	
	
	

}
