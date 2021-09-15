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

	private Vector<GShape> shapeVector;//그림 그리면 여기에 저장
	public Vector<GShape> getShapeVector() { 
		return this.shapeVector;
	}
	public void restoreShapeVector(Object shapeVector) {//파일 오픈하면 이쪽으로 들어옴
		if (shapeVector == null) {
			this.shapeVector.clear();
		} else {
			this.shapeVector = (Vector<GShape>)shapeVector;
		}
		this.repaint();
	}
	
	//working variables
	private enum EActionState { eReady, eTransforming } ;//드로잉패널의 상태
	private EActionState eActionState;
	
	private boolean updated;
	public boolean isUpdated() { return this.updated; }
	public void setUpdated(boolean updated) { this.updated = updated;}
	
	private GShape currentShape;//현재 그리고 있는 애
	private GTransformer transformer;
	private GShape currentTool;
	public void setCurrentTool(EToolBar currentTool) {
		this.currentTool = currentTool.getShape();//EToolBar로 받아서 getShape
	}
	
	public GDrawingPanel() {
		this.eActionState = EActionState.eReady;
		this.updated = false;//처음시작할 때 open 아니면 new를 할 거라서 실제로는 아무 영향을 미치지 않음
		
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
		super.paint(graphics2D);//부모 먼저 그리기//꼭 불러줘야 함!!!
		
		for (GShape shape: this.shapeVector) {
			shape.draw(graphics2D);
		}
	}
	
	private void clearSelected() {
		for (GShape shape: this.shapeVector) {
			shape.setSelected(false);
		}
	}
	
	private EOnState onShape(int x, int y) {//밑에 도형 있으면 move, 없으면 draw
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
	
	private void defineActionState(int x, int y) {//general한 userInterface
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
				break;//switch문에서 break 꼭 할 것!
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
		if (this.transformer instanceof GDrawer) {//드로잉 할 때만 도형 추가
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
		for (int i = this.shapeVector.size()-1; i>=0; i--) {//뒤에서부터 돌림
			if (this.shapeVector.get(i).isSelected()) {
				selectedShapes.add(this.shapeVector.get(i));//새로운 벡터에 옮기고
				this.shapeVector.remove(i);//기존 벡터에서 없앰
			}
		}
		this.repaint();
	}
	
	public void cut() {
		Vector<GShape> selectedShapes = new Vector<GShape>();
		for (int i = this.shapeVector.size()-1; i>=0; i--) {//뒤에서부터 돌림
			if (this.shapeVector.get(i).isSelected()) {
				selectedShapes.add(this.shapeVector.get(i));//새로운 벡터에 옮기고
				this.shapeVector.remove(i);//기존 벡터에서 없앰
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
	
	private class MouseHandler implements MouseListener,MouseMotionListener {//os가 얘를 호출할 수 있어야함->함수 이름이 고정되어야 함
		//이쪽에는 함수호출만할 것. 그림을 그리는 등 잡다한 코드X
		//마우스 핸들러 안에는 logic 같은 거 넣지 말고 '교통정리'만 해야함.
		//event action mapping (스테이트 트렌직션)
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
		public void mousePressed(MouseEvent e) {//클릭이 프레스를 포함
			if (eActionState == EActionState.eReady) {//드로잉패널의 상태
				defineActionState(e.getX(), e.getY());//누구 위에 있는지 체크
				if (!(currentTool instanceof GPolygon)){
					initTransforming(e.getX(), e.getY());
					eActionState = EActionState.eTransforming;//뭘 할지 결정
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
		public void mouseEntered(MouseEvent e) {//마우스 대기
			
		}
		@Override
		public void mouseExited(MouseEvent e) {//마우스 빼기
			
		}
	}
}
