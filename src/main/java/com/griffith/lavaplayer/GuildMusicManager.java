package com.griffith.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class GuildMusicManager {

  private TrackScheculer trackScheculer;
  private AudioForwarder audioForwarder;

  public GuildMusicManager(AudioPlayerManager manager) {
    AudioPlayer player = manager.createPlayer();
    trackScheculer = new TrackScheculer(player);
    player.addListener(trackScheculer);
    audioForwarder = new AudioForwarder(player);
  }

  public TrackScheculer getTrackScheculer() {
    return trackScheculer;
  }

  public AudioForwarder getAudioForwarder() {
    return audioForwarder;
  }
}
