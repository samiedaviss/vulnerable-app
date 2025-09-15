package com.scalesec.vulnado;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Cowsay {
  public static String run(String input) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    System.out.println("Running cowsay with input: " + input);
    
    // Validate and sanitize input to prevent command injection
    String sanitizedInput = sanitizeInput(input);
    if (sanitizedInput.isEmpty()) {
      return "Error: Invalid input provided";
    }
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
    
    // Use whitelist approach: only allow safe characters
    // Allow letters, numbers, spaces, and minimal safe punctuation
    String sanitized = input.replaceAll("[^a-zA-Z0-9\s.,!?\-]", "");
    
    // Remove any potential command injection sequences
    sanitized = sanitized.replaceAll("[\n\r\t]", " ");
    
    // Trim whitespace and ensure it's not empty after sanitization
    sanitized = sanitized.trim();
    if (sanitized.isEmpty()) {
      return "Hello World"; // Default safe message
    }
    
    return sanitized;
  }
}



