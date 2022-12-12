package org.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class MainFileTest {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        FileInputStream fileIn =
                new FileInputStream(
                        path);
        FileOutputStream fileOut =
                new FileOutputStream(
                        "src/main/java/org/example/files/test2.txt");

        while (fileIn.available() > 0) {
            int oneByte = fileIn.read();
            fileOut.write(oneByte);
        }
    }
}
