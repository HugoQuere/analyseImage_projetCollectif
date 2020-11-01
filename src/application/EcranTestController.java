package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

public class EcranTestController {
	
	
	// FXML camera button
	@FXML
	private Button testButton;
	// the FXML area for showing the current frame
	@FXML
	private ImageView firstFrame;
	// the FXML area for showing the current frame
	@FXML
	private ImageView originalFrame;
	// the FXML area for showing the mask
	@FXML
	private ImageView maskImage;
	// the FXML area for showing the output of the morphological operations
	@FXML
	private ImageView morphImage;
	// FXML slider for setting HSV ranges
	@FXML
	private Slider hueStart;
	@FXML
	private Slider hueStop;
	@FXML
	private Slider saturationStart;
	@FXML
	private Slider saturationStop;
	@FXML
	private Slider valueStart;
	@FXML
	private Slider valueStop;
	// FXML label to show the current values set with the sliders
	@FXML
	private ComboBox choiceMode;
	
	
	@FXML
	private Label hsvCurrentValues;
	// FXML label to show the current number of egg detected
	@FXML
	private Label nbOeufsDetecte;
	
	// property for object binding
	private ObjectProperty<String> hsvValuesProp;
	
	private Detection detection;
	private Camera camera;
	
	
	
	@FXML
    public void initialize() {
		// bind a text property with the string containing the current range of
		// HSV values for object detection
		hsvValuesProp = new SimpleObjectProperty<>();
		this.hsvCurrentValues.textProperty().bind(hsvValuesProp);
		
		// set a fixed width for all the image to show and preserve image ratio
		this.imageViewProperties(this.originalFrame, 400);
		this.imageViewProperties(this.maskImage, 200);
		this.imageViewProperties(this.morphImage, 200);
		
		//On indique que le test n'a pas été lancé
		this.testButton.setStyle("-fx-text-fill: red; ");
		
		this.choiceMode.getItems().setAll("Camera", "Photo");
		this.choiceMode.setValue("Camera"); //First value
		
		
		detection = new Detection();
		camera = new Camera();
    }
	
	
	
	
	/**
	 * The action triggered by pushing the button on the GUI
	 */
	@FXML
	private void buttonStartTest()
	{
		long startTime = System.currentTimeMillis();
		
		
		//On indique que le test a été lancé
		this.testButton.setStyle("-fx-text-fill: green; ");
		
		
		Mat imageToProcess = new Mat();
		if(this.choiceMode.getValue().equals("Photo")) {
			String cheminAccesImage = "D:\\cours\\PolytechTours\\5A\\ProjetFinEtude_PFE\\Analyse d'image\\version_en_java\\3oeufs.jpg";
			imageToProcess = Imgcodecs.imread(cheminAccesImage);
		} else { //Par camera
			imageToProcess = this.camera.captureImage();
		}
		
		Size sizepicture = imageToProcess.size();
		System.out.println("Height: "+ sizepicture.height);
		System.out.println("Width: "+ sizepicture.width);
		
		this.detection.processImage(imageToProcess, 
			this.hueStart.getValue(), this.saturationStart.getValue(),this.valueStart.getValue(),
			this.hueStop.getValue(), this.saturationStop.getValue(),this.valueStop.getValue(),
			this.hsvValuesProp,
			originalFrame, maskImage, morphImage,
			nbOeufsDetecte
		);
		
		long endTime = System.currentTimeMillis();
		System.out.println("That took " + (endTime - startTime) + " milliseconds");
	}
	
	@FXML
	private void sliderChange() {
		//System.out.println("Detection du changement du slider");
		this.testButton.setStyle("-fx-text-fill: red; ");
	}
	
	
	
	
	
	/**
	 * Set typical {@link ImageView} properties: a fixed width and the
	 * information to preserve the original image ration
	 * 
	 * @param image
	 *            the {@link ImageView} to use
	 * @param dimension
	 *            the width of the image to set
	 */
	private void imageViewProperties(ImageView image, int dimension)
	{
		// set a fixed width for the given ImageView
		image.setFitWidth(dimension);
		// preserve the image ratio
		image.setPreserveRatio(true);
	}
}
