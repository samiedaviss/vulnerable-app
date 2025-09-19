package com.scalesec.vulnado;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Cowsay {
  public static String run(String input) {
    // Validate input to prevent command injection
    if (input == null || !input.matches("^[a-zA-Z0-9\s.,!?-]*$")) {
      return "Invalid input. Only alphanumeric characters and basic punctuation are allowed.";
    }
    
    ProcessBuilder processBuilder = new ProcessBuilder();
    processBuilder.command("/usr/games/cowsay", input);

    StringBuilder output = new StringBuilder();

    try {
      Process process = processBuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return output.toString();
  }
}

