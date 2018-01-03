package com.bancosantander.puestos.ui.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import com.bancosantander.puestos.ui.fragments.TutorialFragment
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpFragment

/**
 * Created by bangulo on 18/12/2017.
 */
class TutorialAdapter(fm: FragmentManager, private val tutoImages :ArrayList<Int>) : FragmentStatePagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return TutorialFragment.getInstance(tutoImages[position])
    }

    override fun getCount(): Int {
      return tutoImages.size
    }
}