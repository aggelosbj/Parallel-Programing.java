import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ParallelGrayScale {
    static class ParallelTask extends RecursiveAction {
        private BufferedImage img;
        private int start;
        private int end;
        private int threshold;

        public ParallelTask(BufferedImage img, int start, int end, int threshold) {
            this.img = img;
            this.start = start;
            this.end = end;
            this.threshold = threshold;
        }

        protected void compute() {
            if ((end - start) <= threshold) {
                convertToGrayScale(img, start, end);
            } else {
                int mid = (start + end) / 2;
                invokeAll(
                    new ParallelTask(img, start, mid, threshold),
                    new ParallelTask(img, mid + 1, end, threshold)
                );
            }
        }
    }

    public static void main(String[] args) {
        String fileNameR = "C:\\Users\\kldoi\\eclipse-workspace\\SimpleParalllel_Lab3\\src\\original.jpg";
        String fileNameW = "C:\\Users\\kldoi\\eclipse-workspace\\SimpleParalllel_Lab3\\src\\grey.jpg";
        int threshold = 100; // define the threshold for task granularity

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileNameR));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        long start = System.currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new ParallelTask(img, 0, img.getHeight() - 1, threshold));

        long elapsedTimeMillis = System.currentTimeMillis() - start;

        try {
            File file = new File(fileNameW);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Done...");
        System.out.println("Time in ms = " + elapsedTimeMillis);
    }

    private static void convertToGrayScale(BufferedImage img, int start, int end) {
        double redCoefficient = 0.299;
        double greenCoefficient = 0.587;
        double blueCoefficient = 0.114;

        for (int y = start; y <= end; y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int pixel = img.getRGB(x, y);
                Color color = new Color(pixel, true);
                int red = (int) (color.getRed() * redCoefficient);
                int green = (int) (color.getGreen() * greenCoefficient);
                int blue = (int) (color.getBlue() * blueCoefficient);
                int gray = red + green + blue;
                color = new Color(gray, gray, gray);
                img.setRGB(x, y, color.getRGB());
            }
        }
    }
}
