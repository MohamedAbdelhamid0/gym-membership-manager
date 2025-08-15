package com.gymx.controller;

import com.gymx.dao.MemberDao;
import com.gymx.model.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MembersController {
    @FXML private TableView<Member> table;
    @FXML private TableColumn<Member, Number> colId;
    @FXML private TableColumn<Member, String> colFirst, colLast, colEmail, colJoin;
    @FXML private TextField firstName, lastName, email, joinDate, dob, gender, uid;
    @FXML private Label status;

    private final MemberDao dao = new MemberDao();
    private final ObservableList<Member> items = FXCollections.observableArrayList();

    @FXML private void initialize() {
        colId.setCellValueFactory(c -> new SimpleIntegerProperty(
                c.getValue().getMemberId() == null ? 0 : c.getValue().getMemberId()));
        colFirst.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFirstName()));
        colLast.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLastName()));
        colEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
        colJoin.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getJoinDate()));
        table.setItems(items);
        table.getSelectionModel().selectedItemProperty().addListener((o, a, b) -> show(b));
        refresh();
    }

    private void refresh() { items.setAll(dao.findAll()); status.setText("Members: " + items.size()); }

    private void show(Member m) {
        if (m == null) { onClear(); return; }
        firstName.setText(m.getFirstName()); lastName.setText(m.getLastName()); email.setText(m.getEmail());
        joinDate.setText(m.getJoinDate()); dob.setText(m.getDateOfBirth()); gender.setText(m.getGender()); uid.setText(m.getUid());
    }

    @FXML private void onAdd() {
        Member m = new Member(null, null, joinDate.getText(), firstName.getText(), lastName.getText(),
                email.getText(), dob.getText(), gender.getText(), uid.getText());
        dao.insert(m); refresh(); table.getSelectionModel().select(m);
    }

    @FXML private void onUpdate() {
        Member m = table.getSelectionModel().getSelectedItem(); if (m == null) return;
        m.setFirstName(firstName.getText()); m.setLastName(lastName.getText()); m.setEmail(email.getText());
        m.setJoinDate(joinDate.getText()); m.setDateOfBirth(dob.getText()); m.setGender(gender.getText()); m.setUid(uid.getText());
        dao.update(m); refresh();
    }

    @FXML private void onDelete() {
        Member m = table.getSelectionModel().getSelectedItem(); if (m == null) return;
        dao.delete(m.getMemberId()); refresh(); onClear();
    }

    @FXML private void onClear() {
        firstName.clear(); lastName.clear(); email.clear(); joinDate.clear(); dob.clear(); gender.clear(); uid.clear();
        table.getSelectionModel().clearSelection();
    }
}