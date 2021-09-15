package transformer;

import java.awt.Color;
import java.awt.Graphics2D;

public class GMover extends GTransformer {
	
	public GMover() {
	}

	@Override
	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		this.getGShape().initMoving(graphics2d, x, y);
	}

	@Override
	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
		graphics2d.setXORMode(Color.WHITE);
		
		this.getGShape().draw(graphics2d);
		this.getGShape().keepMoving(x, y);
		this.getGShape().draw(graphics2d);
	}

	@Override
	public void finishTransforming(Graphics2D graphics2d, int x, int y) {
		this.getGShape().finishMoving(graphics2d, x, y);
	}

	@Override
	public void continueTransforming(Graphics2D graphics2d, int x, int y) {
		// TODO Auto-generated method stub
		
	}
}
