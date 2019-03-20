import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.internal.WrapsDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
//import CaptureElementImage;
public class UTcaptureElementInSeveralSites extends CaptureElementImage {
    public static void main(String args[]) throws IOException
    {
        //Capture KMS logo at the web site
        String url="https://home.kms-technology.com/";
        String elementXpath="//img[@src='/assets/img/kms-logo.svg']";
        String fileName="kms-home";
        String savedLocation=System.getProperty("user.dir");
        CaptureElementForSpecificScreen(fileName,url,elementXpath,savedLocation);

        //Capture xxx xx at the web site
        url="https://jquery.com/";
        elementXpath="//h3[contains(text(),'Lightweight Footprint')]";
        fileName="jquery-lightweight";
        CaptureElementForSpecificScreen(fileName,url,elementXpath,savedLocation);

        //Capture xxx xx at the web site
        url="https://stackoverflow.com/";
        elementXpath="//span[(text()='Stack Overflow') and @class='-img _glyph']";
        fileName="stackoverflow";
        CaptureElementForSpecificScreen(fileName,url,elementXpath,savedLocation);

    }
}
