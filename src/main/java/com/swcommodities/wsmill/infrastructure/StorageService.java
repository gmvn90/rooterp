package com.swcommodities.wsmill.infrastructure;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.swcommodities.wsmill.bo.FileUrlService;
import com.swcommodities.wsmill.hibernate.dto.FileSent;
import com.swcommodities.wsmill.hibernate.dto.FileUpload;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.FileSentRepository;
import com.swcommodities.wsmill.repository.FileUploadRepository;

/**
 * Created by dunguyen on 9/23/16.
 */
@Service
public class StorageService {

    private String subDirectory = "fileUploads";
    @Autowired
    private FileUploadRepository fileUploadRepository;
    @Autowired FileSentRepository fileSentRepository;
    @Autowired
    private FileUrlService fileUrlService;
    @Resource(name = "configConfigurer") Properties configConfigurer;

    private String getExtension(String fileName) {
        String[] splits = fileName.split("\\.");
        if (splits.length > 1) {
            return splits[splits.length - 1];
        }
        return "";
    }
    
    public String getRandomName(String extension) {
        UUID uuid = UUID.randomUUID();
        return uuid + "." + extension;
    }
    
    public String getBaseDir() {
        return configConfigurer.getProperty("base_dir");
    }
    
    public void setBaseDir(String baseDir) {
    
    }

    public FileUpload save(byte[] bytes, String originName) throws Exception {
        String savedName = getRandomName(getExtension(originName));
        BufferedOutputStream stream
                = new BufferedOutputStream(new FileOutputStream(new File(getAbsolutePathByName(savedName))));
        stream.write(bytes);
        stream.close();
        FileUpload fileUpload = new FileUpload();
        fileUpload.setName(savedName);
        fileUpload.setOriginalName(originName);
        fileUrlService.setBaseStaticHost(configConfigurer.getProperty("base_static_host"));
        fileUpload.setUrl(fileUrlService.getUrlByFilename(savedName));
        fileUploadRepository.save(fileUpload);
        return fileUpload;
    }

    public FileUpload saveFromLocalFile(String fullPathWithoutName, String originName, String baseStaticHost) throws IOException {
        String savedName = getRandomName(getExtension(originName));
        Files.copy(Paths.get(fullPathWithoutName + "/" + originName), Paths.get(fullPathWithoutName + "/" + subDirectory + "/" + savedName));
        FileUpload fileUpload = new FileUpload();
        fileUpload.setName(savedName);
        fileUpload.setOriginalName(originName);
        fileUrlService.setBaseStaticHost(baseStaticHost);
        fileUpload.setUrl(fileUrlService.getUrlByFilename(savedName));
        fileUploadRepository.save(fileUpload);
        return fileUpload;
    }
    
    public Optional<FileSent> saveFileSentFromFile(MultipartFile uploadfile, String name, String emails, User user) {
        FileUpload fileUpload;
        FileSent fileSent = new FileSent();
        try {
            String filename = uploadfile.getOriginalFilename();
            fileUpload = save(uploadfile.getBytes(), filename);
            fileSent.setName(name);
            fileSent.setFileUpload(fileUpload);
            fileSent.setEmails(emails);            
            try {
                fileSent.setUpdater(user.getUserName());
            } catch(Exception e1) {
                System.out.println("saveFileSentFromFile");
            }
            fileSentRepository.save(fileSent);
            return Optional.of(fileSent);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public String getAbsolutePathByName(String fileName) {
        return this.getBaseDir() + "/" + subDirectory + "/" + fileName;
    }

    public String getSubDirectory() {
        return subDirectory;
    }

    public StorageService setSubDirectory(String subDirectory) {
        this.subDirectory = subDirectory;
        return this;
    }

    public FileUploadRepository getFileUploadRepository() {
        return fileUploadRepository;
    }

    public StorageService setFileUploadRepository(FileUploadRepository fileUploadRepository) {
        this.fileUploadRepository = fileUploadRepository;
        return this;
    }

    public FileUrlService getFileUrlService() {
        return fileUrlService;
    }

    public void setFileUrlService(FileUrlService fileUrlService) {
        this.fileUrlService = fileUrlService;
    }
    
    public void createFolderInsideBaseDir(String dirName) {
        File file = new File(Paths.get(getBaseDir(), dirName).toString());
        if(!file.exists()) {
            file.mkdirs();
        }
    }

}
