package com.example.rustorecourse.data.source.local.service

import com.example.rustorecourse.data.source.local.entity.AppDetailsItemEntity
import com.example.rustorecourse.data.source.local.entity.AppEntity
import com.example.rustorecourse.domain.model.Category
import javax.inject.Inject

class AppDaoService @Inject constructor() {
    fun getApp(): AppEntity = AppEntity(
        name = "Гильдия Героев: Экшен ММО РПГ",
        developer = "VK Play",
        category = Category.GAME,
        ageRating = 12,
        size = 223.7f,
        screenshotUrlList = listOf(
            "https://static.rustore.ru/imgproxy/-y8kd-4B6MQ-1OKbAbnoAIMZAzvoMMG9dSiHMpFaTBc/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/dfd33017-e90d-4990-aa8c-6f159d546788.jpg@webp",
            "https://static.rustore.ru/imgproxy/dZCvNtRKKFpzOmGlTxLszUPmwi661IhXynYZGsJQvLw/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/60ec4cbc-dcf6-4e69-aa6f-cc2da7de1af6.jpg@webp",
            "https://static.rustore.ru/imgproxy/g5whSI1uNqaL2TUO7TFfM8M63vXpWXNCm2vlX4Ahvc4/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/c2dde8bc-c4ab-482a-80a5-2789149f598d.jpg@webp",
            "https://static.rustore.ru/imgproxy/TjeurtC7BczOVJt74XhjGYuQnG1l4rx6zpDqyMb00GY/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/08318f76-7a9c-43aa-b4a7-1aa878d00861.jpg@webp",
        ),
        iconUrl = "https://static.rustore.ru/imgproxy/APsbtHxkVa4MZ0DXjnIkSwFQ_KVIcqHK9o3gHY6pvOQ/preset:web_app_icon_62/plain/https://static.rustore.ru/apk/393868735/content/ICON/3f605e3e-f5b3-434c-af4d-77bc5f38820e.png@webp",
        description = "Легендарный рейд героев в Фэнтези РПГ. Станьте героем гильдии и зразите мастера подземелья!"
    )

    fun getListOfApps(): List<AppDetailsItemEntity>{
        return listOf(
            AppDetailsItemEntity(
                appName = "СберБанк Онлайн - с Салютом",
                description = "Больше чем банк",
                category = "Финансы",
                icon = "https://cdn.tvspb.ru/storage/wp-content/uploads/2022/06/sber-vk-3mdthumbnail_gyFF4eN.jpg__0_0x0.jpg"
            ),
            AppDetailsItemEntity(
                appName = "Яндекс.Браузер - с Алисой",
                description = "Быстрый и безопасный браузер",
                category = "Инструменты",
                icon = "https://play-lh.googleusercontent.com/Zg2EKRmLJZHFx3QLTLPAr6lIv8ES8dkxkLKnxKFBHDB1KiRU3H5lK6tod2u9NWh8WgUhOIA9TXyNrjM8rVN9"
            ),
            AppDetailsItemEntity(
                appName = "Почта Mail.ru",
                description = "Почтовый клиент для любых ящиков",
                category = "Инструменты",
                icon = "https://www.alladvertising.ru/porridge/154/180/h_424e67926dbad67291455504f1ddc29c"
            ),
            AppDetailsItemEntity(
                appName = "Яндекс Навигатор",
                description = "Парковки и заправки - по пути",
                category = "Транспорт",
                icon = "https://i0.wp.com/apptractor.ru/wp-content/uploads/2017/04/navi.png?fit=512%2C512&ssl=1"
            ),
            AppDetailsItemEntity(
                appName = "Мой МТС",
                description = "Мой МТС - центр экосистемы МТС",
                category = "Инструменты",
                icon = "https://frgrf.net/d/1100px-mts_logo-1024x707-1.png"
            ),
            AppDetailsItemEntity(
                appName = "Яндекс - с Алисой",
                description = "Яндекс - поиск всегда под рукой",
                category = "Инструменты",
                icon = "https://apktake.com/storage/45fe7c4d0a9c4104b3157fea2f233ad5/6431e71f0b70bApkTake.com.png"
            )
        )
    }
}