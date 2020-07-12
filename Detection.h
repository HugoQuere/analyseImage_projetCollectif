#pragma once
#include "opencv2/imgproc.hpp"
#include "opencv2/imgcodecs.hpp"
#include "opencv2/highgui.hpp"
#include <iostream>
using namespace cv;
using namespace std;

class Detection {
public:
    int Ellipse(const char* arg1);
    //Getter
   /* int getNbOeuf() { return NbOeuf; };
    //Setter
    void setNbOeuf(int Oeuf) { NbOeuf = Oeuf; };*/

private:
    const char* arg1;
    //int NbOeuf;
    //const char* imageName = arg1; //"ImageData"



};
