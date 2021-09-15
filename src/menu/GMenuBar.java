package menu;
import javax.swing.JMenuBar;

import drawingPanel.GDrawingPanel;
import global.Constants.EMenu;

public class GMenuBar extends JMenuBar {
	//1. attributes(속성: 내 내부에 있는 값)
	private static final long serialVersionUID = 1L;
	
	//2. components(자식)
	private GFileMenu fileMenu;
	private GEditMenu editMenu;
	private GColorMenu colorMenu;
	
	//3. associations(형제자매)
	private GDrawingPanel drawingPanel;
	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	public GMenuBar() {//1. 속성과 자식 생성
		//initialize attributes(속성 초기화)
		
		//create components(자식 생성)
		this.fileMenu = new GFileMenu(EMenu.fileMenu.getText());
		this.add(this.fileMenu);
		this.editMenu = new GEditMenu(EMenu.editMenu.getText());
		this.add(this.editMenu);
		this.colorMenu = new GColorMenu(EMenu.colorMenu.getText());
		this.add(this.colorMenu);
	}

	public void initialize() {
		//2. associate
		this.fileMenu.associate(this.drawingPanel);
		this.editMenu.associate(this.drawingPanel);
		this.colorMenu.associate(this.drawingPanel);
		
		//3. initialize components//자식 초기화(친구는 내가 이니셜라이즈 시키면 안됨)
		this.fileMenu.initialize();
		this.editMenu.initialize();
		this.colorMenu.initialize();
	}
}
