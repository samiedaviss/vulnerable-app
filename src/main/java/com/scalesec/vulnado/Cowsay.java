package com.scalesec.vulnado;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Cowsay {
  public static String run(String input) {
    // Validate and sanitize input
    String sanitizedInput = sanitizeInput(input);
    
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
  
  private static String sanitizeInput(String input) {
    if (input == null) {
      return "";
    }
    
    // Limit input length to prevent resource exhaustion
    if (input.length() > 1000) {
      input = input.substring(0, 1000);
    }
    
    // Remove or escape potentially dangerous characters
    // Allow alphanumeric, spaces, and common punctuation
    // Remove newlines, control characters, and shell metacharacters
    String sanitized = input.replaceAll("[^a-zA-Z0-9\s.,!?;:'\"-]", "");
    
    // Remove leading hyphens to prevent flag injection
    sanitized = sanitized.replaceAll("^-+", "");
    
    return sanitized;
  }
}


