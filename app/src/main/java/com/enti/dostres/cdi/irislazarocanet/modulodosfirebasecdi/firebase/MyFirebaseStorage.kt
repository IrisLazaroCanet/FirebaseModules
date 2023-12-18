package com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.firebase

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import java.nio.file.FileSystems
import java.nio.file.Path

class MyFirebaseStorage {

    private val storage = Firebase.storage
    private val storageRootRef = storage.reference

    //Per posar coses dins la carpeta images:
    //Si volguéssim crear una carpeta per les imatges de cada usuari, faríem un child que tingués l'id de l'usuari
    private val imagesPath = "Images"
    private val imagesRef = storageRootRef.child(imagesPath)

    //Si success -> retornarà una url ; si failure -> retornarà una exception
    fun saveImage(uri: Uri, onSuccess: (Uri) -> Unit, onFailure: (Exception) -> Unit)
    {
        //Necessitem la ruta per poder-li posar un nom adequat a la imatge (per exemple, el nom de l'arxiu original)
        val path: Path = FileSystems.getDefault().getPath(uri.path)
        //Per agafar el name de la imatge
        val name = path.fileName.toString()

        val imageRef = imagesRef.child(name)
        imageRef.putFile(uri)
            .addOnSuccessListener { uploadSnapshot ->       //Firebase funciona amb snapshots
                //La snapshot no ens dóna directament la url (sols té la referència a l'objecte dins de firebase)
                //Si volem l'url l'hem d'anar a buscar
                uploadSnapshot.storage.downloadUrl
                    .addOnSuccessListener(onSuccess)
                    .addOnFailureListener(onFailure)


            }
            .addOnFailureListener (onFailure)
    }
}