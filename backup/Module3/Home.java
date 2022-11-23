package QKART_SANITY_LOGIN.Module1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.time.FastDateFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app";

    public Home(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHome() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean PerformLogout() throws InterruptedException {
        try {
            // Find and click on the Logout Button
            WebElement logout_button = driver.findElement(By.className("MuiButton-text"));
            logout_button.click();

            // SLEEP_STMT_10: Wait for Logout to complete
            // Wait for Logout to Complete
            Thread.sleep(3000);

            return true;
        } catch (Exception e) {
            // Error while logout
            return false;
        }
    }

    /*
     * Returns Boolean if searching for the given product name occurs without any
     * errors
     */
    public Boolean searchForProduct(String product) {
        try {
            WebElement searBox = driver.findElement(By.xpath(
            "//div[@class='MuiFormControl-root MuiTextField-root search-desktop css-i44wyl']//input[@name='search']"));
    searBox.clear();
    searBox.sendKeys(product);
    Thread.sleep(10000);
    // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    return true;
        } catch (Exception e) {
            System.out.println("Error while searching for a product: " + e.getMessage());
            return false;
        }
    }

    /*
     * Returns Array of Web Elements that are search results and return the same
     */
    public List<WebElement> getSearchResults() {
        List<WebElement> searchResults = new ArrayList<WebElement>() {};
        try {
          
           // searchResults = driver.findElements(By.xpath("//p[@class='MuiTypography-root MuiTypography-body1 css-yg30e6']"));

            //searchResults = driver.findElements(By.xpath("//div[@class='MuiGrid-root MuiGrid-container MuiGrid-spacing-xs-2 css-1msksyp']"));
            searchResults = driver.findElements(By.xpath("//div[@class='MuiPaper-root MuiPaper-elevation MuiPaper-rounded MuiPaper-elevation1 MuiCard-root card css-s18byi']"));
            
            System.out.println(searchResults.size() + " Size of search");
            return searchResults;
        } catch (Exception e) {
            System.out.println("There were no search results: " + e.getMessage());
            return searchResults;

        }
    }

    /*
     * Returns Boolean based on if the "No products found" text is displayed
     */
    public Boolean isNoResultFound() {
        Boolean status = false;
        try {
            return status;
        } catch (Exception e) {
            return status;
        }
    }

    /*
     * Return Boolean if add product to cart is successful
     */
    public Boolean addProductToCart(String productName) {
        try {
            /*
             * Iterate through each product on the page to find the WebElement corresponding
             * to the matching productName
             * 
             * Click on the "ADD TO CART" button for that element
             * 
             * Return true if these operations succeeds
             */
            List<WebElement> parentElements = driver.findElements(By.xpath(
                    "//div[@class='MuiGrid-root MuiGrid-container MuiGrid-spacing-xs-2 css-1msksyp']"));
            for (WebElement parentElement : parentElements) {
                WebElement titleElement = parentElement.findElement(By
                        .xpath("//p[@class='MuiTypography-root MuiTypography-body1 css-yg30e6']"));
                String actualProductName = titleElement.getText();
                if (actualProductName.equals(productName)) {
                    WebElement addToCartButtonElement =
                            parentElement.findElement(By.xpath("//button[text()='Add to cart']"));
                    addToCartButtonElement.click();
                } else {
                    System.out.println("Unable to find the given product");
                }
            }
            // System.out.println("Unable to find the given product");
            return false;
        } catch (Exception e) {
            System.out.println("Exception while performing add to cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting the status of clicking on the checkout button
     */
    public Boolean clickCheckout() {
        Boolean status = false;
        try {
            WebElement checkOutButtonElement =
            driver.findElement(By.xpath("//button[text()='Checkout']"));
    checkOutButtonElement.click();
            return status;
        } catch (Exception e) {
            System.out.println("Exception while clicking on Checkout: " + e.getMessage());
            return status;
        }
    }
    

    /*
     * Return Boolean denoting the status of change quantity of product in cart
     * operation
     */
    public Boolean changeProductQuantityinCart(String productName, int quantity) {
        try {List<WebElement> cartItemElements = driver.findElements(By.xpath(
            "//div[@class='cart MuiBox-root css-0']//div[@class='MuiBox-root css-0']"));
    for (WebElement cartItemElement : cartItemElements) {
        String actualProductName = cartItemElement
                .findElement(By.xpath("//div[@class='MuiBox-root css-1gjj37g']/div[1]"))
                .getText();
        if (actualProductName.equals(productName)) {
            String quantityString = cartItemElement
                    .findElement(By.xpath("//div[@data-testid='item-qty']")).getText();
            int actualQuantity = Integer.parseInt(quantityString);
            while (true) {
                if (actualQuantity < quantity) {
                    WebElement plusIcon = driver.findElement(
                            By.cssSelector("svg[data-testid='AddOutlinedIcon']"));
                    plusIcon.click();
                } else if (actualQuantity > quantity) {
                    WebElement minusIcon = driver.findElement(
                            By.cssSelector("svg[data-testid='RemoveOutlinedIcon']"));
                    minusIcon.click();
                }
                driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                if (quantity == 0) {
                    break;
                }
                quantityString = cartItemElement
                        .findElement(By.xpath("//div[@data-testid='item-qty']")).getText();
                actualQuantity = Integer.parseInt(quantityString);
                if (actualQuantity == quantity) {
                    break;
                }
            }
        }
    }
            return false;
        } catch (Exception e) {
            if (quantity == 0)
                return true;
            System.out.println("exception occurred when updating cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the cart contains items as expected
     */
    public Boolean verifyCartContents(List<String> expectedCartContents) {
        try {
            return true;

        } catch (Exception e) {
            System.out.println("Exception while verifying cart contents: " + e.getMessage());
            return false;
        }
    }

    public void verifyHomePageUrl() {
        if (this.driver.getCurrentUrl().equals(this.url)) {
            System.out.println("Url validated successfully");
        } else
            System.out.println("Url changed");

    }

    public Boolean contact(String name,String mail,String msg) {
        // Boolean status = false;
        try {
        // driver.findElement(By.linkText("Contact us")).click();
        WebElement ContactLink = driver.findElement(By.xpath("//div/div/div[5]/div[2]/p[3]"));
        ContactLink.click();
        // Thread.sleep(3000);
        WebElement Name = driver.findElement(By.xpath("//div//input[@placeholder='Name']"));
        Name.clear();
        Name.sendKeys(name);
        WebElement Mail = driver.findElement(By.xpath("//div//input[@placeholder='Email']"));
        Mail.clear();
        Mail.sendKeys(mail);
        WebElement Msge = driver.findElement(By.xpath("//div//input[@placeholder='Message']"));
        Msge.clear();
        Msge.sendKeys(msg);
        WebElement ContactNowButton = driver.findElement(By.xpath("//button[text()=' Contact Now']"));
        ContactNowButton.click();
        Thread.sleep(3000);
        WebElement Homepage = driver.findElement(By.xpath("//div"));
        if(Homepage.isDisplayed()){
            if(!Homepage.getText().equals(" Contact Now")){
                return true;}}
                return false;
               
        } catch (Exception e) {
            System.out.println("Exception while entering details in contact us: " + e.getMessage());
            return false;
        }
    }
    
}
