package Launcher;


import java.io.IOException;


public class Launcher {





    public static void main (String[] args) throws IOException {

        LauncherAssistant assistant = new LauncherAssistant();

        assistant.readAndRun("test.txt");


    }

}
