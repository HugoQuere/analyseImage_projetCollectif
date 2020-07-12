// Analyse_Image_CompletV1.cpp : Ce fichier contient la fonction 'main'. L'exécution du programme commence et se termine à cet endroit.
//
#include "PrisePhoto.h"
#include "Filtre.h"
#include "Detection.h"
#include <iostream>


int main()
{
    const char* photoPrise = "\photo1.jpg";
    const char* photoFiltre = "\PhotoFiltre.jpg";

    Photo photo;
    ImageFiltre Filtre;
    Detection Ellipse;

    //1)PrisePhoto
    cout << "Prise de la photo : .....!\n";
    if (photo.PrisePhoto("admin", "projetCo2019_202", "192.168.137.64", "554", photoPrise) == 0)
    {
        //2)Filtre
        cout << "Filtrage de la photo : .....!\n";
        if (Filtre.filtrage("c:\\Temp\\PhotoTest4.jpg") == 0)//"c:\\Temp\\PhotoTest2.jpg"  // photoPrise
        {
            //4)Detection
            cout << "Detection Ellipse : .....!\n\n";
            int nbOeuf = Ellipse.Ellipse(photoFiltre);
            cout << "il y a : "<< nbOeuf <<" oeuf(s)\n";
        }
        else
        {
            cout << "ERREUR FILTRAGE: .....!\n";

        }

    }
    else
    {
        cout << "ERREUR PRISE PHOTO : .....!\n";

    }
}

// Exécuter le programme : Ctrl+F5 ou menu Déboguer > Exécuter sans débogage
// Déboguer le programme : F5 ou menu Déboguer > Démarrer le débogage

// Astuces pour bien démarrer : 
//   1. Utilisez la fenêtre Explorateur de solutions pour ajouter des fichiers et les gérer.
//   2. Utilisez la fenêtre Team Explorer pour vous connecter au contrôle de code source.
//   3. Utilisez la fenêtre Sortie pour voir la sortie de la génération et d'autres messages.
//   4. Utilisez la fenêtre Liste d'erreurs pour voir les erreurs.
//   5. Accédez à Projet > Ajouter un nouvel élément pour créer des fichiers de code, ou à Projet > Ajouter un élément existant pour ajouter des fichiers de code existants au projet.
//   6. Pour rouvrir ce projet plus tard, accédez à Fichier > Ouvrir > Projet et sélectionnez le fichier .sln.
