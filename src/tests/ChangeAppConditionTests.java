package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {
    @Test
    public void testChangesScreenOrientationOnSearchResults()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Meriel Tufnell");
        SearchPageObject.waitForSearchResult("British jockey");
        SearchPageObject.clickByArticleWithSubstring("British jockey");
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String title_before_orientation = ArticlePageObject.getArticleTitle();
        this.rotateScreenLandscape();
        String title_after_orientation = ArticlePageObject.getArticleTitle();
        assertEquals(
                "Article title have been changed after screen rotation",
                title_before_orientation,
                title_after_orientation
        );
        this.rotateScreenPortrait();
        String title_after_second_orientation = ArticlePageObject.getArticleTitle();
        assertEquals(
                "Article title have been changed after screen rotation",
                title_before_orientation,
                title_after_second_orientation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Meriel Tufnell");
        SearchPageObject.waitForSearchResult("British jockey");
        this.backgroundApp(2);
        SearchPageObject.waitForSearchResult("British jockey");
    }
}
