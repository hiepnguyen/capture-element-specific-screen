import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.internal.WrapsDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class UTjquery {
    public static void main(String args[]) throws IOException
    {
        String url="https://jquery.com/";
        String elementXpath="//h3[contains(text(),'Lightweight Footprint')]";
        String fileName="jquery-lightweight";
//Prepare for starting web driver
        System.setProperty("WebDriver.chrome.driver","/Users/hiepnguyen/testImg/chromedriver");
        WebDriver driver=new ChromeDriver();
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Navigate to web site
        //driver.get("http://www.google.com");

        //Get browser information for better navigation to web element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long pixelRatio = (Long) js.executeScript("return window.devicePixelRatio");
        long width = (Long) js.executeScript("return screen.width");
        long height = (Long) js.executeScript("return screen.height");
        String userAgent = (String) js.executeScript("return navigator.userAgent");
        System.out.println("userAgent: " + userAgent);
        System.out.println("pixelRatio: " + pixelRatio);
        System.out.println("width: " + width+"height: " + height);
        long availHeight111 = (Long) js.executeScript(" return screen.availHeight");
        long availWidth111 = (Long) js.executeScript(" return screen.availWidth");
        System.out.println("availHeight111: " + availHeight111+ "availWidth111: " + availWidth111);

        //Navigate to web site
        driver.get(url);
        WebElement element = driver.findElement(By.xpath(elementXpath));
        WrapsDriver wrapsDriver = (WrapsDriver) element;
        File screenshot = ((TakesScreenshot) wrapsDriver.getWrappedDriver()).getScreenshotAs(OutputType.FILE);
        Rectangle rectangle = new Rectangle(element.getSize().width, element.getSize().height, element.getSize().height, element.getSize().width);

        Point location = element.getLocation();
        BufferedImage bufferedImage = ImageIO.read(screenshot);
        System.out.println("xLocation: "+location.x+"yLocation: "+location.y+"elementWidth: " +rectangle.width+",elementHeight: "+ rectangle.height);
//Scale and capture the picture of element
        BufferedImage destImage = bufferedImage.getSubimage(location.x*(int)pixelRatio,location.y*(int)pixelRatio, rectangle.width*(int)pixelRatio, rectangle.height*(int)pixelRatio);
        ImageIO.write(destImage, "png", screenshot);
        File file = new File(System.getProperty("user.dir")+File.separator+fileName+".png");
        FileUtils.copyFile(screenshot, file);

        driver.close();
    }
}
