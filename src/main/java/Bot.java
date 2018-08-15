import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static java.lang.Thread.sleep;

public class Bot {
    private String login, password;
    private WebDriver driver;

    public Bot (String login, String password){
        this.login = login;
        this.password = password;
        driver = new FirefoxDriver();
    }

    public void vkLogin() {
        driver.get("https://m.vk.com/");
        WebElement textArea;
        textArea= driver.findElement(By.name("email"));
        textArea.sendKeys(login);

        textArea = driver.findElement(By.name("pass"));
        textArea.sendKeys(password);

        WebElement submit = driver.findElement(By.className("fi_row_new"));
        submit.click();
    }

    public void serfingLogin() throws InterruptedException {
        driver.get("https://vkserfing.ru");
        WebElement login = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[3]/div[1]/div[2]/div[1]/a[1]"));
        login.click();
        sleep(3000);
        try {
            WebElement close = driver.findElement(By.xpath("/html/body/div[6]/table/tbody/tr/td/div/div"));
            close.click();
        } finally {
            sleep(10000);
            WebElement close = driver.findElement(By.xpath("/html/body/div[6]/table/tbody/tr/td/div/div"));
            close.click();
        }
    }

    public void gorshochekNeVari() {
        driver.quit();
    }

    public void serfLogin() {

    }
}