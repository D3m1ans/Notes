# Notes

Приложение для заметок на Android: создание и редактирование, закрепление,
поиск, удаление с отменой. Данные хранятся локально и доступны без сети.

## Стек
Kotlin, Jetpack Compose (Material 3), Decompose (навигация), Room 3,
Hilt, Coroutines и Flow, JUnit.

## Архитектура
Чистая архитектура, зависимости направлены к `domain`:

```
app ──► feature:notes ──► core:domain
 └────► core:data ──────► core:domain
```

- `core:domain`: чистый Kotlin, модели, контракт репозитория, use case'ы.
- `core:data`: Room и реализация репозитория.
- `feature:notes`: экраны и компоненты Decompose.
- `app`: Hilt и корневой компонент, который собирает всё вместе.

## Тесты
- `./gradlew :core:domain:test`: unit-тесты бизнес-логики.
- `./gradlew :core:data:connectedDebugAndroidTest`: тесты Room (нужен эмулятор).

## Запуск
Открыть проект в Android Studio и запустить конфигурацию `app`.
