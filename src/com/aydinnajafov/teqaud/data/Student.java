package com.aydinnajafov.teqaud.data;

import com.sun.istack.internal.NotNull;
import java.io.PrintStream;
import java.io.Serializable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Student
        implements Comparable<Student>, Serializable
{
    private SimpleIntegerProperty rowNumber = new SimpleIntegerProperty();
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty group = new SimpleStringProperty("");
    private SimpleStringProperty facultyName = new SimpleStringProperty("");
    private ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList();
    private SimpleDoubleProperty gpa = new SimpleDoubleProperty();
    private SimpleStringProperty scholarshipType = new SimpleStringProperty("");

    public Student(String name)
    {
        this.name.set(name);
    }

    public Student(String name, ObservableList<Subject> subjects)
    {
        this.subjectObservableList.addAll(subjects);
    }

    public void addSubjectList(ObservableList<Subject> subjectList)
    {
        this.subjectObservableList.addAll(subjectList);
        this.gpa.set(gpaCalculator());
        this.scholarshipType.set(scholarshipIdentification(this.subjectObservableList));
    }

    private String scholarshipIdentification(ObservableList<Subject> subjectList)
    {
        int A = 0;int B = 0;int C = 0;int D = 0;int E = 0;int F = 0;
        for (Subject subject : subjectList) {
            if (subject.getSubjectGrade() <= 50.0D) {
                F++;
            } else if ((subject.getSubjectGrade() >= 51.0D) && (subject.getSubjectGrade() <= 60.0D)) {
                E++;
            } else if ((subject.getSubjectGrade() >= 61.0D) && (subject.getSubjectGrade() <= 70.0D)) {
                D++;
            } else if ((subject.getSubjectGrade() >= 71.0D) && (subject.getSubjectGrade() <= 80.0D)) {
                C++;
            } else if ((subject.getSubjectGrade() >= 81.0D) && (subject.getSubjectGrade() <= 90.0D)) {
                B++;
            } else {
                A++;
            }
        }
        System.out.printf("%d, %d, %d, %d, %d, %d ", new Object[] { Integer.valueOf(A), Integer.valueOf(B), Integer.valueOf(C), Integer.valueOf(D), Integer.valueOf(E), Integer.valueOf(F) });
        if (F != 0) {
            this.scholarshipType.set("Alm?r");
        } else if ((A != 0) && ((B != 0) || (C != 0)) && (E == 0) && (D == 0)) {
            this.scholarshipType.set("H?v?sl?ndirici");
        } else if ((A != 0) && (D == 0) && (E == 0)) {
            this.scholarshipType.set("?la�?");
        } else {
            this.scholarshipType.set("Adi");
        }
        return this.scholarshipType.getValue();
    }

    public void scholarshipCalculation()
    {
        scholarshipTypeProperty().set(scholarshipIdentification(this.subjectObservableList));
    }

    public ObservableList<Subject> getSubjectObservableList()
    {
        return this.subjectObservableList;
    }

    public double gpaCalculator()
    {
        double sumOfSubjects = 0.0D;
        double sumOfCredits = 0.0D;
        for (Subject subject : this.subjectObservableList) {
            sumOfSubjects += subject.getSubjectGrade() * subject.getSubjectCredit();
        }
        for (Subject subject : this.subjectObservableList) {
            sumOfCredits += subject.getSubjectCredit();
        }
        return sumOfSubjects / sumOfCredits;
    }

    public boolean gpaCheck()
    {
        return (getGpa() >= 0.0D) && (getGpa() <= 100.0D);
    }

    public void updateGpa()
    {
        this.gpa.set(gpaCalculator());
        this.scholarshipType.set(scholarshipIdentification(this.subjectObservableList));
    }

    public int compareTo(@NotNull Student t)
    {
        if (getGpa() > t.getGpa()) {
            return 1;
        }
        if (getGpa() < t.getGpa()) {
            return -1;
        }
        return 0;
    }

    public boolean querrySubject(Subject newSubject)
    {
        for (Subject subject : this.subjectObservableList) {
            if (subject.getSubjectName().equals(newSubject.getSubjectName())) {
                return false;
            }
        }
        return true;
    }

    public void addSubject(Subject subject)
    {
        this.subjectObservableList.add(subject);
    }

    public int getRowNumber()
    {
        return this.rowNumber.get();
    }

    public SimpleIntegerProperty rowNumberProperty()
    {
        return this.rowNumber;
    }

    public void setRowNumber(int rowNumber)
    {
        this.rowNumber.set(rowNumber);
    }

    public void removeSubject(Subject subject)
    {
        this.subjectObservableList.remove(subject);
    }

    public void setSubjectObservableList(ObservableList<Subject> subjectObservableList)
    {
        this.subjectObservableList = subjectObservableList;
    }

    public String getScholarshipType()
    {
        return this.scholarshipType.get();
    }

    public SimpleStringProperty scholarshipTypeProperty()
    {
        return this.scholarshipType;
    }

    public void setScholarshipType(String scholarshipType)
    {
        this.scholarshipType.set(scholarshipType);
    }

    public String getName()
    {
        return this.name.get();
    }

    public SimpleStringProperty nameProperty()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name.set(name);
    }

    public String getGroup()
    {
        return this.group.get();
    }

    public SimpleStringProperty groupProperty()
    {
        return this.group;
    }

    public void setGroup(String group)
    {
        this.group.set(group);
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

    public void setSubjectList(ObservableList<Subject> subjectList)
    {
        this.subjectObservableList = subjectList;
    }

    public double getGpa()
    {
        return this.gpa.get();
    }

    public SimpleDoubleProperty gpaProperty()
    {
        return this.gpa;
    }

    public void setGpa(double gpa)
    {
        this.gpa.set(gpa);
    }
}
