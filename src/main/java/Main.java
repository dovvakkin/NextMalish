import BotLibraris.Bot;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    private static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws InterruptedException {
        loggingConfiguration();
        //Bot bot = new Bot("+79258396534", "k0zhepnin@");
        Bot bot = new Bot("+79164658740", "Marina");
        Thread t1 = new Thread(bot);
        t1.start();
        //t1.interrupt();
        //System.out.println("arbeit");
    }

    private static void loggingConfiguration() {
        try {
            LogManager.getLogManager().readConfiguration(Main.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }
    }
}
