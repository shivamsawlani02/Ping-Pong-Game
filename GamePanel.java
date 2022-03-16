import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class PingPongShow extends JPanel implements Runnable{

	static final int PINGPONGW = 1000;
	static final int PINGPONGH = (int)(PINGPONGW * (0.5555));
	static final Dimension SCREEN_SIZE = new Dimension(PINGPONGW,PINGPONGH);
	static final int BALL_DIAMETER = 15;
	static final int PADDLE_WIDTH = 20;
	static final int PADDLE_HEIGHT = 80;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;
	
	PingPongShow(){
		newPaddles();
		newBall();
		score = new Score(PINGPONGW,PINGPONGH);
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void newBall() {
		random = new Random();
		ball = new Ball((PINGPONGW/2)-(BALL_DIAMETER/2),random.nextInt(PINGPONGH-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
	}
	public void newPaddles() {
		paddle1 = new Paddle(0,(PINGPONGH/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
		paddle2 = new Paddle(PINGPONGW-PADDLE_WIDTH,(PINGPONGH/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
	}
	public void paint(Graphics g) {
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
	}
	public void draw(Graphics g) {
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
Toolkit.getDefaultToolkit().sync();
	}
	public void move() {
		paddle1.move();
		paddle2.move();
		ball.move();
	}
	public void checkCollision() {
		if(ball.y <=0) {
			ball.setYDirection(-ball.y_vel);
		}
		if(ball.y >= PINGPONGH-BALL_DIAMETER) {
			ball.setYDirection(-ball.y_vel);
		}
		if(ball.intersects(paddle1)) {
			ball.x_vel = Math.abs(ball.x_vel);
			ball.x_vel++;
			if(ball.y_vel>0)
				ball.y_vel++;
			else
				ball.y_vel--;
			ball.setXDirection(ball.x_vel);
			ball.setYDirection(ball.y_vel);
		}
		if(ball.intersects(paddle2)) {
			ball.x_vel = Math.abs(ball.x_vel);
			ball.x_vel++;
			if(ball.y_vel>0)
				ball.y_vel++;
			else
				ball.y_vel--;
			ball.setXDirection(-ball.x_vel);
			ball.setYDirection(ball.y_vel);
		}
		if(paddle1.y<=0)
			paddle1.y=0;
		if(paddle1.y >= (PINGPONGH-PADDLE_HEIGHT))
			paddle1.y = PINGPONGH-PADDLE_HEIGHT;
		if(paddle2.y<=0)
			paddle2.y=0;
		if(paddle2.y >= (PINGPONGH-PADDLE_HEIGHT))
			paddle2.y = PINGPONGH-PADDLE_HEIGHT;
		if(ball.x <=0) {
			score.player2++;
			newPaddles();
			newBall();
		}
		if(ball.x >= PINGPONGW-BALL_DIAMETER) {
			score.player1++;
			newPaddles();
			newBall();
		}
	}
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now -lastTime)/ns;
			lastTime = now;
			if(delta >=1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}
	}
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);
		}
	}
}