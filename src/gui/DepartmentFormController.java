package gui;

import gui.util.Constraints;
import gui.util.Subject;
import gui.util.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentFormController implements Initializable, Subject {
    private Department department;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label lbelErrorName;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    @FXML
    public void onBtSaveAction() {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        Department department = new Department();
        Integer id = Utils.tryParseToInt(txtId.getText());

        department.setName(txtName.getText());

        if (id == null) {
            departmentDao.insert(department);
        }
        else {
            departmentDao.update(department);
        }
        notify(department);
        Utils.closeWindow(btCancel);
    }

    public void onBtCancelAction() {
        Utils.closeWindow(btCancel);
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }

}
