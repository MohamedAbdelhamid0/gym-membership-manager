package com.gymx.controller;

import com.gymx.dao.SubscriptionDao;
import com.gymx.model.Subscription;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SubscriptionsController {
    @FXML private TableView<Subscription> table;
    @FXML private TableColumn<Subscription, Number> colId, colMember, colAmount;
    @FXML private TableColumn<Subscription, String> colStart, colEnd, colType;
    @FXML private TextField memberId, start, end, amount, type;

    private final SubscriptionDao dao = new SubscriptionDao();
    private final ObservableList<Subscription> items = FXCollections.observableArrayList();

    @FXML private void initialize() {
        colId.setCellValueFactory(c -> new SimpleIntegerProperty(
                c.getValue().getSubscriptionId() == null ? 0 : c.getValue().getSubscriptionId()));
        colMember.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getMemberId()));
        colStart.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStartDate()));
        colEnd.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEndDate()));
        colAmount.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getAmountPaid()));
        colType.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSubscriptionType()));
        table.setItems(items);
        table.getSelectionModel().selectedItemProperty().addListener((o, a, b) -> show(b));
        refresh();
    }

    private void refresh() { items.setAll(dao.findAll()); }

    private void show(Subscription s) {
        if (s == null) { memberId.clear(); start.clear(); end.clear(); amount.clear(); type.clear(); return; }
        memberId.setText(String.valueOf(s.getMemberId()));
        start.setText(s.getStartDate());
        end.setText(s.getEndDate());
        amount.setText(String.valueOf(s.getAmountPaid()));
        type.setText(s.getSubscriptionType());
    }

    @FXML private void onAdd() {
        Subscription s = new Subscription();
        s.setMemberId(Integer.parseInt(memberId.getText()));
        s.setStartDate(start.getText());
        s.setEndDate(end.getText());
        s.setAmountPaid(Double.parseDouble(amount.getText()));
        s.setSubscriptionType(type.getText());
        dao.insert(s); refresh(); table.getSelectionModel().select(s);
    }

    @FXML private void onUpdate() {
        Subscription s = table.getSelectionModel().getSelectedItem(); if (s == null) return;
        s.setMemberId(Integer.parseInt(memberId.getText()));
        s.setStartDate(start.getText());
        s.setEndDate(end.getText());
        s.setAmountPaid(Double.parseDouble(amount.getText()));
        s.setSubscriptionType(type.getText());
        dao.update(s); refresh();
    }

    @FXML private void onDelete() {
        Subscription s = table.getSelectionModel().getSelectedItem(); if (s == null) return;
        dao.delete(s.getSubscriptionId()); refresh();
    }
}