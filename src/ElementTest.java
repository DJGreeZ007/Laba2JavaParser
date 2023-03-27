import static org.junit.jupiter.api.Assertions.*;

class ElementTest {

    @org.junit.jupiter.api.Test
    void testGetType() {
        Element element = new Element(TypeExpressions.NUMBER, 20);
        assertEquals(element.getType(), TypeExpressions.NUMBER);
        element = new Element(TypeExpressions.MUL);
        assertEquals(element.getType(), TypeExpressions.MUL);
        element = new Element(TypeExpressions.DIV);
        assertEquals(element.getType(), TypeExpressions.DIV);
        element = new Element(TypeExpressions.LEFT_BRACKET);
        assertEquals(element.getType(), TypeExpressions.LEFT_BRACKET);
        element = new Element(TypeExpressions.RIGHT_BRACKET);
        assertEquals(element.getType(), TypeExpressions.RIGHT_BRACKET);
    }

    @org.junit.jupiter.api.Test
    void testGetValue() {
        Element element = new Element(TypeExpressions.NUMBER, 200);
        assertEquals(element.getValue(), 200);
        element = new Element(TypeExpressions.NUMBER, -100);
        assertEquals(element.getValue(), -100);
        element = new Element(TypeExpressions.LEFT_BRACKET);
        assertEquals(element.getValue(), 0.0);
        element = new Element(TypeExpressions.RIGHT_BRACKET);
        assertEquals(element.getValue(), 0.0);
    }
}