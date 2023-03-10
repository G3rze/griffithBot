package com.griffith.commands;

import com.griffith.GriffithBot;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    public GriffithBot bot;
    public String name;
    public String description;
    public List<OptionData> args;
    public Permission permission;
    public Permission botPermission;

    protected Command(GriffithBot bot){
        this.bot = bot;
        this.args = new ArrayList<>();
    }

    public abstract AuditableRestAction<Void> execute(SlashCommandInteraction event);

}
