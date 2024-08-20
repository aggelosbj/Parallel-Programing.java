import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class SetPixels {
    static final int CHUNK_SIZE = 1000; 

    static class ParallelTask extends RecursiveAction {
        private BufferedImage img;
        private int start;
        private int end;
        private int threshold;
        private int redShift;
        private int greenShift;
        private int blueShift;

        public ParallelTask(BufferedImage img, int start, int end, int threshold, int redShift, int greenShift, int blueShift) {
            this.img = img;
            this.start = start;
            this.end = end;
            this.threshold = threshold;
            this.redShift = redShift;
            this.greenShift = greenShift;
            this.blueShift = blueShift;
        }

        protected void compute() {
            if ((end - start) <= threshold) {
                convertAndShiftPixels(img, start, end, redShift, greenShift, blueShift);
            } else {
                int mid = (start + end) / 2;
                invokeAll(
                    new ParallelTask(img, start, mid, threshold, redShift, greenShift, blueShift),
                    new ParallelTask(img, mid + 1, end, threshold, redShift, greenShift, blueShift)
                );
            }
        }
    }

    public static void main(String args[]) {
        if (args.length > 6) {
            System.out.println("Usage: java SetPixel <file to read > <file to write");
            System.exit(1);
        }
        
        String fileNameR = "C:\\Users\\kldoi\\eclipse-workspace\\SimpleParalllel_Lab3\\src\\original.jpg";
        String fileNameW = "C:\\Users\\kldoi\\eclipse-workspace\\SimpleParalllel_Lab3\\src\\grey2.jpg";

     

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileNameR));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        long start = System.currentTimeMillis();

        int redShift = 100;
        int greenShift = 100;
        int blueShift = 100;
        int threshold = 1000; 

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new ParallelTask(img, 0, img.getHeight() - 1, threshold, redShift, greenShift, blueShift));

        long elapsedTimeMillis = System.currentTimeMillis() - start;

        try {
            File file = new File(fileNameW);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Done...");
        System.out.println("time in ms = " + elapsedTimeMillis);
    }

    private static void convertAndShiftPixels(BufferedImage img, int start, int end, int redShift, int greenShift, int blueShift) {
        for (int y = start; y <= end; y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int pixel = img.getRGB(x, y);
                Color color = new Color(pixel, true);
                int red = (color.getRed() + redShift) % 256;
                int green = (color.getGreen() + greenShift) % 256;
                int blue = (color.getBlue() + blueShift) % 256;
                color = new Color(red, green, blue);
                img.setRGB(x, y, color.getRGB());
            }
        }
    }
}
