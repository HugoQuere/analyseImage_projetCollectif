package application;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import javafx.scene.image.Image;

public class Camera {
	
	String Login = "admin";
	String Mdp = "projetCo2019_202";
	String Ip = "192.168.137.64";
	String Port = "554";
	String FileName = "c:\\Temp\\some.jpg";

	String scheme = "rtsp://";


	VideoCapture vcap;
	Mat image;

	//Build LINK  "rtsp://admin:projetCo2019_202@192.168.137.64:554"
	String videoStreamAddress = scheme + Login + ':' + Mdp + '@' + Ip + ':' + Port;

	
	public Camera() {
		vcap = new VideoCapture();
		image = new Mat();
	}


	public Mat captureImage() {
		//open the video stream and make sure it's opened
		if (!vcap.open(videoStreamAddress)) {
			System.out.println("Error opening video stream or file");
		}

		//photo
		if (!vcap.read(image)) {
			System.out.println("No frame");
			//HighGui.waitKey();
		}
		
		return image;
	}

	

}
