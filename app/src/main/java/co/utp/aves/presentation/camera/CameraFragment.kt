package co.utp.aves.presentation.camera


import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import co.utp.aves.base.BaseFragment
import co.utp.aves.databinding.FragmentCameraBinding
import co.utp.aves.presentation.BirdEvent
import co.utp.aves.presentation.BirdViewModel
import co.utp.aves.presentation.bird.BirdFragmentDirections
import co.utp.aves.presentation.bird.adapter.BirdClick
import co.utp.aves.presentation.model.Ave
import co.utp.aves.utils.BirdImageName
import dagger.hilt.android.AndroidEntryPoint
import org.pytorch.IValue
import org.pytorch.Module
import org.pytorch.Tensor
import org.pytorch.torchvision.TensorImageUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class CameraFragment : BaseFragment<FragmentCameraBinding, BirdViewModel>() {


    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = mutableListOf(Manifest.permission.CAMERA).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
    }

    override val viewModel: BirdViewModel by viewModels()

    private lateinit var bitmap: Bitmap
    private lateinit var module: Module

    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCameraBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cameraView.visibility = View.VISIBLE
        binding.btnCamera.visibility = View.VISIBLE
        binding.imageCaptured.root.visibility = View.GONE

        cameraExecutor = Executors.newSingleThreadExecutor()
    }


    override fun setUI() {

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        outputDirectory = getOutputDirectory()
    }


    @SuppressLint("RestrictedApi")
    override fun setListeners() {

        with(binding) {
            btnCamera.setOnClickListener {
                takePhoto()
                binding.cameraView.visibility = View.GONE
                binding.btnCamera.visibility = View.GONE
                binding.imageCaptured.root.visibility = View.VISIBLE
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                is BirdEvent.FindBird -> {
                    findNavController().navigate(
                        CameraFragmentDirections.actionCameraToBirdDetail(
                            event.bird
                        )
                    )
                }
                else -> {}
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun getOutputDirectory(): File {
        val mediaDir = requireContext().externalMediaDirs.firstOrNull()?.let {
            File(it, "FilShare").apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireContext().filesDir
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        imageCapture?.let {

            // Create time stamped name and MediaStore entry.
            val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                .format(System.currentTimeMillis()) + ".jpg"

            // Create time-stamped output file to hold the image
            val photoFile = File(outputDirectory, name)


            // Create output options object which contains file + metadata
            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
                .build()

            it.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(requireContext()),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val msg = "Photo capture succeeded: ${outputFileResults.savedUri}"
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                        Log.d(TAG, msg)

                        binding.imageCaptured.imgBird.setImageBitmap(
                            BitmapFactory.decodeFile(
                                photoFile.absolutePath
                            )
                        )
                        pytorchImage(photoFile.absolutePath)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Log.e(TAG, "Photo capture failed: ${exception.message}", exception)
                    }

                }
            )

        }

    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().setTargetResolution(Size(640 ,1136 ))
                .build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun pytorchImage(path: String) {

        try {
            // creating bitmap from packaged into app android asset 'image.jpg',
            // app/src/main/assets/image.jpg
            bitmap = BitmapFactory.decodeFile(path)
            // loading serialized torchscript module from packaged into app android asset model.pt,
            // app/src/model/assets/model.pt
            module = Module.load(context?.let { assetFilePath(it, "modelo_aves.pt") })
        } catch (e: IOException) {
            Log.e("PytorchHelloWorld", "Error reading assets", e)

        }

        //final Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor(bitmap,
        //TensorImageUtils.TORCHVISION_NORM_MEAN_RGB, TensorImageUtils.TORCHVISION_NORM_STD_RGB);
        val inputTensor = preprocess(bitmap, 224)

        // running the model
        val outputTensor = module.forward(IValue.from(inputTensor)).toTensor()

        // getting tensor content as java array of floats
        val scores = outputTensor.dataAsFloatArray

        // searching for the index with maximum score
        var maxScore = -Float.MAX_VALUE
        var maxScoreIdx = -1
        for (i in scores.indices) {
            if (scores[i] > maxScore) {
                maxScore = scores[i]
                maxScoreIdx = i
            }
        }

        val className = BirdImageName.NAMES[maxScoreIdx]

        binding.imageCaptured.titleBird.text = className.replace("_", " ")

        binding.imageCaptured.imgBird.setOnClickListener {
            viewModel.findBird(className.replace("_"," "))
        }

    }


    private fun preprocess(bitmap: Bitmap?, size: Int): Tensor? {

        val mean = floatArrayOf(0.485f, 0.456f, 0.406f)
        val std = floatArrayOf(0.229f, 0.224f, 0.225f)

        var newbitmap = bitmap
        newbitmap = Bitmap.createScaledBitmap(newbitmap!!, size, size, false)
        return TensorImageUtils.bitmapToFloat32Tensor(newbitmap, mean, std)
    }


    @Throws(IOException::class)
    fun assetFilePath(context: Context, assetName: String): String? {
        val file = File(context.filesDir, assetName)
        if (file.exists() && file.length() > 0) {
            return file.absolutePath
        }
        context.assets.open(assetName).use { `is` ->
            FileOutputStream(file).use { os ->
                val buffer = ByteArray(4 * 1024)
                var read: Int
                while (`is`.read(buffer).also { read = it } != -1) {
                    os.write(buffer, 0, read)
                }
                os.flush()
            }
            return file.absolutePath
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

}