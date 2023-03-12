package com.griffith.bot;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;

public class GriffithBot {
  public static void main(String[] args) {
    Logger logger = Logger.getLogger(GriffithBot.class.getName());
    try {
      new Builder();
    } catch (LoginException e) {
      logger.log(Level.WARNING, "ERROR: Provided bot token is invalid!");
    }
  }
}
