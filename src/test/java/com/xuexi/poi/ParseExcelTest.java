package com.xuexi.poi;

import com.xuexi.commons.utils.HSSFUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 使用apache-poi解析excel文件
 */
public class ParseExcelTest {
    public static void main(String[] args) throws Exception {
        //根据指定的excel文件，生成HSSFWorkbook对象，里面封装了所有excel文件的信息
        InputStream inputStream = new FileInputStream("G:\\CRM客户关系管理系统SSM版\\serverDir\\activitys.xls");
        HSSFWorkbook wb = new HSSFWorkbook(inputStream);

        //根据wb获取HSSFWorkbook对象，封装了一页的所有信息
        //一页的名字有可能会重复，所以我们根据下标来取
        HSSFSheet sheet = wb.getSheetAt(0);   //页的额下标，下标从0开始，依次增加

        //根据sheet来获取HSSFRow对象，封装了一行的信息
        HSSFRow row = null;
        HSSFCell cell = null;
        for (int i = 0;i<=sheet.getLastRowNum();i++) {//sheet.getLastRowNum();获取最后一行的下标
             row = sheet.getRow(i);        //通过行的下标获取的，下标从0开始，依次增加

            //根据row对象，来获取HSSFCell对象，封装了一列的所有信息
            for (int s = 0;s<row.getLastCellNum();s++){//row.getLastCellNum():获取最后一行列的下标+1
                 cell = row.getCell(s);     //列的下标，下标从0开始，依次增加

                //获取列中的数据
                System.out.print(HSSFUtils.getCellValueStr(cell)+" ");
                //System.out.println("数据类型是"+cell.getCellType());
            }
            System.out.println("    ");
        }
    }


}
