package BotLibraris;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;


public class Bot implements Runnable {
    private static Logger log = Logger.getLogger(Bot.class.getName());
    // data pattern: https://www.tutorialspoint.com/java/java_date_time.htm
    private SimpleDateFormat formatTime = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    private String login, password;
    private VkDriver driver;
    private boolean checkMoneyCall = false;

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
            if (iterator < 10)
                executeTasks();

            if (checkMoneyCall)
                innerCheckMoneyFunc();
            iterator = ++iterator % 13;
            int sleepTime = (random.nextInt(15) + 60) * 60 * 1000;
            //todo
            System.out.println(sleepTime);
            sleepTime = sleepTime / 10;

            log.info("next executing in (min) " + (sleepTime / (60 * 1000)) + "    (iterator = " + iterator +
                    ") | now: " + formatTime.format(new Date()) + '\n');
            try {
                sleep(sleepTime);
            } catch (InterruptedException ignored) {
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
        } catch (Exception e) {
            log.warning("did not log into VK: " + e.toString());
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
        } catch (InterruptedException e) {
            log.warning("did not log into surfing:  " + e.toString());
        }
    }

    public void checkMoney(){
        checkMoneyCall = true;
    }

    private void innerCheckMoneyFunc(){

        checkMoneyCall = false;
    }

    public void executeTasks() {
        try {
            sleep(3500);
            List<WebElement> tasks = null;
            try {
                tasks = driver.findElements(By.xpath("//div[@class='join-group']"));
            } catch (org.openqa.selenium.NoSuchElementException e) {
                log.log(Level.FINE, "no tasks, time: " + formatTime.format(new Date()));
            }
            if (tasks != null)
                driver.taskManager(tasks);
        } catch (Exception e) {
            log.warning("tasks did not executed, time: " + formatTime.format(new Date()) +
                    '\n' + "  error: " + e.toString());
        }
    }
}