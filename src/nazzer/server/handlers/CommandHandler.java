package nazzer.server.handlers;

import nazzer.server.objects.Command;

import java.util.HashMap;

public class CommandHandler {

    public static HashMap<String, Command> commandHashMap = new HashMap<>();

    public static void init() {
        commandHashMap.put("authenticate", new Command());
    }
}
