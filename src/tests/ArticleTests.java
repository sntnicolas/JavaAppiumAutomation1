package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.Factories.ArticlePageObjectFactory;
import lib.ui.Factories.SearchPageObjectFactory;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    @Test
    public void testCompareArticleTitle()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Meriel Tufnell");
        SearchPageObject.clickByArticleWithSubstring("British jockey");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title = ArticlePageObject.getArticleTitle();

        assertEquals(
                "We see unexpected title" ,
                "Meriel Tufnell",
                article_title
        );
    }

    @Test
    public void testSwipeArticle() // lesson3.1-3.2
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Meriel Tufnell");
        SearchPageObject.clickByArticleWithSubstring("British jockey");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();
    }

    public void testSaveTwoArticlesToMyList() // Lesson3 HomeWork1 (Ex.5) - refactored
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String article_title1 = "Meriel Tufnell";
        SearchPageObject.typeSearchLine(article_title1);
        SearchPageObject.clickByArticleWithSubstring("British jockey");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String folder_name = "Learning programming";
        ArticlePageObject.addArticleToMyListFirstTime(folder_name);
        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        String article_title2 = "Sam Twiston-Davies";
        SearchPageObject.typeSearchLine(article_title2);
        SearchPageObject.clickByArticleWithSubstring("English jockey");

        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.addArticleToMyCreatedList(folder_name);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(folder_name);
        MyListsPageObject.swipeByArticleToDelete(article_title1);
        MyListsPageObject.waitForArticleToAppearByTitle(article_title2);
    }


    @Test
    public void testAssertTitle() // Lesson3 HomeWork2 (Ex.6) (refactored)
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Meriel Tufnell");
        SearchPageObject.clickByArticleWithSubstring("British jockey");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.assertElementPresent(
                "xpath://org.wikipedia:id/view_page_title_text",
                "Cannot find Article title");
    }
}
