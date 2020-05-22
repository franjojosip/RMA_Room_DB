package ht.ferit.fjjukic.roomapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ht.ferit.fjjukic.roomapplication.R
import ht.ferit.fjjukic.roomapplication.adapter.InspiringPeopleAdapter
import ht.ferit.fjjukic.roomapplication.database.InspiringPeopleDatabase
import ht.ferit.fjjukic.roomapplication.fragment.EditInspiringPeopleFragment
import ht.ferit.fjjukic.roomapplication.fragment.InspiringPeopleFragment
import ht.ferit.fjjukic.roomapplication.interfaces.FragmentListener
import ht.ferit.fjjukic.roomapplication.interfaces.InspiringPeopleListener
import ht.ferit.fjjukic.roomapplication.repository.InspiringPeopleRepository
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var inspiringPeopleFragment: InspiringPeopleFragment
    private lateinit var inspiringPersonAdapter: InspiringPeopleAdapter
    private lateinit var editInspiringPersonFragment: EditInspiringPeopleFragment
    private lateinit var repository: InspiringPeopleRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            lifecycleScope.launch {
                setRepository()
                setFragmentManager()
            }
        }
    }

    private suspend fun setRepository() {
        this.repository = InspiringPeopleRepository(InspiringPeopleDatabase.getDatabase(context = this).inspiringPeopleDao())
        when {
            this.repository.getAll().count() == 0 -> this.repository.fillDB()
        }
    }

    private suspend fun setFragmentManager() {
        this.inspiringPersonAdapter =
                InspiringPeopleAdapter(
                        this@MainActivity,
                        createInspiringPeopleListener(),
                        this.repository
                )
        inspiringPersonAdapter.setItems(repository.getAll())
        this.inspiringPeopleFragment =
                InspiringPeopleFragment(
                        this.inspiringPersonAdapter,
                        createFragmentListener()
                )
        supportFragmentManager.beginTransaction().add(R.id.frameLayout, this.inspiringPeopleFragment)
                .commit()
    }

    private fun createInspiringPeopleListener(): InspiringPeopleListener {
        return object :
                InspiringPeopleListener {
            override fun onShowQuote(index: Int) {
                lifecycleScope.launch {
                    val quote = repository.getQuote(index)
                    Toast.makeText(
                        applicationContext,
                        quote,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun checkRemoveItem(index: Int) {
                val alertDialog: AlertDialog =
                        AlertDialog.Builder(this@MainActivity).create()
                alertDialog.setTitle("Delete")
                alertDialog.setMessage("Are you sure you want to delete this item?")
                alertDialog.setButton(
                        AlertDialog.BUTTON_NEGATIVE, "Cancel"
                ) { dialog, which -> dialog.dismiss() }
                alertDialog.setButton(
                        AlertDialog.BUTTON_POSITIVE, "Erase"
                ) { dialog, which ->
                    lifecycleScope.launch {
                        repository.delete(repository.get(index))
                        dialog.dismiss()
                        inspiringPersonAdapter.setItems(repository.getAll())
                    }
                }
                alertDialog.show()
            }

            override fun editItem(index: Int) {
                editInspiringPersonFragment =
                        EditInspiringPeopleFragment(
                                index,
                                createFragmentListener(),
                                repository
                        )
                supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, editInspiringPersonFragment)
                        .addToBackStack("FragmentTwo")
                        .commit()
            }
        }
    }

    private fun createFragmentListener(): FragmentListener {
        return object :
                FragmentListener {
            override fun backAction() {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, inspiringPeopleFragment)
                        .commit()
                lifecycleScope.launch {
                    inspiringPersonAdapter.setItems(repository.getAll())
                }
            }

            override fun addNewItem() {
                editInspiringPersonFragment =
                        EditInspiringPeopleFragment(
                                -1,
                                createFragmentListener(),
                                repository
                        )
                supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, editInspiringPersonFragment)
                        .addToBackStack("FragmentTwo")
                        .commit()
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
        lifecycleScope.launch {
            inspiringPersonAdapter.setItems(repository.getAll())
        }
    }
}
