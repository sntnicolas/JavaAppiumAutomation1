import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp()throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/Users/nicolas/Desktop/JavaAppiumAutomation1/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    public void firstTest()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text = 'Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );
    }

    @Test
    public void testCancelSearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is still present on the page",
                5
        );
    }

    @Test
    public void testCompareArticleTitle()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text = 'Object-oriented programming language']"),
                "Cannot find search Wikipedia input",
                5
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find Article title",
                15
        );

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title" ,
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testOfMethod() // test for lesson2 homework1
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        isTextOfSearchAvailable();
    }

    @Test
    public void testSearchAndCancel() // lesson2 homework2
    {
        String textForTest = "dethklock";
// ищем поиск и кликаем
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
// пишем запрос
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                textForTest,
                "Cannot find search input",
                5
        );
// убеждаемся что есть хотя бы один контейнер
        waitForElementPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Cannot find first element of listView",
                5
        );
// создаем список всех контейнеров с результатами запроса
        List<WebElement> allContainers = driver.findElements(
                By.id("org.wikipedia:id/page_list_item_container")
        );
        int count = allContainers.size();
        System.out.println("Results found: " + count);
        Assert.assertTrue(
                "It would be more than one result",
                count > 1
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );
        waitForElementNotPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Some result still shown",
                5
        );
    }

    @Test
    public void testSearchWordAtAllResults() // lesson2 homework3
    {
        String textForTest = "Marvel";
// ищем поиск и кликаем
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
// пишем запрос
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                textForTest,
                "Cannot find search input",
                5
        );
// создаем список всех контейнеров с результатами запроса
        List<WebElement> allContainers = driver.findElements(
                By.id("org.wikipedia:id/page_list_item_container")
        );
        System.out.println("Results found: " + allContainers.size());

        for (WebElement itemFounded : allContainers)
        {
            int number = allContainers.indexOf(itemFounded);
            String compositeText = "";
            System.out.println("Result " + number + ":");
// для каждого контейнера с результатом выведем весь текст из ячеек TextView
// так как текст может быть в заголовке, в описании, а может и в редиректе.. мало ли где чем ещё.
            List<WebElement> itemWithText = itemFounded.findElements(By.className("android.widget.TextView"));
            for (WebElement textBox : itemWithText)
            {
//                int number2 = itemWithText.indexOf(textBox);
                String textOfElement = textBox.getText().toLowerCase();
//                System.out.println(number + " " + number2 + " с текстом: " + textOfElement);
                compositeText = compositeText + " | " + textOfElement;
            }
            System.out.println(compositeText);
            int index  = compositeText.indexOf(textForTest.toLowerCase());
//            System.out.println(index);
            Assert.assertTrue(
                    "Every result has contain \"" + textForTest + "\"",
                    index != -1
            );
        }
    }

    @Test
    public void testSwipeArticle() // lesson3.1-3.2
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text = 'Appium']"),
                "Cannot find 'Appium' article in search",
                5
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find Article title",
                15
        );

        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Cannot find the end of the article",
                20
        );

    }

    @Test
    public void saveFirstArticleForMyList() // lesson3.3-3.4
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Meriel Tufnell",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text = 'Meriel Tufnell']"),
                "Cannot find search Wikipedia input",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find Article title",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                300
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5
        );

        String name_of_folder = "Learning programming";
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='"+name_of_folder+"']"),
                "Cannot find created folder",
                15
        );

        swipeElementToLeft(
                By.xpath("//*[@text='Meriel Tufnell']"),
                "Cannot find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='Meriel Tufnell']"),
                "Cannot delete saved article",
                15
        );
    }

    @Test
    public void testAmountOfNotEmptySearch() //lesson3.5
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Wikipedia input",
                5
        );

        String search_line = "Linkin Park Diskography";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by request " + search_line,
                15
        );

        int amount_of_search_results = getAmountOfElements(
                By.xpath(search_result_locator)
        );

        Assert.assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountEmptyOfSearch() // lesson3.6
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Wikipedia input",
                5
        );

        String search_line = "gi54h9gh45gh";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label = "//*[@text='No results found']";

        waitForElementPresent(
                By.xpath(empty_result_label),
                "Cannot find empty result label by the request " + search_line,
                15
        );

        assertElementNotPresent(
                By.xpath(search_result_locator),
                "We`ve found some results by request " + search_line);
    }

    @Test
    public void testChangeScreenOrientationOnSearchResult() // lesson3.7
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Wikipedia input",
                5
        );
        String search_line = "java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text = 'Object-oriented programming language']"),
                "Cannot find search Wikipedia input topic searching by " + search_line,
                15
        );

        String title_before_rotation = waitForElementAndGetAttributes(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = waitForElementAndGetAttributes(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_rotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);

        String title_after_second_rotation = waitForElementAndGetAttributes(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_second_rotation
        );

    }

    @Test
    public void testCheckSearchArticleInBackground() // lesson3.8
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Meriel Tufnell",
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text = 'Meriel Tufnell']"),
                "Cannot find search Wikipedia input",
                15
        );

        driver.runAppInBackground(2);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text = 'Meriel Tufnell']"),
                "Cannot find article after returning from background",
                15
        );
    }

    @Test
    public void testSaveTwoArticlesToMyList() // Lesson3 HomeWork1 (Ex.5)
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Meriel Tufnell",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text = 'Meriel Tufnell']"),
                "Cannot find search Wikipedia input",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find Article title",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                300
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5
        );

        String name_of_folder = "Learning programming";
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );
// идем в поиск, ищем вторую статью и добавляем
        waitForElementAndClick(
                By.xpath("//*[@content-desc='Search Wikipedia']"),
                "Cannot press 'Search' icon",
                10
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Sam Twiston-Davies",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text = 'Sam Twiston-Davies']"),
                "Cannot find search Wikipedia input",
                15
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find Article title",
                15
        );

        waitForElementAndClick(
                //By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                By.xpath("//android.widget.ImageView[@content-desc='Add this article to a reading list']"),
                "Cannot find button to open article options",
                15
        );

        waitForElementAndClick(
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

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='"+name_of_folder+"']"),
                "Cannot find created folder",
                15
        );

        swipeElementToLeft(
                By.xpath("//*[@text='Meriel Tufnell']"),
                "Cannot find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='Meriel Tufnell']"),
                "Cannot delete saved article1",
                15
        );
        waitForElementPresent(
                By.xpath("//*[@text='Sam Twiston-Davies']"),
                "Cannot see saved article2",
                15
        );

    }

    @Test
    public void testAssertTitle() // Lesson3 HomeWork2 (Ex.6)
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search Wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Meriel Tufnell",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
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

        assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find Article title"
        );
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_message)
    {
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element =  waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element =  waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent (By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    private void isTextOfSearchAvailable() //lesson2 homework1
    {
        WebElement textBoxForSearch = waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find textbox for Search",
                5
        );

        String textInTextBox = textBoxForSearch.getAttribute("text");

        Assert.assertEquals(
                "There is no text 'Search…'",
                "Search…",
                textInTextBox
        );

    }

    protected void swipeUp (int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);
        action.
                press(x, start_y).
                waitAction(timeOfSwipe).
                moveTo(x, end_y).
                release().
                perform();
    }

    protected void swipeUpQuick()
    {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes)
    {
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0)
        {
            if (already_swiped > max_swipes){
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }

            swipeUpQuick();
            ++already_swiped;
        }
    }

    protected void swipeElementToLeft (By by, String error_message)
    {
        WebElement element = waitForElementPresent(by, error_message, 10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action.press(right_x*2, middle_y).
// так как мы ищем элемент с текстом, а он часто короткий, то пока сделал множитель 2 (иначе длина свайпа маленькая)
                waitAction(2000).
                moveTo(left_x,middle_y).
                release().
                perform();
    }

    private int getAmountOfElements(By by)
    {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String error_message)
    {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0) {
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private String waitForElementAndGetAttributes(By by, String attribute, String error_message, long timeoutinseconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutinseconds);
        return element.getAttribute(attribute);
    }

    private void assertElementPresent (By by, String error_message)
    {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements < 1){
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }
}
