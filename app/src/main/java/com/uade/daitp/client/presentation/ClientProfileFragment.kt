package com.uade.daitp.client.presentation

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentClientProfileBinding
import com.uade.daitp.login.core.model.User
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.presentation.util.errorDialog
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle
import com.uade.daitp.presentation.util.successDialog
import java.io.ByteArrayOutputStream
import java.io.InputStream

class ClientProfileFragment : Fragment(R.layout.fragment_client_profile) {

    private val viewModel = ViewModelDI.getClientProfileViewModel()
    private lateinit var binding: FragmentClientProfileBinding
    private val SELECT_IMAGE_REQUEST_CODE = 100
    private var base64Image: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientProfileBinding.bind(view)

        viewModel.user.observe(viewLifecycleOwner) { user: User ->
            binding.profileName.setText(user.username)
            binding.profileEmail.setText(user.email)
            base64Image = user.avatar
            loadImage(user.avatar)
        }

        binding.profileButton.setOnClickListenerWithThrottle {
            val name = binding.profileName.text.toString()
            val base64ImageValue =
                base64Image ?: ""
            viewModel.update(name, base64ImageValue)
        }

        binding.editButton.setOnClickListenerWithThrottle {
            // Open gallery to select image
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE)
        }

        viewModel.profileUpdated.observe(viewLifecycleOwner) { updated: Boolean ->
            if (updated)
                successDialog()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            imageUri?.let { uri ->
                try {
                    val inputStream: InputStream? = activity?.contentResolver?.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)

                    val straightBitmap = rotate(bitmap, 270f)

                    base64Image = bitmapToBase64(getResizedBitmap(straightBitmap, 1024))
                    loadImage(base64Image)
                } catch (e: Exception) {
                    errorDialog()
                }
            }
        }
    }

    private fun loadImage(imageData: String?) {
        if (!imageData.isNullOrEmpty()) {
            val byteArray = Base64.decode(imageData, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

            Glide.with(this)
                .load(bitmap)
                .into(binding.profileImage)
        } else {
            Glide.with(this)
                .load(R.drawable.img_logo)
                .into(binding.profileImage)
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    private fun rotate(bitmap: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

}
