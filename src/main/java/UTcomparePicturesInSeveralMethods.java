import java.io.IOException;
import java.util.concurrent.TimeUnit;
public class UTcomparePicturesInSeveralMethods extends ImageComparison {

    public static void main(String args[]) throws IOException
    {
        //Convert image to single color
        String fileName="OrangeHRMlogo";
        //String savedLocation=System.getProperty("user.dir");
        String savedLocation="/Users/hiepnguyen/testImg";
        negativeImage( fileName, savedLocation,  "NEG");

        //Convert image to grey ground
        fileName="OrangeHRMlogo";
        Grayscale( fileName, savedLocation, "GS");


        //Normal comparison

        if((basicComparision("OrangeHRMlogo","OrangeHRMlogo001",savedLocation)))
        {
            System.out.println("Images are Not the same");
        }
        else {
            System.out.println("Images are the same");
        }

        //Compare pictures against the threshold
        if (advanceComparision("OrangeHRMlogo","OrangeHRMlogo001",savedLocation,10))
            System.out.println("passed, Difference percentage is smaller than the threshold");
        else
            System.out.println("failed, Difference percentage is greater than the threshold");
    }

}
