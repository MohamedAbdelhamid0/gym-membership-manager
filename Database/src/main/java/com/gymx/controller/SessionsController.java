package com.gymx.controller;

import com.gymx.dao.SessionDao;
import com.gymx.model.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SessionsController {
    @FXML private TableView<Session> table;
    @FXML private TableColumn<Session, Number> colId, colTrainer, colMax;
    @FXML private TableColumn<Session, String> colTitle, colStart, colEnd;
    @FXML private TextField title, start, end, trainerId, maxCap;

    private final SessionDao dao = new SessionDao();
    private final ObservableList<Session> items = FXCollections.observableArrayList();

    @FXML private void initialize() {
        colId.setCellValueFactory(c -> new SimpleIntegerProperty(
                c.getValue().getSessionId() == null ? 0 : c.getValue().getSessionId()));
        colTitle.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTitle()));
        colStart.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStartTime()));
        colEnd.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEndTime()));
        colTrainer.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getTrainerId()));
        colMax.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getMaxCapacity()));
        table.setItems(items);
        table.getSelectionModel().selectedItemProperty().addListener((o, a, b) -> show(b));
        refresh();
    }

    private void refresh() { items.setAll(dao.findAll()); }

    private void show(Session s) {
        if (s == null) { title.clear(); start.clear(); end.clear(); trainerId.clear(); maxCap.clear(); return; }
        title.setText(s.getTitle()); start.setText(s.getStartTime()); end.setText(s.getEndTime());
        trainerId.setText(String.valueOf(s.getTrainerId())); maxCap.setText(String.valueOf(s.getMaxCapacity()));
    }

    @FXML private void onAdd() {
        Session s = new Session();
        s.setTitle(title.getText()); s.setStartTime(start.getText()); s.setEndTime(end.getText());
        s.setTrainerId(Integer.parseInt(trainerId.getText())); s.setMaxCapacity(Integer.parseInt(maxCap.getText()));
        dao.insert(s); refresh(); table.getSelectionModel().select(s);
    }

    @FXML private void onUpdate() {
        Session s = table.getSelectionModel().getSelectedItem(); if (s == null) return;
        s.setTitle(title.getText()); s.setStartTime(start.getText()); s.setEndTime(end.getText());
        s.setTrainerId(Integer.parseInt(trainerId.getText())); s.setMaxCapacity(Integer.parseInt(maxCap.getText()));
        dao.update(s); refresh();
    }

    @FXML private void onDelete() {
        Session s = table.getSelectionModel().getSelectedItem(); if (s == null) return;
        dao.delete(s.getSessionId()); refresh();
    }
}