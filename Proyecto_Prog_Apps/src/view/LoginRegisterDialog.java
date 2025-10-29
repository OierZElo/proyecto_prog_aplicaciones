package view;

import utils.Utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Playlist;

public class LoginRegisterDialog extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public static JPanel panelPrincipal = new JPanel(new GridLayout(20,1));
	public static JLabel username = new JLabel("Username");
	public static JTextField usernameFill = new JTextField("username");
	public static JPanel panelUsernameFill = new JPanel(new GridLayout(1,3));
	public static JLabel password = new JLabel("Password");
	public static JPasswordField passwordFill = new JPasswordField();
	public static JPanel panelPasswordFill = new JPanel(new GridLayout(1,3));
	public static JPanel panelButtons = new JPanel(new GridLayout(1,4,5,5));
	public static JButton register = new JButton("Sign up");
	public static JButton login = new JButton("Login");
	
	public LoginRegisterDialog() {
		//Jframe
		setSize(600,400);
		setTitle("Login");
		setLocationRelativeTo(null);
		setResizable(false);
		
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
		usernameFill.setBackground(MainFrame.TextColor);
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
		JPanel filler2 = new JPanel();
		filler2.setBackground(MainFrame.BackgroundColor);
		filler2.setOpaque(true);
		panelPrincipal.add(filler2);
		
		
		//Password
		password.setForeground(MainFrame.TextColor);
		password.setHorizontalAlignment(JLabel.CENTER);
		panelPrincipal.add(password);
		
		//Password fill
		JLabel provisional3 = new JLabel();
		provisional3.setBackground(MainFrame.BackgroundColor);
		provisional3.setOpaque(true);
		JLabel provisional4 = new JLabel();
		provisional4.setBackground(MainFrame.BackgroundColor);
		provisional4.setOpaque(true);
		passwordFill.setBackground(MainFrame.TextColor);
		passwordFill.setForeground(MainFrame.BackgroundColor);
		passwordFill.setHorizontalAlignment(JPasswordField.CENTER);
		panelPasswordFill.add(provisional3);
		panelPasswordFill.add(passwordFill);
		panelPasswordFill.add(provisional4);
		panelPrincipal.add(panelPasswordFill);
		 
		//Buttons
		panelButtons.setBackground(MainFrame.BackgroundColor);
		panelButtons.setOpaque(true);
		JPanel filler3 = new JPanel();
		filler3.setBackground(MainFrame.BackgroundColor);
		filler3.setOpaque(true);
		panelPrincipal.add(filler3);
		
		JLabel provisional5 = new JLabel();
		provisional5.setBackground(MainFrame.BackgroundColor);
		provisional5.setOpaque(true);
		JLabel provisional6 = new JLabel();
		provisional6.setBackground(MainFrame.BackgroundColor);
		provisional6.setOpaque(true);
		register.setBackground(MainFrame.BorderColor);
		login.setBackground(MainFrame.BorderColor);
		panelButtons.add(provisional5);
		
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				MainFrame mainFrame = new MainFrame();
				mainFrame.setVisible(true);
				dispose();
			}
		});
		
		panelButtons.add(register);
		panelButtons.add(login);
		panelButtons.add(provisional6);
		panelPrincipal.add(panelButtons);		
		
		add(panelPrincipal);
	 }
}
