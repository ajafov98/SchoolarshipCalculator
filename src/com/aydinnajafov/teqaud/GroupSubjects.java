package com.aydinnajafov.teqaud;

import com.aydinnajafov.teqaud.data.Subject;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GroupSubjects
{
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<Subject> table;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn credit;
    @FXML
    private Button newSubject;
    @FXML
    private Button edit;
    @FXML
    private Button delete;
    @FXML
    private Button add;
    @FXML
    private TextField newSubjectName;
    @FXML
    private TextField newSubjectCredit;
    private ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList();
    private boolean editing = false;

    public void showSubjects(ObservableList<Subject> subjects)
    {
        this.subjectObservableList.addAll(subjects);
        this.table.setItems(this.subjectObservableList);
    }

    @FXML
    private void newSubjectButton()
    {
        this.newSubjectName.setVisible(true);
        this.newSubjectCredit.setVisible(true);
        this.add.setVisible(true);
    }

    @FXML
    private void addButton()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:src/com/aydinnajafov/teqaud/data/coins(1).png"));

        alert.setTitle("X?ta!");
        alert.setHeaderText("Z?hm?t olmasa f?nnin ad?n? h?rfl?rl?, krediti is? h?rfl?rl? daxil edin!");
        if ((!checkNumber(this.newSubjectName.getText())) || (checkNumber(this.newSubjectCredit.getText()))) {
            alert.showAndWait();
        } else if (this.editing) {
            try
            {
                Subject subject = (Subject)this.table.getSelectionModel().getSelectedItem();
                if ((!this.newSubjectName.getText().isEmpty()) && (!this.newSubjectCredit.getText().isEmpty()))
                {
                    subject.setSubjectName(this.newSubjectName.getText());
                    subject.setSubjectCredit(Double.valueOf(this.newSubjectCredit.getText()).doubleValue());
                    this.newSubjectName.clear();
                    this.newSubjectName.setVisible(false);
                    this.newSubjectCredit.clear();
                    this.newSubjectCredit.setVisible(false);
                    this.add.setVisible(false);
                    this.editing = false;
                    this.table.setDisable(false);
                }
                else
                {
                    alert.setHeaderText("Z?hm?t olmasa f?nin h?m ad?n h?m d? kreditin daxil edin!");
                }
            }
            catch (NumberFormatException e)
            {
                alert.showAndWait();
            }
            finally
            {
                this.newSubjectCredit.clear();
            }
        } else {
            try
            {
                Subject subject = new Subject(this.newSubjectName.getText(), Double.valueOf(this.newSubjectCredit.getText()).doubleValue());
                this.subjectObservableList.add(subject);
                this.newSubjectName.clear();
                this.newSubjectName.setVisible(false);
                this.newSubjectCredit.clear();
                this.newSubjectCredit.setVisible(false);
                this.add.setVisible(false);
            }
            catch (NumberFormatException e)
            {
                alert.showAndWait();
            }
            finally
            {
                this.newSubjectCredit.clear();
            }
        }
    }

    @FXML
    private void keyHandler()
    {
        boolean test = (this.newSubjectName.getText().isEmpty()) && (this.newSubjectCredit.getText().isEmpty());
        this.add.setDisable(test);
    }

    @FXML
    private void editStudent()
    {
        if (this.table.getSelectionModel().getSelectedItem() != null)
        {
            Subject subject = (Subject)this.table.getSelectionModel().getSelectedItem();
            this.newSubjectName.setVisible(true);
            this.newSubjectCredit.setVisible(true);
            this.add.setVisible(true);
            this.editing = true;
            this.newSubjectName.setText(subject.getSubjectName());
            this.newSubjectCredit.setText(String.valueOf(subject.getSubjectCredit()));
            this.table.setDisable(true);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:src/com/aydinnajafov/teqaud/data/coins(1).png"));

            alert.setTitle("X?ta!");
            alert.setHeaderText("D?yi?iklik etm?k ist?diyiniz f?nni se�in!");
            alert.showAndWait();
        }
    }

    private boolean checkNumber(String s)
    {
        char[] word = s.toCharArray();
        char[] numbers = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
        for (char i : word) {
            for (char j : numbers) {
                if (i == j) {
                    return false;
                }
            }
        }
        return true;
    }

    @FXML
    private void deleteStudent()
    {
        if (this.table.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:src/com/aydinnajafov/teqaud/data/coins(1).png"));

            alert.setTitle("X?ta!");
            alert.setHeaderText("Z?hm?t olmasa silm?k ist?diyiniz f?nnin ad?n se�in!");
            alert.showAndWait();
        }
        else
        {
            ButtonType yes = new ButtonType("B?li", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("Xeyir", ButtonBar.ButtonData.CANCEL_CLOSE);
            Subject subject = (Subject)this.table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", new ButtonType[] { yes, no });
            alert.setTitle("Sil");
            alert.setHeaderText(subject.getSubjectName() + " adl? t?l?b?ni silm?k ist?diyinizd?n ?minsiniz?");
            Optional<ButtonType> result = alert.showAndWait();
            if ((result.isPresent()) && (result.get() == yes)) {
                this.subjectObservableList.remove(this.table.getSelectionModel().getSelectedItem());
            }
        }
    }

    public ObservableList<Subject> getSubjects()
    {
        return this.subjectObservableList;
    }
}
