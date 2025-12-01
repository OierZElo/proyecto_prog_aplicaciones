package view;

import utils.Utils;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.User;
import controller.ManageDB;

public class LoginRegisterDialog extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField emailField;
	private JPasswordField passwordField;
	private JButton loginButton, registerButton, seePasswordButton;
	private boolean found = false;
	private MainFrame main;
	private ManageDB managedb;

	public LoginRegisterDialog() {
		managedb = ManageDB.getInstance();
		setTitle("Login");
		setSize(300, 225);
		setLocationRelativeTo(null);
		setResizable(false);
		JPanel mainPanel = new JPanel(new GridLayout(0, 1, 0, 10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
		mainPanel.setBackground(MainFrame.BackgroundColor);
		
	
		JLabel emailLabel = new JLabel("Email", SwingConstants.CENTER);
		emailLabel.setForeground(MainFrame.TextColor);
		mainPanel.add(emailLabel);

		emailField = new JTextField("Enter your email...");
		emailField.setBackground(MainFrame.BorderColor);
		emailField.setForeground(MainFrame.BackgroundColor);
		emailField.setHorizontalAlignment(JTextField.CENTER);
		emailField.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				if (emailField.getText().equals("Enter your email...")) {
					emailField.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (emailField.getText().isEmpty()) {
					emailField.setText("Enter your email...");
				}
			}
		});
		mainPanel.add(emailField);

		JLabel passwordLabel = new JLabel("Password", SwingConstants.CENTER);
		passwordLabel.setForeground(MainFrame.TextColor);
		mainPanel.add(passwordLabel);

		JPanel passwordPanel = new JPanel(new BorderLayout());
		passwordPanel.setBackground(MainFrame.BackgroundColor);

		passwordField = new JPasswordField();
		passwordField.setBackground(MainFrame.BorderColor);
		passwordField.setForeground(MainFrame.BackgroundColor);
		passwordField.setHorizontalAlignment(JPasswordField.CENTER);
		passwordPanel.add(passwordField, BorderLayout.CENTER);

		seePasswordButton = new JButton("👁️");
		seePasswordButton.setBackground(MainFrame.BackgroundColor);
		seePasswordButton.setForeground(MainFrame.TextColor);
		seePasswordButton.setFocusPainted(false);
		seePasswordButton.setBorder(BorderFactory.createEmptyBorder());

		char defaultEcho = passwordField.getEchoChar();

		seePasswordButton.addActionListener(e -> {
			if (seePasswordButton.getText().equals("👁️")) {
				passwordField.setEchoChar((char) 0);
				seePasswordButton.setText("🙈");
			} else {
				passwordField.setEchoChar(defaultEcho);
				seePasswordButton.setText("👁️");
			}
		});

		passwordPanel.add(seePasswordButton, BorderLayout.EAST);
		mainPanel.add(passwordPanel);

		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
		buttonsPanel.setBackground(MainFrame.BackgroundColor);

		registerButton = new JButton("Sign up");
		loginButton = new JButton("Login");

		registerButton.setBackground(MainFrame.BorderColor);
		registerButton.setFocusPainted(false);
		loginButton.setBackground(MainFrame.BorderColor);
		loginButton.setFocusPainted(false);

		Utils.generateUsers();
		loginButton.addActionListener(e -> handleLogin());

		registerButton.addActionListener(e -> handleRegister());

		buttonsPanel.add(registerButton);
		buttonsPanel.add(loginButton);
		mainPanel.add(buttonsPanel);

		add(mainPanel);
	}

	private void handleLogin() {
		found = false;
		String email = emailField.getText();
		String password = new String(passwordField.getPassword());

		if (managedb.isEmailInDB(email)) {
			found = true;
			main = MainFrame.getInstance();
			main.setCurrentUser(managedb.getUserFromEmail(email));
			main.getCardPanel().add(UserPanel.PanelUsuario(), "AccountPanel");
			main.setVisible(true);
			dispose();
		}

		//ANTES DE IMPLEMENTAR DB:
		for (User user : Utils.users) {
			if (user.getMail().equals(email) && user.getPassword().equals(password)) {
				found = true;
				main = MainFrame.getInstance();
				main.setCurrentUser(user);
				main.getCardPanel().add(UserPanel.PanelUsuario(), "AccountPanel");
				main.setVisible(true);
				dispose();
				break;
			}
		}

		if (!found) {
			JOptionPane.showMessageDialog(this, "Email and password don't match", "Login failed", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void handleRegister() {
		String email = emailField.getText();
		String password = new String(passwordField.getPassword());
		
		if (managedb.isEmailInDB(email)) {
			JOptionPane.showMessageDialog(null, "That email already has an account", "ERROR", JOptionPane.WARNING_MESSAGE);
		} else {
			boolean newUsername = false;
			String username = "";
			while(!newUsername ) {
				username = JOptionPane.showInputDialog(this, "Username for your new account: ");
				if (!managedb.isUsernameInDB(username)) {
					newUsername = true;
				} else {
					JOptionPane.showMessageDialog(null, "Username already exists", "ERROR", JOptionPane.WARNING_MESSAGE);
				}
			}
			managedb.insertUser(new User(username, email, password));
			
			
		}
		
	}
}
