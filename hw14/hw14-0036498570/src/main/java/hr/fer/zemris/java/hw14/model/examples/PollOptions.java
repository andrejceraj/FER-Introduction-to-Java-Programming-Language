package hr.fer.zemris.java.hw14.model.examples;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Class containing poll options
 * 
 * @author Andrej Ceraj
 *
 */
public abstract class PollOptions {
	private static List<PollOption> bandOptions;
	private static List<PollOption> movieOptions;

	static {
		bandOptions = new ArrayList<PollOption>();
		bandOptions.add(new PollOption("Prljavo Kazalište", "https://www.youtube.com/watch?v=I1cvQa1sTL4", 0));
		bandOptions.add(new PollOption("Film", "https://www.youtube.com/watch?v=UydP3SA7sbQ", 0));
		bandOptions.add(new PollOption("Bajaga", "https://www.youtube.com/watch?v=OMWijeDSQtM", 0));
		bandOptions.add(new PollOption("Divlje Jagode", "https://www.youtube.com/watch?v=FKCS0VhmyJA", 0));
		bandOptions.add(new PollOption("Opća Opasnost", "https://www.youtube.com/watch?v=lKYL24BenzQ", 0));
		bandOptions.add(new PollOption("Neki to vole vruće", "https://www.youtube.com/watch?v=ITZzn_Vjd34", 0));
		bandOptions.add(new PollOption("Aerodrom", "https://www.youtube.com/watch?v=fjKsRJF2WAs", 0));
		bandOptions.add(new PollOption("Plavi Orkestar", "https://www.youtube.com/watch?v=vr4npNKDIis", 0));
		bandOptions.add(new PollOption("Đavoli", "https://www.youtube.com/watch?v=wghW9z-pqZo", 0));
		bandOptions.add(new PollOption("Poslednja igra leptira", "https://www.youtube.com/watch?v=enKIGMnUNRA", 0));
		bandOptions.add(new PollOption("Denis i Denis", "https://www.youtube.com/watch?v=vh0p2FO3Qaw", 0));
		bandOptions.add(new PollOption("Bijelo Dugme", "https://www.youtube.com/watch?v=CAFE6UZ8DHk", 0));

		movieOptions = new ArrayList<PollOption>();
		movieOptions.add(new PollOption("The Shawshank redemption", "https://www.youtube.com/watch?v=6hB3S9bIaco", 0));
		movieOptions.add(new PollOption("The Lord of the Rings", "https://www.youtube.com/watch?v=V75dMMIW2B4", 0));
		movieOptions.add(new PollOption("The Godfather", "https://www.youtube.com/watch?v=sY1S34973zA", 0));
		movieOptions.add(new PollOption("12 Angry Men", "https://www.youtube.com/watch?v=_13J_9B5jEk", 0));
		movieOptions.add(new PollOption("Schindlers List", "https://www.youtube.com/watch?v=gG22XNhtnoY", 0));
		movieOptions.add(new PollOption("Pulp Fiction", "https://www.youtube.com/watch?v=s7EdQ4FqbhY", 0));
		movieOptions.add(new PollOption("Fight Club", "https://www.youtube.com/watch?v=qtRKdVHc-cE", 0));
	}

	/**
	 * @return band poll options
	 */
	public static List<PollOption> getBandPollOptions() {
		return bandOptions;
	}

	/**
	 * @return movie poll options
	 */
	public static List<PollOption> getMoviePollOptions() {
		return movieOptions;
	}
}