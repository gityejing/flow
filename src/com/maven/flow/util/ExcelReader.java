package com.maven.flow.util;



import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


/**
 * @author Jimmy Liu
 */
public class ExcelReader {
    private POIFSFileSystem excelFile = null; //file
    private HSSFWorkbook wb = null; //book [includes sheet]
    private HSSFSheet sheet = null;
    private HSSFRow row = null;
    private HSSFCell cell = null; //cell,the content which contains
    private int sheetNum = 0; //第sheetnum个工作表
    private int rowNum = 0;
    private FileInputStream fis = null;
    private String fileName = "";
    private List list = new ArrayList();
    private List readlist = new ArrayList();
    private String header = "";
    private Object[] fieldType = null;
    private int index = 0;
    public ExcelReader() {

    }

    public List getReadlist() {
        return readlist;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getHeader() {
        return header;
    }

    public String[] getHeaderArray() {
        if (header == null) {
            return new String[0];
        }
        return header.split(",");
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public ExcelReader(String fileName) {
        openFile(fileName);
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public void setSheetNum(int sheetNum) {
        this.sheetNum = sheetNum;
    }

    /**
     * 读取excel文件获得HSSFWorkbook对象
     * @param fileName
     */
    public void openFile(String fileName) {
        this.fileName = fileName;
        File f = new File(fileName);
        if (f.exists()) {
            try {
                fis = new FileInputStream(f);
                excelFile = new POIFSFileSystem(fis);
                wb = new HSSFWorkbook(excelFile);
                fis.close();
                if (null == wb) {
                    log("WorkBook为空");
                }
            } catch (Exception e) {
                log("open the file throw exception!");
                //e.printStackTrace();
            }
        } else {
            log("excel File  not exist");
        }
    }

    /**
     * 返回sheet表数目
     * @return int
     */
    public int getSheetCount() {
        int sheetCount = -1;
        sheetCount = wb.getNumberOfSheets();
        return sheetCount;
    }

    /**
     * sheetnum下的记录行数
     * @return int
     */
    public int getRowCount() {
        if (null == wb) {
            log("WorkBook为空");
        }
        HSSFSheet sheet = wb.getSheetAt(this.sheetNum);
        int rowCount = -1;
        rowCount = sheet.getLastRowNum();
        return rowCount;
    }

    /**
     * 读取指定sheetNum的rowCount
     * @param sheetNum
     * @return int
     */
    public int getRowCount(int sheetNum) {
        HSSFSheet sheet = wb.getSheetAt(sheetNum);
        int rowCount = -1;
        rowCount = sheet.getLastRowNum();
        return rowCount;
    }

    /**
     * 得到指定行的内容
     * @param lineNum
     * @return String[]
     */
    public String[] readExcelLine(int lineNum) {
        return readExcelLine(this.sheetNum, lineNum);
    }

    /**
     * 指定工作表和行数的内容,读取一行的内容,返回一行各列的内容
     * @param sheetNum
     * @param lineNum
     * @return String[]
     */
    public String[] readExcelLine(int sheetNum, int lineNum) {
        if (sheetNum < 0 || lineNum < 0) {
            return null;
        }
        String[] strExcelLine = null;
        try {
            sheet = wb.getSheetAt(sheetNum);
            row = sheet.getRow(lineNum);
            if (row == null) {
                return null;
            }
            int cellCount = row.getLastCellNum();

            strExcelLine = new String[cellCount];
            for (int i = 0; i < cellCount; i++) {
                strExcelLine[i] = "";
                if (null != row.getCell((short) i)) {
                    switch (row.getCell((short) i).getCellType()) {
                    case HSSFCell.CELL_TYPE_FORMULA:
                        strExcelLine[i] = "FORMULA ";
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        strExcelLine[i] = String.valueOf(row.getCell((short) i).
                                getNumericCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_STRING:
                        strExcelLine[i] = row.getCell((short) i).
                                          getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_BLANK:
                        strExcelLine[i] = "";
                        break;
                    default:
                        strExcelLine[i] = "";
                        break;
                    }
                    //如果读取的是科学计数法的格式，则转换为普通格式
                    //added by wangtongjun at 20060626
                    if (null != strExcelLine[i] &&
                        strExcelLine[i].indexOf(".") != -1 &&
                        strExcelLine[i].indexOf("E") != -1) {
                        DecimalFormat df = new DecimalFormat();
                        strExcelLine[i] = df.parse(strExcelLine[i]).toString();
                    }
                    //如果读取的是数字格式，并且以".0"结尾格式，则转换为普通格式
                    //added by wangtongjun at 20060713
                    if (null != strExcelLine &&
                        strExcelLine[i].endsWith(".0")) {
                        int size = strExcelLine[i].length();
                        strExcelLine[i] = strExcelLine[i].substring(0, size - 2);
                    }
                }
            }

        } catch (Exception e) {
            log("read excelLine throw exception!");
        }
        return strExcelLine;
    }

    /**
     * 读取指定列的内容
     * @param cellNum
     * @return String
     */
    public String readStringExcelCell(int cellNum) {
        return readStringExcelCell(this.rowNum, cellNum);
    }

    public void logln(String messgae) {
        //System.out.println(messgae);
    }

    public void log(String messgae) {
        System.out.print(messgae);
    }

    /**
     * 指定行和列编号的内容
     * @param rowNum
     * @param cellNum
     * @return String
     */
    public String readStringExcelCell(int rowNum, int cellNum) {
        return readStringExcelCell(this.sheetNum, rowNum, cellNum);
    }

    /**
     * 指定工作表、行、列下的内容
     * @param sheetNum
     * @param rowNum
     * @param cellNum
     * @return String
     */
    public String readStringExcelCell(int sheetNum, int rowNum, int cellNum) {
        String strExcelCell = "";
        if (sheetNum < 0 || rowNum < 0) {
            return null;
        }

        try {
            sheet = wb.getSheetAt(sheetNum);
            row = sheet.getRow(rowNum);
            /**
             * modified by wangtongjun
             * bug description:
             * if the cell is empty ,the behind data can't get
             * for null point exception deal
             */
            if (null != row.getCell((short) cellNum)) { //add this condition judge
                switch (row.getCell((short) cellNum).getCellType()) {
                case HSSFCell.CELL_TYPE_FORMULA:
                    strExcelCell = "FORMULA ";
                    break;

                case HSSFCell.CELL_TYPE_NUMERIC: {
                    strExcelCell = String.valueOf(row.getCell((short) cellNum).
                                                  getNumericCellValue());
                }
                break;

                case HSSFCell.CELL_TYPE_STRING:
                    strExcelCell = row.getCell((short) cellNum).
                                   getStringCellValue();
                    break;
                default:
                }
                //如果读取的是科学计数法的格式，则转换为普通格式
                //added by wangtongjun at 20060626
                if (null != strExcelCell &&
                    strExcelCell.indexOf(".") != -1 &&
                    strExcelCell.indexOf("E") != -1) {
                    DecimalFormat df = new DecimalFormat();
                    strExcelCell = df.parse(strExcelCell).toString();
                }

                //如果读取的是数字格式，并且以".0"结尾格式，则转换为普通格式
                //added by wangtongjun at 20060713
                if (null != strExcelCell &&
                    strExcelCell.endsWith(".0")) {
                    int size = strExcelCell.length();
                    strExcelCell = strExcelCell.substring(0, size - 2);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strExcelCell;
    }

    public void writeExcelLine(String fileName, String[] strLine) {
        try {
            File f = new File(fileName + ".xls");
            if (!f.exists()) {
                f.createNewFile();
            }
            if (f.isFile()) {
                FileOutputStream fileOut = new FileOutputStream(f);
                sheet = wb.createSheet("Sheet1");
                row = sheet.createRow(0);
                int cellNum = strLine.length;
                for (int i = 0; i < cellNum; i++) {
                    row.createCell((short) i).setCellValue(strLine[i]);
                }
                wb.write(fileOut);
                fileOut.close();
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public void writeExcelLine(String fileName, String[] strLine, int iRownum) {
        try {
            File f = new File(fileName + ".xls");
            if (f.isFile()) {
                FileOutputStream fileOut = new FileOutputStream(f);
                sheet = wb.getSheet("Sheet1");
                if (null == sheet) {
                    sheet = wb.createSheet("Sheet1");
                }
                row = sheet.createRow(iRownum);
                int cellNum = strLine.length;
                for (int i = 0; i < cellNum; i++) {
                    HSSFCell cell = row.createCell((short) i);
                    cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    cell.setCellValue(strLine[i]);
                }
                wb.write(fileOut);
                fileOut.close();
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public void writeExcelCell(int sheetNum, int rowNum, int cellNum,
                               String strCell) {
        sheet = wb.getSheetAt(sheetNum);
        row = sheet.getRow(rowNum);
        cell = row.getCell((short) cellNum);
        cell.setCellValue(strCell);
        try {
            File f = new File(fileName);
            if (f.isFile()) {
                FileOutputStream fileOut = new FileOutputStream(f);
                sheet = wb.createSheet("Sheet1");
                row = sheet.createRow(1);
                //int cellNum=strLine.length;
                for (int i = 0; i < 10; i++) {
                    //row.createCell((short)i).setCellValue(strLine[i]);
                }
                wb.write(fileOut);
                fileOut.close();
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public void writeWxcel(String fileName, String toFileName) {

    }

    public String[] logExcel(String fileName) {
        openFile(fileName);
        setSheetNum(0); //设置读取索引为0的工作表
        //总行数
        int count = getRowCount();
        String[] strExcelLine = null;
        System.out.println(readStringExcelCell(1, 5));
        for (int i = 0; i <= count; i++) {
            String[] rows = readExcelLine(i);
            if (rows != null) {
                for (int j = 0; j < rows.length; j++) {
                    strExcelLine = readExcelLine(j);
                    System.out.print(j + " " + rows[j] + "  ");
                }
                System.out.print("\n");
            }
        }
        System.out.println(fileName + "-----------------已读取完毕!");
        return strExcelLine;
    }

    public String[] readExcel(String fileName, int blindex, int elindex) {
        openFile(fileName);
        setSheetNum(0); //设置读取索引为0的工作表
        int count = getRowCount(); //总行数
        if (blindex == -1) {
            blindex = 0;
        }
        if (elindex == -1) {
            elindex = count;
        }

        String[] strExcelLine = null;
        for (int i = 0; i <= count; i++) {

            if (blindex < i && i < elindex) {
                String[] rows = readExcelLine(i);
                if (rows != null) {
                    for (int j = 0; j < rows.length; j++) {
                        strExcelLine = readExcelLine(j);
                    }
                }
            }
            //readlist.add(strExcelLine);
        }
        return strExcelLine;
    }

    public String[] readTemp(String tempfile) {
        return readTempSpec(tempfile, -1, -1);
    }

    public String[] readTempSpec(String tempfile, int blindex, int elindex) {
        return readExcel(tempfile, blindex, elindex);
    }

    public List readToList(String fileName) {
        openFile(fileName);
        setSheetNum(0); //设置读取索引为0的工作表
        int count = getRowCount(); //总行
        readlist = new ArrayList();
        for (int i = 0; i <= count; i++) {
            String[] rows = readExcelLine(i);
            readlist.add(rows);
        }
        return readlist;

    }

    public Map beaToMap(Object bean) throws Exception {
        Map beaToMaps = new HashMap();
        PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(
                bean);
        beaToMaps.put("Class", bean.getClass().getName());
        for (int i = 0; i < descriptors.length; i++) {
            PropertyDescriptor des = descriptors[i];
            Object proValue = PropertyUtils.getSimpleProperty(bean, des.getName());
            if (ToStringUtil.isSimpleType(des.getPropertyType()) &&
                !des.equals("Class")) {
                beaToMaps.put(des.getName().toLowerCase(),
                              String.valueOf(proValue));
            }
        }
        return beaToMaps;
    }

    public void beaToList(String header) throws Exception {
        this.header = header;
        this.readlist = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Object bea = list.get(i);
            readlist.add(mapToArray(beaToMap(bea), null));
        }
    }

    public List beaToList(List list, String header) throws Exception {
        this.list = list;
        this.header = header;
        this.readlist = new ArrayList();
        this.index=0;
        for (int i = 0; i < list.size(); i++) {
            Object bea = list.get(i);
            if (bea != null) {
                if (bea instanceof String[]) {
                    readlist.add(bea);
                } else {
                    bea = beaToArray(bea, null); //mapToArray(beaToMap(bea), null);
                    readlist.add(bea);
                }
            } else {
                readlist.add(bea);
            }
        }
        return readlist;
    }

    //beaToArray
    public Object[] beaToArray(Object bean, String[] h) throws Exception {
        if (h == null) {
            h = getHeaderArray();
        }
        int cellCount = h.length;

        PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(
                bean);
        if (cellCount == 1) {
            cellCount = descriptors.length;
        }
        StringBuffer sb = new StringBuffer();
        StringBuffer sbtype = new StringBuffer();
        Object[] beaArray = new Object[cellCount];
        if (h.length <= 1) {
            fieldType = new Object[descriptors.length];
            for (int i = 0; i < descriptors.length; i++) {
                PropertyDescriptor des = descriptors[i];
                Object proValue = PropertyUtils.getSimpleProperty(bean,
                        des.getName());
                if (ToStringUtil.isSimpleType(des.getPropertyType()) &&
                    !des.equals("Class")) {
                    if (proValue == null) {
                        beaArray[i] = "";
                    } else {
                        beaArray[i] = proValue;
                    }
                    sb.append(des.getName());
                    sb.append(",");
                    fieldType[i] = des.getPropertyType();
                }
            }
            this.header = sb.toString();

        } else {
            for (int i = 0; i < cellCount; i++) {
                //getBeanPropertyDes( h[i],bean);
                Object proValue = getBeanPropertyValue(h[i], bean);
                if (proValue == null) {
                    if (h[i].equalsIgnoreCase("NO")) {
                        this.index++;
                        beaArray[i] = new Integer(this.index);
                    } else {
                        beaArray[i] = "";
                    }
                } else {
                    beaArray[i] = proValue;
                }
            }
        }
        return beaArray;
    }

    protected Object getBeanPropertyValue(String name, Object bean) throws
            Exception {
        PropertyDescriptor[] des = PropertyUtils.getPropertyDescriptors(bean);
        for (int i = 0; i < des.length; i++) {
            if (des[i].getName().equalsIgnoreCase(name)) {
                return PropertyUtils.getSimpleProperty(bean, des[i].getName());
            }
        }
        return null;
    }

    protected PropertyDescriptor getBeanPropertyDes(String name, Object bean) throws
            Exception {
        PropertyDescriptor[] des = PropertyUtils.getPropertyDescriptors(bean);
        for (int i = 0; i < des.length; i++) {
            if (des[i].getName().equalsIgnoreCase(name)) {
                return des[i];
            }
        }
        return null;
    }

//    public String[] beaToArray(Object bean, String[] h) {
//        if(h==null)h=getHeaderArray();
//        int cellCount = h.length;
//        //if(cellCount==0)cellCount=map.size();
//        String[] mapArray = new String[cellCount];
//        if(h.length==0){
////            if (map != null) {
////                        int index = 0;
////                        for (Iterator it = map.keySet().iterator(); it.hasNext(); ) {
////                            Object key = it.next();
////                            mapArray[index]=String.valueOf(map.get(key));
////                            index++;
////                        }
////            }
//        }else{
//            for (int i = 0; i < cellCount; i++) {
//            // mapArray[i] = (String) map.get(h[i].toLowerCase());
//            }
//        }
//
//        return mapArray;
//    }

    //map to Array
    public String[] mapToArray(Map map, String[] h) {
        if (h == null) {
            h = getHeaderArray();
        }
        int cellCount = h.length;
        if (cellCount == 0) {
            cellCount = map.size();
        }
        String[] mapArray = new String[cellCount];
        if (h.length == 0) {
            if (map != null) {
                int index = 0;
                for (Iterator it = map.keySet().iterator(); it.hasNext(); ) {
                    Object key = it.next();
                    mapArray[index] = String.valueOf(map.get(key));
                    index++;
                }
            }
        } else {
            for (int i = 0; i < cellCount; i++) {
                mapArray[i] = (String) map.get(h[i].toLowerCase());
            }
        }

        return mapArray;
    }

    //public String[] beaToArray(Object bean){
    //    String[] beaToArray=
    //}
    public String writeToByList(String fileName, List list, String header) throws
            Exception {
        //beaToList(list,header);

        return writeToByList(fileName, beaToList(list, header));
        //return  writeByList(fileName);
    }

    public void writeExcelLine(String fileName, List list) {
        try {

            InputStream TemplateFile = this.getClass().getResourceAsStream(
                    "/template/projectBase.xls"); //加载项目的模板.

            POIFSFileSystem fs = new POIFSFileSystem(TemplateFile);
            HSSFWorkbook wb = new HSSFWorkbook(fs);

            File f = new File(fileName + ".xls");
            if (!f.exists()) {
                f.createNewFile();
            }
            if (f.isFile()) {
                FileOutputStream fileOut = new FileOutputStream(f);
                HSSFSheet sheet = wb.getSheetAt(0);
                //sheet = wb.createSheet("Sheet1");
                row = sheet.createRow(0);
                for (int j = 0; j < list.size(); j++) {
                    String[] strLine = (String[]) list.get(j);
                    if (strLine != null) {
                        int cellNum = strLine.length;
                        for (int i = 0; i < cellNum; i++) {
                            log(strLine[i]);
                            row.createCell((short) i).setCellValue(strLine[i]);
                        }
                    }
                }
                wb.write(fileOut);
                fileOut.close();
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public String writeByList(String fileName) {
        try {
            InputStream TemplateFile = this.getClass().getResourceAsStream(
                    "/template/projectBase.xls"); //加载项目的模板.

            POIFSFileSystem fs = new POIFSFileSystem(TemplateFile);
            HSSFWorkbook wb = new HSSFWorkbook(fs);

            File f = new File(fileName + ".xls");
            if (f.isFile()) {
                FileOutputStream fileOut = new FileOutputStream(f);
                sheet = wb.getSheet("Sheet1");
                if (sheet == null) {
                    sheet = wb.getSheetAt(0);
                    //sheet = wb.createSheet("Sheet1");
                }
                row = sheet.createRow(0);
                for (int j = 0; j < readlist.size(); j++) {
                    String[] strLine = (String[]) readlist.get(j);
                    if (strLine != null) {
                        int cellNum = strLine.length;
                        for (int i = 0; i < cellNum; i++) {
                            HSSFCell cell = row.createCell((short) i);
                            cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                            cell.setCellValue(strLine[i]);

                            //row.createCell((short) i).setCellValue(strLine[i]);
                        }
                    }
                }
                wb.write(fileOut);
                fileOut.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;

        // return this.writeToByList(fileName,getReadlist());
    }

    public void loadTemplate(String template, int startRow, List data,String fileName) throws
            Exception {
        try {
            //1.load the template file
            InputStream TemplateFile = this.getClass().getResourceAsStream(
                    "/template/" + template); //加载项目的模板.
            POIFSFileSystem fs = new POIFSFileSystem(TemplateFile);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            sheet = wb.getSheetAt(0);
            //2.open the first sheet
            if (sheet == null) {
                sheet = wb.createSheet("Sheet1");
            }
            //fill the data
            if (data != null) {
                this.readlist = data;
            }
            if (startRow < 0) {
                startRow = 0;
            }
            HSSFCellStyle cellstyle=getCellstyle(null,8,1);
            for (int j = 0; j < readlist.size(); j++) {
                Object[] strLine = (Object[]) readlist.get(j);
                row = sheet.createRow(j + startRow);
                //if it is null then continue
                if (strLine == null) {
                    continue;
                }
                for (int i = 0; i < strLine.length; i++) {
                    HSSFCell cell = row.createCell((short) i);
                    cell.setCellStyle(cellstyle);
                    if (strLine[i] == null) {
                        strLine[i] = "";
                    }

                    Object Value = strLine[i];
                    if (Value instanceof Long || Value.getClass().equals(long.class)) {
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Double.parseDouble(String.
                                valueOf(strLine[i])));
                    } else if (Value instanceof Double ||
                               Value.getClass().equals(double.class)) {
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Double.parseDouble(String.
                                valueOf(strLine[i])));
                    } else if (Value instanceof Integer ||
                               Value.getClass().equals(int.class)) {
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Double.parseDouble(String.
                                valueOf(strLine[i])));
                    } else if (Value instanceof Float ||
                               Value.getClass().equals(float.class)) {
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Double.parseDouble(String.
                                valueOf(strLine[i])));
                    } else {
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(strLine[i] == null ? "" :
                                          String.valueOf(strLine[i]));
                    }
                    cell.setEncoding(HSSFCell.ENCODING_UTF_16);


                }
            }

            File f = new File(fileName);
            if (!f.exists()) {
                f.createNewFile();
            }
            if (f.isFile()) {
                FileOutputStream fileOut = new FileOutputStream(f);
                wb.write(fileOut);
                fileOut.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public HSSFCellStyle getCellstyle(HSSFSheet sheet1, int irow, int icol) throws
            Exception {

        if(sheet1==null){
            InputStream TemplateFile = this.getClass().getResourceAsStream(
                    "/template/template.xls"); //加载项目的模板.

            POIFSFileSystem fs = new POIFSFileSystem(TemplateFile);
            HSSFWorkbook wb2 = new HSSFWorkbook(fs);
            sheet1 = wb2.getSheetAt(0);
        }
        HSSFRow row1 = null;
        HSSFCell cell1 = null;
        HSSFCellStyle cellstyle = null;
        if (sheet1 != null) {
            if (irow == -1) {
                irow = 4;
            }
            if (icol == -1) {
                icol = 1;
            }

            row1 = sheet1.getRow(irow);
            if(row1!=null){
                cell1 = row1.getCell((short) icol);
                cellstyle = cell1.getCellStyle();
            }
        }
        return cellstyle;
    }

    public String writeToByList(String fileName, List readlist) {
        try {
            InputStream TemplateFile = this.getClass().getResourceAsStream(
                    "/template/projectBase.xls"); //加载项目的模板.

            POIFSFileSystem fs = new POIFSFileSystem(TemplateFile);
            HSSFWorkbook wb = new HSSFWorkbook(fs);

            if (fileName.indexOf(".xls") == -1) {
                fileName = fileName + ".xls";
            }
            //fileName=fileName+".xls";
            File f = new File(fileName);
            if (!f.exists()) {
                f.createNewFile();
            } else {
                openFile(fileName);
            }
            if (f.isFile()) {
                FileOutputStream fileOut = new FileOutputStream(f);
                sheet = wb.getSheetAt(0);
                if (sheet == null) {
                    sheet = wb.createSheet("Sheet1");
                }
                for (int j = 0; j < readlist.size(); j++) {
                    Object[] strLine = (Object[]) readlist.get(j);
                    row = sheet.createRow(j + 4);
                    if (strLine != null) {
                        int cellNum = strLine.length;
                        for (int i = 0; i < cellNum; i++) {
                            HSSFCell cell = row.createCell((short) i);

                            if (strLine[i] == null) {
                                strLine[i] = "";
                            }

                            Object Value = strLine[i];
                            if (Value instanceof Long ||
                                Value.getClass().equals(long.class)) {
                                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                cell.setCellValue(Double.parseDouble(String.
                                        valueOf(strLine[i])));
                            } else if (Value instanceof Double ||
                                       Value.getClass().equals(double.class)) {
                                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                cell.setCellValue(Double.parseDouble(String.
                                        valueOf(strLine[i])));
                            } else if (Value instanceof Integer ||
                                       Value.getClass().equals(int.class)) {
                                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                cell.setCellValue(Double.parseDouble(String.
                                        valueOf(strLine[i])));
                            } else if (Value instanceof Float ||
                                       Value.getClass().equals(float.class)) {
                                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                cell.setCellValue(Double.parseDouble(String.
                                        valueOf(strLine[i])));
                            } else {
                                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                cell.setCellValue(strLine[i] == null ? "" :
                                                  String.valueOf(strLine[i]));
                            }
                            cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                            //row.createCell((short) i).setCellValue(strLine[i].equals("null")? "" :String.valueOf(strLine[i]));
                        }
                    }
                }
                wb.write(fileOut);
                fileOut.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    //读取模板
    //readTemp(String tempfile) readTempSpec(String tempfile,int blindex,int elindex)
    //读取数据
    //getList
    //填充数据
    //filldata
    //输出文件
    //outfile

    public static void main(String args[]) throws Exception {
        ExcelReader reader = new ExcelReader();
        /*
                 reader.logExcel("C:\\all.xls");
                 reader.logExcel("C:\\公司台账\\2006年建成台帐(司法鉴定类).xls");
                 reader.logExcel("C:\\公司台账\\2006年建成台帐(招标代理类).xls");

                 reader.openFile("C:\\公司台账\\2007年建成台帐(造价咨询类).xls");
                 reader.readExcelLine(4);

                 reader.readTemp("C:\\公司台账\\2007年建成台帐(造价咨询类).xls");
         */
        reader.writeToByList("C:\\公司台账\\2007年建成台帐(造价咨询类)[3]",
                             reader.readToList("C:\\公司台账\\2007年建成台帐(造价咨询类).xls"),
                             "");
        //reader.writeToByList("C:\\公司台账\\2007年建成台帐(造价咨询类)2",
        //                      reader.readToList("C:\\公司台账\\2007年建成台帐(造价咨询类).xls"));
        // reader.writeExcelLine("C:\\公司台账\\testexcl2",reader.logExcel("C:\\公司台账\\2007年建成台帐(造价咨询类).xls"));

    }

}
