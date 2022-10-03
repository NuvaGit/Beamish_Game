import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 1300;
	static final int SCREEN_HEIGHT = 750;
	static final int GAME_UNIT = 25; // 25 doesnt print square 5- does though
	static final int GAME_UNIT1 = (SCREEN_WIDTH*SCREEN_HEIGHT)/(GAME_UNIT*GAME_UNIT);
	static final int DELAY = 175;
	final int x[] = new int[GAME_UNIT];
	final int y[] = new int[GAME_UNIT];
	int bodyParts = 6;
	int BeamishDowned;
	int beamishX;
	int beamishY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	public void startGame() {
		newBeamish();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		
		if(running) {
			
			for(int i=0;i<SCREEN_HEIGHT/GAME_UNIT;i++) {
				g.drawLine(i*GAME_UNIT, 0, i*GAME_UNIT, SCREEN_HEIGHT);
				g.drawLine(0, i*GAME_UNIT, SCREEN_WIDTH, i*GAME_UNIT);
			}
			
			g.setColor(Color.orange);
            g.fillRect(beamishX, beamishY, GAME_UNIT, GAME_UNIT*2);
			for(int i = 0; i< bodyParts;i++) {
				if(i == 0) {
					g.setColor(Color.white);
					g.fillRect(x[i], y[i], GAME_UNIT, GAME_UNIT);
				}
				else {
					g.setColor(new Color(45,180,0));
					//g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(x[i], y[i], GAME_UNIT, GAME_UNIT);
				}			
			}
			g.setColor(Color.blue); //scoreboard 
			g.setFont( new Font("Ink Free",Font.BOLD, 44));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Beamish: "+BeamishDowned, (SCREEN_WIDTH - metrics.stringWidth("Beamish: "+BeamishDowned))/2, g.getFont().getSize());
		}
		else {
			gameOver(g);
		}
		
	}
	public void newBeamish(){
		beamishX = random.nextInt((int)(SCREEN_WIDTH/GAME_UNIT))*GAME_UNIT;
		beamishY = random.nextInt((int)(SCREEN_HEIGHT/GAME_UNIT))*GAME_UNIT;
	}
	public void move(){
		for(int i = bodyParts;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - GAME_UNIT;
			break;
		case 'D':
			y[0] = y[0] + GAME_UNIT;
			break;
		case 'L':
			x[0] = x[0] - GAME_UNIT;
			break;
		case 'R':
			x[0] = x[0] + GAME_UNIT;
			break;
		}
		
	}
	public void checkBeamish() {
		if((x[0] == beamishX) && (y[0] == beamishY)) {
			bodyParts++;
			BeamishDowned++;
			newBeamish();
		}
	}
	public void checkCollisions() {
		//checks if head collides with body
		for(int i = bodyParts;i>0;i--) {
			if((x[0] == x[i])&& (y[0] == y[i])) {
				running = false;
			}
		}
		if(x[0] < 0) {
			running = false;
		}
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		if(y[0] < 0) {
			running = false;
		}
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	public void gameOver(Graphics g) {
		//Score
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Beamish Dowened: "+BeamishDowned, (SCREEN_WIDTH - metrics1.stringWidth("Beamhish: "+BeamishDowned))/2, g.getFont().getSize());
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkBeamish();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
}
