package app;
import javax.swing.SwingUtilities;
import view.MainFrame;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			MainFrame mainFrame = new MainFrame();
			mainFrame.setVisible(true);
			System.out.println("test");
			
		});
		


	}

}
