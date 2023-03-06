package main.handler;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ResourcesHandler {
    public ResourcesHandler() {

    }

    public static JSONObject loadJson(String directory) {
            InputStream is = ResourcesHandler.class.getResourceAsStream(directory);
            JSONParser jsonParser = new JSONParser();



            // File is =  new File(Objects.requireNonNull(ResourcesHandler.class.getResource(directory)).toURI());

            try {
                return  (JSONObject)jsonParser.parse(new InputStreamReader(is, "UTF-8"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


    }

    public static BufferedImage loadImg(String directory) {
        InputStream is = ResourcesHandler.class.getResourceAsStream(directory);
        BufferedImage bufferedImage;

        try {
            bufferedImage = ImageIO.read(is);
            return bufferedImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
