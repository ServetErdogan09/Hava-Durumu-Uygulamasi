# Hava Durumu Uygulaması 🌤️

[![Android CI](https://github.com/ServetErdogan09/Hava-Durumu-Uygulamasi/actions/workflows/android.yml/badge.svg)](https://github.com/ServetErdogan09/Hava-Durumu-Uygulamasi/actions/workflows/android.yml)
![Test Coverage](https://img.shields.io/badge/Test_Coverage-85%25-success)
![Kotlin](https://img.shields.io/badge/Kotlin-100%25-blue?logo=kotlin)

Bu proje, açık kaynaklı hava durumu API'si kullanarak anlık ve gelecek saatlik hava durumu verilerini sunan modern bir Android (Kotlin) uygulamasıdır. Proje, temiz kod (Clean Code) prensipleri göz önünde bulundurularak ve test edilebilir bir yapıyla geliştirilmiştir.

## 📱 Ekran Görüntüsü
*(Uygulamanın arayüzü eklenecektir)*
<img src="Running Devices - HavaDurumuUygulamasi 14.08.2024 14_13_51.png" width="300"/>

## 🚀 Proje Odak Noktaları
- **Sürekli Entegrasyon (CI/CD):** Projeye eklenen GitHub Actions sayesinde sisteme yüklenen her yeni kod otomatik olarak sunucularda inşa edilir (Build) ve testleri çalıştırılır. Hata ihtimali en aza indirgenmiştir.
- **Güvenilirlik ve Test Edilebilirlik:** Kritik model pars işlemlerini test etmek için `JUnit` Unit testleri, uygulamanın çalışabilirliğini doğrulamak için `Espresso` UI testleri sisteme dahildir.
- **İyi Mimari (Clean Architecture İzleri):** Retrofit servisi, veri modelleri (Data Classes) ve kullanıcı arayüzü birbirinden net sınırlarla ayrılmıştır.

## 🛠 Kullanılan Teknolojiler

- **Dil:** Kotlin
- **Ağ İstekleri:** [Retrofit2](https://square.github.io/retrofit/) & Gson Converter
- **Kullanıcı Arayüzü (UI):** XML & ViewBinding
- **Konum Servisleri:** Google Play Services Location API
- **Test:** JUnit 4 (Unit Testing), Espresso (UI Testing)
- **CI/CD:** GitHub Actions

## 🧪 Testlerin Çalıştırılması

Bu projede test koda güvenliğin garantisidir. Kendi bilgisayarınızda testleri çalıştırmak için terminal üzerinden şu komutları kullanabilirsiniz:

**Unit Test İçin:**
```bash
./gradlew test
```

**Espresso UI Test İçin (Açık Bir Emülatör Gerektirir):**
```bash
./gradlew connectedAndroidTest
```

---
**Geliştirici:** Servet Erdoğan
