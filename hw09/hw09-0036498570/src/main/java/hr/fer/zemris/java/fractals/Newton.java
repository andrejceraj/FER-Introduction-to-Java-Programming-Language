package hr.fer.zemris.java.fractals;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.*;

/**
 * Program that draws and colors fractals derived from Newton-Raphson iteration.
 * 
 * @author Andrej Ceraj
 *
 */
public class Newton {
	/**
	 * Method starts when the program is run.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n"
				+ "Please enter at least two roots, one root per line. Enter 'done' when done.");
		Complex[] roots = NewtonUtil.getInput();
		System.out.println("Image of fractal will appear shortly. Thank you.");

		FractalViewer.show(new FractalProducer(roots));
	}

	/**
	 * Class used for producing fractals.
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	public static class FractalProducer implements IFractalProducer {
		private static final int MAX_ITERATIONS = 256;
		private ExecutorService pool;
		private ComplexRootedPolynomial complexRootedPolynomial;
		private ComplexPolynomial complexPolynomial;
		private int availableProcessors = Runtime.getRuntime().availableProcessors();

		/**
		 * Creates an instance of {@link FractalProducer} with the given root points of
		 * complex polynomial.
		 * 
		 * @param roots complex polynomial roots
		 */
		public FractalProducer(Complex[] roots) {
			this.complexRootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, roots);
			this.complexPolynomial = complexRootedPolynomial.toComplexPolynom();
			this.pool = Executors.newFixedThreadPool(availableProcessors, new DaemonicThreadFactory());
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Calculating...");
			short[] data = new short[height * width];
			int numberOfJobs = 8 * availableProcessors;
			int linesPerJob = height / numberOfJobs;
			List<Future<Void>> results = new ArrayList<Future<Void>>();

			for (int i = 0; i < numberOfJobs; i++) {
				int yMin = i * linesPerJob;
				int yMax = i == numberOfJobs - 1 ? height - 1 : (i + 1) * linesPerJob;
				Job job = new Job(reMin, reMax, imMin, imMax, width, height, yMin, yMax, MAX_ITERATIONS, data,
						complexRootedPolynomial);
				results.add(pool.submit(job));
			}

			for (Future<Void> job : results) {
				while (true) {
					try {
						job.get();
						break;
					} catch (InterruptedException | ExecutionException e) {
					}
				}
			}
			observer.acceptResult(data, (short) (complexPolynomial.order() + 1), requestNo);
		}

		/**
		 * Class used for creating threads with daemon set to true.
		 *
		 * @author Andrej Ceraj
		 *
		 */
		private class DaemonicThreadFactory implements ThreadFactory {
			@Override
			public Thread newThread(Runnable r) {
				Thread thread = Executors.defaultThreadFactory().newThread(r);
				thread.setDaemon(true);
				return thread;
			}
		}
	}

	/**
	 * Representation of a job which threads should do. Job involve determining
	 * pixel colors the user will see.
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	public static class Job implements Callable<Void> {
		private static final double ROOT_THRESHOLD = 0.002;
		private static final double CONVERGENCE_THRESHOLD = 0.001;
		private double reMin;
		private double reMax;
		private double imMin;
		private double imMax;
		private int width;
		private int height;
		private int yMin;
		private int yMax;
		private int maxIter;
		private short[] data;
		private ComplexRootedPolynomial complexRootedPolynomial;
		private ComplexPolynomial complexPolynomial;

		/**
		 * Creates an instance of {@link Job} with the given parameters.
		 * 
		 * @param reMin
		 * @param reMax
		 * @param imMin
		 * @param imMax
		 * @param width
		 * @param height
		 * @param yMin
		 * @param yMax
		 * @param maxIter
		 * @param data
		 * @param complexRootedPolynomial
		 */
		public Job(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin, int yMax,
				int maxIter, short[] data, ComplexRootedPolynomial complexRootedPolynomial) {
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.maxIter = maxIter;
			this.data = data;
			this.complexRootedPolynomial = complexRootedPolynomial;
			this.complexPolynomial = complexRootedPolynomial.toComplexPolynom();
		}

		@Override
		public Void call() throws Exception {
			int offset = yMin * width;
			int xMin = 0;
			int xMax = width;

			for (int y = yMin; y < yMax; y++) {
				for (int x = xMin; x < xMax; x++) {
					Complex c = mapToComplexPlain(x, y, 0, width, yMin, yMax, reMin, reMax, imMin, imMax, width,
							height);
					Complex zn = new Complex(c.getReal(), c.getImaginary());
					int iter = 0;
					double module = 1;
					do {
						Complex znOld = new Complex(zn.getReal(), zn.getImaginary());
						Complex numerator = complexRootedPolynomial.apply(zn);
						Complex denominator = complexPolynomial.derive().apply(zn);
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znOld.sub(zn).module();
						iter++;
					} while (module > CONVERGENCE_THRESHOLD && iter < maxIter);

					int index = complexRootedPolynomial.indexOfClosestRootFor(zn, ROOT_THRESHOLD);
					data[offset++] = (short) (index + 1);
				}
			}
			return null;
		}

		/**
		 * Maps a complex number based on the given parameters.
		 * 
		 * @param x
		 * @param y
		 * @param xMin
		 * @param xMax
		 * @param yMin
		 * @param yMax
		 * @param reMin
		 * @param reMax
		 * @param imMin
		 * @param imMax
		 * @param width
		 * @param height
		 * @return complex number
		 */
		private Complex mapToComplexPlain(double x, double y, double xMin, double xMax, double yMin, double yMax,
				double reMin, double reMax, double imMin, double imMax, int width, int height) {

			double real = x / (width - 1.0) * (reMax - reMin) + reMin;
			double imaginary = (height - y - 1.0) / (height - 1.0) * (imMax - imMin) + imMin;
			return new Complex(real, imaginary);
		}
	}
}
