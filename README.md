# Crawl-week3
Dự án này là một trình thu thập dữ liệu (crawler) từ OpenSea
https://opensea.io/rankings/trending?sortBy=one_day_volume, nền tảng giao dịch NFT 
hàng đầu.Công cụ này tự động thu thập thông tin về các bộ sưu tập, NFT, giá cả, 
lịch sử giao dịch và chủ sở hữu. Đồng thời hỗ trợ lưu dữ liệu dưới dạng JSON. Dữ 
liệu có thể được sử dụng để phân tích xu hướng thị trường, theo dõi giá trị tài sản
kỹ thuật số hoặc hỗ trợ nghiên cứu về NFT.
# Tính năng
- Crawl dữ liệu theo bộ sưu tập hoặc NFT cụ thể
- Lọc và trích xuất thông tin quan trọng (giá, lịch sử giao dịch, người sở hữu)
- Hỗ trợ lưu dữ liệu dưới dạng JSON
- Có thể tùy chỉnh theo nhu cầu phân tích
# Yêu cầu
Để crawl được dữ liệu thì Java yêu cầu cần phải bổ sung thêm các thư viện hỗ trợ sau:
- JSoup để crawl dữ liệu HTML
- org.json để xử lý JSON
# Cài đặt
- clone repository sau về máy : https://github.com/Rang612/HomeWork.git
- Cài đặt thư viện Jsoup và json về máy qua link sau:
  - https://mvnrepository.com/artifact/org.json/json/20231013
  - https://mvnrepository.com/artifact/org.jsoup/jsoup/1.17.1
# Hướng dẫn sử dụng
- Mở IntelliJ IDEA. 
- Vào File > Project Structure > Modules > Dependencies.
- Nhấn dấu + > JARs or Directories.... 
- Chọn file json-20231013.jar và jsoup-1.17.1.jar vừa tải về.
- Nhấn Apply > OK
- Vào File selenium > OpenSea1DScraper.java và chọn hình tam giác màu xanh để chạy
chương trình.
- Data sẽ được lưu vào file opensea_data.json