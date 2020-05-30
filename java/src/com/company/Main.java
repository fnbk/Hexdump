package com.company;

import java.io.*;

public class Main {

    public static void main (String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: hexdump <dateiname>");
            System.exit(1);
        }

        File file = new File(args[0]);
        if(!file.exists() || file.isDirectory()) {
            System.out.println(String.format("No such file: %s", args[0]));
            System.exit(2);
        }

        int position = 0;
        char[] buffer = new char[16];

        try{
            BufferedReader input = new BufferedReader(new FileReader(file));
            while(position < file.length()) {
                var charsRead = input.read(buffer, 0, buffer.length);
                if (charsRead > 0) {
                    System.out.print(String.format("%s: ", String.format("%04x", position)));
                    position += charsRead;

                    for (int i = 0; i < 16; i++) {
                        if (i < charsRead) {
                            var hex = String.format ("%02x", (int)buffer[i]);
                            System.out.print(hex + " ");
                        } else {
                            System.out.print("  ");
                        }

                        if (i == 7) {
                            System.out.print("-- ");
                        }

                        if (buffer[i] < 32 || buffer[i] > 250) {
                            buffer[i] = (byte) '.';
                        }
                    }

                    var bufferContent = new String(buffer);
                    System.out.println("  " + bufferContent.substring(0, charsRead));
                }
            }
        }
        catch(IOException e)
        {
            System.out.println(e.toString());
        }
    }
}

