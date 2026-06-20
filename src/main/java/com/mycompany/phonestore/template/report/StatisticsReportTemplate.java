package com.mycompany.phonestore.template.report;

import com.mycompany.phonestore.model.StoreStatisticsReportData;
import com.mycompany.phonestore.service.StatisticsService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class StatisticsReportTemplate {

    protected final StatisticsService statisticsService;

    protected StatisticsReportTemplate(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    /**
     * The Template Method. 
     * Khung quy trình cố định: Đọc dữ liệu -> Định dạng dữ liệu -> Tạo file -> Gửi luồng dữ liệu về trình duyệt.
     */
    public final void exportReport(HttpServletResponse response, String filename) throws IOException {
        // 1. Đọc dữ liệu (Read data)
        StoreStatisticsReportData data = readData();

        // 2. Định dạng dữ liệu (Format data)
        String formattedContent = formatData(data);

        // 3. Tạo file (Create file)
        byte[] fileBytes = createFile(formattedContent);

        // 4. Gửi luồng dữ liệu về trình duyệt (Send to browser)
        sendToBrowser(response, fileBytes, filename);
    }

    /**
     * Bước 1: Đọc dữ liệu. Thống nhất đọc từ StatisticsService.
     */
    protected StoreStatisticsReportData readData() {
        StoreStatisticsReportData data = new StoreStatisticsReportData();
        data.setTotalOrders(statisticsService.getTotalOrders());
        data.setTotalRevenue(statisticsService.getTotalRevenue());
        data.setTotalProductsStock(statisticsService.getTotalProductsStock());
        data.setTotalProductTypes(statisticsService.getTotalProductTypes());

        data.setProcessingCount(statisticsService.getOrdersCountByStatus("PROCESSING"));
        data.setDeliveringCount(statisticsService.getOrdersCountByStatus("DELIVERING"));
        data.setDeliveredCount(statisticsService.getOrdersCountByStatus("DELIVERED"));
        data.setCancelledCount(statisticsService.getOrdersCountByStatus("CANCELLED"));

        data.setProcessingRevenue(statisticsService.getRevenueByStatus("PROCESSING"));
        data.setDeliveringRevenue(statisticsService.getRevenueByStatus("DELIVERING"));
        data.setDeliveredRevenue(statisticsService.getRevenueByStatus("DELIVERED"));
        data.setCancelledRevenue(statisticsService.getRevenueByStatus("CANCELLED"));

        return data;
    }

    /**
     * Bước 2: Định dạng dữ liệu. Lớp con cần tự định nghĩa cách biểu diễn dữ liệu thống kê.
     */
    protected abstract String formatData(StoreStatisticsReportData data);

    /**
     * Bước 3: Tạo file. Chuyển đổi định dạng văn bản thành file nhị phân (mảng byte) theo chuẩn của định dạng đó.
     */
    protected abstract byte[] createFile(String formattedContent) throws IOException;

    /**
     * Xác định Content-Type thích hợp cho tệp tin được tải xuống.
     */
    protected abstract String getContentType();

    /**
     * Bước 4: Gửi luồng dữ liệu về trình duyệt thông qua HttpServletResponse.
     */
    private void sendToBrowser(HttpServletResponse response, byte[] content, String filename) throws IOException {
        response.setContentType(getContentType());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.setContentLength(content.length);
        response.getOutputStream().write(content);
        response.getOutputStream().flush();
    }
}
