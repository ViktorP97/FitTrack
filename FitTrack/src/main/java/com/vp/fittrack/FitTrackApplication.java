package com.vp.fittrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@EntityScan(basePackages = "com.vp.fittrack")
public class FitTrackApplication{

  public static void main(String[] args) {
    setProperty();
    SpringApplication.run(FitTrackApplication.class, args);
  }

  public static void setProperty() {
    Path currentWorkingDirectory = Paths.get(System.getProperty("user.dir"));
    Path parentDirectory = currentWorkingDirectory.getParent();

    String h2DatabaseUrl = "jdbc:h2:file:" + parentDirectory.resolve("maindb");

    if (!Files.exists(parentDirectory.resolve("maindb"))) {
      try {
        Files.createDirectories(parentDirectory.resolve("maindb"));
        System.out.println("Database directory created: " + parentDirectory.resolve("maindb"));
      } catch (Exception e) {
        System.err.println("Error creating database directory: " + e.getMessage());
        return;
      }
    }
    System.setProperty("spring.datasource.url", h2DatabaseUrl);
  }
}