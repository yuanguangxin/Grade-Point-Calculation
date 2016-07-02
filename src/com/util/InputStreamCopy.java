package com.util;

import java.io.*;

public class InputStreamCopy {
    private FileInputStream fis=null;
    private FileOutputStream fos=null;
    public InputStreamCopy(String oldPath,String newPath){
        try {
            File ff = new File(newPath);
            fis=new FileInputStream(oldPath);
            File file1=new File(oldPath);
            String name=file1.getName();
            byte []bytes=new byte[1024];
            int n=0;
            File file = new File(new File(newPath),name);
            if(!file.exists()) {
                file.getParentFile().mkdirs();
            }
            fos=new FileOutputStream(newPath+"/"+name);
            while((n=fis.read(bytes))!=-1){
                fos.write(bytes,0,n);
                //	String s=new String(bytes,0,n);
                //	fos.write(s.getBytes());
            }
            fis.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

