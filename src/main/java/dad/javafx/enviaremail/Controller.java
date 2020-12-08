package dad.javafx.enviaremail;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller implements Initializable {
	
	// model
	EmailModel model = new EmailModel();
	// view
	
	@FXML
	private GridPane view;
	
	@FXML
	private TextField servidorText;
	
	@FXML
	private TextField puertoText;
	
	@FXML
	private TextField remitenteText;
	
	@FXML
	private PasswordField remitentePassword;
	
	@FXML
	private CheckBox sslCheckBox;
	
	@FXML
	private TextArea mensajeText;
	
	@FXML
	private TextField destinatarioText;
	
	@FXML
	private TextField asuntoText;
	
	@FXML
	private Button enviarButton;
	
	@FXML
	private Button vaciarButton;
	
	@FXML
	private Button cerrarButton;
	
	@FXML
	void onEnviarButtonAction (ActionEvent e) {
		try {
		model.getEmail().setHostName(servidorText.getText());
		model.getEmail().setSmtpPort(Integer.parseInt(puertoText.getText()));
		model.getEmail().setAuthentication(remitenteText.getText(), remitentePassword.getText());
		model.getEmail().setSSLOnConnect(sslCheckBox.isSelected());
		model.getEmail().setFrom(remitenteText.getText());
		model.getEmail().setSubject(asuntoText.getText());
		model.getEmail().setMsg(mensajeText.getText());
		model.getEmail().addTo(destinatarioText.getText());
		
		model.getEmail().send();
		
		exitoAlert();
		}catch(Exception ex) {
			errorAlert(ex);
		}
	}
	
	@FXML
	void onVaciarButtonAction (ActionEvent e) {
		servidorText.clear();
		puertoText.clear();
		remitenteText.clear();
		remitentePassword.clear();
		sslCheckBox.setSelected(false);
		mensajeText.clear();
		destinatarioText.clear();
		asuntoText.clear();
		
	}
	
	@FXML
	void onCerrarButtonAction (ActionEvent e) {
		Platform.exit();
	}
	
	
	public Controller() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
		loader.setController(this);
		loader.load();
	}
	private void exitoAlert() {
		Alert alert=new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Mensaje enviado con éxito a \""+destinatarioText.getText()+"\".");
		alert.setTitle("Mensaje enviado");
		
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/images/email-send-icon-32x32.png"));

		alert.showAndWait();

	}
	private void errorAlert(Exception ex) {
		Alert alert=new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("No se pudo enviar el email.");
		alert.setContentText("Abra \"Mostrar detalles\" para obtener la información completa");
		
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/images/email-send-icon-32x32.png"));

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText=sw.toString();
		
		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);
		
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(textArea, 0, 0);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
		
	public GridPane getView() {
		return view;
	}

}
