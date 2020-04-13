package com.example.jujojazbase;

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.Handler
import com.example.jujojazbase.Lib.Companion.Bytetobyte
import com.example.jujojazbase.Lib.Companion.byteToByte
import json.JSONObject

open class JujojazLib:Network<ByteArray> {
    var andLib: AndroidLib = AndroidLib();
    var context: Context? = null;

    constructor() {}
    constructor(context: Context) {
        this.context = context;
        this.andLib = AndroidLib(context);
    }

    constructor(context: Context, activity: Activity) {
        this.andLib = AndroidLib()
        this.andLib = AndroidLib(context, activity);
    }

    companion object {
        fun hitAPI(api: String, json: JSONObject, context: Context, onDone: (data: JSONObject)->Unit, onError: (msg: String)->Unit, showLoading: Boolean){
            val loading = ProgressDialog(context)
            if (showLoading){
                loading.setMessage("Please Wait...")
                loading.show()
                loading.setCanceledOnTouchOutside(false)
            }

            val net: JujojazLib = object : JujojazLib() {
                override fun onDone(x: List<Byte>) {
                    val byteArray = x.toByteArray().toTypedArray();
                    val data = JSONObject(String(Bytetobyte(byteArray)))
                    println(data.toString())
                    onDone(data);
                    loading.dismiss();
                }

                override fun onError(msg: String) {
                    println("ERROR: $msg")
                    onError(msg);
                    loading.dismiss();
                }
            }
            if (showLoading){
                Handler().postDelayed({
                    if (loading.isShowing){
                        loading.dismiss()
                        onError("Connection Error");
                    }
                }, 10000)
            }
            net.sendUrl(Configuration.API_SERVER+api, byteToByte(("data=" + Auth.authJson.toString()).toByteArray()) as Array<Byte>, 0)
        }
        fun convertJSONToModelData(json: JSONObject): MutableList<ModelData> {


            var datas = mutableListOf<ModelData>();
            var jsonData = json.getJSONArray("data");
            for (o in jsonData) {
                val dataObject = o as JSONObject
                val dataFields = dataObject["fields"] as JSONObject
                datas.add(ModelData(Integer.valueOf(dataObject["pk"].toString()),
                        dataFields["file_foto_b64"].toString(),
                        dataFields["from_name"].toString(),
                        dataFields["car_name"].toString(),
                        dataFields["merk"].toString(),
                        dataFields["tipe"].toString(),
                        dataFields["servis_dimulai"].toString(),
                        dataFields["servis_setiap_berapa_hari"].toString(),
                        dataFields["pajak_dimulai"].toString(),
                        dataFields["pajak_setiap_berapa_hari"].toString()))
            }

            return datas;
        }
    }
}