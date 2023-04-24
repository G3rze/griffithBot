package com.griffith.lavaplayer;

import com.griffith.command.music.EmbedMessage;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class TrackScheculer extends AudioEventAdapter {

  private final AudioPlayer player;
  private final BlockingQueue<AudioTrack> queue = new LinkedBlockingQueue<>();

  private boolean isRepeat = false;

  private TextChannel channel;

  public TrackScheculer(AudioPlayer player) {
    this.player = player;
  }

  @Override
  public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
    if (isRepeat) {
      player.startTrack(track.makeClone(), false);
    } else {
      player.startTrack(queue.poll(), false);
    }
  }

  public void channel(TextChannel channel) {
    this.channel = channel;
  }

  @Override
  public void onTrackStart(AudioPlayer player, AudioTrack track) {
    AudioTrackInfo info = track.getInfo();

    EmbedMessage getEmbed = new EmbedMessage();
    channel
        .sendMessageEmbeds(getEmbed.getEmbedMessage(info.title, info.uri, "Playing this song!"))
        .queue();
  }

  public void queue(AudioTrack track) {
    if (!player.startTrack(track, true)) {
      queue.offer(track);
    }
  }

  public AudioPlayer getPlayer() {
    return player;
  }

  public BlockingQueue<AudioTrack> getQueue() {
    return queue;
  }

  public boolean isRepeat() {
    return isRepeat;
  }

  public void setRepeat(boolean repeat) {
    isRepeat = repeat;
  }
}
