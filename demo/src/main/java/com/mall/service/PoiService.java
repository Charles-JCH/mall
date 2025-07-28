package com.mall.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressBase;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

@Service
public class PoiService {

    public Workbook exportExcel() throws Exception {
        // 1. 读取上传的模板
        InputStream templateStream = new FileInputStream("D:\\template.xlsx");
        Workbook workbook = new XSSFWorkbook(templateStream);
        Sheet sheet = workbook.getSheetAt(0);

        // 2. 查询数据库，组装数据
        List<Map<String, Object>> suppInfos = new ArrayList<>();
        Map<String, Object> supp1 = new HashMap<>();
        supp1.put("供方名称", "供方名称");
        supp1.put("注册资本", "注册资本");
        supp1.put("认证情况", "认证情况");
        List<Map<String, Object>> productDevices = new ArrayList<>();
        Map<String, Object> productDevice1 = new HashMap<>();
        productDevice1.put("产品加工工序名称", "1111111111");
        productDevice1.put("设备名称", "22222222222");
        productDevice1.put("设备参数", "3333333333333");
        productDevice1.put("进口", "444444444444444");
        productDevice1.put("国产", "555555555555");
        productDevice1.put("数量", "66666666666");
        productDevice1.put("设备出厂日期", "7777777777777");
        productDevices.add(productDevice1);
        productDevices.add(productDevice1);
        supp1.put("生产设备清单", productDevices);
        suppInfos.add(supp1);
        suppInfos.add(supp1);
        suppInfos.add(supp1);

        // ============= 核心处理 =============
        // 假设黄色区域在第1行开始，两列（比如D列、E列）
        int baseRow = 0; // 从 Excel 的第1行开始写（下标从0算起）
        int baseCol = 3; // 从D列开始写

        // int maxRows = suppInfos.stream()
        //         .map(user -> user != null && user.get("生产设备清单") != null ? (List<String>) user.get("生产设备清单").size : 0)
        //         .max(Integer::compare)
        //         .orElse(0);

        for (int i = 0; i < suppInfos.size(); i++) {
            if (i > 0) {
                copyColumnsToRight(sheet, baseCol + (i - 1) * 8, 8);
            }
            for (int j = 0; j < 3; j++) {
                if (i == 0) {
                    copyRowsToBottom(sheet, 30, 1);
                }
            }
            for (int j = 0; j < 3; j++) {
                if (i == 0) {
                    copyRowsToBottom(sheet, 27, 1);
                }
            }

        }

        for (int i = 0; i < suppInfos.size(); i++) {
            Map<String, Object> supp = suppInfos.get(i);

            // 如果大于1个供应商，需要往右插入新列
            // if (i > 0) {
            //     // sheet.shiftColumns(3 + i * 8, 3 + i * 8, 8);
            //     // 先处理合并单元格
            //     // moveMergedRegions(sheet, 3 + i * 8, 3 + i * 8);
            //     // moveColumnsToRight(sheet, 3 + i * 8, 3 + i * 8, 8);
            //
            //     // copyTemplateColumns(sheet, 3 + i * 8, 3 + (i + 1) * 8, 0);
            //     // removeMergedRegions(sheet, 3 + i * 8, 3 + (i + 1) * 8);
            //
            //     copyColumnsToRight(sheet, baseCol + (i - 1) * 8, 8);
            // }


            Cell cell1 = sheet.getRow(0).getCell(baseCol + i * 8);
            cell1.setCellValue(supp.get("供方名称").toString());
            Cell cell2 = sheet.getRow(1).getCell(baseCol + i * 8);
            cell2.setCellValue(supp.get("注册资本").toString());
            Cell cell3 = sheet.getRow(2).getCell(baseCol + i * 8);
            cell3.setCellValue(supp.get("认证情况").toString());


            List<Map<String, Object>> productDevices1 = (List<Map<String, Object>>) supp.get("生产设备清单");
            int prodStartRow = 27;
            int prodStartCol = 3;
            for (int j = 0; j < 2; j++) {
                // if (i == 0) {
                //     copyRowsToBottom(sheet, 27, 1);
                // }
                Row prodRow = sheet.getRow(prodStartRow + j);
                prodRow.createCell(prodStartCol + i * 8).setCellValue(productDevices1.get(j).get("产品加工工序名称").toString());
                prodRow.createCell(prodStartCol + 1 + i * 8).setCellValue(productDevices1.get(j).get("设备名称").toString());
                prodRow.createCell(prodStartCol + 2 + i * 8).setCellValue(productDevices1.get(j).get("设备参数").toString());
                prodRow.createCell(prodStartCol + 3 + i * 8).setCellValue(productDevices1.get(j).get("进口").toString());
                prodRow.createCell(prodStartCol + 4 + i * 8).setCellValue(productDevices1.get(j).get("国产").toString());
                prodRow.createCell(prodStartCol + 5 + i * 8).setCellValue(productDevices1.get(j).get("数量").toString());
                prodRow.createCell(prodStartCol + 6 + i * 8).setCellValue(productDevices1.get(j).get("设备出厂日期").toString());
            }


            // 检验设备清单
            int inspStartRow = 30 + 3;
            int inspStartCol = 3;

            for (int j = 0; j < 2; j++) {
                // if (i == 0) {
                //     copyRowsToBottom(sheet, 30, 1);
                // }
                Row prodRow = sheet.getRow(inspStartRow + j);
                prodRow.createCell(inspStartCol + i * 8).setCellValue(productDevices1.get(j).get("产品加工工序名称").toString());
                prodRow.createCell(inspStartCol + 1 + i * 8).setCellValue(productDevices1.get(j).get("设备名称").toString());
                prodRow.createCell(inspStartCol + 2 + i * 8).setCellValue(productDevices1.get(j).get("设备参数").toString());
                prodRow.createCell(inspStartCol + 3 + i * 8).setCellValue(productDevices1.get(j).get("进口").toString());
                prodRow.createCell(inspStartCol + 4 + i * 8).setCellValue(productDevices1.get(j).get("国产").toString());
                prodRow.createCell(inspStartCol + 5 + i * 8).setCellValue(productDevices1.get(j).get("数量").toString());
                prodRow.createCell(inspStartCol + 6 + i * 8).setCellValue(productDevices1.get(j).get("设备出厂日期").toString());
            }


        }
        return workbook;
    }

    /**
     * 列复制
     *
     * @param sheet
     * @param sourceCol
     * @param offset
     */
    private void copyColumnsToRight(Sheet sheet, int sourceCol, int offset) {
        // 复制合并区域
        List<CellRangeAddress> mergedRegions = sortMergedRegions(sheet);
        for (int i = mergedRegions.size() - 1; i >= 0; i--) {
            CellRangeAddress region = mergedRegions.get(i);
            if (region.getFirstColumn() >= sourceCol) {
                if (region.getFirstColumn() == sourceCol + offset
                        && region.getFirstRow() >= 26) {
                    CellRangeAddress currentRegion = new CellRangeAddress(
                            region.getFirstRow(),
                            region.getLastRow(),
                            region.getFirstColumn(),
                            region.getLastColumn()
                    );
                    // 移除权重列的源合并区域
                    removeMergedRegions(sheet, currentRegion);
                }
                CellRangeAddress targetRegion = new CellRangeAddress(
                        region.getFirstRow(),
                        region.getLastRow(),
                        region.getFirstColumn() + offset,
                        region.getLastColumn() + offset
                );
                // 如果目标区域存在合并, 则删除
                removeMergedRegions(sheet, targetRegion);
                // 添加新合并区域
                sheet.addMergedRegion(targetRegion);
            }
        }

        // 复制列
        for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            for (int i = row.getLastCellNum() - 1; i >= sourceCol; i--) {
                // 获取源单元格
                Cell sourceCell = row.getCell(i);
                // 创建目标单元格
                Cell targetCell = row.createCell(i + offset);
                // 复制样式和值
                copyCellStyleAndValue(sourceCell, targetCell);
                // 复制列宽
                if (rowNum == sheet.getLastRowNum()) {
                    sheet.setColumnWidth(i + offset, sheet.getColumnWidth(i));
                }
            }
        }
    }

    /**
     * 行复制
     *
     * @param sheet
     * @param sourceRow
     * @param offset
     */
    private void copyRowsToBottom(Sheet sheet, int sourceRow, int offset) {
        // 复制合并区域
        List<CellRangeAddress> mergedRegions = sortMergedRegions(sheet);
        for (int i = mergedRegions.size() - 1; i >= 0; i--) {
            CellRangeAddress region = mergedRegions.get(i);
            if (region.getFirstRow() < sourceRow && region.getLastRow() >= sourceRow) { // 向下扩展
                CellRangeAddress targetRegion = new CellRangeAddress(
                        region.getFirstRow(),
                        region.getLastRow() + offset,
                        region.getFirstColumn(),
                        region.getLastColumn()
                );
                // 如果目标区域存在合并, 则删除
                removeMergedRegions(sheet, targetRegion);
                // 添加新合并区域
                sheet.addMergedRegion(targetRegion);
            } else if (region.getFirstRow() > sourceRow) { // 向下复制
                CellRangeAddress targetRegion = new CellRangeAddress(
                        region.getFirstRow() + offset,
                        region.getLastRow() + offset,
                        region.getFirstColumn(),
                        region.getLastColumn()
                );
                // 如果目标区域存在合并, 则删除
                removeMergedRegions(sheet, targetRegion);
                // 添加新合并区域
                sheet.addMergedRegion(targetRegion);
            }
        }

        // 复制行
        for (int rowNum = sheet.getLastRowNum(); rowNum >= sourceRow; rowNum--) {
            Row row = sheet.getRow(rowNum);
            Row targetRow = sheet.getRow(rowNum + offset);
            if (targetRow == null) {
                targetRow = sheet.createRow(rowNum + offset);
            }
            // 复制行高
            if (rowNum == sourceRow) {
                targetRow.setHeightInPoints(row.getHeightInPoints());
            }
            for (int colNum = 0; colNum <= row.getLastCellNum() - 1; colNum++) {
                // 获取源单元格
                Cell sourceCell = row.getCell(colNum);
                // 创建目标单元格
                Cell targetCell = targetRow.createCell(colNum);
                // 复制样式和值
                copyCellStyleAndValue(sourceCell, targetCell);
            }
        }
    }

    /**
     * 复制单元格样式和值
     *
     * @param sourceCell 源单元格
     * @param targetCell 目标单元格
     */
    private void copyCellStyleAndValue(Cell sourceCell, Cell targetCell) {
        // 复制样式
        targetCell.setCellStyle(sourceCell.getCellStyle());
        // 复制值
        switch (sourceCell.getCellType()) {
            case STRING:
                targetCell.setCellValue(sourceCell.getStringCellValue());
                break;
            case NUMERIC:
                targetCell.setCellValue(sourceCell.getNumericCellValue());
                break;
            case BOOLEAN:
                targetCell.setCellValue(sourceCell.getBooleanCellValue());
                break;
            case FORMULA:
                targetCell.setCellFormula(sourceCell.getCellFormula());
                break;
            case BLANK:
                targetCell.setBlank();
                break;
            case ERROR:
                targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
                break;
            default:
                break;
        }
    }

    /**
     * 删除合并区域
     *
     * @param sheet
     * @param targetRegion
     */
    private void removeMergedRegions(Sheet sheet, CellRangeAddress targetRegion) {
        for (int i = sheet.getNumMergedRegions() - 1; i >= 0; i--) {
            CellRangeAddress existRegion = sheet.getMergedRegion(i);
            if (existRegion.getFirstRow() <= targetRegion.getLastRow() &&
                    existRegion.getLastRow() >= targetRegion.getFirstRow() &&
                    existRegion.getFirstColumn() <= targetRegion.getLastColumn() &&
                    existRegion.getLastColumn() >= targetRegion.getFirstColumn()) {
                sheet.removeMergedRegion(i);
            }
        }
    }

    /**
     * 排序合并区域
     *
     * @param sheet
     * @return
     */
    private List<CellRangeAddress> sortMergedRegions(Sheet sheet) {
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        mergedRegions.sort(Comparator
                .comparingInt((CellRangeAddress o) -> o.getFirstRow())
                .thenComparingInt(CellRangeAddressBase::getFirstColumn));
        return mergedRegions;
    }
}

