package vfe;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 * This class is used for creating the panel of the color palette
 * 
 * @author NIRUPAM
 * 
 */
@SuppressWarnings("serial")
public class ColorPanel extends AbstractColorChooserPanel implements ActionListener {

	// BLUE, GREEN, NEUTRAL, PINK, RED, TURQUOISE, YELLOW
	// TURQUOISE (web color) (Hex: #40E0D0) (RGB: 64, 224, 208)

	JToggleButton redColor, yellowColor, greenColor, blueColor, pinkColor,
			turquoiseColor, whiteColor;

	@Override
	public void updateChooser() {
		Color color = getColorFromModel();
		if (Color.red.equals(color))
			redColor.setSelected(true);
		else if (Color.yellow.equals(color))
			yellowColor.setSelected(true);
		else if (Color.green.equals(color))
			greenColor.setSelected(true);
		else if (Color.blue.equals(color))
			blueColor.setSelected(true);
		else if (Color.pink.equals(color))
			pinkColor.setSelected(true);
		else if (color.equals(Constants.TURQUOISE))
			turquoiseColor.setSelected(true);
		else if (color.equals(Color.white))
			whiteColor.setSelected(true);

	}

	protected JToggleButton createColor(String name, Color c,
			Border normalBorder) {
		JToggleButton color = new JToggleButton();
		color.setActionCommand(name);
		color.addActionListener(this);

		// Set the image or, if that's invalid, equivalent text.
		// ImageIcon icon = createImageIcon("images/" + name + ".gif");
		Icon icon = new ColorIcon(c);
		color.setIcon(icon);
		color.setToolTipText("The " + name + " color");
		color.setBorder(normalBorder);

		return color;
	}

	@Override
	protected void buildChooser() {
		setLayout(new GridLayout(2, 4));

		ButtonGroup boxOfColors = new ButtonGroup();
		Border border = BorderFactory.createEmptyBorder(4, 4, 4, 4);

		redColor = createColor("red", Color.red, border);
		boxOfColors.add(redColor);
		add(redColor);

		yellowColor = createColor("yellow", Color.yellow, border);
		boxOfColors.add(yellowColor);
		add(yellowColor);

		greenColor = createColor("green", Color.green, border);
		boxOfColors.add(greenColor);
		add(greenColor);

		blueColor = createColor("blue", Color.blue, border);
		boxOfColors.add(blueColor);
		add(blueColor);

		pinkColor = createColor("pink", Color.pink, border);
		boxOfColors.add(pinkColor);
		add(pinkColor);

		turquoiseColor = createColor("turquoise", Constants.TURQUOISE, border);
		boxOfColors.add(turquoiseColor);
		add(turquoiseColor);

		whiteColor = createColor("neutral", Color.white, border);
		boxOfColors.add(whiteColor);
		add(whiteColor);
	}

	/*
	 * /** Returns an ImageIcon, or null if the path was invalid. / protected
	 * static ImageIcon createImageIcon(String path) { java.net.URL imgURL =
	 * ColorPanel.class.getResource(path); if (imgURL != null) { return new
	 * ImageIcon(imgURL); } else { System.err.println("Couldn't find file: " +
	 * path); return null; } }
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		Color newColor = null;
		String command = ((JToggleButton) e.getSource()).getActionCommand();
		if ("green".equals(command))
			newColor = Color.green;
		else if ("red".equals(command))
			newColor = Color.red;
		else if ("yellow".equals(command))
			newColor = Color.yellow;
		else if ("blue".equals(command))
			newColor = Color.blue;
		else if ("pink".equals(command))
			newColor = Color.pink;
		else if ("neutral".equals(command))
			newColor = Color.white;
		else if ("turquoise".equals(command))
			newColor = Constants.TURQUOISE;
		getColorSelectionModel().setSelectedColor(newColor);
	}

	@Override
	public String getDisplayName() {
		return "Colors";
	}

	@Override
	public Icon getSmallDisplayIcon() {
		return null;
	}

	@Override
	public Icon getLargeDisplayIcon() {
		return null;
	}

	class ColorIcon implements Icon {
		Color color;

		public ColorIcon(Color c) {
			this.color = c;
			try 
			{
			   UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
			} 
			catch (Exception e) 
			{
				UIManager.setInstalledLookAndFeels(UIManager.getInstalledLookAndFeels());
			}
		}

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			// Color oldColor = g.getColor();
			g.setColor(color);
			g.fill3DRect(x, y, getIconWidth(), getIconHeight(), true);
		}

		@Override
		public int getIconWidth() {
			return 20;
		}

		@Override
		public int getIconHeight() {
			return 20;
		}
	}
}