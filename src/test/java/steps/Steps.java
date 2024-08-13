package steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.Elements;
import utilities.Config;
import utilities.Driver;



public class Steps extends Elements {

    WebDriver driver = Driver.getDriver();
    int fakeBar;
    @Given("I open the gold bar game website")
    public void i_open_the_gold_bar_game_website() {
        driver.get(Config.getProperty("url"));
    }

    @When("I find the fake gold bar")
    public void i_find_the_fake_gold_bar() throws InterruptedException {
        int[] group1 = {0, 1, 2};
        int[] group2 = {3, 4, 5};
        int[] group3 = {6, 7, 8};
       weigh(group1, group2);
       String result = getWeighingResult();
       if(result.equals(">")) {
           fakeBar = findFakeInGroup(group2);
       } else if (result.equals("<")) {
           fakeBar = findFakeInGroup(group1);
       } else {
           fakeBar = findFakeInGroup(group3);
       }

    }
    @When("I select the fake gold bar")
    public void i_select_the_fake_gold_bar() {
        selectFakeBar(fakeBar);
    }
    @Then("I should see the alert message {string}")
    public void i_should_see_the_alert_message(String expectedMessage) {
        String actualMessage = driver.switchTo().alert().getText();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

}
