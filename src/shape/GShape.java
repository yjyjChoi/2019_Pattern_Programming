package shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import shape.GAnchors.EAnchors;

public abstract class GShape implements Cloneable, Serializable {//슈퍼클래스(처음에 신중히 만들어야 한다)
	private static final long serialVersionUID = 1L;

	public enum EOnState {eOnShape, eOnResize, eOnRotate};//4개의 상태
	
	protected int px;
	protected int py;
	
	protected Shape shape;//인터페이스
	protected Shape myShape;
	protected GAnchors anchors;
	protected EAnchors selectedAnchor;
	protected AffineTransform affineTransform;
	protected Color lineColor, fillColor;
	private boolean selected;
	
	public Shape getShape() {return this.shape;}
	public boolean isSelected() { return selected; }
	public void setSelected(boolean selected) { 
		this.selected = selected;
		if (this.selected) {

		}
	}
	protected void setShape(Shape shape){
		myShape = shape;
	}
	
	public GShape() {
		this.myShape = this.shape;
		this.selected = false;
		this.anchors = new GAnchors();
		affineTransform = new AffineTransform();
	}
	
	public void setGraphicsAttributes(GShape shape) { 
		setLineColor(shape.getLineColor()); 
		setFillColor(shape.getFillColor()); 
		setAnchor(shape.getAnchor());
		setSelected(shape.isSelected()); 
	}
	
	public GAnchors getAnchor(){ 
		return anchors;
	} 
	
	public void setAnchor(GAnchors anchors){ 
		this.anchors = anchors;
	} 
	
	public EAnchors getSelectedAnchor(){ 
		return selectedAnchor; 
	}
	
	public void setLineColor(Color lineColor){ 
		this.lineColor = lineColor;
	} 
	
	public Color getLineColor() { 
		return lineColor;
	}
	
	public void setFillColor(Color fillColor){ 
		this.fillColor = fillColor;
	} 
	
	public Color getFillColor() {
		return fillColor; 
	}
	
	public Rectangle getBounds(){ 
		return myShape.getBounds();
	}
	
	public abstract void setOrigin(int x, int y);
	public abstract void setPoint(int x, int y);
	public abstract void addPoint(int x, int y);
	
	public void initMoving(Graphics2D graphics2d, int x, int y) {
		this.px = x;
		this.py = y;
		//이미 셀렉션이 되어있으면 그리지 말라고 막아놔라
		if(!this.selected) {
			this.anchors.setBoundingRect(this.shape.getBounds());//드로우할때마다 좌표계산
			this.anchors.draw(graphics2d);
		}
	}
	public void keepMoving(int x, int y) {
		int dw = x - this.px;
		int dh = y - this.py;
		
		affineTransform.setToTranslation(dw, dh); 
		shape = affineTransform.createTransformedShape(shape);
		
		this.px = x;
		this.py = y;
	}
	public abstract void finishMoving(Graphics2D graphics2d, int x, int y);

	public GShape clone() {//clone : 자기 메모리와 상태를 그대로 복제하는 함수, 오브젝트라는 최상위 클래스 안에 있다.//reflection : 자기가 자기 클래스 가져옴
		//deep clone
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(this);
			
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			return (GShape)objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	abstract public GShape newInstance();//그림을 그릴 때마다 newInstance
	abstract public GShape deepCopy();
	
	public void draw(Graphics2D graphics2d) {
		graphics2d.draw(this.shape);
		if (this.fillColor!=null){
			graphics2d.setColor(this.fillColor); 
			graphics2d.fill(this.shape);
		}   
		
		if (this.lineColor!=null){  
			graphics2d.setColor(this.lineColor);  
			graphics2d.draw(this.shape);   
		}   
		if (this.selected) {
			this.anchors.setBoundingRect(this.shape.getBounds());//드로우할때마다 좌표계산
			this.anchors.draw(graphics2d);
		}
	}
	
	public EOnState onShape(int x, int y) {//마우스가 누구 위에 있나
		if (this.selected) {
			EAnchors eAnchor = this.anchors.onShape(x, y);
			if (eAnchor == EAnchors.RR) {//rotate
				return EOnState.eOnRotate;
			} else if (eAnchor == null) {//앵커 밖에 있는 경우(move)
				if (this.shape.intersects(x, y ,2, 2)) {//앵커 위에 없고 내 위에 있으면
					return EOnState.eOnShape;
				}
			} else {//resize
				return EOnState.eOnResize;
			}
		} else {//selection이 안된 상태
			if (this.shape.contains(x, y)) {//앵커 위에 없고 내 위에 있으면
				return EOnState.eOnShape;
			}
		}
		return null;
	}
}