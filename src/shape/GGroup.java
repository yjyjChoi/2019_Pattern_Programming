package shape;

import java.awt.BasicStroke;
import java.util.Vector;

public class GGroup extends GRectangle {//rectangle보다 specialize된 클래스
	private static final long serialVersionUID = 1L;
	
	private Vector<GShape> containedShapes;
	protected BasicStroke dashedLineStroke;
	
	public GGroup() {
		this.containedShapes = new Vector<GShape>();
	}

	//override
	public GShape newInstance() {
		return new GGroup();
	}
	
	public void contains(Vector<GShape> shapeVector) {
		for (GShape shape: shapeVector) {
			if (this.getShape().contains(shape.getShape().getBounds())) {
				this.containedShapes.add(shape);
				shape.setSelected(true);
			}
		}
	}
}
