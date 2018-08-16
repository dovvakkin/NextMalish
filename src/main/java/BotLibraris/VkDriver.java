package BotLibraris;

import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

class VkDriver extends FirefoxDriver {
    private static Logger log = Logger.getLogger(Bot.class.getName());
    // data pattern: https://www.tutorialspoint.com/java/java_date_time.htm
    private SimpleDateFormat formatTime = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    void taskManager(List<WebElement> taskList) throws InterruptedException {
        log.log(Level.FINE, "begin task performing: " + formatTime.format(new Date()));

        findElement(By.tagName("html")).sendKeys(Keys.END); //scroll to end of page
        taskList = Lists.reverse(taskList);
        for (WebElement webTask : taskList) {
            String strTask = webTask.findElement(By.xpath(".//span")).getText();

            webTask.findElement(By.xpath(".//a[1]")).click(); //go to vk

            String parentWindow = getWindowHandle();
            Set<String> allWindows = getWindowHandles();
            for (String curWindow : allWindows) {
                switchTo().window(curWindow);
            }

            //todo delete
            System.out.println(strTask);

            log.log(Level.FINE, strTask);

            if (strTask.startsWith("Вступить в сообщество")) {
                joinGroup();
            } else if (strTask.startsWith("Добавить в друзья")) {
                addToFriends();
            } else if (strTask.startsWith("Рассказать друзьям")) {
                makeRepost();
            } else if (strTask.startsWith("Поставить лайк ")) {
                likeOnPage();
            }
            close();
            switchTo().window(parentWindow);
            sleep(4000);
            webTask.findElement(By.xpath(".//a[2]")).click(); //check task
        }
        log.log(Level.FINE, "end task performing: " + formatTime.format(new Date()));
    }

//    private void tripleScroll() {
//        Actions chain = new Actions(this);
//        chain.keyDown(Keys.UP).keyUp(Keys.UP).keyDown(Keys.UP).keyUp(Keys.UP).keyDown(Keys.UP).keyUp(Keys.UP).build().perform();
//    }

//    private void hitroClick(WebElement elem) {
//        Actions link_presser = new Actions(this);
//        link_presser.moveToElement(elem, 2, 2).click().build().perform();
//    }

    private void redirectToMVK() throws InterruptedException {
        sleep(2000);
        String link = getCurrentUrl().replace("https://vk.com/", "https://m.vk.com/");
        get(link);
    }

    private void likeOnPage() {
        try {
            redirectToMVK();
            sleep(5000);
            findElement(By.xpath("//a[@onclick='return ajax.click(this, Like);']")).click();
        } catch (InterruptedException ignored) {
        }
    }

    private void joinGroup() {
        try {
            redirectToMVK();
            sleep(5000);
            findElement(By.xpath("//a[@class='button wide_button']")).click();
            sleep(500);
        } catch (Exception ignored) {
        }
    }

    private void addToFriends() throws InterruptedException {
        try {
            redirectToMVK();
            sleep(5000);
            findElement(By.xpath("//a[@class='button wide_button acceptFriendBtn']")).click();
            sleep(500);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            findElement(By.xpath("//a[@class='button wide_button']")).click();
            sleep(500);
        } catch (Exception ignored) {
        }
    }

    private void makeRepost() {
        try {
            redirectToMVK();
            sleep(5000);
            findElement(By.xpath("//html/body/div[1]/div[2]/div[2]/div/div[2]/div/div[1]/div[2]/div[4]/div/span[1]/a[2]/i")).click();
            sleep(500);
            findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div/form/div[4]/input")).click();
            sleep(500);
        } catch (Exception ignored) {
        }
    }
}
