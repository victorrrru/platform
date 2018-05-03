package com.fww.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Administrator
 * @date 2018/04/29 19:41
 */
public class ExcelUtil {
    public ExcelUtil() {
    }

    private static void exportExcelBySheet(ExcelUtil.ExportMonitor monitor, int[] count, HSSFWorkbook wb, String sheetName, HSSFCellStyle headStyle, HSSFCellStyle titleStyle, HSSFCellStyle dataStyle, String head, String[] fields, String[] titles, List<?> dataList) {
        try {
            if(monitor != null) {
                monitor.doProgress(count[0], (Exception)null);
            }

            HSSFFont font = wb.getFontAt((short) 0);
            (new StringBuilder()).append(font.getFontName()).append(",").append(font.getFontHeightInPoints()).toString();
            HSSFSheet sheet = wb.createSheet(sheetName);
            int startRowIndex = 0;
            HSSFRow titleRow;
            if(StringUtils.isNotEmpty(head)) {
                titleRow = sheet.createRow(startRowIndex);
                sheet.addMergedRegion(new CellRangeAddress(startRowIndex, startRowIndex, 0, fields.length - 1));
                HSSFCell cell1 = titleRow.createCell(0, 1);
                cell1.setCellStyle(headStyle);
                cell1.setCellValue(head);
                ++startRowIndex;
            }

            titleRow = sheet.createRow(startRowIndex);

            int i;
            for(i = 0; i < fields.length; ++i) {
                String field = fields[i];
                String title = titles[i];
                if(title == null) {
                    title = field;
                }

                HSSFCell cell = titleRow.createCell((short)i, 1);
                cell.setCellStyle(titleStyle);
                cell.setCellValue(title);
            }

            Iterator var27 = dataList.iterator();

            label90:
            while(true) {
                Object data;
                Map map;
                do {
                    do {
                        if(!var27.hasNext()) {
                            for(i = 0; i < titles.length; ++i) {
                                if(monitor != null) {
                                    monitor.doProgress(count[0], (Exception)null);
                                }

                                sheet.autoSizeColumn(i);
                            }
                            break label90;
                        }

                        data = var27.next();
                    } while(data == null);

                    if(!(data instanceof Map)) {
                        break;
                    }

                    map = (Map)data;
                } while(map.isEmpty());

                ++count[0];
                if(monitor != null) {
                    monitor.doProgress(count[0], (Exception)null);
                }

                ++startRowIndex;
                HSSFRow row = sheet.createRow(startRowIndex);

                for(i = 0; i < titles.length; ++i) {
                    String field = fields[i];

                    try {
                        Object value = PropertyUtils.getProperty(data, field);
                        if(value == null) {
                            value = "";
                        }

                        short type = 1;
                        HSSFCell cell = row.createCell((short)i, type);
                        if(value instanceof BigDecimal) {
                            dataStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
                            cell.setCellStyle(dataStyle);
                            cell.setCellValue(((BigDecimal)value).doubleValue());
                        } else {
                            cell.setCellStyle(dataStyle);
                            cell.setCellValue(value.toString().trim());
                        }
                    } catch (Exception var24) {
                        var24.printStackTrace();
                    }
                }
            }
        } catch (Exception var25) {
            if(monitor != null) {
                monitor.doProgress(count[0], var25);
            }

            var25.printStackTrace();
        }

    }

    public static synchronized List<File> exportExcel(ExcelUtil.ExportMonitor monitor, String head, String[] fields, String[] titles, List<?> dataList, File saveDir) {
        if(saveDir == null) {
            String tempDir = System.getProperty("user.dir");
            saveDir = new File(tempDir, "temp");
        }

        if(!saveDir.exists()) {
            saveDir.mkdirs();
        }

        if(monitor != null) {
            monitor.before(dataList.size());
        }

        int[] exportCount = new int[1];
        List<File> xlsFileList = new ArrayList();
        int maxSize = '\uea60';
        int xlsCount = dataList.size() / maxSize;
        if(dataList.size() % maxSize >= 0) {
            ++xlsCount;
        }

        try {
            DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String now = format.format(new Date());

            for(int i = 0; i < xlsCount; ++i) {
                int from = i * maxSize;
                int to = Math.min(from + maxSize, dataList.size());
                List list = new ArrayList(dataList.subList(from, to));
                String fileName = now + "_" + (i + 1) + ".xls";
                if(xlsCount == 1) {
                    fileName = now + ".xls";
                }

                File file = new File(saveDir, fileName);
                FileOutputStream fos = new FileOutputStream(file);
                export2Excel(exportCount, monitor, fos, head, fields, titles, list);
                xlsFileList.add(file);
            }

            if(monitor != null) {
                monitor.finish(dataList.size(), exportCount[0], (Exception)null);
            }
        } catch (Exception var22) {
            var22.printStackTrace();
            if(monitor != null) {
                monitor.finish(dataList.size(), exportCount[0], var22);
            }
        } finally {
            dataList.clear();
        }

        return xlsFileList;
    }

    public static synchronized File mergeExportExcel(ExcelUtil.ExportMonitor monitor, String head, List<String[]> fields, List<String[]> titles, Map<String, List<?>> list, File saveDir, String[] sheetNames) {
        if(saveDir == null) {
            String tempDir = System.getProperty("user.dir");
            saveDir = new File(tempDir, "temp");
        }

        if(!saveDir.exists()) {
            saveDir.mkdirs();
        }

        if(monitor != null) {
            monitor.before(list.size());
        }

        int[] exportCount = new int[1];
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String now = format.format(new Date());
        String fileName = now + ".xls";
        File file = new File(saveDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            mergeExport2Excel(exportCount, monitor, fos, head, fields, titles, list, sheetNames);
            if(monitor != null) {
                monitor.finish(list.size(), 1, (Exception)null);
            }
        } catch (Exception var16) {
            var16.printStackTrace();
            if(monitor != null) {
                monitor.finish(list.size(), 1, var16);
            }
        } finally {
            list.clear();
        }

        return file;
    }

    private static synchronized void export2Excel(int[] exportCount, ExcelUtil.ExportMonitor monitor, OutputStream excelOut, String head, String[] fields, String[] titles, List<?> list) {
        if(titles != null && titles.length != 0 && fields != null && fields.length != 0 && list != null) {
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFCellStyle headStyle = createHeadStyle(wb);
            HSSFCellStyle titleStyle = createTitleStyle(wb);
            Font titleFont = wb.createFont();
            titleFont.setFontName("微软雅黑");
            titleFont.setBoldweight((short) 700);
            titleFont.setFontHeightInPoints((short) 10);
            titleStyle.setFont(titleFont);
            HSSFCellStyle dataStyle = createDataStyle(wb);
            HSSFDataFormat format = wb.createDataFormat();
            short textFormat = format.getFormat("@");
            dataStyle.setDataFormat(textFormat);
            Font dataFont = wb.createFont();
            dataFont.setFontName("微软雅黑");
            dataFont.setFontHeightInPoints((short) 10);
            dataStyle.setFont(dataFont);
            int maxSize = '\ufde8';
            int sheetCount = list.size() / maxSize;
            if(list.size() % maxSize > 0) {
                ++sheetCount;
            }

            sheetCount = sheetCount == 0?1:sheetCount;
            int totalCount = list.size();

            for(int i = 0; i < sheetCount; ++i) {
                int from = i * maxSize;
                int to = Math.min(from + maxSize, list.size());
                List row = list.subList(from, to);
                exportExcelBySheet(monitor, exportCount, wb, head + (i + 1), headStyle, titleStyle, dataStyle, head, fields, titles, row);
            }

            try {
                wb.write(excelOut);
                excelOut.flush();
            } catch (IOException var29) {
                throw new RuntimeException(var29);
            } finally {
                list.clear();

                try {
                    excelOut.close();
                } catch (IOException var28) {
                    var28.printStackTrace();
                }

            }

        }
    }

    private static synchronized void mergeExport2Excel(int[] exportCount, ExcelUtil.ExportMonitor monitor, OutputStream excelOut, String head, List<String[]> fileds, List<String[]> titles, Map<String, List<?>> list, String[] sheetNames) {
        if(fileds != null && fileds.size() != 0 && titles != null && titles.size() != 0 && list != null) {
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFCellStyle headStyle = createHeadStyle(wb);
            HSSFCellStyle titleStyle = createTitleStyle(wb);
            HSSFCellStyle dataStyle = createDataStyle(wb);
            HSSFDataFormat format = wb.createDataFormat();
            short textFormat = format.getFormat("@");
            dataStyle.setDataFormat(textFormat);
            int maxSize = '\ufde8';

            for(int i = 0; i < list.size(); ++i) {
                List<?> data = (List)list.get(String.valueOf(i));
                int sheetCount = data.size() / maxSize + (data.size() % maxSize > 0?1:0);
                sheetCount = sheetCount == 0?1:sheetCount;
                String sheetName = sheetNames != null && sheetNames.length >= i?sheetNames[i]:"Sheet";
                head = sheetNames != null && sheetNames.length >= i?sheetNames[i]:head;

                for(int j = 0; j < sheetCount; ++j) {
                    int from = j * maxSize;
                    int to = Math.min(from + maxSize, data.size());
                    List row = data.subList(from, to);
                    exportExcelBySheet(monitor, exportCount, wb, sheetName, headStyle, titleStyle, dataStyle, head, (String[])fileds.get(i), (String[])titles.get(i), row);
                }
            }

            try {
                wb.write(excelOut);
                excelOut.flush();
            } catch (IOException var30) {
                throw new RuntimeException(var30);
            } finally {
                list.clear();

                try {
                    excelOut.close();
                } catch (IOException var29) {
                    var29.printStackTrace();
                }

            }

        }
    }

    private static HSSFCellStyle createHeadStyle(HSSFWorkbook wb) {
        HSSFCellStyle headStyle = wb.createCellStyle();
        headStyle.setVerticalAlignment((short) 1);
        headStyle.setAlignment((short) 2);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("微软雅黑");
        font.setBoldweight((short) 700);
        headStyle.setFont(font);
        return headStyle;
    }

    private static HSSFCellStyle createTitleStyle(HSSFWorkbook wb) {
        HSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setVerticalAlignment((short) 1);
        titleStyle.setAlignment((short) 2);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("微软雅黑");
        titleStyle.setFont(font);
        titleStyle.setWrapText(false);
        titleStyle.setBorderBottom((short) 1);
        titleStyle.setBorderLeft((short) 1);
        titleStyle.setBorderRight((short) 1);
        titleStyle.setBorderTop((short) 1);
        return titleStyle;
    }

    private static HSSFCellStyle createDataStyle(HSSFWorkbook wb) {
        HSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setVerticalAlignment((short) 1);
        dataStyle.setAlignment((short) 2);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("微软雅黑");
        dataStyle.setFont(font);
        dataStyle.setWrapText(true);
        dataStyle.setBorderBottom((short) 1);
        dataStyle.setBorderLeft((short) 1);
        dataStyle.setBorderRight((short) 1);
        dataStyle.setBorderTop((short) 1);
        return dataStyle;
    }

    public static File export(ExcelUtil.ExportMonitor monitor, String head, String[] fields, String[] titles, List<?> dataList, File saveDir) {
        List<File> xlsFiles = exportExcel((ExcelUtil.ExportMonitor)null, head, fields, titles, dataList, saveDir);
        if(xlsFiles.size() > 1) {
            File zipFile = new File(saveDir.getParentFile(), "batchExport.zip");
            return zipFile;
        } else {
            return xlsFiles.size() == 1?(File)xlsFiles.get(0):null;
        }
    }

    public static File mergeExport(ExcelUtil.ExportMonitor monitor, String head, List<String[]> fields, List<String[]> titles, Map<String, List<?>> list, File saveDir, String[] sheetNames) {
        return mergeExportExcel(monitor, head, fields, titles, list, saveDir, sheetNames);
    }

    public abstract static class ExportMonitor {
        public ExportMonitor() {
        }

        public abstract void before(int var1);

        public abstract void doProgress(int var1, Exception var2);

        public abstract void finish(int var1, int var2, Exception var3);
    }
}
