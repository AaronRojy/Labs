import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoaderTest {
    public static void main(String[] args) {
        String[] imagePaths = {
            "C:\\Users\\44742\\OneDrive - Lancaster University\\Labs\\java\\cache_noisettes\\images\\nut.png",
            "C:\\Users\\44742\\OneDrive - Lancaster University\\Labs\\java\\cache_noisettes\\images\\squirrel.png",
            "C:\\Users\\44742\\OneDrive - Lancaster University\\Labs\\java\\cache_noisettes\\images\\border.png",
            "C:\\Users\\44742\\OneDrive - Lancaster University\\Labs\\java\\cache_noisettes\\images\\hole.png",
            "C:\\Users\\44742\\OneDrive - Lancaster University\\Labs\\java\\cache_noisettes\\images\\grass.png"
        };

        for (String path : imagePaths) {
            System.out.println("Checking: " + path);
            File imgFile = new File(path);
            if (!imgFile.exists()) {
                System.out.println("❌ File not found");
                continue;
            }

            try {
                BufferedImage img = ImageIO.read(imgFile);
                if (img == null) {
                    System.out.println("❌ ImageIO.read() returned null — unsupported or corrupt format");
                } else {
                    System.out.println("✅ Loaded successfully: width=" + img.getWidth() + ", height=" + img.getHeight());
                }
            } catch (IOException e) {
                System.out.println("❌ IOException while loading: " + e.getMessage());
            }

            System.out.println();
        }
    }
}
