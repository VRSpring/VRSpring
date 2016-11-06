package com.vrspring.sao;

import com.alibaba.media.MediaDir;

import java.io.File;
import java.util.List;

public interface TaeBcFileSAO
{
	public List<MediaDir> getDirs(String soursDir, int pageNum, int pageSize);
	public void uploadDirectory(String dir);
	public void uploadFile(File file, String dir);
}
