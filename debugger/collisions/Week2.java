package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.interfaces.Week2Reqs;

/**
 * Fill this class in during Week 2.
 */
public final class Week2 extends Week2Reqs {

	// AXIS-ALIGNED BOXES
	
	@Override
	public boolean isColliding(AABShape s1, AABShape s2) {
		if(s1.getTopLeft().x + s1.getSize().x < s2.getTopLeft().x){
			return false;
		}
		if(s2.getTopLeft().x + s2.getSize().x < s1.getTopLeft().x){
			return false;
		}
		if(s1.getTopLeft().y + s1.getSize().y < s2.getTopLeft().y){
			return false;
		}
		if(s2.getTopLeft().y + s2.getSize().y < s1.getTopLeft().y){
			return false;
		}
		return true;
	}

	@Override
	public boolean isColliding(AABShape s1, CircleShape s2) {
		double P_x = Math.min(s2.getCenter().x,s1.getTopLeft().x + s1.getSize().x);
		P_x = Math.max(P_x,s1.getTopLeft().x);
		double P_y = Math.min(s2.getCenter().y,s1.getTopLeft().y + s1.getSize().y);
		P_y = Math.max(P_y,s1.getTopLeft().y);
		return isColliding(s2,new Vec2d(P_x,P_y));
	}

	@Override
	public boolean isColliding(AABShape s1, Vec2d s2) {
		if(s2.x < s1.getTopLeft().x || s2.x > s1.getTopLeft().x + s1.getSize().x){
			return false;
		}
		if(s2.y < s1.getTopLeft().y || s2.y > s1.getTopLeft().y + s1.getSize().y){
			return false;
		}
		return true;
	}

	// CIRCLES
	
	@Override
	public boolean isColliding(CircleShape s1, AABShape s2) {
		return isColliding(s2, s1);
	}

	@Override
	public boolean isColliding(CircleShape s1, CircleShape s2) {
		double dx = s1.getCenter().x - s2.getCenter().x;
		double dy = s1.getCenter().y - s2.getCenter().y;
		return (s1.radius + s2.radius)*(s1.radius + s2.radius) > dx*dx + dy*dy;
	}

	@Override
	public boolean isColliding(CircleShape s1, Vec2d s2) {
		double dx = s1.getCenter().x - s2.x;
		double dy = s1.getCenter().y - s2.y;
		return s1.radius*s1.radius > dx*dx + dy*dy;
	}

	
}
