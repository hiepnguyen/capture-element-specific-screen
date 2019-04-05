import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import org.opencv.core.Core;

import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


public class ImageComparison {
    public String fileName;
    public String savedLocation;
    public String postFix="";
    public static void main(String args[]) throws IOException
    {
        BufferedImage expectedImage = ImageIO.read(new File("/Users/hiepnguyen/testImg/OrangeHRMlogo.png"));
        BufferedImage actualImage = ImageIO.read(new File("/Users/hiepnguyen/testImg/OrangeHRMlogo001.png"));
        //basicComparision(actualImage, expectedImage);
//        advanceComparision();
//        negativeImage();
//        Grayscale();
//        templateMatching();
    }
    public static boolean basicComparision(String fileName1,String fileName2,String savedLocation) {
        BufferedImage expectedImage =null;
        BufferedImage actualImage =null;

        try
        {
            File fileA = new File(savedLocation + File.separator + fileName1 + ".png");
            File fileB = new File(savedLocation + File.separator + fileName2 + ".png");

            actualImage = ImageIO.read(fileA);
            expectedImage = ImageIO.read(fileB);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        ImageDiffer imgDiff = new ImageDiffer();
        ImageDiff diff = imgDiff.makeDiff(actualImage, expectedImage);
        return diff.hasDiff();
    }
    public static Boolean advanceComparision(String fileName1,String fileName2,String savedLocation, int threshold) {
        //Locate Image element to capture screenshot.
        BufferedImage imgA = null;
        BufferedImage imgB = null;
        double percentage =0;

        try
        {
            File fileA = new File(savedLocation + File.separator + fileName1 + ".png");
            File fileB = new File(savedLocation + File.separator + fileName2 + ".png");

            imgA = ImageIO.read(fileA);
            imgB = ImageIO.read(fileB);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        int width1 = imgA.getWidth();
        int width2 = imgB.getWidth();
        int height1 = imgA.getHeight();
        int height2 = imgB.getHeight();

        if ((width1 != width2) || (height1 != height2))
            System.out.println("Error: Images dimensions"+ " mismatch");
        else
        {
            long difference = 0;
            for (int y = 0; y < height1; y++)
            {
                for (int x = 0; x < width1; x++)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }

            // Total number of red pixels = width * height
            // Total number of blue pixels = width * height
            // Total number of green pixels = width * height
            // So total number of pixels = width * height * 3
            double total_pixels = width1 * height1 * 3;

            // Normalizing the value of different pixels
            // for accuracy(average pixels per color
            // component)
            double avg_different_pixels = difference / total_pixels;

            // There are 255 values of pixels in total
            percentage = (avg_different_pixels / 255) * 100;

            System.out.println("Difference Percentage-->" + percentage);

        }
        if (threshold > percentage)
            return true;
        else
            return false;
    }
    public static void negativeImage(String fileName,String savedLocation, String postFix)
    {
//        "/Users/hiepnguyen/testImg/OrangeHRMlogo.png"
        BufferedImage img = null;
        File f = null;

        // read image
        try
        {
            f = new File(savedLocation + File.separator + fileName + ".png");
            img = ImageIO.read(f);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }

        // Get image width and height
        int width = img.getWidth();
        int height = img.getHeight();

        // Convert to negative
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int p = img.getRGB(x,y);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                //subtract RGB from 255
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;

                //set new RGB value
                p = (a<<24) | (r<<16) | (g<<8) | b;
                img.setRGB(x, y, p);
            }
        }

        // write image
        try
        {
            f = new File(savedLocation + File.separator + fileName + postFix+".png");
            ImageIO.write(img, "png", f);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
    public static void Grayscale(String fileName,String savedLocation, String postFix)
    {
        postFix="GS";
        BufferedImage img = null;
        File f = null;

        // read image
        try
        {
            f = new File(savedLocation + File.separator + fileName + ".png");
            img = ImageIO.read(f);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }

        // get image's width and height
        int width = img.getWidth();
        int height = img.getHeight();

        // convert to greyscale
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                // Here (x,y)denotes the coordinate of image;// for modifying the pixel value.
                int p = img.getRGB(x,y);

                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                // calculate average
                int avg = (r+g+b)/3;

                // replace RGB value with avg
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;

                img.setRGB(x, y, p);
            }
        }

        // write image
        try
        {
            f = new File(savedLocation + File.separator + fileName + postFix+".png");
            ImageIO.write(img, "png", f);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    //xxxxxxxxxxxxxxxxxx
//    public static void templateMatching(){
//
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        System.out.println("Welcome to OpenCV " + Core.VERSION);
//        Mat source=null;
//        Mat template=null;
//        //String filePath="/Users/hiepnguyen/testImg/";
//        //Load image file
//        source=Imgcodecs.imread("/Users/hiepnguyen/testImg/kapadokya.jpg");
//
//        template=Imgcodecs.imread("/Users/hiepnguyen/testImg/balon.jpg");
//
//        BufferedImage imgA = null;
//        BufferedImage imgB = null;
//        File fileA = new File("/Users/hiepnguyen/testImg/OrangeHRMlogo.png");
//        File fileB = new File("/Users/hiepnguyen/testImg/OrangeHRMlogo001.png");
//        imgA = ImageIO.read(fileA);
//        imgB = ImageIO.read(fileB);
//        System.out.println(imgA.getWidth());
//        System.out.println(imgB.getWidth());
//        System.out.println(imgA.getHeight());
//        System.out.println(imgB.getHeight());
//
//        int result_cols = source.cols() - template.cols() + 1;
//        int result_rows = source.rows() - template.rows() + 1;
//        System.out.println("source.cols "+source.cols());
//        System.out.println("source.rows "+source.rows());
//
//        System.out.println("result_cols "+result_cols);
//        System.out.println("result_rows "+result_rows);
//
//        Mat outputImage=null;
//        outputImage.create( result_cols, result_rows, CvType.CV_32FC1 );
//
//        System.out.println("Ready for slide ");
//        //Mat outputImage=new Mat(result_rows, result_cols, CvType.CV_32FC1);
//        int machMethod=Imgproc.TM_SQDIFF;
//        //Template matching method
//        Imgproc.matchTemplate(source, template, outputImage, machMethod);
//
//
////        MinMaxLocResult mmr = Core.minMaxLoc(outputImage);
////        Point matchLoc=mmr.maxLoc;
////        //Draw rectangle on result image
////        Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + template.cols(),
////                matchLoc.y + template.rows()), new Scalar(255, 255, 255));
////
////        Imgcodecs.imwrite(filePath+"sonuc.jpg", source);
//        System.out.println("Completed.");
//    }
}