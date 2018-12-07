import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSaveTwoArticlesToMyList() // Lesson3 HomeWork1 (Ex.5)
    {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Wikipedia input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Meriel Tufnell",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text = 'Meriel Tufnell']"),
                "Cannot find search Wikipedia input",
                5
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find Article title",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                300
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5
        );

        String name_of_folder = "Learning programming";
        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );
// идем в поиск, ищем вторую статью и добавляем
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc='Search Wikipedia']"),
                "Cannot press 'Search' icon",
                10
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Sam Twiston-Davies",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text = 'Sam Twiston-Davies']"),
                "Cannot find search Wikipedia input",
                15
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find Article title",
                15
        );

        MainPageObject.waitForElementAndClick(
                //By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                By.xpath("//android.widget.ImageView[@content-desc='Add this article to a reading list']"),
                "Cannot find button to open article options",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='"+name_of_folder+"']"),
                "Cannot find created folder",
                15
        );

        driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"))
                .click();
//
//        waitForElementAndClick(
//                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
//                "Cannot close article, cannot find X link",
//                10
//        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='"+name_of_folder+"']"),
                "Cannot find created folder",
                15
        );

        MainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='Meriel Tufnell']"),
                "Cannot find saved article"
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='Meriel Tufnell']"),
                "Cannot delete saved article1",
                15
        );
        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='Sam Twiston-Davies']"),
                "Cannot see saved article2",
                15
        );

    }


    @Test
    public void testSearchWordAtAllResults() // lesson2 homework3
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Marvel"; // dethklok
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundedArticles();
        assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
// создаем список всех контейнеров с результатами запроса
        List<WebElement> allContainers = driver.findElements(By.id("org.wikipedia:id/page_list_item_container"));
//        System.out.println("Results found: " + allContainers.size());
        for (WebElement itemFounded : allContainers)
        {



//            int number = allContainers.indexOf(itemFounded);
            String compositeText = "";
//            System.out.println("Result " + number + ":");
// для каждого контейнера с результатом выведем весь текст из ячеек TextView
// так как текст может быть в заголовке, в описании, а может и в редиректе.. мало ли где чем ещё.
        List<WebElement> itemWithText = itemFounded.findElements(By.className("android.widget.TextView"));
        for (WebElement textBox : itemWithText)
        {
//                int number2 = itemWithText.indexOf(textBox);
            String textOfElement = textBox.getText().toLowerCase();
//                System.out.println(number + " " + number2 + " с текстом: " + textOfElement);
//            compositeText = compositeText + " | " + textOfElement;
        }
//        System.out.println(compositeText);
        int index  = compositeText.indexOf(search_line.toLowerCase());
//            System.out.println(index);
        assertTrue(
                "Every result has contain \"" + search_line + "\"",
                index != -1);
        }
    }



    @Test
    public void testAssertTitle() // Lesson3 HomeWork2 (Ex.6)
    {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Wikipedia input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Meriel Tufnell",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text = 'Meriel Tufnell']"),
                "Cannot find search Wikipedia input",
                5
        );

// для отладки что ассерт правильно работает
//        waitForElementPresent(
//                By.id("org.wikipedia:id/view_page_title_text"),
//                "cant find an element",
//                10
//
//        );

        MainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find Article title"
        );
    }




}
