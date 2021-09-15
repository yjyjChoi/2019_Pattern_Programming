package shape;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class GRoundRectangle extends GShape {
	private static final long serialVersionUID = 1L;
	
	private java.awt.geom.RoundRectangle2D.Double roundRectangle;
	
	public GRoundRectangle() {
		super();
		this.shape = new java.awt.geom.RoundRectangle2D.Double();
		this.roundRectangle = (java.awt.geom.RoundRectangle2D.Double)this.shape;
	}

	@Override
	public GShape newInstance() {
		return new GRoundRectangle();
	}

	@Override
	public GShape deepCopy() {
		AffineTransform affineTransform = new AffineTransform();
		Shape newShape = affineTransform.createTransformedShape(myShape); 
		GRoundRectangle shape = new GRoundRectangle(); 
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
		this.roundRectangle.setRoundRect(px, py, x-px, y-py, 20, 20);
		
		if (anchors!=null){ 
			anchors.setBoundingRect(this.roundRectangle.getBounds());
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
