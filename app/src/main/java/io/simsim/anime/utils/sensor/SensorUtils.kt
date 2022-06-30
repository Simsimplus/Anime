package io.simsim.anime.utils.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import splitties.systemservices.sensorManager
import kotlin.math.abs


/**
 * Listener that detects shake gesture.
 */
class ShakeEventListener(
    lifecycleOwner: LifecycleOwner,
    private val sensorManager: SensorManager,
    private val onShake: () -> Unit = {}
) : SensorEventListener, DefaultLifecycleObserver {
    /** Time when the gesture started.  */
    private var mFirstDirectionChangeTime: Long = 0

    /** Time when the last movement started.  */
    private var mLastDirectionChangeTime: Long = 0

    /** How many movements are considered so far.  */
    private var mDirectionChangeCount = 0

    /** The last x position.  */
    private var lastX = 0f

    /** The last y position.  */
    private var lastY = 0f

    /** The last z position.  */
    private var lastZ = 0f


    init {
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_UI
        )
        // observe lifecycle state
        lifecycleOwner.lifecycle.addObserver(this)
    }


    override fun onResume(owner: LifecycleOwner) {
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_UI
        )
    }


    override fun onPause(owner: LifecycleOwner) {
        sensorManager.unregisterListener(this)
    }

    fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(se: SensorEvent) {
        // get sensor data
        val x = se.values[0]
        val y = se.values[1]
        val z = se.values[2]

        // calculate movement
        val totalMovement = abs(x + y + z - lastX - lastY - lastZ)
        if (totalMovement > MIN_FORCE) {

            // get time
            val now = System.currentTimeMillis()

            // store first movement time
            if (mFirstDirectionChangeTime == 0L) {
                mFirstDirectionChangeTime = now
                mLastDirectionChangeTime = now
            }

            // check if the last movement was not long ago
            val lastChangeWasAgo = now - mLastDirectionChangeTime
            if (lastChangeWasAgo < MAX_PAUSE_BETWEEN_DIRECTION_CHANGE) {

                // store movement data
                mLastDirectionChangeTime = now
                mDirectionChangeCount++

                // store last sensor data
                lastX = x
                lastY = y
                lastZ = z

                // check how many movements are so far
                if (mDirectionChangeCount >= MIN_DIRECTION_CHANGE) {
                    // check total duration
                    val totalDuration = now - mFirstDirectionChangeTime
                    if (totalDuration < MAX_TOTAL_DURATION_OF_SHAKE) {
                        onShake()
                        resetShakeParameters()
                    }
                }
            } else {
                resetShakeParameters()
            }
        }
    }

    /**
     * Resets the shake parameters to their default values.
     */
    private fun resetShakeParameters() {
        mFirstDirectionChangeTime = 0
        mDirectionChangeCount = 0
        mLastDirectionChangeTime = 0
        lastX = 0f
        lastY = 0f
        lastZ = 0f
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    companion object {
        /** Minimum movement force to consider.  */
        private const val MIN_FORCE = 10

        /**
         * Minimum times in a shake gesture that the direction of movement needs to
         * change.
         */
        private const val MIN_DIRECTION_CHANGE = 3

        /** Maximum pause between movements.  */
        private const val MAX_PAUSE_BETWEEN_DIRECTION_CHANGE = 200

        /** Maximum allowed time for shake gesture.  */
        private const val MAX_TOTAL_DURATION_OF_SHAKE = 400
    }
}

val LifecycleOwner.shakeEventFlow: Flow<Boolean>
    get() {
        return callbackFlow {
            trySend(false)
            val listener = ShakeEventListener(
                lifecycleOwner = this@shakeEventFlow,
                sensorManager = sensorManager,
            ) {
                trySend(true)
            }
            awaitClose {
                listener.unregisterListener()
            }
        }
    }