package com.vrspring.sao.impl;

import com.alibaba.media.MediaConfiguration;
import com.alibaba.media.MediaDir;
import com.alibaba.media.Result;
import com.alibaba.media.client.impl.DefaultMediaClient;
import com.alibaba.media.common.PagedList;
import com.alibaba.media.manage.ManageClient;
import com.alibaba.media.upload.*;
import com.alibaba.media.upload.impl.DefaultUploadClient;
import com.alibaba.media.upload.impl.DefaultUploadTokenClient;
import com.vrspring.sao.TaeBcFileSAO;
import libs.fastjson.com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component("taeBcFileSAO")
public class TaeBcFileSAOImpl implements TaeBcFileSAO {
    private static String TOKEN;
    private MediaConfiguration configuration;
    private ManageClient client;
    private UploadClient uploadClient;

    private void initTaeParam() {
        configuration = new MediaConfiguration();
        configuration.setAk("23452119");
        configuration.setSk("fc41f7e71c2bafdb8e3652e5fe99ae81");
        configuration.setNamespace("vrspring");
        /**
         * 初始化 MediaClient
         */
        client = new DefaultMediaClient(configuration);
        if (StringUtils.isEmpty(TOKEN)) {
            UploadTokenClient tokenClient = new DefaultUploadTokenClient(configuration);

            /**
             * 为用户指定上传策略
             * 下面的上传策略指定了:
             *      用户上传文件允许覆盖
             *      该用户凭证的失效时间为当前时间之后的一个小时, 在之后的一个小时之内Token都可以作为用户的上传凭证
             *      失效时间设置为-1时表示永不失效
             */
            UploadPolicy uploadPolicy = new UploadPolicy();
            uploadPolicy.setInsertOnly(UploadPolicy.INSERT_ONLY_NONE);
            uploadPolicy.setExpiration(-1);

            /**
             * 请求Token服务,为该用户申请该上传策略对应的Token
             */
            TOKEN = tokenClient.getUploadToken(uploadPolicy);
            System.out.println("---->TOKEN:"+TOKEN);
        }
        /**
         * 初始化 UploadClient
         */
        uploadClient = new DefaultUploadClient();
    }

    @Override
    public List<MediaDir> getDirs(String soursDir, int pageNum, int pageSize) {
        initTaeParam();
        /**
         * 分页获取指定文件夹下的目录
         */
        try {
            Result<PagedList<MediaDir>> mediaDirsResult = client.listDirs(soursDir,
                    pageNum, pageSize);
            if (mediaDirsResult.isSuccess()) {
                return mediaDirsResult.getData();
            } else { // 调用出错
                System.out.println(mediaDirsResult.toString());
            }
        } catch (Exception e) {
            System.out.println("请求百川异常");
        }
        return null;
    }

    @Override
    public void uploadDirectory(String dir) {
        initTaeParam();
        uploadDirectory(new File(dir), "/upload");
    }

    private boolean uploadDirectory(File file, String bcDir) {
        try {
            if (file.isDirectory()) {
                boolean dirFlag = false;
                bcDir = bcDir + "/"+file.getName();
                Result<Boolean> dirExistsResult = client.existsDir(bcDir);
                System.out.println("创建目录：" + bcDir + dirExistsResult);
                if (!dirExistsResult.isSuccess()) {
                    Result<Void> createDirResult = client.createDir(bcDir);
                    if (createDirResult.isSuccess()) { // 创建成功
                        dirFlag = true;
                    } else {
                        dirFlag = false;
                        throw new Exception("创建目录失败");
                    }
                } else {
                    dirFlag = true;
                }
                if (dirFlag) {
                    File[] files = file.listFiles();
                    for (File f : files) {
                        uploadDirectory(f, bcDir);
                    }
                }
            } else {
                uploadFile(file, bcDir);
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public void uploadFile(File file, String dir) {
        UploadRequest uploadRequest = new UploadRequest(TOKEN);
        uploadRequest.setFile(file);
        uploadRequest.setDir(dir);
        uploadRequest.setName(file.getName());
        Result<UploadResponse> result = uploadClient.upload(uploadRequest);
        if (result.isSuccess()) {
            // 调用接口成功,打印出上传接口的返回信息
            System.out.println("上传成功" + JSON.toJSONString(result.getData()));
        } else {
            // 调用接口失败,输出错误信息便于排查问题
            System.out.println(JSON.toJSONString(result));
        }
    }
}
