package com.cherry.erp.common.utils;

import com.cherry.erp.account.model.persistent.SpiUser;
import com.cherry.erp.account.service.UserService;
import com.cherry.erp.common.business.GenericParameterService;
import com.cherry.erp.common.exception.SpiException;
import com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum;
import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.common.model.persistent.GenericParameter;
import com.cherry.erp.company.model.persistent.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;

@Service
public class SpiUtils {

    @Autowired
    private UserService userService;

    @Autowired
    private GenericParameterService genericParameterService;

    public <T extends GenericEntity> void setModifiedByAndCreatedByFields(T obj) {
        String currentUserName;
        try {
            currentUserName = userService.getCurrentUserName();
        } catch (Exception e) {
            currentUserName = "anonymous";
        }
        if (obj.getId() == null || obj.getId() == 0) {
            obj.setCreatedBy(currentUserName);
            obj.setCreationDate(new Date());
        }
        obj.setModifiedBy(currentUserName);
        obj.setModificationDate(new Date());
    }

    public static boolean isNumeric(String s) {

        boolean f1 = s != null && s.matches("[-+]?\\d*\\.?\\d+");
        boolean f2 = s != null && s.matches("\\d*");

        return f1 || f2;
    }

    public static boolean isDate(String s) {

        boolean f1 = s != null && s.matches("\\d{4}-\\d{2}-\\d{2}");
        boolean f2 = s != null && s.matches("\\d{2}-\\d{2}-\\d{4}");
        boolean f3 = s != null && s.matches("\\d{2}-\\d{2}-\\d{2}");

        return f1 || f2 || f3;
    }

    public static boolean isString(String s) {
        return s != null && s.matches(".*[1-9.].*");
    }

    public static String buildQuery(String query, String jsonConfig) {

        JsonReader defaultJsonConfigReader = Json.createReader(new StringReader(jsonConfig));
        JsonArray defaultJsonConfigArray = defaultJsonConfigReader.readArray();
        for (int i = 0; i < defaultJsonConfigArray.size(); i++) {
            JsonObject jsonData = defaultJsonConfigArray.getJsonObject(i);
            String value = jsonData.get("value").toString();
            String key = jsonData.get("key").toString();
            value = value.replace("\"", "");
            key = key.replace("\"", "");
            query = query.replaceAll("\\{" + key + "\\}", value);
        }
        defaultJsonConfigReader.close();
        return query;
    }

    public Company findCurrentCompany() {
        Company company = null;
        SpiUser currentUser = userService.getCurrentUser();
        if (Objects.nonNull(currentUser)) {
            company = currentUser.getEmployee().getCompany();
        }
        if (company == null) {
            throw new SpiException(HttpStatus.BAD_REQUEST, ResponseRestMsgCodeEnum.ACTION_NOT_ALLOWED);
        }
        return company;
    }

    public GenericParameter findCurrentGenericParameter() {
        return this.genericParameterService.findByCompany();
    }

    public SpiUser findCurrentUser() {
        return userService.getCurrentUser();
    }

    public Boolean isConnectedUser() {
        return userService.isConnectedUser();
    }

    public <T extends GenericEntity> void setOldValues(T obj, T oldObj) {
        obj.setVersion(oldObj.getVersion());
        obj.setCreatedBy(oldObj.getCreatedBy());
        obj.setCreationDate(oldObj.getCreationDate());
    }

    public Integer extractYearFromReference(String reference) {
        if (reference.indexOf('/') != -1) {
            return Integer.valueOf(reference.substring(reference.indexOf('/') + 1));
        }
        return null;
    }

    public Integer extractNumberFromReference(String reference) {
        if (reference.indexOf('-') != -1 && reference.indexOf('/') != -1) {
            return Integer.valueOf(reference.substring(reference.indexOf('-') + 1, reference.indexOf('/')));
        }
        return null;
    }

    public File convertAndSaveBase64ToImage(String base64String, String destinationPath, String fileName) {
        if (Objects.nonNull(base64String)) {
            String[] strings = base64String.split(",");
            String extension;
            switch (strings[0]) {//check image's extension
                case "data:image/jpeg;base64":
                    extension = "jpeg";
                    break;
                case "data:image/png;base64":
                    extension = "png";
                    break;
                default://should write cases for more images types
                    extension = "jpg";
                    break;
            }
            //convert base64 string to binary data
            byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
            Path path = Paths.get(destinationPath);
            File file = new File(path.toString(), fileName + "." + extension);
            if (!file.exists()) {
                // créer l'hiérarchie si elle n'existe pas
                file.getParentFile().mkdirs();
            }
            try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
                outputStream.write(data);
                return file;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
