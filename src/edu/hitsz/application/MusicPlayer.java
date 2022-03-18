package edu.hitsz.application;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class MusicPlayer{
    public static Thread playMusic(String file){
        Thread thread=null;
        thread=new Thread(()->{
            try {
                while(true){
                    File f=new File(file);
                    AudioClip aau= Applet.newAudioClip(f.toURL());
                    aau.play();
                    System.out.println("播放了");
                }
            }catch (Exception  e){
                e.printStackTrace();
            }
        });
        return thread;
    }

    public static void playshortMusic(String file){
        new Thread(()->{
            File f=new File(file);
            AudioClip aau= null;
            try {
                aau = Applet.newAudioClip(f.toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            aau.play();
        }).start();
    }
}
