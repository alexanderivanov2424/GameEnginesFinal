package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.interfaces.Week5Reqs;

import java.util.Vector;

/**
 * Fill this class in during Week 5. Make sure to also change the week variable in Display.java.
 */
public final class Week5 extends Week5Reqs {

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

	@Override
	public Vec2d collision(AABShape s1, PolygonShape s2) {

		double smallestOverlap = Double.MAX_VALUE;
		Vec2d axis = null;
		{
			Vec2d proj = new Vec2d(0, 1);
			Vec2d range1 = project(s1, proj);
			Vec2d range2 = project(s2, proj);
			double overlap = getOverlap(range1, range2);
			if (overlap == 0) return null;
			if (Math.abs(overlap) < Math.abs(smallestOverlap)) {
				smallestOverlap = overlap;
				axis = proj;
			}
		}
		{
			Vec2d proj = new Vec2d(1, 0);
			Vec2d range1 = project(s1, proj);
			Vec2d range2 = project(s2, proj);
			double overlap = getOverlap(range1, range2);
			if (overlap == 0) return null;
			if (Math.abs(overlap) < Math.abs(smallestOverlap)) {
				smallestOverlap = overlap;
				axis = proj;
			}
		}
		for(int i = 0; i < s2.getNumPoints(); i++){
			Vec2d proj = s2.points[(i+1)%s2.getNumPoints()].minus(s2.points[i]).perpendicular();
			Vec2d range1 = project(s1,proj);
			Vec2d range2 = project(s2,proj);
			double overlap = getOverlap(range1, range2);
			if(overlap == 0) return null;
			if (Math.abs(overlap) < Math.abs(smallestOverlap)) {
				smallestOverlap = overlap;
				axis = proj;
			}
		}

		return axis.normalize().smult(smallestOverlap);
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

	@Override
	public Vec2d collision(CircleShape s1, PolygonShape s2) {
		double smallestOverlap;
		Vec2d axis = null;
		{
			Vec2d closestPoint = s2.points[0];
			double closestDist = Double.MAX_VALUE;

			for (int i = 0; i < s2.getNumPoints(); i++) {
				double dist = s2.points[i].dist(s1.center);
				if (dist < closestDist) {
					closestDist = dist;
					closestPoint = s2.points[i];
				}
			}
			axis = closestPoint.minus(s1.center);
			Vec2d range2 = project(s2, axis);
			double point = (s1.center).dot(axis) / axis.mag();
			smallestOverlap = getOverlap(new Vec2d(point - s1.radius, point + s1.radius), range2);
			if (smallestOverlap == 0) return null;
		}

		for(int i = 0; i < s2.getNumPoints(); i++){
			Vec2d proj = s2.points[(i+1)%s2.getNumPoints()].minus(s2.points[i]).perpendicular();
			double point = (s1.center).dot(proj) / proj.mag();
			Vec2d range2 = project(s2,proj);
			double overlap = getOverlap(new Vec2d(point - s1.radius, point + s1.radius),range2);
			if(overlap == 0) return null;
			if (Math.abs(overlap) < Math.abs(smallestOverlap)) {
				smallestOverlap = overlap;
				axis = proj;
			}
		}
		return axis.normalize().smult(smallestOverlap);
	}
	
	// POLYGONS

	@Override
	public Vec2d collision(PolygonShape s1, AABShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(PolygonShape s1, CircleShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(PolygonShape s1, Vec2d s2) {
		double smallestOverlap = Double.MAX_VALUE;
		Vec2d axis = null;
		for(int i = 0; i < s1.getNumPoints(); i++) {
			Vec2d proj = s1.points[(i + 1) % s1.getNumPoints()].minus(s1.points[i]).perpendicular();
			Vec2d range1 = project(s1, proj);
			double point = (s2).dot(proj)/proj.mag();
			double overlap = getOverlap(range1, point);
			if(overlap == 0) return null;
			if (Math.abs(overlap) < Math.abs(smallestOverlap)) {
				smallestOverlap = overlap;
				axis = proj;
			}
		}
		return axis.normalize().smult(smallestOverlap);
	}

	@Override
	public Vec2d collision(PolygonShape s1, PolygonShape s2) {
		double smallestOverlap = Double.MAX_VALUE;
		Vec2d axis = null;
		for(int i = 0; i < s1.getNumPoints(); i++){
			Vec2d proj = s1.points[(i+1)%s1.getNumPoints()].minus(s1.points[i]).perpendicular();
			Vec2d range1 = project(s1,proj);
			Vec2d range2 = project(s2,proj);
			double overlap = getOverlap(range1, range2);
			if(overlap == 0) return null;
			if (Math.abs(overlap) < Math.abs(smallestOverlap)) {
				smallestOverlap = overlap;
				axis = proj;
			}
		}
		for(int i = 0; i < s2.getNumPoints(); i++){
			Vec2d proj = s2.points[(i+1)%s2.getNumPoints()].minus(s2.points[i]).perpendicular();
			Vec2d range1 = project(s1,proj);
			Vec2d range2 = project(s2,proj);
			double overlap = getOverlap(range1, range2);
			if(overlap == 0) return null;
			if (Math.abs(overlap) < Math.abs(smallestOverlap)) {
				smallestOverlap = overlap;
				axis = proj;
			}
		}

		return axis.normalize().smult(smallestOverlap);
	}

	private Vec2d project(PolygonShape s1, Vec2d axis){
		double min = Double.MAX_VALUE;
		double max = -Double.MAX_VALUE;
		double L = axis.mag();
		for(Vec2d p : s1.points){
			double projection = p.dot(axis)/L;
			min = Math.min(projection,min);
			max = Math.max(projection,max);
		}
		return new Vec2d(min,max);
	}

	private Vec2d project(AABShape s1, Vec2d axis){
		double min = Double.MAX_VALUE;
		double max = -Double.MAX_VALUE;
		double L = axis.mag();
		double projection = 0;
		projection = (s1.topLeft).dot(axis)/L;
		min = Math.min(projection,min);
		max = Math.max(projection,max);

		projection = (s1.topLeft.plus(new Vec2d(s1.getSize().x,0))).dot(axis)/L;
		min = Math.min(projection,min);
		max = Math.max(projection,max);

		projection = (s1.topLeft.plus(new Vec2d(0,s1.getSize().y))).dot(axis)/L;
		min = Math.min(projection,min);
		max = Math.max(projection,max);

		projection = (s1.topLeft.plus(s1.getSize())).dot(axis)/L;
		min = Math.min(projection,min);
		max = Math.max(projection,max);

		return new Vec2d(min,max);
	}

	private double getOverlap(Vec2d range1, Vec2d range2){
		double left = range2.x - range1.y;
		double right = range2.y - range1.x;
		if(left >= 0 || right <= 0) return 0;
		return Math.abs(left) < Math.abs(right) ? left : right;
	}

	private double getOverlap(Vec2d range1, double val){
		double left = val - range1.y;
		double right = val - range1.x;
		if(left >= 0 || right <= 0) return 0;
		return Math.abs(left) < Math.abs(right) ? left : right;
	}
	
}
