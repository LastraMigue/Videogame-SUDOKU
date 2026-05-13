package com.sudoku.controller;

import com.sudoku.service.MusicManager;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Controller for the settings overlay.
 * Handles volume adjustments and closing the overlay.
 */
public class SettingsController {
    
    @FXML private Slider volumeSlider;
    private Pane parentContainer;
    private StackPane self;

    /**
     * Initializes the volume slider with the current manager volume.
     */
    public void initialize() {
        volumeSlider.setValue(MusicManager.getInstance().getVolume());
        volumeSlider.valueProperty().addListener((obs, old, val) -> {
            MusicManager.getInstance().setVolume(val.doubleValue());
        });
    }

    /**
     * Configures the references to the parent container and this overlay's root.
     * @param parent the container where this overlay is hosted
     * @param self the root pane of this overlay
     */
    public void setContainers(Pane parent, StackPane self) {
        this.parentContainer = parent;
        this.self = self;
    }

    /**
     * Closes the settings overlay and plays a click sound.
     */
    @FXML
    private void handleResume() {
        MusicManager.getInstance().playClickSound();
        if (parentContainer != null && self != null) {
            parentContainer.getChildren().remove(self);
        }
    }
}
