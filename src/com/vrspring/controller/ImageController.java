package com.vrspring.controller;

import com.alibaba.media.MediaDir;
import com.vrspring.Bean.Banner;
import com.vrspring.Bean.HomeBean;
import com.vrspring.Bean.IndexBean;
import com.vrspring.sao.TaeBcFileSAO;
import com.vrspring.util.ConfigConstants;
import com.vrspring.util.DateComparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @author LIHAO
 * @version 0.1
 */
@Controller
public class ImageController
{

	@Autowired
	private TaeBcFileSAO taeBcFileSAO;

	@RequestMapping("/list")
	@ResponseBody
	public HomeBean list(HttpServletRequest request) throws IOException
	{
		System.out.println("请求开始！");
		HomeBean homeBean = new HomeBean();
		List<MediaDir> listMeDirs = taeBcFileSAO.getDirs("/"
				+ ConfigConstants.IMAGE_DIRECTORY, 1, 10);
		List<IndexBean> list = new ArrayList<IndexBean>();
		// 从所有VR资源当中随机选取BANNER_NUM个图片放入banner中
		List<Banner> banners = new ArrayList<Banner>();
		if (listMeDirs != null && listMeDirs.size() > 0)
		{
			Collections.sort(listMeDirs,new DateComparator());
			for (MediaDir dir : listMeDirs)
			{
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
				if (banners.size() < ConfigConstants.BANNER_NUM)
				{
					String detailUrl = item.getDir()+"?tourxml="+item.getTourxml()+"&title="+item.getName();
					banners.add(new Banner(item.getPath(), item.getName(),detailUrl));
				}
			}

			homeBean.setBanners(banners);
			homeBean.setList(list);
		}

		return homeBean;
	}

}
