Feature: Find the Fake Gold Bar


  @FindFake
  Scenario: Identify the fake gold bar
    Given I open the gold bar game website
    When I find the fake gold bar
    And I select the fake gold bar
    Then I should see the alert message "Yay! You find it!"
