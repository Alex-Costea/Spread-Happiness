/* Decompiler 66ms, total 236ms, lines 264 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

public class main extends JFrame implements MouseListener, KeyListener {
   Graphics gp = null;
   int[][] v = new int[35][35];
   int[][] v2 = new int[35][35];
   int nrvar = 0;
   int highscore = 0;

   public void paint(Graphics g) {
      this.gp = g;

      for(int i = 1; i <= 30; ++i) {
         for(int j = 1; j <= 30; ++j) {
            if (this.v[i][j] == 0) {
               this.printImage("sad.png", 3 + (i - 1) * 20, 30 + (j - 1) * 20);
            } else {
               this.printImage("happy.png", 3 + (i - 1) * 20, 30 + (j - 1) * 20);
            }
         }
      }

      this.gp.clearRect(3, 631, this.getWidth(), this.getHeight());
      this.gp.drawString("Score:" + this.nrvar + " .High Score:" + this.highscore + ". For instructions press I. Press R to reset.", 3, 650);
   }

   private void init() {
      for(int i = 1; i <= 30; ++i) {
         for(int j = 1; j <= 30; ++j) {
            this.v[i][j] = 0;
         }
      }

      this.v[15][15] = 1;
      this.v[15][16] = 1;
      this.v[16][15] = 1;
      this.v[16][16] = 1;
      this.nrvar = 4;
   }

   private void redo() {
      this.nrvar = 0;

      int i;
      int j;
      for(i = 1; i <= 30; ++i) {
         for(j = 1; j <= 30; ++j) {
            this.v2[i][j] = this.v[i][j];
         }
      }

      for(i = 1; i <= 30; ++i) {
         for(j = 1; j <= 30; ++j) {
            if (this.v[i][j] == 0) {
               if (this.v2[i - 1][j - 1] + this.v2[i - 1][j] + this.v2[i - 1][j + 1] + this.v2[i][j - 1] + this.v2[i][j + 1] + this.v2[i + 1][j - 1] + this.v2[i + 1][j] + this.v2[i + 1][j + 1] == 3) {
                  this.v[i][j] = 1;
               }
            } else if (this.v2[i - 1][j - 1] + this.v2[i - 1][j] + this.v2[i - 1][j + 1] + this.v2[i][j - 1] + this.v2[i][j + 1] + this.v2[i + 1][j - 1] + this.v2[i + 1][j] + this.v2[i + 1][j + 1] != 3 && this.v2[i - 1][j - 1] + this.v2[i - 1][j] + this.v2[i - 1][j + 1] + this.v2[i][j - 1] + this.v2[i][j + 1] + this.v2[i + 1][j - 1] + this.v2[i + 1][j] + this.v2[i + 1][j + 1] != 2) {
               this.v[i][j] = 0;
            }

            if (this.v[i][j] == 1) {
               ++this.nrvar;
            }
         }
      }

      if (this.nrvar < 2) {
         this.init();
      }

      if (this.nrvar > this.highscore) {
         this.highscore = this.nrvar;
      }

   }

   private void readinfo() throws FileNotFoundException, IOException {
      BufferedReader br = new BufferedReader(new FileReader("DO NOT DELETE"));
      String line = br.readLine();
      this.highscore = Integer.parseInt(line);

      for(int i = 1; i <= 30; ++i) {
         for(int j = 1; j <= 30; ++j) {
            line = br.readLine();
            line = br.readLine();
            this.v[i][j] = Integer.parseInt(line);
         }
      }

      br.close();
   }

   void writeinfo() {
      PrintWriter writer = null;

      try {
         writer = new PrintWriter("DO NOT DELETE", "UTF-8");
         writer.println(this.highscore + "\n");

         for(int i = 1; i <= 30; ++i) {
            for(int j = 1; j <= 30; ++j) {
               writer.println(this.v[i][j] + "\n");
            }
         }
      } catch (FileNotFoundException var8) {
         Logger.getLogger(main.class.getName()).log(Level.SEVERE, (String)null, var8);
      } catch (UnsupportedEncodingException var9) {
         Logger.getLogger(main.class.getName()).log(Level.SEVERE, (String)null, var9);
      } finally {
         writer.close();
      }

   }

   main() {
      System.out.println("Hello, world!");
      this.setTitle("Spread Happiness!");
      this.setSize(606, 700);
      this.setResizable(false);
      this.setFocusable(true);
      WindowListener exitListener = new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            main.this.beforeEnding();
         }
      };
      this.addWindowListener(exitListener);
      this.addMouseListener(this);
      this.addKeyListener(this);
      this.highscore = 4;
      this.init();

      try {
         this.readinfo();
      } catch (FileNotFoundException var3) {
         this.highscore = 4;
         this.init();
      } catch (IOException var4) {
         Logger.getLogger(main.class.getName()).log(Level.SEVERE, (String)null, var4);
      }

      this.setVisible(true);
   }

   public void mouseReleased(MouseEvent e) {
      Point where = e.getPoint();
      int x = (where.x - 3) / 20 + 1;
      int y = (where.y - 30) / 20 + 1;
      if (x >= 1 && x <= 30 && y >= 1 && y <= 30) {
         this.v[x][y] = 1 - this.v[x][y];
         this.repaint();
         Timer q = new Timer(300, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               main.this.redo();
               main.this.repaint();
            }
         });
         q.setRepeats(false);
         q.start();
      }

   }

   public void beforeEnding() {
      this.writeinfo();
      System.out.println("Goodbye, world!");
      System.exit(0);
   }

   public void keyTyped(KeyEvent e) {
      if (e.getKeyChar() == 'i' || e.getKeyChar() == 'I') {
         System.out.println("i key pressed");
         new info();
      }

      if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R') {
         System.out.println("r key pressed");
         this.init();
         this.repaint();
         this.highscore = 4;
      }

   }

   public boolean isInside(Point a, int x1, int y1, int x2, int y2) {
      if (a.x < x1) {
         return false;
      } else if (a.x > x1 + x2) {
         return false;
      } else if (a.y < y1) {
         return false;
      } else {
         return a.y <= y1 + y2;
      }
   }

   public Image getImage(String path) {
      if (path == null) {
         path = "null.jpg";
         System.out.println("Printing the null image!");
         return null;
      } else {
         try {
            return (new ImageIcon(this.getClass().getResource(path))).getImage();
         } catch (Exception var3) {
            System.out.println("Error:" + path + " doesn't exist!");
            this.printImage((String)null, 0, 0);
            return null;
         }
      }
   }

   public void printImage(String path, int x1, int y1, int x2, int y2) {
      this.gp.drawImage(this.getImage(path), x1, y1, x2, y2, (ImageObserver)null);
   }

   public void printImage(String path, int x1, int y1) {
      this.gp.drawImage(this.getImage(path), x1, y1, (ImageObserver)null);
   }

   public void keyPressed(KeyEvent e) {
   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseClicked(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void keyReleased(KeyEvent e) {
   }
}
