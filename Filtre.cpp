#include "Filtre.h"
#define NbPixelTraite 10
#define VariationMAXCouleur 20 
#define NbVariationMax 2
#define nbDirectionsIncorrectMax 3

int ImageFiltre::filtrage(const char *arg1)
{
	const char* imageName = arg1;
	Mat image;
	image = imread(imageName, IMREAD_COLOR);
	/*if (!image.data)
	{
		printf(" No image data \n ");
		return -1;
	}*/
	Mat gray_image_original;
	Mat gray_image_traite;
	cvtColor(image, gray_image_original, COLOR_BGR2GRAY);
	cvtColor(image, gray_image_traite, COLOR_BGR2GRAY);
	
	for (int y = 0; y < gray_image_original.rows; y++) { //Déplacement sur les lignes
		for (int x = 0; x < gray_image_original.cols; x++) { //Déplacement sur les colonnes

			Scalar IntensityOrigin = gray_image_original.at<uchar>(y, x); //Intensité de la couleur en niveaux de gris (compris entre 0 et 255)

			int nbDirectionsIncorrect = 0; //Avec trop de variations

			//Branche 1
			int nbVariationsCouleur = 0;
			int emplacementXActuel = x + 1;
			int emplacementYActuel = y;
			for (int i = 0; i < NbPixelTraite && emplacementXActuel < gray_image_original.cols && emplacementYActuel < gray_image_original.rows; i++) {

				Scalar currentIntensity = gray_image_original.at<uchar>(emplacementYActuel, emplacementXActuel); //Intensité de la couleur en niveaux de gris (compris entre 0 et 255)
				int variationCouleur = abs(IntensityOrigin.val[0] - currentIntensity.val[0]);
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
			for (int i = 0; i < NbPixelTraite && emplacementXActuel < gray_image_original.cols && emplacementYActuel < gray_image_original.rows; i++) {

				Scalar currentIntensity = gray_image_original.at<uchar>(emplacementYActuel, emplacementXActuel); //Intensité de la couleur en niveaux de gris (compris entre 0 et 255)
				int variationCouleur = abs(IntensityOrigin.val[0] - currentIntensity.val[0]);
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
			for (int i = 0; i < NbPixelTraite && emplacementXActuel < gray_image_original.cols && emplacementYActuel < gray_image_original.rows; i++) {

				Scalar currentIntensity = gray_image_original.at<uchar>(emplacementYActuel, emplacementXActuel); //Intensité de la couleur en niveaux de gris (compris entre 0 et 255)
				int variationCouleur = abs(IntensityOrigin.val[0] - currentIntensity.val[0]);
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
			for (int i = 0; i < NbPixelTraite && emplacementXActuel > 0 && emplacementYActuel < gray_image_original.rows; i++) {

				Scalar currentIntensity = gray_image_original.at<uchar>(emplacementYActuel, emplacementXActuel); //Intensité de la couleur en niveaux de gris (compris entre 0 et 255)
				int variationCouleur = abs(IntensityOrigin.val[0] - currentIntensity.val[0]);
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
			for (int i = 0; i < NbPixelTraite && emplacementXActuel > 0 && emplacementYActuel < gray_image_original.rows; i++) {

				Scalar currentIntensity = gray_image_original.at<uchar>(emplacementYActuel, emplacementXActuel); //Intensité de la couleur en niveaux de gris (compris entre 0 et 255)
				int variationCouleur = abs(IntensityOrigin.val[0] - currentIntensity.val[0]);
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

				Scalar currentIntensity = gray_image_original.at<uchar>(emplacementYActuel, emplacementXActuel); //Intensité de la couleur en niveaux de gris (compris entre 0 et 255)
				int variationCouleur = abs(IntensityOrigin.val[0] - currentIntensity.val[0]);
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

				Scalar currentIntensity = gray_image_original.at<uchar>(emplacementYActuel, emplacementXActuel); //Intensité de la couleur en niveaux de gris (compris entre 0 et 255)
				int variationCouleur = abs(IntensityOrigin.val[0] - currentIntensity.val[0]);
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
			for (int i = 0; i < NbPixelTraite && emplacementXActuel < gray_image_original.cols && emplacementYActuel > 0; i++) {

				Scalar currentIntensity = gray_image_original.at<uchar>(emplacementYActuel, emplacementXActuel); //Intensité de la couleur en niveaux de gris (compris entre 0 et 255)
				int variationCouleur = abs(IntensityOrigin.val[0] - currentIntensity.val[0]);
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
				gray_image_traite.at<uchar>(y, x) = 0;//noir
			}
			else {
				gray_image_traite.at<uchar>(y, x) = 255;//blanc
			}
		}
	}


	imshow("Photo Filtree", gray_image_traite);
	imwrite("\PhotoFiltre.jpg", gray_image_traite);

	//waitKey(0);
	return 0;
}