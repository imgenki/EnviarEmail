package dad.javafx.enviaremail;

import java.io.IOException;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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
		}catch(Exception ex) {
			ex.printStackTrace();
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
		
	public GridPane getView() {
		return view;
	}

}
