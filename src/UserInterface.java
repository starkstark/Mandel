import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UserInterface {
	
	private MandelRequest request = new MandelRequest();
	
	private MandelBrot mandel;
	
	private BufferedImage image;
	private JFrame jFrame;
	private JMenuBar jMenuBar;
	private JLabel jLabel = new JLabel();
	
	// -------------------------------------------------------------------
	// Interface
	// -------------------------------------------------------------------	
	
	public UserInterface() {
		this.image = new BufferedImage(request.width, request.height, BufferedImage.TYPE_INT_RGB);
    		
		jMenuBar = new MenuBar().getMenuBar();
		
    	jFrame = new JFrame();
    	jFrame.getContentPane().setLayout(new FlowLayout());
    	initImage();	
    	jFrame.setJMenuBar(jMenuBar);
    	jFrame.getContentPane().add(jLabel);
    	jFrame.pack();
    	jFrame.setLocationRelativeTo(null); //center postition
    	jFrame.setVisible(true);
    	
    	/*
    	jFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                request.width  = e.getComponent().getWidth();
                request.height = e.getComponent().getHeight();
                updateImage();
            }
        });
        */
    	
    	jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	jLabel.addMouseListener(new CustomMouseListener());
    	jLabel.addMouseWheelListener(new CustomMouseWheelListener());
    }
	
	@SuppressWarnings("serial")
	private class MenuBar extends JMenuBar {
		
		private JMenuBar jMenuBar;
		
		private MenuBar() {
			jMenuBar = new JMenuBar();
			
			JMenu menu = new JMenu("Menu");
			menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
			jMenuBar.add(menu);

			// 'Save as' menu
			JMenuItem menuItem = new JMenuItem("Save as",KeyEvent.VK_S);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(
			        KeyEvent.VK_1, ActionEvent.ALT_MASK));
			menuItem.getAccessibleContext().setAccessibleDescription(
			        "Save image as file");
			menuItem.addActionListener(new SaveActionListener());
			menu.add(menuItem);
			
			// 'Settings'
			/*
			JMenuItem menuItemSettings = new JMenuItem("Settings", KeyEvent.VK_E);
			menuItemSettings.setAccelerator(KeyStroke.getKeyStroke(
			        KeyEvent.VK_E, ActionEvent.ALT_MASK));
			menuItemSettings.getAccessibleContext().setAccessibleDescription(
			        "Change render settings");
			menuItemSettings.addActionListener(new SettingsActionListener());
			menu.add(menuItemSettings);
			*/
			
			// 'Help'
			JMenuItem menuItemHelp = new JMenuItem("Help", KeyEvent.VK_H);
			menuItemHelp.setAccelerator(KeyStroke.getKeyStroke(
			        KeyEvent.VK_H, ActionEvent.ALT_MASK));
			menuItemHelp.getAccessibleContext().setAccessibleDescription(
			        "Shows Help");
			menuItemHelp.addActionListener(new HelpActionListener());
			menu.add(menuItemHelp);
			
			menu.addSeparator();
			
			// 'Quit'
			JMenuItem menuItemQuit = new JMenuItem("Quit", KeyEvent.VK_Q);
			menuItemQuit.setAccelerator(KeyStroke.getKeyStroke(
			        KeyEvent.VK_Q, ActionEvent.ALT_MASK));
			menuItemQuit.getAccessibleContext().setAccessibleDescription(
			        "Terminate application");
			menuItemQuit.addActionListener(new QuitActionListener());
			menu.add(menuItemQuit);

			jMenuBar.add(new JLabel(" |  Maximal iterations "));
			JTextField textField = new JTextField();
			textField.setText("" + request.maxIterations);
			textField.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	int value = Integer.parseInt(textField.getText());
			        if (value <= 0){
			            JOptionPane.showMessageDialog(null,
			                    "Error: Please enter number bigger than 0", "Error Message",
			                    JOptionPane.ERROR_MESSAGE);
			        }
			        request.maxIterations = value;
			        updateImage();
			    }
			});
		}
		
		JMenuBar getMenuBar() {
			return this.jMenuBar;
		}
		
		private class SaveActionListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				JFileChooser c = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("PNG", new String[] {"png"});
				c.addChoosableFileFilter(filter);
				int rVal = c.showSaveDialog(jFrame);
				if (rVal == JFileChooser.APPROVE_OPTION) {
			    	try {
				   		ImageIO.write(image, "png", c.getSelectedFile());
				   		System.out.println("Saved image: " + c.getSelectedFile());
				   		
			    	} catch (IOException ioException) {
				   		ioException.printStackTrace();
				   		System.err.println(ioException.getMessage());
				   	}	
				}
			}
		}

		private class QuitActionListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Yes", "No" };
				int n = JOptionPane.showOptionDialog(jFrame,
				    "Unsaved progress will be lost.",
				    "Do you still want to quit?",
				    JOptionPane.YES_NO_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,
				    options,
				    options[1]);
				if(n == 0) {
					jFrame.dispose();
				}
			}			
		}
			
		private class HelpActionListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(jFrame,
					    "Currently unavailable");
				//TODO
			}
		}
		
	}

	// -------------------------------------------------------------------
	// Listener
	// -------------------------------------------------------------------

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

    private void initImage() {
    	System.out.println("updating image ...");
    	MandelRequest requestSave = this.request;
    	
    	mandel = new MandelBrot(requestSave);

    	ColorPickerSmooth colorPicker = new ColorPickerSmooth(
    	mandel.iterations, requestSave.maxIterations);

    	this.image.setRGB(
    		0, 0, requestSave.width, requestSave.height,
    		colorPicker.gradientLinear(0x00ffd400, 0x00000000), 0, requestSave.width);
    	
    	this.jLabel.setIcon(new ImageIcon(this.image));

    	System.out.println("updating title ...");
    	jFrame.setTitle("MandelBrot :" 
        		+ " resolution " + this.request.width + " x " + this.request.height + " "
        		+ " iterations " + this.request.maxIterations 
        		+ " center (" + this.request.xCenter + "," + this.request.yCenter + ")"
        		+ " scale " + this.request.scale);
    	
		System.out.println("update finished");  	
    }
    
    private void updateImage() {
    	
    	if(this.mandel != null) {
    		System.out.println("Cancel expired computation");
    		this.mandel.shouldCancel = true;
    	}  		
    
    	System.out.println("updating image ...");
    	MandelRequest requestSave = this.request;
    	
    	mandel = new MandelBrot(requestSave);

    	ColorPickerSmooth colorPicker = new ColorPickerSmooth(
    	mandel.iterations, requestSave.maxIterations);

    	this.image.setRGB(
    		0, 0, requestSave.width, requestSave.height,
    		colorPicker.gradientLinear(0x00ffd400, 0x00000000), 0, requestSave.width);
    	
    	this.jLabel.setIcon(new ImageIcon(this.image));

    	System.out.println("updating title ...");
    	jFrame.setTitle("MandelBrot :" 
        		+ " resolution " + this.request.width + " x " + this.request.height + " "
        		+ " iterations " + this.request.maxIterations 
        		//+ " center (" + this.request.xCenter + "," + this.request.yCenter + ")"
        		+ " scale " + this.request.scale);
    	
    	
		System.out.println("update finished");
    }
}