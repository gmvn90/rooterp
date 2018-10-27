package com.swcommodities.wsmill.clientController;

import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.swcommodities.wsmill.hibernate.dto.FileSent;
import com.swcommodities.wsmill.hibernate.dto.FileUpload;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.infrastructure.StorageService;
import com.swcommodities.wsmill.repository.FileSentRepository;
import com.swcommodities.wsmill.repository.ShippingInstructionRepository;

@Controller("clientController_fileSentController")
@Transactional
public class FileSentController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private FileSentRepository fileSentRepository;

    @Autowired(required = true)
    private ServletContext context;

    @Autowired(required = true)
    private HttpServletRequest request;
    @Autowired ShippingInstructionRepository shippingInstructionRepository;
    @Resource(name = "configConfigurer") Properties configConfigurer;

    protected User getUser(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        return user;
    }

    @RequestMapping(value = "/millclient/file_sents.json", method = RequestMethod.POST)
    public ResponseEntity<FileSent> uploadFile(
            @RequestParam("file") String uploadfileData,
            @RequestParam("name") String name,
            @RequestParam("originalFilename") String originalFilename,
            @RequestParam("emails") String emails, HttpSession httpSession) {
        FileUpload fileUpload;
        FileSent fileSent = new FileSent();
        try {

            // Get the filename and build the local file path
            String filename = originalFilename;
            //byte[] res = Base64.decodeBase64(stringSupporterConnector.getPriceReport());
            //res = Base64.encodeBase64(res);
            fileUpload = storageService.save(Base64.decodeBase64(uploadfileData.getBytes()), filename);
            fileSent.setName(name);
            fileSent.setFileUpload(fileUpload);
            fileSent.setEmails(emails);
            fileSent.setUpdater(getUser(httpSession).getUserName());
            try {
                fileSent.setUpdater(((User) request.getSession().getAttribute("user")).getUserName());
            } catch (Exception e1) {
                System.out.println("com.swcommodities.wsmill.controller.FileSentController.uploadFile()");
            }

            fileSentRepository.save(fileSent);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fileSent, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/millclient/file_set.json", method = RequestMethod.POST)
    public ResponseEntity<String> setFile(
            @RequestParam("type") String type,
            @RequestParam("id") Integer id,
            @RequestParam("fileUploadId") Long fileUploadId) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(id);
        if (type.equals("reference_file")) {
            shippingInstruction.addReferenceFileSent(fileSentRepository.findOne(fileUploadId));
        } else {
            shippingInstruction.addInternalReferenceFileSent(fileSentRepository.findOne(fileUploadId));
        }
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}
