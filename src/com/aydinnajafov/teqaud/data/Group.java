package com.aydinnajafov.teqaud.data;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Group
{
    private SimpleStringProperty groupNumber = new SimpleStringProperty("");
    private SimpleStringProperty facultyName = new SimpleStringProperty("");
    private SimpleIntegerProperty studentSize = new SimpleIntegerProperty();
    private SimpleIntegerProperty subjectSize = new SimpleIntegerProperty();
    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    private ObservableList<Subject> subjectList = FXCollections.observableArrayList();

    public Group(String groupNumber, String facultyName, ObservableList<Student> studentList, ObservableList<Subject> subjectList)
    {
        int sb = subjectList.size();
        int st = studentList.size();
        this.groupNumber.set(groupNumber);
        this.facultyName.set(facultyName);
        this.studentList = studentList;
        this.subjectList = subjectList;
        this.subjectSize.set(sb);
        this.studentSize.set(st);
    }

    public void addStudent(Student student)
    {
        this.studentList.add(student);
    }

    public void addSubject(Subject subject)
    {
        this.subjectList.add(subject);
    }

    public String getGroupNumber()
    {
        return this.groupNumber.get();
    }

    public SimpleStringProperty groupNumberProperty()
    {
        return this.groupNumber;
    }

    public int getStudentSize()
    {
        return this.studentSize.get();
    }

    public SimpleIntegerProperty studentSizeProperty()
    {
        return this.studentSize;
    }

    public void setStudentSize(int studentSize)
    {
        this.studentSize.set(studentSize);
    }

    public int getSubjectSize()
    {
        return this.subjectSize.get();
    }

    public SimpleIntegerProperty subjectSizeProperty()
    {
        return this.subjectSize;
    }

    public void setSubjectSize(int subjectSize)
    {
        this.subjectSize.set(subjectSize);
    }

    public void setGroupNumber(String groupNumber)
    {
        this.groupNumber.set(groupNumber);
    }

    public String getFacultyName()
    {
        return this.facultyName.get();
    }

    public SimpleStringProperty facultyNameProperty()
    {
        return this.facultyName;
    }

    public void setFacultyName(String facultyName)
    {
        this.facultyName.set(facultyName);
    }

    public ObservableList<Student> getStudentList()
    {
        return this.studentList;
    }

    public ObservableList<Subject> getSubjectList()
    {
        return this.subjectList;
    }
}
