package com.aydinnajafov.teqaud.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GroupData
{
    private static ObservableList<Group> groupsList;

    public GroupData()
    {
        groupsList = FXCollections.observableArrayList();
    }

    public void addGroup(Group group)
    {
        groupsList.add(group);
    }

    public void removeGroup(Group group)
    {
        groupsList.remove(group);
    }

    public ObservableList<Group> getGroupsList()
    {
        return groupsList;
    }

    public ObservableList<Student> getStudentofGroup(Group group)
    {
        return group.getStudentList();
    }

    public ObservableList<Subject> getSubjectofGroup(Group group)
    {
        return group.getSubjectList();
    }

    public void editGroupStudents(Group oldGroup, ObservableList<Student> studentList)
    {
        for (int i = 0; i < groupsList.size(); i++) {
            if (((Group)groupsList.get(i)).equals(oldGroup))
            {
                ((Group)groupsList.get(i)).getStudentList().setAll(studentList);
                ((Group)groupsList.get(i)).setStudentSize(studentList.size());
                return;
            }
        }
    }

    public void editGroupSubjects(Group oldGroup, ObservableList<Subject> subjectList)
    {
        for (int i = 0; i < groupsList.size(); i++) {
            if (((Group)groupsList.get(i)).equals(oldGroup))
            {
                ((Group)groupsList.get(i)).getSubjectList().setAll(subjectList);
                ((Group)groupsList.get(i)).setSubjectSize(subjectList.size());
                return;
            }
        }
    }
}
