package com.elf.app.configs;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileHandler {

    public static final String FILE_PATH = "C:/docs/";
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
    public static boolean saveFile(MultipartFile file, String path) {
        try {
            if (file == null) {
                return false;
            }
            file.transferTo(new File(path));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
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
                File file = new File(path);
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
        return new File(path);
    }
}
