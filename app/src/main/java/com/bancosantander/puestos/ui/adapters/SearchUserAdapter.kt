package com.bancosantander.puestos.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.models.User


/**
 * Created by bangulo on 16/01/2018.
 */
class SearchUserAdapter(context: Context, resource: Int, textViewResourceId: Int, listUser: MutableList<User>) : ArrayAdapter<User>(context, resource, textViewResourceId, listUser) {
    var tempItems: MutableList<User>
    var suggestions: MutableList<User>
    var listUser: MutableList<User>

    init {
        this.listUser = listUser
        tempItems = listUser.map { it }.toMutableList()
        suggestions = mutableListOf()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_search_autocompletetextview, parent, false)
        }
        var user: User? = listUser[position]
        user?.let {
            (view?.findViewById(R.id.lbl_searched_name) as TextView)?.let {
                it.text = user.fullname
            }

        }
        return view!!
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                constraint?.let {
                    suggestions.clear()
                    tempItems.forEach {
                        if (it.fullname.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            suggestions.add(it)
                        }
                    }
                    var filterResults = FilterResults()
                    filterResults.values = suggestions
                    filterResults.count = suggestions.size

                    return filterResults

                } ?:
                        return FilterResults()
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                results?.let {
                    if (results.count > 0) {
                        clear()
                        (results.values as List<User>).forEach {
                            add(it)
                            notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }

}