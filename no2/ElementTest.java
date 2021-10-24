package no2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ElementTest {
	
	Element e1 =new Element(1,1,2,'*');
	Element e2=new Element(2,1,7,' ');
	@Test
	void testAdd() {
		Element.Arithmetic(e1, e2);
		Element e=new Element(3,3,14,' ');
		assertEquals(e.wholeNumber,e1.wholeNumber);
		assertEquals(e.numerator,e1.numerator);
		assertEquals(e.denominator,e1.denominator);
		assertEquals(e.operator,e1.operator);
		
	}
}
