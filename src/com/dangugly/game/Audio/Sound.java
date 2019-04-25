package com.dangugly.game.Audio;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
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
            playSound = Applet.newAudioClip(new File("res/Music/intro.wav").toURI().toURL());
            sounds.put("intro", playSound);
            playSound = Applet.newAudioClip(new File("res/Music/intromenu.wav").toURI().toURL());
            sounds.put("introm", playSound);
            playSound = Applet.newAudioClip(new File("res/Music/game_win.wav").toURI().toURL());
            sounds.put("win", playSound);
            playSound = Applet.newAudioClip(new File("res/Music/game_lose.wav").toURI().toURL());
            sounds.put("lose", playSound);
            playSound = Applet.newAudioClip(new File("res/SFX/ally_saved.wav").toURI().toURL());
            sounds.put("opt", playSound);
            playSound = Applet.newAudioClip(new File("res/Music/Background.wav").toURI().toURL());
            sounds.put("background", playSound);
            playSound = Applet.newAudioClip(new File("res/SFX/attack_hit.wav").toURI().toURL());
            sounds.put("playerhit", playSound);
            playSound = Applet.newAudioClip(new File("res/SFX/attack_hit_enemy.wav").toURI().toURL());
            sounds.put("enemyhit", playSound);
            playSound = Applet.newAudioClip(new File("res/SFX/enemy_attack.wav").toURI().toURL());
            sounds.put("enemyattack", playSound);
            playSound = Applet.newAudioClip(new File("res/SFX/player_attack.wav").toURI().toURL());
            sounds.put("playersword", playSound);
            playSound = Applet.newAudioClip(new File("res/SFX/player_attack_add.wav").toURI().toURL());
            sounds.put("playershout1", playSound);
            playSound = Applet.newAudioClip(new File("res/SFX/player_attack_add_2.wav").toURI().toURL());
            sounds.put("playershout2", playSound);
            playSound = Applet.newAudioClip(new File("res/SFX/player_attack_add_3.wav").toURI().toURL());
            sounds.put("playershout3", playSound);
            playSound = Applet.newAudioClip(new File("res/SFX/player_attack_add_4.wav").toURI().toURL());
            sounds.put("playershout4", playSound);
            playSound = Applet.newAudioClip(new File("res/SFX/player_death.wav").toURI().toURL());
            sounds.put("playerdeath", playSound);
            playSound = Applet.newAudioClip(new File("res/SFX/running.wav").toURI().toURL());
            sounds.put("run", playSound);
        }catch (Exception e){ System.out.println("Failed loading sounds");}
    }

    public static AudioClip getClip(String  name){
        if(soundsMap.containsKey(name)){
            try {
                return sounds.get(name);
            }catch (Exception e){
                System.out.println("Error loading clip "+name+"...");
            }
        }
        return null;
    }

    public void loadMap(){
        soundsMap.put("win","/res/Music/game_win.wav");
        soundsMap.put("lose","/res/Music/game_lose.wav");
        soundsMap.put("intro", "/res/Music/intro.wav");
        soundsMap.put("introm", "/res/Music/intromenu.wav");
        soundsMap.put("opt", "/res/SFX/ally_saved.wav");
        soundsMap.put("background", "/res/Music/Background.wav");
        soundsMap.put("playerhit","/res/SFX/attack_hit.wav");
        soundsMap.put("enemyhit","/res/SFX/attack_hit_enemy.wav");
        soundsMap.put("enemyattack","/res/SFX/enemy_attack.wav");
        soundsMap.put("playersword","/res/SFX/player_attack.wav");
        soundsMap.put("playershout1","/res/SFX/player_attack_add.wav");
        soundsMap.put("playershout2","/res/SFX/player_attack_add_2.wav");
        soundsMap.put("playershout3","/res/SFX/player_attack_add_3.wav");
        soundsMap.put("playershout4","/res/SFX/player_attack_add_4.wav");
        soundsMap.put("playerdeath","/res/SFX/player_death.wav");
        soundsMap.put("run","/res/SFX/running.wav");
    }

    public static void playClip(String name){
        if(sounds.containsKey(name)){
            sounds.get(name).play();
        }
    }
}
