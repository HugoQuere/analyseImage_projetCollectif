#pragma once
#include <opencv2/opencv.hpp>
using namespace cv;
using namespace std;


class ImageFiltre {
public:
    int filtrage(const char* arg1);
private:
    const char* arg1;
    const char* imageName = arg1; //"ImageData"
    


};
