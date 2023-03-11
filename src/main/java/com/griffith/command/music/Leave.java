package com.griffith.command.music;

import com.griffith.bot.Builder;
import com.griffith.command.SimpleCommand;
import com.griffith.lavaplayer.GuildMusicManager;
import com.griffith.lavaplayer.PlayerManager;
import com.griffith.lavaplayer.TrackScheculer;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class Leave extends SimpleCommand {

  public Leave(Builder bot) {
    super(bot);
    this.name = "leave";
    this.description = "Leave the voice channel!";
    this.permission = Permission.MESSAGE_SEND;
    this.botPermission = Permission.VOICE_SPEAK;
  }

  @Override
  public void execute(SlashCommandInteraction event) {
    InChannelChecker checker = new InChannelChecker();

    checker.inChannerlChecker(event);

    GuildMusicManager guildMusicManager =
        PlayerManager.getInstance().getGuildMusicManager(event.getGuild());
    TrackScheculer trackScheculer = guildMusicManager.getTrackScheculer();

    trackScheculer.getQueue().clear();
    trackScheculer.getPlayer().stopTrack();
    event.getGuild().getAudioManager().closeAudioConnection();

    event.reply("Finally I'm free!").queue();
  }
}
