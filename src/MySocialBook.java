import java.io.IOException;
import java.text.ParseException;

public class MySocialBook {


    public static void main(String[] args) throws IOException, ParseException {
        User.read(); // reads the user.txt
        Command.read(); // reads the command.txt
    }
}