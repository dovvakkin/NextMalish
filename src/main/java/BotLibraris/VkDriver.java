package BotLibraris;

import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.Set;

import static java.lang.Thread.sleep;

class VkDriver extends FirefoxDriver {

    void taskManager(List<WebElement> taskList) throws InterruptedException {
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

            //TODO logging
            System.out.println(strTask);

            if (strTask.startsWith("Вступить в сообщество")) {
                joinGroup();
            } else if (strTask.startsWith("Добавить в друзья")) {
                addToFriends();
            } else if (strTask.startsWith("Рассказать друзьям")) {
                makeRepost();
            }
            close();
            switchTo().window(parentWindow);
            sleep(4000);
            webTask.findElement(By.xpath(".//a[2]")).click(); //check task
        }
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

    private void likeOnPage() throws InterruptedException {
        try {
            redirectToMVK();
            sleep(5000);
            findElement(By.xpath("//a[@onclick='return ajax.click(this, Like);']")).click();
        } catch (Exception ignored) {
        }
    }

    private void joinGroup() throws InterruptedException {
        try {
            redirectToMVK();
            sleep(5000);
            findElement(By.xpath("//a[@class='button wide_button']")).click();
        } catch (Exception ignored) {
        }
    }

    private void addToFriends() throws InterruptedException {
        try {
            redirectToMVK();
            sleep(5000);
            findElement(By.xpath("//a[@class='button wide_button acceptFriendBtn']")).click();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            findElement(By.xpath("//a[@class='button wide_button']")).click();
        } catch (Exception e) {
        }
    }

    private void makeRepost() throws InterruptedException {
        try {
            redirectToMVK();
            sleep(5000);
            findElement(By.xpath("//html/body/div[1]/div[2]/div[2]/div/div[2]/div/div[1]/div[2]/div[4]/div/span[1]/a[2]/i")).click();
            findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div/form/div[4]/input")).click();
        } catch (Exception ignored) {
        }
    }
}
