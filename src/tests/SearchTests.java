package tests;

import lib.CoreTestCase;
import lib.ui.Factories.SearchPageObjectFactory;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Meriel Tufnell");
        SearchPageObject.waitForSearchResult("British jockey");
    }

    @Test
    public void testCancelSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }


    @Test
    public void testAmountOfNotEmptySearch() //lesson3.5
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park Diskography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundedArticles();

        assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountEmptyOfSearch() // lesson3.6
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "gi54h9gh45gh";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }


    @Test
    public void testSearchAndCancel() // lesson2 homework2 (Ex.3) refactored
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "dethklock";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundedArticles();
        assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testSearchWordAtAllResults() // lesson2 homework3 (Ex.4) refactored
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Marvel"; // dethklok для фейла теста
        SearchPageObject.typeSearchLine(search_line);
        assertTrue(
                "We found too few results!",
                SearchPageObject.getAmountOfFoundedArticles() > 0
        );
        /* не вынес код ниже в дополнительный метод searchObject потому что сейчас я делаю ассерт внутри цикла
       когда разберусь как выводить результат из цикла в массив и обрабатывать его в тесте - тогда дорефакторю */
        List<WebElement> allElements = driver.findElements(By.id("org.wikipedia:id/page_list_item_container"));
        for (WebElement itemFounded : allElements) // создадим одну строку из всех textView для каждого результата
        {
            int index = SearchPageObject.findTextInWebElement(itemFounded, search_line.toLowerCase());
            assertTrue(
                    "Every result has contain \"" + search_line + "\"",
                    index != -1);
        }
    }

    @Test
    public void testSearchThreePredefinedResults() // lesson4_homework2 - Ex.9*
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Marvel";
        SearchPageObject.typeSearchLine(search_line);
        assertTrue(
                "We need at least 3 articles in results.",
                SearchPageObject.getAmountOfFoundedArticles() > 2
        );
        String
                title1="Marvel",
                title2="Marvel Comics",
                title3="Marvel Cinematic Universe",
                description1="Wikimedia disambiguation page",
                description2="Company that publishes comic books and related media",
                description3="Film franchise and shared fictional universe";

        SearchPageObject.waitForElementByTitleAndDescription(title1, description1);
        SearchPageObject.waitForElementByTitleAndDescription(title2, description2);
        SearchPageObject.waitForElementByTitleAndDescription(title3, description3);
    }


}
