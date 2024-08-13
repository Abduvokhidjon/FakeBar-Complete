package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Driver;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class Elements {

    WebDriver driver;
    WebDriverWait wait;

    public Elements() {
        this.driver = Driver.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[@id=\"reset\" and text()='Reset']")
    protected WebElement resetButton;

    @FindBy(xpath = "//button[@id=\"weigh\" and text()='Weigh']")
    protected WebElement weighButton;

    @FindBy(xpath = "//div[@class=\"result\"]//button[@id=\"reset\"]")
    protected WebElement result;

    @FindBy(xpath = "//input[@data-side=\"left\"]")
    protected List<WebElement> leftCells;

    @FindBy(xpath = "//input[@data-side=\"right\"]")
    protected List<WebElement> rightCells;


    public void reset() {
        resetButton.click();
    }

    public void weigh(int[] leftBars, int[] rightBars) {
        reset(); // Reset the scales before each weigh
        for (int i = 0; i < leftBars.length; i++) {
            leftCells.get(i).sendKeys(String.valueOf(leftBars[i]));
        }
        for (int i = 0; i < rightBars.length; i++) {
            rightCells.get(i).sendKeys(String.valueOf(rightBars[i]));
        }
        weighButton.click();
        List<String> possibleResults = Arrays.asList(">", "=", "<");
        wait.until(textToBeOneOf(result, possibleResults));
    }

    public String getWeighingResult() {
        return result.getText();
    }

    private ExpectedCondition<Boolean> textToBeOneOf(WebElement element, List<String> texts) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                String elementText = element.getText();
                for (String text : texts) {
                    if (elementText.contains(text)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }


    public int findFakeInGroup(int[] group) {
        weigh(new int[]{group[0]}, new int[]{group[1]});
        String result = getWeighingResult();
        if (result.equals(">")) {
            return group[1]; // The first bar is the fake one
        } else if (result.contains("<")) {
            return group[0]; // The second bar is the fake one
        } else {
            return group[2]; // The third bar is the fake one
        }
    }

    public void selectFakeBar(int num) {
        driver.findElement(By.id("coin_" + num)).click();
    }






}
