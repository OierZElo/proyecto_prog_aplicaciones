package view;

import utils.Utils;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Playlist;

public class LoginRegisterDialog extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public static JPanel panelPrincipal = new JPanel(new GridLayout(20,1));
	public static JLabel username = new JLabel("Username");
	public static JTextField usernameFill = new JTextField("username");
	public static JPanel panelUsernameFill = new JPanel(new GridLayout(1,3));
	
	public static JPanel LoginRegisterDialogPanel() {
		 //general
		panelPrincipal.setVisible(true);
		panelPrincipal.setBackground(MainFrame.BackgroundColor);
		JLabel filler = new JLabel();
		filler.setBackground(MainFrame.BackgroundColor);
		filler.setOpaque(true);
		panelPrincipal.add(filler);
		
		//Username
		username.setForeground(MainFrame.TextColor);
		
		username.setHorizontalAlignment(JLabel.CENTER);
		panelPrincipal.add(username);
		
		//Username fill
		JLabel provisional = new JLabel();
		provisional.setBackground(MainFrame.BackgroundColor);
		provisional.setOpaque(true);
		JLabel provisional2 = new JLabel();
		provisional2.setBackground(MainFrame.BackgroundColor);
		provisional2.setOpaque(true);
		panelUsernameFill.add(provisional);
		usernameFill.setForeground(MainFrame.BorderColor);
		usernameFill.setHorizontalAlignment(JTextField.CENTER);
		
		usernameFill.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (usernameFill.getText().equals("username")) {
					usernameFill.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (usernameFill.getText().isEmpty()) {
					usernameFill.setText("username");
				}
			}
		});
		
		panelUsernameFill.add(usernameFill);
		panelUsernameFill.add(provisional2);
		panelPrincipal.add(panelUsernameFill);
		 
		
		return panelPrincipal;
	 }
	

}
