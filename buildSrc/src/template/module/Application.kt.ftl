package ${packageName}

import ie.elliot.x.labs.lib.common.TimberApplication

internal class Application : TimberApplication() {

  override fun onCreate() {
    super.onCreate()
    initTimber(BuildConfig.DEBUG)
  }
}