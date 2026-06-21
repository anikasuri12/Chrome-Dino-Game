import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    
    // Dino settings
    int dinoX = 50;
    int dinoY = 185; 
    int dinoVelocityY = 0; 
    int gravity = 1; 

    // Cactus settings
    int cactusX = 800; 
    int cactusY = 195;
    int cactusWidth = 30; 
    int cactusHeight = 45;
    
    // Speed settings (Is baar speed jaldi-jaldi badhegi)
    int baseSpeed = 6;     
    int currentSpeed = 6;  

    Image dinoImg;
    Image cactusImg;

    int score = 0;
    boolean gameOver = false;
    Timer gameLoop; 
    Random random = new Random(); // Random positions ke liye

    public GamePanel() {
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.addKeyListener(this);

        dinoImg = new ImageIcon("dino.png").getImage();
        cactusImg = new ImageIcon("cactus.png").getImage();

        gameLoop = new Timer(20, this);
        gameLoop.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            // 🔥 UPDATE 1: SPEED LOGIC (Har 40 score par speed 2 point badhegi)
            // Score 40 hote hi speed 6 se 8 ho jayegi, score 80 par 10 ho jayegi!
            currentSpeed = baseSpeed + ((score / 40) * 2); 

            // Dino Gravity
            dinoY += dinoVelocityY;
            if (dinoY < 185) { 
                dinoVelocityY += gravity; 
            } else { 
                dinoY = 185;
                dinoVelocityY = 0;
            }

            // Cactus ko aage badhana
            cactusX -= currentSpeed; 

            // 🔥 UPDATE 2: RANDOM CACTUS SPAWNING
            // Jab cactus screen se baahar nikal jaye
            if (cactusX < -50) { 
                // Ab naya cactus bilkul random distance par aayega (750 se 1150 ke beech)
                // Isse do cactus ke beech ka gap har baar alag hoga aur game unpredictable banega
                cactusX = 750 + random.nextInt(400); 
                
                // Randomly cactus ka size thoda bada-chota kar dete hain
                cactusWidth = 25 + random.nextInt(25); // Width 25 se 50 ke beech kuch bhi hogi
                
                score += 10; 
            }

            // Collision Check 
            if (dinoX < cactusX + cactusWidth - 5 &&
                dinoX + 35 > cactusX &&
                dinoY < cactusY + cactusHeight &&
                dinoY + 35 > cactusY) {
                
                gameOver = true;
                gameLoop.stop(); 
            }
        }
        repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Ground
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(0, 230, 700, 230);

        // Draw Dino
        g.drawImage(dinoImg, dinoX, dinoY, 40, 40, null);

        // Draw Cactus (Ab iski width random badal rahi hai)
        g.drawImage(cactusImg, cactusX, cactusY, cactusWidth, cactusHeight, null);

        // Score Board
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, 580, 30);
        g.drawString("Speed: " + currentSpeed, 580, 50); // Teri tasalli ke liye speed screen par print kar di!

        if (gameOver) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("GAME OVER", 260, 110); 
            g.setFont(new Font("Arial", Font.PLAIN, 15));
            g.drawString("Press 'R' to Restart", 285, 150);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && dinoY == 185 && !gameOver) {
            dinoVelocityY = -12; 
        }
        
        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            dinoY = 185;
            cactusX = 800;
            cactusWidth = 30;
            currentSpeed = 6;
            score = 0;
            gameOver = false;
            gameLoop.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}