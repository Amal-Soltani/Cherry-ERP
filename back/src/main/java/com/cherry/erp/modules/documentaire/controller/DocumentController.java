package com.cherry.erp.modules.documentaire.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.documentaire.controller.Mapper.DocumentMapper;
import com.cherry.erp.modules.documentaire.model.dto.DocumentDto;
import com.cherry.erp.modules.documentaire.service.DocumentBusiness;
import com.cherry.erp.modules.documentaire.model.persistent.Document;
import com.cherry.erp.modules.documentaire.message.ResponseMessage;
import com.cherry.erp.modules.production.model.dto.BOMDto;
import com.cherry.erp.modules.production.model.persistent.BOM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    DocumentBusiness documentBusiness;
    @Autowired
    DocumentMapper documentMapper;

    @GetMapping("/allByProduct")
    public ResponseEntity<Page<DocumentDto>> getAllPageAndProduct (@RequestParam("page") Integer page,
                                                                  @RequestParam("pageSize") Integer pageSize,
                                                                  @RequestParam("idProduct") Integer idProduct) {
        return ResponseEntity.ok().body(documentBusiness.findByPageAndProduct(page, pageSize,idProduct).map(documentMapper::toDto));
    }

    @PostMapping("/add/{id}/{indice}")
    public GenericResponse Add (@PathVariable("id") Integer id, @PathVariable("indice") String indice ){
        return success(ADD_OK_CODE, documentMapper.toDto(documentBusiness.Add(id,indice)));
    }


    @GetMapping("/findByProduct/{idProduct}")
    public GenericResponse FindByProduct(@PathVariable("idProduct") Integer idProduct) {
        return success(FIND_ALL_OK_CODE, documentMapper.toDtoList(documentBusiness.FindByProduct(idProduct)));
    }

    @GetMapping("/getOne/{ref}")
    public GenericResponse FindOne(@PathVariable("ref") String ref) {
        return success(FIND_ALL_OK_CODE, documentMapper.toDto(documentBusiness.FindOne(ref)));
    }

    @DeleteMapping("/delete/{id}")
    public GenericResponse DeleteBYId(@PathVariable("id") Integer id) {
        documentBusiness.DeleteBYId(id);
        return success(DELETE_OK_CODE, id);
    }


    @PostMapping("/upload/{idProduct}/{indice}")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,
                                                      @PathVariable("idProduct") Integer idProduct,
                                                      @PathVariable("indice") String indice) {
        String message = "";
        try {
            documentBusiness.store(file,idProduct,indice);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable("id") Integer id) {
        Document fileDB = documentBusiness.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileDB.getData());
    }
}



