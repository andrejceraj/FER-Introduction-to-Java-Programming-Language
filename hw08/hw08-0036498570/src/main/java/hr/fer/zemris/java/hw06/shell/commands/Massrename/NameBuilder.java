package hr.fer.zemris.java.hw06.shell.commands.Massrename;

/**
 * Class used for building names. It appends certain string to string builder
 * based on the class type.
 * 
 * @author Andrej Ceraj
 *
 */
public interface NameBuilder {
	/**
	 * Appends certain part of the {@link FilterResult} to the given
	 * {@code StringBuilder}.
	 * 
	 * @param result Filter Result
	 * @param sb     String builder
	 */
	public void execute(FilterResult result, StringBuilder sb);
}
