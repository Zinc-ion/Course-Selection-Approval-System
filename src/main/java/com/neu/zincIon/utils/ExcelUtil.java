package com.neu.zincIon.utils;

import com.neu.zincIon.pojo.Approval;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;


public class ExcelUtil {

    /**
     *  生成Excel表格
     * @param sheetName sheet名称
     * @param titleList 表头列表
     * @param dataList 数据列表
     * @return HSSFWorkbook对象
     * */
    public static HSSFWorkbook createExcel(String sheetName, List<String> titleList,List<Approval> dataList) throws IllegalAccessException {

        //创建HSSFWorkbook对象
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建sheet对象
        HSSFSheet sheet=wb.createSheet(sheetName);
        //在sheet里创建第一行，这里即是表头
        HSSFRow rowTitle=sheet.createRow(0);

        //写入表头的每一个列
        for (int i = 0; i < titleList.size(); i++) {
            //创建单元格
            rowTitle.createCell(i).setCellValue(titleList.get(i));
        }

        //写入每一行的记录
        for (int i = 0; i < dataList.size(); i++) {
            //创建新的一行，递增
            HSSFRow rowData = sheet.createRow(i+1);

//            //通过反射，获取POJO对象
//            Class cl = dataList.get(i).getClass();
//            //获取类的所有字段
//            Field[] fields = cl.getDeclaredFields();


//            for (int j = 0; j < 10; j++) {
////                //设置字段可见，否则会报错，禁止访问
////                fields[j].setAccessible(true);
//                //创建单元格
//                if(j < 6) {
//                    rowData.createCell(j).setCellValue((String) fields[j].get(dataList.get(i)));
//                }
//
//            }

            rowData.createCell(0).setCellValue( dataList.get(i).getSuid());
            rowData.createCell(1).setCellValue( dataList.get(i).getCourseName());
            rowData.createCell(2).setCellValue( dataList.get(i).getCause());
            rowData.createCell(3).setCellValue( dataList.get(i).getProof());
            rowData.createCell(4).setCellValue( dataList.get(i).getRejection_reason());
            rowData.createCell(5).setCellValue( dataList.get(i).getState());
            rowData.createCell(6).setCellValue( Integer.toString(dataList.get(i).getLt()));
            rowData.createCell(7).setCellValue( Integer.toString(dataList.get(i).getSt()));
            rowData.createCell(8).setCellValue( Integer.toString(dataList.get(i).getRjlt()));
            rowData.createCell(9).setCellValue( Integer.toString(dataList.get(i).getRjst()));


        }
        return wb;
    }
}
