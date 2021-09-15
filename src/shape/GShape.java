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

public abstract class GShape implements Cloneable, Serializable {//����Ŭ����(ó���� ������ ������ �Ѵ�)
	private static final long serialVersionUID = 1L;

	public enum EOnState {eOnShape, eOnResize, eOnRotate};//4���� ����
	
	protected int px;
	protected int py;
	
	protected Shape shape;//�������̽�
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
		//�̹� �������� �Ǿ������� �׸��� ����� ���Ƴ���
		if(!this.selected) {
			this.anchors.setBoundingRect(this.shape.getBounds());//��ο��Ҷ����� ��ǥ���
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

	public GShape clone() {//clone : �ڱ� �޸𸮿� ���¸� �״�� �����ϴ� �Լ�, ������Ʈ��� �ֻ��� Ŭ���� �ȿ� �ִ�.//reflection : �ڱⰡ �ڱ� Ŭ���� ������
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
	abstract public GShape newInstance();//�׸��� �׸� ������ newInstance
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
			this.anchors.setBoundingRect(this.shape.getBounds());//��ο��Ҷ����� ��ǥ���
			this.anchors.draw(graphics2d);
		}
	}
	
	public EOnState onShape(int x, int y) {//���콺�� ���� ���� �ֳ�
		if (this.selected) {
			EAnchors eAnchor = this.anchors.onShape(x, y);
			if (eAnchor == EAnchors.RR) {//rotate
				return EOnState.eOnRotate;
			} else if (eAnchor == null) {//��Ŀ �ۿ� �ִ� ���(move)
				if (this.shape.intersects(x, y ,2, 2)) {//��Ŀ ���� ���� �� ���� ������
					return EOnState.eOnShape;
				}
			} else {//resize
				return EOnState.eOnResize;
			}
		} else {//selection�� �ȵ� ����
			if (this.shape.contains(x, y)) {//��Ŀ ���� ���� �� ���� ������
				return EOnState.eOnShape;
			}
		}
		return null;
	}
}