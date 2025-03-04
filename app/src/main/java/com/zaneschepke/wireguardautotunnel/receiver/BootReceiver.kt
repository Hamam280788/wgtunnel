package com.zaneschepke.wireguardautotunnel.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.zaneschepke.wireguardautotunnel.repository.SettingsDoa
import com.zaneschepke.wireguardautotunnel.service.foreground.ServiceManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var settingsRepo : SettingsDoa

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val settings = settingsRepo.getAll()
                    if (settings.isNotEmpty()) {
                        val setting = settings.first()
                        if (setting.isAutoTunnelEnabled && setting.defaultTunnel != null) {
                            ServiceManager.startWatcherService(context, setting.defaultTunnel!!)
                        }
                    }
                } finally {
                    cancel()
                }
            }
        }
    }
}