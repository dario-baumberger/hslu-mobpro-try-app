package com.example.tryapp

sealed class TryApplicationScreens(val route: String, val title: String) {
    object Home : TryApplicationScreens("home", "Home")
    object Detail : TryApplicationScreens("detail/{senderText}", "Detail")
    object Bands : TryApplicationScreens("bands", "Bands")
    object Band : TryApplicationScreens("band/{bandCode}", "Band Info")
    object Users : TryApplicationScreens("users", "Users")
    object Music : TryApplicationScreens("music", "Music")
    object Sms : TryApplicationScreens("sms", "SMS")
}


fun TryApplicationScreens.withArgs(vararg args: String): String {
    var routeTemplate = this.route
    args.forEach { arg ->
        routeTemplate = routeTemplate.replaceFirst(Regex("""\{.*?}"""), arg)
    }
    return routeTemplate
}

