package shape;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class GRectangle extends GShape {
	private static final long serialVersionUID = 1L;
	
	//¾îµªÅ×ÀÌ¼Ç
	private java.awt.Rectangle rectangle;
	
	public GRectangle() {
		super();
		this.shape = new java.awt.Rectangle();
		this.rectangle = (java.awt.Rectangle)this.shape;//type casting
	}
	
	public GShape newInstance() {
		return new GRectangle();
	}
	
	public GShape deepCopy() {
		AffineTransform affineTransform = new AffineTransform();
		Shape newShape = affineTransform.createTransformedShape(myShape); 
		GRectangle shape = new GRectangle(); 
		shape.setShape(newShape);
		shape.setGraphicsAttributes(this); 
		return shape;
	}
	
	public void setOrigin(int x, int y) {
		this.px = x;
		this.py = y;
		//this.rectangle.setBounds(x, y, 0, 0);
	}
	
	public void setPoint(int x, int y) {
		this.rectangle.setFrameFromDiagonal(px, py, x, y); 
		
		if (anchors!=null){ 
			anchors.setBoundingRect(this.rectangle.getBounds());
		} 	
	}
	
	@Override
	public void addPoint(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finishMoving(Graphics2D graphics2d, int x, int y) {
		// TODO Auto-generated method stub
		
	}
}
