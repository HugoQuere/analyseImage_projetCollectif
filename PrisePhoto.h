#pragma once
#include <stdio.h>
#include <opencv2/opencv.hpp>
#include <iostream>
#include <vector>
#include <stdio.h>
#include <string>

using namespace std;
using namespace cv;

class Photo {
public:
	int PrisePhoto(const char * arg1, const char* arg2, const char* arg3, const char* arg4, const char* arg5);
private :
    const char* arg1, *arg2 , *arg3 , *arg4 ,* arg5;
    const char* cLogin = arg1; //"admin"
    const char* cMdp = arg2; //"projetCo2019_202"
    const char* cIp = arg3; //"192.168.137.64"
    const char* cPort = arg4; //"554"
    const char* cFileName = arg4; //"c:\Temp\some.jpg"


};
