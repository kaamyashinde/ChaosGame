package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.engine.ChaosGameFileHandler;
import javafx.scene.control.ComboBox;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class is responsible for handling the file operations.
 * @version 0.1
 * @since 0.3.3
 * @author Kaamya Shinde
 */
public class FileController {
  private static final String FILE_PATH_PRESETS = "src/main/resources/";
  private static final String FILE_PATH_APP_FILES = "src/main/resources/appFiles/";
  String selectedFile;
  File directory;
  List<File> files;

  ComboBox<String> fileDropDown = new ComboBox<>();

  public FileController() {
    directory = new File(FILE_PATH_APP_FILES);
    files = Arrays.asList(Objects.requireNonNull(directory.listFiles()));
    setFileDropDown();
  }

  public ComboBox<String> getFileDropDown() {
    return fileDropDown;
  }

  private void setFileDropDown() {
    files.forEach(file -> fileDropDown.getItems().add(file.getName()));
    fileDropDown.showingProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) {
        selectedFile = fileDropDown.getSelectionModel().getSelectedItem();
        System.out.println("Selected file: " + selectedFile);
        System.out.println("File neame:");
        // You can add more actions here that should be executed when a file is selected
        ChaosGameDescription desc = readChaosGameDescriptionFromFile("appFiles/" + selectedFile);
      }
    });
  }

  /**
   * Method that reads the chaos game description from a file.
   *
   * @param fileName The name of the file that is read.
   * @return The chaos game description that is read from the file.
   */
  public ChaosGameDescription readChaosGameDescriptionFromFile(String fileName) {
    return ChaosGameFileHandler.readFromFile(FILE_PATH_PRESETS + fileName);
  }
}