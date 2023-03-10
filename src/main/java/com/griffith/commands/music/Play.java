package com.griffith.commands.music;

import com.griffith.GriffithBot;
import com.griffith.commands.Command;
import com.griffith.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

import java.util.ArrayList;

public class Play extends Command {

    public Play(GriffithBot bot) {
        super(bot);
        this.name = "play";
        this.description = "Plays music with a Youtube link!";
        this.permission = Permission.MESSAGE_SEND;
        this.botPermission = Permission.VOICE_SPEAK;
        this.args = new ArrayList<>();
    }

    @Override
    public AuditableRestAction<Void> execute(SlashCommandInteraction event) {
        final TextChannel channel = (TextChannel) event.getChannel();
        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (selfVoiceState.inAudioChannel()) {
            channel.sendMessage("I'm already in a voice channel").queue();
        }

        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()){
            channel.sendMessage("You need to be in a voice channel for this command to work").queue();
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
            channel.sendMessage("You need to be in the same voice as the bot for this to work").queue();
        }

        final AudioManager audioManager = event.getGuild().getAudioManager();
        final AudioChannelUnion memberChannel = memberVoiceState.getChannel();

        if (self.getPermissions().contains(botPermission)) {
            audioManager.openAudioConnection(memberChannel);
        }
        event.reply("Connecting to '\uD83D\uDD0A %s'" + event.getChannel().toString()).queue();

        PlayerManager.getInstance()
                .loadAndPlay(channel, "https://www.youtube.com/watch?v=FG0R6DOdXL0");

        return null;
    }
}
