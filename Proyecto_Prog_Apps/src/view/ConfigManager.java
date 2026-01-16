package view;

import java.awt.BorderLayout;
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
import javax.swing.SwingConstants;
import controller.ManageDB;

public class ConfigManager extends JFrame {
    private static final String CONFIG_PATH = "src/resources/config.properties";
    private static Properties props = new Properties();
    static ManageDB managedb = ManageDB.getInstance();

    //temas generados por IA
    private enum Theme {
        DARK_CLASSIC("Dark Classic", Color.BLACK, Color.WHITE, Color.GRAY),
        LIGHT_MODE("Light Mode", new Color(240, 240, 240), Color.BLACK, new Color(200, 200, 200)),
        OCEAN_BLUE("Ocean Blue", new Color(10, 25, 47), new Color(100, 255, 218), new Color(23, 42, 69)),
        FOREST("Deep Forest", new Color(20, 30, 20), new Color(200, 250, 200), new Color(50, 100, 50)),
        RETRO_WAVE("Retro Wave", new Color(45, 12, 45), new Color(0, 255, 255), new Color(255, 0, 128)),
        HIGH_CONTRAST("High Contrast", Color.WHITE, Color.BLUE, Color.BLACK);

        final String name;
        final Color bg;
        final Color text;
        final Color border;

        Theme(String name, Color bg, Color text, Color border) {
            this.name = name;
            this.bg = bg;
            this.text = text;
            this.border = border;
        }
    }

    public static JPanel ColorPanel() {
        JPanel underPanel = new JPanel(new GridLayout(4, 1));
        underPanel.setBackground(MainFrame.BackgroundColor);
        JPanel mainPanel = new JPanel(new GridLayout(3, 2));
        mainPanel.setBackground(MainFrame.BackgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addBackgroundSection(mainPanel);
        addTextSection(mainPanel);
        addBorderSection(mainPanel);

        JPanel savePanelButton = createSaveSection();
        JPanel presetPanel = createPresetsSection();

        underPanel.add(mainPanel);
        underPanel.add(savePanelButton);
        underPanel.add(presetPanel);
        
        return underPanel;
    }

    private static void addBackgroundSection(JPanel parent) {
        JLabel label = createStyledLabel("Background color:");
        JPanel preview = createPreviewPanel(MainFrame.BackgroundColor);
        JButton btn = createStyledButton("Select...");

        btn.addActionListener(e -> {
            Color c = chooseColor("Select background color", MainFrame.BackgroundColor);
            if (c != null) {
                MainFrame.BackgroundColor = c;
                preview.setBackground(c);
                MainFrame.getInstance().repaint();
            }
        });

        parent.add(label);
        parent.add(btn);
        parent.add(preview);
    }

    private static void addTextSection(JPanel parent) {
        JLabel label = createStyledLabel("Text color:");
        JPanel preview = createPreviewPanel(MainFrame.TextColor);
        JButton btn = createStyledButton("Select...");

        btn.addActionListener(e -> {
            Color c = chooseColor("Select text color", MainFrame.TextColor);
            if (c != null) {
                MainFrame.TextColor = c;
                preview.setBackground(c);
                MainFrame.getInstance().repaint();
            }
        });

        parent.add(label);
        parent.add(btn);
        parent.add(preview);
    }

    private static void addBorderSection(JPanel parent) {
        JLabel label = createStyledLabel("Border color:");
        JPanel preview = createPreviewPanel(MainFrame.BorderColor);
        JButton btn = createStyledButton("Select...");

        btn.addActionListener(e -> {
            Color c = chooseColor("Select border color", MainFrame.BorderColor);
            if (c != null) {
                MainFrame.BorderColor = c;
                preview.setBackground(c);
                MainFrame.getInstance().repaint();
            }
        });

        parent.add(label);
        parent.add(btn);
        parent.add(preview);
    }

    private static JPanel createSaveSection() {
        JPanel savePanel = new JPanel(new GridLayout(3, 1));
        savePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        savePanel.setBackground(MainFrame.BackgroundColor);

        JButton saveBtn = createStyledButton("Save settings");
        saveBtn.addActionListener(e -> {
            ConfigManager.saveColors();
            JOptionPane.showMessageDialog(null, "Saved, restart the app");
        });

        savePanel.add(saveBtn);
        return savePanel;
    }

    private static JPanel createPresetsSection() {
        JPanel presetsPanel = new JPanel(new BorderLayout());
        presetsPanel.setBackground(MainFrame.BackgroundColor);
        
        JLabel title = new JLabel("QUICK PRESETS");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(MainFrame.TextColor);
        presetsPanel.add(title, BorderLayout.NORTH);

        JPanel buttonsGrid = new JPanel(new GridLayout(0, 2, 10, 10));
        buttonsGrid.setBackground(MainFrame.BackgroundColor);
        buttonsGrid.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        for (Theme theme : Theme.values()) {
            JButton themeBtn = new JButton(theme.name.toUpperCase());
            themeBtn.setFont(new Font("Arial", Font.BOLD, 16));
            themeBtn.setOpaque(true);
            themeBtn.setBackground(theme.bg);
            themeBtn.setForeground(theme.text);
            themeBtn.setHorizontalTextPosition(SwingConstants.CENTER);
            
            themeBtn.addActionListener(e -> applyTheme(theme));
            buttonsGrid.add(themeBtn);
        }

        presetsPanel.add(buttonsGrid, BorderLayout.CENTER);
        return presetsPanel;
    }

    private static JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
        label.setForeground(MainFrame.TextColor);
        return label;
    }

    private static JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(MainFrame.BorderColor);
        btn.setFocusPainted(false);
        return btn;
    }

    private static JPanel createPreviewPanel(Color c) {
        JPanel panel = new JPanel();
        panel.setBackground(c);
        return panel;
    }

    private static Color chooseColor(String title, Color current) {
        return JColorChooser.showDialog(null, title, current);
    }
    
    private static void applyTheme(Theme t) {
        MainFrame.BackgroundColor = t.bg;
        MainFrame.TextColor = t.text;
        MainFrame.BorderColor = t.border;
        saveColors(); 
        MainFrame.getInstance().repaint();
        MainFrame.getInstance().getContentPane().repaint();
        JOptionPane.showMessageDialog(null, "Theme '" + t.name + "' applied!");
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
    
    public static void loadProperties() {
        try (FileInputStream f = new FileInputStream(CONFIG_PATH)) {
            props.load(f);
            loadColors();
            
            if (ConfigManager.shouldLoadSongs()) {
                managedb.loadSongsFromCSV("src/resources/db/songs.csv");
            }
            if (ConfigManager.shouldLoadUsers()) {
                managedb.loadUsersFromCSV("src/resources/db/users.csv");
            }
            if (ConfigManager.shouldLoadPlaylists()) {
                managedb.loadPlaylistsFromCSV("src/resources/db/playlists.csv");
            }
            if (ConfigManager.shouldLoadPlaylistSongs()) {
                managedb.loadPlaylistSongsFromCSV("src/resources/db/playlist_songs.csv");
            }
            
        } catch (IOException e) {
            System.out.println("Error loading config.properties, using defaults");
        }
    }
    
    public static boolean shouldLoadUsers() {
        return Boolean.parseBoolean(props.getProperty("load.users", "false"));
    }

    public static boolean shouldLoadSongs() {
        return Boolean.parseBoolean(props.getProperty("load.songs", "false"));
    }

    public static boolean shouldLoadPlaylists() {
        return Boolean.parseBoolean(props.getProperty("load.playlists", "false"));
    }

    public static boolean shouldLoadPlaylistSongs() {
        return Boolean.parseBoolean(props.getProperty("load.playlist_songs", "false"));
    }

    public static void setLoadUsers(boolean value) {
        props.setProperty("load.users", Boolean.toString(value));
    }

    public static void setLoadSongs(boolean value) {
        props.setProperty("load.songs", Boolean.toString(value));
    }

    public static void setLoadPlaylists(boolean value) {
        props.setProperty("load.playlists", Boolean.toString(value));
    }

    public static void setLoadPlaylistSongs(boolean value) {
        props.setProperty("load.playlist_songs", Boolean.toString(value));
    }

    public static void saveLoadProperties() {
        try (FileOutputStream f = new FileOutputStream(CONFIG_PATH)) {
            props.store(f, "Configuración general");
        } catch (IOException e) {
            System.out.println("Error saving load properties: " + e.getMessage());
        }
    }
}