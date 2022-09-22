package stevebot.core.misc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stevebot.core.player.PlayerUtils;

public class StevebotLog {

    private static final Logger logger = LogManager.getLogger(Config.MODID);

    /**
     * Sends the message to the players chat (if possible) and to the logger of this mod.
     *
     * @param message the message to log
     */
    public static void log(String message) {
        log(true, message);
    }


    /**
     * Sends the message to the players chat (if possible) and to the logger of this mod only if {@code Config.isVerboseMode()} is true.
     *
     * @param message the message to log
     */
    public static void logNonCritical(String message) {
        log(false, message);
    }


    /**
     * Sends the message to the players chat (if possible) and to the logger of this mod.
     *
     * @param message  the message to log
     * @param critical set to false to not send the message if {@code Config.isVerboseMode()} is false
     */
    public static void log(boolean critical, String message) {
        if (!Config.isVerboseMode() && !critical) {
            return;
        }
        if (PlayerUtils.hasPlayer()) {
            PlayerUtils.sendMessage(message);
        } else {
            getLogger().info(message);
        }
    }

    /**
     * @return the {@link Logger}
     */
    public static Logger getLogger() {
        return logger;
    }

}
