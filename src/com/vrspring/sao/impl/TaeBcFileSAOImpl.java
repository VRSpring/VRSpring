package com.vrspring.sao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import libs.fastjson.com.alibaba.fastjson.JSON;

import com.alibaba.media.MediaConfiguration;
import com.alibaba.media.MediaDir;
import com.alibaba.media.Result;
import com.alibaba.media.client.impl.DefaultMediaClient;
import com.alibaba.media.common.PagedList;
import com.alibaba.media.manage.ManageClient;
import com.vrspring.sao.TaeBcFileSAO;

@Component("taeBcFileSAO")
public class TaeBcFileSAOImpl implements TaeBcFileSAO
{
	private MediaConfiguration configuration;
	private ManageClient client;
	
	private void initTaeParam()
	{
		configuration = new MediaConfiguration();
		configuration.setAk("23452119");
		configuration.setSk("fc41f7e71c2bafdb8e3652e5fe99ae81");
		configuration.setNamespace("vrspring");
		/**
		 * 初始化 MediaClient
		 */
		client = new DefaultMediaClient(configuration);
	}
	
	@Override
	public List<MediaDir> getDirs(String soursDir,int pageNum,int pageSize)
	{
		initTaeParam();
		/**
		 * 分页获取指定文件夹下的目录
		 */
		try
		{
			Result<PagedList<MediaDir>> mediaDirsResult = client.listDirs(soursDir,
					pageNum, pageSize);
			if (mediaDirsResult.isSuccess())
			{ 
				return mediaDirsResult.getData();
			} else
			{ // 调用出错
				System.out.println(mediaDirsResult.toString());
			}
		} catch (Exception e)
		{
			System.out.println(	"请求百川异常");
		}
		return null;
	}
}
