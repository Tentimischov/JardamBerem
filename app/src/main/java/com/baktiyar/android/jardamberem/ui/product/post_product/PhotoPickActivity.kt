package com.baktiyar.android.jardamberem.ui.product.post_product

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import java.io.File
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.Toast
import com.baktiyar.android.jardamberem.BuildConfig
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.utils.Const
import com.baktiyar.android.jardamberem.utils.FileUtils
import com.baktiyar.android.jardamberem.utils.Permissions
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.toolbar.*

abstract class PhotoPickActivity : AppCompatActivity() {
    private var fileName: String? = null
    var path: String? = null
    private var index: Int? = null


    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        if (toolbar != null)
            setSupportActionBar(toolbar)
        if (supportActionBar != null)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    protected fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    protected fun showPickImageDialog(index: Int) {
        this.index = index
        val args = arrayOf<String>(getString(R.string.pick_photo_from_camera),
                getString(R.string.pick_photo_from_gallery))
        AlertDialog.Builder(this).setItems(args) { dialog, w ->
            if (w == 0) takePhotoFromCamera()
            else pickPhotoFromGallery()
            dialog.dismiss()
        }.show()
    }

    private fun takePhotoFromCamera() {
        if (Permissions.iPermissionCamera(this)) {
            fileName = System.nanoTime().toString()
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val uri = FileUtils.getCaptureImageOutputUri(this, fileName)
            if (uri != null) {
                val file = File(uri.path)
                if (Build.VERSION.SDK_INT >= 24) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            FileProvider.getUriForFile(this,
                                    BuildConfig.APPLICATION_ID + ".provider",
                                    file))
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                } else intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

                startActivityForResult(intent, Const.CAMERA)
            }
        }
    }

    private fun pickPhotoFromGallery() {
        if (Permissions.iPermissionReadStorage(this)) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, Const.GALLERY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                Const.CAMERA -> {
                    val uri = FileUtils.getPickImageResultUri(this, data, fileName)
                    val file = FileUtils.getNormalizedUri(this, uri)
                    val path = Compressor.getDefault(this).compressToFile(File(file.path)).path
                    this.path = path
                }
                Const.GALLERY -> {
                    if (data != null || data?.clipData != null) {
                        var count = data.clipData?.itemCount
                        var currentItem = 0

                        if (data.clipData == null && data.data != null) {
                            val file = FileUtils.getImagePathFromInputStreamUri(this, data.data)
                            val path = Compressor.getDefault(this).compressToFile(File(file!!)).path
                            this.path = path

                        } else while (currentItem < count!!) {
                            val imageUri = data.clipData.getItemAt(currentItem).uri
                            val file = FileUtils.getImagePathFromInputStreamUri(this, imageUri)
                            val path = Compressor.getDefault(this).compressToFile(File(file!!)).path
                            this.path = path
                            ++currentItem
                        }
                    }
                }
            }
            setImagePaths(path, index)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Permissions.Request.READ_EXTERNAL_STORAGE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickPhotoFromGallery()
        } else if (requestCode == Const.CAMERA && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePhotoFromCamera()
        }
    }

    abstract fun setImagePaths(imgPaths: String?, id: Int?)
}