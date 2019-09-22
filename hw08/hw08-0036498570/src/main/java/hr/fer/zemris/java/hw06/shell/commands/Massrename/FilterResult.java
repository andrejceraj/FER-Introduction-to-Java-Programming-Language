package hr.fer.zemris.java.hw06.shell.commands.Massrename;

/**
 * Pattern filtering result
 * 
 * @author Andrej Ceraj
 *
 */
public class FilterResult {
	/**
	 * Groups that matches the patterns
	 */
	String[] groups;

	/**
	 * Creates an instance of {@code FilterResult}.
	 * 
	 * @param groups
	 */
	public FilterResult(String[] groups) {
		this.groups = groups;
	}

	/**
	 * @return number of groups in this {@code FilterResult}.
	 */
	public int numberOfGroups() {
		return groups.length;
	}

	/**
	 * 
	 * @param index
	 * @return group selected by the given index
	 */
	public String group(int index) {
		return groups[index];
	}

	@Override
	public String toString() {
		return groups[0];
	}
}
