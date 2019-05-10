package com.aydinnajafov.teqaud;

import com.aydinnajafov.teqaud.data.Group;
import com.aydinnajafov.teqaud.data.Student;
import com.aydinnajafov.teqaud.data.Subject;
import java.io.PrintStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class NewGroupController
{
    private ObservableList<Subject> subjectList = FXCollections.observableArrayList();
    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    @FXML
    private TextField groupNumber;
    @FXML
    private TextField groupFaculty;
    @FXML
    private TextField studentName;
    @FXML
    private TextField subjectName;
    @FXML
    private TextField subjectCredit;
    @FXML
    private Button studentAddButton;
    @FXML
    private Button subjectAddButton;
    @FXML
    private Button groupAddButton;

    public void addStudent()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("X?ta!");
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:src/com/aydinnajafov/teqaud/data/coins(1).png"));
        if ((this.groupNumber.getText().isEmpty()) || (this.groupFaculty.getText().isEmpty()))
        {
            alert.setTitle("X?ta!");
            alert.setHeaderText("Z?hm?t olmasa qrup ad?n? v? fakult?sini daxil edin!");
            alert.showAndWait();
        }
        else if (this.studentName.getText().isEmpty())
        {
            alert.setHeaderText("T?l?b?nin ad?n daxil edin!");
            alert.showAndWait();
        }
        else
        {
            System.out.println(this.studentName.getText());
            String name = this.studentName.getText();
            Student student = new Student(name);
            student.setGroup(this.groupNumber.getText());
            student.setFacultyName(this.groupFaculty.getText());
            this.studentList.add(student);
            this.studentName.clear();
        }
    }

    public void addSubject()
    {
        if ((this.subjectName.getText().isEmpty()) || (this.subjectCredit.getText().isEmpty()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:src/com/aydinnajafov/teqaud/data/coins(1).png"));

            alert.setTitle("X?ta");
            alert.setHeaderText("Yaln?? daxiletm?");
            alert.setContentText("F?nn ?lav? olunark?n ad v? kreditin h?r ikisi daxil edilm?lidir!");
            alert.showAndWait();
        }
        else if ((!this.subjectName.getText().isEmpty()) || (!this.subjectCredit.getText().isEmpty()))
        {
            try
            {
                String name = this.subjectName.getText();
                double credit = Double.valueOf(this.subjectCredit.getText()).doubleValue();
                this.subjectList.add(new Subject(name, credit));
                this.subjectName.clear();
                this.subjectCredit.clear();
            }
            catch (NumberFormatException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:src/com/aydinnajafov/teqaud/data/coins(1).png"));

                alert.setTitle("X?ta");
                alert.setHeaderText("Yaln?? daxiletm?");
                alert.setContentText("Z?hm?t olmasa krediti d�zg�n daxil edin");
                alert.showAndWait();
            }
        }
        else if ((this.groupNumber.getText().isEmpty()) || (this.groupFaculty.getText().isEmpty()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:src/com/aydinnajafov/teqaud/data/coins(1).png"));

            alert.setTitle("X?ta!");
            alert.setHeaderText("Z?hm?t olmasa qrup ad?n? v? fakult?sini daxil edin!");
            alert.showAndWait();
        }
    }

    public Group addGroup()
    {
        if ((this.groupNumber.getText().isEmpty()) || (this.groupFaculty.getText().isEmpty())) {
            return null;
        }
        String group = this.groupNumber.getText();
        String faculty = this.groupFaculty.getText();
        return new Group(group, faculty, this.studentList, this.subjectList);
    }
}
