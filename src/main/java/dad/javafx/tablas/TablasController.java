package dad.javafx.tablas;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.LocalDateStringConverter;

public class TablasController implements Initializable {
	
	// model
	
	private StringProperty ruta = new SimpleStringProperty();
	private ListProperty<Fichero> ficheros = new SimpleListProperty<Fichero>(FXCollections.observableArrayList());
	
	// view

    @FXML
    private BorderPane view;

    @FXML
    private TextField rutaText;

    @FXML
    private Button listarButton;

    @FXML
    private TableView<Fichero> ficherosTable;

    @FXML
    private TableColumn<Fichero, String> nombreColumn;

    @FXML
    private TableColumn<Fichero, Number> tamanoColumn;

    @FXML
    private TableColumn<Fichero, TipoFichero> tipoColumn;

    @FXML
    private TableColumn<Fichero, LocalDateTime> modificacionColumn;

    @FXML
    private TableColumn<Fichero, Boolean> ejecutableColumn;
    
    public TablasController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TablasView.fxml"));
		loader.setController(this);
		loader.load();
	}
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ruta.bind(rutaText.textProperty());
		ficherosTable.itemsProperty().bind(ficheros);
		
		nombreColumn.setCellValueFactory(v -> v.getValue().nombreProperty());
		tamanoColumn.setCellValueFactory(v -> v.getValue().tamanoProperty());
		tipoColumn.setCellValueFactory(v -> v.getValue().tipoProperty());
		modificacionColumn.setCellValueFactory(v -> v.getValue().ultimaModificacionProperty());
		ejecutableColumn.setCellValueFactory(v -> v.getValue().ejecutableProperty());
	
		nombreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		tipoColumn.setCellFactory(ComboBoxTableCell.forTableColumn(TipoFichero.values()));
		ejecutableColumn.setCellFactory(CheckBoxTableCell.forTableColumn(ejecutableColumn));
		
	}
	
	public BorderPane getView() {
		return view;
	}
	
    @FXML
    void onListarAction(ActionEvent event) {

    	File f = new File(ruta.get());
    	
    	if (!f.exists()) {
    		ficheros.clear();
    		return;
    	}
    	
    	List<File> listado = Arrays.asList(f.listFiles());
    	
    	List<Fichero> fs = 
    			listado.
    				stream().
    				map(f1 -> new Fichero(f1)).
    				collect(Collectors.toList());
    	
    	ficheros.setAll(fs);
    	
    }

}
