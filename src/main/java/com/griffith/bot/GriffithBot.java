package com.griffith.bot;

import javax.security.auth.login.LoginException;

public class GriffithBot {
  public static void main(String[] args) {
    try {
      Builder bot = new Builder();
    } catch (LoginException e) {
      System.out.println("ERROR: Provided bot token is invalid!");
    }
  }
}
