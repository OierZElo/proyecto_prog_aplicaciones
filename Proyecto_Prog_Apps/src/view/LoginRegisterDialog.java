package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controller.ManageDB;

public class LoginRegisterDialog extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton, seePasswordButton;
    private ManageDB managedb;
    private MainFrame main;

    public LoginRegisterDialog() {
        managedb = ManageDB.getInstance();
        managedb.crearBBDD();
        ConfigManager.loadProperties();

        // Configuración de la Ventana
        setTitle("Acceso al Sistema");
        setSize(350, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel Principal con BoxLayout para control vertical total
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(MainFrame.BackgroundColor);
        mainPanel.setBorder(new EmptyBorder(30, 45, 30, 45));

        // --- SECCIÓN ENCABEZADO ---
        JLabel titleLabel = new JLabel("LOGIN");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(MainFrame.TextColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- SECCIÓN EMAIL ---
        JLabel emailLabel = new JLabel("Correo Electrónico");
        emailLabel.setForeground(MainFrame.TextColor);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(emailLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        emailField = new JTextField("Enter your email...");
        styleTextField(emailField);
        // Efecto Placeholder
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

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // --- SECCIÓN PASSWORD ---
        JLabel passwordLabel = new JLabel("Contraseña");
        passwordLabel.setForeground(MainFrame.TextColor);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(passwordLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Panel contenedor para el campo de password + botón de ojo
        JPanel passWrapper = new JPanel(new BorderLayout());
        passWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passWrapper.setBackground(MainFrame.BorderColor);
        passWrapper.setBorder(BorderFactory.createLineBorder(MainFrame.BorderColor));

        passwordField = new JPasswordField();
        passwordField.setBackground(MainFrame.BorderColor);
        passwordField.setForeground(MainFrame.BackgroundColor);
        passwordField.setBorder(new EmptyBorder(0, 10, 0, 0));
        passwordField.setCaretColor(MainFrame.BackgroundColor);

        seePasswordButton = new JButton("👁️");
        seePasswordButton.setPreferredSize(new Dimension(40, 35));
        seePasswordButton.setFocusPainted(false);
        seePasswordButton.setBorderPainted(false);
        seePasswordButton.setContentAreaFilled(false);
        seePasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        char defaultEcho = passwordField.getEchoChar();
        seePasswordButton.addActionListener(e -> {
            if (passwordField.getEchoChar() != (char) 0) {
                passwordField.setEchoChar((char) 0);
                seePasswordButton.setText("🙈");
            } else {
                passwordField.setEchoChar(defaultEcho);
                seePasswordButton.setText("👁️");
            }
        });

        passWrapper.add(passwordField, BorderLayout.CENTER);
        passWrapper.add(seePasswordButton, BorderLayout.EAST);
        mainPanel.add(passWrapper);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 35)));

        // --- SECCIÓN BOTONES ---
        loginButton = new JButton("Iniciar Sesión");
        styleMainButton(loginButton, true);
        loginButton.addActionListener(e -> handleLogin());
        mainPanel.add(loginButton);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        registerButton = new JButton("Crear nueva cuenta");
        styleMainButton(registerButton, false);
        registerButton.addActionListener(e -> {
            // Abrimos la nueva ventana de registro
            RegisterFrame regFrame = new RegisterFrame();
            regFrame.setVisible(true);
        });
        mainPanel.add(registerButton);

        add(mainPanel);
    }

    private void styleTextField(JTextField field) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setBackground(MainFrame.BorderColor);
        field.setForeground(MainFrame.BackgroundColor);
        field.setCaretColor(MainFrame.BackgroundColor);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MainFrame.BorderColor, 1),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
    }

    private void styleMainButton(JButton btn, boolean isPrimary) {
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        
        if (isPrimary) {
            btn.setBackground(MainFrame.TextColor);
            btn.setForeground(MainFrame.BackgroundColor);
        } else {
            btn.setBackground(MainFrame.BackgroundColor);
            btn.setForeground(MainFrame.TextColor);
            btn.setBorder(BorderFactory.createLineBorder(MainFrame.TextColor, 1));
        }
    }

    private void handleLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        boolean loginSuccess = false;

        if (managedb.isEmailInDB(email)) {
            if(managedb.getPasswordFromEmail(email).equals(password)) {
                loginSuccess = true;
                main = MainFrame.getInstance();
                main.setCurrentUser(managedb.getUserFromEmail(email));
                main.getCardPanel().add(UserPanel.PanelUsuario(), "AccountPanel");
                main.setVisible(true);
                dispose();
            }
        }

        if (!loginSuccess) {
            JOptionPane.showMessageDialog(this, 
                "Los datos introducidos no son correctos.", 
                "Error de Autenticación", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}