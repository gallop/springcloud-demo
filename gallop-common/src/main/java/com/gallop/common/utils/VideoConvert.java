package com.gallop.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: gallop
 * E-Mail: 39100782@qq.com
 * Description:
 * Date: Create in 7:14 2019/7/12
 * Modified By:
 */
public class VideoConvert {
    //视频文件在服务器的存储路径
    private String storagePath;
    // 视频路径
    private String ffmpegEXE;

    public VideoConvert(String storagePath,String ffmpegEXE) {
        this.storagePath = storagePath;
        this.ffmpegEXE = ffmpegEXE;
    }

    /**
     *
     * @param videoInputPath
     * @param intervalSeconds 间隔秒数，每几秒切成一个.ts文件
     */
    public String convertToM3u8(String videoInputPath, double intervalSeconds) throws Exception{

        String fileName = videoInputPath.substring(videoInputPath.lastIndexOf("/")+1);
        String arrayFilenameItem[] =  fileName.split("\\.");
        if(arrayFilenameItem.length >1){
            String key = arrayFilenameItem[0];
            String type = arrayFilenameItem[1];
            String newDirectoryName = storagePath+"/"+key;
            //先创建一个目录并进入，用于存储视频切分后的ts 文件及.m3u8文件，即一个视频对于一个文件夹
            List<String> command = new ArrayList<>();
            command.add("mkdir");
            command.add("-m");
            command.add("765");//必须赋予执行此程序的用户可执行权限
            command.add(newDirectoryName);
            //command.add(";");
            //command.add("cd");
            //command.add(storagePath);

            this.executeCmd(command);

            //用ffmpeg将视频转换成.ts格式的文件
            command = new ArrayList<>();
            command.add(ffmpegEXE);

            command.add("-y");
            command.add("-i");
            command.add(storagePath+"/"+fileName);
            System.out.println("==========fileName:"+storagePath+"/"+fileName);
            command.add("-vcodec");
            command.add("copy");
            command.add("-acodec");
            command.add("copy");
            command.add("-vbsf");
            command.add("h264_mp4toannexb");
            command.add(newDirectoryName+"/"+key+".ts");
            System.out.println("============newFileName:"+newDirectoryName+"/"+key+".ts");
            /*for (String c : command) {
                System.out.print(c + " ");
            }*/
            this.executeCmd(command);



            //将ts文件切片并生成key.m3u8文件
            command = new ArrayList<>();
            command.add(ffmpegEXE);
            command.add("-i");
            command.add(newDirectoryName+"/"+key+".ts");
            command.add("-c");
            command.add("copy");
            command.add("-map");
            command.add("0");
            command.add("-f");
            command.add("segment");
            command.add("-segment_list");
            command.add(newDirectoryName+"/"+key+".m3u8");
            command.add("-segment_time");
            command.add(String.valueOf(intervalSeconds));
            command.add(newDirectoryName+"/"+key+"%03d.ts");
            this.executeCmd(command);

            //删除生成的ts文件
            command = new ArrayList<>();
            command.add("rm");
            command.add("-f");
            command.add(newDirectoryName+"/"+key+".ts");
            this.executeCmd(command);

            //给切片生成的图片赋予其他用户可读权限
            command = new ArrayList<>();
            command.add("chmod");
            command.add("765");
            command.add("-R");
            command.add(newDirectoryName+"/");
            this.executeCmd(command);

            return key+"/"+key+".m3u8";
        }

        return null;
    }

    private void executeCmd(List<String> command) throws IOException, InterruptedException{
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();

        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String line = "";
        while ( (line = br.readLine()) != null ) {
            System.out.println(line);
        }


        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }

    public static void main(String[] args) {
        String videoPath= "http://media.mygallop.cn/bigears/storage/c9683b70-44ed-456f-9e72-659c5dbc02fc.mp4";
        String fileName = videoPath.substring(videoPath.lastIndexOf("/")+1);
        System.out.println("fileName="+fileName);
        String arrayFilenameItem[] =  fileName.split("\\.");
        String key = arrayFilenameItem[0];
        String type = arrayFilenameItem[1];
        System.out.println("key="+key);
        System.out.println("type="+type);
    }
}
