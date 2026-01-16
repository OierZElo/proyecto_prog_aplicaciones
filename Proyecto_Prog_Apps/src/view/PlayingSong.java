package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Song;

public class PlayingSong extends JPanel {

    private static final long serialVersionUID = 1L;

    // COMPONENTES (NO static)
    private JPanel southPanel;
    private JPanel centerPanel;

    private JLabel titleLabel;
    private JLabel bandLabel;
    private JLabel iconLabel;

    // CONSTRUCTOR → se crea UNA vez
    public PlayingSong() {
        setLayout(new BorderLayout());
        setBackground(MainFrame.BackgroundColor);

        // Panel inferior
        southPanel = new JPanel(new GridLayout(2, 1));
        southPanel.setBackground(MainFrame.BackgroundColor);

        titleLabel = new JLabel("", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 26));
        titleLabel.setForeground(MainFrame.TextColor);
        titleLabel.setBackground(MainFrame.BackgroundColor);
        titleLabel.setOpaque(true);

        bandLabel = new JLabel("", JLabel.CENTER);
        bandLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 18));
        bandLabel.setForeground(MainFrame.BorderColor);
        bandLabel.setBackground(MainFrame.BackgroundColor);
        bandLabel.setOpaque(true);

        southPanel.add(titleLabel);
        southPanel.add(bandLabel);

        // Panel central (icono)
        centerPanel = new JPanel();
        centerPanel.setBackground(MainFrame.BackgroundColor);

        iconLabel = new JLabel();
        centerPanel.add(iconLabel);

        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    // MÉTODO PARA ACTUALIZAR LA CANCIÓN (SE LLAMA AL CAMBIAR)
    public void updateSong(Song song) {
        if (song == null) return;

        titleLabel.setText(song.getTitle());
        bandLabel.setText(song.getBand());

        String path = "src/resources/icons/" + song.getCod() + ".png";
        File file = new File(path);
        if (!file.exists()) {
            path = "src/resources/icons/SongIcon.png";
        }

        iconLabel.setIcon(new ImageIcon(path));

        revalidate();
        repaint();
    }
}
