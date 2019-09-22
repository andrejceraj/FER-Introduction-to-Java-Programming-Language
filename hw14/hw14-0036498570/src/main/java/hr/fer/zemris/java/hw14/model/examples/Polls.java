package hr.fer.zemris.java.hw14.model.examples;

import hr.fer.zemris.java.hw14.model.Poll;

/**
 * Class containing polls
 * 
 * @author Andrej Ceraj
 *
 */
public abstract class Polls {
	private static Poll bandPoll;
	private static Poll moviePoll;

	static {
		bandPoll = new Poll("Glasanje za omiljeni bend:",
				"Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!");

		moviePoll = new Poll("Glasanje za omiljeni film:",
				"Od sljedećih filmova, koji Vam je film najdraži? Kliknite na link kako biste glasali!");
	}

	/**
	 * @return band poll
	 */
	public static Poll getBandPoll() {
		return bandPoll;
	}

	/**
	 * @return movie poll
	 */
	public static Poll getMoviePoll() {
		return moviePoll;
	}

}
