// package com.mall.controller;
//
// import org.apache.poi.ss.usermodel.Sheet;
// import org.apache.poi.ss.usermodel.Workbook;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import javax.servlet.http.HttpServletResponse;
// import java.io.FileInputStream;
// import java.io.FileOutputStream;
// import java.io.InputStream;
//
// @RestController("excel")
// public class ExportExcelToTemp {
//
//     @PostMapping("/export")
//     public void exportExcel(HttpServletResponse response) {
//         response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//         response.setHeader("Content-Disposition", "attachment; filename=suppInfos.xlsx");
//         InputStream templateStream = null;
//         try {
//             templateStream = new FileInputStream("D:\\template.xlsx");
//             Workbook workbook = new XSSFWorkbook(templateStream);
//             Sheet sheet = workbook.getSheetAt(0);
//             sheet.getRow(0).getCell(0).setCellValue("测试");
//             workbook.write(new FileOutputStream("D:\\template.xlsx"));
//             workbook.close();
//             templateStream.close();
//             System.out.println("done");
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }
