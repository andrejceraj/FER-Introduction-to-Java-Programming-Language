package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

class CalcLayoutTest {

	@Test
	void testExceptions() {
		JPanel p = new JPanel(new CalcLayout(3));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("a"), new RCPosition(3,8)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("b"), new RCPosition(0,4)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("c"), new RCPosition(-2,-2)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("d"), new RCPosition(1,2)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("e"), new RCPosition(1,4)));
		p.add(new JLabel("f"), new RCPosition(3,3));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("g"), new RCPosition(3,3)));
	}
	
	@Test
	void testDimensions() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
	
	@Test
	void testDimensions2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

}
