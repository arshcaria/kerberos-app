package io.github.arshcaria.kerberos

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.github.arshcaria.kerberos.model.Item
import kotlinx.android.synthetic.main.activity_add_item.*
import org.jetbrains.anko.toast

class AddItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        saveItemFab.setOnClickListener { saveItem() }
    }

    private fun saveItem() {
        val item = Item()
        item.title = tvTitle.text.toString()
        item.content = tvContent.text.toString()
        item.type = Item.TYPE_TEXT_NOTE

        SaveAsyncTask().execute(item)
    }

    inner class SaveAsyncTask : AsyncTask<Item, Void, Boolean>() {
        override fun doInBackground(vararg params: Item?): Boolean {
            val item = params[0]
            item?.let {
                KerbApp.db?.itemDao()?.insert(it)
                val items = KerbApp.db.itemDao().getAll()
                Log.d("@@", "${items.size}")
                finish()
                return true
            }
            return false
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            result?.let { toast(if (result) "Inserted" else "Failed") }
        }
    }
}


