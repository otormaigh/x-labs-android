/*
 * Copyright 2018 Elliot Tormey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ie.elliot.x.labs.watertracker.extension

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity

inline fun <reified VM : ViewModel> AppCompatActivity.viewModelProvider(
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
    crossinline provider: () -> VM) = lazy(mode) {

  val factory = object : ViewModelProvider.Factory {
    override fun <M : ViewModel> create(klass: Class<M>) = provider() as M
  }
  ViewModelProviders.of(this, factory).get(VM::class.java)
}