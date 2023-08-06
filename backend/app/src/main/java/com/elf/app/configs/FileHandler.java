package com.elf.app.configs;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.elf.app.models.utils.PdfCompressor;

public class FileHandler {

    public static final String FILE_FOLDER = "C:";
    public static final String FILE_PATH = "/docs/";
    public static final String DOCUMENT_TYPE = ".pdf";

    /**
     * Get the path to save the file
     * 
     * @param file Archive to be saved
     * @return Path to save the file
     */
    public static String getFilePath(MultipartFile file) {
        if (file == null) {
            return null;
        }
        return FILE_PATH + UUID.randomUUID().toString() + DOCUMENT_TYPE;
    }

    /**
     * Save a file in the server
     * 
     * @param file Archive to be saved
     * @param path Path to save the file
     * @return
     */
    public static File saveFile(MultipartFile file, String path) {
        File filer = null;
        try {
            if (file == null) {
                return null;
            }
            filer = new File(FILE_FOLDER + path);
            file.transferTo(filer);
            try{
            PdfCompressor.manipulatePdf(new File(FILE_FOLDER + path).getAbsolutePath(),FILE_FOLDER + path);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        return filer;
    }

    /**
     * Delete a file from the server
     * 
     * @param path Path to the file
     * @return True if the file was deleted, false otherwise
     */
    public static boolean deleteFile(List<String> paths) {
        boolean status = false;
        try {
            for (String path : paths) {
                File file = new File(FILE_FOLDER + path);
                status = file.delete();
            }
        } catch (Exception e) {
            return status;
        }
        return status;
    }

    /**
     * Get a file from the server
     * 
     * @param path Path to the file
     * @return File from the server
     */
    public static File getFile(String path) {
        return new File(FILE_FOLDER + path);
    }
}
