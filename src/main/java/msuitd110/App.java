package msuitd110;

import org.iq80.leveldb.*;
import static org.iq80.leveldb.impl.Iq80DBFactory.*;
import java.io.*;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {
    
    public static void main(String[] args) {
        DB db = null;        
        try {
            db = connectToDatabase();
            if (args.length > 0) {
                String cmd = args[0];
                if (cmd.equals("list")) {
                    printAllStudents(db);
                } else if (cmd.equals("accept")) {
                    String id = args[1];
                    String name = args[2];
                    acceptStudent(db, id, name);
                    Student student = getStudent(db, id);
                    System.out.println("accepted:" + student.getName());
                }
            }
            db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    public static void acceptStudent(DB db, String studentId, String name)
    {
        Student student = new Student();
        student.setId(studentId);
        student.setName(name);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);   
            out.writeObject(student);
            out.flush();
            byte[] studentObjBytes = bos.toByteArray();
            byte [] key = bytes(studentId);
            db.put(key, studentObjBytes);
        } catch (Exception e) {
			e.printStackTrace();
		}         
    }
    public static Student getStudent(DB db, String studentId)
    {
        Student student = null;
        byte[] studentBytes = db.get(bytes(studentId));
        ObjectInputStream in = null;
        try {
			in = new ObjectInputStream(new ByteArrayInputStream(studentBytes));
            student = (Student)in.readObject();
            
		} catch (Exception e) {
			e.printStackTrace();
		}
        return student;
    }
    public static DB connectToDatabase() throws IOException {
        Options options = new Options();
        options.createIfMissing(true);
        DB db = factory.open(new File("example"), options);
        return db;
    }

    public static void printAllStudents(DB db) 
    {
        DBIterator iterator = db.iterator();
        iterator.seekToFirst();
        while (iterator.hasNext())
        {
            Map.Entry<byte[], byte[]> next = iterator.next();
            ObjectInputStream in = null;
            try {
                byte [] studentBytes = (byte[])next.getValue();
                in = new ObjectInputStream(new ByteArrayInputStream(studentBytes));
                Student student = (Student)in.readObject();
                System.out.println("Name:" + student.getName() + " ID: " + student.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
