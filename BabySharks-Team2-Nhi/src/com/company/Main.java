package com.company;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class Main {
    static WebDriver driver;

    /*COMMON KEYWORDS*/
    public static WebElement findElementByXpath(String xpath){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

        WebElement element = driver.findElement(By.xpath(xpath));
        return element;
    }

    public static WebElement findElementById(String id){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));

        WebElement element = driver.findElement(By.id(id));
        return element;
    }

    public static List<WebElement> findMultiElementsByXpath(String xpath){
        List<WebElement> elements = driver.findElements(By.xpath(xpath));
        return elements;
    }

    public static WebElement findElementByName(String name){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(name)));

        WebElement element = driver.findElement(By.name(name));
        return element;
    }

    public static void waitForPageLoaded(long timeout){
        driver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
    }

    public static void waitForElementClickable(String elementXpath){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elementXpath)));
    }

    public static void waitForElementVisible(String elementXpath){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementXpath)));
    }

    public static void waitForElementNotVisible(String elementXpath){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(elementXpath)));
    }

    public static boolean elementIsPresent(String xpath){
        return driver.findElements( By.xpath(xpath) ).size() != 0;
    }

    public static WebDriver initDriver(){
        String driverPath = System.getProperty("user.dir") + "\\driver\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driverPath);
        WebDriver driver = new ChromeDriver();

        return driver;
    }

    public static void openBrowser(String URL){
        driver.get(URL);
    }

    public static void navigateToURL(String URL){
        driver.navigate().to(URL);
    }

    public static void check(WebElement element){
        if ( !element.isSelected())
        {
            element.click();
        }
    }
    public static void unCheck(WebElement element){
        if ( element.isSelected())
        {
            element.click();
        }
    }

    public static String getAttributeValue(WebElement element, String attribute){
        return element.getAttribute(attribute);
    }

    public static String getElementXpath(WebElement element){
        String javaScript = "function getElementXPath(elt){" +
                "var path = \"\";" +
                "for (; elt && elt.nodeType == 1; elt = elt.parentNode){" +
                "idx = getElementIdx(elt);" +
                "xname = elt.tagName;" +
                "if (idx > 1){" +
                "xname += \"[\" + idx + \"]\";" +
                "}" +
                "path = \"/\" + xname + path;" +
                "}" +
                "return path;" +
                "}" +
                "function getElementIdx(elt){" +
                "var count = 1;" +
                "for (var sib = elt.previousSibling; sib ; sib = sib.previousSibling){" +
                "if(sib.nodeType == 1 && sib.tagName == elt.tagName){" +
                "count++;" +
                "}" +
                "}" +
                "return count;" +
                "}" +
                "return getElementXPath(arguments[0]).toLowerCase();";

        return (String)((JavascriptExecutor)driver).executeScript(javaScript, element);      }

    ///////////// END OF COMMON KEYWORDS //////////////////////

    /*COMMON TEST CASE*/
    public static void loginTestCase(){
        WebElement txtEmail = findElementByName("email");
        WebElement txtPassword = findElementByName("password");

        String loginXpath = "//span[text()='Login']/ancestor::button[@type='submit']";
        WebElement btnLogin = findElementByXpath(loginXpath);

        txtEmail.sendKeys("admin@phptravels.com");
        txtPassword.sendKeys("demoadmin");
        btnLogin.click();
    }

    public static void loginTestCaseFE (){
        String btnMyAccountXpath = "//ul[contains(@class,'navbar-right')]//ul[contains(@class,'navbar-side navbar-right')]/li[@id='li_myaccount']/a[contains(text(),'My Account')]";
        WebElement btnMyAccount = findElementByXpath(btnMyAccountXpath);
        btnMyAccount.click();

        String menuLoginXpath = "//ul[contains(@class, 'navbar-right')]//ul[contains(@class,'navbar-side')]/li[@id='li_myaccount']/ul[@class='dropdown-menu']//li//a[contains(text(),'Login')]";
        WebElement menuLogin = findElementByXpath(menuLoginXpath);

        menuLogin.click();

        WebElement txtEmail = findElementByName("username");
        WebElement txtPassword = findElementByName("password");

        String btnLoginXpath = "//button[contains(@class,'login') and @type='submit']";
        WebElement btntLogin = findElementByXpath(btnLoginXpath);

        String username = "user@phptravels.com";
        String password = "demouser";
        txtEmail.sendKeys(username);
        txtPassword.sendKeys(password);
        btntLogin.click();
    }
    /*END OF COMMON TEST CASE*/

    /*COMMON TEST STEPS*/
    public static void clickToOption(String optionNameText, String expectedTitle){
        String liHotelsXpath = "//div[@class='menu-content']//li//a[@href='#Hotels' and contains(text(), 'Hotels')]";
        WebElement liHotels = findElementByXpath(liHotelsXpath);
        liHotels.click();

        String optionXpath = "//div[@class='menu']//ul[@id='Hotels']//li//a";
        List<WebElement> listOptionElement = driver.findElements(By.xpath(optionXpath));

        for(WebElement ele: listOptionElement){
            String optionName = ele.getText();
            if(optionName.equals(optionNameText)){
            System.out.println("Click on " + optionName + " option of 'HOTELS'");
            ele.click();
            String actualTitle = driver.getTitle();
            if (actualTitle.contentEquals(expectedTitle)){
                System.out.println("Test Passed at Clicking to option " + optionName);
                break;
            } else {
                System.out.println("Test Failed at Clicking to option " + optionName);
                break;
            }
            }
        }
    }

    public static void searchHotelByName(String hotelName) throws InterruptedException {

        // Enter text to "Search" phrase
        String txtSearchXpath = "//input[contains(@class, 'search') and @name = 'phrase']";
        WebElement txtSearch = findElementByXpath(txtSearchXpath);
        txtSearch.clear();
        txtSearch.sendKeys(hotelName);

        // Click on "Go" button
        String btnGoXpath = "//span[contains(@class, 'search')]//a[text() = 'Go']";
        WebElement btnGo = findElementByXpath(btnGoXpath);
        btnGo.click();

        String overlayXpath = "//div[contains(@class, 'overlay')]";
        waitForElementNotVisible(overlayXpath);

        // Verify number of search result:
        String gridElementXpath = "//div[contains(@class, 'container')]//table//tbody//tr//a[text()='"+hotelName+"']";
        List<WebElement> searchResult = findMultiElementsByXpath(gridElementXpath);
        int numberOfSearchResult = searchResult.size();
        if (numberOfSearchResult > 0){
            System.out.println("Found " + numberOfSearchResult + " result(s) ");
        }else {
            String noEntryXpath = "//div[contains(@class, 'container')]//table//tbody//tr//td[text()='Entries not found.']";
            boolean result = elementIsPresent(noEntryXpath);
            if(result){
                System.out.println("No result has been found");
                System.out.println("Message 'Entries not found.' is displayed ");
            }
        }
    }
    /*END OF COMMON TEST STEPS*/

    /*TEST CASES*/
    public static void testCaseBE003(){

        /* // BE003-Hotels-Navigate to sub item of Hotels */
        driver = initDriver();

        System.out.println("BE003-Hotels-Navigate to sub item of Hotels ");
        // 1. Navigate and login as Admin to page
        String baseUrl = "https://www.phptravels.net/admin";
        openBrowser(baseUrl);

        driver.manage().window().maximize();

        loginTestCase();

        clickToOption("HOTELS", "Hotels Management");
        driver.navigate().back();

        clickToOption("ROOMS", "Rooms Management");
        driver.navigate().back();

        clickToOption("EXTRAS", "Extras Management");
        driver.navigate().back();

        clickToOption("REVIEWS", "Reviews Management");
        driver.navigate().back();

        clickToOption("HOTELS SETTINGS", "Hotels Settings");
        driver.navigate().back();

        System.out.println("All Tests Passed!");

        driver.close();
    }

    public static void testCaseBE009() throws InterruptedException {
        driver = initDriver();
        /* BE009-Hotels-Search hotel by Name */
        System.out.println("BE009-Hotels-Search hotel by Name");

        // 1. Navigate and login as Admin to page
         String baseUrl = "https://www.phptravels.net/admin/hotels";
         openBrowser(baseUrl);
         driver.manage().window().maximize();

        loginTestCase();
        String validHotelName = "Rendezvous Hotels";
        String invalidHotelName = "SG Deluxe Resort";

        // Click on "Search" button
        String btnSearchXpath = "//a[contains(@class, 'search') and text() = 'Search']";
        WebElement btnSearch = findElementByXpath(btnSearchXpath);
        btnSearch.click();

        // Search with Valid Hotel Name
        System.out.println("Search with Valid Hotel Name: " + validHotelName);
        searchHotelByName(validHotelName);

        //Search with Invalid Hotel Name
        System.out.println("Search with Invalid Hotel Name: " + invalidHotelName);
        searchHotelByName(invalidHotelName);

        driver.close();
    }

    public static void testCaseFE004(){
        driver = initDriver();
        /* FE004-Flights - Verify Flights Filter */
        System.out.println("FE004-Flights - Verify Flights Filter");

        String baseURL = "https://www.phptravels.net/";
        openBrowser(baseURL);
        driver.manage().window().maximize();

        loginTestCaseFE();

//        1	Navigate to url 'https://www.phptravels.net/flights'
        String flightsURL = "https://www.phptravels.net/flights";
        navigateToURL(flightsURL);

/*        "Filter Search:
                + Select Non-Stop
                + Filter By: Emirates"*/
        String fitlerMenuXpath = "//div[contains(@class,'panel-default')]/div[@class='panel-body']";
        String chkNonStopXpath = fitlerMenuXpath+"//input[@id='nonstop']//parent::div[contains(@class, 'icheckbox')]";
        System.out.println("Finding Checkbox Non-stop");
        WebElement chkNonStop = findElementByXpath(chkNonStopXpath);
        System.out.println("Check to Checkbox Non-stop");
        check(chkNonStop);

        String filterName = "Emirates";
        String chkFilterByXpath = fitlerMenuXpath+"//div[contains(@class, 'go-right')]//label[contains(normalize-space(),'"+filterName+"')]//preceding-sibling::div/div";
        String imgFilterXpath = fitlerMenuXpath+"//div[contains(@class, 'go-right')]//label[contains(normalize-space(),'"+filterName+"')]/img";

        // Get Filter Icon:
        WebElement imgFitler = findElementByXpath(imgFilterXpath);
        String icoImageFilter = getAttributeValue(imgFitler,"src");

        System.out.println("Finding Checkbox: "+filterName);
        WebElement chkFilter = findElementByXpath(chkFilterByXpath);
        check(chkFilter);

        System.out.println("Check to Checkbox: "+ filterName);

        // Get and compare result Icons:
        String tblResultXpath="//div[contains(text(),'Available Flights')]//following::table[@id='load_data']";
        String gridResultXpath=tblResultXpath+"//tbody//tr";

        List<WebElement> listElementResult = findMultiElementsByXpath(gridResultXpath);

        if(listElementResult.size()>0){
            System.out.println("Verifying Results: ");
            System.out.println("Number of result: " + listElementResult.size());

            for(WebElement element: listElementResult){
                String elementXpath = getElementXpath(element);
                String icoResultXpath = elementXpath+ "//div[contains(@class, 'col-md-2')]//img";
                WebElement icoResult = findElementByXpath(icoResultXpath);
                String icoResultImage = getAttributeValue(icoResult, "src");
                if(!icoResultImage.equals(icoImageFilter)){
                    System.out.println("Search Failed!");
                    break;
                }
                }
                System.out.println("Searching successfully");
        }

        driver.close();
    }

    public static void main(String[] args) throws InterruptedException {
        testCaseBE003();
        testCaseBE009();
        testCaseFE004();
    }
}
