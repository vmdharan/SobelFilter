import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bitmap {
    
    // Input bitmap file
    private FileInputStream fileInput;
    
    // Bitmap header
    private byte bmpId1;
    private byte bmpId2;
    private int bmpSize;
    private short reserved1;
    private short reserved2;
    private int offset;
    
    // Bitmap Info header
    private int headerSize;
    private int bitmapWidth;
    private int bitmapHeight;
    private short colorPlanes;
    private short bitsPerPixel;
    private int compressionMethod;
    private int imageSize;
    private int hRez;
    private int vRez;
    private int numColors;
    private int numImportantColors;
    
    // RGB color data
    public byte[][][] data;

    public Bitmap() {
        // to do
    }
    
    // A method to read a given image BMP image file and store 
    // it into the bitmap object.
    public void readBitmap(String input) {
        int i, j;

        fileInput = null;

        try {
            fileInput = new FileInputStream(new File(input));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SobelFilter.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Read Bitmap header
        readBitmapHeader();

        // Read Bitmap Info header
        readBitmapInfoHeader();

        // Print out header information
        printBitmapHeader();
        printBitmapInfoHeader();

        try {
            int k = 0;
            byte[] bytes = new byte[imageSize];
            
            fileInput.read(bytes);
            data = new byte[bitmapHeight][bitmapWidth][3];
            
            for (i = 0; i < bitmapHeight; i++) {
                data[i] = new byte[bitmapWidth][3];
                for (j = 0; j < bitmapWidth; j++) {
                    data[i][j] = new byte[3];
                    data[i][j][0] = bytes[k];
                    data[i][j][1] = bytes[k + 1];
                    data[i][j][2] = bytes[k + 2];
                    k += 3;
                }
            }
        } catch (IOException e) {
            System.exit(-1);
        }
    }

    // Method to read the bitmap header.
    private void readBitmapHeader() {
        bmpId1 = getByte();
        bmpId2 = getByte();
        bmpSize = getInt();
        reserved1 = getShort();
        reserved2 = getShort();
        offset = getInt();
    }

    // Method to read the bitmap info header.
    private void readBitmapInfoHeader() {
        headerSize = getInt();
        bitmapWidth = getInt();
        bitmapHeight = getInt();
        colorPlanes = getShort();
        bitsPerPixel = getShort();
        compressionMethod = getInt();
        imageSize = getInt();
        hRez = getInt();
        vRez = getInt();
        numColors = getInt();
        numImportantColors = getInt();
    }

    // Method to display the bitmap header.
    public void printBitmapHeader() {
        System.out.println("+----BitmapHeader----+");
        System.out.println((char) bmpId1 + "" + (char) bmpId2);
        System.out.println(bmpSize);
        System.out.println(reserved1);
        System.out.println(reserved2);
        System.out.println(offset);
        System.out.println("+--------------------+");
    }

    // Method to display the bitmap info header.
    public void printBitmapInfoHeader() {
        System.out.println("+----BitmapInfoHeader----+");
        System.out.println(headerSize);
        System.out.println(bitmapWidth);
        System.out.println(bitmapHeight);
        System.out.println(colorPlanes);
        System.out.println(bitsPerPixel);
        System.out.println(compressionMethod);
        System.out.println(imageSize);
        System.out.println(hRez);
        System.out.println(vRez);
        System.out.println(numColors);
        System.out.println(numImportantColors);
        System.out.println("+------------------------+");
    }
    
    // Return the image width.
    public int getWidth() {
        return bitmapWidth;
    }
    
    // Return the image height.
    public int getHeight() {
        return bitmapHeight;
    }

    // Method to read a byte from the file.
    public byte getByte() {
        byte d = 0;
        try {
            d = (byte) fileInput.read();
        } catch (IOException ex) {
            Logger.getLogger(SobelFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (byte) d;
    }

    // Method to read a 16-bit Short from the file.
    public short getShort() {
        short c = 0, d = 0;
        try {
            c = (byte) fileInput.read();
            d = (byte) fileInput.read();

        } catch (IOException ex) {
            Logger.getLogger(SobelFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (short) ((d << 8) + c);
    }

    // Method to read a 32-bit Int from the file.
    public int getInt() {
        short a = 0, b = 0, c = 0, d = 0;
        try {
            a = (byte) fileInput.read();
            b = (byte) fileInput.read();
            c = (byte) fileInput.read();
            d = (byte) fileInput.read();

        } catch (IOException ex) {
            Logger.getLogger(SobelFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (int) ((d << 24) + (c << 16) + (b << 8) + a);
    }
}
