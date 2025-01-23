# Belajar-RESTful-API

### **Deskripsi Project**
jadi project ini adalah sistem coontact management yang pengguna untuk mengelola kontak dan alamat.semua datanya bisa disimpen di sini! Sistem ini buat pakai **Spring Boot**. project ini memiliki fitur 
1. **User Management**: Mengelola data pengguna (registrasi, login, update, dll).
2. **Contact Management**: Mengelola data kontak (nama, email, telepon, dll).
3. **Address Management**: Mengelola alamat yang terkait dengan kontak.


**Dependensi**:
Spring Boot: Framework buat bikin aplikasi Java yang cepat dan efisien.
Spring Data JPA: Buat ngelola database dengan mudah.
PostgreSQL: Database buat nyimpen semua data.
Spring Security: Buat ngatur keamanan aplikasi, termasuk login dan JWT.
JWT (JSON Web Token): Buat autentikasi, biar aplikasi lebih aman.
Lombok: Biar nggak perlu nulis getter-setter manual, lebih hemat waktu.
Validation: Buat mastiin data yang diinput bener, misalnya email harus valid.
Unit Testing: Buat ngetes kode biar nggak ada bug.

**Struktur Project**
D:\Restful-Api-Application
├── RestfulApiApplication.java          // Main class buat jalanin aplikasi
├── security                            // Folder buat ngatur keamanan
│    ├── JWTAuthenticationFilter.java   // Filter buat ngecek JWT
│    ├── JWTUtil.java                   // Utility buat generate dan validasi JWT
│    └── SecurityConfig.java            // Konfigurasi Spring Security
├── controller                          // Folder buat API endpoint
│   ├── UserController.java             // Endpoint buat user (register, login, dll)
│   ├── ContactController.java          // Endpoint buat kontak
│   └── AddressController.java          // Endpoint buat alamat
├── model                               // Folder buat entitas data
│   ├── User.java                       // Model buat data user
│   ├── Contact.java                    // Model buat data kontak
│   ├── AuthenticationResponse.java     // Response buat login (JWT)
│   ├── AuthenticationRequest.java      // Request buat login (username & password)
│   └── Address.java                    // Model buat data alamat
├── repository                          // Folder buat ngakses database
│   ├── UserRepository.java             // Repository buat user
│   ├── ContactRepository.java          // Repository buat kontak
│   └── AddressRepository.java          // Repository buat alamat
├── service                             // Folder buat logika bisnis
│   ├── UserService.java                // Service buat user
│   ├── ContactService.java             // Service buat kontak
│   └── AddressService.java             // Service buat alamat
├── util                                // Folder buat utility
│   └── SecretKeyGenerator.java         // Utility buat generate secret key JWT
└── test                                // Folder buat unit test
    ├── com.example.belajarRestApi       
    │   ├── service                     // Folder buat unit test service
    │   │   └── UserServiceTest.java    // Unit test buat UserService
    └── BelajarRestApplicationTests.java // Unit test utama

**Database**
- **Nama Database**: `belajar-restful-api`
  
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL
);


CREATE TABLE contacts (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    user_id INT REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE address (
    id SERIAL PRIMARY KEY,
    street VARCHAR(255),
    city VARCHAR(100),
    province VARCHAR(100),users
    country VARCHAR(100),
    postal_code VARCHAR(20),
    contact_id INT REFERENCES contacts(id) ON DELETE CASCADE
);

### **Dependensi yang Dipake**
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'io.jsonwebtoken:jjwt-api:0.11.5' // Buat JWT
    compileOnly 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    runtimeOnly 'com.h2database:h2' // Buat testing
    runtimeOnly 'org.postgresql:postgresql' // Database PostgreSQL
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

### **Requirement dan Fitur**
project ini pubya fitur utama:

#### **1. User Management**
- **Data User**:
  - `username`: Nama pengguna (unik).
  - `password`: Kata sandi pengguna.
  - `nama`: Peran pengguna.

- **API User**:
  - **Register User**: Mendaftarkan pengguna baru.
  - **Login User**: Memungkinkan pengguna untuk login.
  - **Update User**: Memperbarui informasi pengguna.
  - **Get User**: Mengambil informasi pengguna.
  - **Logout User**: Memungkinkan pengguna untuk logout.

#### **2. Contact Management**
- **Data Kontak**:
  - `firstName`: Nama depan kontak.
  - `lastName`: Nama belakang kontak.
  - `email`: Alamat email kontak.
  - `phone`: Nomor telepon kontak.

- **API Kontak**:
  - **Create Contact**: Menambahkan kontak baru.
  - **Update Contact**: Memperbarui kontak berdasarkan ID.
  - **Get Contact**: Mengambil kontak berdasarkan ID.
  - **Remove Contact**: Menghapus kontak berdasarkan ID.

#### **3. Address Management**
- **Data Alamat**:
  - `street`: Nama jalan.
  - `city`: Kota.
  - `province`: Provinsi.
  - `country`: Negara.
  - `postalCode`: Kode pos.

- **API Alamat**:
  - **Create Address**: Menambahkan alamat baru.
  - **Update Address**: Memperbarui alamat berdasarkan ID.
  - **Get Address**: Mengambil alamat berdasarkan ID.
  - **List Address**: Menampilkan daftar alamat.
  - **Remove Address**: Menghapus alamat berdasarkan ID.

### **Endpoint API**
Berikut adalah endpoint API yang tersedia dalam proyek ini:

#### **User API**
1. **Register User**:
   - **Method**: POST
   - **Endpoint**: `/api/users/register`
   - **Fungsi**: Mendaftarkan pengguna baru.

2. **Login User**:
   - **Method**: POST
   - **Endpoint**: `/api/users/login`
   - **Fungsi**: Memungkinkan pengguna untuk login.

3. **Update User**:
   - **Method**: PUT
   - **Endpoint**: `/api/users/{id}`
   - **Fungsi**: Memperbarui informasi pengguna.

4. **Get User**:
   - **Method**: GET
   - **Endpoint**: `/api/users/{id}`
   - **Fungsi**: Mengambil informasi pengguna.

5. **Logout User**:
   - **Method**: POST
   - **Endpoint**: `/api/users/logout`
   - **Fungsi**: Memungkinkan pengguna untuk logout.

#### **Contact API**
1. **Get All Contacts**:
   - **Method**: GET
   - **Endpoint**: `/api/contacts`
   - **Fungsi**: Mengambil semua kontak.

2. **Get Contact by ID**:
   - **Method**: GET
   - **Endpoint**: `/api/contacts/{id}`
   - **Fungsi**: Mengambil kontak berdasarkan ID.

3. **Create Contact**:
   - **Method**: POST
   - **Endpoint**: `/api/contacts`
   - **Fungsi**: Menambahkan kontak baru.

4. **Update Contact**:
   - **Method**: PUT
   - **Endpoint**: `/api/contacts/{id}`
   - **Fungsi**: Memperbarui kontak berdasarkan ID.

5. **Delete Contact**:
   - **Method**: DELETE
   - **Endpoint**: `/api/contacts/{id}`
   - **Fungsi**: Menghapus kontak berdasarkan ID.

#### **Address API**
1. **Create Address**:
   - **Method**: POST
   - **Endpoint**: `/api/contacts/{contactId}/addresses`
   - **Fungsi**: Menambahkan alamat baru untuk kontak tertentu.

2. **Update Address**:
   - **Method**: PUT
   - **Endpoint**: `/api/contacts/{contactId}/addresses/{addressId}`
   - **Fungsi**: Memperbarui alamat berdasarkan ID.

3. **Get Address**:
   - **Method**: GET
   - **Endpoint**: `/api/contacts/{contactId}/addresses/{addressId}`
   - **Fungsi**: Mengambil alamat berdasarkan ID.

4. **List Addresses**:
   - **Method**: GET
   - **Endpoint**: `/api/contacts/{contactId}/addresses`
   - **Fungsi**: Menampilkan daftar alamat untuk kontak tertentu.

5. **Remove Address**:
   - **Method**: DELETE
   - **Endpoint**: `/api/contacts/{contactId}/addresses/{addressId}`
   - **Fungsi**: Menghapus alamat berdasarkan ID.
   - 


### **Alur Kerja**
1. **Registrasi dan Login**:
   - Pengguna mendaftar dan login menggunakan endpoint `/api/users/register` dan `/api/users/login`.
2. **Mengelola Kontak**:
   - Setelah login, pengguna dapat menambahkan, memperbarui, melihat, atau menghapus kontak menggunakan endpoint `/api/contacts`.
3. **Mengelola Alamat**:
   - Pengguna juga dapat menambahkan, memperbarui, melihat, atau menghapus alamat yang terkait dengan kontak menggunakan endpoint `/api/contacts/{contactId}/addresses`.



# **Step By Step Buat Unit Test**
1. Buat application-test.properties

2. file ini isinya konfigurasi buat testing, pake database H2 (in-memory database) biar nggak ganggu database utama (PostgreSQL). Ini isi filenya:

# Test Profile Configuration
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA Configuration for Testing
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

3.Nambahin Dependensi untuk Unit Test
// Dependensi buat testing
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
    
4. Nulis Unit Test

package com.example.belajarRestApi.service;

import com.example.belajarRestApi.model.User;
import com.example.belajarRestApi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");
        mockUser.setPassword("password");
        mockUser.setName("Test User");
    }

    // Test methods...
}

   
