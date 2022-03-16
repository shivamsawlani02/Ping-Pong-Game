import java.awt.*;
import javax.swing.*;

public class PingPongLayout extends JFrame{
	GamePanel panel;
	PingPongLayout(){
		panel = new GamePanel();
		this.add(panel);
		this.setTitle("Ping-Pong Game");
		this.setResizable(false);
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}