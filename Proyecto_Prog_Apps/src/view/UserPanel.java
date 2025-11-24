package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import app.Main;
import model.User;
import utils.Utils;

public class UserPanel {

	
	public static JPanel PanelUsuario() {
		MainFrame main = MainFrame.getInstance();

	// de momento usaremos este user de prueba para el display 
		
		
// JPanel container of all the UserPanel's window	
		JPanel result = new JPanel(); 
		result.setBorder(BorderFactory.createLineBorder(MainFrame.BackgroundColor, 10));

		result.setBackground(MainFrame.BackgroundColor);
		result.setLayout(new GridLayout(2,1,0, 10));
		 
		 
		 JPanel LastSongs = new JPanel(); 
		 LastSongs.setBackground(MainFrame.BackgroundColor);

// Window's north's settings: 
		 // users data display
		JPanel userdata = new JPanel();; 
		 userdata.setBackground(MainFrame.BackgroundColor);
		 userdata.setLayout(new BorderLayout(10, 10));
		 // user's picture: 
		 
		 Image scaledImage = main.getCurrentUser().getPhoto().getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		 ImageIcon fotoScala = new ImageIcon(scaledImage);
		 JLabel foto = new JLabel(fotoScala);
		 foto.setPreferredSize(new Dimension(200, 200));

		 userdata.add(foto, BorderLayout.WEST);
		 userdata.add(generarDatos(main.getCurrentUser()), BorderLayout.CENTER);
		 //user's data controlers's display:
		 userdata.add(botonesControl(result, main.getCurrentUser()),BorderLayout.SOUTH);

// Windoe's center's display: 
		 JPanel lsl = new JPanel(); 
		 lsl.setLayout(new BorderLayout(10, 10));
		 lsl.setBackground(MainFrame.BackgroundColor);

		 JLabel title = new JLabel("LAST 10 SONGS LISTENED"); 
		 title.setHorizontalAlignment(JLabel.CENTER);
		 title.setBackground(MainFrame.BackgroundColor);
		 title.setForeground(MainFrame.TextColor);
		 Font font = new Font("Arial", Font.BOLD, 24);
		 title.setFont(font);
		 lsl.add(title,BorderLayout.NORTH );
//		 Utils.generateRandomPlaylist(Utils.playlist1, main.getSongs());
		 JPanel lastsongs = songTable.createSongTablePlaylist(Utils.playlist1) ;
		 lsl.add(lastsongs, BorderLayout.CENTER);

// here we add the created JPanels into the result JPanel
		result.add(userdata);
		result.add(lsl);

		
		return result;
	} 
	
	private static JPanel generarDatos(User usuario) {
		JPanel r = new JPanel(new GridLayout(3, 0, 0, 10)); 
		r.setBackground(MainFrame.BackgroundColor);
		JLabel mail = new JLabel("Mail : " + usuario.getMail()); 
		JLabel name = new  JLabel("Username: " + usuario.getName()); 
		JLabel password = new JLabel("Password: " + usuario.getPassword());
		
		// labels color: 
		mail.setBackground(MainFrame.BorderColor);
		name.setBackground(MainFrame.BorderColor);
		password.setBackground(MainFrame.BorderColor);

		// text Alignment 
		mail.setHorizontalAlignment(JLabel.CENTER);
		name.setHorizontalAlignment(JLabel.CENTER);
		password.setHorizontalAlignment(JLabel.CENTER);

		mail.setOpaque(true);
		name.setOpaque(true);
		password.setOpaque(true);
		
		r.add(mail);
		r.add(name);
		r.add(password);
		
		return r; }
		
	private static JPanel botonesControl(JPanel result, User usuario) {
		JPanel r = new JPanel(); 
		r.setBackground(MainFrame.BackgroundColor);
		r.setLayout(new FlowLayout(FlowLayout.CENTER, 70, 10));
		JButton cp = new JButton("Change profile's picture"); 
		cp.setBackground(MainFrame.BorderColor);
		JButton lg = new JButton("Sign out"); 
		lg.setBackground(MainFrame.BorderColor);
		JButton chp = new JButton("Change password "); 
		chp.setBackground(MainFrame.BorderColor);
		r.add(cp); 
		r.add(lg);
		r.add(chp);
		// JDialog change picture
		JDialog newpic = CambioDeFoto(result, "Introduce the url of the new picture: ", usuario); 
		JDialog newpassword = CambiarContrasena(result, "Introduce the new password", usuario);
		JDialog logOut = cerrarSesion(result);
		chp.addActionListener(e -> newpassword.setVisible(true));
		cp.addActionListener(e -> newpic.setVisible(true));
		lg.addActionListener(e -> logOut.setVisible(true));
	return r;
	}
	
	
	private static JDialog CambioDeFoto(JPanel result, String texto, User usuario) {
		JDialog r = new JDialog(); 
		r.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		r.setLayout(new BorderLayout());
		// datos de entrada del user
		JPanel datos = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10) ); 
		datos.setBackground(MainFrame.BackgroundColor);
		JLabel t = new JLabel(texto);
		t.setBackground(MainFrame.BorderColor);
		t.setOpaque(true);
		datos.add(t); 
		JTextField usersdata = new JTextField(20); 
		datos.add(usersdata);
		r.add(datos, BorderLayout.CENTER);
		// botones de interacción 
		JPanel control = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10)); 
		control.setBackground(MainFrame.BackgroundColor);
		JButton aceptar = new JButton("ACCEPT"); 
	    JButton cancelar = new JButton("CANCEL"); 
	    aceptar.setBackground(MainFrame.BorderColor);
	    aceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
					ImageIcon foto = new ImageIcon(usersdata.getText()); 
					if (foto.getIconWidth() == -1) {
						JOptionPane.showMessageDialog(null, "invalid url", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
					else { usuario.setPhoto(foto); 
					r.dispose();
					};	
			}
		});
	    
	    cancelar.addActionListener( e -> r.dispose());
	    control.add(aceptar); 
	    control.add(cancelar); 
	    r.add(control, BorderLayout.SOUTH);
        r.setLocationRelativeTo(result);
        r.setBackground(MainFrame.BackgroundColor);
        r.pack();
		return r;
		
	}
	
	private static JDialog CambiarContrasena(JPanel result, String texto, User usuario) {
		JDialog r = new JDialog(); 
		r.setTitle("Reset Password");
		r.setSize(300,280);
		r.setLocationRelativeTo(result);
		r.setBackground(MainFrame.BackgroundColor);
		r.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		r.setLayout(new BorderLayout() );
		
		// main panel with GridagLayout
		JPanel mainPanel = new JPanel(new GridLayout(0,1,0,10)); 
		mainPanel.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));
		mainPanel.setBackground(MainFrame.BackgroundColor);
		
//		// title
//		JLabel t = new JLabel(texto, SwingConstants.CENTER);
//		t.setForeground(MainFrame.TextColor);
//		//t.setBackground(MainFrame.BackgroundColor);
//		mainPanel.add(t); 
		
		// field new password
		JLabel newPassLabel = new JLabel("New Password",SwingConstants.CENTER  ); 
		newPassLabel.setForeground(MainFrame.BackgroundColor);
		mainPanel.add(newPassLabel);
		
		JPasswordField newpass = new JPasswordField(); 
		newpass.setBackground(MainFrame.BorderColor);
		newpass.setForeground(MainFrame.BackgroundColor); 
		newpass.setHorizontalAlignment(JTextField.CENTER);
	    mainPanel.add(newpass);
	    
		// field confirm password + button 
		 JLabel confirmLabel = new JLabel("Confirm Password", SwingConstants.CENTER);
		 confirmLabel.setForeground(MainFrame.TextColor);
		 mainPanel.add(confirmLabel);
		
		 JPanel confirmPanel = new JPanel(new BorderLayout()); 
		 confirmPanel.setBackground(MainFrame.BackgroundColor);
		
		JPasswordField newpass2 = new JPasswordField(); 
		newpass2.setBackground(MainFrame.BorderColor);
		newpass2.setForeground(MainFrame.TextColor);
		newpass2.setHorizontalAlignment(JTextField.CENTER);
		confirmPanel.add(newpass2, BorderLayout.CENTER); 
		
		JButton viewPassword = new JButton("👁️"); 
		viewPassword.setBackground(MainFrame.BackgroundColor);
		viewPassword.setForeground(MainFrame.TextColor);
		viewPassword.setFocusPainted(false);
		viewPassword.setBorder(BorderFactory.createEmptyBorder());
		
	    char defaultEcho = newpass.getEchoChar();
	    
		viewPassword.addActionListener(e -> {
			if(viewPassword.getText().equals("👁️")) {
				viewPassword.setText("🙈");
				newpass.setEchoChar((char) 0);
				newpass2.setEchoChar((char) 0);

			} else {
				viewPassword.setText("👁️");
				newpass.setEchoChar(defaultEcho);
				newpass2.setEchoChar(defaultEcho);

			}
		});
		confirmPanel.add(viewPassword, BorderLayout.EAST);
		mainPanel.add(confirmPanel);

		// Buttons 
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		buttonsPanel.setBackground(MainFrame.BackgroundColor);
		
		JButton aceptar = new JButton("ACCEPT");
		JButton cancelar = new JButton("CANCEL");

		aceptar.setBackground(MainFrame.BorderColor);
	    cancelar.setBackground(MainFrame.BorderColor);
	    aceptar.setFocusPainted(false);
	    cancelar.setFocusPainted(false);
		
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (!java.util.Arrays.equals(newpass.getPassword(), newpass2.getPassword())) {
					JOptionPane.showMessageDialog(null, "The passwords don't match", "ERROR",
							JOptionPane.ERROR_MESSAGE);

				} else {
					usuario.setPassword(new String(newpass.getPassword()));
					newpass.setText("");
					newpass2.setText("");
					r.dispose();
				}

			}
		});

		cancelar.addActionListener(e -> r.dispose());
		
		buttonsPanel.add(aceptar);
		buttonsPanel.add(cancelar);
		mainPanel.add(buttonsPanel);
		
		r.add(mainPanel);
		return r;

	}

	private static JDialog cerrarSesion(JPanel r) {
		JDialog result = new JDialog();
		result.setSize(300, 100);
		result.setBackground(MainFrame.BackgroundColor);
		result.setLocationRelativeTo(r);
		result.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		result.setLayout(new BorderLayout());
		JLabel pregunta = new JLabel("Are you sure you want to log out?");
		pregunta.setHorizontalAlignment(JLabel.CENTER);
		pregunta.setBackground(MainFrame.BackgroundColor);
		pregunta.setOpaque(true);
		pregunta.setForeground(MainFrame.TextColor);
		pregunta.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));

		result.add(pregunta, BorderLayout.NORTH);
		
		// control buttons
		JButton yes = new JButton("YES");
		JButton no = new JButton("NO");
		yes.setBackground(MainFrame.BorderColor);
		no.setBackground(MainFrame.BorderColor);

		JPanel botones = new JPanel();
		botones.setBackground(MainFrame.BackgroundColor);
		botones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		botones.add(yes);
		botones.add(no);
		result.add(botones, BorderLayout.CENTER);

		yes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				result.dispose();
				MainFrame.getInstance().dispose();
				new LoginRegisterDialog().setVisible(true); //hacer singletone
			}
		});

		no.addActionListener(e -> result.dispose());
		return result;
	}
	
}
