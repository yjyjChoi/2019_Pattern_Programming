package drawingPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

import global.Constants.EToolBar;
import shape.GGroup;
import shape.GPolygon;
import shape.GShape;
import shape.GShape.EOnState;
import transformer.GDrawer;
import transformer.GMover;
import transformer.GResizer;
import transformer.GRotator;
import transformer.GTransformer;

public class GDrawingPanel extends JPanel {
	//attributes
	private static final long serialVersionUID = 1L;

	//components
	private Color lineColor, fillColor;
	private MouseHandler mouseHandler;
	private Clipboard clipboard;

	private Vector<GShape> shapeVector;//�׸� �׸��� ���⿡ ����
	public Vector<GShape> getShapeVector() { 
		return this.shapeVector;
	}
	public void restoreShapeVector(Object shapeVector) {//���� �����ϸ� �������� ����
		if (shapeVector == null) {
			this.shapeVector.clear();
		} else {
			this.shapeVector = (Vector<GShape>)shapeVector;
		}
		this.repaint();
	}
	
	//working variables
	private enum EActionState { eReady, eTransforming } ;//������г��� ����
	private EActionState eActionState;
	
	private boolean updated;
	public boolean isUpdated() { return this.updated; }
	public void setUpdated(boolean updated) { this.updated = updated;}
	
	private GShape currentShape;//���� �׸��� �ִ� ��
	private GTransformer transformer;
	private GShape currentTool;
	public void setCurrentTool(EToolBar currentTool) {
		this.currentTool = currentTool.getShape();//EToolBar�� �޾Ƽ� getShape
	}
	
	public GDrawingPanel() {
		this.eActionState = EActionState.eReady;
		this.updated = false;//ó�������� �� open �ƴϸ� new�� �� �Ŷ� �����δ� �ƹ� ������ ��ġ�� ����
		
		this.setForeground(Color.BLACK);
		this.setBackground(Color.WHITE);
		
		this.mouseHandler = new MouseHandler();
		this.addMouseListener(this.mouseHandler);
		this.addMouseMotionListener(this.mouseHandler);
		
		this.clipboard = new Clipboard();
		
		this.shapeVector = new Vector<GShape>();
		this.transformer = null;
		
		initializeShapeColor();
	}
	
	public void initialize() {
		
	}
	
	public void setLineColor(Color lineColor) { 
		if (selectedSetColor(true, lineColor)) {
			return; 
		} 
		this.lineColor = lineColor; 
	} 
	
	public void setFillColor(Color fillColor) {
		if (selectedSetColor(false, fillColor)) {
			return; 
		} 
		this.fillColor = fillColor; 
	}
	
	private boolean selectedSetColor(boolean flag, Color color) {
		boolean returnValue = false; 
		for(GShape shape: shapeVector){ 
			if (shape.isSelected()) { 
				if (flag) { 
					shape.setLineColor(color); 
				} else { 
					shape.setFillColor(color); 
				} 
			returnValue = true;
			}
		}
		repaint();
		return returnValue;
	}
	
	private void initializeShapeColor() { 
		this.lineColor = Color.BLACK;
		this.fillColor = Color.WHITE;
	} 
	
	public void paint(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D)graphics;
		super.paint(graphics2D);//�θ� ���� �׸���//�� �ҷ���� ��!!!
		
		for (GShape shape: this.shapeVector) {
			shape.draw(graphics2D);
		}
	}
	
	private void clearSelected() {
		for (GShape shape: this.shapeVector) {
			shape.setSelected(false);
		}
	}
	
	private EOnState onShape(int x, int y) {//�ؿ� ���� ������ move, ������ draw
		this.currentShape = null;
		for(GShape shape: this.shapeVector) {
			EOnState eOnState = shape.onShape(x, y);
			if (eOnState != null) {
				this.currentShape = shape;
				return eOnState;
			}
		}
		return null;
	}
	
	private void defineActionState(int x, int y) {//general�� userInterface
		EOnState eOnState = onShape(x, y);
		if (eOnState == null) {
			this.clearSelected();
			this.transformer = new GDrawer();
		} else {
			if (!(this.currentShape.isSelected())) {
				this.clearSelected();
				this.currentShape.setSelected(true);
			}

			switch (eOnState) {
			case eOnShape:
				this.transformer = new GMover();
				break;//switch������ break �� �� ��!
			case eOnResize:
				this.transformer = new GResizer();
				break;
			case eOnRotate:
				this.transformer = new GRotator();
				break;
			default:
				//exception
				this.eActionState =  null;
				break;
			}
		}
	}
	
	//transforming
	private void initTransforming(int x, int y) {
		if (this.transformer instanceof GDrawer) {
			this.currentShape = this.currentTool.newInstance();
			//this.currentShape.setLineColor(lineColor);
			//this.currentShape.setFillColor(fillColor); 
		}
		this.transformer.setGShape(this.currentShape);
		this.transformer.initTransforming((Graphics2D)this.getGraphics(), x, y);
	}
	
	private void keepTransforming(int x, int y) {
		Graphics2D graphics2d = (Graphics2D)this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		this.transformer.keepTransforming((Graphics2D)this.getGraphics(), x, y);
		//this.repaint();
	}
	
	private void finishTransforming(int x, int y) {
		this.transformer.finishTransforming((Graphics2D)this.getGraphics(), x, y);
		if (this.transformer instanceof GDrawer) {//����� �� ���� ���� �߰�
			if (this.currentShape instanceof GGroup) {
				((GGroup)(this.currentShape)).contains(this.shapeVector);
			} else {
				this.shapeVector.add(currentShape);
			}
		}
		this.repaint();
		this.updated = true;
	}
	
	public void continueTransforming(int x, int y) {
		this.transformer.continueTransforming((Graphics2D)this.getGraphics(), x, y);
	}
	
	//edit
	public void clearPanel() {
		for(int i = this.shapeVector.size()-1; i>=0; i--){  
			this.shapeVector.remove(i);
		} 
		this.repaint();
	}
	
	public void undo() {
		
	}
	
	public void redo() {
		
	}
	
	public void delete() {
		Vector<GShape> selectedShapes = new Vector<GShape>();
		for (int i = this.shapeVector.size()-1; i>=0; i--) {//�ڿ������� ����
			if (this.shapeVector.get(i).isSelected()) {
				selectedShapes.add(this.shapeVector.get(i));//���ο� ���Ϳ� �ű��
				this.shapeVector.remove(i);//���� ���Ϳ��� ����
			}
		}
		this.repaint();
	}
	
	public void cut() {
		Vector<GShape> selectedShapes = new Vector<GShape>();
		for (int i = this.shapeVector.size()-1; i>=0; i--) {//�ڿ������� ����
			if (this.shapeVector.get(i).isSelected()) {
				selectedShapes.add(this.shapeVector.get(i));//���ο� ���Ϳ� �ű��
				this.shapeVector.remove(i);//���� ���Ϳ��� ����
			}
		}
		this.clipboard.setContents(selectedShapes);
		this.repaint();
	}
	
	public void copy() {
		Vector<GShape> selectedShapes = new Vector<GShape>(); 
		for(int i = this.shapeVector.size()-1; i>=0; i--){ 
			if (this.shapeVector.get(i).isSelected()) {
				selectedShapes.add(this.shapeVector.get(i));
			} 
		} 
		this.clipboard.setContents(selectedShapes);
		this.repaint(); 
	}
	
	public void paste() {
		Vector<GShape> shapes = this.clipboard.getContents();
		this.shapeVector.addAll(shapes);
		this.repaint();
	}
	
	public void group(GGroup group) {
		
	} 
	
	public void ungroup() {
		
	}
	
	private class MouseHandler implements MouseListener,MouseMotionListener {//os�� �긦 ȣ���� �� �־����->�Լ� �̸��� �����Ǿ�� ��
		//���ʿ��� �Լ�ȣ�⸸�� ��. �׸��� �׸��� �� ����� �ڵ�X
		//���콺 �ڵ鷯 �ȿ��� logic ���� �� ���� ���� '��������'�� �ؾ���.
		//event action mapping (������Ʈ Ʈ������)
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 1) {
				mouse1Clicked(e);
			} else if(e.getClickCount() == 2) {
				mouse2Clicked(e);
			}
		}
		private void mouse2Clicked(MouseEvent e) {
			if (eActionState == EActionState.eTransforming) {
				if (currentTool instanceof GPolygon){
					finishTransforming(e.getX(), e.getY());
					eActionState = EActionState.eReady;
				}
			}
		}
		private void mouse1Clicked(MouseEvent e) {
			if (currentTool instanceof GPolygon){
				if (eActionState == EActionState.eReady) {
					initTransforming(e.getX(), e.getY());
					eActionState = EActionState.eTransforming;
				} else if (eActionState == EActionState.eTransforming) {
					continueTransforming(e.getX(), e.getY());
				}
			}
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			if (eActionState == EActionState.eTransforming) {
				if (currentTool instanceof GPolygon){
					keepTransforming(e.getX(), e.getY());
				}
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {//Ŭ���� �������� ����
			if (eActionState == EActionState.eReady) {//������г��� ����
				defineActionState(e.getX(), e.getY());//���� ���� �ִ��� üũ
				if (!(currentTool instanceof GPolygon)){
					initTransforming(e.getX(), e.getY());
					eActionState = EActionState.eTransforming;//�� ���� ����
				} else {
					eActionState = EActionState.eReady;
				}
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if (eActionState == EActionState.eTransforming) {
				if (!(currentTool instanceof GPolygon)){
					finishTransforming(e.getX(), e.getY());
					eActionState = EActionState.eReady;
				}
			}
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if (eActionState == EActionState.eTransforming) {
				keepTransforming(e.getX(), e.getY());
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {//���콺 ���
			
		}
		@Override
		public void mouseExited(MouseEvent e) {//���콺 ����
			
		}
	}
}
