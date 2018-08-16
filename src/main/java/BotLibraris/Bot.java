package BotLibraris;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class Bot implements Runnable {
    private static Logger log = Logger.getLogger(Bot.class.getName());
    private String login, password;
    private VkDriver driver;

    public Bot(String login, String password) {
        this.login = login;
        this.password = password;
        driver = new VkDriver();
    }

    public void run() {
        Random random = new Random();
        int iterator = 0;
        vkLogin();
        surfingLogin();
        while (true) {
            // for "user's night" imitation
            if (iterator < 10) {
                executeTasks();
            }

            iterator = ++iterator % 13;
            int sleepTime = (random.nextInt(15) + 60) * 60 * 1000;
            log.info("  next executing in (min) " + (sleepTime / 60000) + "    (iterator = " + iterator + ")");
            try {
                sleep(sleepTime);
            } catch (InterruptedException e) {
            }
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

    public void surfingLogin() {
        try {
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
        } catch (InterruptedException ignored) {
        }
    }

//    public List<WebElement> scanTasks() throws InterruptedException {
//        sleep(3000);
//        return driver.findElements(By.xpath("//div[@class='join-group']"));
//    }

    public void executeTasks() {
        try {
            sleep(3500);
            List<WebElement> tasks = driver.findElements(By.xpath("//div[@class='join-group']"));
            driver.taskManager(tasks);
        } catch (InterruptedException ignored) {
        }
    }
}