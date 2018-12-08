package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Meriel Tufnell");
        SearchPageObject.waitForSearchResult("British jockey");
    }

    @Test
    public void testCancelSearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }


    @Test
    public void testAmountOfNotEmptySearch() //lesson3.5
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
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
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String search_line = "gi54h9gh45gh";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }


    @Test
    public void testSearchAndCancel() // lesson2 homework2 (Ex.3) refactored
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String search_line = "dethklock";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundedArticles();
        assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForEmptyResultsLabel();
    }

    @Test
    public void testSearchWordAtAllResults() // lesson2 homework3 (Ex.4) refactored
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
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
}
