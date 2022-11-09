package com.xuexi.poi;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 使用 Apache——poi生成excel文件
 *
 *
 *  文件---------HSSFWorkbook
 * 	 页-----------HSSFSheet
 * 	 行-----------HSSFRow
 *   列-----------HSSFCell
 * 	 样式---------HSSFCellStyle
 */
public class CreateExcelTest {
    public static void main(String[] args) throws Exception {

        //创建HSSFWorkbook对象，对应一个excel的文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //使用workbook创建一个HSSFSheet对象，对应wb文件中的一页
        HSSFSheet sheet = wb.createSheet("学生列表");
        //使用sheet创建HSSFRow对象，对应sheet中的一行
        HSSFRow row = sheet.createRow(0);//行号，从0开始，依次增加
        //使用row创建HSSFCell对象，对应row中的列
        HSSFCell cell = row.createCell(0);//列的编号，从0开始，依次增加
        //当文件创建好了，页页创建好了，行和列都准备好了，就可以往里面写数据了
        cell.setCellValue("学号");
        cell = row.createCell(1);
        cell.setCellValue("姓名");
        cell = row.createCell(2);
        cell.setCellValue("年龄");

        HSSFCellStyle hssfCellStyle = wb.createCellStyle();    //这行代码和下面这一行都是样式的选择
        hssfCellStyle.setAlignment(HorizontalAlignment.CENTER);  //数据       居中  显示

        //使用sheet创建10个HSSFRow对象，对应sheet中的10行
        for (int i = 1;i<=10;i++){
            row = sheet.createRow(i);
            cell = row.createCell(0);
            cell.setCellValue(100+i);
            cell = row.createCell(1);
            cell.setCellValue("姓名"+i);
            cell = row.createCell(2);
            cell.setCellStyle(hssfCellStyle);
            cell.setCellValue(20+i);
        }
        //调用工具函数生成excel文件
        OutputStream outputStream = new FileOutputStream("G:\\CRM客户关系管理系统SSM版\\serverDir\\studentList.xls");//其中，studentList.xls是你的文件的名字，
                                                                                            // 你写在这里，代码会去帮你创建，你在目录里创建好了也没关系，一样的
                                                                                            //但是前面的路径：  G:\CRM客户关系管理系统SSM版\serverDir,这个路径必须创建好

        wb.write(outputStream);

        //关闭资源
        wb.close();
        outputStream.flush();
        outputStream.close();
        System.out.println("=============create OK!============");
    }
}
