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

package ie.elliot.x.labs.watertracker.ui.archive

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ie.elliot.x.labs.watertracker.R
import ie.elliot.x.labs.watertracker.extension.toPercentageString
import ie.elliot.x.labs.watertracker.model.Archive
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_archive.*

class ArchiveRecyclerAdapter : RecyclerView.Adapter<ArchiveRecyclerAdapter.ViewHolder>() {
  private var data = listOf<Archive>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
      ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_archive, parent, false))

  override fun getItemCount() = 20

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind()
  }

  inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind() {
      tvIntakePercentage.text = 48f.toPercentageString()
    }
  }
}