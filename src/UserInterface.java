import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class UserInterface {
	
	private MandelRequest request;
	private BufferedImage image;
	private JFrame jFrame;
	private JLabel jLabel = new JLabel();
	
	public UserInterface() {
		this.request = new MandelRequest();
		this.image   = new BufferedImage(request.width, request.height, BufferedImage.TYPE_INT_RGB);
		updateImage();
		
    	jFrame = new JFrame();
    	jFrame.getContentPane().setLayout(new FlowLayout());
    	//jLabel = new JLabel(new ImageIcon(this.image));
    	jFrame.getContentPane().add(jLabel);
    	jFrame.pack();
    	jFrame.setVisible(true);
    	
    	jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	jFrame.addKeyListener(new CustomKeyListener());
    	jLabel.addMouseListener(new CustomMouseListener());
    	jLabel.addMouseWheelListener(new CustomMouseWheelListener());
    }

    private class CustomKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent arg0) {
			
			// 'ESC' released
			if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
				jFrame.dispose();
				System.exit(0);
			}
			
			// 'h' released
			if(arg0.getKeyCode() == KeyEvent.VK_H) {
				printHelp();
			}
			
			// 'enter' released
			if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
				updateImage();
			}
			
			// 'p' released
			if(arg0.getKeyCode() == KeyEvent.VK_P) {
				System.out.println("Printing...");
				//TODO print
			}
		}
    }

    private class CustomMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			int x = jLabel.getMousePosition().x;
			int y = jLabel.getHeight() - jLabel.getMousePosition().y;		
			System.out.println("Mouse x : " + x);
			System.out.println("Mouse y : " + y);
			request.updateCenter(x, y);
			updateImage();
		}
    }
    
    private class CustomMouseWheelListener extends MouseAdapter{
        public void mouseWheelMoved(MouseWheelEvent e) {
           int notches = e.getWheelRotation();
           if (notches < 0) {
        	   System.out.println("Mouse wheel moved UP");
        	   request.decreaseScale();
        	   updateImage();
        	   
           } else {
               System.out.println("Mouse wheel moved DOWN");
               request.increaseScale();
               updateImage();
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
    
    private void updateImage() {
    	System.out.println("updating image ...");
    	MandelRequest requestSave = this.request;
    	MandelBrot mandel = new MandelBrot(requestSave);
    	
    	ColorPicker colorPicker = new ColorPicker(
    		mandel.iterations, requestSave.maxIterations);

    	this.image.setRGB(
    		0, 0, requestSave.width, requestSave.height,
    		colorPicker.convertToRainbow(), 0, requestSave.width);
    	
    	this.jLabel.setIcon(new ImageIcon(this.image));

		System.out.println("update finished");
    }
}