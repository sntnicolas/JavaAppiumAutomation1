package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

abstract public class SearchPageObject extends MainPageObject{

    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_BY_TITLE_AND_DESC_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT;

    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }
    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByTitleAndDescription (String title, String description)
    {
        System.out.println(SEARCH_RESULT_BY_TITLE_AND_DESC_TPL);
        System.out.println(
               SEARCH_RESULT_BY_TITLE_AND_DESC_TPL.replace("{TITLE}", title).replace("{DESCRIPTION}", description));
        System.out.println(
                SEARCH_RESULT_BY_TITLE_AND_DESC_TPL.replace("{TITLE}", title).replace("{DESCRIPTION}", description));
        return SEARCH_RESULT_BY_TITLE_AND_DESC_TPL.replace("{TITLE}", title).replace("{DESCRIPTION}", description);

    }


    /* TEMPLATES METHODS */

    public void initSearchInput()
    {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element",
                5);
        this.waitForElementPresent(SEARCH_INIT_ELEMENT,
                "Cannot find search input after clicking search init element");
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON,
                "Cannot find search cancel button!",
                5);
    }

    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON,
                "Search cancel button is still present!",
                5);
    }

    public void clickCancelSearch()
    {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON,
                "Cannot find and click search cancel button",
                5);
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(SEARCH_INPUT,
                search_line,
                "Cannot find and type into search input",
                5);
    }

    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        System.out.println(search_result_xpath);
        this.waitForElementPresent(search_result_xpath,
        "Cannot find search result with substring " + substring);
    }

    public void clickByArticleWithSubstring(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath,
                "Cannot find and click search result with substring " + substring,
                10);
    }

    public int getAmountOfFoundedArticles()
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by request",
                15
        );
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                15
        );
    }

    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT,
        "We supposed not to find any results");
    }

    public int findTextInWebElement (WebElement webElement, String search_text)
    {
        String compositeText = "";
        List<WebElement> webElements = webElement.findElements(By.className("android.widget.TextView"));
        for (WebElement textBox : webElements)
        {
            String textOfElement = textBox.getText().toLowerCase();
            compositeText = (compositeText + " | " + textOfElement).toLowerCase();
        }
        int index  = compositeText.indexOf(search_text.toLowerCase());
        return index;
    }

    public void waitForElementByTitleAndDescription(String title, String description)
    {
        String search_result = getResultSearchElementByTitleAndDescription(title, description);
        System.out.println(search_result +"before Wait");
        this.waitForElementPresent(search_result,
                "\nCannot find search result with title: " + title + "\nAnd description: " + description,
                10);
    }
}
