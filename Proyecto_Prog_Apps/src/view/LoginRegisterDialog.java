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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

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
	public static JButton seePassword = new JButton("👁️");
	public static JPanel panelSeePassword = new JPanel(new BorderLayout());
	public static boolean found = false;
	
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
		emailFill.setBackground(MainFrame.BorderColor);
		emailFill.setForeground(MainFrame.BackgroundColor);
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
		passwordFill.setBackground(MainFrame.BorderColor);
		passwordFill.setForeground(MainFrame.BackgroundColor);
		passwordFill.setHorizontalAlignment(JPasswordField.CENTER);
		panelPasswordFill.setBackground(MainFrame.BackgroundColor);
		panelPasswordFill.setOpaque(true);
		
		seePassword.setBackground(MainFrame.BackgroundColor);
		seePassword.setFocusPainted(false);
		seePassword.setForeground(MainFrame.TextColor);
		seePassword.setBorder(BorderFactory.createEmptyBorder());
		
		char defaultEcho = passwordFill.getEchoChar();
		
		seePassword.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (seePassword.getText().equals("👁️")) {
					passwordFill.setEchoChar((char)0);
					seePassword.setText("🙈");
				} else {
					passwordFill.setEchoChar(defaultEcho);
					seePassword.setText("👁️");
				}
			}
		});
		
		panelSeePassword.setPreferredSize(new Dimension(50,30));
		panelSeePassword.setMinimumSize(new Dimension(10,30));
		panelSeePassword.add(seePassword, BorderLayout.CENTER);
		panelSeePassword.add(filler2(), BorderLayout.EAST);
		panelSeePassword.add(filler2(), BorderLayout.WEST);
	
		
		
		panelPasswordFill.add(passwordFill, BorderLayout.CENTER);
		panelPasswordFill.add(filler(), BorderLayout.WEST);
		panelPasswordFill.add(panelSeePassword, BorderLayout.EAST);
		
		panelPrincipal.add(panelPasswordFill);
		 
		//Buttons
		panelButtons.setBackground(MainFrame.BackgroundColor);
		panelButtons.setOpaque(true);
		panelPrincipal.add(filler());
		

		register.setBackground(MainFrame.BorderColor);
		register.setFocusPainted(false);
		login.setBackground(MainFrame.BorderColor);
		login.setFocusPainted(false);

		
		Utils.generateUsers();
		
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
//				found = false;
				for(User user: Utils.users) {
			
					
					if(user.getMail().equals(emailFill.getText()) 
							&& user.getPassword().equals(new String(passwordFill.getPassword()))) {
						found = true;
						MainFrame mainFrame = new MainFrame(user);
						mainFrame.setVisible(true);
						panelPrincipal.removeAll();
						dispose();
						break;
					}
				}
				if (!found) {
					JOptionPane.showMessageDialog(null, "Email and password don't match", "NOT FOUND!", JOptionPane.ERROR_MESSAGE);
				}
				
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
	
	public static JPanel filler2() {
		JPanel filler = new JPanel();
		filler.setBackground(MainFrame.BackgroundColor);
		filler.setPreferredSize(new Dimension(2,0));
		return filler;
	}
}
