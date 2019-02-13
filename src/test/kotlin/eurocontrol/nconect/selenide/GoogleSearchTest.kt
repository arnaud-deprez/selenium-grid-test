package eurocontrol.nconect.selenide

import com.codeborne.selenide.CollectionCondition.size
import com.codeborne.selenide.CollectionCondition.sizeGreaterThan
import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Configuration
import org.openqa.selenium.By
import com.codeborne.selenide.SelenideElement
import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.Selenide.*
import com.codeborne.selenide.junit.ScreenShooter
import org.junit.BeforeClass
import org.junit.Test
import com.codeborne.selenide.junit.ScreenShooter.failedTests
import org.junit.Rule
import org.openqa.selenium.remote.DesiredCapabilities


class SearchResultsPage {
    val results: ElementsCollection
        get() = `$$`("#ires .g")

    fun getResult(index: Int): SelenideElement {
        return `$`("#ires .g", index)
    }
}

class GooglePage {
    fun searchFor(text: String): SearchResultsPage {
        `$`(By.name("q")).`val`(text).pressEnter()
        return page(SearchResultsPage::class.java)
    }
}

class GoogleSearchTest {
    companion object {
        @BeforeClass @JvmStatic
        fun setUpClass() {
            System.setProperty("selenide.browser", "chrome") // or firefox
            System.setProperty("capabilities.name", "GoogleSearch systemTest")
//            System.setProperty("selenide.remote", "http://192.168.64.99:31699/wd/hub")
        }
    }

//    or @JvmField
    @get:Rule
    val makeScreenshotOnFailure = ScreenShooter.failedTests().succeededTests()

    @Test
    fun `user can search`() {
        val page = open("https://google.com/ncr", GooglePage::class.java)
        val results = page.searchFor("selenide")
        results.results.shouldHave(sizeGreaterThan(10))
        results.getResult(0).shouldHave(text("Selenide: concise UI tests in Java"))
    }
}