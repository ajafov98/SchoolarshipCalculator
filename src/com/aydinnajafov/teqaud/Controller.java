package com.aydinnajafov.teqaud;

import com.aydinnajafov.teqaud.data.Group;
import com.aydinnajafov.teqaud.data.GroupData;
import com.aydinnajafov.teqaud.data.StudentData;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Optional;
import javafx.application.Platform;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Controller
{
    @FXML
    BorderPane mainPane;
    @FXML
    TableView<Group> tableView;
    @FXML
    MenuItem addFile;
    @FXML
    MenuItem deleteFile;
    @FXML
    MenuItem guide;
    @FXML
    MenuItem about;
    @FXML
    MenuItem exit;
    @FXML
    TableColumn group;
    @FXML
    TableColumn facultyName;
    @FXML
    TableColumn studentSize;
    @FXML
    TableColumn subjectSize;
    @FXML
    TextField studentNumberField;
    private StudentData studentData;
    private GroupData groupData;
    Image image = new Image("file:src/com/aydinnajafov/teqaud/data/coins(1).png");

    public void initialize()
    {
        this.groupData = new GroupData();
        try
        {
            this.tableView.setItems(this.groupData.getGroupsList());
        }
        catch (Exception e)
        {
            System.out.println("No group found!");
        }
    }

    public void addGroupButton()
            throws IOException
    {
        Dialog<ButtonType> dialog = new Dialog();
        dialog.setTitle("Yeni qrup");
        dialog.initOwner(this.mainPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("newGroupDialog.fxml"));
        dialog.getDialogPane().setContent((Node)fxmlLoader.load());
        NewGroupController controller = (NewGroupController)fxmlLoader.getController();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        Optional<ButtonType> result = dialog.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            if (controller.addGroup() == null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Xəta!");
                ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(this.image);
                alert.setHeaderText("Zəhmət olmasa qrup adını və fakultəsini daxil edin!");
                alert.showAndWait();
            }
            else
            {
                Group group = controller.addGroup();
                this.groupData.addGroup(group);
            }
        }
    }

    public void groupStudents()
            throws IOException
    {
        try
        {
            Group group = (Group)this.tableView.getSelectionModel().getSelectedItem();
            Dialog<ButtonType> dialog = new Dialog();
            dialog.setTitle(group.getGroupNumber() + " Qrup t?l?b?l?ri");
            dialog.initOwner(this.mainPane.getScene().getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("groupStudents.fxml"));
            dialog.getDialogPane().setContent((Node)fxmlLoader.load());
            GroupStudents controller = (GroupStudents)fxmlLoader.getController();
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            controller.showStudents(this.groupData.getStudentofGroup(group), this.groupData.getSubjectofGroup(group), group.getGroupNumber(), group.getFacultyName());
            Optional<ButtonType> result = dialog.showAndWait();
            if ((result.isPresent()) && (result.get() == ButtonType.OK))
            {
                this.groupData.editGroupStudents(group, controller.getStudents());
                this.tableView.setItems(this.groupData.getGroupsList());
            }
        }
        catch (NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(this.image);
            alert.setHeaderText("Qrup seçilməyib!");
            alert.setContentText("Zəhmət olmasa qrup seçin");
            alert.show();
        }
    }

    public void groupSubjects()
            throws IOException
    {
        try
        {
            Group group = (Group)this.tableView.getSelectionModel().getSelectedItem();
            Dialog<ButtonType> dialog = new Dialog();
            dialog.setTitle(group.getGroupNumber() + " Qrup f?nnl?ri");
            dialog.initOwner(this.mainPane.getScene().getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("groupSubjects.fxml"));
            dialog.getDialogPane().setContent((Node)fxmlLoader.load());
            GroupSubjects controller = (GroupSubjects)fxmlLoader.getController();
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            controller.showSubjects(this.groupData.getSubjectofGroup(group));
            Optional<ButtonType> result = dialog.showAndWait();
            if ((result.isPresent()) && (result.get() == ButtonType.OK))
            {
                this.groupData.editGroupSubjects(group, controller.getSubjects());
                this.tableView.setItems(this.groupData.getGroupsList());
            }
        }
        catch (NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xəta");
            alert.setHeaderText("Qrup seçilməyib!");
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(this.image);
            alert.setContentText("Zəhmət olmasa qrup seçin");
            alert.show();
        }
    }

    @FXML
    private void deleteGroup()
    {
        Group group = (Group)this.tableView.getSelectionModel().getSelectedItem();
        if (group == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(this.image);
            alert.setTitle("Xəta");
            alert.setHeaderText("Qrup seçilməyib");
            alert.setContentText("Zəhmət olmasa siləcəyiniz qrupu seçin");
            alert.showAndWait();
        }
        else
        {
            ButtonType yes = new ButtonType("Bəli", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("Xeyir", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", new ButtonType[] { yes, no });
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(this.image);
            alert.setTitle("Qrupun silinməsi");
            alert.setHeaderText(group.getGroupNumber() + " №-li qrupu silmək istəditinizdən əminsiniz?");
            Optional<ButtonType> result = alert.showAndWait();
            if ((result.isPresent()) && (result.get() == yes)) {
                this.groupData.removeGroup(group);
            }
        }
    }

    public void showResult()
    {
        boolean empty = false;
        for (Group group : this.groupData.getGroupsList()) {
            if (group.getStudentList().isEmpty()) {
                empty = true;
            }
        }
        if (!empty) {
            try
            {
                Object resultDialog = new Dialog();
                ((Dialog)resultDialog).initOwner(this.mainPane.getScene().getWindow());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("result.fxml"));
                try
                {
                    ((Dialog)resultDialog).getDialogPane().setContent((Node)fxmlLoader.load());
                }
                catch (IOException e)
                {
                    System.out.println("Couldn't load the dialog");
                    e.printStackTrace();
                    return;
                }
                ((Dialog)resultDialog).getDialogPane().getButtonTypes().add(ButtonType.OK);
                ResultController controller = (ResultController)fxmlLoader.getController();
                try
                {
                    Alert alert;
                    if (Integer.parseInt(this.studentNumberField.getText()) == 0) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(this.image);
                        alert.setTitle("Xəta");
                        alert.setHeaderText("Tələbə sayı \"0\" a bərabərdir!");
                        alert.setContentText("Təqaüd alacaq tələbələrin sayı \"0\" ola bilməz zəhmət olmasa tələbə sayın düzgün daxil edin");
                        alert.show();
                    }
                }
                catch (NumberFormatException e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(this.image);
                    alert.setTitle("Xəta");
                    alert.setHeaderText("Tələbə sayı daxil edilməyib!");
                    alert.setContentText("Zəhmət olmasa təqaüd alacaq tələbələrin sayını daxil edin");
                    alert.show();
                }
                catch (IndexOutOfBoundsException e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(this.image);
                    alert.setTitle("Xəta");
                    alert.setHeaderText("Siyahı boşdur");
                    alert.setContentText("Zəhmət olmasa tələbələri daxil edin");
                    alert.show();
                }
            }
            catch (NullPointerException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(this.image);
                alert.setTitle("Xəta");
                alert.setHeaderText("Boş siyahı");
                alert.setContentText("Zəhmət olmasa tələbələri qruplar üzrə daxil edin");
                alert.show();
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(this.image);
        alert.setTitle("Xəta");
        alert.setHeaderText("Boş qrup");
        alert.setContentText("Zəhmət olmasa bütün qruplara tələbələri əlavə edin və ya boş qrupları silin");
        alert.show();
    }

    public void userGuide()
            throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(this.image);
        alert.setTitle("Istifadəçi təlimatə");
        alert.setHeaderText("Proqram təminatının istifadə qaydaları aşağıdakı kimidir:");
        alert.setContentText("*Təqaüd siyahısının hazırlanması üçün hət bir qrup, onun fənnləri və tələbələri ayrı - ayrılıqda daxil edilməlidir.\n" +
                "*\"Fayl\" menusu:\n" +
                "*Yeni qrup əlavə etmək üçün \"Əlavə et\" bölməsinə daxil olun\n" +
                "*Qrupun tələbələrini redaktə etmək üçün \"Tələbələrin redaktəsi\" bölməsinə daxil olun, buradan həm də balları daxil edə bilərsiz\n" +
                "*Qrupun fənnlərini redaktə etmək üçün \"Fənnlərin redaktəsi\" bölməsinə daxil olun\n" +
                "*Qrupu silmək üçün, onu seçib \"Sil\" düyməsini seçin\n" +
                "*Nəticələri hesablamaq üçün ilk əvvəl ekranın aşağı hissəsində təqaüd alacaq tələbələrin sayın daxil edin daha sonra \"Nəticə\" düyməsini seçin\n" +
                "*\"Əlavə\" menusu:\n" +
                "*İstifadəçi təlimatları üçün \"İstifadəçi təlimatı\" bölməsinə daxil olun\n" +
                "*Tərtibatçı və proqram haqqında məlumat üçün \"Haqqında\" bölməsinə daxil olun\n" +
                "*Proqramdan çıxmaq üçün \"Çıxış\" düyməsini seçin\n" +
                "*\"Qrupun tələbləri\" bölməsi:\n" +
                "*Yeni tələbə əlavə etmək üçün \"Yeni\" düyməsini basıb adı daxil edib \"Təsdiqlə\" düyməsini seçin\n" +
                "*Tələbələri redaktə etmək üçün istədiyiniz tələbəni seçib redaktə et düyməsini basın, redaktəni bitirdikdən sonra \"Təsdiqlə\" düyməsin seçin\n" +
                "*Silmək istədiyiniz tələbəni seçərək \"Sil\" düyməsini seçin\n" +
                "*Tələbələrin ballarını daxil etmək üçün alt menunu (adətən mausun sağ düyməsi) açaraq \"Qiymətlərin əlavəsi\" düyməsini seçin\n" +
                "*\"Qiymətlərin əlavəsi\" bölməsi:\n" +
                "*Fənnləri seçərək uyğun xanaya qiymətləri daxil edin\n" +
                "*\"Qrupun fənnləri\" bölməsi:\n" +
                "*Yeni fənn əlavə etmək üçün \"Yeni\" düyməsini basıb adı və krediti daxil edib \"Təsdiqlə\" düyməsini seçin\n" +
                "*Fənnləri redaktə etmək üçün istədiyiniz fənni seçib redaktə et düyməsini basın, redaktəni bitirdikdən sonra \"Təsdiqlə\" düyməsin seçin\n" +
                "*Silmək istədiyiniz fənni seçərək \"Sil\" düyməsini seçin\n");

        alert.showAndWait();
    }

    public void about()
    {
        ButtonType buttonType = new ButtonType("Ba?la", ButtonBar.ButtonData.CANCEL_CLOSE);
        String about = "Versiya 2.0\n\n Bütün hüquqlar qorunur.\n Tərtibatçı: Aydın Nəcəfov\n E-mail: aydinnecefov@gmail.com\n Icon made by Freepik from www.flaticon.com";

        Alert alert = new Alert(Alert.AlertType.INFORMATION, about, new ButtonType[] { buttonType });
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(this.image);
        alert.setTitle("Haqqında");
        alert.getDialogPane();
        Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
        Image image = new Image("file:src/com/aydinnajafov/teqaud/data/coins(3).png");
        stage.getIcons().add(image);
        ImageView imageView = new ImageView(image);
        alert.setGraphic(imageView);
        alert.setHeaderText("Təqaüd siyahısı");
        alert.showAndWait();
    }

    public void exit() {}
}
