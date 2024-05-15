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
 *
 * @author Kaamya Shinde
 * @version 0.3
 * @since 0.3.3
 */
public class FileController {
  private static final String FILE_PATH_PRESETS = "src/main/resources/";
  private static final String FILE_PATH_APP_FILES = "src/main/resources/appFiles/";
 // String selectedFile;
  File directory;
  List<File> files;

  ComboBox<String> fileDropDown = new ComboBox<>();

  /**
   * Constructor that initializes the directory and the list of files.
   */

  public FileController() {
    directory = new File(FILE_PATH_APP_FILES);
    files = Arrays.asList(Objects.requireNonNull(directory.listFiles()));
    setFileDropDown();
  }

  /**
   * Method that returns the file drop down.
   *
   * @return The file drop down.
   */

  public ComboBox<String> getFileDropDown() {
    return fileDropDown;
  }

  /**
   * Method that sets the file drop down with the files in the appFiles directory.
   */

  private void setFileDropDown() {
    files.forEach(file -> fileDropDown.getItems().add(file.getName()));
    fileDropDown.showingProperty().addListener((observable, oldValue, newValue) -> {
      /*if (!newValue) {
        selectedFile = fileDropDown.getSelectionModel().getSelectedItem();
        System.out.println("Selected file: " + selectedFile);
        System.out.println("File neame:");
        // You can add more actions here that should be executed when a file is selected
        ChaosGameDescription desc = readChaosGameDescriptionFromFile("appFiles/" + selectedFile);
      }*/
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

  /**
   * Method that writes the chaos game description to a file.
   * @param desc The chaos game description that is written to the file.
   * @param fileName The name of the file that is written.
   */

  public void writeChaosGameDescriptionToFile(ChaosGameDescription desc, String fileName) {
    ChaosGameFileHandler.writeToFile(desc, FILE_PATH_APP_FILES + fileName + ".txt");

  }

  /**
   * Method that updates the file drop down with the files in the appFiles directory.
   * It is called when a file is added to the directory from the application.
   */

  public void updateFileDropDown() {
    fileDropDown.getItems().clear();
    files = Arrays.asList(Objects.requireNonNull(directory.listFiles()));
    files.forEach(file -> fileDropDown.getItems().add(file.getName()));
  }
}