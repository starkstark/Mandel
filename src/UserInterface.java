import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UserInterface {

	private MandelRequest request;
	private MandelBrot mandel;
	private MenuBar menuBar;
	private Frame frame;

	public UserInterface() {
		request = new MandelRequest();
		mandel = new MandelBrot(request);
		menuBar = new MenuBar();
		frame = new Frame();

	}

	@SuppressWarnings("serial")
	private class Frame extends JFrame {

		private JLabel jLabel;
		private BufferedImage image;

		private Frame() {
			super();

			// initImage();
			MandelRequest requestSave = request;
			System.out.println("initializing image ...");
			ColorPickerSmooth colorPicker = new ColorPickerSmooth(mandel.iterations, requestSave.maxIterations);
			this.image = new BufferedImage(requestSave.width, requestSave.height, BufferedImage.TYPE_INT_RGB);
			this.image.setRGB(0, 0, requestSave.width, requestSave.height,
					colorPicker.gradientLinear(Context.color1, Context.color2), 0, requestSave.width);

			this.jLabel = new JLabel();
			this.jLabel.setIcon(new ImageIcon(this.image));

			System.out.println("updating title ...");
			this.setTitle("MandelBrot :" + " resolution " + requestSave.width + " x " + requestSave.height + " "
					+ " iterations " + requestSave.maxIterations + " center (" + requestSave.xCenter + ","
					+ requestSave.yCenter + ")" + " scale " + requestSave.scale);

			System.out.println("initializing finished");

			JScrollPane scroller = new JScrollPane(this.jLabel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			this.getContentPane().setLayout(new FlowLayout());
			this.setJMenuBar(menuBar);
			this.getContentPane().add(scroller);
			this.pack();
			this.setLocationRelativeTo(null); // center postition
			this.setVisible(true);

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.jLabel.addMouseListener(new CustomMouseListener());
			this.jLabel.addMouseWheelListener(new CustomMouseWheelListener());
		}

		private JLabel getDisplayLabel() {
			return this.jLabel;
		}

		private BufferedImage getMandelImage() {
			return this.image;
		}

		private void resize() {
			this.image = new BufferedImage(request.width, request.height, BufferedImage.TYPE_INT_RGB);
		}

	}

	@SuppressWarnings("serial")
	private class MenuBar extends JMenuBar {

		private JTextField maxIterationField = new JTextField();
		private JTextField heightField = new JTextField();
		private JTextField widthField = new JTextField();
		private JTextField color1Field = new JTextField();
		private JTextField color2Field = new JTextField();

		private MenuBar() {
			super();

			JMenu menu = new JMenu("Menu");
			menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");

			// 'Save as' menu
			JMenuItem menuItem = new JMenuItem("Save as", KeyEvent.VK_S);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
			menuItem.getAccessibleContext().setAccessibleDescription("Save image as file");
			menuItem.addActionListener(new SaveActionListener());
			menu.add(menuItem);

			// 'Settings'
			/*
			 * JMenuItem menuItemSettings = new JMenuItem("Settings",
			 * KeyEvent.VK_E);
			 * menuItemSettings.setAccelerator(KeyStroke.getKeyStroke(
			 * KeyEvent.VK_E, ActionEvent.ALT_MASK));
			 * menuItemSettings.getAccessibleContext().setAccessibleDescription(
			 * "Change render settings"); menuItemSettings.addActionListener(new
			 * SettingsActionListener()); menu.add(menuItemSettings);
			 */

			// 'Help'
			JMenuItem menuItemHelp = new JMenuItem("Help", KeyEvent.VK_H);
			menuItemHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
			menuItemHelp.getAccessibleContext().setAccessibleDescription("Shows Help");
			menuItemHelp.addActionListener(new HelpActionListener());
			menu.add(menuItemHelp);

			menu.addSeparator();

			// 'Quit'
			JMenuItem menuItemQuit = new JMenuItem("Quit", KeyEvent.VK_Q);
			menuItemQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
			menuItemQuit.getAccessibleContext().setAccessibleDescription("Terminate application");
			menuItemQuit.addActionListener(new QuitActionListener());
			menu.add(menuItemQuit);
			menu.setBorder(new EmptyBorder(5, 5, 5, 5));

			this.add(menu);
			this.addSeparator();

			// max iterations input

			addLabeledTextField("Maximal iterations", "" + request.maxIterations, maxIterationField,
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int value = Integer.parseInt(maxIterationField.getText());
							if (value <= 0) {
								JOptionPane.showMessageDialog(null, "Error: Please enter number bigger than 0",
										"Error Message", JOptionPane.ERROR_MESSAGE);
							}
							request.maxIterations = value;
							updateSetting();
							updateImage();
						}
					});

			this.addSeparator();

			// width input

			addLabeledTextField("width in px", "" + request.width, widthField, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int value = Integer.parseInt(widthField.getText());
					if (value <= 0) {
						JOptionPane.showMessageDialog(null, "Error: Please enter number bigger than 0", "Error Message",
								JOptionPane.ERROR_MESSAGE);
					}
					request.width = value;
					request.height = (int) ((float) value / 3.5 * 2.0);
					frame.resize();
					updateSetting();
					updateImage();
				}
			});

			this.addSeparator();

			// height input

			addLabeledTextField("height in px", "" + request.height, heightField, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int value = Integer.parseInt(heightField.getText());
					if (value <= 0) {
						JOptionPane.showMessageDialog(null, "Error: Please enter number bigger than 0", "Error Message",
								JOptionPane.ERROR_MESSAGE);
					}
					request.height = value;
					request.width = (int) ((float) value / 2.0 * 3.5);
					frame.resize();
					updateSetting();
					updateImage();
				}
			});

			this.addSeparator();

			// color1 input

			addLabeledTextField("color1", "0x" + Integer.toHexString(Context.color1), color1Field,
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int value = Integer.decode(color1Field.getText());
							Context.color1 = value;
							updateSetting();
							updateImage();
						}
					});

			this.addSeparator();

			// color2 input

			addLabeledTextField("color2", "0x" + Integer.toHexString(Context.color2), color2Field,
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int value = Integer.decode(color2Field.getText());
							Context.color2 = value;
							updateSetting();
							updateImage();
						}
					});

			this.setBorder(BorderFactory.createRaisedBevelBorder());
		}

		private class SaveActionListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				JFileChooser c = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("PNG", new String[] { "png" });
				c.addChoosableFileFilter(filter);
				int rVal = c.showSaveDialog(frame);
			
				if (rVal == JFileChooser.APPROVE_OPTION) {
					try {
						ImageIO.write(frame.getMandelImage(), "png", c.getSelectedFile());
						System.out.println("Saved image: " + c.getSelectedFile());
						c.

					} catch (IOException ioException) {
						ioException.printStackTrace();
						System.err.println(ioException.getMessage());
					}
				}
			}
		}

		private class QuitActionListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Object[] options = { "Yes", "No" };
				int n = JOptionPane.showOptionDialog(frame, "Unsaved progress will be lost.",
						"Do you still want to quit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						options, options[1]);
				if (n == 0) {
					frame.dispose();
				}
			}
		}

		private class HelpActionListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Currently unavailable");
				// TODO
			}
		}

		private void addSeparator() {
			JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
			sep.setBorder(new EmptyBorder(5, 5, 5, 5));
			this.add(sep);
		}

		private void addLabeledTextField(String name, String text, JTextField textField,
				ActionListener actionListener) {
			JLabel label = new JLabel(name);
			label.setBorder(new EmptyBorder(5, 5, 5, 5));
			this.add(label);

			textField.setText(text);
			textField.addActionListener(actionListener);
			textField.setBorder(new EmptyBorder(5, 5, 5, 5));
			this.add(textField);
		}

		private void updateSetting() {
			this.widthField.setText("" + request.width);
			this.heightField.setText("" + request.height);
			this.maxIterationField.setText("" + request.maxIterations);
			this.color1Field.setText("0x" + Integer.toHexString(Context.color1));
			this.color2Field.setText("0x" + Integer.toHexString(Context.color2));
		}
	}

	private class CustomMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			int x = frame.getDisplayLabel().getMousePosition().x;
			int y = frame.getDisplayLabel().getHeight() - frame.getDisplayLabel().getMousePosition().y;
			System.out.println("Mouse x : " + x);
			System.out.println("Mouse y : " + y);
			request.updateCenter(x, y);
			updateImage();
		}
	}

	private class CustomMouseWheelListener extends MouseAdapter {
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
				 * message += "    Scroll type: WHEEL_UNIT_SCROLL" + newline;
				 * message += "    Scroll amount: " + e.getScrollAmount() +
				 * " unit increments per notch" + newline; message +=
				 * "    Units to scroll: " + e.getUnitsToScroll() +
				 * " unit increments" + newline; message +=
				 * "    Vertical unit increment: " +
				 * scrollPane.getVerticalScrollBar().getUnitIncrement(1) +
				 * " pixels" + newline;
				 */
			} else { // scroll type == MouseWheelEvent.WHEEL_BLOCK_SCROLL
				/*
				 * message += "    Scroll type: WHEEL_BLOCK_SCROLL" + newline;
				 * message += "    Vertical block increment: " +
				 * scrollPane.getVerticalScrollBar().getBlockIncrement(1) +
				 * " pixels" + newline;
				 */
			}
			// saySomething(message, e);
		}
	}

	private void updateImage() {

		if (this.mandel != null) {
			System.out.println("Cancel expired computation");
			this.mandel.shouldCancel = true;
		}

		System.out.println("updating image ...");
		MandelRequest requestSave = this.request;
		BufferedImage image = frame.getMandelImage();

		mandel = new MandelBrot(requestSave);

		ColorPickerSmooth colorPicker = new ColorPickerSmooth(mandel.iterations, requestSave.maxIterations);

		image.setRGB(0, 0, requestSave.width, requestSave.height,
				colorPicker.gradientLinear(Context.color1, Context.color2), 0, requestSave.width);

		frame.getDisplayLabel().setIcon(new ImageIcon(image));

		System.out.println("updating title ...");
		frame.setTitle("MandelBrot :" + " resolution " + this.request.width + " x " + this.request.height + " "
				+ " iterations " + this.request.maxIterations
				// + " center (" + this.request.xCenter + "," +
				// this.request.yCenter + ")"
				+ " scale " + this.request.scale);

		System.out.println("update finished");
	}

}
