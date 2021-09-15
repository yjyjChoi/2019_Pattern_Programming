package menu;
import javax.swing.JMenuBar;

import drawingPanel.GDrawingPanel;
import global.Constants.EMenu;

public class GMenuBar extends JMenuBar {
	//1. attributes(�Ӽ�: �� ���ο� �ִ� ��)
	private static final long serialVersionUID = 1L;
	
	//2. components(�ڽ�)
	private GFileMenu fileMenu;
	private GEditMenu editMenu;
	private GColorMenu colorMenu;
	
	//3. associations(�����ڸ�)
	private GDrawingPanel drawingPanel;
	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	public GMenuBar() {//1. �Ӽ��� �ڽ� ����
		//initialize attributes(�Ӽ� �ʱ�ȭ)
		
		//create components(�ڽ� ����)
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
		
		//3. initialize components//�ڽ� �ʱ�ȭ(ģ���� ���� �̴ϼȶ����� ��Ű�� �ȵ�)
		this.fileMenu.initialize();
		this.editMenu.initialize();
		this.colorMenu.initialize();
	}
}
