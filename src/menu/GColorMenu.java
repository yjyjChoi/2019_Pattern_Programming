package menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import drawingPanel.GDrawingPanel;
import global.Constants.EColorMenuItems;


public class GColorMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	private GDrawingPanel drawingPanel; 
	private ColorMenuHandler colorMenuHandler; 
	public void associate(GDrawingPanel drawingPanel) { this.drawingPanel = drawingPanel; }

	public GColorMenu(String text) {
		super(text); 
		colorMenuHandler = new ColorMenuHandler(); 
		for (EColorMenuItems items: EColorMenuItems.values()) { 
			JMenuItem menuItem = new JMenuItem(items.name()); 
			menuItem.addActionListener(colorMenuHandler); 
			menuItem.setActionCommand(items.name()); 
			this.add(menuItem);
		}
	}
	
	public void initialize() {
		
	}
	
	private void setLineColor() {
		Color lineColor = JColorChooser.showDialog (null, "Select Line Color" , null);
		if (lineColor != null) { 
			drawingPanel.setLineColor(lineColor); 
		} 
	}
	
	private void setFillColor() { 
		Color fillColor = JColorChooser.showDialog (null, "Select Fill Color" , null); 
		if (fillColor != null) { 
			drawingPanel.setFillColor(fillColor); 
		} 
	} 
	
	private void setBackgroundColor() { 
		Color fillColor = JColorChooser.showDialog (null, "Select Background Color" , null); 
		if (fillColor != null) { 
			drawingPanel.setBackground(fillColor);
		} 
	}
	
	private void clearLineColor() { 
		drawingPanel.setLineColor(Color.BLACK); 
	} 
	
	private void clearFillColor() { 
		drawingPanel.setFillColor(Color.WHITE); 
	}
	
	private void clearBackgroundColor() {
		drawingPanel.setBackground(Color.WHITE);
	}
	
	private class ColorMenuHandler implements ActionListener { 
		public void actionPerformed(ActionEvent e) { 
			switch (EColorMenuItems.valueOf(e.getActionCommand())) { 
				case setLineColor : setLineColor();
				break; 
				case setFillColor : setFillColor(); 
				break;
				case clearLineColor : clearLineColor();
				break; 
				case clearFillColor : clearFillColor(); 
				break;
				case setBackgroundColor : setBackgroundColor();
				break;
				case clearBackgroundColor : clearBackgroundColor();
				break;
			}
		} 
	}
}
