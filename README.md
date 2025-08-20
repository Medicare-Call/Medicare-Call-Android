# MediCareCall Android
<p align="center">
  <img width="3840" height="2160" alt="image" src="https://github.com/user-attachments/assets/431df588-c2ad-48d4-bf39-462c14475114" />
 </p>


<p align="center">
  <img src="https://img.shields.io/badge/platform-Android-green.svg" alt="Platform Android"/>
  <img src="https://img.shields.io/badge/Kotlin-100%25-blueviolet.svg" alt="Kotlin"/>
  <img src="https://img.shields.io/badge/version-0.1.0-blue.svg" alt="Version"/>
</p>

## 🚀 개요 (Overview)

Medicare-Call-Android 프로젝트는 보호자용 케어콜 앱의 핵심 플로우를 제공합니다. (회원가입/로그인 → 결제 등록 → 어르신 등록 → 홈/통계/설정)

## 🌟 주요 기능 (Features)

*   📞 **로그인 & 어르신 정보 등록**: 전화번호 기반 로그인 및 어르신 정보 관리 (최대 5명)
*   💳 **결제 등록**: WebView 연동 기반 Naver Pay 결제 기능
*   🏠 **홈 화면**: 콜 스케줄, 상태 확인, 알림 기능
*   📊 **주간 통계**: 콜 수행률, 건강 지표 등 주간 단위 통계 제공
*   ⚙️ **설정**: 알림, 계정, 보안 관련 설정
*   ➕ 기타: 상세 화면 (식사, 복약, 수면, 건강, 심리, 혈당), 날짜별 데이터 조회, 혈당 그래프 (스크롤 및 상세 정보) 등

## 🛠️ 기술 스택 (Tech Stack & Libraries)

*   **언어 (Language)**: [Kotlin](https://kotlinlang.org/)
*   **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
*   **아키텍처 (Architecture)**: MVVM (Model-View-ViewModel)
*   **비동기 처리 (Asynchronous Programming)**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
*   **의존성 주입 (Dependency Injection)**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
*   **네트워킹 (Networking)**: [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/)
*   **데이터 저장 (Data Persistence)**: [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
*   **네비게이션 (Navigation)**: [Navigation Component](https://developer.android.com/guide/navigation) (Compose)
*   **기타 라이브러리 (Other Libraries)**:
    *   [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization): Kotlin 직렬화/역직렬화 (JSON 처리)
    *   [Accompanist WebView](https://google.github.io/accompanist/webview/): Jetpack Compose용 WebView

## 📁 프로젝트 구조 (Project Structure)

MediCareCall 프로젝트는 다음과 같은 주요 모듈 및 패키지로 구성되어 있습니다:

*   **`app`**: 메인 애플리케이션 모듈
    *   **`src/main/java/com/konkuk/medicarecall`**: 애플리케이션의 주요 소스 코드 디렉토리
        *   **`App.kt`**: Application 클래스 (Hilt 설정 등)
        *   **`MainActivity.kt`**: 메인 액티비티
        *   **`data`**: 데이터 계층
            *   `AppModule.kt`: Hilt 의존성 주입 모듈 (Data 관련)
            *   `api`: Retrofit 서비스 인터페이스
            *   `dto`: Data Transfer Objects (Request/Response 모델)
            *   `mapper`: 데이터 모델 간 변환 로직
            *   `network`: 네트워크 통신 관련 유틸리티
            *   `repository`: 데이터 저장소 인터페이스 및 구현체
        *   **`domain`**: 도메인 계층 (비즈니스 로직)
            *   `usecase`: 유스케이스 (애플리케이션 비즈니스 로직)
        *   **`navigation`**: Jetpack Compose Navigation 관련
        *   **`ui`**: UI 계층 (Jetpack Compose)
            *   `alarm`: 알림 관련 UI
            *   `calendar`: 캘린더 관련 UI
            *   `component`: 공통 UI 컴포넌트
            *   `home`: 홈 화면 관련 UI
            *   `homedetail`: 홈 상세 화면 관련 UI
            *   `login`: 로그인 관련 UI
            *   `model`: UI 상태 및 모델
            *   `settings`: 설정 관련 UI
            *   `splash`: 스플래시 화면 UI
            *   `statistics`: 통계 화면 관련 UI
            *   `theme`: 앱 테마 (Color, Shape, Typography)
            *   `util`: UI 관련 유틸리티

## 🔧 빌드 및 실행 (Getting Started)

### 사전 준비 (Prerequisites)

*   Android Studio (최신 버전 권장 - Narwhal 또는 이후 버전)
*   JDK 17 or higher

### 설치 및 실행 (Installation & Running)

1.  **Clone the repository:**
    
    `git clone https://github.com/Medicare-Call/Medicare-Call-Android.git`
    
2.  **Open in Android Studio:**
    *   Android Studio를 실행합니다.
    *   "Open an Existing Project"를 선택하고 클론한 저장소의 `Medicare-Call-Frontend` 폴더를 엽니다.
3.  **Sync Gradle:**
    *   Gradle 동기화가 자동으로 시작됩니다. 그렇지 않다면, 툴바에서 "Sync Project with Gradle Files" (코끼리 아이콘)을 클릭합니다.
4.  **Run the app:**
    *   원하는 에뮬레이터 또는 실제 기기를 선택합니다.
    *   툴바에서 "Run 'app'" (초록색 재생 버튼)을 클릭합니다.

## 📝 참고 사항 (Notes from original README)

*   어르신 등록은 5명까지 가능합니다.
*   알림 부분은 원래 기획부터 일단 피그마 형식대로 UI 구현이 목표였습니다.
*   혈당그래프나 홈, 통계 화면 부분 등 API 관련해서 데이터를 서버에서 받아오는 시간이 좀 소요될 수 있습니다. (평가 시 참고)
*   네이버 페이시 화면이 꽉차게 보이지 않는 곳이 있을 수 있는데, 이 부분의 경우 네이버 페이쪽의 문제로 확인됐습니다.
*   네이버 페이의 결제의 경우 PM님의 네이버 페이 계정으로 결제하시면 됩니다.
*   케어콜 기능을 확인해보고 싶으시면 홈화면 하단의 케어콜 걸기 버튼을 눌러 기능 확인이 가능합니다.(이 때, 어르신의 전화번호가 본인의 번호로 돼있어야 합니다. 만약 타인의 번호로 등록했을 경우, 설정의 어르신 개인정보 설정 부분에서 수정해주세요)
*   홈 진입 시, 등록된 첫 번째 어르신이 자동 선택되며, 네임드롭(상단 드롭다운)을 통해 어르신을 변경하면 모든 화면이 해당 어르신 기준 데이터로 전환됩니다.
*   식사, 복약, 수면, 건강, 심리, 혈당 카드를 누르면 각 상세화면으로 이동합니다.
*   각 상세화면 상단의 연/월 셀렉터를 누르면 달력 모달(DatePickerDialog)이 뜨며, 날짜를 선택하면 그 주의 주간 달력이 자동 업데이트됩니다.
*   뒤로가기 후 다시 진입하면 오늘 날짜 기준으로 초기화됩니다.
*   혈당 상세화면의 그래프의 경우, 7개 이상의 데이터가 있을 경우 좌우 스크롤이 가능하며, 과거 데이터를 보고 싶을 경우 왼쪽으로 드래그하여 조회할 수 있습니다.
*   그래프의 점을 탭하면 해당 날짜의 혈당값 리스트와 상태가 하단에 강조 표시됩니다.
*   케어콜 완료 후, 홈 / 상세 / 주간통계 화면 진입 시 최대 3초 내외의 로딩 시간이 발생할 수 있습니다. (어르신 변경 후 다른 화면으로 진입 시에도 동일하게 로딩이 발생합니다.)
*   당일 기록이 없을 경우, 각 화면에 "미기록" 상태 안내 UI가 노출됩니다.
*   각 상세화면에서 뒤로가기를 누르면 하단 탭 네비게이션이 다시 표시됩니다.
*   홈화면 진입후, 하단의 홈, 통계, 설정 화면에 진입 후 뒤로가기를 누르면 앱이 종료되게 됩니다.
*   어르신 건강정보 등록, 어르신 케어콜 시간 설정, 결제하기 부분에서 앱을 종료 후 다시 실행하게 되면 해당 부분부터 앱이 진행되게 됩니다.
*   케어콜의 경우, 기본 시간은 1차는 오전 9시, 2차는 오후 12시, 3차는 5시로 초깃값이 정해져있습니다.(1차 시간 → 오전 12시 ~ 오전 11시 50분, 2차 시간 → 오후 12시 ~ 오후 4시 50분, 3차 시간 → 오후 5시 ~ 오후 11시 50분)
*   로그인 시 문자 인증을 바탕으로 로그인이 진행됩니다.
*   로그인 과정에서 앱 종료 후 다시 시작할 경우, 진행상황에 맞게 적절한 화면으로 이동합니다.

## 🌳 브랜치 전략 (Branch Strategy)

```main               ← 최종 배포용
 └─ develop        ← 개발 통합 브랜치
    ├─ feat/login-info         ← 로그인 + 어르신 정보 등록
    ├─ feat/home               ← 홈화면 + 주간통계
    └─ feat/settings           ← 설정 화면
```

## 💬 커밋 메시지 규칙 (Commit Message Conventions)

*   `feat` : 새로운 기능 추가
*   `fix` : 버그 수정
*   `docs` : 문서 관련
*   `style` : 스타일 변경 (포매팅 수정, 들여쓰기 추가, …)
*   `refactor` : 코드 리팩토링
*   `test` : 테스트 관련 코드
*   `build` : 빌드 관련 파일 수정
*   `ci` : CI 설정 파일 수정
*   `perf` : 성능 개선
*   `chore` : 그 외 자잘한 수정

---
<p align="center">
  <em>README last updated: 2025-08-21 </em>
</p>
