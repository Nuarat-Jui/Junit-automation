import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Random;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Junit {
    WebDriver driver;
    @BeforeAll
    public void setup(){
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }
    @Test
    @DisplayName("Check if submission is successful")
    public void submission() throws InterruptedException {
        driver.get("https://www.digitalunite.com/practice-webform-learners");

        WebElement name= driver.findElement(By.id("edit-name"));
        name.sendKeys("jui");

        WebElement number= driver.findElement(By.id("edit-number"));
        number.sendKeys("01710666022");

        Utils.scroll(driver,300);

        WebElement date= driver.findElement(By.id("edit-date"));
        date.sendKeys("19");
        date.sendKeys("Jun");
        date.sendKeys(Keys.ARROW_RIGHT);
        date.sendKeys("2024");

        WebElement email= driver.findElement(By.id("edit-email"));
        email.sendKeys("jui1@test.com");

        Utils.scroll(driver,400);

        WebElement about= driver.findElement(By.id("edit-tell-us-a-bit-about-yourself-"));
        about.sendKeys("I'm learning selenium using java");

        driver.findElement(By.id("edit-uploadocument-upload")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/Bug_report.xlsx");

        Utils.scroll(driver,200);
        try{
            WebElement cookie= driver.findElements(By.className("onetrust-close-btn-handler")).get(0);
            cookie.click();
        } catch (Exception e) {

        }
        //terms and con
        driver.findElements(By.className("js-form-required")).get(1).click();
        Thread.sleep(5000);
        //submit
        Actions ac=new Actions(driver);
        ac.click(driver.findElements(By.className("js-form-submit")).get(1)).perform();

        Thread.sleep(4000);

        String msgPage=driver.getWindowHandle();
        driver.switchTo().window(msgPage);

        String actual=driver.findElement(By.tagName("h1")).getText();
        String expected="Thank you for your submission!";
        Assertions.assertTrue(actual.equals(expected));

    }
    @Test
    @DisplayName("Check if registration is successful")
    public void registration(){
        driver.get("https://demo.wpeverest.com/user-registration/guest-registration-form/");
        WebElement firstNamename= driver.findElement(By.id("first_name"));
        firstNamename.sendKeys("nusrat");

        WebElement lastname= driver.findElement(By.id("last_name"));
        lastname.sendKeys("jui");

        Random rand=new Random();
        int num=100+rand.nextInt(900);

        WebElement email= driver.findElement(By.id("user_email"));
        email.sendKeys("jui"+num+"@me.com");


        WebElement btn= driver.findElement(By.id("radio_1665627729_Female"));
        btn.click();

        WebElement pass= driver.findElement(By.id("user_pass"));
        pass.sendKeys("123asd&hhfhfudgfuegfefh-=edgdgfgfhfhfjfk");

        Utils.scroll(driver,200);
        WebElement date= driver.findElement(By.xpath("//input[@data-id='date_box_1665628538']"));
        JavascriptExecutor js= (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='2010-07-14';", date);


        WebElement nationality= driver.findElement(By.id("input_box_1665629217"));
        nationality.sendKeys("bangladeshi");


        WebElement phoneField = driver.findElement(By.xpath("//input[@id='phone_1665627880']"));
        phoneField.sendKeys("1010103333");

        Select options=new Select(driver.findElement(By.id("country_1665629257")));
        options.selectByValue("BD");

        Utils.scroll(driver,1500);


        WebElement terms= driver.findElement(By.id("privacy_policy_1665633140"));
        terms.click();

        WebElement submit= driver.findElement(By.className("ur-submit-button"));
        submit.click();


        String actual=driver.findElement(By.xpath("//div[@id='ur-submit-message-node']//ul")).getText();
        String expected="successfully registered";
        Assertions.assertTrue(actual.contains(expected));

    }

    @Test
    @DisplayName("Scrape data from table")
    public void scrapData() throws IOException {
        driver.get("https://dsebd.org/latest_share_price_scroll_by_value.php");
        FileWriter writer = new FileWriter(System.getProperty("user.dir") + "/src/test/resources/Table.txt", true);

        List<WebElement> tables = driver.findElements(By.className("table"));
        WebElement table = tables.get(1);
        List<WebElement> allrows=table.findElements(By.tagName("tr"));
        int size=allrows.size();

        for(int i=1;i<size;i++){
            List<WebElement> allcols=allrows.get(i).findElements(By.tagName("td"));
            StringBuilder values = new StringBuilder();
            for(WebElement col:allcols) {
                String text=col.getText();
                values.append(text).append("\t");

            }
            writer.write(values.toString().trim() + "\n");
        }
        writer.close();
    }
    @AfterAll
        public void closeDriver(){
        driver.quit();
    }
}
