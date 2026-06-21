import javax.swing.JFrame;

public class DinoApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chrome Dino Game");
        
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        
        // Game ka perfect size set kiya
        frame.setSize(700, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false); // Window ka size bada-chota nahi kar payenge
        frame.setVisible(true);
    }
}