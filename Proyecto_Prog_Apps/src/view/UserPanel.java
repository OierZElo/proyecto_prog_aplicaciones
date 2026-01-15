package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import controller.UserController;
import model.User;
import utils.Utils;

public class UserPanel {


	public static JPanel PanelUsuario() {
		 UserController userControl = new UserController(ConfigManager.managedb); // creamos un controller para conectar la parte visual con la logica de la app
		MainFrame main = MainFrame.getInstance();
		

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
		 userdata.setPreferredSize(new Dimension(400, 220)); // ancho suficiente para WEST + CENTER

		 
		 // user's picture:
		 
		 String rutaFoto = main.currentUser.getPhotoString(main.currentUser);
		// Verificar que exista
	
			System.out.println("hemos encontrado la imagen "+ rutaFoto);
		    ImageIcon iconOriginal = new ImageIcon(rutaFoto);
		    Image iconEscalado = iconOriginal.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		    ImageIcon iconFinal = new ImageIcon(iconEscalado);
		    JLabel foto = new JLabel(iconFinal);
		   
		    foto.setPreferredSize(new Dimension(200, 200));
		    foto.setMinimumSize(new Dimension(200, 200));
		    foto.setMaximumSize(new Dimension(200, 200));

		    foto.setHorizontalAlignment(JLabel.CENTER);
		    foto.setVerticalAlignment(JLabel.CENTER);
		    foto.setOpaque(true);

		    userdata.add(foto, BorderLayout.WEST);
			 userdata.add(generarDatos(main.getCurrentUser()), BorderLayout.CENTER);
			 //user's data controlers's display:
			 userdata.add(botonesControl(result, main.getCurrentUser(), userControl, foto),BorderLayout.SOUTH);
		
		

// Windoe's center's display: 
		 JPanel lsl = new JPanel(); 
		 lsl.setLayout(new BorderLayout(10, 10));
		 lsl.setBackground(MainFrame.BackgroundColor);
// esto 100% hay que cambiarlo por otra cosa
		 JLabel title = new JLabel("RECOMMENDED SONGS"); 
		 title.setHorizontalAlignment(JLabel.CENTER);
		 title.setBackground(MainFrame.BackgroundColor);
		 title.setForeground(MainFrame.TextColor);
		 Font font = new Font("Arial", Font.BOLD, 24);
		 title.setFont(font);
		 lsl.add(title,BorderLayout.NORTH );
		 Utils.generateRandomPlaylist(Utils.playlist1);
		 System.out.println("Songs in playlist: " + Utils.playlist1.getL_songs().size());

		 JPanel recommended = songTable.createSongTablePlaylist(Utils.playlist1) ;
		 lsl.add(recommended, BorderLayout.CENTER);

// here we add the created JPanels into the result JPanel
		result.add(userdata);
		result.add(lsl);
		
		

		
		return result;
	} 
	
	private static  JPanel generarDatos(User usuario) {
		JPanel r = new JPanel(new GridLayout(3, 0, 0, 10)); 
		r.setBackground(MainFrame.BackgroundColor);
		JLabel mail = new JLabel("Mail : " + usuario.getMail()); 
		JLabel name = new  JLabel("Username: " + usuario.getName()); 
		
		String maskedPass = "Password: " + "*".repeat(usuario.getPassword().length());
		String clearPass = "Password: " + usuario.getPassword();
		
		JLabel password = new JLabel(maskedPass);
		
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
		
		JPanel passContainer = new JPanel(new BorderLayout());
		passContainer.setBackground(MainFrame.BorderColor);
		passContainer.add(password, BorderLayout.CENTER);
		
		JButton toggleView = new JButton("👁️");
		toggleView.setBackground(MainFrame.BorderColor);
		toggleView.setForeground(MainFrame.TextColor);
		toggleView.setFocusPainted(false);
		toggleView.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 20));
		toggleView.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 40));
		
		toggleView.addActionListener(e -> {
			if (toggleView.getText().equals("👁️")) {
				password.setText(clearPass);
				toggleView.setText("🙈");
			} else {
				password.setText(maskedPass);
				toggleView.setText("👁️");
			}
		});
		
		passContainer.add(toggleView, BorderLayout.EAST);

		r.add(mail);
		r.add(name);
		r.add(passContainer);
		
		return r; }
		
	private static  JPanel botonesControl(JPanel result, User usuario, UserController userControl, JLabel foto) {
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
		JDialog newpic = CambioDeFoto(result, "Introduce the url of the new picture: ", usuario, userControl,  foto); 
		JDialog newpassword = CambiarContrasena(result, "Introduce the new password", usuario, userControl);
		JDialog logOut = cerrarSesion(result, userControl);
		chp.addActionListener(e -> newpassword.setVisible(true));
		cp.addActionListener(e -> newpic.setVisible(true));
		lg.addActionListener(e -> logOut.setVisible(true));
	return r;
	}

	private static JDialog CambioDeFoto(JPanel result, String texto, User usuario, UserController userControl, JLabel foto) {
	    JDialog r = new JDialog();
	    r.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    r.setLayout(new BorderLayout());

	    // Paneles
	    JPanel datos = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	    JPanel control = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

	    // Campo de texto para mostrar ruta (solo lectura)
	    JTextField usersdata = new JTextField(20);
	    usersdata.setEditable(false);

	    JLabel t = new JLabel(texto);
	    JButton buscar = new JButton("Buscar...");
	    JButton aceptar = new JButton("ACCEPT");
	    JButton cancelar = new JButton("CANCEL");

	    datos.setBackground(MainFrame.BackgroundColor);
	    t.setBackground(MainFrame.BorderColor);
	    t.setOpaque(true);

	    datos.add(t);
	    datos.add(usersdata);
	    datos.add(buscar);
	    r.add(datos, BorderLayout.CENTER);

	    // JFileChooser para seleccionar solo imágenes
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif"));

	    // Acción del botón Buscar
	    buscar.addActionListener(e -> {
	        int opcion = fileChooser.showOpenDialog(r);
	        if (opcion == JFileChooser.APPROVE_OPTION) {
	            File archivo = fileChooser.getSelectedFile();
	            usersdata.setText(archivo.getAbsolutePath());
	        }
	    });

	    // Acción del botón Aceptar
	    aceptar.addActionListener(e -> {
	        String rutaSeleccionada = usersdata.getText();
	        if (rutaSeleccionada.isEmpty()) {
	            JOptionPane.showMessageDialog(r, "Debe seleccionar una imagen", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        File archivoSeleccionado = new File(rutaSeleccionada);

	        // Carpeta destino: "user_images" en la raíz del proyecto
	        File carpetaDestino = new File("user_images");
	        if (!carpetaDestino.exists()) {
	            carpetaDestino.mkdirs(); // crea la carpeta si no existe
	        }

	        // Obtener extensión del archivo original

	        // Crear nombre único basado en ID + email
	        String nombreOriginal = archivoSeleccionado.getName();
	        String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
	        String nuevoNombre =  usuario.getId() + "_" + usuario.getMail() +extension; 


	        File archivoDestino = new File(carpetaDestino, nuevoNombre);

	        try {
	            // Copiar archivo a carpeta destino
	            Files.copy(
	                archivoSeleccionado.toPath(),
	                archivoDestino.toPath(),
	                StandardCopyOption.REPLACE_EXISTING // reemplaza si ya existe
	            );

	            System.out.println("Imagen copiada a: " + archivoDestino.getAbsolutePath());

	            // Pasar la ruta al controlador (esta parte no esta bien implementada), no detecta el path para generar la imagen
	            boolean ok = userControl.changePhoto(usuario, archivoDestino.getPath());
	            foto.setIcon(usuario.getPhoto());  // actualzar el JLabel
	            foto.revalidate();
	            foto.repaint();

	            if (!ok) {
	                JOptionPane.showMessageDialog(r, "No se ha podido cambiar la foto", "Foto inválida", JOptionPane.ERROR_MESSAGE);
	            } else {

	                r.dispose();
	            }

	        } catch (IOException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(r, "No se pudo copiar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    });

	    // Cancelar
	    cancelar.addActionListener(e -> r.dispose());

	    // Panel de control
	    control.setBackground(MainFrame.BackgroundColor);
	    aceptar.setBackground(MainFrame.BorderColor);
	    control.add(aceptar);
	    control.add(cancelar);

	    r.add(control, BorderLayout.SOUTH);

	    // Configuración final del JDialog
	    r.setLocationRelativeTo(result);
	    r.setBackground(MainFrame.BackgroundColor);
	    r.pack();

	    return r;
	}

	
	private static  JDialog CambiarContrasena(JPanel result, String texto, User usuario, UserController userControl) {
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
		
		aceptar.addActionListener(e -> {
			// intentamos actualizar contraseña
			boolean ok = userControl.changePassword(usuario, newpass.getPassword(), newpass2.getPassword());
			// en caso de que no se haya podido, mostramos JOptionPane
			if (!ok) {
				int paneAns = JOptionPane.showConfirmDialog(r, "Las contraseñas no coinciden", "Contraseña(s) invalidas", JOptionPane.ERROR_MESSAGE);

			} else {r.dispose();} // si se ha podido cambiar la contraseña cerramos la ventana; 
		});

		cancelar.addActionListener(e -> r.dispose());
		
		buttonsPanel.add(aceptar);
		buttonsPanel.add(cancelar);
		mainPanel.add(buttonsPanel);
		
		r.add(mainPanel);
		return r;

	}

	private static  JDialog cerrarSesion(JPanel r,  UserController userControl) {
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
				userControl.logout(); 

			}
		});

		no.addActionListener(e -> result.dispose());
		return result;
	}
	
}