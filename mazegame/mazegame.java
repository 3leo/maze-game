import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class mazegame extends JFrame implements KeyListener {
  private Image wallImg;
  private Image playerImg;
  private int mazeSize = 20;
  private int[][] mazeGrid = new int[mazeSize][mazeSize];
  private int playerX = 2;
  private int playerY = 2;
  private int cellSize = 25;
  
  public mazegame(String filename) {
    addKeyListener(this);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(0, 0, 475, 499);
    setVisible(true);


    // Load images
    try {
        wallImg = ImageIO.read(getClass().getResource("wall.png"));
        playerImg = ImageIO.read(getClass().getResource("player.png"));
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Load maze from file
    File mazeFile = new File(filename);
    try {
        Scanner scanner = new Scanner(mazeFile);
        int row = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            for (int col = 0; col < parts.length; col++) {
                mazeGrid[row][col] = Integer.parseInt(parts[col]);
            }
            row++;
        }
        scanner.close();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
}

    
  public void paint(Graphics g) {
    super.paint(g); // clear the panel background
    g.setColor(Color.WHITE);
    for (int i = 0; i < mazeSize; i++) {
        for (int j = 0; j < mazeSize; j++) {
            if (mazeGrid[i][j] == 1) {
                g.drawImage(wallImg, j * cellSize, i * cellSize, cellSize, cellSize, this);
            } else if (i == playerY && j == playerX) {
                g.drawImage(playerImg, j * cellSize, i * cellSize, cellSize, cellSize, this);
            }
        }
    }
}

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_UP && playerY > 0 && mazeGrid[playerY - 1][playerX] != 1) {
        playerY--;
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN && playerY < mazeSize - 1 && mazeGrid[playerY + 1][playerX] != 1) {
        playerY++;
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT && playerX > 0 && mazeGrid[playerY][playerX - 1] != 1) {
        playerX--;
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && playerX < mazeSize - 1 && mazeGrid[playerY][playerX + 1] != 1) {
        playerX++;
    }
    // Check if the player has reached the end of the maze
    if (playerX == mazeSize - 2 && playerY == mazeSize - 2) {
        JOptionPane.showMessageDialog(this, "Congratulations you won!");
        System.exit(0);
    }
    repaint(); // move the repaint() call to the end of the method
}

    public void keyTyped(KeyEvent e) {
        
    }
    
    public void keyReleased(KeyEvent e) {
        
    }
    
   public static void main(String[] args) {
     String filename = JOptionPane.showInputDialog("Enter the filename of the map you want to play (map1.txt, map2.txt, or map3.txt):");
     new mazegame(filename);
}

}
