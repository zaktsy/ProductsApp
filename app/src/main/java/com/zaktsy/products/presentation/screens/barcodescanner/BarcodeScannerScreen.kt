package com.zaktsy.products.presentation.screens.barcodescanner

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.common.util.concurrent.ListenableFuture
import com.zaktsy.products.R
import com.zaktsy.products.domain.BarCodeAnalyser
import com.zaktsy.products.presentation.navigation.NavigationRoutes.Companion.AddProductWithBarCodeRoot
import com.zaktsy.products.ui.components.TitleWithBackButton
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BarcodeScannerScreen(
    navController: NavController
) {
    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            TitleWithBackButton(
                title = stringResource(id = R.string.scan_barcode),
                onBackAction = navController::popBackStack
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Box(
                    modifier = Modifier
                        .height(400.dp)
                        .width(300.dp)
                ) {
                    CameraPreview(navController)
                }
            }
        }
    }
}

@Composable
fun CameraPreview(navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var preview by remember { mutableStateOf<Preview?>(null) }

    AndroidView(factory = { AndroidViewContext ->
        PreviewView(AndroidViewContext).apply {
            this.scaleType = PreviewView.ScaleType.FIT_CENTER
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
        }
    }, modifier = Modifier
        .height(400.dp)
        .width(300.dp), update = { previewView ->
        val cameraSelector: CameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
        val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
        val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
            ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val barcodeAnalyser = BarCodeAnalyser { barcodes ->
                val barcode = barcodes.first()
                barcode.rawValue?.let { barcodeValue ->
                    navController.navigate(buildTwoRoute(barcodeValue)){
                        popUpTo(0)
                    }
                }
            }
            val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build().also {
                    it.setAnalyzer(cameraExecutor, barcodeAnalyser)
                }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview, imageAnalysis
                )
            } catch (e: Exception) {
                Log.d("TAG", "CameraPreview: ${e.localizedMessage}")
            }
        }, ContextCompat.getMainExecutor(context))
    })
}

private fun buildTwoRoute(argument: String) = "${AddProductWithBarCodeRoot}/$argument"