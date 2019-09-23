package com.huangniuniu.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {

    //设置白名单
    private static final List<String> CONTENT_TYPES = Arrays.asList("image/png", "image/jpeg","image/gif","image/jpg","video/mp4");

    //日志
    private static final Logger  LOGGER = LoggerFactory.getLogger(UploadService.class);

    @Autowired
    private FastFileStorageClient storageClient;

    public String uploadImage(MultipartFile file) {

            //文件名
            String originalFilename = file.getOriginalFilename();
            //校验文件类型
            String contentType = file.getContentType();
            if(!CONTENT_TYPES.contains(contentType)){
                LOGGER.info("文件类型不合法：{}",originalFilename);
                return null;
            }

        try {
            //校验文件内容
            /*BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if(bufferedImage == null){
                LOGGER.info("文件内容不合法：{}",originalFilename);
                return null;
            }*/
            if(file.getSize() == 0 || file.isEmpty()){
                LOGGER.info("文件内容为空：{}",originalFilename);
                return null;
            }
            //获取后缀名
            String ext = StringUtils.substringAfterLast(originalFilename, ".");

            //保存到服务器
           // file.transferTo(new File("C:\\Users\\Administrator\\Pictures\\test\\"+originalFilename));
            StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);

            //返回url，进行回显
           // return "http://image.leyou.com/"+originalFilename;
            return "http://image.zzwl.xyz/"+storePath.getFullPath();
        } catch (IOException e) {
            LOGGER.info("服务器内部错误：{}",originalFilename);
            e.printStackTrace();
        }
        return null;
    }
}
