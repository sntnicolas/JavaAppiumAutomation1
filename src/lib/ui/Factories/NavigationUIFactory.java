package lib.ui.Factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.Android.AndroidNavigationUI;
import lib.ui.IOS.iOSNavigationUI;
import lib.ui.NavigationUI;

public class NavigationUIFactory
{
    public static NavigationUI get(AppiumDriver driver)
    {
        if(Platform.getInstance().isAndroid()) {
            return new AndroidNavigationUI(driver);
        } else {
            return new iOSNavigationUI(driver);
        }
    }
}
