package hr.fer.zemris.java.raytracer.model;

import java.util.Objects;

/**
 * Representation of sphere in 3D space.
 * 
 * @author Andrej Ceraj
 *
 */
public class Sphere extends GraphicalObject {
	/**
	 * Center point
	 */
	private Point3D center;
	/**
	 * Radius
	 */
	private double radius;
	/**
	 * Red diffusive component
	 */
	private double kdr;
	/**
	 * Green diffusive component
	 */
	private double kdg;
	/**
	 * Blue diffusive component
	 */
	private double kdb;
	/**
	 * Red reflective component
	 */
	private double krr;
	/**
	 * Green reflective component
	 */
	private double krg;
	/**
	 * Blue reflective component
	 */
	private double krb;
	/**
	 * Shininess factor
	 */
	private double krn;

	/**
	 * Creates an instance of {@link Sphere} with the given parameters.
	 * 
	 * @param center Center point
	 * @param radius Radius
	 * @param kdr    Red diffusive component
	 * @param kdg    Green diffusive component
	 * @param kdb    Green diffusive component
	 * @param krr    Red reflective component
	 * @param krg    Green reflective component
	 * @param krb    Blue reflective component
	 * @param krn    Shininess factor
	 * @throws NullPointerException if the center point is null.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		Objects.requireNonNull(center);
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;

	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D direction = ray.direction;
		Point3D start = ray.start;
		double apsoluteDistance = center.sub(start).norm();
		if (apsoluteDistance < radius) {
			return null;
		}
		double ortogonalDistance = direction.scalarProduct(start.sub(center));
		try {
			double innerDistance = Math
					.sqrt(Math.pow(ortogonalDistance, 2) - Math.pow(apsoluteDistance, 2) + radius * radius);
			if (Double.isNaN(innerDistance)) {
				return null;
			}
			double intersectionDistance = Math.min(-ortogonalDistance + innerDistance,
					-ortogonalDistance - innerDistance);
			Point3D intersectionPoint = start.add(direction.scalarMultiply(intersectionDistance));
			boolean outer = true;
			if (apsoluteDistance < radius) {
				outer = false;
			}
			return new SphereRayIntersection(intersectionPoint, intersectionDistance, outer, this);
		} catch (Exception exception) {
			return null;
		}
	}

	/**
	 * @return center point
	 */
	public Point3D getCenter() {
		return center;
	}

	/**
	 * @return radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @return Red diffusive component
	 */
	public double getKdr() {
		return kdr;
	}

	/**
	 * @return Green diffusive component
	 */
	public double getKdg() {
		return kdg;
	}

	/**
	 * @return Blue diffusive component
	 */
	public double getKdb() {
		return kdb;
	}

	/**
	 * @return Red reflective component
	 */
	public double getKrr() {
		return krr;
	}

	/**
	 * @return Green reflective component
	 */
	public double getKrg() {
		return krg;
	}

	/**
	 * @return Blue reflective component
	 */
	public double getKrb() {
		return krb;
	}

	/**
	 * @return Shininess factor
	 */
	public double getKrn() {
		return krn;
	}
}