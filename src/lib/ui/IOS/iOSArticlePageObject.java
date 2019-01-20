package lib.ui.IOS;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class iOSArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "xpath://XCUIElementTypeStaticText[@name='Meriel Tufnell']";
        FOOTER_ELEMENT = "id:View article in browser";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://XCUIElementTypeButton[@name='Save for later']";
        //MY_LIST_OK_BUTTON = "id:places auth close";
        MY_LIST_OK_BUTTON = "xpath://XCUIElementTypeButton[@name='places auth close']";
        //CLOSE_ARTICLE_BUTTON = "id:back";
        CLOSE_ARTICLE_BUTTON = "xpath://XCUIElementTypeButton[@name='Back']";
    }

    public iOSArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }
}
