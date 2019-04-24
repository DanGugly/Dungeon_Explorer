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
            playSound = Applet.newAudioClip(getClass().getResource("/Music/intro.wav"));
            sounds.put("intro", playSound);
            playSound = Applet.newAudioClip(getClass().getResource("/Music/intromenu.wav"));
            sounds.put("introm", playSound);
            playSound = Applet.newAudioClip(getClass().getResource("/SFX/menuoption.wav"));
            sounds.put("opt", playSound);
            playSound = Applet.newAudioClip(getClass().getResource("/Music/Background.wav"));
            sounds.put("background", playSound);
            playSound = Applet.newAudioClip(getClass().getResource("/SFX/attack_hit.wav"));
            sounds.put("playerhit", playSound);
            playSound = Applet.newAudioClip(getClass().getResource("/SFX/attack_hit_enemy.wav"));
            sounds.put("enemyhit", playSound);
            playSound = Applet.newAudioClip(getClass().getResource("/SFX/enemy_attack.wav"));
            sounds.put("enemyattack", playSound);
            playSound = Applet.newAudioClip(getClass().getResource("/SFX/player_attack.wav"));
            sounds.put("playersword", playSound);
            playSound = Applet.newAudioClip(getClass().getResource("/SFX/player_attack_add.wav"));
            sounds.put("playershout1", playSound);
            playSound = Applet.newAudioClip(getClass().getResource("/SFX/player_attack_add_2.wav"));
            sounds.put("playershout2", playSound);
            playSound = Applet.newAudioClip(getClass().getResource("/SFX/player_attack_add_3.wav"));
            sounds.put("playershout3", playSound);
            playSound = Applet.newAudioClip(getClass().getResource("/SFX/player_attack_add_4.wav"));
            sounds.put("playershout4", playSound);
            playSound = Applet.newAudioClip(getClass().getResource("/SFX/player_death.wav"));
            sounds.put("playerdeath", playSound);
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
        soundsMap.put("intro", "/Music/intro.wav");
        soundsMap.put("introm", "/Music/intromenu.wav");
        soundsMap.put("opt", "/SFX/menuoption.wav");
        soundsMap.put("background", "/Music/Background.wav");
        soundsMap.put("playerhit","/SFX/attack_hit.wav");
        soundsMap.put("enemyhit","/SFX/attack_hit_enemy.wav");
        soundsMap.put("enemyattack","/SFX/enemy_attack.wav");
        soundsMap.put("playersword","/SFX/player_attack.wav");
        soundsMap.put("playershout1","/SFX/player_attack_add.wav");
        soundsMap.put("playershout2","/SFX/player_attack_add_2");
        soundsMap.put("playershout3","/SFX/player_attack_add_3");
        soundsMap.put("playershout4","/SFX/player_attack_add_4");
        soundsMap.put("playerdeath","/SFX/player_death");
    }

    public static void playClip(String name){
        if(sounds.containsKey(name)){
            sounds.get(name).play();
        }
    }
}
