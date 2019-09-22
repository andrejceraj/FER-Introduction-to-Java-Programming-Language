package hr.fer.zemris.java.raytracer.model;

/**
 * Representation of intersection between a ray and sphere
 * 
 * @author Andrej Ceraj
 *
 */
public class SphereRayIntersection extends RayIntersection {
	/**
	 * Reference to {@link Sphere}
	 */
	private Sphere sphere;

	/**
	 * Creates an instance of {@link SphereRayIntersection} with the given
	 * parameters.
	 * 
	 * @param point    intersection point
	 * @param distance distance from ray starting point to intersection point
	 * @param outer    true if the intersection is outer; false if it is inner
	 * @param sphere   reference to a sphere
	 */
	public SphereRayIntersection(Point3D point, double distance, boolean outer, Sphere sphere) {
		super(point, distance, outer);
		this.sphere = sphere;
	}

	@Override
	public Point3D getNormal() {
		return sphere.getCenter().sub(getPoint());
	}

	@Override
	public double getKdr() {
		return sphere.getKdr();
	}

	@Override
	public double getKdg() {
		return sphere.getKdg();
	}

	@Override
	public double getKdb() {
		return sphere.getKdb();
	}

	@Override
	public double getKrr() {
		return sphere.getKrr();
	}

	@Override
	public double getKrg() {
		return sphere.getKrg();
	}

	@Override
	public double getKrb() {
		return sphere.getKrb();
	}

	@Override
	public double getKrn() {
		return sphere.getKrn();
	}

}
