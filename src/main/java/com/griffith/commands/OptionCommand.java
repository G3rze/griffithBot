package com.griffith.commands;

import com.griffith.GriffithBot;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public abstract class OptionCommand {
    public GriffithBot bot;
    public String name;
    public String description;
    public List<OptionData> args;
    public Permission permission;
    public Permission botPermission;

    protected OptionCommand(GriffithBot bot){
        this.bot = bot;
        this.args = new ArrayList<>();
    }

    public abstract void execute(SlashCommandInteraction event);

}
