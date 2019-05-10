package com.aydinnajafov.teqaud.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentData
{
    private static ObservableList<Student> studentsList;

    public StudentData()
    {
        studentsList = FXCollections.observableArrayList();
    }

    public void addStudent(Student student)
    {
        studentsList.add(student);
    }

    public void removeStudent(Student student)
    {
        studentsList.remove(student);
    }

    public ObservableList getStudentSubjectList(Student student)
    {
        ObservableList<Subject> subjectList = FXCollections.observableArrayList();
        for (Subject subject : student.getSubjectObservableList()) {
            subjectList.add(subject);
        }
        return subjectList;
    }

    public void newStudentList(ObservableList<Student> students)
    {
        studentsList.setAll(students);
    }

    public ObservableList<Student> getStudentsList()
    {
        return studentsList;
    }
}
