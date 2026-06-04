<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thống kê Cửa hàng - Phone Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/statistics.css?v=1.0">
    <!-- Chart.js for data visualization -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <c:set var="totalStatusCount" value="${processingCount + deliveringCount + deliveredCount + cancelledCount}" />
    <c:choose>
        <c:when test="${totalStatusCount > 0}">
            <c:set var="processingPercent" value="${(processingCount * 100.0) / totalStatusCount}" />
            <c:set var="deliveringPercent" value="${(deliveringCount * 100.0) / totalStatusCount}" />
            <c:set var="deliveredPercent" value="${(deliveredCount * 100.0) / totalStatusCount}" />
            <c:set var="cancelledPercent" value="${(cancelledCount * 100.0) / totalStatusCount}" />
        </c:when>
        <c:otherwise>
            <c:set var="processingPercent" value="0.0" />
            <c:set var="deliveringPercent" value="0.0" />
            <c:set var="deliveredPercent" value="0.0" />
            <c:set var="cancelledPercent" value="0.0" />
        </c:otherwise>
    </c:choose>

    <div class="dashboard-container">
        
        <!-- Header -->
        <div class="dashboard-header">
            <h2>Bảng thống kê hoạt động</h2>
            <a href="${pageContext.request.contextPath}/" class="btn-back">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="margin-right: 8px;">
                    <line x1="19" y1="12" x2="5" y2="12"></line>
                    <polyline points="12 19 5 12 12 5"></polyline>
                </svg>
                Quay lại trang chủ
            </a>
        </div>

        <!-- Metrics Overview Grid -->
        <div class="metrics-grid">
            
            <!-- Card 1: Revenue -->
            <div class="metric-card card-revenue">
                <div class="metric-title">Tổng Doanh Thu</div>
                <div class="metric-value">
                    <fmt:formatNumber value="${totalRevenue}" pattern="#,##0"/>
                    <span style="font-size: 1.2rem; font-weight: 500; color: var(--color-success);">VNĐ</span>
                </div>
                <div class="metric-subtitle">Không bao gồm đơn hàng bị hủy</div>
            </div>

            <!-- Card 2: Total Orders -->
            <div class="metric-card card-orders">
                <div class="metric-title">Tổng Đơn Hàng</div>
                <div class="metric-value">${totalOrders}</div>
                <div class="metric-subtitle">Đơn đặt hàng trên hệ thống</div>
            </div>

            <!-- Card 3: Product Types (Models) -->
            <div class="metric-card card-types">
                <div class="metric-title">Mẫu Sản Phẩm</div>
                <div class="metric-value">${totalProductTypes}</div>
                <div class="metric-subtitle">Các mẫu điện thoại trong danh mục</div>
            </div>

            <!-- Card 4: Total Stock Quantity -->
            <div class="metric-card card-stock">
                <div class="metric-title">Số Lượng Trong Kho</div>
                <div class="metric-value">
                    <fmt:formatNumber value="${totalProductsStock}" pattern="#,##0"/>
                </div>
                <div class="metric-subtitle">Tổng số sản phẩm còn lại</div>
            </div>

        </div>

        <!-- Main Details Columns -->
        <div class="dashboard-details">
            
            <!-- Status Breakdown Panel -->
            <div class="panel">
                <h3>Trạng thái đơn hàng</h3>
                <div class="status-list">
                    
                    <!-- Processing -->
                    <div class="status-item">
                        <div class="status-label">
                            <span class="status-dot dot-processing"></span>
                            <span class="status-name">Chờ xử lý (Processing)</span>
                        </div>
                        <div class="status-value">
                            <div class="status-count">
                                ${processingCount} đơn hàng
                                <span style="font-size: 0.95rem; color: var(--color-info); margin-left: 5px;">
                                    (<fmt:formatNumber value="${processingPercent}" pattern="0.0"/>%)
                                </span>
                            </div>
                            <div class="status-rev">
                                Doanh thu dự kiến: <fmt:formatNumber value="${processingRevenue}" pattern="#,##0"/> đ
                            </div>
                        </div>
                    </div>

                    <!-- Delivering -->
                    <div class="status-item">
                        <div class="status-label">
                            <span class="status-dot dot-delivering"></span>
                            <span class="status-name">Đang giao hàng (Delivering)</span>
                        </div>
                        <div class="status-value">
                            <div class="status-count">
                                ${deliveringCount} đơn hàng
                                <span style="font-size: 0.95rem; color: var(--color-warning); margin-left: 5px;">
                                    (<fmt:formatNumber value="${deliveringPercent}" pattern="0.0"/>%)
                                </span>
                            </div>
                            <div class="status-rev">
                                Đang vận chuyển: <fmt:formatNumber value="${deliveringRevenue}" pattern="#,##0"/> đ
                            </div>
                        </div>
                    </div>

                    <!-- Delivered -->
                    <div class="status-item">
                        <div class="status-label">
                            <span class="status-dot dot-delivered"></span>
                            <span class="status-name">Đã giao hàng (Delivered)</span>
                        </div>
                        <div class="status-value">
                            <div class="status-count" style="color: var(--color-success);">
                                ${deliveredCount} đơn hàng
                                <span style="font-size: 0.95rem; color: var(--color-success); margin-left: 5px;">
                                    (<fmt:formatNumber value="${deliveredPercent}" pattern="0.0"/>%)
                                </span>
                            </div>
                            <div class="status-rev">
                                Doanh thu thực tế: <fmt:formatNumber value="${deliveredRevenue}" pattern="#,##0"/> đ
                            </div>
                        </div>
                    </div>

                    <!-- Cancelled -->
                    <div class="status-item">
                        <div class="status-label">
                            <span class="status-dot dot-cancelled"></span>
                            <span class="status-name">Đã hủy (Cancelled)</span>
                        </div>
                        <div class="status-value">
                            <div class="status-count" style="color: var(--color-danger);">
                                ${cancelledCount} đơn hàng
                                <span style="font-size: 0.95rem; color: var(--color-danger); margin-left: 5px;">
                                    (<fmt:formatNumber value="${cancelledPercent}" pattern="0.0"/>%)
                                </span>
                            </div>
                            <div class="status-rev">
                                Giá trị hủy bỏ: <fmt:formatNumber value="${cancelledRevenue}" pattern="#,##0"/> đ
                            </div>
                        </div>
                    </div>

                </div>
            </div>

            <!-- Visual Chart Panel -->
            <div class="panel" style="display: flex; flex-direction: column; align-items: center; justify-content: center;">
                <h3 style="align-self: flex-start; width: 100%;">Tỷ lệ trạng thái đơn</h3>
                <div class="chart-container">
                    <canvas id="statusChart"></canvas>
                </div>
            </div>

        </div>

    </div>

    <!-- Script to render Chart.js chart -->
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            const ctx = document.getElementById('statusChart').getContext('2d');
            
            // Values from model attribute
            const processing = ${processingCount};
            const delivering = ${deliveringCount};
            const delivered = ${deliveredCount};
            const cancelled = ${cancelledCount};
            
            const total = processing + delivering + delivered + cancelled;
            
            if (total === 0) {
                // Draw a placeholder circle if there's no data
                new Chart(ctx, {
                    type: 'doughnut',
                    data: {
                        labels: ['Không có dữ liệu'],
                        datasets: [{
                            data: [1],
                            backgroundColor: ['rgba(255, 255, 255, 0.1)'],
                            borderColor: ['rgba(255, 255, 255, 0.2)'],
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        plugins: {
                            legend: {
                                labels: { color: '#94a3b8' }
                            }
                        }
                    }
                });
                return;
            }

            new Chart(ctx, {
                type: 'doughnut',
                data: {
                    labels: [
                        'Chờ xử lý: ' + (total > 0 ? ((processing / total) * 100).toFixed(1) : 0) + '%',
                        'Đang giao: ' + (total > 0 ? ((delivering / total) * 100).toFixed(1) : 0) + '%',
                        'Đã giao: ' + (total > 0 ? ((delivered / total) * 100).toFixed(1) : 0) + '%',
                        'Đã hủy: ' + (total > 0 ? ((cancelled / total) * 100).toFixed(1) : 0) + '%'
                    ],
                    datasets: [{
                        data: [processing, delivering, delivered, cancelled],
                        backgroundColor: [
                            '#06b6d4', // Cyan
                            '#f59e0b', // Amber
                            '#10b981', // Emerald
                            '#ef4444'  // Rose
                        ],
                        borderColor: '#0a0b10',
                        borderWidth: 2,
                        hoverOffset: 8
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: {
                            position: 'bottom',
                            labels: {
                                color: '#f8fafc',
                                font: {
                                    family: "'Outfit', sans-serif",
                                    size: 12
                                },
                                padding: 16
                            }
                        },
                        tooltip: {
                            callbacks: {
                                label: function(context) {
                                    const value = context.raw;
                                    const percentage = ((value / total) * 100).toFixed(1);
                                    return `${context.label.split(':')[0]}: ${value} đơn (${percentage}%)`;
                                }
                            }
                        }
                    },
                    cutout: '65%'
                }
            });
        });
    </script>
</body>
</html>
