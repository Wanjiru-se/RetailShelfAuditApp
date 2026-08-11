# Retail Shelf Audit & Product Visibility Analysis System

An Android application that uses Artificial Intelligence (YOLOv8) to automate retail shelf audits by detecting products, counting facings, identifying empty shelf spaces, and generating audit reports.

## Project Overview

Retail shelf audits are commonly performed manually by merchandisers, making the process time-consuming, repetitive, and prone to human error. This project aims to simplify the auditing process by allowing users to capture or upload shelf images and automatically analyze product placement using computer vision.

The application is being developed as a Software Engineering Project using Android Studio (Kotlin) and YOLOv8 object detection.

---

## Features

### Current Features

- Capture shelf images using the device camera
- Upload shelf images from the gallery
- Preview captured or selected images
- Navigate through the complete audit workflow
- Generate audit reports (prototype)
- Save reports locally using SQLite

### Planned AI Features

- Detect Coca-Cola products on retail shelves
- Count product facings automatically
- Identify empty shelf spaces
- Display detection results with bounding boxes
- Generate AI-powered audit reports
- Store historical audit reports

---

## Technology Stack

### Mobile Development

- Kotlin
- Android Studio
- View Binding
- SQLite

### Artificial Intelligence

- YOLOv8
- Roboflow
- Google Colab (planned)
- TensorFlow Lite (planned)

### Version Control

- Git
- GitHub

---

## Application Workflow

```text
Home Screen
     │
     ├── Capture Image
     │        │
     │        ▼
     │   Camera
     │        │
     ├────────┘
     │
     ├── Upload Image
     │        │
     │        ▼
     │     Gallery
     │
     ▼
Image Preview
     │
     ├── Retake
     │
     └── Analyze Image
               │
               ▼
      Analysis Results
               │
               ▼
        Audit Report
               │
               ▼
         Save Report
```

---

## Project Structure

```
app/
 ├── activities/
 │      ├── MainActivity
 │      ├── HomeActivity
 │      ├── ImagePreviewActivity
 │      ├── AnalysisResultsActivity
 │      ├── AuditReportActivity
 │      └── ReportsActivity
 │
 ├── data/
 │
 ├── utils/
 │
 ├── res/
 │      ├── layout/
 │      ├── drawable/
 │      ├── values/
 │      └── mipmap/
 │
 └── AndroidManifest.xml
```

---

## Current Progress

### Sprint 1 – Prototype

- Home Screen
- Image Preview
- Analysis Results
- Audit Report
- SQLite integration
- Navigation between screens

### Sprint 2 – Image Acquisition

- Camera integration
- Runtime camera permission
- Gallery image selection
- Image preview
- Capture and upload testing
- Retake functionality

### Sprint 3 – AI Integration (In Progress)

- Dataset preparation
- YOLOv8 training
- Model integration
- TensorFlow Lite conversion
- Real-time object detection

---

## Installation

1. Clone the repository

```bash
git clone https://github.com/Wanjiru-se/RetailShelfAuditApp.git
```

2. Open the project in Android Studio.

3. Sync Gradle.

4. Run the application on an Android emulator or physical device.

---

## Future Improvements

- Real-time object detection
- Offline AI inference
- Cloud report synchronization
- Dashboard analytics
- Multiple product categories
- User authentication
- Export reports as PDF

---

## Author

**Annlucy Wanjiru**

Bachelor of Science in Information Technology

USIU-Africa

---

## License

This project is developed for educational purposes as part of the Software Engineering Project course at USIU-Africa.
