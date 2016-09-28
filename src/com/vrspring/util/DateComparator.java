package com.vrspring.util;

import java.util.Comparator;

import com.alibaba.media.MediaDir;

public class DateComparator implements Comparator{  
	  
    public int compare(Object o1, Object o2) {  
        if(null!=o1&&null!=o2)  
        {  
            MediaDir menu1=(MediaDir)o1;  
            MediaDir menu2=(MediaDir)o2;  
            if(menu1.getCreateDate().compareTo(menu2.getCreateDate())>0){  
                return 1;  
            }else {  
                return 0;  
            }  
        }  
        return 0;  
    }  
      
}