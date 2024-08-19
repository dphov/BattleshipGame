import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        int counter = 0;

        // write your code here
        Pattern a = Pattern.compile("^STAR.*");
    for (Secret secret : Secret.values()) {

        Matcher m = a.matcher(secret.name());
        if (m.find()) counter++;
    }

        System.out.println(counter);
    }
}

// sample enum for inspiration
// enum Secret {
//     STAR, CRASH, START, // ...
// }
