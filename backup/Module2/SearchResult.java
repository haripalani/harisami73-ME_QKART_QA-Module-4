package QKART_SANITY_LOGIN.Module1;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResult {
    WebElement parentElement;

    public SearchResult(WebElement SearchResultElement) {
        this.parentElement = SearchResultElement;
    }

    /*
     * Return title of the parentElement denoting the card content section of a
     * search result
     */
    public String getTitleofResult() {
        String titleOfSearchResult = "";

        titleOfSearchResult = parentElement.getText();   
        return titleOfSearchResult;
    }

    /*
     * Return Boolean denoting if the open size chart operation was successful
     */
    public Boolean openSizechart() {
        try {
            WebElement sizeChartWebElement = parentElement.findElement(By.cssSelector("button[class*='MuiButton-textSizeMedium']"));
            sizeChartWebElement.click();
            return true;
        } catch (Exception e) {
            System.out.println("Exception while opening Size chart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the close size chart operation was successful
     */
    public Boolean closeSizeChart(WebDriver driver) {
        try {
            Thread.sleep(2000);
            Actions action = new Actions(driver);

            // Clicking on "ESC" key closes the size chart modal
            action.sendKeys(Keys.ESCAPE);
            action.perform();
            Thread.sleep(2000);
            return true;
        } catch (Exception e) {
            System.out.println("Exception while closing the size chart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean based on if the size chart exists
     */
    public Boolean verifySizeChartExists() {
        Boolean status = false;
        try {
            WebElement sizeChartWebElement = parentElement.findElement(By.cssSelector("button[class*='MuiButton-textSizeMedium']"));
            if(sizeChartWebElement.isDisplayed()){
            if(sizeChartWebElement.getText().equals("SIZE CHART")){
                status = true;
            }else{
                status = false;
            }}
            return status;
        } catch (Exception e) {
            return status;
        }
    }

    /*
     * Return Boolean if the table headers and body of the size chart matches the
     * expected values
     */
    public Boolean validateSizeChartContents(List<String> expectedTableHeaders, List<List<String>> expectedTableBody,
            WebDriver driver) {
        Boolean status = true;
        try {
            List<WebElement> tableHeaders = driver.findElements(By.xpath("//table/thead//th"));
            for(int i=0;i<expectedTableHeaders.size();i++){
                WebElement headerElement = tableHeaders.get(i);
                String headervalue = expectedTableHeaders.get(i);
            if(!headerElement.getText().equals(headervalue)){
                status = false;
            }}

            for(int i=0;i<expectedTableBody.get(i).size();i++){
                int tablerow = i+1;
                List<String> childArrayRow = expectedTableBody.get(i);
                for(int j=0;j<childArrayRow.size();j++){
                    int tablecolumn = j+1;
                WebElement tablevalue = driver.findElement(By.xpath("//table/tbody/tr["+tablerow+"]/td["+tablecolumn+"]"));
                String actualValue = tablevalue.getText(); 
                String expectedValue = childArrayRow.get(j);
                if(!actualValue.equals(expectedValue)){
                    status = false;
                }
            }
        }
            return status;
        } catch (Exception e) {
            System.out.println("Error while validating chart contents");
            return false;
        }
    }

    /*
     * Return Boolean based on if the Size drop down exists
     */
    public Boolean verifyExistenceofSizeDropdown(WebDriver driver) {
        Boolean status = false;
        try {

            WebElement sizeDropDown = parentElement.findElement(By.xpath("//select[@id='uncontrolled-native']"));
            if(sizeDropDown.isDisplayed()){
                status = true;
            }else{
                status = false;
            }
            return status;
        } catch (Exception e) {
            return status;
        }
    }
}