package com.griffith.commands;

import com.griffith.GriffithBot;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleCommand {
    public GriffithBot bot;
    public String name;
    public String description;
    public Permission permission;
    public Permission botPermission;

    protected SimpleCommand(GriffithBot bot){
        this.bot = bot;
    }

    public abstract void execute(SlashCommandInteraction event);

}
