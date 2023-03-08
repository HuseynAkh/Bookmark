package home.Search;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageDownloader {
    private BufferedImage image;

    public ImageDownloader(){
        this.image = null;
    }

    public void downloadImage(String urlString, String title){
        try {
            URL url = new URL(urlString);
            this.image = ImageIO.read(url);
            ImageIO.write(image, "jpg", new File("./temporary.jpg"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
