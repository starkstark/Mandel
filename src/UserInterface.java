import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class UserInterface {
	
	private JFrame jFrame;
	private JLabel jLabel;
	
	public UserInterface(BufferedImage bufferedImage) {
    	jFrame = new JFrame();
    	jFrame.getContentPane().setLayout(new FlowLayout());
    	jLabel = new JLabel(new ImageIcon(bufferedImage));
    	jFrame.getContentPane().add(jLabel);
    	jFrame.pack();
    	jFrame.setVisible(true);
    	
    	jFrame.addKeyListener(new CustomKeyListener());
    	jLabel.addMouseListener(new CustomMouseListener());
    	jLabel.addMouseWheelListener(new CustomMouseWheelListener());
    }

    private class CustomKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			
			// 'ESC' released
			if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
				jFrame.dispose();
			}
			
			// 'h' released
			if(arg0.getKeyCode() == KeyEvent.VK_H) {
				printHelp();
			}
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
		}
    }
    
    private class CustomMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int x = jLabel.getMousePosition().x;
			int y = jLabel.getHeight() - jLabel.getMousePosition().y;		
			System.out.println("Mouse x : " + x);
			System.out.println("Mouse y : " + y);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
    }
    
    private class CustomMouseWheelListener implements MouseWheelListener {
        public void mouseWheelMoved(MouseWheelEvent e) {
           String message;
           int notches = e.getWheelRotation();
           if (notches < 0) {
        	   System.out.println("Mouse wheel moved UP");
        	   
           } else {
               System.out.println("Mouse wheel moved DOWN");
           }
           if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
        	   /*
               message += "    Scroll type: WHEEL_UNIT_SCROLL" + newline;
               message += "    Scroll amount: " + e.getScrollAmount()
                       + " unit increments per notch" + newline;
               message += "    Units to scroll: " + e.getUnitsToScroll()
                       + " unit increments" + newline;
               message += "    Vertical unit increment: "
                   + scrollPane.getVerticalScrollBar().getUnitIncrement(1)
                   + " pixels" + newline;
               */
           } else { //scroll type == MouseWheelEvent.WHEEL_BLOCK_SCROLL
        	   /*
               message += "    Scroll type: WHEEL_BLOCK_SCROLL" + newline;
               message += "    Vertical block increment: "
                   + scrollPane.getVerticalScrollBar().getBlockIncrement(1)
                   + " pixels" + newline;
        	   */
           }
           //saySomething(message, e);
        }
    }
    
    private void printHelp() {
    	System.out.println("help message");
    }
}