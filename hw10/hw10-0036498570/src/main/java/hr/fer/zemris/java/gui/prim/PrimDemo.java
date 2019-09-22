package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demonstration of {@link PrimListModel}
 * 
 * @author Andrej Ceraj
 *
 */
public class PrimDemo {
	/**
	 * Method starts when the program is run
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new ListFrame();
			frame.setVisible(true);
		});
	}

	/**
	 * Frame containing two identical lists altered on pressing the button.
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	public static class ListFrame extends JFrame {
		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = 5284854505433168599L;

		/**
		 * Creates an instance of {@link ListFrame}
		 */
		public ListFrame() {
			setLocation(20, 20);
			setSize(200, 300);
			setTitle("PrimeDemo");
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			initGUI();
		}

		/**
		 * Initializes {@link ListFrame} GUI
		 */
		private void initGUI() {
			Container cp = getContentPane();
			cp.setLayout(new BorderLayout());
			PrimListModel listModel = new PrimListModel();

			JList<Integer> list1 = new JList<>(listModel);
			JList<Integer> list2 = new JList<>(listModel);

			JButton nextPrime = new JButton("sljedeći");
			nextPrime.addActionListener(a -> listModel.next());
			cp.add(nextPrime, BorderLayout.SOUTH);

			JPanel central = new JPanel(new GridLayout(1, 2));
			central.add(new JScrollPane(list1));
			central.add(new JScrollPane(list2));
			cp.add(central, BorderLayout.CENTER);
		}
	}
}