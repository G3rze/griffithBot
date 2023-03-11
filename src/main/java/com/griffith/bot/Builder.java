package com.griffith.bot;

import com.griffith.command.CommandRegistry;
import com.sedmelluq.discord.lavaplayer.jdaudp.NativeAudioSendFactory;
import io.github.cdimascio.dotenv.Dotenv;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Builder {

  private final ShardManager shardManager;
  private final Dotenv config;

  public Builder() throws LoginException {
    config = Dotenv.configure().load();
    String token = config.get("TOKEN");

    DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
    builder.setStatus(OnlineStatus.ONLINE);
    builder.setActivity(Activity.playing("Eclipse"));
    builder.enableIntents(
        GatewayIntent.GUILD_MESSAGES,
        GatewayIntent.GUILD_MEMBERS,
        GatewayIntent.GUILD_PRESENCES,
        GatewayIntent.GUILD_VOICE_STATES);
    builder.setMemberCachePolicy(MemberCachePolicy.ALL);
    builder.setChunkingFilter(ChunkingFilter.ALL);
    builder.setAudioSendFactory(new NativeAudioSendFactory());
    shardManager = builder.build();

    CommandRegistry registry = new CommandRegistry();

    shardManager.addEventListener(registry.register(this));
  }

  public ShardManager getShardManager() {
    return shardManager;
  }

  public Dotenv getConfig() {
    return config;
  }
}
