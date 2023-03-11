package com.griffith.command.music;

import com.griffith.bot.Builder;
import com.griffith.command.SimpleCommand;
import com.griffith.lavaplayer.GuildMusicManager;
import com.griffith.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class Skip extends SimpleCommand {

  public Skip(Builder bot) {
    super(bot);
    this.name = "skip";
    this.description = "Skip the current song!";
    this.permission = Permission.MESSAGE_SEND;
    this.botPermission = Permission.VOICE_SPEAK;
  }

  @Override
  public void execute(SlashCommandInteraction event) {
    InChannelChecker checker = new InChannelChecker();

    checker.inChannerlChecker(event);

    GuildMusicManager guildMusicManager =
        PlayerManager.getInstance().getGuildMusicManager(event.getGuild());
    guildMusicManager.getTrackScheculer().getPlayer().stopTrack();

    event.reply("Skipped").queue();
  }
}
