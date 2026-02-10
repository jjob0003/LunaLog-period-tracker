package com.example.a5046.presentation

enum class Routes(val value: String) {
    Report("Report"),
    Main("Main"),
    Setting("Setting"),
    Diary("Diary"),
    Mood("Mood"),
    DiaryItem("diary_item/{ID}"),
    SignUp("SignUp"),
    Username("Username"),
    LastDate("LastDate"),
    BirthDate("BirthDate"),
    PeriodLength("PeriodLength"),
    CycleLength("CycleLength"),
    SignIn("SignIn"),
    PeriodTracker("PeriodTracker"),
    //
    Profile("Profile"),
    Password("Password"),
    Notification("Notification")
}

 sealed class DiaryRoutes(val value: String) {
////    object Report: Routes("Report")
////    object Main: Routes("Main")
////    object Setting: Routes("Setting")
////    object Diary: Routes("Diary")
////    object Mood: Routes("Mood")
////    object SignUp: Routes("SignUp")
////    object SignIn: Routes("SignIn")
////    object PeriodTracker: Routes("PeriodTracker")
    object DiaryItem: DiaryRoutes("DiaryItem/{ID}")
}
