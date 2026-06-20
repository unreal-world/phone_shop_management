package com.mycompany.phonestore.template.report;

import com.mycompany.phonestore.model.StoreStatisticsReportData;
import com.mycompany.phonestore.service.StatisticsService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PdfStatisticsReport extends StatisticsReportTemplate {

    public PdfStatisticsReport(StatisticsService statisticsService) {
        super(statisticsService);
    }

    /**
     * Bước 2: Định dạng dữ liệu thành trang HTML báo cáo thống kê chuyên nghiệp, tích hợp CSS in ấn (Print CSS).
     */
    @Override
    protected String formatData(StoreStatisticsReportData data) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = now.format(formatter);

        // Định dạng tiền tệ
        String totalRevenueStr = String.format("%,.0f", data.getTotalRevenue());
        String processingRevenueStr = String.format("%,.0f", data.getProcessingRevenue());
        String deliveringRevenueStr = String.format("%,.0f", data.getDeliveringRevenue());
        String deliveredRevenueStr = String.format("%,.0f", data.getDeliveredRevenue());
        String cancelledRevenueStr = String.format("%,.0f", data.getCancelledRevenue());

        return "<!DOCTYPE html>\n" +
                "<html lang=\"vi\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Báo cáo thống kê hoạt động - Phone Store</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap\" rel=\"stylesheet\">\n" +
                "    <style>\n" +
                "        :root {\n" +
                "            --primary: #2563eb;\n" +
                "            --success: #10b981;\n" +
                "            --warning: #f59e0b;\n" +
                "            --danger: #ef4444;\n" +
                "            --info: #06b6d4;\n" +
                "            --text-main: #1e293b;\n" +
                "            --text-muted: #64748b;\n" +
                "            --bg-light: #f8fafc;\n" +
                "            --border: #e2e8f0;\n" +
                "        }\n" +
                "        * { box-sizing: border-box; margin: 0; padding: 0; }\n" +
                "        body {\n" +
                "            font-family: 'Outfit', sans-serif;\n" +
                "            color: var(--text-main);\n" +
                "            background-color: #ffffff;\n" +
                "            line-height: 1.5;\n" +
                "            padding: 40px;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 850px;\n" +
                "            margin: 0 auto;\n" +
                "        }\n" +
                "        /* Header */\n" +
                "        .header {\n" +
                "            display: flex;\n" +
                "            justify-content: space-between;\n" +
                "            align-items: center;\n" +
                "            border-bottom: 3px solid var(--primary);\n" +
                "            padding-bottom: 20px;\n" +
                "            margin-bottom: 30px;\n" +
                "        }\n" +
                "        .brand h1 {\n" +
                "            color: var(--primary);\n" +
                "            font-size: 28px;\n" +
                "            font-weight: 700;\n" +
                "            text-transform: uppercase;\n" +
                "        }\n" +
                "        .brand p {\n" +
                "            font-size: 14px;\n" +
                "            color: var(--text-muted);\n" +
                "        }\n" +
                "        .meta-info {\n" +
                "            text-align: right;\n" +
                "            font-size: 13px;\n" +
                "            color: var(--text-muted);\n" +
                "        }\n" +
                "        .report-title {\n" +
                "            text-align: center;\n" +
                "            font-size: 22px;\n" +
                "            font-weight: 600;\n" +
                "            margin-bottom: 30px;\n" +
                "            color: #0f172a;\n" +
                "            text-transform: uppercase;\n" +
                "            letter-spacing: 0.5px;\n" +
                "        }\n" +
                "        /* Metrics Grid */\n" +
                "        .grid {\n" +
                "            display: grid;\n" +
                "            grid-template-columns: repeat(4, 1fr);\n" +
                "            gap: 15px;\n" +
                "            margin-bottom: 35px;\n" +
                "        }\n" +
                "        .card {\n" +
                "            background: var(--bg-light);\n" +
                "            border: 1px solid var(--border);\n" +
                "            border-radius: 8px;\n" +
                "            padding: 15px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .card-title {\n" +
                "            font-size: 12px;\n" +
                "            font-weight: 500;\n" +
                "            color: var(--text-muted);\n" +
                "            text-transform: uppercase;\n" +
                "            margin-bottom: 8px;\n" +
                "        }\n" +
                "        .card-value {\n" +
                "            font-size: 18px;\n" +
                "            font-weight: 700;\n" +
                "            color: #0f172a;\n" +
                "        }\n" +
                "        .card-value span {\n" +
                "            font-size: 12px;\n" +
                "            font-weight: 500;\n" +
                "            color: var(--text-muted);\n" +
                "        }\n" +
                "        /* Table */\n" +
                "        table {\n" +
                "            width: 100%;\n" +
                "            border-collapse: collapse;\n" +
                "            margin-bottom: 40px;\n" +
                "        }\n" +
                "        th, td {\n" +
                "            padding: 12px 15px;\n" +
                "            text-align: left;\n" +
                "            border-bottom: 1px solid var(--border);\n" +
                "        }\n" +
                "        th {\n" +
                "            background-color: var(--bg-light);\n" +
                "            color: #0f172a;\n" +
                "            font-weight: 600;\n" +
                "            font-size: 13px;\n" +
                "            text-transform: uppercase;\n" +
                "        }\n" +
                "        td {\n" +
                "            font-size: 14px;\n" +
                "        }\n" +
                "        .badge {\n" +
                "            display: inline-block;\n" +
                "            padding: 4px 8px;\n" +
                "            border-radius: 4px;\n" +
                "            font-size: 11px;\n" +
                "            font-weight: 600;\n" +
                "            color: #fff;\n" +
                "            text-transform: uppercase;\n" +
                "        }\n" +
                "        .badge-processing { background-color: var(--info); }\n" +
                "        .badge-delivering { background-color: var(--warning); }\n" +
                "        .badge-delivered { background-color: var(--success); }\n" +
                "        .badge-cancelled { background-color: var(--danger); }\n" +
                "        .text-right { text-align: right; }\n" +
                "        /* Footer Signatures */\n" +
                "        .signatures {\n" +
                "            display: flex;\n" +
                "            justify-content: space-between;\n" +
                "            margin-top: 60px;\n" +
                "            font-size: 14px;\n" +
                "        }\n" +
                "        .signature-box {\n" +
                "            text-align: center;\n" +
                "            width: 250px;\n" +
                "        }\n" +
                "        .signature-box p {\n" +
                "            margin-bottom: 80px;\n" +
                "        }\n" +
                "        .signature-box .name {\n" +
                "            font-weight: 600;\n" +
                "            text-decoration: underline;\n" +
                "        }\n" +
                "        /* Print styles */\n" +
                "        @media print {\n" +
                "            body { padding: 0; }\n" +
                "            .no-print { display: none; }\n" +
                "            .card {\n" +
                "                border: 1px solid #cbd5e1 !important;\n" +
                "                background: #f8fafc !important;\n" +
                "                -webkit-print-color-adjust: exact;\n" +
                "                print-color-adjust: exact;\n" +
                "            }\n" +
                "            th {\n" +
                "                background-color: #f1f5f9 !important;\n" +
                "                -webkit-print-color-adjust: exact;\n" +
                "                print-color-adjust: exact;\n" +
                "            }\n" +
                "            .badge {\n" +
                "                -webkit-print-color-adjust: exact;\n" +
                "                print-color-adjust: exact;\n" +
                "            }\n" +
                "            .badge-processing { background-color: #0284c7 !important; }\n" +
                "            .badge-delivering { background-color: #d97706 !important; }\n" +
                "            .badge-delivered { background-color: #059669 !important; }\n" +
                "            .badge-cancelled { background-color: #dc2626 !important; }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <!-- Header -->\n" +
                "        <div class=\"header\">\n" +
                "            <div class=\"brand\">\n" +
                "                <h1>PHONE STORE</h1>\n" +
                "                <p>Hệ thống quản lý cửa hàng điện thoại di động</p>\n" +
                "            </div>\n" +
                "            <div class=\"meta-info\">\n" +
                "                <p>Mẫu số: B01-STT</p>\n" +
                "                <p>Thời gian xuất: " + formattedDate + "</p>\n" +
                "                <p>Người xuất: Người quản trị</p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "        <!-- Title -->\n" +
                "        <div class=\"report-title\">\n" +
                "            Báo cáo thống kê hoạt động kinh doanh\n" +
                "        </div>\n" +
                "\n" +
                "        <!-- Metrics Grid -->\n" +
                "        <div class=\"grid\">\n" +
                "            <div class=\"card\">\n" +
                "                <div class=\"card-title\">Tổng doanh thu</div>\n" +
                "                <div class=\"card-value\" style=\"color: var(--primary);\">" + totalRevenueStr + " <span>đ</span></div>\n" +
                "            </div>\n" +
                "            <div class=\"card\">\n" +
                "                <div class=\"card-title\">Tổng đơn hàng</div>\n" +
                "                <div class=\"card-value\">" + data.getTotalOrders() + " <span>đơn</span></div>\n" +
                "            </div>\n" +
                "            <div class=\"card\">\n" +
                "                <div class=\"card-title\">Mẫu sản phẩm</div>\n" +
                "                <div class=\"card-value\">" + data.getTotalProductTypes() + " <span>mẫu</span></div>\n" +
                "            </div>\n" +
                "            <div class=\"card\">\n" +
                "                <div class=\"card-title\">Hàng trong kho</div>\n" +
                "                <div class=\"card-value\">" + data.getTotalProductsStock() + " <span>cái</span></div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "        <!-- Details Table -->\n" +
                "        <table>\n" +
                "            <thead>\n" +
                "                <tr>\n" +
                "                    <th>Trạng thái đơn hàng</th>\n" +
                "                    <th class=\"text-right\">Số lượng đơn</th>\n" +
                "                    <th class=\"text-right\">Tỷ lệ (%)</th>\n" +
                "                    <th class=\"text-right\">Doanh thu / Giá trị tương ứng</th>\n" +
                "                </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td><span class=\"badge badge-processing\">Chờ xử lý</span></td>\n" +
                "                    <td class=\"text-right\">" + data.getProcessingCount() + "</td>\n" +
                "                    <td class=\"text-right\">" + String.format("%.1f", data.getTotalOrders() > 0 ? (data.getProcessingCount() * 100.0 / data.getTotalOrders()) : 0) + "%</td>\n" +
                "                    <td class=\"text-right\">" + processingRevenueStr + " VNĐ</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td><span class=\"badge badge-delivering\">Đang giao hàng</span></td>\n" +
                "                    <td class=\"text-right\">" + data.getDeliveringCount() + "</td>\n" +
                "                    <td class=\"text-right\">" + String.format("%.1f", data.getTotalOrders() > 0 ? (data.getDeliveringCount() * 100.0 / data.getTotalOrders()) : 0) + "%</td>\n" +
                "                    <td class=\"text-right\">" + deliveringRevenueStr + " VNĐ</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td><span class=\"badge badge-delivered\">Đã giao hàng</span></td>\n" +
                "                    <td class=\"text-right\" style=\"font-weight: 500; color: var(--success);\">" + data.getDeliveredCount() + "</td>\n" +
                "                    <td class=\"text-right\">" + String.format("%.1f", data.getTotalOrders() > 0 ? (data.getDeliveredCount() * 100.0 / data.getTotalOrders()) : 0) + "%</td>\n" +
                "                    <td class=\"text-right\" style=\"font-weight: 500; color: var(--success);\">" + deliveredRevenueStr + " VNĐ</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td><span class=\"badge badge-cancelled\">Đã hủy</span></td>\n" +
                "                    <td class=\"text-right\" style=\"color: var(--danger);\">" + data.getCancelledCount() + "</td>\n" +
                "                    <td class=\"text-right\">" + String.format("%.1f", data.getTotalOrders() > 0 ? (data.getCancelledCount() * 100.0 / data.getTotalOrders()) : 0) + "%</td>\n" +
                "                    <td class=\"text-right\" style=\"color: var(--danger);\">" + cancelledRevenueStr + " VNĐ</td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "\n" +
                "        <!-- Signatures -->\n" +
                "        <div class=\"signatures\">\n" +
                "            <div class=\"signature-box\">\n" +
                "                <p>Người lập báo cáo</p>\n" +
                "                <p class=\"name\">Quản lý Cửa hàng</p>\n" +
                "            </div>\n" +
                "            <div class=\"signature-box\">\n" +
                "                <p>Giám đốc duyệt</p>\n" +
                "                <p class=\"name\">Ban Giám Đốc</p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "    <!-- Script tự động mở hộp thoại in/xuất PDF khi mở tệp tin -->\n" +
                "    <script type=\"text/javascript\">\n" +
                "        window.onload = function() {\n" +
                "            setTimeout(function() {\n" +
                "                window.print();\n" +
                "            }, 500);\n" +
                "        }\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";
    }

    /**
     * Bước 3: Tạo tệp PDF (HTML) dạng byte array.
     */
    @Override
    protected byte[] createFile(String formattedContent) throws IOException {
        return formattedContent.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Content-Type của tệp HTML in ấn báo cáo PDF
     */
    @Override
    protected String getContentType() {
        return "text/html; charset=UTF-8";
    }
}
