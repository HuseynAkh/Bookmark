package home.yorku.bookmarks.controller.search;
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

    public void downloadImage(String urlString, String title){ //download and store book cover image in current directory
        try {
            URL url = new URL(urlString); //convert passed url String into url object
            this.image = ImageIO.read(url); //download url
            ImageIO.write(image, "jpg", new File("./temporary.jpg")); //store in current directory.
            System.out.println("Image downloaded @: ");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
