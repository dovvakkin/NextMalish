package BotLibraris;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static java.lang.Thread.sleep;

public class Bot implements Runnable {
    private String login, password;
    private VkDriver driver;

    public Bot(String login, String password) {
        this.login = login;
        this.password = password;
        driver = new VkDriver();
    }

    public void run() {
        try {
            while (true) {
                vkLogin();
                surfingLogin();
                executeTasks();
                sleep(70*60*1000);
            }
        } catch (InterruptedException ignored) {
        }
    }


    public void vkLogin() {
        driver.get("https://m.vk.com/");
        WebElement textArea;
        textArea = driver.findElement(By.name("email"));
        textArea.sendKeys(login);

        textArea = driver.findElement(By.name("pass"));
        textArea.sendKeys(password);

        WebElement submit = driver.findElement(By.className("fi_row_new"));
        try {
            submit.click();
        } catch (Exception ignored) {
        }
    }

    public void surfingLogin() throws InterruptedException {
        driver.get("https://vkserfing.ru");
        WebElement login = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[3]/div[1]/div[2]/div[1]/a[1]"));
        login.click();
        sleep(3000);
        try {
            WebElement close = driver.findElement(By.xpath("/html/body/div[6]/table/tbody/tr/td/div/div"));
            close.click();
        } catch (Exception e) {
            sleep(10000);
            WebElement close = driver.findElement(By.xpath("/html/body/div[6]/table/tbody/tr/td/div/div"));
            close.click();
        }
    }

//    public List<WebElement> scanTasks() throws InterruptedException {
//        sleep(3000);
//        return driver.findElements(By.xpath("//div[@class='join-group']"));
//    }

    public void executeTasks() throws InterruptedException {
        sleep(3500);
        List<WebElement> tasks = driver.findElements(By.xpath("//div[@class='join-group']"));

        driver.taskManager(tasks);
    }

    public void gorshochekNeVari() {
        driver.quit();
    }

}