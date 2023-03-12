package com.griffith.command;

import com.griffith.bot.Builder;
import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public abstract class OptionCommand {
  protected Builder bot;
  protected String name;
  protected String description;
  protected List<OptionData> args;
  protected Permission permission;
  protected Permission botPermission;

  protected OptionCommand(Builder bot) {
    this.bot = bot;
    this.args = new ArrayList<>();
  }

  public abstract void execute(SlashCommandInteraction event);
}
