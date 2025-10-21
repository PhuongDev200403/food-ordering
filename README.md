# Food ordering system

## Author
**Nguyễn Văn Phương**
phuongnguyen20403@gmail.com

## Giới thiệu
**Food ordering system** được phát triển ằng **Spring Boot** nhằm mô phỏng hệ thóng bán hàng online giúp customer có được trải nghiệm mua đồ ăn một cách tiện lợi, nhanh chóng. Ngoài ra hệ thống còn giúp **Restaurant owner** quản lý được nhà hàng của bản thân.

## Tính năng chính
- **Admin**
  + Quản lý đơn hàng ( Cập nhật trạng thái đơn hàng, xem tất cả, xem đơn hàng theo nhà hàng)
  + Quản lý món ăn ( Thêm mới, xóa và cập nhật trạng thái, xem chi tiết, xem tất cả)
  + Quản lý thành phần món ăn (Thêm danh muc thành phần, thêm thành phần, cập nhật trạng thái của thành phần của món ăn trong kho, xem danh mục thành phần theo nhà hàng)
  + Quản lý giỏ hàng ( Lấy danh sách giỏ hàng, xem chi tiết giỏ hàng)
  + Quản lý người dùng (Thêm, sửa, xóa )
  + Quản lý nhà hàng ( Thêm,sửa, xóa )
  + Quản lý danh mục món ăn ( Thêm , xóa danh mục, xem danh mục, xem chi tiết)
- **Restaurant owner**
  + Quản lý đơn hàng của cửa hàng mình ( Cập nhật trạng thái đơn hàng, xem tất cả, xem đơn hàng)
  + Quản lý món ăn của ửa hàng mình ( Thêm mới, xóa và cập nhật trạng thái, xem chi tiết, xem tất cả)
  + Quản lý thành phần món ăn của cửa hàng mình (Thêm danh muc thành phần, thêm thành phần, cập nhật trạng thái của thành phần của món ăn trong kho, xem danh mục thành phần theo nhà hàng)
  + Quản lý giỏ hàng của cửa hàng mình ( Lấy danh sách giỏ hàng, xem chi tiết giỏ hàng)
  + Quản lý nhà hàng của mình đang sỏ hữu ( Thêm,sửa, xóa, cập nhật trạng thái ( open or close) )
  + Quản lý danh mục món ăn của cửa hàng mình( Thêm , xóa danh mục, xem danh mục, xem chi tiết)
- **Customer**
  + Đăng nhập, đăng ký
  + Thêm sản phẩm vào giỏ hàng, cập nhật số lượng trong giỏ hàng, clear giỏ hàng, Xem giỏ hàng của bản thân
  + Xem chi tiết sản phẩm,tìm kiếm sản phẩm, Xem món ăn thao nhà hàng
  + Đặt hàng, xem lịch sử đặt hàng
  + Xem danh sách nhà hàng, xem chi tiết nhà hàng
  + Xem danh mục món ăn, tìm kiếm danh mục

## Kiến thức và công nghệ sử dụng 
| Công nghệ | Mô tả | 
|------------|-------| 
| **Java** | Ngôn ngữ chính | 
| **Spring Boot 3** | Framework backend | 
| **Spring Security + JWT** | Xác thực và phân quyền | 
| **Spring Data JPA** | Tương tác cơ sở dữ liệu | 
| **MySQL** | Hệ quản trị cơ sở dữ liệu | 
| **Postman** | Kiểm thử API | 

## ER Diagram
<img width="1415" height="1096" alt="image" src="https://github.com/user-attachments/assets/6b6acdd5-89d0-4117-9732-578447402e02" />

## Ví dụ Api
#### Đăng nhập
POST /api/auth/login
**Request body**
```json
{
    "email":"quyetdk@gmail.com",
    "password":"123456789"
}
```
**Response**
```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3NjA3NzU3MzQsImV4cCI6MTc2MDg2MjEzNCwiZW1haWwiOiJxdXlldGRrQGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjoiUk9MRV9BRE1JTiJ9.djtBeIZJbG-Jg7C0sm-OLSQZXOCh6geHs8CcHzc_O3wk4INmU69BhCf1i2wXlBf737oUFYBMw0PXy6EwiOrn3w",
    "message": "Login successfully",
    "role": "ROLE_ADMIN"
}
```
#### Lấy tất cả User hiện tại có
GET /api/users
```json
[
    {
        "id": 1,
        "fullName": "Nguyễn Văn Phương",
        "password": "$2a$10$.eiQOdCHv0sBo/3H/dz7d.mbYNCJU/2BSTPKr91SBYZevBi51FDSO",
        "email": "phuongnguyen20403@gmail.com",
        "role": "ROLE_RESTAURANT_OWNER",
        "favorites": [],
        "addresses": []
    },
    {
        "id": 2,
        "fullName": "Nguyễn Văn Quyết",
        "password": "$2a$10$cir7iq7E6X7guvqv24gNQOdt1ONUUSRwBt1p0KKeOZ.r6dTZVQqJu",
        "email": "quyetnv@gmail.com",
        "role": "ROLE_RESTAURANT_OWNER",
        "favorites": [],
        "addresses": []
    },
    {
        "id": 3,
        "fullName": "Nguyễn Văn Phương",
        "password": "$2a$10$rOVumatPhhOzXFwTAPCs3un3ar3ui9i5tXqB8e4BBeIqnpTFPSIlS",
        "email": "phuongnguyen204@gmail.com",
        "role": "ROLE_RESTAURANT_OWNER",
        "favorites": [
            {
                "title": "Vietnamese food",
                "images": [],
                "description": null,
                "id": 1
            },
            {
                "title": "Vietnamese food",
                "images": [],
                "description": null,
                "id": 1
            }
        ],
        "addresses": []
    },
    {
        "id": 4,
        "fullName": "Nguyễn Khánh Ngọc",
        "password": "$2a$10$WqCAPDroF7CTou32VlTglurWdx7jZbywLgiOQjJw45VwcOfpCRp3C",
        "email": "phuongnv@gmail.com",
        "role": "ROLE_ADMIN",
        "favorites": [],
        "addresses": []
    },
    {
        "id": 5,
        "fullName": "Bùi Xuân Trưởng",
        "password": "$2a$10$jiHFS0M2O2cyy1KBkZaKU.OHvNS5gNaT6v.XqpPckQAV1cqnjQdF2",
        "email": "Bxtsama@gmail.com",
        "role": "ROLE_CUSTOMER",
        "favorites": [],
        "addresses": []
    },
    {
        "id": 7,
        "fullName": null,
        "password": "$2a$10$tMApu2kuvZ/3mvJptFSRHOF.3IFyn3LPU1GDkDUUJW7kireFWb4mG",
        "email": "quyetdk@gmail.com",
        "role": "ROLE_ADMIN",
        "favorites": [],
        "addresses": []
    }
]
```
## Deployement
**Local**
```bash
mvn clean package
java -jar target/nguyenvanphuong-0.0.1-SNAPSHOT.jar
```


