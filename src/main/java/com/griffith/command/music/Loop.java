package com.griffith.command.music;

import com.griffith.bot.Builder;
import com.griffith.command.SimpleCommand;
import com.griffith.lavaplayer.GuildMusicManager;
import com.griffith.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class Loop extends SimpleCommand {
  public Loop(Builder bot) {
    super(bot);
    this.name = "loop";
    this.description = "Repeat the current song in loop!";
    this.permission = Permission.MESSAGE_SEND;
    this.botPermission = Permission.VOICE_SPEAK;
  }

  @Override
  public void execute(SlashCommandInteraction event) {

    InChannelChecker checker = new InChannelChecker();

    checker.getChecker(event);

    GuildMusicManager guildMusicManager =
        PlayerManager.getInstance().getGuildMusicManager(event.getGuild());
    boolean isRepeat = !guildMusicManager.getTrackScheculer().isRepeat();
    guildMusicManager.getTrackScheculer().setRepeat(isRepeat);
    if (isRepeat) {
      AudioTrackInfo info =
          guildMusicManager.getTrackScheculer().getPlayer().getPlayingTrack().getInfo();

      EmbedMessage getEmbed = new EmbedMessage();

      event
          .replyEmbeds(getEmbed.getEmbedMessage(info.title, info.uri, "Repeating the current song"))
          .queue();
    }
    event.reply("Loop ended!").queue();
  }
}
