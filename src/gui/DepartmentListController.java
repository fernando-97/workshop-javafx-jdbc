package gui;

import gui.util.Alerts;
import gui.util.Listener;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentListController implements Initializable, Listener {
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
    public void onBtNewAction(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        createDialogForm(parentStage, "/gui/DepartmentForm.fxml");
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

    private void createDialogForm(Stage stage, String absoluteName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();
            DepartmentFormController controller = loader.getController();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Department Data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(stage);
            controller.addListener(this);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getCause().toString(), Alert.AlertType.ERROR);
        }
        catch (Exception e) {
            System.out.println(this);;
        }
    }

    @Override
    public void update(Object obj) {
        updateTableView();
    }
}
