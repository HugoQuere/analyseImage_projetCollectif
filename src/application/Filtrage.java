package application;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Filtrage {
	
	Mat image;
	
	int NbPixelTraite =  10;
	int VariationMAXCouleur = 20; 
	int NbVariationMax = 2;
	int nbDirectionsIncorrectMax = 3;
	
	public Filtrage(Mat image) {
		this.image = image;
	}
	
	public Mat processFiltrage() {
		Mat gray_image_original = new Mat();
		Mat gray_image_traite = new Mat();
		Imgproc.cvtColor(image, gray_image_original, Imgproc.COLOR_BGR2GRAY);
		Imgproc.cvtColor(image, gray_image_traite, Imgproc.COLOR_BGR2GRAY);
		
		for (int y = 0; y < gray_image_original.rows(); y++) { //Déplacement sur les lignes
			for (int x = 0; x < gray_image_original.cols(); x++) { //Déplacement sur les colonnes

				double IntensityOrigin = gray_image_original.get(y, x)[0]; //Intensité de la couleur en niveaux de gris (compris entre 0 et 255)

				int nbDirectionsIncorrect = 0; //Avec trop de variations

				//Branche 1
				int nbVariationsCouleur = 0;
				int emplacementXActuel = x + 1;
				int emplacementYActuel = y;
				for (int i = 0; i < NbPixelTraite && emplacementXActuel < gray_image_original.cols() && emplacementYActuel < gray_image_original.rows(); i++) {

					double currentIntensity = gray_image_original.get(y, x)[0]; //Intensité de la couleur en niveaux de gris (compris entre 0 et 255)
					int variationCouleur = (int) Math.abs(IntensityOrigin - currentIntensity);
					//std::cout << "Variation couleur: " << variationCouleur << std::endl;

					if (variationCouleur > VariationMAXCouleur) {//Variation trop importante
						nbVariationsCouleur++;
					}

					emplacementXActuel++;
				}
				if (nbVariationsCouleur > NbVariationMax) {
					nbDirectionsIncorrect++;
				}


				//Branche 2
				nbVariationsCouleur = 0;
				emplacementXActuel = x + 1;
				emplacementYActuel = y + 1;
				for (int i = 0; i < NbPixelTraite && emplacementXActuel < gray_image_original.cols() && emplacementYActuel < gray_image_original.rows(); i++) {

					double currentIntensity = gray_image_original.get(y, x)[0]; //Intensité de la couleur en niveaux de gris (compris entre 0 et 255)
					int variationCouleur = (int) Math.abs(IntensityOrigin - currentIntensity);
					//std::cout << "Variation couleur: " << variationCouleur << std::endl;

					if (variationCouleur > VariationMAXCouleur) {//Variation trop importante
						nbVariationsCouleur++;
					}

					emplacementXActuel++;
					emplacementYActuel++;
				}
				if (nbVariationsCouleur > NbVariationMax) {
					nbDirectionsIncorrect++;
				}


				//Branche 3
				nbVariationsCouleur = 0;
				emplacementXActuel = x;
				emplacementYActuel = y + 1;
				for (int i = 0; i < NbPixelTraite && emplacementXActuel < gray_image_original.cols() && emplacementYActuel < gray_image_original.rows(); i++) {

					double currentIntensity = gray_image_original.get(y, x)[0]; //Intensité de la couleur en niveaux de gris (compris entre 0 et 255)
					int variationCouleur = (int)Math.abs(IntensityOrigin - currentIntensity);
					//std::cout << "Variation couleur: " << variationCouleur << std::endl;

					if (variationCouleur > VariationMAXCouleur) {//Variation trop importante
						nbVariationsCouleur++;
					}

					emplacementYActuel++;
				}
				if (nbVariationsCouleur > NbVariationMax) {
					nbDirectionsIncorrect++;
				}


				//Branche 4
				nbVariationsCouleur = 0;
				emplacementXActuel = x - 1;
				emplacementYActuel = y + 1;
				for (int i = 0; i < NbPixelTraite && emplacementXActuel > 0 && emplacementYActuel < gray_image_original.rows(); i++) {

					double currentIntensity = gray_image_original.get(y, x)[0]; //Intensité de la couleur en niveaux de gris (compris entre 0 et 255)
					int variationCouleur = (int)Math.abs(IntensityOrigin - currentIntensity);
					//std::cout << "Variation couleur: " << variationCouleur << std::endl;

					if (variationCouleur > VariationMAXCouleur) {//Variation trop importante
						nbVariationsCouleur++;
					}

					emplacementXActuel--;
					emplacementYActuel++;
				}
				if (nbVariationsCouleur > NbVariationMax) {
					nbDirectionsIncorrect++;
				}


				//Branche 5
				nbVariationsCouleur = 0;
				emplacementXActuel = x - 1;
				emplacementYActuel = y;
				for (int i = 0; i < NbPixelTraite && emplacementXActuel > 0 && emplacementYActuel < gray_image_original.rows(); i++) {

					double currentIntensity = gray_image_original.get(y, x)[0]; //Intensité de la couleur en niveaux de gris (compris entre 0 et 255)
					int variationCouleur = (int)Math.abs(IntensityOrigin - currentIntensity);
					//std::cout << "Variation couleur: " << variationCouleur << std::endl;

					if (variationCouleur > VariationMAXCouleur) {//Variation trop importante
						nbVariationsCouleur++;
					}

					emplacementXActuel--;
				}
				if (nbVariationsCouleur > NbVariationMax) {
					nbDirectionsIncorrect++;
				}


				//Branche 6
				nbVariationsCouleur = 0;
				emplacementXActuel = x - 1;
				emplacementYActuel = y - 1;
				for (int i = 0; i < NbPixelTraite && emplacementXActuel > 0 && emplacementYActuel > 0; i++) {

					double currentIntensity = gray_image_original.get(y, x)[0]; //Intensité de la couleur en niveaux de gris (compris entre 0 et 255)
					int variationCouleur = (int)Math.abs(IntensityOrigin - currentIntensity);
					//std::cout << "Variation couleur: " << variationCouleur << std::endl;

					if (variationCouleur > VariationMAXCouleur) {//Variation trop importante
						nbVariationsCouleur++;
					}

					emplacementXActuel--;
					emplacementYActuel--;
				}
				if (nbVariationsCouleur > NbVariationMax) {
					nbDirectionsIncorrect++;
				}


				//Branche 7
				nbVariationsCouleur = 0;
				emplacementXActuel = x;
				emplacementYActuel = y - 1;
				for (int i = 0; i < NbPixelTraite && emplacementXActuel > 0 && emplacementYActuel > 0; i++) {

					double currentIntensity = gray_image_original.get(y, x)[0]; //Intensité de la couleur en niveaux de gris (compris entre 0 et 255)
					int variationCouleur = (int)Math.abs(IntensityOrigin - currentIntensity);
					//std::cout << "Variation couleur: " << variationCouleur << std::endl;

					if (variationCouleur > VariationMAXCouleur) {//Variation trop importante
						nbVariationsCouleur++;
					}

					emplacementYActuel--;
				}
				if (nbVariationsCouleur > NbVariationMax) {
					nbDirectionsIncorrect++;
				}


				//Branche 8
				nbVariationsCouleur = 0;
				emplacementXActuel = x + 1;
				emplacementYActuel = y - 1;
				for (int i = 0; i < NbPixelTraite && emplacementXActuel < gray_image_original.cols() && emplacementYActuel > 0; i++) {

					double  currentIntensity = gray_image_original.get(emplacementYActuel, emplacementXActuel)[0]; //Intensité de la couleur en niveaux de gris (compris entre 0 et 255)
					int variationCouleur = (int)Math.abs(IntensityOrigin - currentIntensity);
					//std::cout << "Variation couleur: " << variationCouleur << std::endl;

					if (variationCouleur > VariationMAXCouleur) {//Variation trop importante
						nbVariationsCouleur++;
					}

					emplacementXActuel++;
					emplacementYActuel--;
				}
				if (nbVariationsCouleur > NbVariationMax) {
					nbDirectionsIncorrect++;
				}

				if (nbDirectionsIncorrect > nbDirectionsIncorrectMax) {
					gray_image_traite.get(y, x)[0] = 0; //noir
				}
				else {
					gray_image_traite.get(y, x)[0] = 255; //blanc
				}
			}
		}

		return gray_image_traite;
	}
	
}
