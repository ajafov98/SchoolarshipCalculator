package com.aydinnajafov.teqaud;

import com.aydinnajafov.teqaud.data.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;

public class UpdateSubject
{
    @FXML
    private TableView<Subject> table;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn grade;
    @FXML
    private TableColumn credit;
    @FXML
    private Label nameLabel;
    @FXML
    private Label creditLabel;
    @FXML
    private TextField gradeField;
    @FXML
    private Button saveButton;
    private boolean added = false;
    private ObservableList<Subject> subjectList = FXCollections.observableArrayList();

    public void initialize()
    {
        this.gradeField.setDisable(true);
        this.saveButton.setDisable(true);
        this.nameLabel.setText("F?nni se�in");
        this.creditLabel.setText("F?nni se�in");
    }

    public void showSubjects(ObservableList<Subject> subjects)
    {
        this.subjectList = subjects;
        this.table.setItems(this.subjectList);
    }

    @FXML
    private void nameSetter()
    {
        if (this.table.getSelectionModel().getSelectedItem() != null)
        {
            this.nameLabel.setText(((Subject)this.table.getSelectionModel().getSelectedItem()).getSubjectName());
            this.creditLabel.setText(String.valueOf(((Subject)this.table.getSelectionModel().getSelectedItem()).getSubjectCredit()));
            this.gradeField.setDisable(false);
            this.saveButton.setDisable(false);
        }
    }

    @FXML
    private void saveButtonHandler()
    {
        if ((this.table.getSelectionModel().getSelectedItem() != null) && (!this.gradeField.getText().isEmpty()))
        {
            Subject subject = (Subject)this.table.getSelectionModel().getSelectedItem();
            subject.setSubjectGrade(Double.valueOf(this.gradeField.getText()).doubleValue());
            this.gradeField.clear();
            this.gradeField.setDisable(true);
            this.saveButton.setDisable(true);
            this.nameLabel.setText("F?nni se�in");
            this.creditLabel.setText("F?nni se�in");
        }
    }

    public ObservableList<Subject> getSubjectList()
    {
        return this.subjectList;
    }
}
