package com.griffith.lavaplayer;

import com.griffith.bot.GriffithBot;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import java.util.HashMap;
import java.util.Map;
import net.dv8tion.jda.api.entities.Guild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerManager {

  private static PlayerManager playerManager;
  private final Map<Long, GuildMusicManager> guildMusicManagerMap = new HashMap<>();
  private final AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();

  private PlayerManager() {
    AudioSourceManagers.registerRemoteSources(audioPlayerManager);
    AudioSourceManagers.registerLocalSource(audioPlayerManager);
  }

  public static PlayerManager getInstance() {
    if (playerManager == null) {
      playerManager = new PlayerManager();
    }
    return playerManager;
  }

  public GuildMusicManager getGuildMusicManager(Guild guild) {
    return guildMusicManagerMap.computeIfAbsent(
        guild.getIdLong(),
        guildId -> {
          GuildMusicManager musicManager = new GuildMusicManager(audioPlayerManager);

          guild.getAudioManager().setSendingHandler(musicManager.getAudioForwarder());

          return musicManager;
        });
  }

  public void play(Guild guild, String trackURL) {
    GuildMusicManager guildMusicManager = getGuildMusicManager(guild);
    audioPlayerManager.loadItemOrdered(
        guildMusicManager,
        trackURL,
        new AudioLoadResultHandler() {
          private static final Logger logger = LoggerFactory.getLogger(GriffithBot.class);

          @Override
          public void trackLoaded(AudioTrack audioTrack) {
            guildMusicManager.getTrackScheculer().queue(audioTrack);
          }

          @Override
          public void playlistLoaded(AudioPlaylist audioPlaylist) {
            if (trackURL.contains("www.youtube.com/playlist")) {
              for (int i = 0; i < audioPlaylist.getTracks().size(); i++) {
                guildMusicManager.getTrackScheculer().queue(audioPlaylist.getTracks().get(i));
              }
            } else {
              guildMusicManager.getTrackScheculer().queue(audioPlaylist.getTracks().get(0));
            }
          }

          @Override
          public void noMatches() {
            logger.info("No matches found for the requested track.");
          }

          @Override
          public void loadFailed(FriendlyException e) {
            final String ERROR_MESSAGE = "Failed to load the track: " + e.getMessage();
            logger.error(ERROR_MESSAGE);
          }
        });
  }
}
