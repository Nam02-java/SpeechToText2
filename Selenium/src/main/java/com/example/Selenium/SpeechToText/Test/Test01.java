package com.example.Selenium.SpeechToText.Test;

import javax.sound.sampled.*;
import java.io.*;

public class Test01 {

    public static void main(String[] args) throws IOException {
        String wavFile1 = "E:\\New folder\\mp3-output-ttsfree(dot)com.mp3";
        String wavFile2 = "E:\\New folder\\mp3-output-ttsfree(dot)com (1).mp3";
        String wavFile3 = "E:\\New folder\\mp3-output-ttsfree(dot)com (2).mp3";
        String wavFile4 = "E:\\New folder\\mp3-output-ttsfree(dot)com (3).mp3";

        FileInputStream fistream1 = new FileInputStream(wavFile1);  // first source file
        FileInputStream fistream2 = new FileInputStream(wavFile2);//second source file

        SequenceInputStream sistream = new SequenceInputStream(fistream1, fistream2);
        FileOutputStream fostream = new FileOutputStream("E:\\New folder\\dune.mp3");//destinationfile

        int temp;

        while( ( temp = sistream.read() ) != -1)
        {
            // System.out.print( (char) temp ); // to print at DOS prompt
            fostream.write(temp);   // to write to file
        }
        fostream.close();
        sistream.close();
        fistream1.close();
        fistream2.close();

    }

}




