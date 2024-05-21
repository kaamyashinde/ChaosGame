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
 * <p>
 * Author: 10041
 * Version: 0.3
 * Since: 0.3.3
 */
public class FileController {
  private static final String FILE_PATH_RESOURCES = "src/main/resources/";
  private static final String FILE_PATH_APP_FILES = FILE_PATH_RESOURCES + "appFiles/";
  private static final String FILE_PATH_PRESETS = FILE_PATH_RESOURCES + "presets/";
  private static final String FILE_PATH_PERSISTENCE = FILE_PATH_RESOURCES + "persistence/persistence.txt";
  private final ComboBox<String> fileDropDown = new ComboBox<>();
  private final File directory;
  private List<File> files;

  /**
   * Constructor that initializes the directory and the list of files.
   */
  public FileController() {
    directory = new File(FILE_PATH_APP_FILES);
    files = Arrays.asList(Objects.requireNonNull(directory.listFiles()));
    setFileDropDown();
  }
  /**
   * Method that reads the chaos game description from the persistence file.
   * @return The chaos game description that is read from the persistence file.
   */
  private ChaosGameDescription readChaosGameDescriptionFromPersistence(){
    return ChaosGameFileHandler.readFromFile(FILE_PATH_PERSISTENCE);
  }
  /**
   * Method that sets the file drop down with the files in the appFiles directory.
   */
  private void setFileDropDown() {
    files.forEach(file -> fileDropDown.getItems().add(file.getName()));
  }

  /**
   * Method that clears the contents of the persistence file.
   */
  public static void clearFileContent() {
    ChaosGameFileHandler.clearFileContent(FILE_PATH_PERSISTENCE);
  }
  /**
   * Method that loads the last game from the persistence file.
   * @return The chaos game description that is read from the persistence file.
   */
  public ChaosGameDescription loadLastGame() {
    return readChaosGameDescriptionFromPersistence();
  }
  /**
   * Method that reads the chaos game description from a file in the appFiles directory.
   * @param fileName The name of the file that is read.
   * @return The chaos game description that is read from the file.
   */
  public ChaosGameDescription readChaosGameDescriptionFromAppFiles(String fileName) {
    return ChaosGameFileHandler.readFromFile(FILE_PATH_APP_FILES + fileName );
  }
  /**
   * Method that reads the chaos game description from a file in the presets directory.
   * @param fileName The name of the file that is read.
   * @return The chaos game description that is read from the file.
   */
  public ChaosGameDescription readChaosGameDescriptionFromPresets(String fileName) {
    return ChaosGameFileHandler.readFromFile(FILE_PATH_PRESETS + fileName + ".txt");
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
   * Method that overwrites the persistence file with the chaos game description.
   * @param desc The chaos game description that is written to the persistence file.
   */
  public void saveLastGame(ChaosGameDescription desc) {
    ChaosGameFileHandler.writeToFile(desc, FILE_PATH_PERSISTENCE);
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
  /**
   * Method that writes the chaos game description to a file.
   *
   * @param desc     The chaos game description that is written to the file.
   * @param fileName The name of the file that is written.
   */
  public void writeChaosGameDescriptionToAppFiles(ChaosGameDescription desc, String fileName) {
    ChaosGameFileHandler.writeToFile(desc, FILE_PATH_APP_FILES + fileName + ".txt");
  }
  /**
   * Method that writes the chaos game description to a file in the presets directory.
   */
  public void writeChaosGameDescriptionToPresets(ChaosGameDescription desc, String fileName) {
    ChaosGameFileHandler.writeToFile(desc, FILE_PATH_PRESETS + fileName + ".txt");
  }

}
