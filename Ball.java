import java.awt.*;
import java.util.*;

public class Ball extends Rectangle{
	Random random;
	int x_vel;
	int y_vel;
	int initialSpeed = 2;
	
	Ball(int x, int y, int width, int height){
		super(x, y, width, height);
		random = new Random();
		int rand_X = random.nextInt(2);
		if(rand_X == 0)
			rand_X--;
		setXDirection(rand_X*initialSpeed);
		int rand_Y = random.nextInt(2);
		if(rand_Y == 0)
			rand_Y--;
		setYDirection(rand_Y*initialSpeed);
		
	}
	
	public void setXDirection(int rand_X) {
		x_vel = rand_X;
	}
	public void setYDirection(int rand_Y) {
		y_vel = rand_Y;
	}
	public void move() {
		x += x_vel;
		y += y_vel;
	}
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(x, y, height, width);
	}
}