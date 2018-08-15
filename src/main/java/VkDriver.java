import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static java.lang.Thread.sleep;

public class VkDriver extends FirefoxDriver {
    public void taskManager(List<WebElement> taskList) throws InterruptedException {
        findElement(By.tagName("html")).sendKeys(Keys.END); //scroll to end of page

        for (String task:taskList) {
            if (task.startsWith("Вступить в сообщество")) {
                joinGroup();
            }
            else if (task.startsWith("Добавить в друзья")) {
                addToFriends();
            }
            else if (task.startsWith("Рассказать друзьям")) {
                makeRepost();
            }
        }
    }

    private void tripleScroll() {
        Actions chain = new Actions();
        chain.keyDown(Keys.UP).keyUp(Keys.UP).keyDown(Keys.UP).keyUp(Keys.UP).keyDown(Keys.UP).keyUp(Keys.UP).perform();
    }

    private void redirectToMVK() throws InterruptedException {
        sleep(2000);
        String link = getCurrentUrl().replace("https://vk.com/","https://m.vk.com/");
        get(link);
    }

    private void likeOnPage() throws InterruptedException {
        try {
            redirectToMVK();
            sleep(5000);
            findElement(By.xpath("//a[@onclick='return ajax.click(this, Like);']")).click();
        } catch (Exception e) {}
    }

    private void joinGroup() throws InterruptedException {
        try {
            redirectToMVK();
            sleep(5000);
            findElement(By.xpath("//a[@class='button wide_button']")).click();
        } catch (Exception e) {}
    }

    private void addToFriends() throws InterruptedException {
        try {
            redirectToMVK();
            sleep(5000);
            findElement(By.xpath("//a[@class='button wide_button acceptFriendBtn']")).click();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            findElement(By.xpath("//a[@class='button wide_button']")).click();
        } catch (Exception e) {}
    }

    private void makeRepost() throws InterruptedException {
        try {
            redirectToMVK();
            sleep(5000);
            findElement(By.xpath( "//html/body/div[1]/div[2]/div[2]/div/div[2]/div/div[1]/div[2]/div[4]/div/span[1]/a[2]/i")).click();
            findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div/form/div[4]/input")).click();
        } catch (Exception e) {}
    }
}
