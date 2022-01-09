package daily.programmer.utility;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTrace {
    public static String asString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
