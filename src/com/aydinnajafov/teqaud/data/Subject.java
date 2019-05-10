package com.aydinnajafov.teqaud.data;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Subject
{
    private SimpleStringProperty subjectName = new SimpleStringProperty("");
    private SimpleDoubleProperty subjectCredit = new SimpleDoubleProperty();
    private SimpleDoubleProperty subjectGrade = new SimpleDoubleProperty();

    public Subject(Subject subject)
    {
        this.subjectName.set(subject.getSubjectName());
        this.subjectCredit.set(subject.getSubjectCredit());
    }

    public Subject(String name, double credits)
    {
        this.subjectName.set(name);
        this.subjectCredit.set(credits);
    }

    public String getSubjectName()
    {
        return this.subjectName.get();
    }

    public SimpleStringProperty subjectNameProperty()
    {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName.set(subjectName);
    }

    public double getSubjectCredit()
    {
        return this.subjectCredit.get();
    }

    public SimpleDoubleProperty subjectCreditProperty()
    {
        return this.subjectCredit;
    }

    public void setSubjectCredit(double subjectCredit)
    {
        this.subjectCredit.set(subjectCredit);
    }

    public double getSubjectGrade()
    {
        return this.subjectGrade.get();
    }

    public SimpleDoubleProperty subjectGradeProperty()
    {
        return this.subjectGrade;
    }

    public void setSubjectGrade(double subjectGrade)
    {
        this.subjectGrade.set(subjectGrade);
    }
}
