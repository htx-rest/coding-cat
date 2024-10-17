package com.htx.controller;

import com.htx.util.FileNameUtil;
import com.htx.webapi.ResultObject;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/17 22:13
 * @Desc:
 */
@Controller
@Api(tags = "MinIO上传")
@RequestMapping("/minioController")
@Slf4j
public class MinIOController {
    private static final String bucketName = "tobebetterjavaer";

    @RequestMapping(value = "/upload",method= RequestMethod.POST)
    @ResponseBody
    @ApiOperation("上传")
    public ResultObject<String> upload(@RequestParam("file") MultipartFile file, HttpServletRequest req) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("http://39.105.208.175:9000")
                            .credentials("minioadmin", "minioadmin")
                            .build();
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());

            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {
                log.debug("桶 '{}' 存在.", bucketName);
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(FileNameUtil.getImgName(file.getOriginalFilename()))
                                .stream(file.getInputStream(),file.getInputStream().available(),-1)
                                .build());
            }
        } catch (MinioException e) {
            log.error("MinIO 上传文件出错{}" + e);
            return ResultObject.failed("MinIO 上传文件出错");
        }
        return ResultObject.success("上传成功");
    }
}
