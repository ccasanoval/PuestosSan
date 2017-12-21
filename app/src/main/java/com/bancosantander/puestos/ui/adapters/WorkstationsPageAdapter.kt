package com.bancosantander.puestos.ui.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpFragment

/**
 * Created by bangulo on 18/12/2017.
 */
class WorkstationsPageAdapter(fm: FragmentManager, private val context: Context,val tabs : ArrayList<BaseMvpFragment<*,*>>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return tabs.get(position);
    }

    override fun getCount(): Int {
        // Show 5 total pages.
        return tabs.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // return null to show no title.
        return tabs.get(position).tag

    }

}