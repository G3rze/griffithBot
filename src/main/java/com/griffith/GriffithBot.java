package com.griffith;

import com.griffith.commands.GiveRole;
import com.griffith.commands.NewRole;
import com.griffith.commands.music.Play;
import com.sedmelluq.discord.lavaplayer.jdaudp.NativeAudioSendFactory;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class GriffithBot {

    private final ShardManager shardManager;
    private final Dotenv config;

    public GriffithBot() throws LoginException {
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("Eclipse"));
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES
                ,GatewayIntent.GUILD_MEMBERS
                ,GatewayIntent.GUILD_PRESENCES
                ,GatewayIntent.GUILD_VOICE_STATES);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.setAudioSendFactory(new NativeAudioSendFactory());
        shardManager = builder.build();

        CommandManager manager = new CommandManager();
        manager.add(new NewRole(this));
        manager.add(new GiveRole(this));
        manager.add(new Play(this));
        shardManager.addEventListener(manager);

    }

    public ShardManager getShardManager() {
         return shardManager;
    }

    public Dotenv getConfig(){
        return config;
    }

    public static void main(String[] args) {
        try {
            GriffithBot bot = new GriffithBot();
        } catch (LoginException e) {
            System.out.println("ERROR: Provided bot token is invalid!");
        }
    }

}
