package com.gymx.controller;

import com.gymx.dao.AttendanceDao;
import com.gymx.model.Attendance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AttendanceController {
    @FXML private TableView<Attendance> table;
    @FXML private TableColumn<Attendance, Number> colId, colMember, colSession;
    @FXML private TableColumn<Attendance, String> colDate, colStatus;
    @FXML private TextField memberId, sessionId, date, status;

    private final AttendanceDao dao = new AttendanceDao();
    private final ObservableList<Attendance> items = FXCollections.observableArrayList();

    @FXML private void initialize() {
        colId.setCellValueFactory(c -> new SimpleIntegerProperty(
                c.getValue().getAttendanceId() == null ? 0 : c.getValue().getAttendanceId()));
        colMember.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getMemberId()));
        colSession.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getSessionId()));
        colDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAttendanceDate()));
        colStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));
        table.setItems(items);
        table.getSelectionModel().selectedItemProperty().addListener((o, a, b) -> show(b));
        refresh();
    }

    private void refresh() { items.setAll(dao.findAll()); }

    private void show(Attendance a) {
        if (a == null) { memberId.clear(); sessionId.clear(); date.clear(); status.clear(); return; }
        memberId.setText(String.valueOf(a.getMemberId()));
        sessionId.setText(String.valueOf(a.getSessionId()));
        date.setText(a.getAttendanceDate());
        status.setText(a.getStatus());
    }

    @FXML private void onAdd() {
        Attendance a = new Attendance();
        a.setMemberId(Integer.parseInt(memberId.getText()));
        a.setSessionId(Integer.parseInt(sessionId.getText()));
        a.setAttendanceDate(date.getText());
        a.setStatus(status.getText());
        dao.insert(a); refresh(); table.getSelectionModel().select(a);
    }

    @FXML private void onUpdate() {
        Attendance a = table.getSelectionModel().getSelectedItem(); if (a == null) return;
        a.setMemberId(Integer.parseInt(memberId.getText()));
        a.setSessionId(Integer.parseInt(sessionId.getText()));
        a.setAttendanceDate(date.getText());
        a.setStatus(status.getText());
        dao.update(a); refresh();
    }

    @FXML private void onDelete() {
        Attendance a = table.getSelectionModel().getSelectedItem(); if (a == null) return;
        dao.delete(a.getAttendanceId()); refresh();
    }
}