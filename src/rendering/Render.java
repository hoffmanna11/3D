package rendering;

import java.awt.Dimension;
import java.awt.Graphics;

import game.Camera;
import game.Env;
import sub.Vector3D;

public class Render {
	public static int[] getRender(Graphics g, Camera camera, Vector3D point){
		Vector3D diff;

		// Get the difference from the camera to the point
		diff = new Vector3D(
				point.dx() - camera.loc.dx(),
				point.dy() - camera.loc.dy(),
				point.dz() - camera.loc.dz()
				);

		Vector3D mult;

		boolean isVisible = false;
		//int visibleCount = 0;
		mult = Vector3D.getBasisMultiples(camera.orient, diff);
		if(mult.dy() > 0){
			isVisible = true;
			//visibleCount++;
		}

		if(isVisible){
			// Then draw it
		}else{
			// If no visible, no draw
			return null;
		}

		double xyAngle = 30;
		double xzAngle = 45;

		int[] screenLoc = getScreenLoc(mult, xyAngle, xzAngle);

		if(null == screenLoc){
			return null;
		}
		
		return screenLoc;
	}
	
	public static int[] getScreenLoc(Vector3D mults, double xyAngle, double xzAngle){
		double dx = mults.dx();
		double dy = mults.dy();
		double dz = mults.dz();
		xyAngle = Math.toRadians(xyAngle);
		xzAngle = Math.toRadians(xzAngle);
		
		double resXHalf = Env.resWidth / 2;
		double resYHalf = Env.resHeight / 2;
		
		Dimension dim = Env.window.frame.getSize();
		
		if(dim.getWidth() != Env.resWidth){
			Env.resWidth = (int)dim.getWidth();
		}
		if(dim.getHeight() != Env.resHeight){
			Env.resHeight = (int)dim.getHeight();
		}

		if(dy <= 0){
			return null;
		}

		int x = (int) ( resXHalf + ( dx * Math.tan(xyAngle) / dy * resXHalf ) );
		int y = (int) ( resYHalf - ( dz * Math.tan(xzAngle) / dy * resYHalf ) );

		return new int[]{x,y};
	}
}
