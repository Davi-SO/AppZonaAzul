package br.com.appzonaazul

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.*
import androidx.core.content.FileProvider
import java.io.File
import java.time.LocalDateTime


private const val REQUEST_CODE = 42

class CameraActivity : AppCompatActivity() {

    private var FILE_NAME = "photo.jpg"
    private var paths: MutableList<String> = mutableListOf("","","","")
    private lateinit var tvData: AppCompatTextView
    private lateinit var btnEnviarFoto : AppCompatButton
    private lateinit var ivFoto : AppCompatImageButton
    private lateinit var ivFoto2 : AppCompatImageButton
    private lateinit var ivFoto3 : AppCompatImageButton
    private lateinit var ivFoto4 : AppCompatImageButton
    private lateinit var photoFile: File




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)


        tvData  = findViewById(R.id.tvData)
        ivFoto = findViewById(R.id.ivFoto)
        ivFoto2 = findViewById(R.id.ivFoto2)
        ivFoto3 = findViewById(R.id.ivFoto3)
        ivFoto4 = findViewById(R.id.ivFoto4)
        btnEnviarFoto = findViewById(R.id.btnEnviarFoto)

        ivFoto.setOnClickListener(){
            pegarFoto(1)
        }
        ivFoto2.setOnClickListener(){
            pegarFoto(2)
        }
        ivFoto3.setOnClickListener(){
            pegarFoto(3)
        }
        ivFoto4.setOnClickListener(){
            pegarFoto(4)
        }
        btnEnviarFoto.setOnClickListener(){
            if(paths[0].isEmpty()||paths[1].isEmpty()||paths[2].isEmpty()||paths[3].isEmpty()) {
                Toast.makeText(
                    baseContext,
                    "!!!ERRO!!!Por favor, tire as quatro fotos antes de enviar!",
                    Toast.LENGTH_SHORT
                ).show()
                pegarData()
            } else{

            }
        }


    }

    private fun pegarFoto(REQUEST_CODE: Int){
        val tirarFoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = getPhotoFile(FILE_NAME)

        //tirarFoto.putExtra(MediaStore.EXTRA_OUTPUT, photoFile)
        val fileProvider = FileProvider.getUriForFile(this, "br.com.appzonaazul.fileprovider", photoFile)
        tirarFoto.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
        if (tirarFoto.resolveActivity(this.packageManager) != null) {
            startActivityForResult(tirarFoto, REQUEST_CODE)
        } else {
            Toast.makeText(this, "Erro ao abrir a camera", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getPhotoFile(fileName: String): File {
        // Use "getExternalFilesDir" on Context to access package-especific directories
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK){
            val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
            val img: Drawable = BitmapDrawable(resources,bitmap)
            if(requestCode == 1) {
                ivFoto.background = img
                ivFoto.setImageIcon(null)
                paths[0] = photoFile.absolutePath
                Log.i("ENDERECO",paths[0])
            }
            if(requestCode == 2) {
                ivFoto2.background = img
                ivFoto2.setImageIcon(null)
                paths[1] = photoFile.absolutePath
                Log.i("ENDERECO",paths[1])
            }
            if(requestCode == 3) {
                ivFoto3.background = img
                ivFoto3.setImageIcon(null)
                paths[2] = photoFile.absolutePath
                Log.i("ENDERECO",paths[2])
            }
            if(requestCode == 4) {
                ivFoto4.background = img
                ivFoto4.setImageIcon(null)
                paths[3] = photoFile.absolutePath
                Log.i("ENDERECO",paths[3])
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    private fun pegarData(){
        val data = LocalDateTime.now()
        tvData.text = data.toString()
    }


}