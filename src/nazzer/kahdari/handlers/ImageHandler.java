package nazzer.kahdari.handlers;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ImageHandler {

    public static HashMap<String, Image> images = new HashMap();

    public static void initImages() {
        images.put("logo", getImage("logo.png"));
    }

    private static Image getImage(String data) {
        try {
            Image icon = new ImageIcon(ImageHandler.class.getResource("../images/"+data)).getImage();
            return icon;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
