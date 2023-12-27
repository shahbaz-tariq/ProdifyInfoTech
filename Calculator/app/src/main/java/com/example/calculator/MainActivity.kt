package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var lastNumeric = false
    private var stateError = false
    private var lastDot = false

    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onBackClick(view: View) {

        binding.tvData.text = binding.tvData.text.toString().dropLast(1)

        try {
            val lastChar = binding.tvData.text.toString().last()
            if(lastChar.isDigit()){
                onEqual()
            }
        }catch (e: Exception){
            binding.tvResult.text = ""
            binding.tvResult.visibility = View.GONE
            Log.e("last char error",e.toString())
        }

    }

    fun onOperatorClick(view: View) {

        if(!stateError && lastNumeric){
            binding.tvData.append((view as Button).text)
            lastDot = false
            lastNumeric = false
            onEqual()
        }

    }

    fun onDigitClick(view: View) {

        if(stateError){
            binding.tvData.text = (view as Button).text
            stateError = false
        }else{
            binding.tvData.append((view as Button).text)
        }
        lastNumeric = true
        onEqual()

    }

    fun onAllClearClick(view: View) {

        binding.tvData.text = ""
        binding.tvResult.text = ""
        stateError = false
        lastDot = false
        lastNumeric = false
        binding.tvResult.visibility = View.GONE

    }
    fun onEqualClick(view: View) {

        onEqual()
        binding.tvData.text = binding.tvResult.text.toString().drop(1)

    }

    fun onEqual(){

        if (lastNumeric && !stateError){
            val txt = binding.tvData.text.toString()
            expression = ExpressionBuilder(txt).build()

            try {
                val result = expression.evaluate()
                binding.tvResult.visibility = View.VISIBLE
                binding.tvResult.text = "=$result"
            }catch (ex: ArithmeticException){
                Log.e("Evaluate Error",ex.toString())
                binding.tvResult.text = "Error"
                stateError = true
                lastNumeric = false
            }

        }

    }

    /*fun onClearClick(view: View) {
        binding.tvData.text = ""
        lastNumeric = false
    }*/

}