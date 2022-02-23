package com.android.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.common.R
import com.android.common.widget.model.CustomSpinner

class CustomSpinnerWidget : ConstraintLayout {
    var customSpinnerLists : MutableList<CustomSpinner> = ArrayList()
    private var parentContainer : ConstraintLayout? = null
    private var spinnerList : AppCompatSpinner? = null
    private var titleText : AppCompatTextView? = null
    private var icArrow : ImageView? = null
    private var listener : OnItemClickedListener? = null
    private var hintWord = "-"
    private var isExpanded = false
    private var isEnable = true
    private var initiateValue = true
    private var title = "Title"
    constructor(context : Context) : super ( context ){
        initView(context)
    }
    constructor(context : Context , attrs : AttributeSet) : super ( context , attrs ){
        initView(context)
    }
    constructor(context : Context , attrs : AttributeSet , defStyle : Int) : super ( context , attrs , defStyle){
        initView(context)
    }
    private fun initView(context : Context){
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_spinner_widget , this)
        spinnerList = this.findViewById(R.id.spinnerList)
        icArrow = this.findViewById(R.id.icArrow)
        titleText = this.findViewById(R.id.titleText)
        parentContainer = this.findViewById(R.id.parentContainer)
        parentContainer?.setOnClickListener {
            if(isEnable){
                if(isExpanded){
                    icArrow?.rotationY = 0f
                } else {
                    icArrow?.rotationY = 180f
                }
            }
        }
    }

    fun setEnable(isEnable : Boolean){
        this.isEnable = isEnable
    }

    fun setHintWord(hintWord : String){
        this.hintWord = hintWord
    }

    fun setTitle(title : String){
        this.title = title
        titleText?.let {
            it.text = title
        }
    }

    fun setOnItemClickedListener(listener : OnItemClickedListener){
        this.listener = listener
    }

    fun setCustomSpinnerList(customSpinnerLists : MutableList<CustomSpinner>){
        //this.customSpinnerLists.add(CustomSpinner(-1 , hintWord))
        this.customSpinnerLists = customSpinnerLists
    }

    fun setSelectedValue(value : String){
        for((index , data) in customSpinnerLists.withIndex()){
            if(data.text.equals(value , ignoreCase = true)){
                spinnerList?.setSelection(index)
            }
        }
    }

    fun setAdapter(context : Context){
        val spinnerArray = arrayOfNulls<String>(customSpinnerLists.size)
        for((index , model) in customSpinnerLists.withIndex()){
            spinnerArray[index] = model.text
        }
        val spinnerAdapter = ArrayAdapter<String>(context , R.layout.custom_spinner_textview , spinnerArray)
        spinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_textview)
        spinnerList?.adapter = spinnerAdapter
        spinnerList?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                listener?.onItemClicked(customSpinnerLists[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                listener?.onItemClicked(customSpinnerLists[0])
            }
        }
    }

    fun setInitiateValue(initiateValue : Boolean){
        this.initiateValue = initiateValue
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        spinnerList = this.findViewById(R.id.spinnerList)
        icArrow = this.findViewById(R.id.icArrow)
    }

    interface OnItemClickedListener {
        fun onItemClicked(data : CustomSpinner)
    }
}