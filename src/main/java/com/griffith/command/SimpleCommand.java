package com.griffith.command;

import com.griffith.bot.Builder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public abstract class SimpleCommand {
  protected Builder bot;
  protected String name;
  protected String description;
  protected Permission permission;
  protected Permission botPermission;

  protected SimpleCommand(Builder bot) {
    this.bot = bot;
  }

  public abstract void execute(SlashCommandInteraction event);
}
