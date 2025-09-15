package com.scalesec.vulnado;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Cowsay {
  public static String run(String input) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    System.out.println("Running cowsay with input: " + input);
    
    // Validate and sanitize input to prevent command injection
    String sanitizedInput = sanitizeInput(input);
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
  
  private static String sanitizeInput(String input) {
    if (input == null) {
      return "";
    }
    
    // Limit input length to prevent potential buffer overflow
    if (input.length() > 1000) {
      input = input.substring(0, 1000);
    }
    
    // Allow only safe characters: letters, numbers, spaces, and basic punctuation
    // Remove potentially dangerous characters that could be used for command injection
    String sanitized = input.replaceAll("[^a-zA-Z0-9\s.,!?'\"\-_()\[\]{}:;]", "");
    
    // Additional safety: remove any sequences that might be interpreted as command separators
    sanitized = sanitized.replaceAll("[;&|`$]", "");
    
    return sanitized;
  }
}


