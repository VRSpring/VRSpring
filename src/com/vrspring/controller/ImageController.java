package com.vrspring.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.vrspring.Bean.IndexBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.media.MediaDir;
import com.vrspring.sao.TaeBcFileSAO;
import com.vrspring.util.ConfigConstants;

/**
 * @author LIHAO
 * @version 0.1
 */
@Controller
public class ImageController {

    // @RequestMapping("/list")
    // public ModelAndView list(HttpServletRequest request) throws IOException {
    // System.out.println("请求开始");
    // String path = request.getSession().getServletContext().getRealPath("/") +
    // ConfigConstants.IMAGE_DIRECTORY;
    // File[] arr = new File(path).listFiles();
    // List<Map<String, String>> list = new LinkedList<Map<String, String>>();
    // if (arr != null && arr.length > 0) {
    // for (File f : arr) {
    // Map<String, String> map = new HashMap<String, String>();
    // map.put("dir",ConfigConstants.DNS_NAME+"/"+ConfigConstants.IMAGE_DIRECTORY+
    // "/" + f.getName()
    // + "/tour.html");
    // File panos = new File(f, "/panos");
    // File thumb = new File(panos.listFiles()[0], "/thumb.jpg");
    // String p = thumb.getPath();
    // map.put("path", ConfigConstants.DNS_NAME+"/" +
    // p.substring(p.indexOf(ConfigConstants.IMAGE_DIRECTORY)));
    // String name = f.getName();
    // map.put("name", name);
    // list.add(map);
    // }
    // }
    // Map<String, Object> model = new HashMap<String, Object>();
    // model.put("list", list);
    // return new ModelAndView("/list.jsp", model);
    // }
    @RequestMapping("/list")
    @ResponseBody
    public List list(HttpServletRequest request) throws IOException {
        System.out.println("请求开始");
        List<MediaDir> listMeDirs = new TaeBcFileSAO().getDirs("/" + ConfigConstants.IMAGE_DIRECTORY, 1, 10);
        List<IndexBean> list = new ArrayList<>();
        if (listMeDirs != null && listMeDirs.size() > 0) {
            for (MediaDir dir : listMeDirs) {
                IndexBean item = new IndexBean();
                item.setDir(ConfigConstants.DNS_NAME + "/VR-Engine/tour.html");
                item.setName(dir.getName());
                item.setPath(ConfigConstants.IMAGE_URL + "/" + dir.getDir() + "/panos/1.tiles/thumb.jpg");
                item.setTourxml(ConfigConstants.IMAGE_URL + "/" + dir.getDir() + "/tour.xml");
                list.add(item);
            }
        }
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("list", list);
        return list;
    }

}
