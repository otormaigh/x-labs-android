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

package ie.elliot.x.labs.watertracker.ui.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnRepeat
import ie.elliot.x.labs.watertracker.R

class IntakeProgressbar : View {
  private val amplitude = 8f
  private val density = 5.0f
  private val frequency = 0.6f
  private val numberOfWaves = 2
  private val phaseShift = -0.04f
  private val primaryWaveLineWidth = 3.0f
  private val secondaryWaveLineWidth = 1.0f

  private val path by lazy { Path() }
  private val paint by lazy {
    Paint().apply {
      color = ContextCompat.getColor(context, R.color.deep_sky_blue)
      style = Paint.Style.FILL_AND_STROKE
      isAntiAlias = true
    }
  }
  private var phase = 0f
  var progress = 0f
    set(value) {
      ValueAnimator.ofFloat(0f, value).apply {
        duration = (value * 1000).toLong()
        addUpdateListener {
          field = animatedValue as Float
          invalidate()
        }
        doOnEnd { animator.start() }
      }.start()
    }

  // FIXME: Figure out a better way to animate the waves.
  private val animator by lazy {
    ValueAnimator.ofFloat(frequency).apply {
      duration = (frequency * 10).toLong()
      repeatCount = ValueAnimator.INFINITE
      doOnRepeat {
        phase += phaseShift
        invalidate()
      }
    }
  }

  constructor(context: Context) : this(context, null)
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  override fun onDraw(canvas: Canvas) {
    val maxHeight = measuredHeight - (measuredHeight * progress) // TODO: Animate height change
    val width = measuredWidth.toFloat()
    val mid = (measuredWidth / 2).toFloat()

    for (i in 0 until numberOfWaves) {
      // Prepare variables for this wave
      paint.strokeWidth = if (i == 0) primaryWaveLineWidth else secondaryWaveLineWidth
      val progress = 1.0f - i.toFloat() / this.numberOfWaves
      val normedAmplitude = (1.5f * progress - 0.5f) * this.amplitude

      // Prepare path for this wave
      path.reset()
      var x = 0f
      while (x < width + density) {
        // We use a parable to scale the sinus wave, that has its peak in the middle of the view.
        val scaling = (-Math.pow((1 / mid * (x - mid)).toDouble(), 2.0) + 1).toFloat()
        val y = ((scaling.toDouble() * amplitude.toDouble() * normedAmplitude.toDouble()
            * Math.sin(2.0 * Math.PI * (x / width).toDouble() * frequency.toDouble() + phase * (i + 1))) + maxHeight).toFloat()

        if (x == 0f) {
          path.moveTo(x, y)
        } else {
          path.lineTo(x, y)
        }
        x += density
      }
      path.lineTo(x, measuredHeight.toFloat())
      path.lineTo(0f, measuredHeight.toFloat())
      path.close()

      // Set opacity for this wave fill
      paint.alpha = if (i == 0) 255 else 255 / (i + 1)

      // Draw wave
      canvas.drawPath(path, paint)
    }
  }
}