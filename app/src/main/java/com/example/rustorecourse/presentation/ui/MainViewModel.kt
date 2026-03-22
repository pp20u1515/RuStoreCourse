package com.example.rustorecourse.presentation.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.rustorecourse.domain.AppDetailsItem

class MainViewModel : ViewModel() {

    private val _apps = mutableStateOf<List<AppDetailsItem>>(emptyList())
    val apps: State<List<AppDetailsItem>> = _apps

    init {
        loadApps()
    }

    fun loadApps() {
        _apps.value = listOf(
            AppDetailsItem(
                appName = "СберБанк Онлайн - с Салютом",
                description = "Больше чем банк",
                category = "Финансы",
                icon = "https://cdn.tvspb.ru/storage/wp-content/uploads/2022/06/sber-vk-3mdthumbnail_gyFF4eN.jpg__0_0x0.jpg"
            ),
            AppDetailsItem(
                appName = "Яндекс.Браузер - с Алисой",
                description = "Быстрый и безопасный браузер",
                category = "Инструменты",
                icon = "https://play-lh.googleusercontent.com/Zg2EKRmLJZHFx3QLTLPAr6lIv8ES8dkxkLKnxKFBHDB1KiRU3H5lK6tod2u9NWh8WgUhOIA9TXyNrjM8rVN9"
            ),
            AppDetailsItem(
                appName = "Почта Mail.ru",
                description = "Почтовый клиент для любых ящиков",
                category = "Инструменты",
                icon = "https://www.alladvertising.ru/porridge/154/180/h_424e67926dbad67291455504f1ddc29c"
            ),
            AppDetailsItem(
                appName = "Яндекс Навигатор",
                description = "Парковки и заправки - по пути",
                category = "Транспорт",
                icon = "https://i0.wp.com/apptractor.ru/wp-content/uploads/2017/04/navi.png?fit=512%2C512&ssl=1"
            ),
            AppDetailsItem(
                appName = "Мой МТС",
                description = "Мой МТС - центр экосистемы МТС",
                category = "Инструменты",
                icon = "https://frgrf.net/d/1100px-mts_logo-1024x707-1.png"
            ),
            AppDetailsItem(
                appName = "Яндекс - с Алисой",
                description = "Яндекс - поиск всегда под рукой",
                category = "Инструменты",
                icon = "https://apktake.com/storage/45fe7c4d0a9c4104b3157fea2f233ad5/6431e71f0b70bApkTake.com.png"
            )
        )
    }
}