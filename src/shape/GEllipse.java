package shape;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class GEllipse extends GShape {
	private static final long serialVersionUID = 1L;

	private java.awt.geom.Ellipse2D.Double ellipse;
	
	public GEllipse() {
		super();
		this.shape = new java.awt.geom.Ellipse2D.Double();
		this.ellipse = (java.awt.geom.Ellipse2D.Double)this.shape;
	}
	
	public GShape newInstance() {
		return new GEllipse();	
	}
	
	@Override
	public GShape deepCopy() {
		AffineTransform affineTransform = new AffineTransform();
		Shape newShape = affineTransform.createTransformedShape(myShape); 
		GEllipse shape = new GEllipse(); 
		shape.setShape(newShape);
		shape.setGraphicsAttributes(this); 
		return shape;
	}
	
	@Override
	public void setOrigin(int x, int y) {
		this.px = x;
		this.py = y;
	}

	@Override
	public void setPoint(int x, int y) {
		this.ellipse.setFrameFromDiagonal(px, py, x,  y); 
		if (anchors!=null){ 
			anchors.setBoundingRect(this.ellipse.getBounds());
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
