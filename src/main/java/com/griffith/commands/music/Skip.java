package com.griffith.commands.music;

import com.griffith.GriffithBot;
import com.griffith.commands.OptionCommand;
import com.griffith.commands.SimpleCommand;
import com.griffith.lavaplayer.GuildMusicManager;
import com.griffith.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class Skip extends SimpleCommand {

    public Skip(GriffithBot bot) {
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

        GuildMusicManager guildMusicManager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild());
        guildMusicManager.getTrackScheculer().getPlayer().stopTrack();

        event.reply("Skipped").queue();
    }
}
