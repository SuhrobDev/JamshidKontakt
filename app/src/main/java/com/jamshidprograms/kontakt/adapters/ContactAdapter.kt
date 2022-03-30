package com.jamshidprograms.kontakt.adapters
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jamshidprograms.kontakt.MainActivity
import com.jamshidprograms.kontakt.databinding.ItemContactBinding
import com.jamshidprograms.kontakt.models.ContactData

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private var contactList = ArrayList<ContactData>()
    private var deleteListener: ((position: Int) -> Unit)? = null
    private var callListener : ((position:Int, nomer:String) ->Unit)?=null
    fun setDeleteListener(f: (position: Int) -> Unit) {
        deleteListener = f
    }
    fun setCallListener(f:(posistion:Int, nomer:String) ->Unit){
        callListener = f
    }
    fun addContact(contact: ContactData) {
        contactList.add(contact)
//        notifyDataSetChanged() bu listni to`liq yangilaydi
        notifyItemInserted(contactList.size - 1)//bu aynan positsiyani yangilaydi
    }

    fun deleteContact(position: Int) {

        contactList.removeAt(position)
//        notifyDataSetChanged()
        notifyItemRemoved(position)
    }
    fun position():Int{
        return s
    }
    fun editContact(newContact: ContactData, position: Int) {
        contactList[position] = newContact
        notifyItemChanged(position)//positsiyadagini yangilaydi
    }

    //-----homework-----//

    //favourite
    // share
    // search
    //call
    //pop up menu

    var s = 0
    private var editListener: ((position: Int, name: String, number: String) -> Unit)? = null


    fun setEditListener(f: (position: Int, name: String, number: String) -> Unit) {
        editListener = f
    }

    inner class ContactViewHolder(var binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(contactData: ContactData) {
            binding.name.text = contactData.name
            binding.number.text = contactData.number
            binding.delete.setOnClickListener {

                deleteListener?.invoke(layoutPosition)
            }
            binding.edit.setOnClickListener {
                editListener?.invoke(layoutPosition, contactData.name, contactData.number)
            }
            binding.call.setOnClickListener {
                callListener?.invoke(layoutPosition, contactData.number)
//
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ContactViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) =
        holder.bindData(contactList[position])


    override fun getItemCount() = contactList.size
}