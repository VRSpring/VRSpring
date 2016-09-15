package com.vrspring.sao;

import java.util.List;

import libs.fastjson.com.alibaba.fastjson.JSON;

import com.alibaba.media.MediaConfiguration;
import com.alibaba.media.MediaDir;
import com.alibaba.media.Result;
import com.alibaba.media.client.impl.DefaultMediaClient;
import com.alibaba.media.common.PagedList;
import com.alibaba.media.manage.ManageClient;

public interface TaeBcFileSAO
{
	public List<MediaDir> getDirs(String soursDir,int pageNum,int pageSize);
}
