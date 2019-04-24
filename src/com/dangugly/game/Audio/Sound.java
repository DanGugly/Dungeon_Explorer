package com.dangugly.game.Audio;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.concurrent.ConcurrentHashMap;

public class Sound {

    public static ConcurrentHashMap<String, AudioClip> sounds;
    public static ConcurrentHashMap<String, String> soundsMap;

    public Sound(){
        sounds = new ConcurrentHashMap<String, AudioClip>();
        soundsMap = new ConcurrentHashMap<String, String>();
        loadClips();
        loadMap();
    }

    public void loadClips(){
        try {
            AudioClip playSound = null;
            playSound = Applet.newAudioClip(getClass().getResource("/Music/Background.wav"));
            sounds.put("background", playSound);
        }catch (Exception e){ System.out.println("Failed loading sounds");}
    }

    public static AudioClip getClip(String  name){
        if(soundsMap.containsKey(name)){
            try {
                return Applet.newAudioClip((Sound.class.getResource(soundsMap.get(name))));
            }catch (Exception e){
                System.out.println("Error loading clip "+name+"...");
            }
        }
        return null;
    }

    public void loadMap(){
        soundsMap.put("background", "/Music/Background.wav");
    }

    public static void playClip(String name){
        if(sounds.containsKey(name)){
            sounds.get(name).play();
        }
    }
}
