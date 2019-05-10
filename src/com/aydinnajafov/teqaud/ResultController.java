package com.aydinnajafov.teqaud;

import com.aydinnajafov.teqaud.data.Group;
import com.aydinnajafov.teqaud.data.Student;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ResultController
{
    @FXML
    private BorderPane mainPane;
    @FXML
    private TableView<Student> tableView;
    @FXML
    private Button resultButton;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn facultyColumn;
    @FXML
    private TableColumn groupColumn;
    @FXML
    private TableColumn uomgColumn;
    @FXML
    private TableColumn scholarshipType;
    @FXML
    private TableColumn rowNumber;
    private Alert alert;
    private ObservableList<Group> groupsList = FXCollections.observableArrayList();
    private ObservableList<Student> bigStudentsList = FXCollections.observableArrayList();

    public void showResults(ObservableList<Group> groupsList, int numberOfStudents)
    {
        this.groupsList = groupsList;
        while (numberOfStudents > 0) {
            for (Group group : groupsList) {
                for (int j = 0; j < group.getStudentList().size(); j++) {
                    if (!((Student)group.getStudentList().get(j)).getScholarshipType().equals("Alm?r"))
                    {
                        this.bigStudentsList.add(group.getStudentList().get(j));
                        numberOfStudents--;
                    }
                }
            }
        }
        Collections.sort(this.bigStudentsList);
        Collections.reverse(this.bigStudentsList);
        for (int i = 0; i < this.bigStudentsList.size(); i++) {
            ((Student)this.bigStudentsList.get(i)).setRowNumber(i + 1);
        }
        this.tableView.setItems(this.bigStudentsList);
    }

    public void printResult()
            throws IOException
    {
        BufferedWriter writer = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text", new String[] { ".txt" }));
        fileChooser.setTitle("Yadda saxlamaq");

        File file = fileChooser.showSaveDialog(this.mainPane.getScene().getWindow());
        try
        {
            writer = new BufferedWriter(new FileWriter(file));
            for (Student student : this.bigStudentsList) {
                writer.write(String.format("%d %s %s %s %.02f %s", new Object[] { Integer.valueOf(student.getRowNumber()), student.getName(), student.getFacultyName(), student
                        .getGroup(), Double.valueOf(student.getGpa()), student.getScholarshipType() }));
            }
        }
        finally
        {
            writer.close();
        }
    }
}
