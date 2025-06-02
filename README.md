# D4C Final Android Assignment - OTP Login, Product Listing & Ticket Creation App

![B4C](https://raw.githubusercontent.com/yeshuwahane/d4c/refs/heads/main/screeenshots/b4c.png)

This is the final submission for the D4C Android assignment. The project demonstrates a complete flow of OTP-based user authentication, product browsing, and support ticket creation with optional image upload.

---

## 📱 Features

### ✅ OTP Authentication
- Login screen for phone number input (`+91 9899500873`)
- Send OTP via POST API
- OTP verification screen with mock OTP (`123456`)
- Secure JWT and refresh token storage using DataStore
- Navigates to Product Listing upon successful verification

### 🛍 Product Listing
- Fetches and displays a scrollable list of products using JWT-authenticated GET API
- Shows product image (and name if available)
- Graceful handling of API states (loading, error)

### 📝 Ticket Creation
- Allows authenticated users to create a support ticket
- Input: Ticket Type, Message, and optional image upload
- Submits a multipart/form-data POST request
- Shows image preview and displays success/failure messages


---

## 🛠️ Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM + Clean Architecture
- **UI**: Jetpack Compose
- **Networking**: Retrofit with Coroutine support
- **Image Handling**: Android Content Resolver + File IO
- **Dependency Injection**: Hilt
- **Data Persistence**: Jetpack DataStore
- **Navigation**: Jetpack Navigation Compose

---

## 📂 Project Structure

```bash
.
├── data/             # API service, repository, models
├── domain/           # Use cases, entities
├── presentation/     # Screens (Login, OTP, Products, Tickets)
├── di/               # Hilt modules

