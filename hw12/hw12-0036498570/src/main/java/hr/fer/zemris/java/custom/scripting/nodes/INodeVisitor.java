package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Offers functionalities for each of the visit methods.
 * 
 * @author Andrej Ceraj
 *
 */
public interface INodeVisitor {

	/**
	 * Does something with {@link TextNode}
	 * 
	 * @param node text node
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Does something with {@link ForLoopNode}
	 * 
	 * @param node for loop node
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Does something with {@link EchoNode}
	 * 
	 * @param node echo node
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Does something with {@link DocumentNode}
	 * 
	 * @param node document node
	 */
	public void visitDocumentNode(DocumentNode node);
}
