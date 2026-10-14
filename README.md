# Retail Shelf Audit & Product Visibility Analysis System

An Android application that uses Artificial Intelligence (YOLOv8) to automate retail shelf audits by detecting products, counting facings, identifying empty shelf spaces, and generating audit reports.

## Project Overview

Retail shelf audits are commonly performed manually by merchandisers, making the process time-consuming, repetitive, and prone to human error. This project aims to simplify the auditing process by allowing users to capture or upload shelf images and automatically analyze product placement using computer vision.

---

## Features

### Current Features

- Capture shelf images using the device camera
- Upload shelf images from the gallery
- Preview captured or selected images
- Navigate through the complete audit workflow
- Generate audit reports (prototype)
- Save reports locally using SQLite
- Detect Coca-Cola products on retail shelves
- Count product facings automatically
- Display detection results with bounding boxes
- Generate AI-powered audit reports
- Store historical audit reports

### Planned AI Features

- Identify empty shelf spaces

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
- Google Colab 
- TensorFlow Lite 

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
## Screenshots

<img width="1919" height="1079" alt="Screenshot 2026-08-14 125555" src="https://github.com/user-attachments/assets/19e1cf38-bb44-41bf-b66a-6eb5357bc6a6" />

<img width="1916" height="1079" alt="Screenshot 2026-08-14 125708" src="https://github.com/user-attachments/assets/77ae374a-d3b8-4546-a488-7a3db699fbb7" />

<img width="1919" height="1079" alt="Screenshot 2026-08-14 125730" src="https://github.com/user-attachments/assets/7492eb82-0974-4c0c-826d-03dda59677d9" />

<img width="1919" height="1079" alt="Screenshot 2026-08-14 125759" src="https://github.com/user-attachments/assets/793df5bd-40a5-46fd-a032-0e973c0e8927" />

<img width="1919" height="1079" alt="Screenshot 2026-08-14 125826" src="https://github.com/user-attachments/assets/3236ded7-0bb5-4c74-95b5-4808d672c96c" />

<img width="1919" height="1079" alt="Screenshot 2026-08-14 125843" src="https://github.com/user-attachments/assets/e64e054f-226c-4f8f-b147-255587da35f2" />

<img width="1919" height="1079" alt="Screenshot 2026-08-14 125858" src="https://github.com/user-attachments/assets/da1c8baf-b419-413d-8876-545feeeda9cc" />

<img width="1919" height="1079" alt="Screenshot 2026-08-14 130228" src="https://github.com/user-attachments/assets/008211ca-1574-4050-a1a5-70e6195fc6c2" />


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
- User authentication
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

### Sprint 3 – AI Integration 

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

- Offline AI inference
- Cloud report synchronization
- Dashboard analytics
- Multiple product categories
- Export reports as PDF

---


---

