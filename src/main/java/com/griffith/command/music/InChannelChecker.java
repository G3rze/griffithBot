package com.griffith.command.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class InChannelChecker {

  public void getChecker(SlashCommandInteraction event) {
    Member member = event.getMember();
    GuildVoiceState memberVoiceState = member.getVoiceState();

    if (!memberVoiceState.inAudioChannel()) {
      event.reply("You must be in a voice channel!").queue();
      return;
    }

    Member selfBot = event.getGuild().getSelfMember();
    GuildVoiceState selfBotVoiceState = selfBot.getVoiceState();

    if (!selfBotVoiceState.inAudioChannel()) {
      event.reply("I'm not in an audio channel!").queue();
      return;
    }

    if (selfBotVoiceState.getChannel() != memberVoiceState.getChannel()) {
      event.reply("You're not in the same channel as me!").queue();
    }
  }
}
