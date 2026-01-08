package controller;

import java.util.Arrays;

import javax.swing.ImageIcon;

import model.User;
import view.LoginRegisterDialog;
import view.MainFrame;
import view.songBar;

public class UserController {
	
	// parametro de entrada para poder actualizar la base de datos en base a los cambios del usuario
	private ManageDB db;
	
	public UserController(ManageDB db) {
		super();
		this.db = db;
		
	} 
	
	//  metodo para  gestionar lógica de cambio de contraseña 
	public boolean changePassword(User user, char[] p1, char[] p2) {
		if (!Arrays.equals(p1, p2)) {
			return false; // se han metido 2 contraseñas diferentes, no actualizamos.
		}
		user.setPassword(new String(p1)); // actualizamos la contraseña del usuario
	    db.updatePassword(user, user.getPassword());
		
		return true;
	}
	
	// metodo para gestionar la lógica del cambio de foto de perfil
	public boolean changePhoto(User user, String url) {
		ImageIcon icono = new ImageIcon(url);
		// comprobamos que la foto se haya cargado bien
		if ( icono.getIconWidth() == -1 ) {
			return false; 
		}
		
		user.setPhoto(icono);
		return true;
	}
	
	public void logout() {
        songBar.reset();
        MainFrame.getInstance().dispose();
        new LoginRegisterDialog().setVisible(true);
    }

}
