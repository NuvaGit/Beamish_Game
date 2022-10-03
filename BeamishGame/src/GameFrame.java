import javax.swing.JFrame;

public class GameFrame extends JFrame {

	GameFrame() {
		GamePanel panel = new GamePanel();
		this.add(panel);

		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("SNAKE");
		this.pack();
		this.setVisible(true);
		this.setLocation(null);
		

	}
}