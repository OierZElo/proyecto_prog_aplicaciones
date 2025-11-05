package view;

import utils.Utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.Box.Filler;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Playlist;
import model.User;

public class LoginRegisterDialog extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public static JPanel panelPrincipal = new JPanel(new GridLayout(11,1));
	public static JLabel email = new JLabel("Email");
	public static JTextField emailFill = new JTextField("Enter your email...");
	public static JPanel panelEmailFill = new JPanel(new BorderLayout());
	public static JLabel password = new JLabel("Password");
	public static JPasswordField passwordFill = new JPasswordField();
	public static JPanel panelPasswordFill = new JPanel(new BorderLayout());
	public static JPanel panelButtons = new JPanel(new BorderLayout());
	public static JPanel Buttons = new JPanel(new GridLayout(1,2,5,5));
	public static JButton register = new JButton("Sign up");
	public static JButton login = new JButton("Login");
	
	public LoginRegisterDialog() {
		//Jframe
		setSize(300,225);
		setTitle("Login");
		setLocationRelativeTo(null);
		setResizable(false);
		
		 //general
		panelPrincipal.setVisible(true);
		panelPrincipal.setBackground(MainFrame.BackgroundColor);
	
		panelPrincipal.add(filler());
		
		//Email
		email.setForeground(MainFrame.TextColor);
		
		email.setHorizontalAlignment(JLabel.CENTER);
		panelPrincipal.add(email);
		
		//Email fill
		panelEmailFill.setBackground(MainFrame.BackgroundColor);
		panelEmailFill.setOpaque(true);
		emailFill.setBackground(MainFrame.TextColor);
		emailFill.setForeground(MainFrame.BorderColor);
		emailFill.setHorizontalAlignment(JTextField.CENTER);
		
		emailFill.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (emailFill.getText().equals("Enter your email...")) {
					emailFill.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (emailFill.getText().isEmpty()) {
					emailFill.setText("Enter your email...");
				}
			}
		});
		
		panelEmailFill.add(emailFill, BorderLayout.CENTER);
		panelEmailFill.add(filler(), BorderLayout.WEST);
		panelEmailFill.add(filler() , BorderLayout.EAST);


		panelPrincipal.add(panelEmailFill);

		panelPrincipal.add(filler());
		
		
		//Password
		password.setForeground(MainFrame.TextColor);
		password.setHorizontalAlignment(JLabel.CENTER);
		panelPrincipal.add(password);
		
		//Password fill
		passwordFill.setBackground(MainFrame.TextColor);
		passwordFill.setForeground(MainFrame.BackgroundColor);
		passwordFill.setHorizontalAlignment(JPasswordField.CENTER);
		panelPasswordFill.setBackground(MainFrame.BackgroundColor);
		panelPasswordFill.setOpaque(true);
	
		panelPasswordFill.add(passwordFill, BorderLayout.CENTER);
		panelPasswordFill.add(filler(), BorderLayout.EAST);
		panelPasswordFill.add(filler(), BorderLayout.WEST);
		panelPrincipal.add(panelPasswordFill);
		 
		//Buttons
		panelButtons.setBackground(MainFrame.BackgroundColor);
		panelButtons.setOpaque(true);
		panelPrincipal.add(filler());
		

		register.setBackground(MainFrame.BorderColor);
		register.setFocusPainted(false);
		login.setBackground(MainFrame.BorderColor);
		login.setFocusPainted(false);
		
		
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				MainFrame mainFrame = new MainFrame();
				mainFrame.setVisible(true);
				dispose();
			}
		});
		
		Buttons.add(register);
		Buttons.add(login);
		Buttons.setBackground(MainFrame.BackgroundColor);
		panelButtons.add(Buttons, BorderLayout.CENTER);
		panelButtons.add(filler(), BorderLayout.EAST);
		panelButtons.add(filler(), BorderLayout.WEST);
		panelPrincipal.add(panelButtons);		
		
		add(panelPrincipal);

	 }
	
	public static JPanel filler() {
		JPanel filler = new JPanel();
		filler.setBackground(MainFrame.BackgroundColor);
		filler.setPreferredSize(new Dimension(50,0));
		return filler;
	}
}
