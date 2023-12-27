# Dictionary
Это приложение на `Jetpack Compose` с применением `MVVM` для изучения различных слов. Можно учить иностранные слова, а можно и просто термины на русском языке.
# Внешний вид:
## Область ввода ответов
Для её реализации выбора языка был [использован](https://github.com/quqveik1/Dictionary/blob/main/app/src/main/java/com/kurlic/dictionary/elements/styled/StyledSpinner.kt) `Android View` и view `Spinner` из обычного `Android SDK`, тк в `Compose` нет стандартного спиннера.

<img src="https://github.com/quqveik1/Dictionary/assets/64206443/c76f645d-1613-4f7e-aaf4-ac1d7d736591" width="200">
<img src="https://github.com/quqveik1/Dictionary/assets/64206443/0e9b4a6c-3e45-4ade-b3e2-4357e8da0f84" width="200">

## Экран выбора параметров для тренировки
### Особенности:
- Возможность выбрать какой язык учить и как.
- Можно учить слова по значению на русском и на иностранном
- Приложения поддерживает разные типы выборки слов(можно учить случайные, последние или вспомнить старину)
- Можно задавать количество слов в одной тренировке
- Есть 3 типа тренировки
## Типы тренировок:
### Написание слов
<img src="https://github.com/quqveik1/Dictionary/assets/64206443/8aadb575-b5e6-4484-a215-15b1f68c120a" width="200">
<img src="https://github.com/quqveik1/Dictionary/assets/64206443/3cce21b8-5949-4c1d-89a1-4e6e933eac31" width="200">

### Карточки как в `Quizlet`.
Поддерживается анимация переворота. [Реализована](https://github.com/quqveik1/Dictionary/blob/main/app/src/main/java/com/kurlic/dictionary/screens/learnwords/train/LearnByCardsSection.kt) она при помощи `animateFloatAsState` и `Modifier.graphicsLayer`. На нее можно посмотреть тут [🔗](https://github.com/quqveik1/Dictionary/assets/64206443/e7d46305-1e4a-46de-b53c-b04219cb18dd)

<img src="https://github.com/quqveik1/Dictionary/assets/64206443/e2fd9174-a9bc-4ce5-a6db-381f1ffedb70" width="200">

### Обучение с вариантами ответа

<img src="https://github.com/quqveik1/Dictionary/assets/64206443/407f3111-707a-46a6-a31c-01147ea97d04" width="200">

### Статистика
В конце каждой тренировки показывается статистика и сколько слов выучил пользователь.

<img src="https://github.com/quqveik1/Dictionary/assets/64206443/e2f73362-0a4e-4245-82fa-f49bdb55310e" width="200">

## Список всех слов
Для отображения списка используется `LazyColumn`, чтобы при большом количестве слов не падала производительность приложения. Тут также [присутсвует](https://github.com/quqveik1/Dictionary/blob/main/app/src/main/java/com/kurlic/dictionary/screens/WordListScreen.kt) анимация удаления слова из списка. [🔗](https://github.com/quqveik1/Dictionary/assets/64206443/8bb6e170-5971-4dac-8d90-993bc3567004)

<img src="https://github.com/quqveik1/Dictionary/assets/64206443/7b49ca0b-8951-440a-a8a7-b1ce8e22901a" width="200">

# Особенности реализации модели хранения данных
Все слова храняться в памяти устройства при помощи `Room`.
Каждое слово храниться в таком виде:
```kotlin
data class WordEntity(
    val id: Int?,
    val key: String,
    val keyLang: LangName,
    val wordValue: String,
    val valueLang: LangName,
    val learningProgress: Int,
    val category: WordCategory,
)
```
Для удобной коммуникации между БД и приложением [используется](https://github.com/quqveik1/Dictionary/blob/main/app/src/main/java/com/kurlic/dictionary/data/WordListViewModel.kt) `ViewModel`, `LiveData`.
Данные тренировки храняться в таком виде:
```kotlin
data class TrainData(
    val words: List<WordEntity>,
    val learnedWords: MutableList<WordEntity> = mutableListOf(),
    val learnByKey: Boolean = true,
    val trainTypes: TrainTypes = TrainTypes.Writing
)
```
Для сохранения конфигурации между поворотами экрана и для удобной передачи данных между экранами была [использована](https://github.com/quqveik1/Dictionary/blob/main/app/src/main/java/com/kurlic/dictionary/screens/learnwords/traindata/TrainViewModel.kt) `ViewModel`.
# Навигация
Для навигации между секциями приложениями [используется](https://github.com/quqveik1/Dictionary/blob/main/app/src/main/java/com/kurlic/dictionary/screens/ScreenNavigation.kt) `NavHost` и `NavController`.
 # Список библиотек:
- `Jetpack Compose`
- `GSON`
- `ViewModel`
- `Room`
- `Material 3`
- `Jetpack Navigation`
- `LiveData`
