package com.mycompany.phonestore.template.report;

import com.mycompany.phonestore.model.StoreStatisticsReportData;
import com.mycompany.phonestore.service.StatisticsService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ExcelStatisticsReport extends StatisticsReportTemplate {

    public ExcelStatisticsReport(StatisticsService statisticsService) {
        super(statisticsService);
    }

    /**
     * Bước 2: Định dạng dữ liệu thống kê thành định dạng CSV (Excel).
     */
    @Override
    protected String formatData(StoreStatisticsReportData data) {
        StringBuilder csv = new StringBuilder();
        
        // CSV Headers
        csv.append("Chỉ số thống kê,Giá trị,Đơn vị / Mô tả\n");
        
        // Tổng quan
        csv.append(String.format("Tổng Doanh Thu,%.0f,VNĐ (Không bao gồm đơn hàng bị hủy)\n", data.getTotalRevenue()));
        csv.append(String.format("Tổng Đơn Hàng,%d,Đơn đặt hàng trên hệ thống\n", data.getTotalOrders()));
        csv.append(String.format("Mẫu Sản Phẩm,%d,Các mẫu điện thoại trong danh mục\n", data.getTotalProductTypes()));
        csv.append(String.format("Số Lượng Trong Kho,%d,Tổng số sản phẩm còn lại\n", data.getTotalProductsStock()));
        
        csv.append("\n"); // Dòng trống ngăn cách
        
        // Phân loại trạng thái
        csv.append("Trạng thái đơn hàng,Số lượng đơn hàng,Doanh thu tương ứng (VNĐ)\n");
        csv.append(String.format("Chờ xử lý (Processing),%d,%.0f\n", data.getProcessingCount(), data.getProcessingRevenue()));
        csv.append(String.format("Đang giao hàng (Delivering),%d,%.0f\n", data.getDeliveringCount(), data.getDeliveringRevenue()));
        csv.append(String.format("Đã giao hàng (Delivered),%d,%.0f\n", data.getDeliveredCount(), data.getDeliveredRevenue()));
        csv.append(String.format("Đã hủy (Cancelled),%d,%.0f\n", data.getCancelledCount(), data.getCancelledRevenue()));
        
        return csv.toString();
    }

    /**
     * Bước 3: Tạo tệp dữ liệu CSV dạng byte array. 
     * Thêm ký tự BOM (Byte Order Mark) của UTF-8 để Microsoft Excel nhận diện được tiếng Việt có dấu.
     */
    @Override
    protected byte[] createFile(String formattedContent) throws IOException {
        byte[] csvBytes = formattedContent.getBytes(StandardCharsets.UTF_8);
        byte[] bom = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}; // UTF-8 BOM
        
        byte[] fileBytes = new byte[bom.length + csvBytes.length];
        System.arraycopy(bom, 0, fileBytes, 0, bom.length);
        System.arraycopy(csvBytes, 0, fileBytes, bom.length, csvBytes.length);
        
        return fileBytes;
    }

    /**
     * Content-Type của tệp Excel/CSV
     */
    @Override
    protected String getContentType() {
        return "text/csv; charset=UTF-8";
    }
}
