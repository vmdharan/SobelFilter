import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class SobelFilter extends Canvas {

    private static Bitmap bmpImage; // Image
    private static int width;       // Image width
    private static int height;      // Image height

    // Sobel approximation operators
    private static int Gx[][]
            = {
                {-1, 0, 1},
                {-2, 0, 2},
                {-1, 0, 1}
            };
    private static int Gy[][]
            = {
                {1, 2, 1},
                {0, 0, 0},
                {-1, -2, -1}
            };

    // Constructor
    public SobelFilter() {
        processSobelFilter();
    }

    public static void initialise() {
        bmpImage = new Bitmap();
        bmpImage.readBitmap("test.bmp");
        width = bmpImage.getWidth();
        height = bmpImage.getHeight();

        bmpImage.printBitmapHeader();
        bmpImage.printBitmapInfoHeader();
    }

    public static void main(String[] args) {
        initialise();

        // Open a window to render image
        JFrame frame = new JFrame();
        frame.setSize(width, height);
        frame.add(new SobelFilter());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void processSobelFilter() {
        int i, j;
        int xr, xg, xb, yr, yg, yb, R, G, B;

        // Byte arrays to hold results from applying Sobel operator.
        byte[][][] dgx = new byte[bmpImage.getHeight()][bmpImage.getWidth()][3];
        byte[][][] dgy = new byte[bmpImage.getHeight()][bmpImage.getWidth()][3];

        // Initialise the byte arrays
        for (i = 0; i < bmpImage.getHeight(); i++) {
            dgx[i] = new byte[bmpImage.getWidth()][];
            dgy[i] = new byte[bmpImage.getWidth()][];

            for (j = 0; j < bmpImage.getWidth(); j++) {
                dgx[i][j] = new byte[3];
                dgx[i][j][0] = bmpImage.data[i][j][0];
                dgx[i][j][1] = bmpImage.data[i][j][1];
                dgx[i][j][2] = bmpImage.data[i][j][2];

                dgy[i][j] = new byte[3];
                dgy[i][j][0] = bmpImage.data[i][j][0];
                dgy[i][j][1] = bmpImage.data[i][j][1];
                dgy[i][j][2] = bmpImage.data[i][j][2];
            }
        }

        // Convolution
        for (i = 1; i < bmpImage.getHeight() - 1; i++) {
            for (j = 1; j < bmpImage.getWidth() - 1; j++) {

                // Process the red intensity for the x dataset.
                xr = dgx[i - 1][j - 1][0] * Gx[0][0]
                        + dgx[i - 1][j][0] * Gx[0][1]
                        + dgx[i - 1][j + 1][0] * Gx[0][2]
                        + dgx[i][j - 1][0] * Gx[1][0]
                        + dgx[i][j][0] * Gx[1][1]
                        + dgx[i][j + 1][0] * Gx[1][2]
                        + dgx[i + 1][j - 1][0] * Gx[2][0]
                        + dgx[i + 1][j][0] * Gx[2][1]
                        + dgx[i + 1][j + 1][0] * Gx[2][2];

                // Process the red intensity for the y dataset.
                yr = dgy[i - 1][j - 1][0] * Gy[0][0]
                        + dgy[i - 1][j][0] * Gy[0][1]
                        + dgy[i - 1][j + 1][0] * Gy[0][2]
                        + dgy[i][j - 1][0] * Gy[1][0]
                        + dgy[i][j][0] * Gy[1][1]
                        + dgy[i][j + 1][0] * Gy[1][2]
                        + dgy[i + 1][j - 1][0] * Gy[2][0]
                        + dgy[i + 1][j][0] * Gy[2][1]
                        + dgy[i + 1][j + 1][0] * Gy[2][2];

                // Process the green intensity for the x dataset.
                xg = dgx[i - 1][j - 1][1] * Gx[0][0]
                        + dgx[i - 1][j][1] * Gx[0][1]
                        + dgx[i - 1][j + 1][1] * Gx[0][2]
                        + dgx[i][j - 1][1] * Gx[1][0]
                        + dgx[i][j][1] * Gx[1][1]
                        + dgx[i][j + 1][1] * Gx[1][2]
                        + dgx[i + 1][j - 1][1] * Gx[2][0]
                        + dgx[i + 1][j][1] * Gx[2][1]
                        + dgx[i + 1][j + 1][1] * Gx[2][2];

                // Process the green intensity for the y dataset.
                yg = dgy[i - 1][j - 1][1] * Gy[0][0]
                        + dgy[i - 1][j][1] * Gy[0][1]
                        + dgy[i - 1][j + 1][1] * Gy[0][2]
                        + dgy[i][j - 1][1] * Gy[1][0]
                        + dgy[i][j][1] * Gy[1][1]
                        + dgy[i][j + 1][1] * Gy[1][2]
                        + dgy[i + 1][j - 1][1] * Gy[2][0]
                        + dgy[i + 1][j][1] * Gy[2][1]
                        + dgy[i + 1][j + 1][1] * Gy[2][2];

                // Process the blue intensity for the x dataset.
                xb = dgx[i - 1][j - 1][2] * Gx[0][0]
                        + dgx[i - 1][j][2] * Gx[0][1]
                        + dgx[i - 1][j + 1][2] * Gx[0][2]
                        + dgx[i][j - 1][2] * Gx[1][0]
                        + dgx[i][j][2] * Gx[1][1]
                        + dgx[i][j + 1][2] * Gx[1][2]
                        + dgx[i + 1][j - 1][2] * Gx[2][0]
                        + dgx[i + 1][j][2] * Gx[2][1]
                        + dgx[i + 1][j + 1][2] * Gx[2][2];

                // Process the blue intensity for the y dataset.
                yb = dgy[i - 1][j - 1][2] * Gy[0][0]
                        + dgy[i - 1][j][2] * Gy[0][1]
                        + dgy[i - 1][j + 1][2] * Gy[0][2]
                        + dgy[i][j - 1][2] * Gy[1][0]
                        + dgy[i][j][2] * Gy[1][1]
                        + dgy[i][j + 1][2] * Gy[1][2]
                        + dgy[i + 1][j - 1][2] * Gy[2][0]
                        + dgy[i + 1][j][2] * Gy[2][1]
                        + dgy[i + 1][j + 1][2] * Gy[2][2];

                // Get the color magnitude from processing both filters
                R = (int) sqr1(sq1(xr) + sq1(yr));
                G = (int) sqr1(sq1(xg) + sq1(yg));
                B = (int) sqr1(sq1(xb) + sq1(yb));

                // Limit the color intensities to a valid range.
                if (R > 255) {
                    R = 255;
                } else if (R < 0) {
                    R = 0;
                }

                if (G > 255) {
                    G = 255;
                } else if (G < 0) {
                    G = 0;
                }

                if (B > 255) {
                    B = 255;
                } else if (B < 0) {
                    B = 0;
                }

                // Store the processed data
                bmpImage.data[i][j][0] = (byte) (R & 0x000000FF);
                bmpImage.data[i][j][1] = (byte) (G & 0x000000FF);
                bmpImage.data[i][j][2] = (byte) (B & 0x000000FF);
            }
        }
    }

    // Return the square of an integer.
    public static int sq1(int x) {
        return x * x;
    }

    // Return the square root of an integer.
    public static int sqr1(int x) {
        return (int) Math.sqrt(x);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int cr, cg, cb, ct;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cr = bmpImage.data[i][j][0] & 0x000000FF;
                cg = bmpImage.data[i][j][1] & 0x000000FF;
                cb = bmpImage.data[i][j][2] & 0x000000FF;

                // Color edge
                //g.setColor(new Color(((0xff000000 | (cr << 16)) | (cg << 8)) | (cb)));
                // Greyscale edge
                ct = (cr + cg + cb) / 3;
                g.setColor(new Color(((0xff000000 | (ct << 16)) | (ct << 8)) | (ct)));

                // Draw the edge
                g.drawLine(j, height - 1 - i, j, height - 1 - i);
            }
        }
    }
}
