package app;
import javax.swing.SwingUtilities;

import view.LoginRegisterDialog;


public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			LoginRegisterDialog loginFrame = new LoginRegisterDialog();
			loginFrame.setVisible(true);			
		});
		


	}

}
