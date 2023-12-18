package com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.fragments.screens.chat

import android.graphics.BitmapFactory
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.R
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.firebase.FB
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.firebase.models.DataBaseMessage
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val container by lazy {view.findViewById<LinearLayout>(R.id.message_container)}
    private val message by lazy {view.findViewById<MaterialTextView>(R.id.message_text)}
    private val image by lazy {view.findViewById<ImageView>(R.id.message_image)}

    fun SetupWithmessage(dataBaseMessage: DataBaseMessage) {

        if(dataBaseMessage.userId == FB.auth.GetUser()?.id) {
            container.gravity = Gravity.RIGHT
        } else {
            container.gravity = Gravity.LEFT
        }

        dataBaseMessage.message?.let { text ->
            message.text = text
            message.visibility = View.VISIBLE
        } ?: kotlin.run {
            message.visibility = View.GONE
        }

        dataBaseMessage.imageUrl?.let {imageUrl ->

            //Podríem posar-li un placeholder mentre es carrega la imatge
            //Es podria agafar la mida de la imatge en pujar-la, perquè així el placeholder tingui la mateixa mida
            //I no creixi de cop quan es carrega la imatge
            //...

            //També li podríem posar un spinner de càrrega mentre no apareix la imatge

            //Creem un thread utilitzant el sistema de corutines de kotlin
            CoroutineScope(Dispatchers.IO).launch {

                val stream = URL(imageUrl).openStream()
                //Tot els streams treballen amb mapes de bits
                //Passar a bitmap ens fa l'array de puntets de colors
                val bitMap = BitmapFactory.decodeStream(stream)

                //No es pot canviar la UI en un thread que no sigui el principal
                //Sols ho podríem fer si tinguéssim un engine propi
                //Hem de tornar al thread principal, doncs
                CoroutineScope(Dispatchers.Main).launch {

                    image.setImageBitmap(bitMap)
                    image.visibility = View.VISIBLE
                }

            }

            //TODO load image
            message.visibility = View.VISIBLE
        } ?: kotlin.run {
            message.visibility = View.GONE
        }
    }
}