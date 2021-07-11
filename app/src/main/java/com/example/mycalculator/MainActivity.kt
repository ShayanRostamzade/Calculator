package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    fun onDigit(view: View){
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View){
        //tvInput.setText(null)
        tvInput.text = ""
        lastDot = false
    }

    fun onDecimalPoint(view: View){
        if (lastNumeric && !lastDot){
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View){
        if (lastNumeric){
            var tvValue = tvInput.text.toString()
            var prefix = ""
           try {
               if (tvValue.startsWith("-")){
                   prefix = "-"
                   tvValue = tvValue.substring(1)
               }
               if (tvValue.contains("-")){
                   val split = tvValue.split("-")
                   var leftSplit = split[0]
                   var rightSplit = split[1]
                   if (!prefix.isEmpty())
                       leftSplit = prefix + leftSplit
                   var outputText = (leftSplit.toDouble() - rightSplit.toDouble()).toString()
                   tvInput.text = removeZeroAfterDot(outputText)
               }

               else if (tvValue.contains("+")){
                   val split = tvValue.split("+")
                   var leftSplit = split[0]
                   var rightSplit = split[1]
                   var outputText = (leftSplit.toDouble() + rightSplit.toDouble()).toString()
                   tvInput.text = removeZeroAfterDot(outputText)
               }

               else if (tvValue.contains("*")){
                   val split = tvValue.split("*")
                   var leftSplit = split[0]
                   var rightSplit = split[1]
                   var outputText = (leftSplit.toDouble() * rightSplit.toDouble()).toString()
                   tvInput.text = removeZeroAfterDot(outputText)
               }

               else if (tvValue.contains("/")){
                   val split = tvValue.split("/")
                   var leftSplit = split[0]
                   var rightSplit = split[1]
                   var outputText = (leftSplit.toDouble() / rightSplit.toDouble()).toString()
                   tvInput.text = removeZeroAfterDot(outputText)
               }

           } catch (e: ArithmeticException){
               e.printStackTrace()
           }
        }
    }

    private fun removeZeroAfterDot(result: String) : String{
        var noZeroVal = result
        if (result.contains(".0"))
            noZeroVal = result.substring(0, result.length - 2)
        return noZeroVal
    }

    fun onOperator(view: View){
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }

    }

    private fun isOperatorAdded(value: String): Boolean{

        // if the text starts with a minus sign it shouldn't be considered as an operator
        return if (value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("*")
                || value.contains("-") || value.contains("+")
        }
    }

}