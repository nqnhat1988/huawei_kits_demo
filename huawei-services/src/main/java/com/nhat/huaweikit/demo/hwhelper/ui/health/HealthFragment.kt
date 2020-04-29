package com.nhat.huaweikit.demo.hwhelper.ui.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nhat.huaweikit.demo.hwhelper.R


class HealthFragment : Fragment() {

    //    private lateinit var mSampleHandler: Runnable
//    private lateinit var mHandler: Handler
//    private lateinit var sensorsController: SensorsController
    private lateinit var healthViewModel: HealthViewModel
//    private var mBaseTimeStamp: Long = 0
//    private var mLastSamplePoint: SamplePoint? = null
//    private var mCurrentSamplePoint: SamplePoint? = null
//
//    private val TAG = HealthFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        healthViewModel =
            ViewModelProvider(this).get(HealthViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_health, container, false)
        healthViewModel.text.observe(viewLifecycleOwner, Observer {

        })

        return root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        //FIXME I assume the app logged in
//        val options: HiHealthOptions = HiHealthOptions.builder().build()
//        val hwId = HuaweiIdAuthManager.getExtendedAuthResult(options)
//        sensorsController = HuaweiHiHealth.getSensorsController(requireContext(), hwId)
//
//        val dataCollector: DataCollector = DataCollector.Builder()
//            .setDataType(DataType.DT_CONTINUOUS_STEPS_TOTAL)
//            .setDataGenerateType(DataCollector.DATA_TYPE_RAW)
//            .setPackageName(requireContext())
//            .setDeviceInfo(DeviceInfo("hw", "hw", "hw", 0))
//            .build()
//
//        sensorsController.register(
//            SensorOptions.Builder()
//                .setDataType(DataType.DT_CONTINUOUS_STEPS_TOTAL)
//                .setDataCollector(dataCollector)
//                .build()
//        ) {
//            Log.d(TAG, "on sample point received");
//            processSamplePoint(it)
//        }
//
//        mHandler = Handler()
//
//        mSampleHandler = Runnable {
//            healthViewModel.isStart.value?.let { isStarted ->
//                if (isStarted) {
//                    if (mLastSamplePoint != null && mCurrentSamplePoint != null) {
//                        val samplePoint: SamplePoint = mCurrentSamplePoint!!
//                        val walkingSportData = WalkingSportData(
//                            samplePoint.getSamplingTime(TimeUnit.MILLISECONDS) - mBaseTimeStamp,
//                            (samplePoint.getFieldValue(Field.FIELD_STEPS).asIntValue()
//                                    - mLastSamplePoint!!.getFieldValue(Field.FIELD_STEPS).asIntValue()).toLong()
//                        )
//                        mLastSamplePoint = samplePoint
//                        Log.d(
//                            TAG,
//                            String.format("feed data: %s", walkingSportData.toString())
//                        )
//                        Toast.makeText(
//                            requireContext(),
//                            String.format("feed data: %s", walkingSportData.toString()),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                    mHandler.postDelayed(mSampleHandler, 1000)
//                }
//            }
//        }
//
//        btn_start_stop.setOnClickListener {
//            healthViewModel.isStart.value?.let {
//                if (it) {
//                    btn_start_stop.text = "START"
//                    healthViewModel.isStart.postValue(false)
//                    mHandler.removeCallbacks(mSampleHandler)
//                } else {
//                    btn_start_stop.text = "STOP"
//                    healthViewModel.isStart.postValue(true)
//                    mHandler.postDelayed(mSampleHandler, 1000)
//                }
//            }
//        }
//    }
//
//    private fun processSamplePoint(samplePoint: SamplePoint?) {
//        samplePoint?.let { sp ->
//            mCurrentSamplePoint = sp
//            mLastSamplePoint?.let {
//                mBaseTimeStamp = sp.getSamplingTime(TimeUnit.MILLISECONDS);
//                mLastSamplePoint = sp
//            }
//        }
//    }
}
