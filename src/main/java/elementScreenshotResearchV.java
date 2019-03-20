import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;

public class elementScreenshotResearchV {
    WebDriver driver;
    @BeforeTest
    public void setup() throws Exception {
        System.setProperty("WebDriver.chrome.driver","/Users/hiepnguyen/testImg/chromedriver");
        WebDriver driver=new ChromeDriver();
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://only-testing-blog.blogspot.in/2014/09/selectable.html");
    }

    @Test
    public void captureScreenshot() throws Exception {
        //Locate Image element to capture screenshot.
        WebElement Image = driver.findElement(By.xpath("//img[@border='0']"));
        //Call captureElementScreenshot function to capture screenshot of element.
        captureElementScreenshot(Image);
    }

    public void captureElementScreenshot(WebElement element) throws IOException{
        //Capture entire page screenshot as buffer.
        //Used TakesScreenshot, OutputType Interface of selenium and File class of java to capture screenshot of entire page.
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        //Used selenium getSize() method to get height and width of element.
        //Retrieve width of element.
        int ImageWidth = element.getSize().getWidth();
        //Retrieve height of element.
        int ImageHeight = element.getSize().getHeight();

        //Used selenium Point class to get x y coordinates of Image element.
        //get location(x y coordinates) of the element.
        Point point = element.getLocation();
        int xcord = point.getX();
        int ycord = point.getY();


        System.out.println("Image File NOT exist"+xcord);
        System.out.println("Image File NOT exist"+ycord);

        //Reading full image screenshot.
        BufferedImage img = ImageIO.read(screen);

        //cut Image using height, width and x y coordinates parameters.
        BufferedImage dest = img.getSubimage(xcord, ycord, ImageWidth, ImageHeight);
        ImageIO.write(dest, "png", screen);

        //Used FileUtils class of apache.commons.io.
        //save Image screenshot In D: drive.
        FileUtils.copyFile(screen, new File("/Users/hiepnguyen/testImg/screenshot_element.png"));
    }

    public static void main(String args[]) throws IOException
    {
        System.setProperty("WebDriver.chrome.driver","/Users/hiepnguyen/testImg/chromedriver");
        WebDriver driver=new ChromeDriver();
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://only-testing-blog.blogspot.in/2014/09/selectable.html");


        //Locate Image element to capture screenshot.
        WebElement Image = driver.findElement(By.xpath("//img[@id='Image1_img']"));
        //Call captureElementScreenshot function to capture screenshot of element.
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        Screenshot logoImageScreenshot = new AShot().takeScreenshot(driver);
        ImageIO.write(logoImageScreenshot.getImage(),"png",new File("/Users/hiepnguyen/testImg/fullScreen.png"));
        File f = new File("/Users/hiepnguyen/testImg/fullScreen.png");

        //Used selenium getSize() method to get height and width of element.
        //Retrieve width of element.
        int ImageWidth = Image.getSize().getWidth();
        //Retrieve height of element.
        int ImageHeight = Image.getSize().getHeight();

        //Used selenium Point class to get x y coordinates of Image element.
        //get location(x y coordinates) of the element.
        Point point = Image.getLocation();
        int xcord = point.getX();
        int ycord = point.getY();


        System.out.println(xcord);
//        console.printf(xcord);
        System.out.println(ycord);
//        console.printf(ycord);


        //Reading full image screenshot.
        BufferedImage img = ImageIO.read(screen);

        //cut Image using height, width and x y coordinates parameters.
        BufferedImage dest = img.getSubimage(xcord, ycord, ImageWidth, ImageHeight);
        ImageIO.write(dest, "png", screen);

        //Used FileUtils class of apache.commons.io.
        //save Image screenshot In D: drive.
        FileUtils.copyFile(screen, new File("/Users/hiepnguyen/testImg/screenshot_element.png"));

    }
}
