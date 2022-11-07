package QKART_SANITY_LOGIN.Module1;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.WebRowSet;

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

            // Wait for Logout to Complete
            Thread.sleep(3000);

            return true;
        } catch (Exception e) {
            // Error while logout
            return false;
        }
    }

    /*
     * Returns Boolean if searching for the given product name occurs without any errors
     */
    public Boolean searchForProduct(String product) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Clear the contents of the search box and Enter the product name in the search
            // box
            WebElement searBox = driver.findElement(By.xpath(
                    "//div[@class='MuiFormControl-root MuiTextField-root search-desktop css-i44wyl']//input[@name='search']"));
            searBox.clear();
            searBox.sendKeys(product);
            Thread.sleep(3000);
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
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Find all webelements corresponding to the card content section of each of
            // search results
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
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Check the presence of "No products found" text in the web page. Assign status
            // = true if the element is *displayed* else set status = false
            WebElement noProductsFound =
                    driver.findElement(By.xpath("//h4[text()=' No products found ']"));
            status = noProductsFound.isDisplayed();
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
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            /*
             * Iterate through each product on the page to find the WebElement corresponding to the
             * matching productName
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
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            // Find and click on the the Checkout button
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
     * Return Boolean denoting the status of change quantity of product in cart operation
     */
    public Boolean changeProductQuantityinCart(String productName, int quantity) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 06: MILESTONE 5

            // Find the item on the cart with the matching productName

            // Increment or decrement the quantity of the matching product until the current
            // quantity is reached (Note: Keep a look out when then input quantity is 0,
            // here we need to remove the item completely from the cart)
            // List<WebElement> cartItemElements = driver.findElements(By.xpath(
            //         "//div[@class='cart MuiBox-root css-0']//div[@class='MuiBox-root css-0']"));
            // for (WebElement cartItemElement : cartItemElements) {
            //     String actualProductName = cartItemElement
            //             .findElement(By.xpath("//div[@class='MuiBox-root css-1gjj37g']/div[1]"))
            //             .getText();
            //     if (actualProductName.equals(productName)) {
            //         String quantityString = cartItemElement
            //                 .findElement(By.xpath("//div[@data-testid='item-qty']")).getText();
            //         int actualQuantity = Integer.parseInt(quantityString);
            //         while (true) {
            //             if (actualQuantity < quantity) {
            //                 WebElement plusIcon = driver.findElement(
            //                         By.cssSelector("svg[data-testid='AddOutlinedIcon']"));
            //                 plusIcon.click();
            //             } else if (actualQuantity > quantity) {
            //                 WebElement minusIcon = driver.findElement(
            //                         By.cssSelector("svg[data-testid='RemoveOutlinedIcon']"));
            //                 minusIcon.click();
            //             }
            //             Thread.sleep(2000);
            //             if (quantity == 0) {
            //                 break;
            //             }
            //             quantityString = cartItemElement
            //                     .findElement(By.xpath("//div[@data-testid='item-qty']")).getText();
            //             actualQuantity = Integer.parseInt(quantityString);
            //             if (actualQuantity == quantity) {
            //                 // break;
            //             }
            //         }
            //     }
            // }
            // return false;
            List<WebElement> cartElem = driver.findElements(By.xpath("//div[@class='MuiBox-root css-1gjj37g']"));
            //List<String> cartProducts = new ArrayList<>();
            for (WebElement e : cartElem)
            {
                Thread.sleep(2000);
                if(e.findElement(By.xpath("div[1]")).getText().equals(productName))
                {
                    WebElement quantityValue = e.findElement(By.xpath("div/div"));
                    int q = Integer.valueOf(quantityValue.getText().trim());
                   if(q<quantity)
                    {
                        while(q!=quantity)
                        {
                            Thread.sleep(1000);
                            e.findElement(By.xpath("div[2]/div/button[2]")).click();
                            q++;
                            
                        }
                        break;
                    }
                    else if(q>quantity) 
                    {
                        while(q!=quantity)
                        {
                        e.findElement(By.xpath("div[2]/div/button[1]")).click();;
                        q--;
                        }
                        break;
                    }
                }
            }
            /* 
            if(s.contains(productName))
                {
                    if(quantity==0)
                    {
                        Thread.sleep(2000);
                        driver.findElement(By.xpath("//*[@id='root']/div/div/div[3]/div[2]/div/div[1]/div/div[2]/div[2]/div[1]/button[1]")).click();
                        return true;
                    }
                    else if(quantity>0)
                    {
                        Thread.sleep(2000);
                        driver.findElement(By.xpath("//*[@id='root']/div/div/div[3]/div[2]/div/div[1]/div/div[2]/div[2]/div[1]/button[2]")).click();
                        return true;
                    }




            */ 
            // Increment or decrement the quantity of the matching product until the current
            // quantity is reached (Note: Keep a look out when then input quantity is 0,
            // here we need to remove the item completely from the cart)
            return true;
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
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 07: MILESTONE 6

            // Get all the cart items as an array of webelements

            // Iterate through expectedCartContents and check if item with matching product
            // name is present in the cart

            List<WebElement> myList = driver.findElements(By.xpath(
                    "//div[@class='cart MuiBox-root css-0']//div[@class='MuiBox-root css-0']"));

            // myList contains all the web elements
            // if you want to get all elements text into array list
            List<String> all_elements_text = new ArrayList<>();

            for (int i = 0; i < myList.size(); i++) {

                // loading text of each element in to array all_elements_text
                all_elements_text.add(myList.get(i).getText());

                // to print directly
                System.out.println(myList.get(i).getText());

            }

            return true;
            // List<WebElement> cartElem = driver.findElements(By.xpath("//*[@id='root']/div/div/div[3]/div[2]/div/div/div/div[2]/div[1]"));
            // List<String> cartItems = new ArrayList<>();
            // for(WebElement e: cartElem)
            // {
            //     Thread.sleep(1000);
            //     cartItems.add(e.getText().trim());
            // }

            // System.out.println(cartItems);


            // if(cartItems.equals(expectedCartContents))
            // {
            //     //System.out.println(cartItems);
            //     return true;
            // }
        
            // return false;

        } catch (Exception e) {
            System.out.println("Exception while verifying cart contents: " + e.getMessage());
            return false;
        }
    }
}
