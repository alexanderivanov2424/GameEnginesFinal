package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.interfaces.Week6Reqs;

import debugger.collisions.Week5.*;

/**
 * Fill this class in during Week 6. Make sure to also change the week variable in Display.java.
 */
public final class Week6 extends Week6Reqs {

	// AXIS-ALIGNED BOXES
	
	@Override
	public Vec2d collision(AABShape s1, AABShape s2) {
		return (new Week5()).collision(s1,s2);
	}

	@Override
	public Vec2d collision(AABShape s1, CircleShape s2) {
		return (new Week5()).collision(s1,s2);
	}

	@Override
	public Vec2d collision(AABShape s1, Vec2d s2) {
		return (new Week5()).collision(s1,s2);
	}

	@Override
	public Vec2d collision(AABShape s1, PolygonShape s2) {
		return (new Week5()).collision(s1,s2);
	}

	// CIRCLES
	
	@Override
	public Vec2d collision(CircleShape s1, AABShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(CircleShape s1, CircleShape s2) {
		return (new Week5()).collision(s1,s2);
	}

	@Override
	public Vec2d collision(CircleShape s1, Vec2d s2) {
		return (new Week5()).collision(s1,s2);
	}

	@Override
	public Vec2d collision(CircleShape s1, PolygonShape s2) {
		return (new Week5()).collision(s1,s2);
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
		return (new Week5()).collision(s1,s2);
	}

	@Override
	public Vec2d collision(PolygonShape s1, PolygonShape s2) {
		return (new Week5()).collision(s1,s2);
	}
	
	// RAYCASTING
	
	@Override
	public float raycast(AABShape s1, Ray s2) {
		Vec2d a = s1.getTopLeft();
		Vec2d b = s1.getTopLeft().plus(new Vec2d(s1.getSize().x,0));
		Vec2d c = s1.getTopLeft().plus(s1.getSize());
		Vec2d d = s1.getTopLeft().plus(new Vec2d(0,s1.getSize().y));
		double min_dist = Double.MAX_VALUE;
		{
			double t = raycast(a, b, s2);
			if(t > 0) min_dist = Math.min(t,min_dist);
		}
		{
			double t = raycast(b, c, s2);
			if(t > 0) min_dist = Math.min(t,min_dist);
		}
		{
			double t = raycast(c, d, s2);
			if(t > 0) min_dist = Math.min(t,min_dist);
		}
		{
			double t = raycast(d, a, s2);
			if(t > 0) min_dist = Math.min(t,min_dist);
		}
		if(min_dist == Double.MAX_VALUE) return -1;
		return (float)min_dist;
	}
	
	@Override
	public float raycast(CircleShape s1, Ray s2) {
		Vec2d pnt = s1.getCenter().minus(s2.src).projectOnto(s2.dir);
		if(pnt.dot(s2.dir) < 0) return -1;
		double l = pnt.mag();
		double d = pnt.dist(s1.getCenter().minus(s2.src));
		if(d > s1.getRadius()) return -1;
		if( pnt.minus(s1.getCenter().minus(s2.src)).mag() > s1.getRadius())
			return (float)(l + Math.sqrt(s1.radius*s1.radius - d*d)); //outside
		return (float)(l -Math.sqrt(s1.radius*s1.radius - d*d)); //inside
	}
	
	@Override
	public float raycast(PolygonShape s1, Ray s2) {
		double min_dist = Double.MAX_VALUE;
		for(int i = 0; i < s1.points.length; i++){
			double t = raycast(s1.getPoint(i), s1.getPoint((i+1)%s1.points.length), s2);
			if(t < 0){
				continue;
			}
			min_dist = Math.min(t,min_dist);
		}
		return (float)min_dist;
	}

	private double raycast(Vec2d a, Vec2d b, Ray s2){
		Vec2d b_minus_p = a.minus(s2.src);
		Vec2d a_minus_p = b.minus(s2.src);
		if(b_minus_p.cross(s2.dir)*a_minus_p.cross(s2.dir) > 0) return -1;
		Vec2d n = b.minus(a).perpendicular();
		double t = b_minus_p.dot(n) / s2.dir.dot(n);
		if(t < 0) return -1;
		return t;
	}

}
