import BotLibraris.Bot;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Bot bot = new Bot("89258396534", "k0zhepnin@");
        Thread t1 = new Thread(bot);
        t1.start();

        //t1.interrupt();
        //System.out.println("arbeit");
    }
}
