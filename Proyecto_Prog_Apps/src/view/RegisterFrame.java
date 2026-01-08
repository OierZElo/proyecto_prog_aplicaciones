package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.User;
import controller.ManageDB;

public class RegisterFrame extends JFrame {

    private JTextField emailField, userField;
    private JPasswordField passField;
    private JButton registerBtn, cancelBtn;
    private ManageDB managedb;

    public RegisterFrame() {
        managedb = ManageDB.getInstance();
        setTitle("Crear Nueva Cuenta");
        setSize(350, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));
        panel.setBackground(MainFrame.BackgroundColor);

        // Titulo
        JLabel title = new JLabel("REGISTRO");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(MainFrame.TextColor);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Campos
        emailField = createStyledField("Email", panel);
        userField = createStyledField("Username", panel);
        
        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(MainFrame.TextColor);
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(passLabel);
        
        passField = new JPasswordField();
        styleComponent(passField);
        panel.add(passField);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Botones
        registerBtn = new JButton("Confirmar Registro");
        styleButton(registerBtn, true);
        registerBtn.addActionListener(e -> executeRegister());

        cancelBtn = new JButton("Cancelar");
        styleButton(cancelBtn, false);
        cancelBtn.addActionListener(e -> dispose());

        panel.add(registerBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(cancelBtn);

        add(panel);
    }

    private JTextField createStyledField(String labelText, JPanel parent) {
        JLabel lbl = new JLabel(labelText);
        lbl.setForeground(MainFrame.TextColor);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        parent.add(lbl);
        
        JTextField field = new JTextField();
        styleComponent(field);
        parent.add(field);
        parent.add(Box.createRigidArea(new Dimension(0, 15)));
        return field;
    }

    private void styleComponent(JComponent comp) {
        comp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        comp.setBackground(MainFrame.BorderColor);
        comp.setForeground(MainFrame.BackgroundColor);
        comp.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MainFrame.BorderColor),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)));
    }

    private void styleButton(JButton btn, boolean primary) {
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBackground(primary ? MainFrame.TextColor : new Color(100, 100, 100));
        btn.setForeground(MainFrame.BackgroundColor);
    }

    private void executeRegister() {
        String email = emailField.getText();
        String user = userField.getText();
        String pass = new String(passField.getPassword());

        if (email.isEmpty() || user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, rellena todos los campos");
            return;
        }

        if (managedb.isEmailInDB(email)) {
            JOptionPane.showMessageDialog(this, "El email ya está registrado");
        } else if (managedb.isUsernameInDB(user)) {
            JOptionPane.showMessageDialog(this, "El nombre de usuario ya existe");
        } else {
            managedb.insertUser(new User(email, pass, user));
            JOptionPane.showMessageDialog(this, "¡Cuenta creada con éxito!");
            dispose();
        }
    }
}