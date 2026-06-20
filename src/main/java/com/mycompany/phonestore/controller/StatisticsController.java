package com.mycompany.phonestore.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mycompany.phonestore.model.User;
import com.mycompany.phonestore.model.UserRole;
import com.mycompany.phonestore.service.StatisticsService;
import com.mycompany.phonestore.template.report.StatisticsReportTemplate;
import com.mycompany.phonestore.template.report.ExcelStatisticsReport;
import com.mycompany.phonestore.template.report.PdfStatisticsReport;

@Controller
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/statistics")
    public String showStatistics(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != UserRole.ADMIN) {
            return "redirect:/";
        }

        // Metrics for Overview cards
        int totalOrders = statisticsService.getTotalOrders();
        double totalRevenue = statisticsService.getTotalRevenue();
        int totalProductsStock = statisticsService.getTotalProductsStock();
        int totalProductTypes = statisticsService.getTotalProductTypes();

        // Metrics for status-based order distribution
        int processingCount = statisticsService.getOrdersCountByStatus("PROCESSING");
        int deliveringCount = statisticsService.getOrdersCountByStatus("DELIVERING");
        int deliveredCount = statisticsService.getOrdersCountByStatus("DELIVERED");
        int cancelledCount = statisticsService.getOrdersCountByStatus("CANCELLED");

        // Metrics for status-based revenue
        double processingRevenue = statisticsService.getRevenueByStatus("PROCESSING");
        double deliveringRevenue = statisticsService.getRevenueByStatus("DELIVERING");
        double deliveredRevenue = statisticsService.getRevenueByStatus("DELIVERED");
        double cancelledRevenue = statisticsService.getRevenueByStatus("CANCELLED");

        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalProductsStock", totalProductsStock);
        model.addAttribute("totalProductTypes", totalProductTypes);

        model.addAttribute("processingCount", processingCount);
        model.addAttribute("deliveringCount", deliveringCount);
        model.addAttribute("deliveredCount", deliveredCount);
        model.addAttribute("cancelledCount", cancelledCount);

        model.addAttribute("processingRevenue", processingRevenue);
        model.addAttribute("deliveringRevenue", deliveringRevenue);
        model.addAttribute("deliveredRevenue", deliveredRevenue);
        model.addAttribute("cancelledRevenue", cancelledRevenue);

        return "statistics";
    }

    @GetMapping("/statistics/export/excel")
    public String exportExcel(HttpSession session, HttpServletResponse response) throws IOException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != UserRole.ADMIN) {
            return "redirect:/";
        }

        StatisticsReportTemplate report = new ExcelStatisticsReport(statisticsService);
        report.exportReport(response, "Bao_Cao_Thong_Ke_Cua_Hang_" + System.currentTimeMillis() + ".csv");
        return null;
    }

    @GetMapping("/statistics/export/pdf")
    public String exportPdf(HttpSession session, HttpServletResponse response) throws IOException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != UserRole.ADMIN) {
            return "redirect:/";
        }

        StatisticsReportTemplate report = new PdfStatisticsReport(statisticsService);
        report.exportReport(response, "Bao_Cao_Thong_Ke_Cua_Hang_" + System.currentTimeMillis() + ".html");
        return null;
    }
}
