package nazzer.log;

public class LogHandler {

    /**
     * Allows the application to log correctly.
     * @param msg
     * @param info
     * @param caller
     */
    public static void logMessage(String msg, LogType info, Class caller) {
        String prefix;
        switch(info) {
            case SUCCESS:
                prefix = "Success";
                break;
            case INFO:
                prefix = "Info";
                break;
            case WARNING:
                prefix = "Warning";
                break;
            case SEVERE:
                prefix = "Severe";
                break;
            default:
                prefix = "Unknown";
        }

        System.out.println("["+prefix.toUpperCase()+"] ["+caller.getSimpleName()+"] " + msg);
    }

}
