package com.textcooling

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class TextCoolingMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        //Check for the intent filter action
        if (intent.action == Intent.ACTION_PROCESS_TEXT) {

            //Get the text processed
            val handleText = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
            if (handleText.isBlank()) {
                Toast.makeText(this, "Message is empty !", Toast.LENGTH_LONG).show();
            } else {
                var isAnyConverted = false;
                val inLowerCase = handleText.decapitalize()
                val convMap: HashMap<String, String> = HashMap<String, String>()
                convMap["i"] = "1"
                convMap["e"] = "3"
                convMap["o"] = "0"
                //two, three, four, five, ten; thousand as 10k etc. ??
                //regex

                val convStr = StringBuilder()

                for (index in handleText.indices) {
                    val symbol = inLowerCase[index].toString()
                    if (convMap.containsKey(symbol)) {
                        convStr.append(convMap[symbol])
                        isAnyConverted = true;
                    } else {
                        convStr.append(handleText[index])
                    }
                }
                var result = convStr.toString()

                //return result
                if (!isAnyConverted) {
                    //clipboard should return saved original
                    //but it doesn't work due to finish activity ?
                    Toast.makeText(this, "Nothing to cool", Toast.LENGTH_SHORT).show();
                } else {
                    val clip: ClipData = ClipData.newPlainText("label", handleText)
                    clipboard?.setPrimaryClip(clip)
                    Toast.makeText(this, "Using PASTE to return back", Toast.LENGTH_SHORT).show();

                }
                intent.putExtra(Intent.EXTRA_PROCESS_TEXT, result)
                setResult(Activity.RESULT_OK, intent)
            }
        }
        //End the activity
        finish()
    }
}