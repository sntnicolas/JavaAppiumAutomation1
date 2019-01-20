package lib.ui.Factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.Android.AndroidMyListsPageObject;
import lib.ui.IOS.iOSMyListsPageObject;
import lib.ui.MyListsPageObject;

public class MyListsPageObjectFactory
{
    public static MyListsPageObject get(AppiumDriver driver)
    {
        if (Platform.getInstance().isAndroid()){
            return new AndroidMyListsPageObject(driver);
        } else {
            return new iOSMyListsPageObject(driver);
        }
    }
}
