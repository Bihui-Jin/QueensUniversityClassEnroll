package queens.enrollClass;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

public class Login {

    /**
     * @param url solus url
     * @param netId student number
     * @param pwd password
     * @param term the term you want your classes to get enrolled
     * @Description: login the SOLUS
     * **/
    public void LoginSolus(String url, String netId, String pwd, String term, int timeOut) throws Exception {
        System.setProperty("webdriver.chrome.args", "--disable-logging");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        ChromeDriver driver = new ChromeDriver();
        // open page
        driver.get(url);

        // wait for loading
        WebDriverWait webDriverWait=new WebDriverWait(driver,timeOut);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='username']"))).sendKeys(netId);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='password']"))).sendKeys(pwd);
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_F12); // open the console
        Thread.sleep(10);

        //log in button on the login page
        driver.findElement(By.xpath("//button[@class='form-element form-button']")).click();
        //get the dynamic page
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("ptifrmtgtframe")));
        String shoppingCarUrl=driver.findElement(By.id("ptifrmtgtframe")).getAttribute("src");
        driver.get(shoppingCarUrl);
        //continue button on enroll select term page
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("DERIVED_SSS_SCR_SSS_LINK_ANCHOR3"))).click();
        //go the the shopping car
        driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(timeOut, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(timeOut, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//a[text()='Enrollment Shopping Cart']")).click();
        //find out the related enroll term
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='DERIVED_SSS_SCT_SSR_PB_GO']")));
        for(int i=1; i<4; i++){
            WebElement webElement = driver.findElement(By.xpath(String.format("//tr[@id='trSSR_DUMMY_RECV1$0_row%d']",i)));
            WebElement termBtn = webElement.findElement(By.tagName("input"));
            String termInfo = webElement.findElement(By.tagName("span")).getText();
            if(term.equals(termInfo)){
                //select term
                termBtn.click();
                driver.findElement(By.xpath("//input[@id='DERIVED_SSS_SCT_SSR_PB_GO']")).click();
                break;
            }

        }
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@title='Enroll in Classes']")));

        //class numbers that need to get enrolled
        int classNumSelect =driver.findElements(By.xpath("//a[contains(@name,'P_SELECT')]")).size();
        int classNum=
                classNumSelect ==0
                        ? driver.findElements(By.xpath("//a[contains(@name,'P_DELETE')]")).size() //delete button
                        : classNumSelect;
        System.out.println(classNum + " is/are in the shopping cart");
        while(classNum!=0){
            //box all classes
            webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@title='Enroll in Classes']")));
            boolean selectBoxShow = classNumSelect >0;
            for(int j=0;j<classNum && selectBoxShow;j++){
                driver.findElement(By.xpath(String.format("//input[@name='P_SELECT$%d']",j))).click();
            }
            //proceed to Step 2 of 3
            driver.findElement(By.xpath("//input[@title='Enroll in Classes']")).click();
            //Confirm classes
            webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='DERIVED_REGFRM1_SSR_PB_SUBMIT']"))).click();
            //get back to the shopping cart page -- add another class
            webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='DERIVED_REGFRM1_SSR_LINK_STARTOVER']"))).click();
            //update the classNum
            webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@title='Enroll in Classes']")));
            int classNumDelete =driver.findElements(By.xpath("//a[contains(@name,'P_DELETE')]")).size();
            classNum=
                    classNumDelete==0
                            ? driver.findElements(By.xpath("//a[contains(@name,'P_SELECT')]")).size()
                            : classNumDelete;
        }
//        driver.close(); //close the browser
    }
}
