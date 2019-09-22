package hr.fer.zemris.java.hw05;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.StudentRecord;
import hr.fer.zemris.java.hw05.db.parser.QueryParser;

class ParserFilterExpressionsTest {

	private ConditionalExpression getExampleExpression1() {
		return new ConditionalExpression(FieldValueGetters.JMBAG, "0000000002", ComparisonOperators.EQUALS);
	}

	private ConditionalExpression getExampleExpression2() {
		return new ConditionalExpression(FieldValueGetters.JMBAG, "*0", ComparisonOperators.LIKE);
	}

	private StudentRecord getExapmleRecord1() {
		return new StudentRecord("0000000002", "Ivanić", "Ivan", 3);
	}

	private StudentRecord getExapmleRecord2() {
		return new StudentRecord("00000000020", "Perić", "Pero", 5);
	}

	@Test
	void testConditionalExpressions() {
		ConditionalExpression expression1 = getExampleExpression1();
		ConditionalExpression expression2 = getExampleExpression2();

		assertEquals(FieldValueGetters.JMBAG, expression1.getFieldGetter());
		assertEquals("0000000002", expression1.getStringLiteral());
		assertEquals(ComparisonOperators.EQUALS, expression1.getComparisonOperator());

		assertEquals(FieldValueGetters.JMBAG, expression2.getFieldGetter());
		assertEquals("*0", expression2.getStringLiteral());
		assertEquals(ComparisonOperators.LIKE, expression2.getComparisonOperator());
	}

	@Test
	void testParser1() {
		QueryParser parser = new QueryParser("jmbag = \"0000000002\"");
		List<ConditionalExpression> queryExpressions = parser.getQuery();

		assertEquals(FieldValueGetters.JMBAG, queryExpressions.get(0).getFieldGetter());
		assertEquals("0000000002", queryExpressions.get(0).getStringLiteral());
		assertEquals(ComparisonOperators.EQUALS, queryExpressions.get(0).getComparisonOperator());
	}

	@Test
	void testParser2() {
		QueryParser parser = new QueryParser("jmbag like \"*0\"");
		List<ConditionalExpression> queryExpressions = parser.getQuery();

		assertEquals(FieldValueGetters.JMBAG, queryExpressions.get(0).getFieldGetter());
		assertEquals("*0", queryExpressions.get(0).getStringLiteral());
		assertEquals(ComparisonOperators.LIKE, queryExpressions.get(0).getComparisonOperator());
	}

	@Test
	void testParser3() {
		QueryParser parser = new QueryParser("jmbag = \"0000000002\" and jmbag like \"*0\"");
		List<ConditionalExpression> queryExpressions = parser.getQuery();

		assertEquals(FieldValueGetters.JMBAG, queryExpressions.get(0).getFieldGetter());
		assertEquals("0000000002", queryExpressions.get(0).getStringLiteral());
		assertEquals(ComparisonOperators.EQUALS, queryExpressions.get(0).getComparisonOperator());

		assertEquals(FieldValueGetters.JMBAG, queryExpressions.get(1).getFieldGetter());
		assertEquals("*0", queryExpressions.get(1).getStringLiteral());
		assertEquals(ComparisonOperators.LIKE, queryExpressions.get(1).getComparisonOperator());
	}

	@Test
	void testFilter1() {
		List<ConditionalExpression> expressions = new ArrayList<ConditionalExpression>();
		expressions.add(getExampleExpression1());
		QueryFilter filter = new QueryFilter(expressions); // query like: jmbag = "000000002"

		assertTrue(filter.accepts(getExapmleRecord1()));
		assertFalse(filter.accepts(getExapmleRecord2()));
	}

	@Test
	void testFilter2() {
		List<ConditionalExpression> expressions = new ArrayList<ConditionalExpression>();
		expressions.add(getExampleExpression2());
		QueryFilter filter = new QueryFilter(expressions); // query like: jmbag like "*0"

		assertFalse(filter.accepts(getExapmleRecord1()));
		assertTrue(filter.accepts(getExapmleRecord2()));
	}

	@Test
	void testFilter3() {
		List<ConditionalExpression> expressions = new ArrayList<ConditionalExpression>();
		expressions.add(getExampleExpression1());
		expressions.add(getExampleExpression2());
		QueryFilter filter = new QueryFilter(expressions); // query like: jmbag = "000000002" and jmbag like "*0"

		assertFalse(filter.accepts(getExapmleRecord1()));
		assertFalse(filter.accepts(getExapmleRecord2()));
	}

}
