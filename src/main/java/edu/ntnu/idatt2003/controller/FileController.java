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
 * Author: Kaamya Shinde
 * Version: 0.3
 * Since: 0.3.3
 */
public class FileController {
    private static final String FILE_PATH_PRESETS = "src/main/resources/";
    private static final String FILE_PATH_APP_FILES = "src/main/resources/appFiles/";
    String selectedFile;
    File directory;
    List<File> files;

    ComboBox<String> fileDropDown = new ComboBox<>();
    ChaosGameDescription description;

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
        fileDropDown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedFile = newValue;
            System.out.println("Selected file is the followinmg: " + selectedFile);
            setDescription(readChaosGameDescriptionFromFile("appFiles/" + selectedFile));
        });
    }

    private void setDescription(ChaosGameDescription description) {
        this.description = description;
        if (this.description != null) {
            System.out.println("Description set: " + this.description.toString());
        } else {
            System.out.println("Description is null");
        }
    }

    /**
     * Method that reads the chaos game description from a file.
     *
     * @param fileName The name of the file that is read.
     * @return The chaos game description that is read from the file.
     */
    public ChaosGameDescription readChaosGameDescriptionFromFile(String fileName) {
        System.out.println("Reading from file: " + fileName);
        return ChaosGameFileHandler.readFromFile(FILE_PATH_PRESETS + fileName);
    }

    //method to read from appFiles

    public ChaosGameDescription readChaosGameDescriptionFromAppFiles(String fileName) {
        System.out.println("Reading from file: " + fileName);
        return ChaosGameFileHandler.readFromFile(FILE_PATH_APP_FILES + fileName);
    }

    /**
     * Method that writes the chaos game description to a file.
     * @param desc The chaos game description that is written to the file.
     * @param fileName The name of the file that is written.
     */
    public void writeChaosGameDescriptionToFile(ChaosGameDescription desc, String fileName) {
        System.out.println("Writing to file: " + fileName);
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

    // Persistence methods
    public void saveLastGame(ChaosGameDescription desc) {
        System.out.println("Saving game to persistence file...");
        System.out.println("Description: " + desc.toString());
        writeChaosGameDescriptionToFile(desc, "persistance");
    }

    public ChaosGameDescription loadLastGame() {
        System.out.println("Loading game from persistence file...");
        return readChaosGameDescriptionFromFile("appFiles/" + "persistance.txt");
    }
}
