package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.interfaces.Week3Reqs;

/**
 * Fill this class in during Week 3. Make sure to also change the week variable in Display.java.
 */
public final class Week3 extends Week3Reqs {

	// AXIS-ALIGNED BOXES
	
	@Override
	public Vec2d collision(AABShape s1, AABShape s2) {
		double left = s2.getTopLeft().x - s1.getTopLeft().x - s1.getSize().x;
		double right = s2.getTopLeft().x + s2.getSize().x - s1.getTopLeft().x;
		double up = s2.getTopLeft().y - s1.getTopLeft().y - s1.getSize().y;
		double down = s2.getTopLeft().y + s2.getSize().y - s1.getTopLeft().y;
		if(left > 0 || right < 0 || up > 0 || down < 0){
			return null;
		}
		if(Math.abs(left) > Math.abs(right)){
			left = right;
		}
		if(Math.abs(up) > Math.abs(down)){
			up = down;
		}
		if(Math.abs(left)<Math.abs(up))
			return new Vec2d(left,0);
		return new Vec2d(0,up);

	}

	@Override
	public Vec2d collision(AABShape s1, CircleShape s2) {
		if(s1.getTopLeft().x <= s2.getCenter().x && s2.getCenter().x <= s1.getTopLeft().x + s1.getSize().x &&
			s1.getTopLeft().y <= s2.getCenter().y && s2.getCenter().y <= s1.getTopLeft().y + s1.getSize().y){

			double x = s2.getCenter().x - s1.getTopLeft().x;
			double y = s2.getCenter().y - s1.getTopLeft().y;

			if(y*s1.getSize().y > x * s1.getSize().x){
				//Left Bottom side
				if(y*s1.getSize().y > (s1.getSize().x - x) * s1.getSize().x){
					//Bottom side
					return new Vec2d(0,y - s1.getSize().x - s2.getRadius());
				} else{
					//Left side
					return new Vec2d(x + s2.getRadius(),0);
				}
			} else{
				//Right Top side
				if(y*s1.getSize().y > (s1.getSize().x - x) * s1.getSize().x){
					//Right side
					return new Vec2d(x - s1.getSize().x - s2.getRadius(),0);
				} else{
					//Top side
					return new Vec2d(0,y + s2.getRadius());
				}
			}
		}

		double x = Math.max(Math.min(s2.getCenter().x,s1.getTopLeft().x + s1.getSize().x),s1.getTopLeft().x);
		double y = Math.max(Math.min(s2.getCenter().y,s1.getTopLeft().y + s1.getSize().y),s1.getTopLeft().y);
		double dx = s2.getCenter().x - x;
		double dy = s2.getCenter().y - y;
		double D = Math.sqrt(dx*dx + dy*dy);
		if(D > s2.getRadius()){
			return null;
		}

		double L = s2.getRadius() - D;
		if(L < 0){
			return null;
		}

		return new Vec2d(-dx * L / D, -dy * L / D);
	}

	@Override
	public Vec2d collision(AABShape s1, Vec2d s2) {
		if(s1.getTopLeft().x > s2.x || s2.x > s1.getTopLeft().x + s1.getSize().x ||
				s1.getTopLeft().y > s2.y || s2.y > s1.getTopLeft().y + s1.getSize().y){
			return null;
		}
		double x = s2.x - s1.getTopLeft().x;
		double y = s2.y - s1.getTopLeft().y;

		if(y*s1.getSize().y > x * s1.getSize().x){
			//Left Bottom side
			if(y*s1.getSize().y > (s1.getSize().x - x) * s1.getSize().x){
				//Bottom side
				return new Vec2d(s1.getSize().y - y,0);
			} else{
				//Left side
				return new Vec2d(0,- x);
			}
		} else{
			//Right Top side
			if(y*s1.getSize().y > (s1.getSize().x - x) * s1.getSize().x){
				//Right side
				return new Vec2d(0,s1.getSize().x - x);
			} else{
				//Top side
				return new Vec2d(- y,0);
			}
		}
	}
	
	// CIRCLES

	@Override
	public Vec2d collision(CircleShape s1, AABShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(CircleShape s1, CircleShape s2) {
		double dx = s1.getCenter().x - s2.getCenter().x;
		double dy = s1.getCenter().y - s2.getCenter().y;
		double D = Math.sqrt(dx*dx + dy*dy);
		if(D == 0){
			return new Vec2d(s1.getRadius() + s2.getRadius(),0);
		}
		double L = s1.getRadius() + s2.getRadius() - D;
		if(L < 0){
			return null;
		}
		return new Vec2d(dx*L/D, dy*L/D);
	}

	@Override
	public Vec2d collision(CircleShape s1, Vec2d s2) {
		double dx = s1.getCenter().x - s2.x;
		double dy = s1.getCenter().y - s2.y;
		double D = Math.sqrt(dx*dx + dy*dy);
		if(D == 0){
			return new Vec2d(s1.getRadius(),0);
		}
		double L = s1.getRadius() - D;
		if(L < 0){
			return null;
		}
		return new Vec2d(dx*L/D, dy*L/D);
	}

}
