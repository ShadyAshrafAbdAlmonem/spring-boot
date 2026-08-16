package com.inventory.inventory_management_system.barcode.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.UPCAWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class BarcodeGenerator {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 150;

    /**
     * Generate a Code 128 barcode (supports alphanumeric characters)
     *
     * @param barcodeText the text to encode
     * @return byte array of PNG image
     */
    public byte[] generateBarcodeImage(String barcodeText) {
        if (barcodeText == null || barcodeText.trim().isEmpty()) {
            throw new IllegalArgumentException("Barcode text cannot be null or empty");
        }

        try {
            Code128Writer writer = new Code128Writer();
            BitMatrix bitMatrix = writer.encode(
                    barcodeText.trim(),
                    BarcodeFormat.CODE_128,
                    DEFAULT_WIDTH,
                    DEFAULT_HEIGHT
            );

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate barcode: " + e.getMessage(), e);
        }
    }

    /**
     * Generate a barcode with custom dimensions
     *
     * @param barcodeText the text to encode
     * @param width       the width of the barcode image
     * @param height      the height of the barcode image
     * @return byte array of PNG image
     */
    public byte[] generateBarcodeImage(String barcodeText, int width, int height) {
        if (barcodeText == null || barcodeText.trim().isEmpty()) {
            throw new IllegalArgumentException("Barcode text cannot be null or empty");
        }
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive");
        }

        try {
            Code128Writer writer = new Code128Writer();
            BitMatrix bitMatrix = writer.encode(
                    barcodeText.trim(),
                    BarcodeFormat.CODE_128,
                    width,
                    height
            );

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate barcode: " + e.getMessage(), e);
        }
    }

    /**
     * Generate a QR code from text
     *
     * @param text the text to encode in QR code
     * @return byte array of PNG image
     */
    public byte[] generateQRCode(String text) {
        return generateQRCode(text, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Generate a QR code with custom dimensions
     *
     * @param text  the text to encode
     * @param width the width of the QR code
     * @param height the height of the QR code
     * @return byte array of PNG image
     */
    public byte[] generateQRCode(String text, int width, int height) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("QR code text cannot be null or empty");
        }
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive");
        }

        try {
            com.google.zxing.qrcode.QRCodeWriter qrCodeWriter = new com.google.zxing.qrcode.QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(
                    text.trim(),
                    com.google.zxing.BarcodeFormat.QR_CODE,
                    width,
                    height
            );

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();

        } catch (WriterException | IOException e) {
            throw new RuntimeException("Failed to generate QR code: " + e.getMessage(), e);
        }
    }

    /**
     * Generate an EAN-13 barcode (requires 12 or 13 digits)
     *
     * @param ean13Code the EAN-13 code (12 or 13 digits)
     * @return byte array of PNG image
     */
    public byte[] generateEAN13Barcode(String ean13Code) {
        if (ean13Code == null || !ean13Code.matches("\\d{12,13}")) {
            throw new IllegalArgumentException("EAN-13 code must be 12 or 13 digits");
        }

        try {
            EAN13Writer writer = new EAN13Writer();
            BitMatrix bitMatrix = writer.encode(
                    ean13Code,
                    BarcodeFormat.EAN_13,
                    DEFAULT_WIDTH,
                    DEFAULT_HEIGHT
            );

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate EAN-13 barcode: " + e.getMessage(), e);
        }
    }

    /**
     * Generate a UPC-A barcode (requires 11 or 12 digits)
     *
     * @param upcaCode the UPC-A code (11 or 12 digits)
     * @return byte array of PNG image
     */
    public byte[] generateUPCABarcode(String upcaCode) {
        if (upcaCode == null || !upcaCode.matches("\\d{11,12}")) {
            throw new IllegalArgumentException("UPC-A code must be 11 or 12 digits");
        }

        try {
            UPCAWriter writer = new UPCAWriter();
            BitMatrix bitMatrix = writer.encode(
                    upcaCode,
                    BarcodeFormat.UPC_A,
                    DEFAULT_WIDTH,
                    DEFAULT_HEIGHT
            );

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate UPC-A barcode: " + e.getMessage(), e);
        }
    }

}
