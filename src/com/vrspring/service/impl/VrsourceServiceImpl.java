package com.vrspring.service.impl;

import com.vrspring.sao.TaeBcFileSAO;
import com.vrspring.service.VrsourceService;
import com.vrspring.util.ConfigConstants;
import com.vrspring.util.KrPanoUtil;
import libs.logging.org.apache.commons.logging.Log;
import libs.logging.org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by lenovo on 2016-10-22.
 */
@Component("vrsourceServerce")
public class VrsourceServiceImpl implements VrsourceService {
    private Log log = LogFactory.getLog(VrsourceServiceImpl.class);
    private final static String batName = ConfigConstants.VR_NAS + "vr_engine\\krpano-1.19-pr6-win\\MAKE VTOUR (MULTIRES) droplet.bat";
    @Autowired
    private TaeBcFileSAO taeBcFileSAO;

    @Override
    public void makeVRSource(String filePath) {
        String root = "C:\\vrspring\\source_img";
        File rootFile = new File(root);
        int count = 0;
        for (File imgFile : rootFile.listFiles()) {
            if (imgFile.getName().endsWith(".jpg")) {
                count++;
                log.info("正在处理第" + count + "张图片：" + imgFile.getName());
                String dest = imgFile.getName().substring(0,
                        imgFile.getName().indexOf("."));
                KrPanoUtil.runbat(batName, imgFile.getAbsolutePath());
                log.info("处理完毕第" + count + "张图片："
                        + imgFile.getName() + "======dest:" + dest);
                KrPanoUtil.renameToNewFile(root + "\\vtour", root + "\\"
                        + dest);
                KrPanoUtil.cleanFiles(root + "\\" + dest);
            }
        }
    }

    @Override
    public boolean makeVRSourceFile(String file) {
        String root = ConfigConstants.VR_NAS;
        File imgFile = new File(root + "source_img\\" + file);
        if (imgFile.getName().endsWith(".jpg")) {
            log.info("正在处理图片：" + imgFile.getName());
            String dest = imgFile.getName().substring(0,
                    imgFile.getName().indexOf("."));
            KrPanoUtil.runbat(batName, imgFile.getAbsolutePath());
            File fileM = new File(root + "source_img\\vtour");
            if (fileM.exists() && fileM.isDirectory()) {
                log.info("处理完毕图片："
                        + imgFile.getName() + "======dest:" + dest);
                KrPanoUtil.renameToNewFile(root + "source_img\\vtour", root + "source_img\\"
                        + dest);
                KrPanoUtil.cleanFiles(root + "source_img\\" + dest);
                KrPanoUtil.moveFolder(root + "source_img\\" + dest, root + "vrsource\\upload\\" + dest);
                return true;
            } else {
                log.error("Image is not PanoFile!!!");
                return false;
            }
        }
        log.error("file is not exist");
        return false;
    }

    @Override
    public void uploadDir(String dir) {
        taeBcFileSAO.uploadDirectory(dir);
    }
}
