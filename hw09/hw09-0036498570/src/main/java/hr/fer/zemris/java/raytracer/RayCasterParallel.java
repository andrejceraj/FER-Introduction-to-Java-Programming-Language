package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program showing interaction of lots of small spheres and 2 big spheres with
 * two sources of light. Calculations are done using parallel threading
 * 
 * @author Andrej Ceraj
 *
 */
public class RayCasterParallel {
	/**
	 * Method starts when the program is run.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Creates and returns an implementation of {@link IRayTracerProducer}.
	 * 
	 * @return implementation of IRayTracerProducer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D direction = view.sub(eye).normalize(); // OG vector
				Point3D zAxis = direction.normalize();
				Point3D upVector = viewUp.normalize(); // VUV vector
				double scalarProduct = direction.scalarProduct(upVector);
				Point3D yAxis = upVector.sub(direction.scalarMultiply(scalarProduct)).normalize();
				Point3D xAxis = direction.vectorProduct(yAxis).normalize();

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();

				short[] rgb = new short[3];
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new Job(xAxis, yAxis, horizontal, vertical, width, height, screenCorner, scene, rgb, eye,
						red, green, blue, 0, height));
				pool.shutdown();
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

	/**
	 * Representation of a job which threads should do. Job involve determining
	 * pixel colors the user will see.
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	public static class Job extends RecursiveAction {
		private static final long serialVersionUID = 1L;
		private static final int TRESHOLD = 16;
		private Point3D xAxis;
		private Point3D yAxis;
		private double horizontal;
		private double vertical;
		private int width;
		private int height;
		private Point3D screenCorner;
		private Scene scene;
		private short[] rgb;
		private Point3D eye;
		private short[] red;
		private short[] green;
		private short[] blue;
		private int yMin;
		private int yMax;

		/**
		 * Creates an instance of a Job with the given parameters
		 * 
		 * @param xAxis
		 * @param yAxis
		 * @param horizontal
		 * @param vertical
		 * @param width
		 * @param height
		 * @param screenCorner
		 * @param scene
		 * @param rgb
		 * @param eye
		 * @param red
		 * @param green
		 * @param blue
		 * @param yMin
		 * @param yMax
		 */
		public Job(Point3D xAxis, Point3D yAxis, double horizontal, double vertical, int width, int height,
				Point3D screenCorner, Scene scene, short[] rgb, Point3D eye, short[] red, short[] green, short[] blue,
				int yMin, int yMax) {
			super();
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.width = width;
			this.height = height;
			this.screenCorner = screenCorner;
			this.scene = scene;
			this.rgb = rgb;
			this.eye = eye;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.yMin = yMin;
			this.yMax = yMax;
		}

		@Override
		public void compute() {
			if (yMax - yMin < TRESHOLD) {
				computeDirect();
				return;
			} else {
				int yMiddle = yMin + (yMax - yMin) / 2 + 1;
				invokeAll(
						new Job(xAxis, yAxis, horizontal, vertical, width, height, screenCorner, scene, rgb, eye, red,
								green, blue, yMin, yMiddle),
						new Job(xAxis, yAxis, horizontal, vertical, width, height, screenCorner, scene, rgb, eye, red,
								green, blue, yMiddle, yMax));
			}
		}

		/**
		 * Determines pixel colors of rows yMin to yMax.
		 */
		private void computeDirect() {
			int offset = yMin * width;
			for (int y = yMin; y < yMax; y++) {
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(horizontal * x / (width - 1.0)))
							.sub(yAxis.scalarMultiply(vertical * y / (height - 1.0)));
					Ray ray = Ray.fromPoints(eye, screenPoint);

					tracer(scene, ray, rgb);

					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

					offset++;
				}
			}
		}

		/**
		 * Associates the right color with the given ray in the given scene.
		 * 
		 * @param scene scene
		 * @param ray   ray
		 * @param rgb   red, green and blue component
		 */
		private void tracer(Scene scene, Ray ray, short[] rgb) {
			double[] rgbIntersection = new double[3];
			RayIntersection closestIntersection = closestIntersection(scene, ray);
			if (closestIntersection == null) {
				rgb[0] = 0;
				rgb[1] = 0;
				rgb[2] = 0;
				return;
			}
			rgbIntersection = determineColorFor(closestIntersection, scene, ray);
			rgb[0] = (short) rgbIntersection[0];
			rgb[1] = (short) rgbIntersection[1];
			rgb[2] = (short) rgbIntersection[2];
		}

		/**
		 * Finds the closest intersection between the ray and the object(s) in the
		 * scene.
		 * 
		 * @param scene scene
		 * @param ray   ray
		 * @return closest ray intersection
		 */
		private RayIntersection closestIntersection(Scene scene, Ray ray) {
			List<GraphicalObject> objects = scene.getObjects();
			RayIntersection closestIntersection = null;

			for (GraphicalObject object : objects) {
				RayIntersection intersection = object.findClosestRayIntersection(ray);
				if (intersection != null) {
					if (closestIntersection == null || intersection.getDistance() < closestIntersection.getDistance()) {
						closestIntersection = intersection;
					}
				}
			}
			return closestIntersection;
		}

		/**
		 * Determines the color for the given ray in the given scene.
		 * 
		 * @param closestIntersection closest ray intersection
		 * @param scene               scene
		 * @param ray                 ray
		 * @return values of red, green and blue components
		 */
		private double[] determineColorFor(RayIntersection closestIntersection, Scene scene, Ray ray) {
			List<LightSource> lights = scene.getLights();
			double[] rgb = new double[] { 15, 15, 15 };

			for (LightSource light : lights) {
				Ray lightRay = Ray.fromPoints(light.getPoint(), closestIntersection.getPoint());
				RayIntersection lightIntersection = closestIntersection(scene, lightRay);

				double lightToObjectDistance = lightRay.start.sub(closestIntersection.getPoint()).norm();

				if (lightIntersection == null) {
					continue;
				} else if (Math
						.abs(lightToObjectDistance - lightRay.start.sub(lightIntersection.getPoint()).norm()) >= 1e-6) {
					continue;
				}

				addDifusiveComponent(rgb, light, lightIntersection);
				addReflectComponent(rgb, light, lightIntersection, ray);

			}
			return rgb;
		}

		/**
		 * Adds diffusive component to red, green and blue values.
		 * 
		 * @param rgb               red, green and blue values
		 * @param light             source of light
		 * @param lightIntersection light ray intersection
		 */
		private void addDifusiveComponent(double[] rgb, LightSource light, RayIntersection lightIntersection) {
			Point3D l = lightIntersection.getPoint().sub(light.getPoint()).normalize();
			Point3D n = lightIntersection.getNormal().normalize();

			double cosFi = n.scalarProduct(l);
			cosFi = Math.max(cosFi, 0);

			rgb[0] += light.getR() * lightIntersection.getKdr() * cosFi;
			rgb[1] += light.getG() * lightIntersection.getKdg() * cosFi;
			rgb[2] += light.getB() * lightIntersection.getKdb() * cosFi;
		}

		/**
		 * Adds reflect component to red, green and blue values.
		 * 
		 * @param rgb               red, green and blue values
		 * @param light             source of light
		 * @param lightIntersection light ray intersection
		 * @param ray               viewer's ray
		 */
		private void addReflectComponent(double[] rgb, LightSource light, RayIntersection lightIntersection, Ray ray) {
			Point3D n = lightIntersection.getNormal().normalize();
			Point3D l = lightIntersection.getPoint().sub(light.getPoint()).normalize();
			Point3D v = lightIntersection.getPoint().sub(ray.start).normalize();
			Point3D r = l.negate().sub(n.scalarMultiply(l.negate().scalarProduct(n) * 2));

			double cosFi = v.scalarProduct(r);
			cosFi = Math.max(cosFi, 0);

			rgb[0] += light.getR() * lightIntersection.getKrr() * Math.pow(cosFi, lightIntersection.getKrn());
			rgb[1] += light.getG() * lightIntersection.getKrg() * Math.pow(cosFi, lightIntersection.getKrn());
			rgb[2] += light.getB() * lightIntersection.getKrb() * Math.pow(cosFi, lightIntersection.getKrn());
		}

	}

}