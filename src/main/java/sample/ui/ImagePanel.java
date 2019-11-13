package sample.ui;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel{

    private Image image;

    public void setImage(Image image) {
      this.image = image;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(image!= null) {

          Dimension scale = getScaledDimension(image.getWidth(null), image.getHeight(null), this.getWidth(), this.getHeight());
          
          Image scaledImage = image.getScaledInstance(scale.width, scale.height, Image.SCALE_AREA_AVERAGING);
          Graphics2D g2d = (Graphics2D) g.create();
          g2d.drawImage(scaledImage, 0, 0, scaledImage.getWidth(null), scaledImage.getHeight(null), null);
          g2d.dispose();
        }
    }
    
    public static Dimension getScaledDimension(int originalWidth, int originalHeight, int boundWidth, int boundHeight) {
      int newWidth = originalWidth;
      int newHeight = originalHeight;

      // first check if we need to scale width
      if(originalWidth > boundWidth) {
        // scale width to fit
        newWidth = boundWidth;
        // scale height to maintain aspect ratio
        newHeight = (newWidth * originalHeight) / originalWidth;
      }

      // then check if we need to scale even with the new height
      if(newHeight > boundHeight) {
        // scale height to fit instead
        newHeight = boundHeight;
        // scale width to maintain aspect ratio
        newWidth = (newHeight * originalWidth) / originalHeight;
      }

      return new Dimension(newWidth, newHeight);
    }


}