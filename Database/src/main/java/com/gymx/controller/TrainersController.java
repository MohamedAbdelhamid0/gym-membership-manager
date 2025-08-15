package com.gymx.controller;

import com.gymx.dao.TrainerDao;
import com.gymx.model.Trainer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TrainersController {
    @FXML private TableView<Trainer> table;
    @FXML private TableColumn<Trainer, Number> colId;
    @FXML private TableColumn<Trainer, String> colFirst, colLast, colEmail, colPhone;
    @FXML private TextField first, last, email, phone, specialty;

    private final TrainerDao dao = new TrainerDao();
    private final ObservableList<Trainer> items = FXCollections.observableArrayList();

    @FXML private void initialize() {
        colId.setCellValueFactory(c -> new SimpleIntegerProperty(
                c.getValue().getTrainerId() == null ? 0 : c.getValue().getTrainerId()));
        colFirst.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFirstName()));
        colLast.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLastName()));
        colEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
        colPhone.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPhone()));
        table.setItems(items);
        table.getSelectionModel().selectedItemProperty().addListener((o, a, b) -> show(b));
        refresh();
    }

    private void refresh() { items.setAll(dao.findAll()); }

    private void show(Trainer t) {
        if (t == null) { first.clear(); last.clear(); email.clear(); phone.clear(); specialty.clear(); return; }
        first.setText(t.getFirstName()); last.setText(t.getLastName()); email.setText(t.getEmail());
        phone.setText(t.getPhone()); specialty.setText(t.getSpecialty());
    }

    @FXML private void onAdd() {
        Trainer t = new Trainer();
        t.setFirstName(first.getText()); t.setLastName(last.getText()); t.setEmail(email.getText());
        t.setPhone(phone.getText()); t.setSpecialty(specialty.getText());
        dao.insert(t); refresh(); table.getSelectionModel().select(t);
    }

    @FXML private void onUpdate() {
        Trainer t = table.getSelectionModel().getSelectedItem(); if (t == null) return;
        t.setFirstName(first.getText()); t.setLastName(last.getText()); t.setEmail(email.getText());
        t.setPhone(phone.getText()); t.setSpecialty(specialty.getText());
        dao.update(t); refresh();
    }

    @FXML private void onDelete() {
        Trainer t = table.getSelectionModel().getSelectedItem(); if (t == null) return;
        dao.delete(t.getTrainerId()); refresh();
    }
}