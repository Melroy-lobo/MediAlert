# MediAlert – Android Medication Reminder (Kotlin + Compose)

A clean starter for a medication reminder app. Built with **Kotlin**, **Jetpack Compose**, **Room**, **Hilt**, and **AlarmManager**.

## Features
- MVVM + Hilt
- Room local DB
- Exact alarms with `setExactAndAllowWhileIdle`
- Re-schedules after reboot
- Material 3 UI

## Getting Started
1. Open in Android Studio (Ladybug or newer).
2. Let Gradle sync.
3. Run on a device/emulator (grant notification permission on Android 13+).
4. Add a medication and you’ll get notifications at the next scheduled time slot.

## Notes
- Scheduling logic picks the next dose slot today or tomorrow based on first-dose hour and interval.
- Extend schema to support days-of-week, snooze, or cloud sync.