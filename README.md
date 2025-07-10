# 📱 Android Curriculum Vitae Manager App

An Android application designed for faculty members to seamlessly **create, manage, and export professional CVs**, integrating real-time cloud updates and secure user authentication with a polished mobile-first interface.

## ✨ Key Features

- 🧾 **Dynamic CV Builder**: Add, edit, and organize personal, academic, and professional details through an intuitive interface.
- ☁️ **Firebase Integration**: Real-time sync and secure data storage using Firebase Realtime Database and Firebase Auth.
- 🔐 **User Authentication**: Secure login and registration using Firebase Authentication (Email/Password).
- 📄 **PDF Export**: One-tap professional CV generation as downloadable PDF.
- 🔗 **Profile Sharing**: Share CVs easily via email, WhatsApp, or other platforms.
- 🎨 **Material UI/UX**: Built with a responsive layout, clean design, and user-friendly navigation.
- 📊 **Modular Architecture**: Follows MVVM architecture with proper separation of concerns.

## 🛠️ Tech Stack

- **Languages**: Kotlin, Java  
- **Frameworks**: Android SDK, Jetpack (ViewModel, LiveData, Room)  
- **Backend**: Firebase (Auth, Realtime DB, Cloud Storage)  
- **PDF Generation**: iText / Android PdfDocument  
- **UI/UX**: Material Design Components  
- **Tools**: Android Studio, Git, XML, Glide/Picasso

## 🧠 Architecture

```plaintext
MVVM Pattern
├── View: Activities & Fragments
├── ViewModel: Manages UI logic and LiveData
└── Model: Firebase + Local Data Sources

📂 Folder Structure

CurriculumVitaeManager/
├── activities/
│   ├── LoginActivity.kt
│   ├── RegisterActivity.kt
│   ├── DashboardActivity.kt
│   ├── EditProfileActivity.kt
│   └── ViewCVActivity.kt
├── models/
├── adapters/
├── utils/
├── viewmodels/
├── res/
│   ├── layout/
│   ├── drawable/
│   └── values/
└── AndroidManifest.xml

🚀 How to Run
Clone the repo:
git clone https://github.com/Reethikaa05/android-cv-manager.git

Open in Android Studio

Connect Firebase (via Tools > Firebase Assistant)

Run on emulator or real device
