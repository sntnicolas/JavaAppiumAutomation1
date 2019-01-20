package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.Factories.ArticlePageObjectFactory;
import lib.ui.Factories.MyListsPageObjectFactory;
import lib.ui.Factories.NavigationUIFactory;
import lib.ui.Factories.SearchPageObjectFactory;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    private static final String name_of_folder = "Learning programming";


    @Test
    public void testSaveFirstArticleForMyList() // lesson3.3-3.4
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Meriel Tufnell");
        SearchPageObject.clickByArticleWithSubstring("British jockey");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
  //      String article_title = ArticlePageObject.getArticleTitle();
        String article_title = "Meriel Tufnell";

        if (Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyListFirstTime(name_of_folder);
        } else {
            ArticlePageObject.addArticleToMySavedFirstTime();
        }
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()){
            MyListPageObject.openFolderByName(name_of_folder);
        }
//        MyListPageObject.swipeByArticleToDelete(article_title);
        MyListPageObject.swipeByArticleToDelete("Meriel Tufnell");
    }


}
