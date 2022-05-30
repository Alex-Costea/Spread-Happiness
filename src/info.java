/* Decompiler 3ms, total 169ms, lines 45 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class info extends JFrame {
   Graphics gp;

   public void paint(Graphics g) {
      this.gp = g;
      this.printImage("info.jpg", 3, 30, 400, 400);
   }

   info() {
      this.setTitle("Info");
      this.setSize(406, 430);
      this.setResizable(false);
      this.setFocusable(true);
      this.setVisible(true);
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
            this.printImage((String)null, 0, 0, 0, 0);
            return null;
         }
      }
   }

   public void printImage(String path, int x1, int y1, int x2, int y2) {
      this.gp.drawImage(this.getImage(path), x1, y1, x2, y2, (ImageObserver)null);
   }
}
