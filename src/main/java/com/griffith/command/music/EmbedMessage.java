package com.griffith.command.music;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class EmbedMessage {

  public MessageEmbed getEmbedMessage(String title, String uri, String description) {
    EmbedBuilder embedBuilder = new EmbedBuilder();
    embedBuilder.setTitle(title, uri).setDescription(description);
    return embedBuilder.build();
  }
}
