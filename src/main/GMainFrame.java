package main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import drawingPanel.GDrawingPanel;
import global.Constants.EMainFrame;
import menu.GMenuBar;
import toolbar.GToolBar;

public class GMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	//components
	private GMenuBar menuBar;
	private GToolBar toolBar;
	private GDrawingPanel drawingPanel;
	
	public GMainFrame() {//자기 속성은 되도록 안에서
		//attributes(속성)
		this.setTitle(EMainFrame.title.getValue());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
		this.setLocation(
				dim.width/7 - this.getSize().width/2, // x좌표
				dim.height/7 - this.getSize().height/2); // y좌표
		this.setSize(EMainFrame.w.getInt(), EMainFrame.h.getInt());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(new BorderLayout());

		//components(자식들)
		this.menuBar = new GMenuBar();
		this.setJMenuBar(this.menuBar);
		
		this.toolBar = new GToolBar();
		this.add(this.toolBar, BorderLayout.NORTH);
		
		this.drawingPanel = new GDrawingPanel();
		this.add(this.drawingPanel, BorderLayout.CENTER);
	}

	public void initialize() {
		//associations
		this.menuBar.associate(this.drawingPanel);
		this.toolBar.associate(this.drawingPanel);
		
		//initialize
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize();
		//자기가 어소시에이션 되고 나서 자식의 어소시에이션 한다.
	}
}
