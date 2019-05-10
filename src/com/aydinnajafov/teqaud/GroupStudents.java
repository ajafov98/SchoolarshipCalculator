package com.aydinnajafov.teqaud;

import com.aydinnajafov.teqaud.data.Student;
import com.aydinnajafov.teqaud.data.Subject;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GroupStudents
{
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<Student> table;
    @FXML
    private TableColumn rowNumber;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn group;
    @FXML
    private TableColumn faculty;
    @FXML
    private TableColumn gpa;
    @FXML
    private TableColumn schoolarship;
    @FXML
    private Button newStudent;
    @FXML
    private Button edit;
    @FXML
    private Button delete;
    @FXML
    private Button add;
    @FXML
    private TextField newStudentName;
    private ObservableList<Student> studentObservableList = FXCollections.observableArrayList();
    private ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList();
    private boolean editing = false;
    private boolean added = false;
    private String groupName;
    private String facultyName;

    public void showStudents(ObservableList<Student> students, ObservableList<Subject> subjects, String group, String faculty)
    {
        this.groupName = group;
        this.facultyName = faculty;
        Student student;
        for (Iterator localIterator1 = students.iterator(); localIterator1.hasNext();)
        {
            student = (Student)localIterator1.next();
            if (student.getSubjectObservableList().isEmpty()) {
                for (Subject subject : subjects)
                {
                    System.out.println("Everything empty subject adding " + subject.getSubjectName() + " to " + student.getName());
                    student.getSubjectObservableList().add(new Subject(subject));
                }
            } else {
                for (Subject subject : subjects) {
                    if (student.querrySubject(subject))
                    {
                        System.out.println("Cant find subject addind subject " + subject.getSubjectName() + " to " + student.getName());
                        student.getSubjectObservableList().add(new Subject(subject));
                    }
                }
            }
        }

        this.studentObservableList.addAll(students);
        this.subjectObservableList.addAll(subjects);
        this.table.setItems(this.studentObservableList);
    }

    @FXML
    private void newStudentButton()
    {
        this.newStudentName.setVisible(true);
        this.add.setVisible(true);
    }

    @FXML
    private void addButton()
    {
        if (checkNumber(this.newStudentName.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:src/com/aydinnajafov/teqaud/data/coins(1).png"));

            alert.setTitle("X?ta!");
            alert.setHeaderText("Z?hm?t olmasa t?l?b?nin ad?n? h?rfl?rl? daxil edin!");
            alert.showAndWait();
        }
        else if (this.editing)
        {
            Student student = (Student)this.table.getSelectionModel().getSelectedItem();
            student.setName(this.newStudentName.getText());
            this.newStudentName.clear();
            this.newStudentName.setVisible(false);
            this.add.setVisible(false);
            this.editing = false;
            this.table.setDisable(false);
        }
        else
        {
            Alert alert;
            if (this.newStudentName.getText().isEmpty())
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("X?ta!");
                alert.setHeaderText("T?l?b?nin ad?n? daxil edin!");
                alert.showAndWait();
            }
            else
            {
                Student student = new Student(this.newStudentName.getText());
                student.setGroup(this.groupName);
                student.setFacultyName(this.facultyName);
                if (student.getSubjectObservableList().isEmpty()) {
                    for (Subject subject : this.subjectObservableList) {
                        student.getSubjectObservableList().add(new Subject(subject));
                    }
                }
                this.studentObservableList.add(student);
                this.newStudentName.clear();
                this.newStudentName.setVisible(false);
                this.add.setVisible(false);
            }
        }
    }

    @FXML
    private void keyHandler()
    {
        boolean test = (this.newStudentName.getText().isEmpty()) || (this.newStudentName.getText().trim().isEmpty());
        this.add.setDisable(test);
    }

    @FXML
    private void editStudent()
    {
        if (this.table.getSelectionModel().getSelectedItem() != null)
        {
            Student student = (Student)this.table.getSelectionModel().getSelectedItem();
            this.newStudentName.setVisible(true);
            this.add.setVisible(true);
            this.editing = true;
            this.table.setDisable(true);
            this.newStudentName.setText(student.getName());
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:src/com/aydinnajafov/teqaud/data/coins(1).png"));

            alert.setTitle("X?ta!");
            alert.setHeaderText("D?yi?iklik etm?k ist?diyiniz t?l?b?ni se�in!");
            alert.showAndWait();
        }
    }

    public void updateSubjects()
            throws IOException
    {
        try
        {
            Student student = (Student)this.table.getSelectionModel().getSelectedItem();
            Dialog<ButtonType> dialog = new Dialog();
            dialog.setTitle(student.getName() + " adl? t?l?b?nin qiym?tl?ri");
            dialog.initOwner(this.anchorPane.getScene().getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("updateSubject.fxml"));
            dialog.getDialogPane().setContent((Node)fxmlLoader.load());
            UpdateSubject controller = (UpdateSubject)fxmlLoader.getController();
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            controller.showSubjects(student.getSubjectObservableList());
            Optional<ButtonType> result = dialog.showAndWait();
            if ((result.isPresent()) && (result.get() == ButtonType.OK))
            {
                student.setSubjectList(controller.getSubjectList());
                student.updateGpa();
            }
        }
        catch (NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:src/com/aydinnajafov/teqaud/data/coins(1).png"));

            alert.setTitle("X?ta!");
            alert.setHeaderText("T?l?b? se�ilm?yib!");
            alert.showAndWait();
        }
    }

    @FXML
    private void deleteStudent()
    {
        if (this.table.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:src/com/aydinnajafov/teqaud/data/coins(1).png"));

            alert.setTitle("X?ta!");
            alert.setHeaderText("Z?hm?t olmasa silm?k ist?diyiniz t?l?b?nin ad?n se�in!");
            alert.showAndWait();
        }
        ButtonType yes = new ButtonType("B?li", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("Xeyir", ButtonBar.ButtonData.CANCEL_CLOSE);
        Student student = (Student)this.table.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", new ButtonType[] { yes, no });
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:src/com/aydinnajafov/teqaud/data/coins(1).png"));

        alert.setTitle("Sil");
        alert.setHeaderText(student.getName() + " adl? t?l?b?ni silm?k ist?diyinizd?n ?minsiniz?");
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent()) && (result.get() == yes)) {
            this.studentObservableList.remove(student);
        }
    }

    private boolean checkNumber(String s)
    {
        char[] word = s.toCharArray();
        char[] numbers = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
        for (char i : word) {
            for (char j : numbers) {
                if (i == j) {
                    return true;
                }
            }
        }
        return false;
    }

    public ObservableList<Student> getStudents()
    {
        return this.studentObservableList;
    }
}
