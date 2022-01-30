package Shared;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Asker {
    private final Scanner scanner;
    private final PrintStream out;

    public Asker(InputStream in, PrintStream out) {
        scanner = new Scanner(in);
        this.out = out;
    }
    public Asker(Scanner in, PrintStream out){
        scanner = in;
        this.out = out;
    }
    public int askInt(String message) {
        out.println(message);
        return scanner.nextInt();
    }

    public String askString(String message){
        out.println(message);
        return scanner.nextLine();
    }

    public void println(String message){
        System.out.println(message);
    }
}
