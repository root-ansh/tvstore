# 📺 TV Store – Android TV App (Kotlin + MVVM + Jetpack Compose)

A modern Android TV application built using **Kotlin**, **Jetpack Compose**, **MVVM**, and **ExoPlayer**, designed to display a categorized movie list from a mock API, allowing users to browse, view details, and play videos with full seek functionality.

---

## 🏗️ Architecture

**MVVM (Model-View-ViewModel)** architecture is used for clean separation of concerns:
```
├── Model      -> Data classes (MovieModel), API interfaces
├── Repository -> Data abstraction from API
├── UseCase    -> Business logic layer
├── ViewModel  -> UI-related logic and state
├── UI         -> Jetpack Compose TV UI with navigation
```

Supporting libraries include:
- **Hilt** for dependency injection
- **Retrofit** for networking
- **ExoPlayer** for video playback
- **Jetpack Compose TV** for building the TV-optimized UI

---

## 🚀 Features Implemented

✅ Fetch movie categories from a mock API  
✅ Home screen with multi-style movie tiles (carousel, grid, etc.)  
✅ Video player screen using **ExoPlayer** with seek functionality  
✅ Fully navigable with D-Pad/remote control  
✅ TV-optimized layout using Compose  
✅ Fallback for empty or invalid data  
✅ Modular code with proper layering  
✅ Unit tests for `UseCase` and `Repository` layers

---

## 🧪 Testing

Tests are written using `junit`, `mockito-kotlin`, and `kotlinx.coroutines-test`.  
Tested components:
- ✅ `MovieDataUseCase`
- ✅ `MovieDataRepoImpl`
- ✅ Edge cases and exception handling

---

## 🔗 Important Files & URLs

- Backend JSON : [https://github.com/root-ansh/misc_test_server/tree/master/ott](https://github.com/root-ansh/misc_test_server/tree/master/ott)

---

## 🎥 Demo Video

📽️ [Watch the demo here](#)  
(*Upload a short demo video to Google Drive, YouTube, or Loom and link it here.*)

---

## 📦 How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/tv-store-app.git
   ```

2. Open in **Android Studio Hedgehog or later**

3. Run the app on an **Android TV emulator** or **real TV device**

4. Navigate with the **D-Pad/Remote**

---

## 📝 Submission Checklist

- ✅ GitHub repo shared with full source code
- ✅ Readme with architecture, setup & test coverage
- ✅ Video demo included
- ✅ All features implemented and tested on Android TV

---

## 📱 Screens (Add screenshots or screen recordings)

| Home Screen | Player Screen |
|-------------|----------------|
| ![Home](screenshots/home.png) | ![Player](screenshots/player.png) |

---

## 💡 Future Improvements

- Add search & filtering
- Persist playback history
- Use paging for large video lists
- Integrate real streaming APIs (YouTube, etc.)

---

## 🛠️ Tech Stack

| Tool            | Purpose                        |
|-----------------|---------------------------------|
| Kotlin          | Programming Language            |
| Jetpack Compose | UI for Android TV               |
| Retrofit        | REST API client                 |
| Hilt            | Dependency Injection            |
| ExoPlayer       | Video Playback                  |
| Coroutines      | Async code                      |
| JUnit + Mockito | Unit Testing                    |

---

## 📧 Contact

Have questions or feedback? Reach out at:  
📧 `your.email@example.com`  
🔗 [LinkedIn Profile](#)
