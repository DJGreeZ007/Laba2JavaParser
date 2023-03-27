import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.MarshalException;

import static org.junit.jupiter.api.Assertions.*;

class MathParserTest {

    @Test
    void mathParser() throws NoSuchMethodException {
        MathParser parser = new MathParser();
        assertEquals(parser.mathParser("-(5446-645*(5613*641/3422)/(53421-(56431/434)))"), -5433.2743750978725);
        assertEquals(parser.mathParser("-(5446-645*(5613*641/3422)/(53421-(56431/434))"), 0);
        System.setIn(new ByteArrayInputStream("12324\n".getBytes()));
        assertEquals(parser.mathParser("-(a-645*(5613*641/3422)/(53421-(56431/434)))"), -12311.274375097872);
        System.setIn(new ByteArrayInputStream("12324\n".getBytes()));
        System.setIn(new ByteArrayInputStream("123.234\n".getBytes()));
        assertEquals(parser.mathParser("-(a-645*(5613*641/3422)/(53421-(asdf/434)))"), -12311.274375097872);

    }
}