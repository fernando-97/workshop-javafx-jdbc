package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import sample.Main;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentListController implements Initializable {
    private DepartmentDao departmentDao;
    private ObservableList<Department> departments;

    @FXML
    private Button btNew;

    @FXML
    private TableView<Department> tableViewDepartment;

    @FXML
    private TableColumn<Department, Integer> tableColumId;

    @FXML
    private TableColumn<Department, String> tableColumName;

    @FXML
    public void onBtNewAction() {
        System.out.println("onBtNewAction");
    }

    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
        setDepartmentDao(DaoFactory.createDepartmentDao());
        updateTableView();
    }

    private void initializeNodes() {
        tableColumId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumName.setCellValueFactory(new PropertyValueFactory<>("name"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView() {
        if (departmentDao == null) {
            throw new IllegalStateException("DepartmentDao não está instanciado");
        }
        else {
            List<Department> list = departmentDao.findAll();
            departments = FXCollections.observableArrayList(list);
            tableViewDepartment.setItems(departments);
        }
    }
}
