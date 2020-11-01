package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Detection {

	private Random rng;
	
	
	public Detection() {
		this.rng = new Random(12345);;
	}

	/**
	 * Launch test
	 * 
	 * @return the {@link Image} to show
	 */
	public Mat processImage(String cheminAccesImage,
							double hueStart, double saturationStart, double valueStart,
							double hueStop, double saturationStop, double valueStop,
							ObjectProperty<String> hsvValuesProp,
							ImageView originalFrame, ImageView maskImage, ImageView morphImage,
							Label nbOeufsDetecte
							)
	{
		Mat frame = new Mat();
		
		frame = Imgcodecs.imread(cheminAccesImage);
		
		// if the frame is not empty, process it
		if (!frame.empty())
		{
			// init
			Mat blurredImage = new Mat();
			Mat hsvImage = new Mat();
			Mat mask = new Mat();
			Mat morphOutput = new Mat();
			
			// remove some noise
			Imgproc.blur(frame, blurredImage, new Size(7, 7));
			
			// convert the frame to HSV
			Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);
			
			// get thresholding values from the UI
			// remember: H ranges 0-180, S and V range 0-255
			Scalar minValues = new Scalar(hueStart, saturationStart, valueStart);
			Scalar maxValues = new Scalar(hueStop, saturationStop, valueStop);
			
			// show the current selected HSV range
			String valuesToPrint = "Hue range: " + minValues.val[0] + "-" + maxValues.val[0]
					+ "\tSaturation range: " + minValues.val[1] + "-" + maxValues.val[1] + "\tValue range: "
					+ minValues.val[2] + "-" + maxValues.val[2];
			Utils.onFXThread(hsvValuesProp, valuesToPrint);
			
			// threshold HSV image to select eggs
			Core.inRange(hsvImage, minValues, maxValues, mask);
			// show the partial output
			this.updateImageView(maskImage, Utils.mat2Image(mask));
			
			// morphological operators
			// dilate with large element, erode with small ones
			Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24));
			Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(12, 12));
			
			Imgproc.erode(mask, morphOutput, erodeElement);
			Imgproc.erode(morphOutput, morphOutput, erodeElement);
			
			Imgproc.dilate(morphOutput, morphOutput, dilateElement);
			Imgproc.dilate(morphOutput, morphOutput, dilateElement);
			
			// show the partial output
			this.updateImageView(morphImage, Utils.mat2Image(morphOutput));
			
			// find the tennis ball(s) contours and show them
			frame = this.findAndDrawEggs(morphOutput, frame, nbOeufsDetecte);
			
			
			
			// if the frame is not empty, print it
			if (!frame.empty())
			{
				// convert and show the frame
				Image imageToShow = Utils.mat2Image(frame);
				updateImageView(originalFrame, imageToShow);
			}
			
			
			return frame;
		}
		return frame;
	}
	
	/**
	 * Given a binary image containing one or more closed surfaces, use it as a
	 * mask to find and highlight the objects contours
	 * 
	 * @param maskedImage
	 *            the binary image to be used as a mask
	 * @param frame
	 *            the original {@link Mat} image to be used for drawing the
	 *            objects contours
	 * @return the {@link Mat} image with the objects contours framed
	 */
	private Mat findAndDrawEggs(Mat maskedImage, Mat frame, Label nbOeufsDetecte)
	{
		// init
		List<MatOfPoint> contours = new ArrayList<>();
		Mat hierarchy = new Mat();
		
		// find contours
		Imgproc.findContours(maskedImage, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
		
		//Boucle permettant de detecter les ellipses au sein des contours
		RotatedRect[] minRect = new RotatedRect[contours.size()];
        RotatedRect[] minEllipse = new RotatedRect[contours.size()];
        
        int nbOeufs = 0;
        
        for (int i = 0; i < contours.size(); i++) {
            minRect[i] = Imgproc.minAreaRect(new MatOfPoint2f(contours.get(i).toArray()));
            minEllipse[i] = new RotatedRect();
            if (contours.get(i).rows() > 5) {
                minEllipse[i] = Imgproc.fitEllipse(new MatOfPoint2f(contours.get(i).toArray()));
                
                if (minEllipse[i].size.height<= 700 && minEllipse[i].size.width<=700 && minEllipse[i].size.height >= 300 && minEllipse[i].size.width >= 300) {
                    //System.out.println("Oeuf n°:" + /*oeuf + */" SIZE_height " + minEllipse[i].size.height);
                    //System.out.println("Oeuf n°:" + /*oeuf + */" SIZE_width " + minEllipse[i].size.width +"\n");
                    //paper.drawEllipseWithBox(box, fitEllipseColor, 2);
                    nbOeufs++;
                    
                    Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
    	            // contour
    	            Imgproc.drawContours(frame, contours, i, color);
    	            // ellipse
    	            Imgproc.ellipse(frame, minEllipse[i], color, 2);
    	            // rotated rectangle
    	            Point[] rectPoints = new Point[4];
    	            minRect[i].points(rectPoints);
    	            for (int j = 0; j < 4; j++) {
    	                Imgproc.line(frame, rectPoints[j], rectPoints[(j+1) % 4], color, 50);
    	            }

                }
            }
        }
        
        //System.out.println("Nb oeufs detectes: "+nbOeufs);
		nbOeufsDetecte.textProperty().set("Nb oeufs detecte: "+nbOeufs);
		
		return frame;
	}
	
	
	/**
	 * Update the {@link ImageView} in the JavaFX main thread
	 * 
	 * @param view
	 *            the {@link ImageView} to update
	 * @param image
	 *            the {@link Image} to show
	 */
	private void updateImageView(ImageView view, Image image)
	{
		Utils.onFXThread(view.imageProperty(), image);
	}
	
}
