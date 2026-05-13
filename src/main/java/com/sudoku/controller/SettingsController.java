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

    public void initialize() {
        volumeSlider.setValue(MusicManager.getInstance().getVolume());
        volumeSlider.valueProperty().addListener((obs, old, val) -> {
            MusicManager.getInstance().setVolume(val.doubleValue());
        });
    }

    public void setContainers(Pane parent, StackPane self) {
        this.parentContainer = parent;
        this.self = self;
    }

    @FXML
    private void handleResume() {
        if (parentContainer != null && self != null) {
            parentContainer.getChildren().remove(self);
        }
    }
}
