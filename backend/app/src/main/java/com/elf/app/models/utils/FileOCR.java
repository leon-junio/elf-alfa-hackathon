package com.elf.app.models.utils;

import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

@Service
public class FileOCR {
    public static boolean isDocument = false;

    public static void compare(int type, File file) throws Exception {

        Resource companyDataResource = new ClassPathResource("cpf.jpg");
        File fileCpf = companyDataResource.getFile();
        companyDataResource = new ClassPathResource("rg.jpg");
        File fileRg = companyDataResource.getFile();
        companyDataResource = new ClassPathResource("cnh.jpg");
        File fileCnh = companyDataResource.getFile();

        
        //if(isDocument){
        Mat rg = Imgcodecs.imread(fileRg.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
        Mat cnh = Imgcodecs.imread(fileCnh.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
        Mat cpf = Imgcodecs.imread(fileCpf.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
        System.out.println(fileRg.getAbsolutePath());
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        ArrayList<Mat> pdfImages = new ArrayList<>();
        PdfReader reader = new PdfReader(file.getAbsolutePath());
        for (int i = 0; i < reader.getXrefSize(); i++) {
            PdfObject pdfobj = reader.getPdfObject(i);
            if (pdfobj != null && pdfobj.isStream()) {
                PRStream stream = (PRStream) pdfobj;
                if (PdfName.IMAGE.equals(stream.getAsName(PdfName.SUBTYPE))) {
                    PdfImageObject image = new PdfImageObject(stream);
                    byte[] imgBytes = image.getImageAsBytes();
                    Mat mat = Imgcodecs.imdecode(new MatOfByte(imgBytes), Imgcodecs.IMREAD_GRAYSCALE);
                    pdfImages.add(mat);
                }
            }
        }
        reader.close();
        double threshold = 0.8;
        for (int i = 0; i < pdfImages.size(); i++) {
            Mat pdfImage = pdfImages.get(i);

            Mat result = new Mat();
            switch (type) {
                case 0:
                    Imgproc.matchTemplate(rg, pdfImage, result, Imgproc.TM_CCOEFF_NORMED);
                    break;
                case 1:
                    Imgproc.matchTemplate(cpf, pdfImage, result, Imgproc.TM_CCOEFF_NORMED);
                    break;
                case 2:
                    Imgproc.matchTemplate(cnh, pdfImage, result, Imgproc.TM_CCOEFF_NORMED);
                    break;
            }

            Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
            
            //}
            //Random random = new Random();
            double maxSimilarity = mmr.maxVal; //random.nextDouble(2f, 20f);
            System.out.println("THE MAX: "+maxSimilarity);
            if (maxSimilarity >= 50) {
                System.out.println("The PDF image is in the same format as the fixed image. "+maxSimilarity);
            } else {
                System.out.println("The PDF image  is NOT in the same format as the fixed image. "+maxSimilarity);
            }
            }
        }

}