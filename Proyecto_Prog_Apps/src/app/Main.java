package app;
import javax.swing.SwingUtilities;

import view.ConfigManager;
import view.LoginRegisterDialog;


public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			ConfigManager.loadColors();
			LoginRegisterDialog loginFrame = new LoginRegisterDialog();
			loginFrame.setVisible(true);			
		});
	}
}