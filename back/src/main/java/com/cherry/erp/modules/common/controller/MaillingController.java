package com.cherry.erp.modules.common.controller;

import com.cherry.erp.modules.common.mail.controller.dto.FolderMailDto;
import com.cherry.erp.modules.common.mail.controller.dto.Mail;
import com.cherry.erp.modules.common.mail.controller.dto.MailDto;
import com.cherry.erp.modules.common.mail.controller.dto.MailFolderDto;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mailling")
@Api(tags = "mailling")
@Slf4j
public class MaillingController {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping(value = "/send",  consumes = {"multipart/form-data"})
    public ResponseEntity<MailDto> send(@RequestPart("mail") Mail mail,
                                        @RequestPart(value = "file0", required = false) MultipartFile filePart,
                                        HttpServletResponse response) throws MessagingException, IOException {


        JavaMailSenderImpl mailSender = createMailSender();

        MimeMessage msg = mailSender.createMimeMessage();

        //MimeMessage msg = javaMailSender.createMimeMessage();
        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        if(mail.getTo() !=null && !mail.getTo().isEmpty()) {
            String[] arrayTo = new String[mail.getTo().size()];
            helper.setTo(mail.getTo().toArray(arrayTo));
        }
        if(mail.getCc() !=null && !mail.getCc().isEmpty()) {
            String[] arrayCC = new String[mail.getCc().size()];
            helper.setCc(mail.getCc().toArray(arrayCC));
        }
        if(mail.getBcc() !=null && !mail.getBcc().isEmpty()) {
            String[] arrayBCC = new String[mail.getBcc().size()];
            helper.setBcc(mail.getCc().toArray(arrayBCC));
        }

        //helper.setFrom("dddddAloccccc");
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getContent(), true); // true = text/html

        // hard coded a file path
        if(filePart != null) {
            File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+filePart.getOriginalFilename());
            filePart.transferTo(convFile);
            //byte[] bytes = filePart.getBytes();
            //InputStream initialStream = filePart.getInputStream();
            helper.addAttachment(filePart.getOriginalFilename(), convFile);
        }

        javaMailSender.send(msg);

        return ResponseEntity.ok().body(null);

    }

    JavaMailSenderImpl createMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @GetMapping("/inbox")
    public ResponseEntity<FolderMailDto> inbox(@RequestParam("folder") String folderToFind, @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) throws MessagingException, IOException{

        FolderMailDto folderMailDto = new FolderMailDto();

        Store store = connect();
        if (store != null) {
            Folder[] folders = store.getDefaultFolder().list("*");
            for (Folder folder : folders) {
                if ((folder.getType() & Folder.HOLDS_MESSAGES) != 0 && folderToFind.equals(folder.getFullName())) {
                    folder.open(Folder.READ_ONLY);
                    int start = folder.getMessageCount() > 10 ? (folder.getMessageCount() - 10) : 1;
                    int end = folder.getMessageCount();
                    Message messages[] = folder.getMessages(start, end);

                    folderMailDto.setMessageCount(folder.getMessageCount());
                    folderMailDto.setUnreadMessageCount(folder.getUnreadMessageCount());
                    folderMailDto.setNewMessageCount(folder.getNewMessageCount());
                    folderMailDto.setFolderName(folder.getName());
                    folderMailDto.setFullName(folder.getFullName());

                    List<MailDto> mailDtoList = new ArrayList<>();
                    for (int i=0; i < messages.length; ++i) {
                        Message msg = messages[i];
                        mailDtoList.add(mapper(i+1, msg, false));
                    }
                    folderMailDto.setMailDtoList(mailDtoList);
                    break;
                }
            }
        }

        return ResponseEntity.ok().body(folderMailDto);
    }

    @GetMapping("/message")
    public ResponseEntity<MailDto> message(@RequestParam("folder") String folderToFind, @RequestParam("numero") Integer numero) throws MessagingException, IOException {

        Store store = connect();
        Folder[] folders = store.getDefaultFolder().list("*");

        for (Folder folder : folders) {
            if ((folder.getType() & Folder.HOLDS_MESSAGES) != 0 && folderToFind.equals(folder.getFullName())) {
                folder.open(Folder.READ_ONLY);
                Message msg = folder.getMessage(numero);
                return ResponseEntity.ok().body(mapper(numero, msg, true));
            }
        }

        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/folders")
    public ResponseEntity<List<MailFolderDto>> folders() throws MessagingException, IOException {
        List<MailFolderDto> list = new ArrayList<>();

        Store store = connect();
        if(store != null) {
            Folder[] folders = store.getDefaultFolder().list("*");
            for (Folder folder : folders) {
                if ((folder.getType() & Folder.HOLDS_MESSAGES) != 0) {
                    MailFolderDto mailFolderDto = new MailFolderDto();
                    log.info(folder.getFullName() + ": " + folder.getMessageCount());
                    mailFolderDto.setName(folder.getFullName());
                    mailFolderDto.setCount(folder.getMessageCount());
                    mailFolderDto.setUnreadMessageCount(folder.getUnreadMessageCount());
                    mailFolderDto.setNewMessageCount(folder.getNewMessageCount());
                    list.add(mailFolderDto);
                }
            }
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam("filename") String filename, @RequestParam("folder") String folderToFind, @RequestParam("numero") Integer numero) throws MessagingException, IOException {

        Store store = connect();
        Folder[] folders = store.getDefaultFolder().list("*");

        for (Folder folder : folders) {
            if ((folder.getType() & Folder.HOLDS_MESSAGES) != 0 && folderToFind.equals(folder.getFullName())) {
                folder.open(Folder.READ_ONLY);
                Message msg = folder.getMessage(numero);
                InputStream in = this.getFileAttachFromMimeMultipart((MimeMultipart) msg.getContent());
                // Load file as Resource
                Resource resource  = new InputStreamResource(in);
                // Try to determine file's content type
                String contentType = null;
                // Fallback to the default content type if type could not be determined
                if(contentType == null) {
                    contentType = "application/octet-stream";
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            }
        }

        return ResponseEntity.ok().body(null);
    }

    Store connect() {

        try {
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");

            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", username, password);
            return store;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private MailDto mapper(Integer numero, Message msg, boolean withContent) throws MessagingException, IOException {
        MailDto mailDto = new MailDto();

        mailDto.setNumero(numero);
        mailDto.setNumero(msg.getMessageNumber());
        mailDto.setContentType(msg.getContentType());
        mailDto.setSize(msg.getSize());
        if (msg.getReplyTo().length >= 1) {
            List<String> from =  Arrays.stream(msg.getFrom()).map(addr -> addr.toString()).collect(Collectors.toList());
            mailDto.setFrom(from);
        }
        else if (msg.getFrom().length >= 1) {

            List<String> replyTo =  Arrays.stream(msg.getReplyTo()).map(Address::toString).collect(Collectors.toList());
            mailDto.setReplyTo(replyTo);
        }
        if (msg.getAllRecipients() != null && msg.getAllRecipients().length >= 1) {
            List<String> allRecipients =  Arrays.stream(msg.getAllRecipients()).map(addr -> addr.toString()).collect(Collectors.toList());
            mailDto.setAllRecipients(allRecipients);
        }
        String subject = msg.getSubject();
        mailDto.setSubject(subject);
        mailDto.setReceivedDate(msg.getReceivedDate());
        mailDto.setSentDate(msg.getSentDate());
        if (!msg.isSet(Flags.Flag.SEEN)) {
            mailDto.setUnread(true);
        }
        if (withContent) {
            mailDto.setContent(getTextFromMessage(msg));
        }
        if (msg.getContent() instanceof MimeMultipart){
            mailDto.setAttach(getAttachFromMimeMultipart((MimeMultipart)msg.getContent()));
        }
        return mailDto;
    }

    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "<br/>" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "<br/>" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }

    private List<String> getAttachFromMimeMultipart(MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        List<String> attachList = new ArrayList<>();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("application/*") || bodyPart.isMimeType("image/*")) {
                String filename = bodyPart.getDataHandler().getName();
                attachList.add(filename);
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                attachList.addAll(getAttachFromMimeMultipart((MimeMultipart)bodyPart.getContent()));
            }
        }
        return attachList;
    }


    private InputStream getFileAttachFromMimeMultipart(MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("application/*") || bodyPart.isMimeType("image/*")) {
                String filename = bodyPart.getDataHandler().getName();
                return bodyPart.getInputStream();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                return getFileAttachFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return null;
    }
}
