#include "PrisePhoto.h"



//boite_noire(Login,mdp,ip,port,stockage); sortie la photo dans dossier donnée
//1) Capture d'écrant
int Photo::PrisePhoto(const char* arg1, const char* arg2, const char* arg3, const char* arg4, const char* arg5) {
    
    //récupération des arguments du programme
    
   const char* cLogin =arg1 ; //"admin"
   const char* cMdp = arg2; //"projetCo2019_202"
   const char* cIp = arg3; //"192.168.137.64"
   const char* cPort = arg4; //"554"
   const char* cFileName = arg5; //"c:\Temp\some.jpg"

    //Conversion Char * en String
    string Login(cLogin);
    string Mdp(cMdp);
    string Ip(cIp); 
    string Port(cPort);
    string FileName(cFileName);
    string scheme="rtsp://"; 

    VideoCapture vcap;
    Mat image;
 
    //Build LINK  "rtsp://admin:projetCo2019_202@192.168.137.64:554"
    const string videoStreamAddress = scheme+Login+':'+Mdp+'@'+Ip+':'+Port;
        

    //open the video stream and make sure it's opened
    if (!vcap.open(videoStreamAddress)) {
        cout << "Error opening video stream or file" << std::endl;
        return -1;
    }
    
    //photo
    if (!vcap.read(image)) {
        cout << "No frame" << std::endl;
        waitKey();
    }
    imshow("Output Window", image);
    waitKey(2000);
    
    //SAVE jpg
    imwrite(FileName, image);
 
    //Vidéo
   /*for (;;) {
        if (!vcap.read(image)) {
            std::cout << "No frame" << std::endl;
            cv::waitKey();
        }
        cv::imshow("Output Window", image);
        if (cv::waitKey(1) >= 0) break;
    }*/

    return 0;
}
