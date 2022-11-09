package com.xuexi.commons.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class HSSFUtils {
    /**
     * 关于excel文件的工具类
     * 从指定的Cell对象中来获取列的值
     * @return
     */
    public static String getCellValueStr(HSSFCell cell){

        String result = "";
        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
            result = cell.getStringCellValue();
        }else  if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            result = cell.getNumericCellValue()+"";
        }else  if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
            result = cell.getBooleanCellValue()+"";
        }else  if (cell.getCellType() == HSSFCell.CELL_TYPE_ERROR) {
            result = cell.getErrorCellValue()+"";
        }else  if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
            result = cell.getCellFormula();
        }else {
            System.out.print("   "+"  ");
        }
        return  result;
    }
}
