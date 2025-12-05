# Hướng Dẫn Chạy Dự Án Restaurant Management System (RMS)



## Yêu Cầu Hệ Thống

### Phần mềm cần cài đặt:

| Phần mềm | Phiên bản | Link tải |
|----------|-----------|----------|
| Node.js | >= 18.x | https://nodejs.org/ |
| Java JDK | 17 | https://www.oracle.com/java/technologies/downloads/#java17 |
| Maven | >= 3.9 | https://maven.apache.org/download.cgi |
| MongoDB | Atlas hoặc Local | https://www.mongodb.com/atlas |

### Kiểm tra cài đặt:

```bash
# Kiểm tra Node.js
node -v

# Kiểm tra npm
npm -v

# Kiểm tra Java
java -version

# Kiểm tra Maven
mvn -version
```

---

## Cấu Trúc Dự Án

```
backend/                    # Spring Boot Backend
├── src/
│   └── main/
│       ├── java/com/rms/
│       │   ├── config/     # Security, CORS config
│       │   ├── controller/ # REST API endpoints
│       │   ├── dto/        # Data Transfer Objects
│       │   ├── model/      # MongoDB entities
│       │   ├── repository/ # MongoDB repositories
│       │   ├── security/   # JWT authentication
│       │   └── service/    # Business logic
│       └── resources/
│           └── application.yml
└── pom.xml
---

## Hướng Dẫn Cài Đặt

### Bước 1: Clone dự án

```bash
git clone <repository-url>
cd backend
```

### Bước 2: Cấu hình Backend

#### 2.1. Cấu hình MongoDB

Mở file `backend/src/main/resources/application.yml` và cập nhật connection string:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb+srv://<username>:<password>@<cluster>.mongodb.net/<database>
      database: rms
```

**Lưu ý**: Thay thế `<username>`, `<password>`, `<cluster>`, `<database>` bằng thông tin MongoDB của bạn.

#### 2.2. Cấu hình JWT Secret

Trong file `application.yml`, đảm bảo có JWT secret (base64 encoded, ít nhất 32 ký tự):

```yaml
jwt:
  secret: <your-base64-encoded-secret>
  expiration: 86400000        # 24 giờ
  refresh-expiration: 604800000  # 7 ngày
```

**Tạo secret key**:
```bash
# Linux/Mac
echo -n "your-secret-key-at-least-32-characters" | base64

# Windows PowerShell
[Convert]::ToBase64String([Text.Encoding]::UTF8.GetBytes("your-secret-key-at-least-32-characters"))
```

### Bước 3: Cài đặt Dependencies


#### Backend:
```bash
# Từ thư mục backend
cd backend
mvn clean install -DskipTests
```

---

## Chạy Dự Án

#### Terminal - Backend:
```bash
cd backend
mvn spring-boot:run
```
Backend sẽ chạy tại: `http://localhost:8017`

---

## API Endpoints

### Authentication

*LƯU Ý: những API cần token cần cấp token ở Authorization là Bearer Token `accessToken`

=====
USER
=====
| POST | `/api/auth/register` | Đăng ký |
ví dụ payload 
{
    "displayName": "Nguyen Van B",
    "email": "nguyenvand@gmail.com",
    "password": "Son1182004"
}

response
{
    "success": true,
    "message": "Registration successful",
    "data": {
        "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2OTMzMDIwZTE0MTVhYTZkZTlhNzhiNzAiLCJpYXQiOjE3NjQ5NTA1NDIsImV4cCI6MTc2NTAzNjk0Mn0.7mDy4Qw142yHN8ADBHzDMSxyvHGBgB7vrFjQpV5YZmNTcBtKNAbXXRNgGSkUuJ2wD3acom5_M3DdRigJlvsdnA",
        "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2OTMzMDIwZTE0MTVhYTZkZTlhNzhiNzAiLCJpYXQiOjE3NjQ5NTA1NDIsImV4cCI6MTc2NTU1NTM0Mn0.5_qUsmvjdfbL_diZ2jc5BMVoRwbZCe8PfJmXT16Jr-vaB4eEvbBZNDkhGxsLDa-Dj-pDU1H5a890CEcz5rrnpQ",
        "tokenType": "Bearer",
        "user": {
            "id": "6933020e1415aa6de9a78b70",
            "email": "nguyenvand@gmail.com",
            "displayName": "Nguyen Van B",
            "avatar": null,
            "createdAt": "2025-12-05T23:02:22.80384"
        }
    }
}


| POST | `/api/auth/login` | Đăng nhập |
ví dụ payload
{
    "email": "nguyenvanb@gmail.com",
    "password": "Son1182004"

}

response
{
    "success": true,
    "message": "Login successful",
    "data": {
        "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2OTMzMDIwZTE0MTVhYTZkZTlhNzhiNzAiLCJpYXQiOjE3NjQ5NTI2MTMsImV4cCI6MTc2NTAzOTAxM30.zE_ShKLSU6JtNTCyG-Ku5siavVeXEZlGNJO9EyB8UczH4rba-66nUTm4iIS7b3jrJj9Q-rKKhJJd3fdMY7j0YQ",
        "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2OTMzMDIwZTE0MTVhYTZkZTlhNzhiNzAiLCJpYXQiOjE3NjQ5NTI2MTMsImV4cCI6MTc2NTU1NzQxM30.fQqDZW_99-84tdqdruhuYLQN31ZIly5nU4NH5CNt5fDpHea8Q-qmT_cyZ2tzKwoQSf7VhLEMw1P0it5KxeE6PA",
        "tokenType": "Bearer",
        "user": {
            "id": "6933020e1415aa6de9a78b70",
            "email": "nguyenvand@gmail.com",
            "displayName": "Nguyen Van B",
            "avatar": null,
            "createdAt": "2025-12-05T23:02:22.803"
        }
    }
}



| POST | `/api/auth/refresh` | Refresh token |


------------------------------------------------------------------------------------------------------



=====
TABLE
=====
| POST | `/api/tables` | Tạo bàn mới (Chỉ ADMIN OR STAFF) |
ví dụ payload
{
    "name": "B4",
    "seats": 3
}

response
{
    "id": "69330a7a17364e06252ede39",
    "name": "B4",
    "seats": 3,
    "available": true
}

| GET | `/api/tables/6932f42aaa07ce07d7f5edf6` | Lấy thông tin chi tiết bàn (Chỉ ADMIN OR STAFF) |
response
{
    "id": "6932f42aaa07ce07d7f5edf6",
    "name": "A1",
    "seats": 5,
    "available": true
}

| PUT | `/api/tables/6932f42aaa07ce07d7f5edf6` | Chỉnh sửa thông tin bàn (Chỉ ADMIN OR STAFF) |
ví dụ payload
{
  "name": "Bàn VIP 1",
  "seats": 6
}
response
{
    "id": "6932f42aaa07ce07d7f5edf6",
    "name": "Bàn VIP 1",
    "seats": 6,
    "available": true
}

| DELETE | `/api/tables/6932f42aaa07ce07d7f5edf6` | Xóa bàn (Chỉ ADMIN OR STAFF) |

------------------------------------------------------------------------------------------------------



=====
DISH
=====
| POST | `/api/dishes` | Tạo món (Chỉ ADMIN OR STAFF) |
ví dụ payload
{
  "name": "Phở gà",
  "price": 35000,
  "categoryId": "6932f6c8aa07ce07d7f5edfa",
  "imageUrl": "https://abc.com/phoga.jpg"
}
response
{
    "id": "6932fd61647cbf1cf45c37d9",
    "name": "Phở gà",
    "description": null,
    "price": 35000.0,
    "categoryId": "abc123",
    "available": true,
    "imageUrl": "https://abc.com/phoga.jpg"
}

| GET | `/api/dishes/6932fb811024662f11ad577b` | Thông tin món (Chỉ ADMIN OR STAFF) |
response
{
    "id": "6932fb811024662f11ad577b",
    "name": "Cơm gà",
    "description": "Cơm gà xối mỡ1",
    "price": 45000.0,
    "categoryId": "67a2049192ccab44ed001111",
    "available": true,
    "imageUrl": /anhcomga.img
}


| PUT | `/api/dishes/6932fb811024662f11ad577b` | Sửa thông tin món (Chỉ ADMIN OR STAFF) |
ví dụ payload
{
    "name": "Cơm rang dưa bò"
    "description": "Cơm chiên cùng với dưa và thịt bò thơm ngon" 
}
response
{
    "id": "6932fb811024662f11ad577b",
    "name": "Cơm rang dưa bò",
    "description": "Cơm chiên cùng với dưa và thịt bò thơm ngon" 
    "price": 45000.0,
    "categoryId": "67a2049192ccab44ed001111",
    "available": true,
    "imageUrl": /anhcomga.img
}

| DELETE | `/api/dishes/6932fb811024662f11ad577b` | Xóa món (Chỉ ADMIN OR STAFF) |


------------------------------------------------------------------------------------------------------



=====
CATEGORY
=====
| POST | `/api/categories` | Tạo danh mục món (Chỉ ADMIN OR STAFF) |
ví dụ payload
{
  "name": "Mì"
}

response
{
    "id": "6932f6c8aa07ce07d7f5edfa",
    "name": "Mì",
    "description": null
}

| GET | `/api/categories/6932f6c8aa07ce07d7f5edfa` | Thông tin danh mục (Chỉ ADMIN OR STAFF) |
response
{
    "id": "6932f6c8aa07ce07d7f5edfa",
    "name": "Mì",
    "description": null
}

| PUT | `/api/categories/6932f6c8aa07ce07d7f5edfa` | Sửa thông tin danh mục (Chỉ ADMIN OR STAFF) |
ví dụ payload
{
    "name": "Cơm"
    "description": "Cơm" 
}
response
{
    "id": "6932f6c8aa07ce07d7f5edfa",
    "name": "Cơm",
    "description": "Cơm ngon"
}
| DELETE | `/api/categories/6932f6c8aa07ce07d7f5edfa` | Xóa danh mục (Chỉ ADMIN OR STAFF) |

------------------------------------------------------------------------------------------------------






=====
Order
=====

| POST | `/api/orders` | Tạo order món |
ví dụ payload
{
  "tableId": "6932f42aaa07ce07d7f5edf6",
  "items": [
    {
      "dishId": "6932fb4e1024662f11ad577a",
      "quantity": 2
    },
    {
      "dishId": "6932fd61647cbf1cf45c37d9",
      "quantity": 1
    }
  ]
}
response
{
    "id": "6932f6c8aa07ce07d7f5edfa",
    "name": "Mì",
    "description": null
}

| GET | `/api/orders/69330f8f17364e06252ede3a` | Thông tin order (Chỉ ADMIN OR STAFF) |
response
{
    "id": "69330f8f17364e06252ede3a",
    "tableId": "6932f42aaa07ce07d7f5edf6",
    "items": [
        {
            "dishId": "6932fb4e1024662f11ad577a",
            "quantity": 2,
            "pricePerUnit": 45000.0
        },
        {
            "dishId": "6932fd61647cbf1cf45c37d9",
            "quantity": 1,
            "pricePerUnit": 35000.0
        }
    ],
    "totalPrice": 125000.0,
    "createdAt": "2025-12-05T23:59:59.617",
    "status": "NEW"
}

| PUT | `/api/orders/69330f8f17364e06252ede3a/status` | Sửa thông tin order (Chỉ ADMIN OR STAFF) |
ví dụ payload
{
  "status": "COMPLETED"
}

response
{
    "id": "69330f8f17364e06252ede3a",
    "tableId": "6932f42aaa07ce07d7f5edf6",
    "items": [
        {
            "dishId": "6932fb4e1024662f11ad577a",
            "quantity": 2,
            "pricePerUnit": 45000.0
        },
        {
            "dishId": "6932fd61647cbf1cf45c37d9",
            "quantity": 1,
            "pricePerUnit": 35000.0
        }
    ],
    "totalPrice": 125000.0,
    "createdAt": "2025-12-05T23:59:59.617",
    "status": "COMPLETED"
}
| DELETE | `/api/orders/69330f8f17364e06252ede3a` | Xóa order (Chỉ ADMIN OR STAFF) |




---

## Xử Lý Lỗi Thường Gặp

### 1. Port đã được sử dụng

**Lỗi**: `Port 8017 is already in use`

**Giải pháp**:
```bash
# Windows - Tìm process đang dùng port
netstat -ano | findstr :8017

# Kill process
taskkill /PID <PID> /F
```

### 2. MongoDB connection failed

**Lỗi**: `MongoTimeoutException`

**Giải pháp**:
- Kiểm tra connection string trong `application.yml`
- Đảm bảo IP của bạn được whitelist trong MongoDB Atlas
- Kiểm tra username/password

### 3. CORS Error

**Lỗi**: `Access-Control-Allow-Origin`

**Giải pháp**: Đảm bảo `SecurityConfig.java` có cấu hình CORS cho `http://localhost:5173`

### 4. JWT Token Invalid

**Lỗi**: `401 Unauthorized`

**Giải pháp**:
- Xóa localStorage trong browser
- Đăng nhập lại
- Kiểm tra JWT secret trong `application.yml`

---

## Build Production

### Backend:
```bash
cd backend
mvn clean package -DskipTests
```
Output: `backend/target/rms-0.0.1-SNAPSHOT.jar`

### Chạy JAR file:
```bash
java -jar backend/target/rms-0.0.1-SNAPSHOT.jar
```

---

## Công Nghệ Sử Dụng

### Backend
- **Spring Boot 3.2** - Framework
- **Spring Security** - Authentication
- **Spring Data MongoDB** - Database
- **JWT** - Token authentication
- **Lombok** - Code generation

