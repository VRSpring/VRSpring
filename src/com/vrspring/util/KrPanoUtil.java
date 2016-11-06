package com.vrspring.util;

import com.sun.xml.internal.ws.api.message.saaj.SaajStaxWriter;
import libs.logging.org.apache.commons.logging.Log;
import libs.logging.org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class KrPanoUtil {
    private static Log log = LogFactory.getLog(KrPanoUtil.class);

    public static void runbat(String batName) {
        Process ps;
        InputStream in;
        try {
            ps = Runtime.getRuntime().exec(batName);
            InputStream fis = ps.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            PrintWriter inputWriter = new PrintWriter(ps.getOutputStream(), true);
            String line = null;
            final Long nowTime=System.currentTimeMillis();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while ((System.currentTimeMillis()-nowTime)<5000) {
                        inputWriter.print("0\n");
                    }
                }
            }).start();
            while ((line = br.readLine()) != null) {

            }
            fis.close();
            ps.waitFor();
            log.info("runbat(" + batName + ") is finished!");
        } catch (Exception e) {
            log.error(e);
        }
        log.info("child thread done");
    }

    public static void runbat(String batName, String param) {
        String bat = batName + " " + param;
        System.out.println(bat);
        runbat(bat);
    }

    public static boolean renameToNewFile(String src, String dest) {
        File srcDir = new File(src);
        boolean isOk = srcDir.renameTo(new File(dest));
        log.info("renameToNewFile is OK ? :" + isOk);
        return isOk;
    }

    private static void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
            log.info("deleteFile:" + file.getAbsolutePath());
        } else {
            log.info("所删除的文件不存在！" + '\n');
        }
    }

    public static void cleanFiles(String dest) {
        Set<String> set = new HashSet<String>();
        set.add("plugins");
        set.add("tour.html");
        set.add("tour.js");
        set.add("tour.swf");
        set.add("tour_editor.html");
        for (String name : set) {
            deleteFile(new File(dest + "\\" + name));
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String  原文件路径  如：c:/fqf.txt
     * @param newPath String  复制后路径  如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {  //文件存在时
                InputStream inStream = new FileInputStream(oldPath);  //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            log.info("复制单个文件操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String  原文件路径  如：c:/fqf
     * @param newPath String  复制后路径  如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {
        try {
            (new File(newPath)).mkdirs();  //如果文件夹不存在  则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            log.error("复制整个文件夹内容操作出错" + e);
        }
    }

    /**
     * 移动整个文件夹内容
     *
     * @param oldFile String  原文件路径  如：c:/fqf
     * @param newFile String  复制后路径  如：f:/fqf/ff
     * @return boolean
     */
    public static void moveFolder(String oldFile, String newFile) {
        copyFolder(oldFile, newFile);
        deleteFile(new File(oldFile));
    }
    public static void main(String[] args) {
//        String oldFile = "C:\\vrspring\\source_img\\vtual";
//        String newFile = "C:\\vrspring\\vrsource\\vtual";
        final String fileName = "05804351160451833.jpg";

//        runbat("C:\\vrspring\\vr_engine\\tae-upload\\upload.bat");
//        moveFolder(oldFile, newFile);
//		String batName = "D:\\VR\\krpano-1.19-pr6-win\\MAKE VTOUR (MULTIRES) droplet.bat";
//		String root = "D:\\VR\\素材\\全景";
//		File rootFile = new File(root);
//		int count = 0;
//		for (File imgFile : rootFile.listFiles())
//		{
//			if (imgFile.getName().endsWith(".jpg"))
//			{
//				count++;
//				System.out
//						.println("正在处理第" + count + "张图片：" + imgFile.getName());
//				String dest = imgFile.getName().substring(0,
//						imgFile.getName().indexOf("."));
//				KrPanoUtil.runbat(batName, imgFile.getAbsolutePath());
//				 log.info("处理完毕第" + count + "张图片："
//						+ imgFile.getName()+"======dest:"+dest);
//				KrPanoUtil.renameToNewFile(root + "\\vtour", root + "\\"
//						+ dest);
//				cleanFiles(root + "\\" + dest);
//			}
//		}
        // test1.renameToNewFile(src, dest);
        // String batName =
        // "D:\\VR\\krpano-1.19-pr6-win\\MAKE VTOUR (MULTIRES) droplet.bat";
        // String param = "D:\\VR\\素材\\全景图片—田—哈尔滨\\单反拍\\2.jpg";
        // test1.runbat(batName,param);
        //  log.info("main thread");
    }

//    public static boolean makeVRSourceFile(String file) {
//        String root = "C:\\vrspring\\";
//        File imgFile = new File(root + "source_img\\" + file);
//        if (imgFile.getName().endsWith(".jpg")) {
//            log.info("正在处理图片：" + imgFile.getName());
//            String dest = imgFile.getName().substring(0,
//                    imgFile.getName().indexOf("."));
//            KrPanoUtil.runbat(batName, imgFile.getAbsolutePath());
//            File fileM = new File(root + "source_img\\vtour");
//            if (fileM.exists()&&fileM.isDirectory()) {
//                log.info("处理完毕图片："
//                        + imgFile.getName() + "======dest:" + dest);
//                KrPanoUtil.renameToNewFile(root + "source_img\\vtour", root + "source_img\\"
//                        + dest);
//                KrPanoUtil.cleanFiles(root + "source_img\\" + dest);
//                KrPanoUtil.moveFolder(root + "source_img\\" + dest, root + "vrsource\\upload\\" + dest);
//            return true;
//            }else{log.error("Image is not PanoFile!!!");
//                return false;
//            }
//        }
//        log.error("file is not exist");
//        return false;
//    }
}
