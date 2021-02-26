package msuitd110;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.iq80.leveldb.*;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     * 
     * @throws IOException
     */
    @Test
    public void shouldAnswerWithTrue() throws IOException
    {
        DB db = App.connectToDatabase();
        App.acceptStudent(db, "xyz", "test");
        Student student = App.getStudent(db, "xyz");
        assertTrue(student.getName().equals("test"));
    }
}
