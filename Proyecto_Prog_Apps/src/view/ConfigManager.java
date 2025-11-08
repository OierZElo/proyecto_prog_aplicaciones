package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ConfigManager extends JFrame {
    private static final String CONFIG_PATH = "src/resources/config.properties";
    private static Properties props = new Properties();
    
    public static JPanel ColorPanel() {
    	JPanel mainPanel = new JPanel(new GridLayout(3, 2));
    	JPanel underPanel = new JPanel(new GridLayout(4, 1));
    	JPanel savePanelButton = new JPanel(new GridLayout(3, 1));
        mainPanel.setBackground(MainFrame.BackgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        savePanelButton.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        underPanel.setBackground(MainFrame.BackgroundColor);
        savePanelButton.setBackground(MainFrame.BackgroundColor);
        
        JLabel backgroundLabel = new JLabel("Background color:");
        JLabel textLabel = new JLabel("Text color:");
        JLabel borderLabel = new JLabel("Border color:");
		backgroundLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		textLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		borderLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));

        backgroundLabel.setForeground(MainFrame.TextColor);
        textLabel.setForeground(MainFrame.TextColor);
        borderLabel.setForeground(MainFrame.TextColor);

        JButton backgroundBtn = new JButton("Select...");
        backgroundBtn.setBackground(MainFrame.BorderColor);
        backgroundBtn.setFocusPainted(false);
        
        JButton textBtn = new JButton("Select...");
        textBtn.setBackground(MainFrame.BorderColor);
        textBtn.setFocusPainted(false);

        JButton borderBtn = new JButton("Select...");
        borderBtn.setBackground(MainFrame.BorderColor);
        borderBtn.setFocusPainted(false);

        JPanel backgroundPreview = new JPanel();
        backgroundPreview.setBackground(MainFrame.BackgroundColor);
        JPanel textPreview = new JPanel();
        textPreview.setBackground(MainFrame.TextColor);
        JPanel borderPreview = new JPanel();
        borderPreview.setBackground(MainFrame.BorderColor);

        backgroundBtn.addActionListener(e -> {
            Color c = JColorChooser.showDialog(null, "Select background color", MainFrame.BackgroundColor);
            if (c != null) {
                MainFrame.BackgroundColor = c;
                backgroundPreview.setBackground(c);
                MainFrame.getInstance().repaint();
            }
        });

        textBtn.addActionListener(e -> {
            Color c = JColorChooser.showDialog(null, "Select text color", MainFrame.TextColor);
            if (c != null) {
                MainFrame.TextColor = c;
                textPreview.setBackground(c);
                MainFrame.getInstance().repaint();
            }
        });

        borderBtn.addActionListener(e -> {
            Color c = JColorChooser.showDialog(null, "Select border color", MainFrame.BorderColor);
            if (c != null) {
                MainFrame.BorderColor = c;
                borderPreview.setBackground(c);
                MainFrame.getInstance().repaint();
            }
        });
        
        JButton saveBtn = new JButton("Save settings");
        saveBtn.setBackground(MainFrame.BorderColor);
        saveBtn.setFocusPainted(false);
        saveBtn.addActionListener(e -> {
            ConfigManager.saveColors();
            JOptionPane.showMessageDialog(null, "Saved, restart the app");
        });

        mainPanel.add(backgroundLabel);
        mainPanel.add(backgroundBtn);
        mainPanel.add(backgroundPreview);
        mainPanel.add(textLabel);
        mainPanel.add(textBtn);
        mainPanel.add(textPreview);
        mainPanel.add(borderLabel);
        mainPanel.add(borderBtn);
        mainPanel.add(borderPreview);
        underPanel.add(mainPanel);
        savePanelButton.add(saveBtn);
        underPanel.add(savePanelButton);
    	
    	return underPanel;
    }

    public static void loadColors() {
        try (FileInputStream f = new FileInputStream(CONFIG_PATH)) {
            props.load(f);

            MainFrame.BackgroundColor = Color.decode(props.getProperty("background", "#000000"));
            MainFrame.BorderColor = Color.decode(props.getProperty("border", "#808080"));
            MainFrame.TextColor = Color.decode(props.getProperty("text", "#FFFFFF"));

        } catch (IOException e) {
            System.out.println("Error, loading defaults");
        }
    }

    public static void saveColors() {
        props.setProperty("background", colorToHex(MainFrame.BackgroundColor));
        props.setProperty("border", colorToHex(MainFrame.BorderColor));
        props.setProperty("text", colorToHex(MainFrame.TextColor));

        try (FileOutputStream f = new FileOutputStream(CONFIG_PATH)) {
            props.store(f, "Theme colors configuration");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    private static String colorToHex(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }
}