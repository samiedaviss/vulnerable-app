package com.scalesec.vulnado;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Cowsay {
  public static String run(String input) {
    // Validate and sanitize input to prevent command injection
    String sanitizedInput = input.replaceAll("[;&|<>]", "");
    
    ProcessBuilder processBuilder = new ProcessBuilder();
    processBuilder.command("/usr/games/cowsay", sanitizedInput);
    
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


