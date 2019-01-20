package lib.ui.IOS;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class iOSMyListsPageObject extends MyListsPageObject
{
    static
    {
        //ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[@name='{TITLE}']";
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{TITLE}')]";
        //ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[@name='Meriel Tufnell British jockey']"
    }

    public iOSMyListsPageObject(AppiumDriver driver)
    {
        super(driver);
    }
}