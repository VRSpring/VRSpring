package com.vrspring.controller;

import com.alibaba.media.MediaDir;
import com.vrspring.Bean.Banner;
import com.vrspring.Bean.HomeBean;
import com.vrspring.Bean.IndexBean;
import com.vrspring.Bean.PanoFileBean;
import com.vrspring.sao.TaeBcFileSAO;
import com.vrspring.service.VrsourceService;
import com.vrspring.util.ConfigConstants;
import com.vrspring.util.DateComparator;

import libs.logging.org.apache.commons.logging.Log;
import libs.logging.org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author LIHAO
 * @version 0.1
 */
@Controller
public class ImageController {
    private Log log = LogFactory.getLog(ImageController.class);
    @Autowired
    private TaeBcFileSAO taeBcFileSAO;
    @Autowired
    private VrsourceService vrsourceService;


    @RequestMapping("/upload")
    @ResponseBody
    public String upload(HttpServletRequest request, PanoFileBean panoFileBean) throws IOException {
        log.info("请求上传开始！");
        panoFileBean.setFileUser("feiniao");
        //1,存储文件
        //2,启动线程。执行生成pano文件
        //3,执行上传pano
        MultipartFile file = panoFileBean.getFile();
        final String fileName = ("" + Math.random()).replace(".", "") + ".jpg";
        if (!file.isEmpty()) {
            // 文件保存路径
            String filePath = ConfigConstants.VR_NAS+"source_img/" + fileName;
            // 转存文件
            try {
                file.transferTo(new File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
//            vrsourceService.makeVRSourceFile(fileName);
//            log.info("图片处理完成，开始上传");
//            taeBcFileSAO.uploadDirectory("C:\\vrspring\\vrsource\\upload\\" + fileName.substring(0, fileName.indexOf(",")));
//
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (vrsourceService.makeVRSourceFile(fileName)) {
                            log.info("图片处理完成，开始上传");
                            taeBcFileSAO.uploadDirectory("C:\\vrspring\\vrsource\\upload\\" + fileName.substring(0, fileName.indexOf(".")));
                            log.error("pano全部上传成功！！");
                        } else {
                            log.error("生成PANO失败！！");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        return "";
    }

    @RequestMapping("/list")
    @ResponseBody
    public HomeBean list(HttpServletRequest request) throws IOException {
        log.info("请求开始！");
        HomeBean homeBean = new HomeBean();
        List<MediaDir> listMeDirs = taeBcFileSAO.getDirs("/"
                + ConfigConstants.IMAGE_DIRECTORY, 1, 10);
        List<IndexBean> list = new ArrayList<IndexBean>();
        // 从所有VR资源当中随机选取BANNER_NUM个图片放入banner中
        List<Banner> banners = new ArrayList<Banner>();
        if (listMeDirs != null && listMeDirs.size() > 0) {
            Collections.sort(listMeDirs, new DateComparator());
            for (MediaDir dir : listMeDirs) {
                String panosPath = dir.getDir() + "/panos";
                String path = ConfigConstants.IMAGE_URL
                        + taeBcFileSAO.getDirs(panosPath, 1, 10).get(0)
                        .getDir() + "/vr/pano_f.jpg";
                String tourxml = ConfigConstants.IMAGE_URL + "/" + dir.getDir()
                        + "/tour.xml";
                String name = dir.getName();
                IndexBean item = new IndexBean();
                item.setDir(ConfigConstants.DNS_NAME + "/VR-Engine/tour.html");
                item.setName(name);
                item.setPath(path);
                item.setTourxml(tourxml);
                list.add(item);
                if (banners.size() < ConfigConstants.BANNER_NUM) {
                    String detailUrl = item.getDir() + "?tourxml=" + item.getTourxml() + "&title=" + item.getName();
                    banners.add(new Banner(item.getPath(), item.getName(), detailUrl));
                }
            }

            homeBean.setBanners(banners);
            homeBean.setList(list);
        }

        return homeBean;
    }
    @RequestMapping("/profile")
    @ResponseBody
    public HomeBean profile(HttpServletRequest request) throws IOException {
        log.info("请求个人信息开始！");
        HomeBean homeBean = new HomeBean();
        List<MediaDir> listMeDirs = taeBcFileSAO.getDirs("/upload", 1, 10);
        List<IndexBean> list = new ArrayList<IndexBean>();
        // 从所有VR资源当中随机选取BANNER_NUM个图片放入banner中
        List<Banner> banners = new ArrayList<Banner>();
        if (listMeDirs != null && listMeDirs.size() > 0) {
            Collections.sort(listMeDirs, new DateComparator());
            for (MediaDir dir : listMeDirs) {
                String panosPath = dir.getDir() + "/panos";
                String path = ConfigConstants.IMAGE_URL
                        + taeBcFileSAO.getDirs(panosPath, 1, 10).get(0)
                        .getDir() + "/vr/pano_f.jpg";
                String tourxml = ConfigConstants.IMAGE_URL + "/" + dir.getDir()
                        + "/tour.xml";
                String name = dir.getName();
                IndexBean item = new IndexBean();
                item.setDir(ConfigConstants.DNS_NAME + "/VR-Engine/tour.html");
                item.setName(name);
                item.setPath(path);
                item.setTourxml(tourxml);
                list.add(item);
                if (banners.size() < ConfigConstants.BANNER_NUM) {
                    String detailUrl = item.getDir() + "?tourxml=" + item.getTourxml() + "&title=" + item.getName();
                    banners.add(new Banner(item.getPath(), item.getName(), detailUrl));
                }
            }

            homeBean.setBanners(banners);
            homeBean.setList(list);
        }

        return homeBean;
    }
}
