package hr.fer.zemris.java.hw06.shell.commands.Massrename;

import java.util.List;

/**
 * Class containing implementations of some {@link NameBuilder}s.
 * 
 * @author Andrej Ceraj
 *
 */
public abstract class NameBuilders {
	/**
	 * Creates {@link NameBuilder} that appends simple text.
	 * 
	 * @param text
	 * @return Name builder
	 */
	public static NameBuilder text(String text) {
		return (result, sb) -> {
			sb.append(text);
		};
	}

	/**
	 * Creates {@link NameBuilder} that appends group with the given index.
	 * 
	 * @param index group index
	 * @return Name builder
	 */
	public static NameBuilder group(int index) {
		return (result, sb) -> {
			sb.append(result.group(index));
		};
	}

	/**
	 * Creates {@link NameBuilder} that appends group with the given index which
	 * minimal width is set to the given value and if the group width is less than
	 * the minimal width it is padded with the given padding.
	 * 
	 * @param index    group index
	 * @param padding  padding character
	 * @param minWidth minimal width
	 * @return Name builder
	 */
	public static NameBuilder group(int index, char padding, int minWidth) {
		return (result, sb) -> {
			String string = String.format("%" + minWidth + "s", result.group(index)).replace(' ', padding);
			sb.append(string);
		};
	}

	/**
	 * Creates {@link NameBuilder} that contains a list of {@code NameBuilder}s and
	 * it calls method execute for each of them.
	 * 
	 * @param nameBuilders list of {@code NameBuilder}s
	 * @return Name builder
	 */
	public static NameBuilder builder(List<NameBuilder> nameBuilders) {
		return (result, sb) -> {
			nameBuilders.forEach(nb -> nb.execute(result, sb));
		};
	}
}
