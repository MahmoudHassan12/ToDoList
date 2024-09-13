To-Do App with Room Database and RecyclerView
This is a simple To-Do List app built with Kotlin that allows users to add, display, and delete tasks. The app leverages Room for local data storage and RecyclerView for efficiently displaying and managing tasks. The app is designed to demonstrate common Android components such as Dialogs, View Binding, and Coroutines.

Features
Room Database: Stores the tasks locally using Room, making it efficient and persistent across app sessions.
RecyclerView: Displays a list of tasks in a scrollable view, allowing users to manage their to-do items.
Add & Delete Tasks: Users can add new tasks via a custom dialog and delete tasks by tapping on them.
Coroutines: Handles database operations asynchronously using Kotlin Coroutines to avoid blocking the main thread.
View Binding: Simplifies access to views, eliminating the need for findViewById().
Technologies Used
Room: A robust SQLite abstraction to handle database creation and management.
RecyclerView: An optimized list view for handling large datasets efficiently.
Kotlin Coroutines: For managing background tasks and database operations.
Dialogs: Used for task input, including title, description, and date.
View Binding: For easier and safer view manipulation.
How to Use
Clone the repository.
Open it in Android Studio.
Build and run the app on your device/emulator.
