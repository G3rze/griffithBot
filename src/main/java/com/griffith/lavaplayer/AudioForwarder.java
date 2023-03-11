package com.griffith.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import java.nio.ByteBuffer;
import net.dv8tion.jda.api.audio.AudioSendHandler;

public class AudioForwarder implements AudioSendHandler {

  private final AudioPlayer player;
  private final ByteBuffer buffer = ByteBuffer.allocate(1024);
  private final MutableAudioFrame frame = new MutableAudioFrame();

  public AudioForwarder(AudioPlayer player) {
    this.player = player;
    frame.setBuffer(buffer);
  }

  @Override
  public boolean isOpus() {
    return true;
  }

  @Override
  public boolean canProvide() {
    return player.provide(frame);
  }

  @Override
  public ByteBuffer provide20MsAudio() {
    return buffer.flip();
  }
}
