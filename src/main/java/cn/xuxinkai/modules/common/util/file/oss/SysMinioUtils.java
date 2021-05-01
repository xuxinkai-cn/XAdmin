package cn.xuxinkai.modules.common.util.file.oss;

import cn.xuxinkai.modules.common.entity.ToolOssStorage;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xuxinkai
 */
@Slf4j
public class SysMinioUtils {
    //private String endpoint;
    //private String accessKey;
    //private String secretKey;
    //private String bucketName;

    public ToolOssStorage upload(MultipartFile file, String fileName) {
        String bucketName = "oss";
        try {
            MinioClient minioClient = new MinioClient("https://minio.xuxinkai.cn", "xuxinkai", "Xujiajia1998");
            minioClient.putObject(bucketName, fileName, file.getInputStream(), "application/octet-stream");
            return new ToolOssStorage(
                    "minio",
                    bucketName,
                    "/".concat(bucketName).concat("/").concat(fileName),
                    "private"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}

