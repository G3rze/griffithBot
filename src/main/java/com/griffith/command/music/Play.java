package com.griffith.command.music;

import com.griffith.bot.Builder;
import com.griffith.command.OptionCommand;
import com.griffith.lavaplayer.GuildMusicManager;
import com.griffith.lavaplayer.PlayerManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Play extends OptionCommand {

  public Play(Builder bot) {
    super(bot);
    this.name = "play";
    this.description = "Plays some music!";
    this.permission = Permission.MESSAGE_SEND;
    this.botPermission = Permission.VOICE_SPEAK;
    this.args = new ArrayList<>();

    args.add(
        new OptionData(OptionType.STRING, "name", "The name of the song you want to play", true));
  }

  @Override
  public void execute(SlashCommandInteraction event) {
    Member member = event.getMember();
    GuildVoiceState memberVoiceState = member.getVoiceState();

    if (!memberVoiceState.inAudioChannel()) {
      event.reply("You must be in a voice channel").queue();
      return;
    }

    Member selfBot = event.getGuild().getSelfMember();
    GuildVoiceState selfBotVoiceState = selfBot.getVoiceState();


    if (!selfBotVoiceState.inAudioChannel()) {
      event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());
      event
          .reply("Connecting to '\uD83D\uDD0A' " + memberVoiceState.getChannel().getAsMention())
          .queue();

    } else {
      if (selfBotVoiceState.getChannel() != memberVoiceState.getChannel()) {
        event.reply("You must be in the same channel with me").queue();
        return;
      }
    }

    String name = event.getOption("name").getAsString();
    try {
      new URI(name);
    } catch (URISyntaxException e) {
      name = "ytsearch:" + name;
    }
    PlayerManager playerManager = PlayerManager.getInstance();
    playerManager.play(event.getGuild(), name);

    GuildMusicManager guildMusicManager = playerManager.getGuildMusicManager(event.getGuild());

    guildMusicManager.getTrackScheculer().channel(event.getChannel().asTextChannel());

    event.reply("Song added to the queue").queue();
  }
}
