package com.zaneschepke.wireguardautotunnel.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zaneschepke.wireguardautotunnel.repository.model.Settings
import com.zaneschepke.wireguardautotunnel.repository.model.TunnelConfig

@Database(entities = [Settings::class, TunnelConfig::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseListConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun settingDao(): SettingsDoa
    abstract fun tunnelConfigDoa() : TunnelConfigDao
}