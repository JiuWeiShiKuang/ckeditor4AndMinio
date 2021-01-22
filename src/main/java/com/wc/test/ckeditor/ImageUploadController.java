package com.wc.test.ckeditor;

import com.wc.test.minio.utils.MinIoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 */
@RestController
@RequestMapping("/images")
@Slf4j
public class ImageUploadController extends HttpServlet {


    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping("/ckeditorUpload")
    public ImageUploadVo ckeditorUpload(@RequestParam("upload") MultipartFile file,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("开始上传图片");
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //图片下载地址
        String ckeditor = null;
        try {
            //图片上传
            ckeditor = MinIoUtil.upload("ckeditor", file);
        } catch (Exception e) {
            log.error("上传失败 : [{}]", Arrays.asList(e.getStackTrace()));
        }

        log.info("上传文件文件地址：{}", ckeditor);
        log.info("上传文件大小 ：{}", file.getSize());

        //回传照片回显信息
        ImageUploadVo imageUploadVo = new ImageUploadVo();
        imageUploadVo.setUploaded(1);
        imageUploadVo.setUrl(ckeditor);
        return imageUploadVo;
    }
}
