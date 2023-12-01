package hu.raiffeisen.nfc

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import hu.raiffeisen.nfc.ui.theme.QrNfcBeamingTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private var mNfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QrNfcBeamingTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    var userInput by remember { mutableStateOf("27") }

                    TextField(value = userInput,
                        onValueChange = { userInput = it },
                        label = { Text("Type in custom number") })

                    Button(onClick = {
                        sendMessage(getLink(userInput))
                    }) {
                        Text(text = "Set message")
                    }
                }
            }
        }

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
    }

    private fun sendMessage(text: String) {
        val intent = Intent(this@MainActivity, MyHostApduService::class.java)
        intent.putExtra("ndefMessage", text)
        startService(intent)
    }

    private fun getLink(customPart: String) =
        "https://teszt-azonnalifizetes.hu/HCT/3/1//PDV%20BankZRT./C%C3%89GES%20KINGA/HU23120836000021479600100002/HUF539/20231127100731%2B1-0086400/IVPT//4268061937.SUBA.2.87688630.INNOHUH0/POS-RAIFF00015/INV-RAIFF00015/CUSTOMER-RAIFF00015/35346473_IN${customPart}27DOk2pKvODx/https%3A%2F%2Fgiro.RAIFF%2Ftop%2FUBRTHUH0/___/1b02.0WUvYP_USzpi28h4L-CeMeIsN2u8gWylSoa_0eTqHaqPh2zysCsHyDRFTflhGTfJf7ElEertWNqGtBOdbS7VBMsTiilz1Vxmut_FK--UHjEyxhGCwlmQZOlbxisVH_lA"

}
